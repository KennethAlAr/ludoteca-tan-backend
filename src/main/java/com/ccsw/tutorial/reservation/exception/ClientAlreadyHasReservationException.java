package com.ccsw.tutorial.reservation.exception;

public class ClientAlreadyHasReservationException extends RuntimeException {

    private String message;

    public ClientAlreadyHasReservationException() {
    }

    public ClientAlreadyHasReservationException(String message) {
        super(message);
        this.message = message;
    }
}
