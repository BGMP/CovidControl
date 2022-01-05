package cl.bgmp.covidcontrol.model;

public class Patient {
  private PatientBasicInfo patientBasicInfo;
  private PatientMedicalInfo patientMedicalInfo;

  public Patient(PatientBasicInfo patientBasicInfo, PatientMedicalInfo patientMedicalInfo) {
    this.patientBasicInfo = patientBasicInfo;
    this.patientMedicalInfo = patientMedicalInfo;
  }

  public PatientBasicInfo getPatientBasicInfo() {
    return patientBasicInfo;
  }

  public PatientMedicalInfo getPatientMedicalInfo() {
    return patientMedicalInfo;
  }

  public void setPatientBasicInfo(PatientBasicInfo patientBasicInfo) {
    this.patientBasicInfo = patientBasicInfo;
  }

  public void setPatientMedicalInfo(PatientMedicalInfo patientMedicalInfo) {
    this.patientMedicalInfo = patientMedicalInfo;
  }
}
