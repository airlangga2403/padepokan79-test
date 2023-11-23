package com.booking.service;

import com.booking.models.*;
import com.booking.repositories.ReservationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReservationService {

    private static final Scanner input = new Scanner(System.in);

    public static void createReservation(List<Person> personList, List<Service> serviceList) {
        List<Service> newServiceList = serviceList;
        String inpCustomerId, inpEmployeeId, inpServiceId, isChooseAnotherService;
        List<Service> currentService = new ArrayList<>();

        double totalPriceBooking = 0;

        Customer customer;
        Employee employee;

        customer = null;
        employee = null;

        while (customer == null) {
            PrintService.showAllCustomer(personList);
            PrintService.printInput("Customer ID : \n");
            inpCustomerId = input.nextLine();
            customer = getCustomerByCustomerId(personList, inpCustomerId);

            if (customer == null) {
                PrintService.printNotFound("Customer");
            }
        }

        while (employee == null) {
            PrintService.showAllEmployee(personList);
            PrintService.printInput("Employee ID : \n");
            inpEmployeeId = input.nextLine();
            employee = getEmployee(personList, inpEmployeeId);

            if (employee == null) {
                PrintService.printNotFound("Employee");
            }
        }

        do {
            PrintService.printServicesCreates(newServiceList);
            PrintService.printInput("Service ID : \n");
            inpServiceId = input.nextLine();

            if (ValidationService.validateServiceId(newServiceList, inpServiceId)) {
                String finalInpServiceId = inpServiceId;
                Service chosenService = newServiceList.stream()
                        .filter(service -> service.getServiceId().equals(finalInpServiceId))
                        .findFirst()
                        .orElse(null);

                if (chosenService != null) {
                    currentService.add(chosenService);
                    totalPriceBooking += chosenService.getPrice();

                    PrintService.printChooseAnotherService();
                    isChooseAnotherService = input.nextLine();
                    if (!isChooseAnotherService.equalsIgnoreCase("Y")) {
                        break;
                    } else {
                        newServiceList.remove(chosenService);
                    }
                }
            } else {
                PrintService.printServiceIdInvalidOrChosenBefore();
            }
        } while (!newServiceList.isEmpty());

        Reservation newReservation = new Reservation(
                generateReservationId(),
                customer,
                employee,
                currentService,
                totalPriceBooking,
                "In Process"
        );
        ReservationRepository.getInstance().reservationList().add(newReservation);
        PrintService.printBookingSuccessful(newReservation.getReservationPrice());
    }

    public static void editReservationWorkstage() {
        List<Reservation> reservationList = ReservationRepository.getInstance().reservationList();
        boolean isChangeReservationRunning = true;
        boolean isReservationFound;

        do {
            isReservationFound = false;
            PrintService.printInput("Reservation Id atau tekan 0 untuk kembali ke Menu Utama: ");
            String reservationId = input.nextLine();

            if (reservationId.equals("0")) {
                // Kembali ke Menu Utama
                break;
            }

            // Check Reservasi
            for (Reservation reservation : reservationList) {
                if (reservation.getReservationId().equalsIgnoreCase(reservationId)) {
                    isReservationFound = true;
                    if (ValidationService.validateWorkStage(reservation.getWorkstage())) {
                        PrintService.printInput("Selesaikan Reservasi: ");
                        String newWorkStage = input.nextLine();
                        reservation.setWorkstage(newWorkStage);
                        isChangeReservationRunning = false;
                        System.out.printf("Reservasi dengan %s Sudah %s\n", reservationId, newWorkStage);
                    } else {
                        PrintService.printNotFound("Reservation yang dicari sudah selesai");
                        isChangeReservationRunning = true;
                    }
                    break;
                }
            }

            if (!isReservationFound) {
                PrintService.printNotFound("Reservation yang dicari tidak tersedia");
            }

        } while (isChangeReservationRunning);
    }


    private static Customer getCustomerByCustomerId(List<Person> personList, String idCustomer) {
        return personList.stream()
                .filter(person -> person instanceof Customer && ValidationService.validateCustomerId(idCustomer, personList))
                .map(Customer.class::cast)
                .findFirst()
                .orElse(null);
    }

    private static Employee getEmployee(List<Person> personList, String idEmployee) {
        return personList.stream()
                .filter(person -> person instanceof Employee && ValidationService.validateEmployeeId(idEmployee, personList))
                .map(Employee.class::cast)
                .findFirst()
                .orElse(null);
    }

    private static String generateReservationId() {
        List<Reservation> reservationList = ReservationRepository.getInstance().reservationList();
        int lastIndex = reservationList.size();

        String newReservationId = "Rsv-" + String.format("%02d", lastIndex + 1);

        for (Reservation reservation : reservationList) {
            if (reservation.getReservationId().equals(newReservationId)) {
                return generateReservationId();
            }
        }

        return newReservationId;
    }
}
