package org.facengineer.PublicTools;

import org.facengineer.Model.Person;

public class BaseRequest {
    private String requesttype;
    private Object data;

    public String getRequestType() {
        return requesttype;
    }

    public void setRequestType(String requestType) {
        this.requesttype = requestType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
