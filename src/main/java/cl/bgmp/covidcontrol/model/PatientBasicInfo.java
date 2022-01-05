package cl.bgmp.covidcontrol.model;

import java.util.Date;

public class PatientBasicInfo {
  private String name;
  private String rut;
  private String address;
  private Date birthDate;
  private String phone;
  private String email;
  private String prevision;
  private String sex;

  public PatientBasicInfo(
      String name,
      String rut,
      String address,
      Date birthDate,
      String phone,
      String email,
      String prevision,
      String sex) {
    this.name = name;
    this.rut = rut;
    this.address = address;
    this.birthDate = birthDate;
    this.phone = phone;
    this.email = email;
    this.prevision = prevision;
    this.sex = sex;
  }

  public String getName() {
    return name;
  }

  public String getRut() {
    return rut;
  }

  public String getAddress() {
    return address;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  public String getPrevision() {
    return prevision;
  }

  public String getSex() {
    return sex;
  }
}
