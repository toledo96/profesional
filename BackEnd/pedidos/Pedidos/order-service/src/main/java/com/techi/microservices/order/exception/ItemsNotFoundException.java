package com.techi.microservices.order.exception;

public class ItemsNotFoundException extends RuntimeException{
    public ItemsNotFoundException(String message) {
        super(message);
    }
}
