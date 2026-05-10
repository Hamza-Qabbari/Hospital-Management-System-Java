import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        HospitalSystem.start();

        boolean run = true;

        while (run) {
            System.out.println("___Main menu___");
            System.out.println("1. Login Admin");
            System.out.println("2. Login Doctor");
            System.out.println("3. Login Patient");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            String input = sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            if (choice == 4) {
                System.out.println("Exiting System... Goodbye!");
                run = false;
                continue;
            }

            if (choice < 1 || choice > 4) {
                System.out.println("Invalid choice, please try again.");
                continue;
            }

            System.out.print("Enter your username: ");
            String username = sc.nextLine();
            System.out.print("Enter your password: ");
            String password = sc.nextLine();

            switch (choice) {
                case 1 -> {
                    Admin admin = (Admin) HospitalSystem.login(username, password, "ADMIN");
                    if (admin == null) {
                        System.out.println("Invalid credentials");
                    } else {
                        admin.display();
                    }
                }
                case 2 -> {
                    Doctor doctor = (Doctor) HospitalSystem.login(username, password, "DOCTOR");
                    if (doctor == null) {
                        System.out.println("Invalid credentials");
                    } else {
                        doctor.display();
                    }
                }
                case 3 -> {
                    Patient patient = (Patient) HospitalSystem.login(username, password, "PATIENT");
                    if (patient == null) {
                        System.out.println("Invalid credentials");
                    } else {
                        patient.display();
                    }
                }
            }
        }
        sc.close();
    }
}
