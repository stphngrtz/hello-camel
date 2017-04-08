package de.stphngrtz.camel.component;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.ScheduledPollEndpoint;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;

@UriEndpoint(scheme = "my", syntax = "?", title = "?")
public class MyEndpoint extends ScheduledPollEndpoint {

    @UriParam
    private int count = 10;

    @UriParam
    private boolean uppercase = true;

    MyEndpoint(String uri, MyComponent myComponent) {
        super(uri, myComponent);
    }

    @Override
    public Producer createProducer() throws Exception {
        return new MyProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        MyConsumer myConsumer = new MyConsumer(this, processor);
        configureConsumer(myConsumer);
        return myConsumer;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    int getCount() {
        return count;
    }

    @SuppressWarnings("unused")
    public void setCount(int count) {
        this.count = count;
    }

    boolean isUppercase() {
        return uppercase;
    }

    @SuppressWarnings("unused")
    public void setUppercase(boolean uppercase) {
        this.uppercase = uppercase;
    }
}
