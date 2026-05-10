import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboardFrame extends JFrame {
    private final Admin admin;
    private JTextArea displayArea;

    public AdminDashboardFrame(Admin admin) {
        this.admin = admin;
        setTitle("Admin Dashboard - Hospital Management System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnAddDoctor = new JButton("Add Doctor");
        JButton btnRegPatient = new JButton("Register Patient");
        JButton btnAssign = new JButton("Assign Patient to Doctor");
        JButton btnCreateAppt = new JButton("Create Appointment");
        JButton btnViewDoctors = new JButton("View All Doctors");
        JButton btnViewPatients = new JButton("View All Patients");
        JButton btnViewAppts = new JButton("View All Appointments");
        JButton btnReports = new JButton("Generate Reports");
        JButton btnSave = new JButton("Save Data");
        JButton btnLogout = new JButton("Logout");

        buttonPanel.add(btnAddDoctor);
        buttonPanel.add(btnRegPatient);
        buttonPanel.add(btnAssign);
        buttonPanel.add(btnCreateAppt);
        buttonPanel.add(btnViewDoctors);
        buttonPanel.add(btnViewPatients);
        buttonPanel.add(btnViewAppts);
        buttonPanel.add(btnReports);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnLogout);

        add(buttonPanel, BorderLayout.EAST);

       
        btnAddDoctor.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter Doctor info (id,username,password,name,phone,department,specialization):");
            if (input != null && !input.trim().isEmpty()) {
                String[] d = input.split(",");
                if (d.length < 7) {
                    JOptionPane.showMessageDialog(this, "Invalid format! Need 7 fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (HospitalSystem.users.containsKey(d[0])) {
                    JOptionPane.showMessageDialog(this, "ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Doctor doctor = new Doctor(d[0], d[1], d[2], "DOCTOR");
                doctor.setName(d[3]);
                doctor.setPhoneNumber(d[4]);
                doctor.setDepartment(d[5]);
                doctor.setSpecialization(d[6]);
                HospitalSystem.users.put(d[0], doctor);
                HospitalSystem.doctors.put(d[0], doctor);
                HospitalSystem.saveAll();
                displayArea.setText("Doctor added successfully.\nName: Dr. " + d[3]);
            }
        });

        btnRegPatient.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter Patient info (id,username,password,name,phone,age,gender):");
            if (input != null && !input.trim().isEmpty()) {
                String[] d = input.split(",");
                if (d.length < 7) {
                    JOptionPane.showMessageDialog(this, "Invalid format! Need 7 fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (HospitalSystem.users.containsKey(d[0])) {
                    JOptionPane.showMessageDialog(this, "ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Patient patient = new Patient(d[0], d[1], d[2], "PATIENT");
                patient.setName(d[3]);
                patient.setPhoneNumber(d[4]);
                try {
                    patient.setAge(Integer.parseInt(d[5]));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Age must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                patient.setGender(d[6]);
                HospitalSystem.users.put(d[0], patient);
                HospitalSystem.patients.put(d[0], patient);
                HospitalSystem.saveAll();
                displayArea.setText("Patient registered successfully.\nName: " + d[3]);
            }
        });

        btnAssign.addActionListener(e -> {
            JTextField pIdField = new JTextField();
            JTextField dIdField = new JTextField();
            Object[] message = {"Patient ID:", pIdField, "Doctor ID:", dIdField};
            int option = JOptionPane.showConfirmDialog(this, message, "Assign Patient to Doctor", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                Patient p = HospitalSystem.patients.get(pIdField.getText().trim());
                Doctor d = HospitalSystem.doctors.get(dIdField.getText().trim());
                if (p == null || d == null) {
                    JOptionPane.showMessageDialog(this, "Invalid IDs!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (p.getDoctor() != null) {
                    JOptionPane.showMessageDialog(this, "Patient already assigned to a doctor!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                p.setDoctor(d);
                d.assign_patient(p);
                HospitalSystem.saveAll();
                displayArea.setText("Patient " + p.getName() + " assigned to Dr. " + d.getName() + " successfully!");
            }
        });

        btnCreateAppt.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter Appointment (id,patient_id,doctor_id,date,time,status):");
            if (input != null && !input.trim().isEmpty()) {
                String[] d = input.split(",");
                if (d.length < 6) {
                    JOptionPane.showMessageDialog(this, "Invalid format! Need 6 fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Patient patient = HospitalSystem.patients.get(d[1]);
                Doctor doctor = HospitalSystem.doctors.get(d[2]);
                if (patient == null || doctor == null) {
                    JOptionPane.showMessageDialog(this, "Invalid patient or doctor ID!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Appointment appointment = new Appointment(d[0], d[1], d[2], d[3], d[4], d[5]);
                patient.addAppointment(appointment);
                doctor.addAppointment(appointment);
                HospitalSystem.appointments.put(d[0], appointment);
                HospitalSystem.saveAll();
                displayArea.setText("Appointment " + d[0] + " created successfully.");
            }
        });

        btnViewDoctors.addActionListener(e -> {
            displayArea.setText("--- All Doctors ---\n");
            if (HospitalSystem.doctors.isEmpty()) {
                displayArea.append("No doctors registered yet.\n");
                return;
            }
            for (Doctor doctor : HospitalSystem.doctors.values()) {
                displayArea.append("ID: " + doctor.getId() + " | Name: Dr. " + doctor.getName() + " | Spec: " + doctor.getSpecialization() + "\n");
            }
        });

        btnViewPatients.addActionListener(e -> {
            displayArea.setText("--- All Patients ---\n");
            if (HospitalSystem.patients.isEmpty()) {
                displayArea.append("No patients registered yet.\n");
                return;
            }
            for (Patient patient : HospitalSystem.patients.values()) {
                displayArea.append("ID: " + patient.getId() + " | Name: " + patient.getName() + " | Phone: " + patient.getPhoneNumber() + "\n");
            }
        });

        btnViewAppts.addActionListener(e -> {
            displayArea.setText("--- All Appointments ---\n");
            if (HospitalSystem.appointments.isEmpty()) {
                displayArea.append("No appointments booked yet.\n");
                return;
            }
            for (Appointment app : HospitalSystem.appointments.values()) {
                displayArea.append("Appt ID: " + app.getAppointmentID() + " | Patient: " + app.getPatientID() + " | Doctor: " + app.getDoctorID() + " | Date: " + app.getDate() + " | Status: " + app.getStatus() + "\n");
            }
        });

        btnReports.addActionListener(e -> {
            displayArea.setText("===== Hospital Reports =====\n");
            displayArea.append("Total Doctors: " + HospitalSystem.doctors.size() + "\n");
            displayArea.append("Total Patients: " + HospitalSystem.patients.size() + "\n\n");

            int confirmed = 0, completed = 0, cancelled = 0;
            for (Appointment appointment : HospitalSystem.appointments.values()) {
                switch (appointment.getStatus().toLowerCase()) {
                    case "confirmed" -> confirmed++;
                    case "completed" -> completed++;
                    case "cancelled" -> cancelled++;
                }
            }
            displayArea.append("Confirmed Appts: " + confirmed + "\n");
            displayArea.append("Completed Appts: " + completed + "\n");
            displayArea.append("Cancelled Appts: " + cancelled + "\n\n");

            displayArea.append("--- Top Doctors (by appointments) ---\n");
            List<Doctor> docList = new ArrayList<>(HospitalSystem.doctors.values());
            docList.sort((d1, d2) -> Integer.compare(d2.getAppointments().size(), d1.getAppointments().size()));
            
            int count = 0;
            for (Doctor doc : docList) {
                if (count >= 3) break;
                if (!doc.getAppointments().isEmpty()) {
                    displayArea.append((count + 1) + ". Dr. " + doc.getName() + " - Appointments: " + doc.getAppointments().size() + "\n");
                    count++;
                }
            }
        });

        btnSave.addActionListener(e -> {
            HospitalSystem.saveAll();
            JOptionPane.showMessageDialog(this, "All data saved to files successfully!", "Save Data", JOptionPane.INFORMATION_MESSAGE);
        });

        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }
}