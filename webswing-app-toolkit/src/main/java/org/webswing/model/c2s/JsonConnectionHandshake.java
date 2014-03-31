package org.webswing.model.c2s;


public class JsonConnectionHandshake implements JsonEvent{

    private static final long serialVersionUID = -3865929274935490301L;
    public String clientId;
    public String sessionId;
    public Integer desktopWidth;
    public Integer desktopHeight;
    public String applicationName;
    
    
    @Override
    public String toString() {
        return "JsonConnectionHandshake [clientId=" + clientId + ", sessionId=" + sessionId + ", desktopWidth=" + desktopWidth + ", desktopHeight=" + desktopHeight + ", applicationName=" + applicationName + "]";
    }
}
