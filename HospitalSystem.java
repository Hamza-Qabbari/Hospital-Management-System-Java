import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HospitalSystem {
    public static HashMap<String,User> users = new HashMap<>();
    public static HashMap<String,Doctor> doctors = new HashMap<>();
    public static HashMap<String,Patient> patients = new HashMap<>();
    public static HashMap<String,Appointment> appointments = new HashMap<>();

    public static void start() throws IOException {
        loadUsers();
        loadDoctors();
        loadPatients();
        loadAppointments();
    }

    public static User login(String username, String password, String role) {
        for (User u : users.values()) {
            if (u.role.equals(role)
                    && u.userName.equals(username)
                    && u.password.equals(password)) {
                return u;
            }
        }
        return null;
    }

    private static void loadUsers() {
        List<String> u = FileManger.readFile("users.txt");
        u.forEach(l->{
            String[] user = l.split(",");

            String id = user[0];
            String username = user[1];
            String password = user[2];
            String role = user[3];

            User user_object;

            switch (role) {
                case "ADMIN" -> user_object = new Admin(id,username,password,role);
                case "DOCTOR" -> user_object = new Doctor(id,username,password,role);
                case "PATIENT" -> user_object = new Patient(id,username,password,role);
                default -> throw new IllegalArgumentException("Invalid role");
            }

            users.put(id,user_object);
        });
    }

    private static void loadDoctors () {
        List<String> d_list = FileManger.readFile("doctors.txt");
        d_list.forEach(line->{
            String[] d = line.split(",");

            String id = d[0];

            Doctor doctor = (Doctor) users.get(id);

            if (doctor != null) {
                doctor.setName(d[1]);
                doctor.setSpecialization(d[2]);
                doctor.setDepartment(d[3]);
                doctor.setPhoneNumber(d[4]);
                doctors.put(id, doctor);
            }
        });
    }

    private static void loadPatients() {
        List<String> p_list = FileManger.readFile("patients.txt");
        p_list.forEach(line->{
            String[] p = line.split(",");

            if (p.length < 5) return; 

            String id = p[0];
            Patient patient = (Patient) users.get(id);

            if (patient != null) {
                patient.setName(p[1]);
                patient.setAge(Integer.parseInt(p[2]));
                patient.setGender(p[3]);
                patient.setPhoneNumber(p[4]);
                
                if (p.length > 5 && !p[5].equals("None")) {
                    Doctor d = doctors.get(p[5]);
                    if (d != null) {
                        patient.setDoctor(d);
                        d.assign_patient(patient);
                    }
                }

                patients.put(id, patient);
            }
        });
    }

    private static void loadAppointments() {
        List<String> a_list = FileManger.readFile("appointments.txt");
        a_list.forEach(line->{
            String[] aa = line.split(",");
            Appointment appointment = new Appointment(aa[0],aa[1],aa[2],aa[3],aa[4],aa[5]);
            HospitalSystem.patients.get(aa[1]).addAppointment(appointment);
            HospitalSystem.doctors.get(aa[2]).addAppointment(appointment);
            appointments.put(aa[0],appointment);
        });
    }

    public static void saveUsers() {

        List<String> data = new ArrayList<>();

        for (User user : users.values()) {
            data.add(user.getId()+","+user.getUserName()+","+user.getPassword()+","+user.getRole());
        }

        FileManger.writeFile("users.txt", data);
    }

    public static void saveDoctors() {

        List<String> data = new ArrayList<>();

        for (Doctor doctor : doctors.values()) {
            data.add(doctor.toFileFormat());
        }

        FileManger.writeFile("doctors.txt", data);
    }

    public static void savePatients() {

        List<String> data = new ArrayList<>();

        for (Patient patient : patients.values()) {
            data.add(patient.toFileFormat());
        }

        FileManger.writeFile("patients.txt", data);
    }

    public static void saveAppointments() {

        List<String> data = new ArrayList<>();

        for (Appointment appointment : appointments.values()) {
            data.add(appointment.toFileFormat());
        }

        FileManger.writeFile("appointments.txt", data);
    }

    public static void saveAll() {
        saveUsers();
        saveDoctors();
        savePatients();
        saveAppointments();
    }
}
