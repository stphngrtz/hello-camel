package de.stphngrtz.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.UriEndpointComponent;

import java.util.Map;

/**
 * Represents the component that manages {@link ExampleEndpoint}.
 */
public class ExampleComponent extends UriEndpointComponent {

    public ExampleComponent() {
        super(ExampleEndpoint.class);
    }

    public ExampleComponent(CamelContext context) {
        super(context, ExampleEndpoint.class);
    }

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        Endpoint endpoint = new ExampleEndpoint(uri, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }
}
