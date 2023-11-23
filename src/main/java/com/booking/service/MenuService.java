package com.booking.service;

import com.booking.models.Person;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ReservationRepository;
import com.booking.repositories.ServiceRepository;

import java.util.List;
import java.util.Scanner;

public class MenuService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Service> serviceList = ServiceRepository.getAllService();
    //    private static List<Reservation> reservationList = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);


    public static void mainMenu() {
        String[] mainMenuArr = {"Show Data", "Create Reservation", "Complete/cancel reservation", "Exit"};
        String[] subMenuArr = {"Recent Reservation", "Show Customer", "Show Available Employee",
                "Tampilan History Reservation + Total Keuntungan", "Back To Main menu"};

        int optionMainMenu;
        int optionSubMenu;

        boolean backToMainMenu = false;
        boolean backToSubMenu = false;
        do {
            PrintService.printMenu("Main Menu", mainMenuArr);
            optionMainMenu = Integer.valueOf(input.nextLine());
            switch (optionMainMenu) {
                case 1:
                    do {
                        PrintService.printMenu("Show Data", subMenuArr);
                        optionSubMenu = Integer.valueOf(input.nextLine());
                        // Sub menu - menu 1
                        switch (optionSubMenu) {
                            case 1:
                                // panggil fitur tampilkan recent reservation
                                PrintService.showRecentReservation(ReservationRepository.getInstance().reservationList());
                                break;
                            case 2:
                                // panggil fitur tampilkan semua customer
                                PrintService.showAllCustomer(personList);
                                break;
                            case 3:
                                // panggil fitur tampilkan semua employee
                                PrintService.showAllEmployee(personList);
                                break;
                            case 4:
                                // panggil fitur tampilkan history reservation + total keuntungan
                                PrintService.showHistoryReservation(ReservationRepository.getInstance().reservationList());
                                break;
                            case 0:
                                backToSubMenu = true;
                        }
                    } while (!backToSubMenu);
                    break;
                case 2:
                    // panggil fitur menambahkan reservation
                    ReservationService.createReservation(personList, serviceList);
                    break;
                case 3:
                    // panggil fitur mengubah workstage menjadi finish/cancel
                    PrintService.showRecentReservation(ReservationRepository.getInstance().reservationList());
                    ReservationService.editReservationWorkstage();
                    break;
                case 0:
                    backToMainMenu = true;
                    PrintService.printExit();
                    System.exit(0);
                    break;
            }
        } while (!backToMainMenu);

    }
}
