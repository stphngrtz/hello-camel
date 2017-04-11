package de.stphngrtz.camel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;

public class MyRouteBuilder extends RouteBuilder {

    public void configure() {
        getContext().setTracing(false);

        from("jetty:http://0.0.0.0:8081")
                .inOnly("rabbitmq://localhost:5672/performance-tests") // channelPoolMaxWait=2000
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .setBody(constant(""));

        /*
        from("rabbitmq://localhost:5672/performance-tests")
                .log("${body}");
        */

        from("jetty:http://0.0.0.0:8082")
                .convertBodyTo(String.class)
                .setHeader(KafkaConstants.PARTITION_KEY, constant(0))
                .setHeader(KafkaConstants.KEY, constant("1"))
                .inOnly("kafka:localhost:9092?topic=performance-tests")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .setBody(constant(""));
        /*
        from("kafka:localhost:9092?topic=performance-tests&groupId=testing&autoOffsetReset=earliest&consumersCount=1")
                .log("${body}");
        */
    }
}