package cl.bgmp.covidcontrol.database;

import cl.bgmp.covidcontrol.model.Cesfam;
import cl.bgmp.covidcontrol.model.HealthEstablishment;
import cl.bgmp.covidcontrol.model.Patient;
import cl.bgmp.covidcontrol.model.PatientBasicInfo;
import cl.bgmp.covidcontrol.model.PatientMedicalInfo;
import cl.bgmp.covidcontrol.model.PatientState;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/** Intermediaro entre los pacientes y la base de datos (DAO). */
public class PatientManager {
  private SQLiteConnector connector;
  private String datePattern = "MM/dd/yyyy HH:mm:ss";
  private DateFormat df = new SimpleDateFormat(datePattern);

  private String nameCol;
  private String rutCol;
  private String addressCol;
  private String birthDateCol;
  private String phoneCol;
  private String emailCol;
  private String previsionCol;
  private String sexCol;
  private String pcrDateCol;
  private String chronicDeceasesCol;
  private String otherDeceasesCol;
  private String contactPhoneCol;
  private String healthEstablishmentNameCol;
  private String healthEstablishmentMedicCol;
  private String healthEstablishmentMedicationCol;
  private String healthEstablishmentMedicalProceduresCol;
  private String deadCol;
  private String cesfamNameCol;
  private String cesfamAddressCol;
  private String cesfamDirectorCol;
  private String activeCol;

  public PatientManager(SQLiteConnector connector) {
    this.connector = connector;

    this.nameCol = "name";
    this.rutCol = "rut";
    this.addressCol = "address";
    this.birthDateCol = "birthDate";
    this.phoneCol = "phone";
    this.emailCol = "email";
    this.previsionCol = "prevision";
    this.sexCol = "sex";
    this.pcrDateCol = "pcrDate";
    this.chronicDeceasesCol = "chronicDeceases";
    this.otherDeceasesCol = "otherDeceases";
    this.contactPhoneCol = "contactPhone";
    this.healthEstablishmentNameCol = "healthEstablishmentName";
    this.healthEstablishmentMedicCol = "healthEstablishmentMedic";
    this.healthEstablishmentMedicationCol = "healthEstablishmentMedication";
    this.healthEstablishmentMedicalProceduresCol = "healthEstablishmentMedicalProcedures";
    this.deadCol = "dead";
    this.cesfamNameCol = "cesfamName";
    this.cesfamAddressCol = "cesfamAddress";
    this.cesfamDirectorCol = "cesfamDirector";
    this.activeCol = "active";
  }

  public DateFormat getDf() {
    return df;
  }

  public List<Patient> getAllPatients() {
    List<Patient> patients = new ArrayList<>();

    String query = "SELECT * FROM patients";
    try {
      Connection connection = this.connector.connect();
      Statement statement = connection.createStatement();
      ResultSet r = statement.executeQuery(query);

      while (r.next()) {
        PatientBasicInfo basicInfo =
            new PatientBasicInfo(
                r.getString(nameCol),
                r.getString(rutCol),
                r.getString(addressCol),
                df.parse(r.getString(birthDateCol)),
                r.getString(phoneCol),
                r.getString(emailCol),
                r.getString(previsionCol),
                r.getString(sexCol));

        PatientState state;
        if (r.getBoolean(deadCol)) state = PatientState.DEAD;
        else {
          if (r.getBoolean(activeCol)) {
            state = PatientState.ACTIVE;
          } else {
            state = PatientState.RECOVERED;
          }
        }

        HealthEstablishment healthEstablishment = null;
        String healthEstablishmentName = r.getString(healthEstablishmentNameCol);
        if (healthEstablishmentName != null) {
          healthEstablishment =
              new HealthEstablishment(
                  healthEstablishmentName,
                  r.getString(healthEstablishmentMedicCol),
                  r.getString(healthEstablishmentMedicationCol),
                  r.getString(healthEstablishmentMedicalProceduresCol));
        }

        PatientMedicalInfo medicalInfo =
            new PatientMedicalInfo(
                r.getString(chronicDeceasesCol),
                r.getString(otherDeceasesCol),
                df.parse(r.getString(pcrDateCol)),
                r.getString(contactPhoneCol),
                state,
                healthEstablishment,
                new Cesfam(
                    r.getString(cesfamNameCol),
                    r.getString(cesfamAddressCol),
                    r.getString(cesfamDirectorCol)));

        patients.add(new Patient(basicInfo, medicalInfo));
      }

      connector.disconnect();
    } catch (SQLException | ParseException e) {
      e.printStackTrace();
    }

    return patients;
  }

  public int registerNewPatient(Patient patient) {
    try {
      Connection connection = this.connector.connect();
      PreparedStatement statement =
          connection.prepareStatement(
              "INSERT INTO patients(name, rut, address, birthDate, phone, email, prevision, sex, pcrDate, chronicDeceases, otherDeceases, contactPhone, healthEstablishmentName, healthEstablishmentMedic, healthEstablishmentMedication, healthEstablishmentMedicalProcedures, dead, cesfamName, cesfamAddress, cesfamDirector, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

      PatientBasicInfo basicInfo = patient.getPatientBasicInfo();
      PatientMedicalInfo medicalInfo = patient.getPatientMedicalInfo();

      String healthEstablishmentName = null;
      String healthEstablishmentMedic = null;
      String healthEstablishmentMedication = null;
      String healthEstablishmentProcedures = null;
      if (patient.getPatientMedicalInfo().getHealthEstablishment() != null) {
        HealthEstablishment healthEstablishment =
            patient.getPatientMedicalInfo().getHealthEstablishment();
        healthEstablishmentName = healthEstablishment.getName();
        healthEstablishmentMedic = healthEstablishment.getMedic();
        healthEstablishmentMedication = healthEstablishment.getProvidedMedicine();
        healthEstablishmentProcedures = healthEstablishment.getProvidedMedicalProcedures();
      }

      statement.setString(1, basicInfo.getName());
      statement.setString(2, basicInfo.getRut());
      statement.setString(3, basicInfo.getAddress());
      statement.setString(4, df.format(basicInfo.getBirthDate()));
      statement.setString(5, basicInfo.getPhone());
      statement.setString(6, basicInfo.getEmail());
      statement.setString(7, basicInfo.getPrevision());
      statement.setString(8, basicInfo.getSex());
      statement.setString(9, df.format(medicalInfo.getPcrDate()));
      statement.setString(10, medicalInfo.getChronicDeceases());
      statement.setString(11, medicalInfo.getOtherDeceases());
      statement.setString(12, medicalInfo.getContactPhone());
      statement.setString(13, healthEstablishmentName);
      statement.setString(14, healthEstablishmentMedic);
      statement.setString(15, healthEstablishmentMedication);
      statement.setString(16, healthEstablishmentProcedures);
      statement.setBoolean(17, medicalInfo.getState() == PatientState.DEAD);
      statement.setString(18, medicalInfo.getCesfam().getName());
      statement.setString(19, medicalInfo.getCesfam().getAddress());
      statement.setString(20, medicalInfo.getCesfam().getDirectorName());
      statement.setBoolean(21, medicalInfo.getState() == PatientState.ACTIVE);

      int result = statement.executeUpdate();
      connector.disconnect();

      return result;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return 0;
  }

  public int updatePatient(Patient patient) {
    try {
      Connection connection = this.connector.connect();
      PreparedStatement statement =
          connection.prepareStatement(
              "UPDATE patients SET name = ?, address = ?, birthDate = ?, phone = ?, email = ?, prevision = ?, sex = ?, pcrDate = ?, chronicDeceases = ?, otherDeceases = ?, contactPhone = ?, healthEstablishmentName = ?, healthEstablishmentMedic = ?, healthEstablishmentMedication = ?, healthEstablishmentMedicalProcedures = ?, dead = ?, cesfamName = ?, cesfamAddress = ?, cesfamDirector = ?, active = ? WHERE rut = ?");

      PatientBasicInfo basicInfo = patient.getPatientBasicInfo();
      PatientMedicalInfo medicalInfo = patient.getPatientMedicalInfo();

      String healthEstablishmentName = null;
      String healthEstablishmentMedic = null;
      String healthEstablishmentMedication = null;
      String healthEstablishmentProcedures = null;
      if (patient.getPatientMedicalInfo().getHealthEstablishment() != null) {
        HealthEstablishment healthEstablishment =
            patient.getPatientMedicalInfo().getHealthEstablishment();
        healthEstablishmentName = healthEstablishment.getName();
        healthEstablishmentMedic = healthEstablishment.getMedic();
        healthEstablishmentMedication = healthEstablishment.getProvidedMedicine();
        healthEstablishmentProcedures = healthEstablishment.getProvidedMedicalProcedures();
      }

      statement.setString(1, basicInfo.getName());
      statement.setString(2, basicInfo.getAddress());
      statement.setString(3, df.format(basicInfo.getBirthDate()));
      statement.setString(4, basicInfo.getPhone());
      statement.setString(5, basicInfo.getEmail());
      statement.setString(6, basicInfo.getPrevision());
      statement.setString(7, basicInfo.getSex());
      statement.setString(8, df.format(medicalInfo.getPcrDate()));
      statement.setString(9, medicalInfo.getChronicDeceases());
      statement.setString(10, medicalInfo.getOtherDeceases());
      statement.setString(11, medicalInfo.getContactPhone());
      statement.setString(12, healthEstablishmentName);
      statement.setString(13, healthEstablishmentMedic);
      statement.setString(14, healthEstablishmentMedication);
      statement.setString(15, healthEstablishmentProcedures);
      statement.setBoolean(16, medicalInfo.getState() == PatientState.DEAD);
      statement.setString(17, medicalInfo.getCesfam().getName());
      statement.setString(18, medicalInfo.getCesfam().getAddress());
      statement.setString(19, medicalInfo.getCesfam().getDirectorName());
      statement.setBoolean(20, medicalInfo.getState() == PatientState.ACTIVE);
      statement.setString(21, basicInfo.getRut());

      int result = statement.executeUpdate();
      this.connector.disconnect();

      return result;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return 0;
  }

  public int deletePatient(Patient patient) {
    try {
      Connection connection = this.connector.connect();
      PreparedStatement statement =
          connection.prepareStatement("DELETE FROM patients WHERE rut = ?");

      statement.setString(1, patient.getPatientBasicInfo().getRut());

      int result = statement.executeUpdate();
      this.connector.disconnect();

      return result;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return 0;
  }

  public int deleteAllPatients() {
    try {
      Connection connection = this.connector.connect();
      PreparedStatement statement = connection.prepareStatement("DELETE FROM patients");

      int result = statement.executeUpdate();
      this.connector.disconnect();
      return result;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return 0;
  }
}
