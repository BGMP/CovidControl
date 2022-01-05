package cl.bgmp.covidcontrol.model;

public class Cesfam {
  private String name;
  private String address;
  private String directorName = null;

  public Cesfam(String name, String address, String directorName) {
    this.name = name;
    this.address = address;
    this.directorName = directorName;
  }

  public Cesfam(String name, String address) {
    this.name = name;
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getDirectorName() {
    return directorName;
  }

  public void setDirectorName(String directorName) {
    this.directorName = directorName;
  }
}
