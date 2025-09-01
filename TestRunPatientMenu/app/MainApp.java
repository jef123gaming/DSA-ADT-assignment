package TestRunPatientMenu.app;

import boundary.PatientUI;
import control.PatientControl;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PatientUI patientUI = new PatientUI();
        PatientControl patientControl = new PatientControl(patientUI);

        boolean exit = false;

        while (!exit) {
            int choice = patientUI.getPatientManagementMenu();

            switch (choice) {
                case 1: // Register new patient
                    patientControl.registerNewPatient();
                    break;

                case 2: // Edit patient record
                    patientControl.handlePatientRecordMenu();
                    break;
                case 3: // Walk-in patient queue
                    patientControl.handleQueueMenu();
                    break;
                case 4: // Payment
                    patientControl.handlePaymentMenu();                  
                    break;
                case 0: // Quit
                    System.out.println("Exiting system... Goodbye!");
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
