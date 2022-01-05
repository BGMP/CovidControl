package cl.bgmp.covidcontrol.model;

public class HealthEstablishment {
  private String name;
  private String medic;
  private String givenMedicines;
  private String providedMedicalProcedures;

  public HealthEstablishment(
      String name, String medic, String givenMedicines, String providedMedicalProcedures) {
    this.name = name;
    this.medic = medic;
    this.givenMedicines = givenMedicines;
    this.providedMedicalProcedures = providedMedicalProcedures;
  }

  public String getName() {
    return name;
  }

  public String getMedic() {
    return medic;
  }

  public String getProvidedMedicine() {
    return givenMedicines;
  }

  public String getProvidedMedicalProcedures() {
    return providedMedicalProcedures;
  }
}
