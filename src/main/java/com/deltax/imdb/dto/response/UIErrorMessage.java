package com.deltax.imdb.dto.response;

public class UIErrorMessage<T> extends UIResponse<T>{

    private static final long serialVersionUID = 4311696013200578760L;
    private String status;
    private String message;
    private Integer code;
    private String cause;

    public UIErrorMessage(T t)
    {
        super(t);
    }

    public UIErrorMessage()
    {
        super();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

}
