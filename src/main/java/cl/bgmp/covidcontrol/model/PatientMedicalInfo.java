package cl.bgmp.covidcontrol.model;

import java.util.Date;

public class PatientMedicalInfo {
  private String chronicDeceases;
  private String otherDeceases;
  private Date pcrDate;
  private String contactPhone;
  private PatientState state;
  private HealthEstablishment healthEstablishment = null;
  private Cesfam cesfam;

  public PatientMedicalInfo(
      String chronicDeceases,
      String otherDeceases,
      Date pcrDate,
      String contactPhone,
      PatientState state,
      HealthEstablishment healthEstablishment,
      Cesfam cesfam) {
    this.chronicDeceases = chronicDeceases;
    this.otherDeceases = otherDeceases;
    this.pcrDate = pcrDate;
    this.contactPhone = contactPhone;
    this.state = state;
    this.healthEstablishment = healthEstablishment;
    this.cesfam = cesfam;
  }

  public String getChronicDeceases() {
    return chronicDeceases;
  }

  public String getOtherDeceases() {
    return otherDeceases;
  }

  public Date getPcrDate() {
    return pcrDate;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public PatientState getState() {
    return state;
  }

  public HealthEstablishment getHealthEstablishment() {
    return healthEstablishment;
  }

  public Cesfam getCesfam() {
    return cesfam;
  }
}
