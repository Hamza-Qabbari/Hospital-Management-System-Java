import java.util.ArrayList;

public class Patient extends User {
    private String name;
    private String phoneNumber;
    private int age;
    private String gender;
    private Doctor doctor;
    private ArrayList<Appointment> appointments;

    public Patient(String id, String username, String password, String role) {
        super(id,username,password, role);
        this.doctor=null;
        this.appointments=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void addAppointment (Appointment appointment) {
        this.appointments.add(appointment);
    }

    @Override
    public void displayProfile() {
        System.out.println("=== Patient Profile ===");
        System.out.println("ID: " + getId() + " | Name: " + getName());
        System.out.println("Age: " + age + " | Gender: " + gender);
        System.out.println("Phone Number: " + getPhoneNumber());
        System.out.println("=======================");
    }

    public void viewAssignedDoctor() {
        if (doctor == null) {
            System.out.println("You don't have an assigned doctor yet.");
        } else {
            System.out.println("Your Doctor: Dr. " + doctor.getName() + " (" + doctor.getSpecialization() + ")");
        }
    }

    public void viewAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("You have no booked appointments.txt.");
            return;
        }
        System.out.println("--- Your Appointments ---");
        for (Appointment app : appointments) {
            System.out.println("Appointment ID: " + app.getAppointmentID() +
                    " | Date: " + app.getDate() +
                    " | Time: " + app.getTime() +
                    " | Status: " + app.getStatus());
        }
    }

    public void bookAppointment(String id,String date,String time) {
        if (doctor == null) {
            System.out.println("Error: You cannot book an appointment without being assigned to a doctor first.");
            return;
        }

        for (Appointment appointment : doctor.getAppointments()) {
            if (appointment.getDate().equals(date)&&appointment.getTime().equals(time)) {
                System.out.println("A doctor cannot have two appointments at the same date and time");
                return;
            }
        }

        Appointment appointment = new Appointment(id,this.id,doctor.getId(),date,time,"confirmed");

        appointments.add(appointment);
        doctor.addAppointment(appointment);
        HospitalSystem.appointments.put(id,appointment);

        System.out.println("Appointment booked successfully.");
        HospitalSystem.saveAll();
    }

    public void cancelAppointment(String appointmentId) {
        for (Appointment app : appointments) {
            if (app.getAppointmentID().equals(appointmentId)) {
                app.setStatus("Cancelled");
                System.out.println("Appointment cancelled successfully.");
                HospitalSystem.saveAll();
                return;
            }
        }
        System.out.println("Error: Appointment not found.");
        
    }

    @Override
    public String toFileFormat() {
        String docId = (doctor != null) ? doctor.getId() : "None";
        return id + "," + name + "," + age + "," + gender + "," + phoneNumber + "," + docId;
    }

    public void display() {
        boolean run = true;

        while (run) {
            System.out.println("\n--- Patient Menu ---");
            System.out.println("1. View My Profile");
            System.out.println("2. View Assigned Doctor");
            System.out.println("3. View My Appointments");
            System.out.println("4. Book Appointment");
            System.out.println("5. Cancel Appointment");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            String input = Main.sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1 -> displayProfile();
                case 2 -> viewAssignedDoctor();
                case 3 -> viewAppointments();
                case 4 -> {
                    System.out.print("Enter Appointment ID: ");
                    String id = Main.sc.nextLine();
                    System.out.print("Enter Date: ");
                    String date = Main.sc.nextLine();
                    System.out.print("Enter Time: ");
                    String time = Main.sc.nextLine();
                    bookAppointment(id,date,time);
                }
                case 5 -> {
                    System.out.print("Enter Appointment ID: ");
                    String id = Main.sc.nextLine();
                    cancelAppointment(id);
                }
                case 6 -> {
                    System.out.println("Logging out from Patient account...");
                    run = false;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
}