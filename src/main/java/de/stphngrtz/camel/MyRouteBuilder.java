package de.stphngrtz.camel;

import de.stphngrtz.camel.model.Message;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;
import org.apache.camel.component.http4.HttpMethods;
import org.apache.camel.model.dataformat.JsonLibrary;

public class MyRouteBuilder extends RouteBuilder {

    public void configure() {
        getContext().setTracing(true);
        getContext().addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:61616"));

        from("file:src/data?noop=true")
                .id("file-consumer")
                .to("direct:coordinator");

        from("jetty:http://0.0.0.0:8081/messages")
                .id("jetty-consumer")
                .convertBodyTo(String.class)
                .inOnly("direct:coordinator")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));

        from("direct:coordinator")
                .id("coordinator")
                .setProperty("id").jsonpath("$.nr")
                .transform(body().regexReplaceAll("([0-9]{2})\\.([0-9]{2})\\.([0-9]{4})", "$3-$2-$1"))
                .enrich("direct:geocoder", (o, n) -> {
                    o.getIn().setHeader("destination", n.getIn().getBody());
                    return o;
                })
                .filter(header("destination").isEqualTo(""))
                /* */.log("destination missing, using default...")
                /* */.setHeader("destination", constant("40.706574,-73.996829"))
                .end()
                .log("destination: ${header.destination}")
                .choice()
                /* */.when(exchangeProperty("id").isGreaterThan(500)).to("direct:gt500")
                /* */.otherwise().to("direct:lt500");

        from("direct:geocoder")
                .id("geocoder")
                .setProperty("zip").jsonpath("$.address.zip")
                .setProperty("city").jsonpath("$.address.city")
                .log("ZIP: ${property.zip}, City: ${property.city}")
                .setHeader(Exchange.HTTP_PATH, constant("/geocode"))
                .setHeader(Exchange.HTTP_QUERY, simple("FreeFormAddress=DE,${property.zip}%20${property.city},DE&MaxResponse=1&api_key=ee0b8233adff52ce9fd6afc2a2859a28"))
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
                .to("http4://openls.geog.uni-heidelberg.de?bridgeEndpoint=true")
                .setBody().xpath("//gml:pos/text()", new Namespaces("gml", "http://www.opengis.net/gml"))
                .setBody(body().regexReplaceAll(" ", ","));

        from("direct:gt500")
                .id("gt500")
                .log("${property.id}")
                .unmarshal().json(JsonLibrary.Jackson, Message.class)
                .bean(MyBean.class)
                .log("${body}")
                .to("rabbitmq://localhost:5672/gt500");

        from("direct:lt500")
                .id("lt500")
                .log("${property.id}")
                .unmarshal().json(JsonLibrary.Jackson, Message.class)
                .bean(MyBean.class)
                .log("${body}")
                .to("activemq:lt500");

        from("rabbitmq://localhost:5672/gt500")
                .id("RabbitMQ")
                .log("${body} (${header.destination})");

        from("activemq:lt500")
                .id("ActiveMQ")
                .log("${body} (${header.destination})");

        from("my:name?count=5&uppercase=true")
                .id("my-component")
                .log("${body}")
                .to("my:name");
    }
}
