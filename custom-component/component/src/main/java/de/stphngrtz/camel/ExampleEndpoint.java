package de.stphngrtz.camel;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;

/**
 * Represents a Example Component endpoint.
 */
@UriEndpoint(scheme = "example", title = "Example Component", syntax = "example:name", consumerClass = ExampleConsumer.class, label = "Example Component")
public class ExampleEndpoint extends DefaultEndpoint {

    @UriPath
    @Metadata(required = "true")
    private String name;

    @UriParam(defaultValue = "10")
    private int option = 10;

    public ExampleEndpoint() {
    }

    public ExampleEndpoint(String uri, ExampleComponent component) {
        super(uri, component);
    }

    public ExampleEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public Producer createProducer() throws Exception {
        return new ExampleProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        return new ExampleConsumer(this, processor);
    }

    public boolean isSingleton() {
        return true;
    }

    /**
     * Some description of this option, and what it does
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Some description of this option, and what it does
     */
    public void setOption(int option) {
        this.option = option;
    }

    public int getOption() {
        return option;
    }
}
