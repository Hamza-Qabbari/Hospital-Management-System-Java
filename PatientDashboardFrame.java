import javax.swing.*;
import java.awt.*;

public class PatientDashboardFrame extends JFrame {
    private final Patient patient;
    private JTextArea displayArea;

    public PatientDashboardFrame(Patient patient) {
        this.patient = patient;
        setTitle("Patient Dashboard - " + patient.getName());
        setSize(600, 450);
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
        buttonPanel.setLayout(new GridLayout(6, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnProfile = new JButton("View Profile");
        JButton btnDoctor = new JButton("View Assigned Doctor");
        JButton btnAppts = new JButton("View Appointments");
        JButton btnBook = new JButton("Book Appointment");
        JButton btnCancel = new JButton("Cancel Appointment");
        JButton btnLogout = new JButton("Logout");

        buttonPanel.add(btnProfile);
        buttonPanel.add(btnDoctor);
        buttonPanel.add(btnAppts);
        buttonPanel.add(btnBook);
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnLogout);

        add(buttonPanel, BorderLayout.EAST);

        btnProfile.addActionListener(e -> {
            displayArea.setText("=== Patient Profile ===\n");
            displayArea.append("ID: " + patient.getId() + " | Name: " + patient.getName() + "\n");
            displayArea.append("Age: " + patient.toFileFormat().split(",")[2] + " | Gender: " + patient.toFileFormat().split(",")[3] + "\n");
            displayArea.append("Phone: " + patient.getPhoneNumber() + "\n");
        });

        btnDoctor.addActionListener(e -> {
            if (patient.getDoctor() == null) {
                displayArea.setText("You don't have an assigned doctor yet.");
            } else {
                displayArea.setText("=== Your Doctor ===\n");
                displayArea.append("Dr. " + patient.getDoctor().getName() + " (" + patient.getDoctor().getSpecialization() + ")");
            }
        });

        btnAppts.addActionListener(e -> {
            displayArea.setText("=== Your Appointments ===\n");
            patient.viewAppointments(); 
            displayArea.append("Check console for appointment details.\n(Note: Add a getter in backend to display fully in GUI)");
        });

        btnBook.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField dateField = new JTextField();
            JTextField timeField = new JTextField();
            Object[] message = {
                "Appointment ID:", idField,
                "Date (DD/MM/YYYY):", dateField,
                "Time (HH:MM):", timeField
            };
            int option = JOptionPane.showConfirmDialog(this, message, "Book Appointment", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                patient.bookAppointment(idField.getText(), dateField.getText(), timeField.getText());
                displayArea.setText("Booking process executed. Check console for confirmation.");
            }
        });

        btnCancel.addActionListener(e -> {
            String apptId = JOptionPane.showInputDialog(this, "Enter Appointment ID to cancel:");
            if (apptId != null && !apptId.trim().isEmpty()) {
                patient.cancelAppointment(apptId);
                displayArea.setText("Cancellation process executed. Check console for confirmation.");
            }
        });

        btnLogout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }
}