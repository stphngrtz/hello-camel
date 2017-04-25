package de.stphngrtz.camel;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.camel.component.kafka.KafkaConstants;

public class MyRouteBuilder extends RouteBuilder {

    private static final boolean RABBITMQ_PRODUCER = false;
    private static final boolean RABBITMQ_CONSUMER = false;
    private static final int RABBITMQ_PORT = 5672;

    private static final boolean KAFKA_PRODUCER = false;
    private static final boolean KAFKA_CONSUMER = false;
    private static final int KAFKA_PORT = 32771;

    private static final boolean ACTIVEMQ_PRODUCER = false;
    private static final boolean ACTIVEMQ_CONSUMER = false;
    private static final int ACTIVEMQ_PORT = 61616;

    private static final boolean ARTEMIS_PRODUCER = true;
    private static final boolean ARTEMIS_CONSUMER = false;
    private static final int ARTEMIS_PORT = 5672;

    private static final String TOPIC = "test";

    public void configure() {
        getContext().setTracing(false);
        getContext().addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://localhost:" + ACTIVEMQ_PORT));
        getContext().addComponent("amqp", AMQPComponent.amqpComponent("amqp://localhost:" + ARTEMIS_PORT, "admin", "admin"));

        if (RABBITMQ_PRODUCER)
            from("jetty:http://0.0.0.0:8081")
                    .inOnly("rabbitmq://localhost:" + RABBITMQ_PORT + "/" + TOPIC) // channelPoolMaxWait=2000
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                    .setBody(constant(""));

        if (RABBITMQ_CONSUMER)
            from("rabbitmq://localhost:" + RABBITMQ_PORT + "/" + TOPIC)
                    .log("${body}");

        if (KAFKA_PRODUCER)
            from("jetty:http://0.0.0.0:8082")
                    .convertBodyTo(String.class)
                    .setProperty("key").jsonpath("$.nr", String.class)
                    .setHeader(KafkaConstants.KEY, exchangeProperty("key"))
                    .inOnly("kafka:localhost:" + KAFKA_PORT + "?topic=" + TOPIC) // + "&keySerializerClass=org.apache.kafka.common.serialization.IntegerSerializer")
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                    .setBody(constant(""));

        if (KAFKA_CONSUMER)
            from("kafka:localhost:" + KAFKA_PORT + "?topic=" + TOPIC + "&groupId=testing&consumersCount=3")
                    .log("consumed: key:${header.kafka.KEY} offset:${header.kafka.OFFSET} partition:${header.kafka.PARTITION} topic:${header.kafka.TOPIC}")
                    // .log("${body}")
                    ;

        if (ACTIVEMQ_PRODUCER)
            from("jetty:http://0.0.0.0:8083")
                    .inOnly("activemq:" + TOPIC)
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                    .setBody(constant(""));

        if (ACTIVEMQ_CONSUMER)
            from("activemq:" + TOPIC)
                    .log("${body}");

        if (ARTEMIS_PRODUCER)
            from("jetty:http://0.0.0.0:8084")
                    .inOnly("amqp:topic:" + TOPIC)
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                    .setBody(constant(""));

        if (ARTEMIS_CONSUMER)
            from("amqp:topic:" + TOPIC)
                    .log("${body}");
    }
}
