package org.csii.yeeframe.extend;


public class RespondError extends Exception {
    private static final long serialVersionUID = 1L;

    public String code;
    
    public RespondError(String code, String message) {
    	super(message);
        this.code = code;
    }
    
    public RespondError(String code, String message,Throwable e) {
        super(message,e);
        this.code = code;
    }
}
