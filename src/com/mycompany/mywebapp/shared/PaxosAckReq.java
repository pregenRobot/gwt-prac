package com.mycompany.mywebapp.shared;

import java.io.Serializable;
import com.google.gwt.user.client.rpc.IsSerializable;

public class PaxosAckReq extends PaxosReq{

    public Boolean previouslyAccepted;
    public Integer ballotId;
    public String decree;
}
