package com.ccsw.tutorial.reservation.exception;

import org.springframework.http.HttpStatus;

public class GameAlreadyReservedException extends RuntimeException {

    private String message;

    public GameAlreadyReservedException() {
    }

    public GameAlreadyReservedException(String message) {
        super(message);
        this.message = message;
    }
}
