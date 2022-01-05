package cl.bgmp.covidcontrol.gui.tab.list;

import cl.bgmp.covidcontrol.common.Data;
import cl.bgmp.covidcontrol.gui.App;
import cl.bgmp.covidcontrol.model.HealthEstablishment;
import cl.bgmp.covidcontrol.model.Patient;
import cl.bgmp.covidcontrol.model.PatientState;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PatientsTabPanel extends JPanel implements ActionListener {
  private static final String COMBOBOX_DISPLAY_VALUE =
      "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

  private App app;

  private JPanel spacerPanel;
  private JComboBox<String> filterComboBox;
  private JButton searchButton;
  private JTextField searchTextField;
  private JScrollPane patientsScrollPane;
  private JTable patientsTable;
  private JButton deleteButton;

  private int selectedTableRow = -1;

  public PatientsTabPanel(App app) {
    this.app = app;

    this.setLayout(new FlowLayout());
    this.initUI();
  }

  private void initUI() {
    this.initSpacerPanel();
    this.initFilterComboBoxUI();
    this.initSearchUI();
    this.add(this.spacerPanel);
    this.initPatientsTableUI();
    this.initDeleteButtonUI();
  }

  private void initSpacerPanel() {
    this.spacerPanel = new JPanel();
    this.spacerPanel.setLayout(new GridLayout(1, 1));
    this.spacerPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
  }

  private void initFilterComboBoxUI() {
    this.filterComboBox = new JComboBox<>(Data.FILTER_OPTIONS);
    this.filterComboBox.setPrototypeDisplayValue(COMBOBOX_DISPLAY_VALUE);
    this.filterComboBox.addActionListener(this);
    this.add(filterComboBox);
  }

  private void initSearchUI() {
    this.searchButton = new JButton("Buscar");
    this.searchButton.setPreferredSize(new Dimension(90, 27));
    this.searchButton.addActionListener(this);
    this.add(searchButton);

    this.searchTextField = new JTextField();
    this.searchTextField.setPreferredSize(new Dimension(745, 25));
    this.searchTextField.addActionListener(this);
    this.add(searchTextField);
  }

  private void initPatientsTableUI() {
    this.patientsScrollPane = new JScrollPane();

    this.patientsTable = new JTable();
    this.patientsTable.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            JTable table = (JTable) e.getSource();
            Point point = e.getPoint();
            int selectedRow = table.rowAtPoint(point);
            if (e.getClickCount() == 1 && table.getSelectedRow() != -1) {
              selectedTableRow = selectedRow;
            }
            if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
              String name = (String) patientsTable.getValueAt(selectedRow, 1);
              app.getPatients().stream()
                  .filter(p -> p.getPatientBasicInfo().getName().equals(name))
                  .findFirst()
                  .ifPresent(patient -> app.getPatientDataPanel().displayDataFor(patient));

              app.getTabs().setSelectedIndex(2);
            }
          }
        });
    this.patientsTable.setPreferredScrollableViewportSize(new Dimension(835, 350));
    this.patientsScrollPane.setViewportView(patientsTable);

    DefaultTableModel model = (DefaultTableModel) patientsTable.getModel();

    model.addColumn("N°");
    model.addColumn("Nombre");
    model.addColumn("RUT");
    model.addColumn("Sexo");
    model.addColumn("E-mail");
    model.addColumn("Fallecido");
    model.addColumn("Recuperado");
    model.addColumn("Hospitalizado");
    model.addColumn("Residencia S.");

    this.updatePatientsTable(this.app.getPatients());

    this.patientsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
    this.patientsTable.getColumnModel().getColumn(1).setPreferredWidth(200);
    this.patientsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
    this.patientsTable.getColumnModel().getColumn(3).setPreferredWidth(40);
    this.patientsTable.getColumnModel().getColumn(4).setPreferredWidth(160);
    this.patientsTable.getColumnModel().getColumn(5).setPreferredWidth(60);
    this.patientsTable.getColumnModel().getColumn(6).setPreferredWidth(80);
    this.patientsTable.getColumnModel().getColumn(7).setPreferredWidth(40);
    this.patientsTable.getColumnModel().getColumn(8).setPreferredWidth(150);

    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    renderer.setHorizontalAlignment(JLabel.CENTER);
    this.patientsTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
    this.patientsTable.setDefaultEditor(Object.class, null);

    this.add(this.patientsScrollPane);
  }

  public void initDeleteButtonUI() {
    this.deleteButton = new JButton("Eliminar Paciente");
    this.deleteButton.setPreferredSize(new Dimension(500, 30));
    this.deleteButton.addActionListener(this);

    this.add(deleteButton);
  }

  public void updatePatientsTable(List<Patient> patients) {
    DefaultTableModel model = (DefaultTableModel) patientsTable.getModel();
    model.setRowCount(0);

    int i = 0;
    for (Patient patient : patients) {
      HealthEstablishment healthEstablishment =
          patient.getPatientMedicalInfo().getHealthEstablishment();

      model.addRow(new Object[0]);
      model.setValueAt(i + 1, i, 0);
      model.setValueAt(patient.getPatientBasicInfo().getName(), i, 1);
      model.setValueAt(patient.getPatientBasicInfo().getRut(), i, 2);
      model.setValueAt(patient.getPatientBasicInfo().getSex(), i, 3);
      model.setValueAt(patient.getPatientBasicInfo().getEmail(), i, 4);
      model.setValueAt(
          patient.getPatientMedicalInfo().getState() == PatientState.DEAD ? "SI" : "NO", i, 5);
      model.setValueAt(
          patient.getPatientMedicalInfo().getState() == PatientState.RECOVERED ? "SI" : "NO", i, 6);
      model.setValueAt(
          patient.getPatientMedicalInfo().getState() == PatientState.ACTIVE ? "SI" : "NO", i, 7);
      if (healthEstablishment != null) {
        model.setValueAt(healthEstablishment.getName(), i, 8);
      } else {
        model.setValueAt("N/A", i, 8);
      }
      i++;
    }
  }

  public void resetFilters() {
    this.searchTextField.setText("");
    this.filterComboBox.setSelectedIndex(0);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.searchButton) {
      String nameQuery = this.searchTextField.getText();
      int filterNumber = this.filterComboBox.getSelectedIndex();

      if (nameQuery.isBlank() && filterNumber == 0) {
        this.updatePatientsTable(this.app.getPatients());
        return;
      }

      List<Patient> filteredPatients = this.app.getPatients();
      switch (filterNumber) {
        case 0:
          break;
        case 1:
          filteredPatients =
              filteredPatients.stream()
                  .filter(p -> p.getPatientMedicalInfo().getState() == PatientState.ACTIVE)
                  .collect(Collectors.toList());
          break;
        case 2:
          filteredPatients =
              filteredPatients.stream()
                  .filter(p -> p.getPatientMedicalInfo().getHealthEstablishment() != null)
                  .collect(Collectors.toList());
          break;
        case 3:
          filteredPatients =
              filteredPatients.stream()
                  .filter(p -> p.getPatientMedicalInfo().getState() == PatientState.DEAD)
                  .collect(Collectors.toList());
          break;
        case 4:
          filteredPatients =
              filteredPatients.stream()
                  .filter(p -> p.getPatientMedicalInfo().getState() == PatientState.RECOVERED)
                  .collect(Collectors.toList());
          break;
      }

      if (nameQuery.isBlank()) {
        updatePatientsTable(filteredPatients);
      } else {
        updatePatientsTable(
            filteredPatients.stream()
                .filter(
                    p ->
                        p.getPatientBasicInfo()
                            .getName()
                            .toLowerCase()
                            .startsWith(this.searchTextField.getText().toLowerCase()))
                .collect(Collectors.toList()));
      }
    }

    if (e.getSource() == this.deleteButton) {
      if (this.selectedTableRow == -1) return;

      String rut = (String) this.patientsTable.getValueAt(this.selectedTableRow, 2);
      Patient patient =
          this.app.getPatients().stream()
              .filter(p -> p.getPatientBasicInfo().getRut().equals(rut))
              .findFirst()
              .orElse(null);
      if (patient != null) {
        this.app.getPatients().remove(patient);
        this.app.getPatientManager().deletePatient(patient);
      }

      this.updatePatientsTable(this.app.getPatients());
    }
  }
}
