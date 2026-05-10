import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doctor extends User{
    private String name;
    private String phoneNumber;
    private String specialization;
    private String department;
    private final List<Patient> assignedPatients;
    private List<Appointment> appointments;

    public Doctor(String id, String username, String password,String role) {
        super(id,username,password, role);
        this.assignedPatients=new ArrayList<>();
        this.appointments=new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSpecialization() {
        return specialization;
    }
    
    public String getDepartment() {
        return department;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    @Override
    public void displayProfile() {
        System.out.println("===Doctor Profile===");
        System.out.println("ID: "+getId()+"| Name: Dr."+name);
        System.out.println("Specialization: "+specialization+"| Department: "+department);
        System.out.println("Phone Number: "+phoneNumber);
        System.out.println("=====================");
    }

    public void assign_patient(Patient patient) {
        this.assignedPatients.add(patient);
    }

    public void viewAssignedPatients() {
        if (assignedPatients.isEmpty()) {
            System.out.println("No patients assigned to you yet.");
            return;
        }
        System.out.println("---Assigned Patients---");
        for (Patient p:assignedPatients) {
            System.out.println("- Patient ID:"+p.getId()+"|Name: "+p.getName());
        }
    }

    public void viewAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("You have no appointments.txt scheduled");
            return;
        }
        System.out.println("---Your Appointments---");
        for(Appointment app:appointments) {
            System.out.println("Appointment ID: " + app.getAppointmentID() +
                    " | Date: " + app.getDate() +
                    " | Time: " + app.getTime() +
                    " | Status: " + app.getStatus());
        }
    }

    public void updateAppointmentStatus(String appointmentId, String newStatus) {
        for (Appointment app : appointments) {
            if (app.getAppointmentID().equals(appointmentId)) {
                if (app.getStatus().equalsIgnoreCase("Cancelled")
                        && newStatus.equalsIgnoreCase("Completed")) {
                    System.out.println("Error: A cancelled appointment cannot be marked as completed.");
                    return;
                }
                app.setStatus(newStatus);
                System.out.println("Appointment status successfully updated to: " + newStatus);
                HospitalSystem.saveAll();
                return;
            }
        }
        System.out.println("Error: Appointment not found in your schedule.");
        
    }

    @Override
    public String toFileFormat() {
        return id + "," + name + "," + specialization + "," + department + "," + phoneNumber;
    }

    public void display() {
        boolean run = true;

        while (run) {
            System.out.println("\n--- Doctor Menu ---");
            System.out.println("1. View My Profile");
            System.out.println("2. View Assigned Patients");
            System.out.println("3. View My Appointments");
            System.out.println("4. Update Appointment Status");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            String input = Main.sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1 -> displayProfile();
                case 2 -> viewAssignedPatients();
                case 3 -> viewAppointments();
                case 4 -> {
                    System.out.print("Enter Appointment ID: ");
                    String id = Main.sc.nextLine();
                    System.out.print("Enter Appointment Status: ");
                    String status = Main.sc.nextLine();
                    updateAppointmentStatus(id,status);
                }
                case 5 -> {
                    System.out.println("Logging out from Doctor account...");
                    run = false;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
