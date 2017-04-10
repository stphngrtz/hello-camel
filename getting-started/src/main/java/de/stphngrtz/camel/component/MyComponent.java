package de.stphngrtz.camel.component;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class MyComponent extends DefaultComponent {

    private final Logger log = LoggerFactory.getLogger(MyComponent.class);

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        log.debug("MyComponent: {}", remaining);
        MyEndpoint myEndpoint = new MyEndpoint(uri, this);
        setProperties(myEndpoint, parameters);
        return myEndpoint;
    }
}
