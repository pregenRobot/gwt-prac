package com.mycompany.mywebapp.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TACSMessage implements IsSerializable{
    public int sourcePid;
    public int targetPid;
    public String message;
    public long sendTime;
    public long receiveTime;


    public String getLogMessage(){
        return "<MESSAGE source=" + sourcePid + ", target=" + targetPid + ", content='" + message + "', sendTime=" + sendTime + ", receiveTime=" + ">";
    }

    @Override
    public String toString() {
        return "<MESSAGE source=" + sourcePid + ", target=" + targetPid + ", content='" + message + "', sendTime=" + sendTime + ", receiveTime=" + ">";
    }
}
