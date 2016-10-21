package com.jbee.domain;

/**
 * Created by Jbee on 2016. 10. 21..
 */
public class Result {
    private boolean valid;

    private String errorMessage;

    public Result(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return errorMessage;
    }

    public boolean isValid() {
        return valid;
    }

    public static Result ok(){
        return new Result(true, null);
    }

    public static Result fail(String errorMessage){
        return new Result(false, errorMessage);
    }
}
