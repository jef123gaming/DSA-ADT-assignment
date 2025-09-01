
//SH
package control;

/**
 *
 * @author Khoo Sheng Hao
 */
import ADT.*;
import Entity.*;
import java.time.LocalDate;
public class TreatmentCtrl {


    private HashMap<String, MyList<Treatment>> patientTreatments;
    private HashMap<String, MyList<TreatmentInfo>> assignedTreatment;
    private PharmacyManager pharmacyManager;
    
    public TreatmentCtrl(PharmacyManager pharmacyManager) {
        patientTreatments = new HashMap<>();
        assignedTreatment = new HashMap<>();
        this.pharmacyManager = pharmacyManager;
    }
    

    public class TreatmentInfo {
    private String patientID;
    private String diseaseName;
    private HashMap<String, Integer> medicines;
    private String duration;

    public TreatmentInfo(String patientID, Treatment t) {
        this.patientID = patientID;
        this.diseaseName = t.getDiseaseName();
        this.medicines = t.getTreatmentProvided();
        this.duration = t.getdurationOfTreatment();
    }

    public String getPatientID(){
        return patientID;
    }
    public String getDiseaseName() { 
        return diseaseName;
    } 
    
    public HashMap<String, Integer> getMedicines() { 
        return medicines; 
    }
    
    public String getDuration() { 
        return duration; 
    }
    
    public String toString(){
        StringBuilder meds = new StringBuilder();
        StringBuilder amounts = new StringBuilder();

        for (int i = 0; i < medicines.size(); i++) {
             String med = medicines.getKey(i);       
             int amt = medicines.getValue(i);        

             meds.append(med);
             amounts.append(amt);

            if (i < medicines.size() - 1) {
            meds.append(", ");
             amounts.append(", ");
            }
        }

        return String.format("%-14s %-20s %-30s %-20s %-18s",
                    patientID, diseaseName,meds.toString(), amounts.toString(), duration);

    }
    }
    
    public void addTreatment(String diseaseName, Treatment treatmentDesc) {
    MyList<Treatment> treatments;
    if (patientTreatments.containsKey(diseaseName)) {
        treatments = patientTreatments.get(diseaseName);
    } else {
        treatments = new MyList<>();
    }

    treatments.add(treatmentDesc);
    patientTreatments.add(diseaseName, treatments);
}

    // Add treatment information for a disease
  

    // Remove the treatment information
    public boolean removeTreatment(String diseaseName) {
    if (!patientTreatments.containsKey(diseaseName)) {
        return false;  // no treatment with this disease name
    }
    patientTreatments.remove(diseaseName);  // removes the entire entry
    return true;
}

    
    public void assignTreatmentToPatient(String patientID, String diseaseName){
        MyList<TreatmentInfo> treatmentInfo = new MyList<>();
        MyList<Treatment> treatments = patientTreatments.get(diseaseName);
       if (treatments == null || treatments.isEmpty())
           return;
       
       for(int i = 0; i < treatments.size(); i++){
           Treatment t = treatments.get(i);
           if(t != null){
               TreatmentInfo patientDiseaseInfo = new TreatmentInfo(patientID, t);
               treatmentInfo.add(patientDiseaseInfo);
               
               MyList<DispenseOrder> orders = buildDispenseOrders(patientDiseaseInfo, pharmacyManager);
               for(int j = 0; j < orders.size(); j++){
                   DispenseOrder order = orders.get(j);
                   pharmacyManager.addDispenseOrder(order);
               }
           }
       }
       if (assignedTreatment.containsKey(patientID)) {
        MyList<TreatmentInfo> existing = assignedTreatment.get(patientID);
        for (int i = 0; i < treatmentInfo.size(); i++) {
            existing.add(treatmentInfo.get(i));
            }
       }
       else
        assignedTreatment.add(patientID, treatmentInfo);
       
       
    }


    public MyList<Treatment> getTreatmentDesc(String diseaseName) { 
    if (!patientTreatments.containsKey(diseaseName)) {
        return null; // no treatment found
    }

    MyList<Treatment> result = patientTreatments.get(diseaseName);

    // Optional debug
    System.out.println("Retrieved " + (result == null ? 0 : result.size()) + " treatments for " + diseaseName);

    return result; 
}






    


    public MyList<Treatment> getAllTreatments() {
        MyList<Treatment> allTreatments = new MyList<>();

        ListInterface<MyList<Treatment>> valueLists = 
            (ListInterface<MyList<Treatment>>) (ListInterface<?>) patientTreatments.convertToMyList();

        for (int i = 0; i < valueLists.size(); i++) {
            MyList<Treatment> treatments = valueLists.get(i);
            if (treatments == null || treatments.isEmpty()) continue;

            for (int j = 0; j < treatments.size(); j++) {
                Treatment t = treatments.get(j);
                if (t != null) {
                allTreatments.add(t);
            }
        }
    }

    return allTreatments;
}
    
   public MyList<TreatmentInfo> getAllAssignedPatient() {
    MyList<TreatmentInfo> allAssignedInfo = new MyList<>();

    ListInterface<MyList<TreatmentInfo>> valueLists =
        (ListInterface<MyList<TreatmentInfo>>) (ListInterface<?>) assignedTreatment.convertToMyList();

    // iterate through each patient's assigned treatments
    for (int i = 0; i < valueLists.size(); i++) {
        MyList<TreatmentInfo> patientList = valueLists.get(i);
        for (int j = 0; j < patientList.size(); j++) {
            allAssignedInfo.add(patientList.get(j));
        }
    }

    return allAssignedInfo;
}
   

    public MyList<DispenseOrder> buildDispenseOrders(TreatmentInfo info, PharmacyManager pharmacyManager) {
        MyList<DispenseOrder> orders = new MyList<>();

    int durationDays = Integer.parseInt(info.getDuration().split(" ")[0]);

    for (int i = 0; i < info.getMedicines().size(); i++) {
        String medName = info.getMedicines().getKey(i);
        
        int amount = info.getMedicines().getValue(i);
        int quantity = amount * durationDays;

        Medicine med = pharmacyManager.findMedicineByName(medName);
        DispenseOrder order = new DispenseOrder(info.getPatientID(),med.getMedID(), quantity,LocalDate.now().toString());

        orders.add(order);
    }

    return orders;
}

    public boolean hasTreatment(String diseaseName) {
        return patientTreatments.containsKey(diseaseName);
    }

    public int getTotalTreatments() {
        return patientTreatments.size();
    }
}

