package com.booking.models;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
public class Reservation {
    private String reservationId;
    private Customer customer;
    private Employee employee;
    private List<Service> services;
    private double reservationPrice;
    private String workstage;
    //   workStage (In Process, Finish, Canceled)

    public Reservation(String reservationId, Customer customer, Employee employee, List<Service> services, Double reservationPrice,
                       String workstage) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.employee = employee;
        this.services = services;
        this.reservationPrice = calculateReservationPrice(reservationPrice, customer);
        this.workstage = workstage;
    }

    private double calculateReservationPrice(double reservationPrice, Customer customer) {
        String membership = customer.getMember().getMembershipName();
        double discount = 0;
        if (membership.equalsIgnoreCase("gold")) {
            discount = 0.1;
        } else if (membership.equalsIgnoreCase("silver")) {
            discount = 0.05;
        }

        return reservationPrice * (1 - discount);
    }

}
