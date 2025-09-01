//SH
package boundary;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Khoo Sheng Hao
 */

import java.util.Scanner;
import Entity.Treatment;
import control.TreatmentCtrl;
import ADT.*;

public class TreatmentBoundary {

    private static int treatmentID = 1000;
    private TreatmentCtrl control;
    private Scanner scanner;

    public TreatmentBoundary(TreatmentCtrl control) {
        this.control = control;
        scanner = new Scanner(System.in);
    }

    public void start() {
        int choice = -1;
        do {
            System.out.println("\n=============================================");
            System.out.println("===      MEDICAL TREATMENT MANAGEMENT     ===");
            System.out.println("=============================================");
            System.out.println("1. Add Treatment Type");
            System.out.println("2. Remove Treatment Type");
            System.out.println("3. Assign Treatment to Patient");
            System.out.println("4. Search for Treatment");
            System.out.println("5. View All Current Available Treatments");
            System.out.println("6. View All Current Assigned Treatments to Patient");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            
            try {
            choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
            System.out.println("\nInvalid input. Please enter a number only.");
            choice = -1; // reset choice to avoid accidental exit
            }

            switch (choice) {
                case 1:
                    addTreatmentUI();
                    break;
                case 2:
                    removeTreatmentUI();
                    break;
                case 3:
                    assignTreatmentUI();
                    break;
                case 4:
                    viewDiseaseUI();
                    break;
                case 5:
                    displayAllTreatmentSummary();
                    break;
                case 6:
                    displayAllAssignedTreatment();
                    break;
                case 0:
                    System.out.println("\nExiting module...");
                    break;
                case -1:
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private void addTreatmentUI() {
        System.out.println("\n===== Add New Disease Treatment =====");
        String disease;
        String symptoms;
        String severity;
        HashMap<String, Integer> medicines = new HashMap<>();
        int amount = 0;
        int duration = 0;
        String formatDuration = "";
        
        
        do{
        System.out.print("Enter Disease Name: ");
        disease = scanner.nextLine().trim();
        if(disease.isEmpty()){
            System.out.println("Disease Name cannot be empty. Please try again");
            }
        }
        while (disease.isEmpty());  
        disease = disease.intern();

        do{
        System.out.print("Enter Symptoms: ");
        symptoms = scanner.nextLine().trim();
        if(symptoms.isEmpty()){
            System.out.println("Symptoms Cannot be Empty. Please try again");
        }
        }
        while(symptoms.isEmpty());

        do{
        System.out.print("Enter Severity of the Disease (Low/Moderate/High): ");
        severity = scanner.nextLine().trim();
        if(severity.equalsIgnoreCase("Low") ||
            severity.equalsIgnoreCase("Moderate") ||
                severity.equalsIgnoreCase("High")){
            break;
        }
        else{
            System.out.println("Invalid Input, Severity must be Low, Moderate or High. Please try again");
        }
        }
        while(severity.isEmpty());


        System.out.println("Enter Medicines / Treatments (type '-' when finished):");
        while (true) {
        System.out.print("Medicine: ");
        String medicineValue = scanner.nextLine().trim();
        if (medicineValue.equalsIgnoreCase("-")) break;
        
        if(medicineValue.isEmpty()){
            System.out.println("Medicine cannot be empty. Please try again.");
            continue;
        }
        
        while(true){
        System.out.print("Amount of Medicine (Per Day): ");
        String amountInput = scanner.nextLine().trim();
            if (amountInput.isEmpty()) {
                System.out.println("Amount cannot be empty. Please try again.");
                continue;
                }   

            try {
                amount = Integer.parseInt(amountInput);

                if (amount <= 0) {
                    System.out.println("Amount must be greater than 0. Please try again.");
                 continue;
                }

                if (amount > 10) {
                    System.out.println("Amount cannot exceed 10. Please try again.");
                    continue;
                }
                 break;

            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid integer.");
                
            }
        }
            medicines.add(medicineValue,amount);
            
        
}

        while (true) {
        System.out.print("Enter Duration of Treatment (Days): ");
        String input = scanner.nextLine().trim();
        try {
            duration = Integer.parseInt(input);
            if (duration > 0) {
                formatDuration = String.valueOf(duration) + " days";
                break;
        }
            System.out.println("Duration must be greater than 0.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }

        
    }

        Treatment treatment = new Treatment("T" + treatmentID, disease, symptoms, severity, medicines, formatDuration);
        control.addTreatment(disease, treatment);
        treatmentID++;
        System.out.println("\n...");
        System.out.println("\nRecord Added\n");
    }

    private void removeTreatmentUI() {
        System.out.print("Enter treatment to remove: ");
        String treatment = scanner.nextLine().trim();
        if (control.removeTreatment(treatment)) {
            System.out.println("Treatment removed successfully!");
        } else {
            System.out.println("Treatment not found.");
        }
    }
    
    private void assignTreatmentUI(){
        String disease;
        String patientID;
        while(true){
        System.out.print("Enter Patient ID: ");
        patientID = scanner.nextLine().trim();
        if(patientID.isEmpty()){
            System.out.println("Patient ID cannot be empty");
            continue;
        }
        System.out.print("Enter Disease to be Assigned: ");
        disease = scanner.nextLine().trim();
        if(disease.isEmpty()){
            System.out.println("Disease Name cannot be empty");
            continue;
        }
        else
            break;
        }
        control.assignTreatmentToPatient(patientID, disease);
        System.out.println("Treatment Assigned Successfully");
    }

    private void viewDiseaseUI() {
        System.out.print("Enter Disease Name: ");
        String disease = scanner.nextLine().trim();
        disease = disease.intern();
        MyList<Treatment> diseaseType = control.getTreatmentDesc(disease);
        if (diseaseType == null || diseaseType.isEmpty()) {
            System.out.println("No Treatment Type found: " + disease);
        } else {
            System.out.println("Treatment for " + disease + ":");
            System.out.printf("%-14s %-20s %-30s %-12s %-30s %-18s",
                            "TreatmentID", "Disease Name", "Symptoms", "Severity", "Medication / Treatment", "Duration (Days)");
            System.out.println("\n====================================================================================================================================");
           for (int i = 0; i < diseaseType.size(); i++) {  // 0-based index
        Treatment t = diseaseType.get(i);
           if (t != null) {
               String meds = "";
                    for (int j = 0; j < t.getTreatmentProvided().size(); j++) {
                        meds += t.getTreatmentProvided().getValue(j);
                    if (j < t.getTreatmentProvided().size() - 1) meds += ", ";
                     }
        System.out.printf("%-14s %-20s %-30s %-12s %-30s %-18s\n",
                          t.getTreatmentID(), t.getDiseaseName(), t.getSymptoms(),
                          t.getSeverity(), meds, t.getdurationOfTreatment());
        System.out.println("\n");
    }
}
        }
    }
    
    public void displayAllTreatmentSummary() {
    MyList<Treatment> allTreatments = control.getAllTreatments();

    if (allTreatments.isEmpty()) {
        System.out.println("No treatments available.");
        return;
    }

    System.out.printf("%-14s %-20s %-30s %-12s %-30s %-20s %-18s\n",
            "TreatmentID","Disease", "Symptoms", "Severity",
            "Medication / Treatment","Amount (PerDay)", "Duration");
    System.out.println("======================================================================================================================================");

        for (int i = 0; i < allTreatments.size(); i++) {
            Treatment t = allTreatments.get(i);
            System.out.println(t);
        }
        
    }
    
    public void displayAllAssignedTreatment(){
        MyList<TreatmentCtrl.TreatmentInfo> allAssignedPatientInfo = control.getAllAssignedPatient();
        
        if(allAssignedPatientInfo.isEmpty()){
            System.out.println("No Patient Assigned");
            System.out.println("===================");
            return;
        }
        System.out.printf("%-14s %-20s %-30s %-20s %-18s", 
                "PatientID", "Disease", "Medication / Treatment", "Amount (PerDay)", "Duration");
        System.out.println("\n====================================================================================================================");

        for (int i = 0; i < allAssignedPatientInfo.size(); i++) {
            System.out.println(allAssignedPatientInfo.get(i));
}

    }
}





