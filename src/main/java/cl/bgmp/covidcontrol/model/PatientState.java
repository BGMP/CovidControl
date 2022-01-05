package cl.bgmp.covidcontrol.model;

public enum PatientState {
  ACTIVE("Activo"),
  RECOVERED("Recuperado"),
  DEAD("Fallecido");

  private String string;

  PatientState(String string) {
    this.string = string;
  }

  public String getString() {
    return string;
  }

  public static PatientState fromString(String string) {
    for (PatientState state : values()) {
      if (state.getString().equals(string)) return state;
    }

    return null;
  }
}
