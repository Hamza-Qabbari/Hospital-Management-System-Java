public class Appointment {
    private String AppointmentID;
    private String PatientID;
    private String DoctorID;
    private String Date;
    private String Time;
    private String Status;

    public Appointment(String AppointmentID,String PatientID,String DoctorID,String Date,String Time,String Status){
        if(Date==null) {
            throw new IllegalArgumentException("Appointment date cannot be empty");
        }
        if(Time==null) {
            throw new IllegalArgumentException("Appointment time cannot be empty");
        }
        this.AppointmentID = AppointmentID;
        this.PatientID = PatientID;
        this.DoctorID = DoctorID;
        this.Date = Date;
        this.Time = Time;
        this.Status = Status;
    }

    public String getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        AppointmentID = appointmentID;
    }

    public String getPatientID() {
        return PatientID;
    }

    public void setPatientID(String patientID) {
        PatientID = patientID;
    }

    public String getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(String doctorID) {
        DoctorID = doctorID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String toFileFormat() {
        return AppointmentID+","+PatientID+","+DoctorID+","+Date+","+Time+","+Status;
    }
}
