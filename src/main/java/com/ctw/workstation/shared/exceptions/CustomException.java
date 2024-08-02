package com.ctw.workstation.shared.exceptions;

import jakarta.ws.rs.core.Response;


public class CustomException extends RuntimeException{
    private Response.Status status;

    public CustomException(Response.Status status, String message) {
        super(message);
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }

    public static Response throwCustomException(Response.Status status, String message) {
        throw new CustomException(status, message);
    }
}
