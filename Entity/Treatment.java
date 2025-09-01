
//SH
package Entity;

import ADT.*;
import java.io.Serializable;

public class Treatment implements Serializable{
    private String treatmentID;
    private String diseaseName;
    private String symptoms;
    private String severity;
    private String durationOfTreatment;  
    private HashMap<String, Integer> treatmentProvided;

public Treatment (String treatmentID, String diseaseName, String symptoms, String severity, HashMap<String, Integer> treatmentProvided, String durationOfTreatment) {
	this.treatmentID = treatmentID;
	this.diseaseName = diseaseName;
	this.symptoms = symptoms;
	this.severity = severity;
        this.treatmentProvided = treatmentProvided;
	this.durationOfTreatment = durationOfTreatment;


}

public Treatment() {
 	this("","","","",new HashMap<String, Integer>(),"");
}

public String getTreatmentID(){
	return treatmentID;
}

public String getDiseaseName(){
	return diseaseName;
}
public String getSymptoms(){
	return symptoms;
}

public String getSeverity(){
	return severity;
}

public String getdurationOfTreatment(){
	return durationOfTreatment;
}

public HashMap<String, Integer> getTreatmentProvided(){
	return treatmentProvided;
}

public void setTreatmentID(String treatmentID){
	this.treatmentID = treatmentID;
}

public void setDiseaseName(String diseaseName){
	this.diseaseName = diseaseName;
}
public void setSymptoms(String symptoms){
	this.symptoms = symptoms;
}

public void setSeverity(String severity){
	this.severity = severity;
}

public void setDurationOfTreatment(String durationOfTreatment){
	this.durationOfTreatment = durationOfTreatment;
}

public void setTreatmentProvided(HashMap<String, Integer> treatmentProvided){
	this.treatmentProvided = treatmentProvided;
}

public String toString() {
       StringBuilder meds = new StringBuilder();
       StringBuilder amounts = new StringBuilder();

        for (int i = 0; i < treatmentProvided.size(); i++) {
             String med = treatmentProvided.getKey(i);       
             int amt = treatmentProvided.getValue(i);        

             meds.append(med);
             amounts.append(amt);

            if (i < treatmentProvided.size() - 1) {
            meds.append(", ");
             amounts.append(", ");
    }
}

return String.format("%-14s %-20s %-30s %-12s %-30s %-20s %-18s",
                     treatmentID, diseaseName, symptoms, severity,
                     meds.toString(), amounts.toString(), durationOfTreatment);

    }
}
