import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("Hospital Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel(new GridBagLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        contentPane.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPane.add(passwordField, gbc);

        JLabel roleLabel = new JLabel("Role:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(roleLabel, gbc);

        roleComboBox = new JComboBox<>(new String[]{"ADMIN", "DOCTOR", "PATIENT"});
        gbc.gridx = 1;
        gbc.gridy = 2;
        contentPane.add(roleComboBox, gbc);

        loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPane.add(loginButton, gbc);

        loginButton.addActionListener(new LoginActionListener());

        setContentPane(contentPane);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = (String) roleComboBox.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Please enter both username and password.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            User loggedInUser = HospitalSystem.login(username, password, role);
            
            if (loggedInUser != null) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Login successful! Welcome " + loggedInUser.getRole() + ".",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                
                dispose();
                if (loggedInUser instanceof Admin) {
                    new AdminDashboardFrame((Admin) loggedInUser).setVisible(true);
                } else if (loggedInUser instanceof Doctor) {
                    new DoctorDashboardFrame((Doctor) loggedInUser).setVisible(true);
                } else if (loggedInUser instanceof Patient) {
                    new PatientDashboardFrame((Patient) loggedInUser).setVisible(true);
                }
                
            } else {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Invalid username, password, or role. Please try again.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                HospitalSystem.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "Unable to load application data.\n" + ex.getMessage(),
                        "Startup Error",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }

            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
