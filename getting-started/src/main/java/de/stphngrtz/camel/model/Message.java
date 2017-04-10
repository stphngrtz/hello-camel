package de.stphngrtz.camel.model;

import java.util.Date;
import java.util.List;

public class Message {

    public Integer nr;
    public Date date;
    public String customer;
    public Address address;
    public List<Position> positions;
}
