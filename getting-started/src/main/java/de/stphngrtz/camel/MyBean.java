package de.stphngrtz.camel;

import de.stphngrtz.camel.model.Message;

public class MyBean {

    @SuppressWarnings("unused")
    public String transform(Message message) {
        return message.nr + " (" + (message.positions == null ? 0 : message.positions.size()) + " positions)";
    }
}
