package de.stphngrtz.camel.component;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;

public class MyConsumer extends ScheduledPollConsumer {

    private MyEndpoint myEndpoint;
    private int count = 0;

    MyConsumer(MyEndpoint myEndpoint, Processor processor) {
        super(myEndpoint, processor);
        this.myEndpoint = myEndpoint;
    }

    @Override
    protected int poll() throws Exception {
        if (count < myEndpoint.getCount()) {
            count++;

            String body = "hello world ("+count+"/"+myEndpoint.getCount()+")";
            if (myEndpoint.isUppercase())
                body = body.toUpperCase();

            Exchange exchange = myEndpoint.createExchange();
            exchange.getIn().setBody(body);

            getProcessor().process(exchange);
            return 1;
        }
        return 0;
    }
}
