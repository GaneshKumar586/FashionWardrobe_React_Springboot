package com.gana.model;

//import jakarta.persistence.Entity;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class PaymentInfo {
    @Column(name = "cardholder_name")
    private String cardholderName;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "cvv")
    private String cvv;
}
