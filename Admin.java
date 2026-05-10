import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User{
    public Admin(String id,String userName,String password,String role) {
        super(id,userName,password,role);
    }

    @Override
    public void displayProfile() {}

    private void printMenu(){
        System.out.println("___Admin menu___");
        System.out.println("1. Add Doctor");
        System.out.println("2. Register Patient");
        System.out.println("3. Assign Patient to Doctor");
        System.out.println("4. Create Appointment");
        System.out.println("5. View All Doctors");
        System.out.println("6. View All Patients");
        System.out.println("7. View All Appointments");
        System.out.println("8. Search Patient by ID");
        System.out.println("9. Search Doctor by ID");
        System.out.println("10. Generate Reports");
        System.out.println("11. Save Data");
        System.out.println("12. Logout");
    }

    private int readInt() {
        System.out.print("Enter your choice: ");
        while (!Main.sc.hasNextInt()) {
            System.out.println("Invalid input!");
            Main.sc.next();
        }
        int val = Main.sc.nextInt();
        Main.sc.nextLine();
        return val;
    }

    public void display() {
        boolean run=true;

        while (run) {
            printMenu();

            int choice = readInt();

            switch (choice){
                case 1->addDoctor();
                case 2->registerPatient();
                case 3->assign_patient_doctor();
                case 4->createAppointment();
                case 5->viewAllDoctors();
                case 6->viewAllPatients();
                case 7->viewAllAppointments();
                case 8->searchPatient();
                case 9->searchDoctor();
                case 10->generateReports();
                case 11->HospitalSystem.saveAll();
                case 12->run=false;
                default -> throw new IllegalArgumentException("Invalid choice");
            }
        }
    }

    private void addDoctor() {
        System.out.print("Enter Doctor (id,username,password,name,phoneNumber,department,specialization): ");
        String[] d = Main.sc.nextLine().split(",");
        if (d.length < 7) {
            System.out.println("Invalid input format! Please provide all fields.");
            return;
        }
        if (HospitalSystem.users.containsKey(d[0])) {
            System.out.println("ID already exists!");
            return;
        }
        Doctor doctor = new Doctor(d[0],d[1],d[2],"DOCTOR");
        doctor.setName(d[3]);
        doctor.setPhoneNumber(d[4]);
        doctor.setDepartment(d[5]);
        doctor.setSpecialization(d[6]);
        HospitalSystem.users.put(d[0],doctor);
        HospitalSystem.doctors.put(d[0],doctor);
        System.out.println("Doctor added successfully.");
        HospitalSystem.saveAll();
    }

    private void registerPatient() {
        System.out.print("Enter Patient (id,username,password,name,phoneNumber,age,gender): ");
        String[] d = Main.sc.nextLine().split(",");
        if (d.length < 7) {
            System.out.println("Invalid input format! Please provide all fields.");
            return;
        }
        if (HospitalSystem.users.containsKey(d[0])) {
            System.out.println("ID already exists!");
            return;
        }
        Patient patient = new Patient(d[0],d[1],d[2],"PATIENT");
        patient.setName(d[3]);
        patient.setPhoneNumber(d[4]);
        patient.setAge(Integer.parseInt(d[5]));
        patient.setGender(d[6]);
        HospitalSystem.users.put(d[0],patient);
        HospitalSystem.patients.put(d[0],patient);
        System.out.println("Patient registered successfully.");
        HospitalSystem.saveAll();
    }

    private void createAppointment() {
        System.out.print("Enter Appointment (id,patient id,doctor id,date,time,status): ");
        String[] d = Main.sc.nextLine().split(",");
        if (d.length < 6) {
            System.out.println("Invalid input format! Please provide all fields.");
            return;
        }
        Patient patient = HospitalSystem.patients.get(d[1]);
        Doctor doctor = HospitalSystem.doctors.get(d[2]);

        if (patient == null || doctor == null) {
            System.out.println("Invalid patient or doctor ID!");
            return;
        }

        for (Appointment app : doctor.getAppointments()) {
            if (app.getDate().equals(d[3]) && app.getTime().equals(d[4]) && !app.getStatus().equalsIgnoreCase("Cancelled")) {
                System.out.println("Error: Doctor already has an appointment at this date and time.");
                return;
            }
        }

        Appointment appointment = new Appointment(d[0],d[1],d[2],d[3],d[4],d[5]);
        patient.addAppointment(appointment);
        doctor.addAppointment(appointment);
        HospitalSystem.appointments.put(d[0],appointment);

        System.out.println("Appointment created successfully.");
        HospitalSystem.saveAll();
    }

    private void viewAllPatients() {
        System.out.println("--- All Patients ---");
        if (HospitalSystem.patients.isEmpty()) {
            System.out.println("No patients registered yet.");
            return;
        }
        for (Patient patient : HospitalSystem.patients.values()) {
            System.out.println("ID: " + patient.getId() + " | Name: " + patient.getName() + " | Phone: " + patient.getPhoneNumber());
        }
    }

    private void assign_patient_doctor() {
        System.out.print("Enter Patient id: ");
        String p_id = Main.sc.nextLine();
        System.out.print("Enter Doctor id: ");
        String d_id = Main.sc.nextLine();
        Patient p = HospitalSystem.patients.get(p_id);
        Doctor d = HospitalSystem.doctors.get(d_id);
        if (p==null||d==null) {
            System.out.println("Invalid IDs!");
            return;
        }

        if (p.getDoctor()!=null) {
            System.out.println("Patient: "+p_id+" Already assigned");
            return;
        }

        p.setDoctor(d);
        d.assign_patient(p);

        System.out.println("Assigned successfully!");
        HospitalSystem.saveAll();
    }

    private void viewAllDoctors() {
        System.out.println("--- All Doctors ---");
        if (HospitalSystem.doctors.isEmpty()) {
            System.out.println("No doctors registered yet.");
            return;
        }
        for (Doctor doctor : HospitalSystem.doctors.values()) {
            System.out.println("ID: " + doctor.getId() + " | Name: Dr. " + doctor.getName() + " | Specialization: " + doctor.getSpecialization());
        }
    }

    private void searchDoctor() {
        System.out.print("Enter Doctor ID: ");
        String id = Main.sc.nextLine();

        if (HospitalSystem.doctors.containsKey(id)) {
            Doctor doctor = HospitalSystem.doctors.get(id);
            doctor.displayProfile();
        } else {
            System.out.println("Doctor not found");
        }
    }

    private void searchPatient() {
        System.out.print("Enter Patient ID: ");
        String id = Main.sc.nextLine();

        if (HospitalSystem.patients.containsKey(id)) {
            Patient patient = HospitalSystem.patients.get(id);
            patient.displayProfile();
        } else {
            System.out.println("Patient not found");
        }
    }

    private void viewAllAppointments() {
        System.out.println("--- All Appointments ---");
        if (HospitalSystem.appointments.isEmpty()) {
            System.out.println("No appointments booked yet.");
            return;
        }
        for (Appointment app : HospitalSystem.appointments.values()) {
            System.out.println("Appt ID: " + app.getAppointmentID() + " | Patient ID: " + app.getPatientID() + " | Doctor ID: " + app.getDoctorID() + " | Date: " + app.getDate() + " | Status: " + app.getStatus());
        }
    }

    private void generateReports() {
        System.out.println("===== Hospital Reports =====");
        System.out.println("Total Doctors: " + HospitalSystem.doctors.size());
        System.out.println("Total Patients: " + HospitalSystem.patients.size());

        int confirmed = 0;
        int completed = 0;
        int cancelled = 0;

        for (Appointment appointment : HospitalSystem.appointments.values()) {
            switch (appointment.getStatus().toLowerCase()) {
                case "confirmed" -> confirmed++;
                case "completed" -> completed++;
                case "cancelled" -> cancelled++;
            }
        }

        System.out.println("Confirmed Appointments: " + confirmed);
        System.out.println("Completed Appointments: " + completed);
        System.out.println("Cancelled Appointments: " + cancelled);

        System.out.println("--- Top 3 Doctors ---");
        List<Doctor> docList = new ArrayList<>(HospitalSystem.doctors.values());
        docList.sort((d1, d2) -> Integer.compare(d2.getAppointments().size(), d1.getAppointments().size()));

        int count = 0;
        for (Doctor doc : docList) {
            if (count >= 3) break;
            if (!doc.getAppointments().isEmpty()) {
                System.out.println((count + 1) + ". Dr. " + doc.getName() + " - Appointments: " + doc.getAppointments().size());
                count++;
            }
        }

        if (count == 0) {
            System.out.println("No appointments assigned to any doctor yet.");
        }
        System.out.println("============================");
    }
}
