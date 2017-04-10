package de.stphngrtz.camel.component;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyProducer extends DefaultProducer {

    private final Logger log = LoggerFactory.getLogger(MyComponent.class);

    MyProducer(Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        log.debug("MyProducer: {}", exchange.getIn().getBody());
    }
}
