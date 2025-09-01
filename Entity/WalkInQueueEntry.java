//Author: Koo Jing Yik

package Entity;

import java.io.Serializable;

public class WalkInQueueEntry implements Serializable {
    private Patient patient;          // Reference to patient
    private String purposeOfVisit;
    private String doctorName;
    private String roomNumber;

    public WalkInQueueEntry(Patient patient, String purposeOfVisit, String doctorName, String roomNumber) {
        this.patient = patient;
        this.purposeOfVisit = purposeOfVisit;
        this.doctorName = doctorName;
        this.roomNumber = roomNumber;
    }

    // Getters and setters
    public Patient getPatient() {
        return patient;
    }

    public String getPurposeOfVisit() {
        return purposeOfVisit;
    }

    public void setPurposeOfVisit(String purposeOfVisit) {
        this.purposeOfVisit = purposeOfVisit;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}

