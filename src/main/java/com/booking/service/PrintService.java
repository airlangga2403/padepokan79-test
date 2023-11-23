package com.booking.service;

import com.booking.models.*;

import java.text.DecimalFormat;
import java.util.List;

public class PrintService {
    public static void printMenu(String title, String[] menuArr) {
        int num = 1;
        System.out.println(title);
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);
            num++;
        }
    }

    public static String printServices(List<Service> serviceList) {

        String result = "";
        if (serviceList == null || serviceList.isEmpty()) {
            return "Service list is empty or null.";
        }
        // Bisa disesuaikan kembali
        for (Service service : serviceList) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }

    public static void printServicesCreates(List<Service> serviceList) {

        // Bisa disesuaikan kembali
        System.out.printf("| %-4s | %-4s | %-11s | %-15s\n",
                "No.", "ID", "Nama", "Harga");
        int num = 1;
        for (Service service : serviceList) {
            System.out.printf("| %-4s | %-4s | %-11s | %-15s |\n",
                    num, service.getServiceId(), service.getServiceName(), service.getPrice());
            num += 1;
        }
    }

    // Function yang dibuat hanya sebgai contoh bisa disesuaikan kembali
    public static void showRecentReservation(List<Reservation> reservationList) {
        int num = 1;
        System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+========================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("In process")) {
                System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                        num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), reservation.getReservationPrice(), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
            }
        }
    }

    public static void showAllCustomer(List<Person> peopleList) {
        System.out.println(" | No | \tID\t   | \tNama\t  |   \tAlamat\t   | \tMembership| \tUang \t|");

        DecimalFormat formatter = new DecimalFormat("Rp#,##0");

        int count = 1;
        for (Person person : peopleList) {
            if (person instanceof Customer) {
                Customer customer = (Customer) person;
                System.out.printf(" | %d |  \t%s\t   |  \t%s\t  |   \t%s\t  |   \t%s\t |  \t%s\t    | \n",
                        count++,
                        customer.getId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getMember().getMembershipName(),
                        formatter.format(customer.getWallet()));
            }
        }
    }

    public static void showAllEmployee(List<Person> peopleList) {
        System.out.println(" | No | \tID | \tNama | \tAlamat | \tPengalaman |");

        DecimalFormat formatter = new DecimalFormat("Rp#,##0");

        int count = 1;
        for (Person person : peopleList) {
            if (person instanceof Employee) {
                Employee employee = (Employee) person;
                System.out.printf(" | %d | \t%s  | \t%s | \t%s | \tExperience: %d years | \n",
                        count++,
                        employee.getId(),
                        employee.getName(),
                        employee.getAddress(),
                        employee.getExperience());
            }
        }

    }

    public static void showHistoryReservation(List<Reservation> reservationList) {
        int num = 1;
        System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+========================================================================================+");
        int totalPrice = 0;
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Finish")) {
                System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                        num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), reservation.getReservationPrice(), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
                totalPrice += reservation.getReservationPrice();
            } else if (reservation.getWorkstage().equalsIgnoreCase("Canceled")) {
                System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                        num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), reservation.getReservationPrice(), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
            }
        }
        System.out.printf("Total Keuntungan : Rp %-11s \n", totalPrice);
    }


    //
    public static void printInput(String input) {
        System.out.printf("Silahkan masukkan %s ", input);
    }

    public static void printExit() {
        System.out.println("Aplikasi telah berhenti ... !");
    }

    public static void printNotFound(String notFoundText) {
        System.out.printf("Data %s tidak ditemukan. Silakan coba lagi.\n", notFoundText);
    }
    public static void printChooseAnotherService() {
        System.out.println("Ingin Pilih Service Yang Lain (Y) / (T)");
    }

    public static void printServiceIdInvalidOrChosenBefore() {
        System.out.println("Service ID tidak valid atau sudah dipilih sebelumnya.");
    }

    public static void printBookingSuccessful(double totalBookingCost) {
        System.out.printf("Booking Berhasil\nTotal Biaya Booking : Rp. %,.0f\n", totalBookingCost);
    }
}
