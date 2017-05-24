package com.baomidou.springwind.exception;

/**
 * Created by Woody on 2017/5/16.
 */
public class ShopException extends Exception {
    private static final long serialVersionUID = 1L;

    public ShopException(String message) {
        super(message);
    }

    public ShopException(Throwable throwable) {
        super(throwable);
    }

    public ShopException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
