package com.hms.app;

import com.hms.dao.AppointmentDAO;
import com.hms.dao.DoctorDAO;
import com.hms.dao.PatientDAO;
import com.hms.model.Appointment;
import com.hms.model.Doctor;
import com.hms.model.Patient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class HospitalApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DoctorDAO doctorDAO = new DoctorDAO();
    private static final PatientDAO patientDAO = new PatientDAO();
    private static final AppointmentDAO appointmentDAO = new AppointmentDAO();

    public static void main(String[] args) {
        runMenu();
    }

    private static void runMenu() {
        int choice;
        do {
            printMainMenu();
            choice = getUserChoice();

            switch (choice) {
                case 1: doctorMenu(); break;
                case 2: patientMenu(); break;
                case 3: appointmentMenu(); break;
                case 0: System.out.println("Exiting Hospital Management System. Goodbye!"); break;
                default: System.out.println("Invalid choice. Please try again.");
            }
            System.out.println("\n Press Enter to continue...");
            scanner.nextLine(); 
        } while (choice != 0);
        scanner.close(); 
    }

    private static void printMainMenu() {
        System.out.println("\n--- Hospital Management System ---");
        System.out.println("1. Doctor Management");
        System.out.println("2. Patient Management");
        System.out.println("3. Appointment Management");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); 
            return -1;
        } finally {
            scanner.nextLine(); 
        }
    }

    // --- Doctor Menu ---
    private static void doctorMenu() {
        int choice;
        do {
            System.out.println("\n--- Doctor Management ---");
            System.out.println("1. Add Doctor");
            System.out.println("2. View All Doctors");
            System.out.println("3. Update Doctor");
            System.out.println("4. Delete Doctor");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = getUserChoice();

            switch (choice) {
                case 1: addDoctor(); break;
                case 2: viewAllDoctors(); break;
                case 3: updateDoctor(); break;
                case 4: deleteDoctor(); break;
                case 0: break;
                default: System.out.println("Invalid choice. Please try again.");
            }
            if (choice != 0) {
                System.out.println("\n Press Enter to continue...");
                scanner.nextLine();
            }
        } while (choice != 0);
    }

    private static void addDoctor() {
        System.out.println("\n--- Add New Doctor ---");
        System.out.print("Enter Doctor Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Specialization: ");
        String specialization = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        Doctor doctor = new Doctor(name, specialization, phoneNumber, email);
        doctorDAO.addDoctor(doctor);
    }

    private static void viewAllDoctors() {
        System.out.println("\n--- All Doctors ---");
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
        } else {
            doctors.forEach(System.out::println);
        }
    }

    private static void updateDoctor() {
        System.out.println("\n--- Update Doctor ---");
        System.out.print("Enter Doctor ID to update: ");
        int id = getUserChoice(); // Already handles nextLine()
        if (id == -1) return;

        Doctor existingDoctor = doctorDAO.getDoctorById(id);
        if (existingDoctor == null) {
            System.out.println("Doctor with ID " + id + " not found.");
            return;
        }

        System.out.println("Current Doctor Details: " + existingDoctor);
        System.out.print("Enter new Name (or press Enter to keep current: " + existingDoctor.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) existingDoctor.setName(name);

        System.out.print("Enter new Specialization (or press Enter to keep current: " + existingDoctor.getSpecialization() + "): ");
        String specialization = scanner.nextLine();
        if (!specialization.isEmpty()) existingDoctor.setSpecialization(specialization);

        System.out.print("Enter new Phone Number (or press Enter to keep current: " + existingDoctor.getPhoneNumber() + "): ");
        String phoneNumber = scanner.nextLine();
        if (!phoneNumber.isEmpty()) existingDoctor.setPhoneNumber(phoneNumber);

        System.out.print("Enter new Email (or press Enter to keep current: " + existingDoctor.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) existingDoctor.setEmail(email);

        doctorDAO.updateDoctor(existingDoctor);
    }

    private static void deleteDoctor() {
        System.out.println("\n--- Delete Doctor ---");
        System.out.print("Enter Doctor ID to delete: ");
        int id = getUserChoice();
        if (id == -1) return;
        doctorDAO.deleteDoctor(id);
    }

    // --- Patient Menu --- 
    private static void patientMenu() {
        int choice;
        do {
            System.out.println("\n--- Patient Management ---");
            System.out.println("1. Add Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = getUserChoice();

            switch (choice) {
                case 1: addPatient(); break;
                case 2: viewAllPatients(); break;
                case 3: updatePatient(); break;
                case 4: deletePatient(); break;
                case 0: break;
                default: System.out.println("Invalid choice. Please try again.");
            }
            if (choice != 0) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        } while (choice != 0);
    }

    
    
    private static void addPatient() {
        System.out.println("\n--- Add New Patient ---");
        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();
        int age = -1;
        while (age < 0) {
            System.out.print("Enter Age: ");
            try {
                age = Integer.parseInt(scanner.nextLine());
                if (age < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Invalid age. Please enter a positive number.");
            }
        }
        System.out.print("Enter Gender (Male/Female/Other): ");
        String gender = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        Patient patient = new Patient(name, age, gender, phoneNumber, address);
        patientDAO.addPatient(patient);
    }

    private static void viewAllPatients() {
        System.out.println("\n--- All Patients ---");
        List<Patient> patients = patientDAO.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
        } else {
            patients.forEach(System.out::println);
        }
    }

    private static void updatePatient() {
        System.out.println("\n--- Update Patient ---");
        System.out.print("Enter Patient ID to update: ");
        int id = getUserChoice();
        if (id == -1) return;

        Patient existingPatient = patientDAO.getPatientById(id);
        if (existingPatient == null) {
            System.out.println("Patient with ID " + id + " not found.");
            return;
        }

        System.out.println("Current Patient Details: " + existingPatient);
        System.out.print("Enter new Name (or press Enter to keep current: " + existingPatient.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) existingPatient.setName(name);

        int age = existingPatient.getAge();
        System.out.print("Enter new Age (or press Enter to keep current: " + existingPatient.getAge() + "): ");
        String ageStr = scanner.nextLine();
        if (!ageStr.isEmpty()) {
            try {
                age = Integer.parseInt(ageStr);
                if (age >= 0) existingPatient.setAge(age);
                else System.out.println("Invalid age, keeping current.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid age format, keeping current.");
            }
        }

        System.out.print("Enter new Gender (or press Enter to keep current: " + existingPatient.getGender() + "): ");
        String gender = scanner.nextLine();
        if (!gender.isEmpty()) existingPatient.setGender(gender);

        System.out.print("Enter new Phone Number (or press Enter to keep current: " + existingPatient.getPhoneNumber() + "): ");
        String phoneNumber = scanner.nextLine();
        if (!phoneNumber.isEmpty()) existingPatient.setPhoneNumber(phoneNumber);

        System.out.print("Enter new Address (or press Enter to keep current: " + existingPatient.getAddress() + "): ");
        String address = scanner.nextLine();
        if (!address.isEmpty()) existingPatient.setAddress(address);

        patientDAO.updatePatient(existingPatient);
    }

    private static void deletePatient() {
        System.out.println("\n--- Delete Patient ---");
        System.out.print("Enter Patient ID to delete: ");
        int id = getUserChoice();
        if (id == -1) return;
        patientDAO.deletePatient(id);
    }

    // --- Appointment Menu --- 
    private static void appointmentMenu() {
        int choice;
        do {
            System.out.println("\n--- Appointment Management ---");
            System.out.println("1. Schedule Appointment");
            System.out.println("2. View All Appointments");
            System.out.println("3. Update Appointment");
            System.out.println("4. Cancel Appointment");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = getUserChoice();

            switch (choice) {
                case 1: scheduleAppointment(); break;
                case 2: viewAllAppointments(); break;
                case 3: updateAppointment(); break;
                case 4: cancelAppointment(); break;
                case 0: break;
                default: System.out.println("Invalid choice. Please try again.");
            }
            if (choice != 0) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        } while (choice != 0);
    }

    private static void scheduleAppointment() {
        System.out.println("\n--- Schedule New Appointment ---");

        System.out.print("Enter Patient ID: ");
        int patientId = getUserChoice();
        if (patientId == -1) return;
        if (patientDAO.getPatientById(patientId) == null) {
            System.out.println("Patient with ID " + patientId + " does not exist. Please add patient first.");
            return;
        }

        System.out.print("Enter Doctor ID: ");
        int doctorId = getUserChoice();
        if (doctorId == -1) return;
        if (doctorDAO.getDoctorById(doctorId) == null) {
            System.out.println("Doctor with ID " + doctorId + " does not exist. Please add doctor first.");
            return;
        }

        LocalDate apptDate = null;
        while (apptDate == null) {
            System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
            String dateStr = scanner.nextLine();
            try {
                apptDate = LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        LocalTime apptTime = null;
        while (apptTime == null) {
            System.out.print("Enter Appointment Time (HH:MM - 24-hour format): ");
            String timeStr = scanner.nextLine();
            try {
                apptTime = LocalTime.parse(timeStr);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use HH:MM.");
            }
        }

        System.out.print("Enter Reason for Appointment: ");
        String reason = scanner.nextLine();

        Appointment appointment = new Appointment(patientId, doctorId, apptDate, apptTime, reason);
        appointmentDAO.addAppointment(appointment);
    }

    private static void viewAllAppointments() {
        System.out.println("\n--- All Appointments ---");
        List<Appointment> appointments = appointmentDAO.getAllAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            System.out.printf("%-5s %-10s %-10s %-12s %-8s %-30s\n",
                              "ID", "Patient ID", "Doctor ID", "Date", "Time", "Reason");
            System.out.println("-------------------------------------------------------------------------------");
            for (Appointment appt : appointments) {
                
                System.out.printf("%-5d %-10d %-10d %-12s %-8s %-30s\n",
                                  appt.getAppointmentId(),
                                  appt.getPatientId(),
                                  appt.getDoctorId(),
                                  appt.getAppointmentDate(),
                                  appt.getAppointmentTime(),
                                  appt.getReason());
            }
        }
    }

    private static void updateAppointment() {
        System.out.println("\n--- Update Appointment ---");
        System.out.print("Enter Appointment ID to update: ");
        int id = getUserChoice();
        if (id == -1) return;

        Appointment existingAppointment = appointmentDAO.getAppointmentById(id);
        if (existingAppointment == null) {
            System.out.println("Appointment with ID " + id + " not found.");
            return;
        }

        System.out.println("Current Appointment Details: " + existingAppointment);

        System.out.print("Enter new Patient ID (or press Enter to keep current: " + existingAppointment.getPatientId() + "): ");
        String patientIdStr = scanner.nextLine();
        if (!patientIdStr.isEmpty()) {
            try {
                int newPatientId = Integer.parseInt(patientIdStr);
                if (patientDAO.getPatientById(newPatientId) != null) {
                    existingAppointment.setPatientId(newPatientId);
                } else {
                    System.out.println("Patient with ID " + newPatientId + " does not exist. Keeping current.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Patient ID format, keeping current.");
            }
        }

        System.out.print("Enter new Doctor ID (or press Enter to keep current: " + existingAppointment.getDoctorId() + "): ");
        String doctorIdStr = scanner.nextLine();
        if (!doctorIdStr.isEmpty()) {
            try {
                int newDoctorId = Integer.parseInt(doctorIdStr);
                if (doctorDAO.getDoctorById(newDoctorId) != null) {
                    existingAppointment.setDoctorId(newDoctorId);
                } else {
                    System.out.println("Doctor with ID " + newDoctorId + " does not exist. Keeping current.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Doctor ID format, keeping current.");
            }
        }

        LocalDate apptDate = existingAppointment.getAppointmentDate();
        System.out.print("Enter new Appointment Date (YYYY-MM-DD, or press Enter to keep current: " + apptDate + "): ");
        String dateStr = scanner.nextLine();
        if (!dateStr.isEmpty()) {
            try {
                apptDate = LocalDate.parse(dateStr);
                existingAppointment.setAppointmentDate(apptDate);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format, keeping current.");
            }
        }

        LocalTime apptTime = existingAppointment.getAppointmentTime();
        System.out.print("Enter new Appointment Time (HH:MM, or press Enter to keep current: " + apptTime + "): ");
        String timeStr = scanner.nextLine();
        if (!timeStr.isEmpty()) {
            try {
                apptTime = LocalTime.parse(timeStr);
                existingAppointment.setAppointmentTime(apptTime);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format, keeping current.");
            }
        }

        System.out.print("Enter new Reason (or press Enter to keep current: " + existingAppointment.getReason() + "): ");
        String reason = scanner.nextLine();
        if (!reason.isEmpty()) existingAppointment.setReason(reason);

        appointmentDAO.updateAppointment(existingAppointment);
    }

    private static void cancelAppointment() {
        System.out.println("\n--- Cancel Appointment ---");
        System.out.print("Enter Appointment ID to cancel: ");
        int id = getUserChoice();
        if (id == -1) return;
        appointmentDAO.deleteAppointment(id);
    }
}