package de.stphngrtz.camel;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Example Component producer.
 */
public class ExampleProducer extends DefaultProducer {
    private static final Logger LOG = LoggerFactory.getLogger(ExampleProducer.class);
    private ExampleEndpoint endpoint;

    public ExampleProducer(ExampleEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
        System.out.println(exchange.getIn().getBody());
    }
}
