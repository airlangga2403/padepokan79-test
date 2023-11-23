package com.booking.repositories;

import com.booking.models.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {
    private static final List<Reservation> createReservation = new ArrayList<>();
    private static final ReservationRepository instance = new ReservationRepository();

    private ReservationRepository() {
        // Private constructor untuk mencegah pembuatan instance lain di luar kelas ini
    }

    public static ReservationRepository getInstance() {
        return instance;
    }

    public List<Reservation> reservationList() {
        return createReservation;
    }
}
