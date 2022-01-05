package cl.bgmp.covidcontrol.common;

public enum StringConstant {
  FILE("File"),
  IMPORT_PATIENT_DATA("Import Patient Data"),
  EXPORT_PATIENT_DATA("Export Patient Data"),
  REPORT_A_BUG("Report a Bug"),
  HELP("Help"),

  PATIENTS("Pacientes"),
  NEW_PATIENT("Nuevo"),
  PATIENT_DATA("Datos");

  private String string;

  StringConstant(String string) {
    this.string = string;
  }

  public String get() {
    return string;
  }
}
