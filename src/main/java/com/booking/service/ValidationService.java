package com.booking.service;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Service;

import java.util.List;

public class ValidationService {

    public static boolean validateCustomerId(String id, List<Person> personList) {
        for (Person person : personList) {
            if (person instanceof Customer && person.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateEmployeeId(String id, List<Person> personList) {
        for (Person person : personList) {
            if (person instanceof Employee && person.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    public static boolean validateServiceId(List<Service> serviceList, String id) {
        for (Service service : serviceList) {
            if (service.getServiceId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateWorkStage(String workStage) {
        return workStage.equalsIgnoreCase("in process");
    }
}
