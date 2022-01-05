package cl.bgmp.covidcontrol.gui;

import cl.bgmp.covidcontrol.CovidControlConfig;
import cl.bgmp.covidcontrol.database.PatientManager;
import cl.bgmp.covidcontrol.database.SQLiteConnector;
import cl.bgmp.covidcontrol.gui.tab.add.NewPatientTabPanel;
import cl.bgmp.covidcontrol.gui.tab.info.PatientDataTabPanel;
import cl.bgmp.covidcontrol.gui.tab.list.PatientsTabPanel;
import cl.bgmp.covidcontrol.model.Cesfam;
import cl.bgmp.covidcontrol.model.HealthEstablishment;
import cl.bgmp.covidcontrol.model.Patient;
import cl.bgmp.covidcontrol.model.PatientBasicInfo;
import cl.bgmp.covidcontrol.model.PatientMedicalInfo;
import cl.bgmp.covidcontrol.model.PatientState;
import cl.bgmp.covidcontrol.util.FileUtils;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.*;

public class App extends JFrame {
  private JTabbedPane tabs;
  private PatientsTabPanel patientsPanel;
  private NewPatientTabPanel newPatientPanel;
  private PatientDataTabPanel patientDataPanel;

  private JFileChooser importChooser;

  private List<Patient> patients = new ArrayList<>();
  private List<Cesfam> cesfams = new ArrayList<>();

  private SQLiteConnector connector;
  private PatientManager patientManager;

  private CovidControlConfig config = new CovidControlConfig();

  public App() {
    this.loadCesfamData();

    this.connector = new SQLiteConnector(new File("resources/database.db").getPath());
    this.patientManager = new PatientManager(this.connector);

    this.patients = patientManager.getAllPatients();
    this.cesfams = this.getCesfams();

    this.initUI();
  }

  public List<Patient> getPatients() {
    return patients;
  }

  public List<Cesfam> getCesfams() {
    return cesfams;
  }

  public PatientManager getPatientManager() {
    return patientManager;
  }

  public PatientsTabPanel getPatientsTabPanel() {
    return patientsPanel;
  }

  public NewPatientTabPanel getNewPatientPanel() {
    return newPatientPanel;
  }

  public PatientDataTabPanel getPatientDataPanel() {
    return patientDataPanel;
  }

  public JTabbedPane getTabs() {
    return tabs;
  }

  private void initUI() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            try {
              if (connector.getConnection() != null && !connector.getConnection().isClosed()) {
                connector.disconnect();
              }
            } catch (SQLException ex) {
              ex.printStackTrace();
            }

            super.windowClosing(e);
          }
        });

    this.setTitle(this.config.getTitle());
    this.setResizable(this.config.isResizable());
    this.setSize(this.config.getDimensions());
    this.setLocationRelativeTo(this.config.getInitialLocation());

    this.setIcon();
    this.initMenuBar();
    this.initTabs();

    this.setVisible(true);
  }

  private void setIcon() {
    File img = FileUtils.resourceAsFile(App.class, "icon/icon.png");
    if (img == null || !img.exists()) {
      ImageIcon icon = new ImageIcon("icon/icon.png");
      this.setIconImage(icon.getImage());
    } else {
      ImageIcon icon = new ImageIcon(img.getPath());
      this.setIconImage(icon.getImage());
    }
  }

  private void initMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    this.setJMenuBar(menuBar);

    /* File */
    JMenu menuFile = new JMenu("File");

    JMenuItem importMenuItem = new JMenuItem("Importar");
    importMenuItem.addActionListener(e -> this.importPatientsFromCSV());
    menuFile.add(importMenuItem);

    JMenuItem exportMenuItem = new JMenuItem("Exportar");
    exportMenuItem.addActionListener(e -> this.exportPatientsToCSVFile());
    menuFile.add(exportMenuItem);

    menuBar.add(menuFile);

    /* Help */
    JMenu menuHelp = new JMenu("Ayuda");

    JMenuItem reportBugMenuItem = new JMenuItem("Reportar un Bug");
    reportBugMenuItem.addActionListener(
        e -> {
          if (Desktop.isDesktopSupported()
              && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
              Desktop.getDesktop().browse(new URI("https://github.com/BGMP/CovidControl/issues"));
            } catch (IOException | URISyntaxException ignored) {
            }
          }
        });
    menuHelp.add(reportBugMenuItem);

    menuBar.add(menuHelp);
  }

  private void initTabs() {
    this.tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
    this.patientsPanel = new PatientsTabPanel(this);
    this.newPatientPanel = new NewPatientTabPanel(this);
    this.patientDataPanel = new PatientDataTabPanel(this);

    this.tabs.add("Pacientes", this.patientsPanel);
    this.tabs.add("Nuevo", this.newPatientPanel);
    this.tabs.add("Data", this.patientDataPanel);

    this.getContentPane().add(this.tabs);
  }

  private void loadCesfamData() {
    File cesfamDataFile = FileUtils.resourceAsFile(App.class, "cesfamData.csv");
    ArrayList<String[]> data = FileUtils.getCSVData(cesfamDataFile);
    if (data == null) {
      cesfamDataFile = new File("resources/cesfamData.csv");
      data = FileUtils.getCSVData(cesfamDataFile);
    }

    assert data != null;
    for (String[] row : data) {
      if (row == data.get(0)) continue;

      String cesfamName = row[3];
      String cesfamAddress = row[5];

      this.cesfams.add(new Cesfam(cesfamName, cesfamAddress));
    }
  }

  public Cesfam getCesfamByName(String name) {
    return this.getCesfams().stream()
        .filter(c -> c.getName().equals(name))
        .findFirst()
        .orElse(null);
  }

  public String[] getCesfamNames() {
    String[] names = new String[this.getCesfams().size()];

    int i = 0;
    for (Cesfam cesfam : this.getCesfams()) {
      names[i] = cesfam.getName();
      i++;
    }

    return names;
  }

  public File exportPatientsToCSVFile() {
    final String timeStamp =
        new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime());
    final String filePath = "exported/" + timeStamp + "_data_pacientes.csv";
    try {
      CSVWriter writer = new CSVWriter(new FileWriter(filePath));
      String[][] rows = new String[this.patients.size()][21];

      int i = 0;
      for (Patient patient : this.getPatients()) {
        rows[i][0] = patient.getPatientBasicInfo().getName();
        rows[i][1] = patient.getPatientBasicInfo().getRut();
        rows[i][2] = patient.getPatientBasicInfo().getAddress();
        rows[i][3] =
            this.patientManager.getDf().format(patient.getPatientBasicInfo().getBirthDate());
        rows[i][4] = patient.getPatientBasicInfo().getPhone();
        rows[i][5] = patient.getPatientBasicInfo().getEmail();
        rows[i][6] = patient.getPatientBasicInfo().getPrevision();
        rows[i][7] = patient.getPatientBasicInfo().getSex();
        rows[i][8] =
            this.patientManager.getDf().format(patient.getPatientMedicalInfo().getPcrDate());
        rows[i][9] = patient.getPatientMedicalInfo().getChronicDeceases();
        rows[i][10] = patient.getPatientMedicalInfo().getOtherDeceases();
        rows[i][11] = patient.getPatientMedicalInfo().getContactPhone();

        HealthEstablishment healthEstablishment =
            patient.getPatientMedicalInfo().getHealthEstablishment();
        if (healthEstablishment != null) {
          rows[i][12] = healthEstablishment.getName();
          rows[i][13] = healthEstablishment.getMedic();
          rows[i][14] = healthEstablishment.getProvidedMedicine();
          rows[i][15] = healthEstablishment.getProvidedMedicalProcedures();
        } else {
          rows[i][12] = "";
          rows[i][13] = "";
          rows[i][14] = "";
          rows[i][15] = "";
        }

        rows[i][16] =
            patient.getPatientMedicalInfo().getState() == PatientState.DEAD ? "true" : "false";
        rows[i][17] = patient.getPatientMedicalInfo().getCesfam().getName();
        rows[i][18] = patient.getPatientMedicalInfo().getCesfam().getAddress();
        rows[i][19] = patient.getPatientMedicalInfo().getCesfam().getDirectorName();
        rows[i][20] =
            patient.getPatientMedicalInfo().getState() == PatientState.ACTIVE ? "true" : "false";

        i++;
      }

      for (String[] row : rows) {
        writer.writeNext(row);
      }

      writer.flush();

      JOptionPane.showMessageDialog(
          new JFrame(),
          "La información de los pacientes se exportó con éxito.\n" + "Archivo: " + filePath,
          "Info",
          JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  public void importPatientsFromCSV() {
    this.importChooser = new JFileChooser("exported");
    int returnValue = importChooser.showOpenDialog(null);

    if (returnValue != JFileChooser.APPROVE_OPTION) return;

    File selectedFile = importChooser.getSelectedFile();
    try {
      CSVReader reader = new CSVReader(new FileReader(selectedFile.getAbsolutePath()));

      this.patients.clear();

      for (String[] row : reader.readAll()) {
        PatientBasicInfo basicInfo =
            new PatientBasicInfo(
                row[0],
                row[1],
                row[2],
                this.patientManager.getDf().parse(row[3]),
                row[4],
                row[5],
                row[6],
                row[7]);

        HealthEstablishment establishment = null;
        if (!row[12].equals("")) {
          establishment = new HealthEstablishment(row[12], row[13], row[14], row[15]);
        }

        Cesfam cesfam = new Cesfam(row[17], row[18], row[19]);

        PatientMedicalInfo medicalInfo =
            new PatientMedicalInfo(
                row[9],
                row[10],
                this.patientManager.getDf().parse(row[8]),
                row[11],
                Boolean.parseBoolean(row[16])
                    ? PatientState.DEAD
                    : (Boolean.parseBoolean(row[20])
                        ? PatientState.ACTIVE
                        : PatientState.RECOVERED),
                establishment,
                cesfam);

        Patient patient = new Patient(basicInfo, medicalInfo);
        this.patients.add(patient);
      }

      this.getPatientsTabPanel().updatePatientsTable(this.getPatients());
      this.patientManager.deleteAllPatients();
      for (Patient patient : patients) {
        this.patientManager.registerNewPatient(patient);
      }
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
  }
}
