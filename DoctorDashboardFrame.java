import javax.swing.*;
import java.awt.*;

public class DoctorDashboardFrame extends JFrame {
    private final Doctor doctor;
    private JTextArea displayArea;

    public DoctorDashboardFrame(Doctor doctor) {
        this.doctor = doctor;
        setTitle("Doctor Dashboard - Dr. " + doctor.getName());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

       
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnProfile = new JButton("View Profile");
        JButton btnPatients = new JButton("View Patients");
        JButton btnAppointments = new JButton("View Appointments");
        JButton btnUpdateStatus = new JButton("Update Appt Status");
        JButton btnLogout = new JButton("Logout");

        buttonPanel.add(btnProfile);
        buttonPanel.add(btnPatients);
        buttonPanel.add(btnAppointments);
        buttonPanel.add(btnUpdateStatus);
        buttonPanel.add(btnLogout);

        add(buttonPanel, BorderLayout.EAST);

      
        btnProfile.addActionListener(e -> {
            displayArea.setText("=== Doctor Profile ===\n");
            displayArea.append("ID: " + doctor.getId() + "\nName: Dr. " + doctor.getName() + "\n");
            displayArea.append("Specialization: " + doctor.getSpecialization() + "\nDepartment: " + doctor.getDepartment() + "\n");
            displayArea.append("Phone: " + doctor.getPhoneNumber() + "\n");
        });

        btnPatients.addActionListener(e -> {
            displayArea.setText("=== Assigned Patients ===\n");
            doctor.viewAssignedPatients(); 
            if (doctor.toFileFormat().isEmpty()) { 
                displayArea.append("Check console for patient details (Needs backend getter adjustment for full GUI).\n");
                JOptionPane.showMessageDialog(this, "Please check the console for assigned patients.");
            }
        });

        btnAppointments.addActionListener(e -> {
            displayArea.setText("=== Your Appointments ===\n");
            if (doctor.getAppointments().isEmpty()) {
                displayArea.append("No appointments scheduled.\n");
            } else {
                for (Appointment app : doctor.getAppointments()) {
                    displayArea.append("ID: " + app.getAppointmentID() + " | Date: " + app.getDate() + 
                            " | Time: " + app.getTime() + " | Status: " + app.getStatus() + "\n");
                }
            }
        });

        btnUpdateStatus.addActionListener(e -> {
            String apptId = JOptionPane.showInputDialog(this, "Enter Appointment ID:");
            if (apptId != null && !apptId.trim().isEmpty()) {
                String newStatus = JOptionPane.showInputDialog(this, "Enter New Status (e.g., Completed, Cancelled):");
                if (newStatus != null && !newStatus.trim().isEmpty()) {
                    doctor.updateAppointmentStatus(apptId, newStatus);
                    displayArea.setText("Appointment status update triggered.\nCheck console for errors or success.");
                }
            }
        });

        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }
}