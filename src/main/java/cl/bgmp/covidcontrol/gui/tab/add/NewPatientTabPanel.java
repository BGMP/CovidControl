package cl.bgmp.covidcontrol.gui.tab.add;

import cl.bgmp.covidcontrol.gui.App;
import cl.bgmp.covidcontrol.model.Cesfam;
import cl.bgmp.covidcontrol.model.HealthEstablishment;
import cl.bgmp.covidcontrol.model.Patient;
import cl.bgmp.covidcontrol.model.PatientBasicInfo;
import cl.bgmp.covidcontrol.model.PatientMedicalInfo;
import cl.bgmp.covidcontrol.model.PatientState;
import cl.bgmp.covidcontrol.util.Validate;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.swing.*;

public class NewPatientTabPanel extends JPanel implements ActionListener {
  private static final String COMBOBOX_DISPLAY_VALUE = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

  private App app;

  private JPanel upperPanel;

  private JPanel leftPanel;
  private JLabel nameLabel;
  private JTextField nameTextField;
  private JLabel rutLabel;
  private JTextField rutTextField;
  private JLabel addressLabel;
  private JTextField addressTextField;
  private JLabel birthDateLabel;
  private JDateChooser birthDateChooser;
  private JLabel phoneLabel;
  private JTextField phoneTextField;
  private JLabel emailLabel;
  private JTextField emailTextField;
  private JLabel sexLabel;
  private ButtonGroup sexButtonGroup;
  private JRadioButton maleRadioButton;
  private JRadioButton femaleRadioButton;
  private JRadioButton otherRadioButton;
  private JLabel previsionLabel;
  private ButtonGroup previsionButtonGroup;
  private JRadioButton fonasaRadioButton;
  private JRadioButton isapreRadioButton;
  private JRadioButton naRadioButton;
  private JLabel stateLabel;
  private ButtonGroup stateButtonGroup;
  private JRadioButton activeButton;
  private JRadioButton recoveredButton;
  private JRadioButton deadButton;

  private JPanel rightPanel;
  private JLabel chronicDeceasesLabel;
  private JTextField chronicDeceasesTextField;
  private JLabel otherDeceasesLabel;
  private JTextField otherDeceasesTextField;
  private JLabel contactPhoneLabel;
  private JTextField contactPhoneTextField;
  private JLabel cesfamLabel;
  private JComboBox<String> cesfamComboBox;
  private JLabel cesfamDirectorLabel;
  private JTextField cesfamDirectorTextField;
  private JLabel pcrLabel;
  private JDateChooser pcrDateChooser;
  private JLabel healthEstablishmentCheckBoxLabel;
  private JCheckBox healthEstablishmentCheckBox;
  private JPanel healthEstablishmentPanel;
  private JLabel healthEstablishmentNameLabel;
  private JTextField healthEstablishmentNameTextField;
  private JLabel medicLabel;
  private JTextField medicTextField;
  private JLabel medicationLabel;
  private JTextField medicationTextField;
  private JLabel medicalProceduresLabel;
  private JTextField medicalProceduresTextField;

  private JPanel lowerPanel;
  private JButton addPatientButton;

  public NewPatientTabPanel(App app) {
    this.app = app;

    this.setLayout(new FlowLayout());
    this.initUI();
  }

  private void initUI() {
    this.initUpperPanelUI();
    this.initLowerPanelUI();
  }

  private void initUpperPanelUI() {
    this.upperPanel = new JPanel();
    this.upperPanel.setLayout(new GridLayout(1, 2));

    this.initLeftUpperPanelUI();
    this.initRightUpperPanelUI();

    this.add(upperPanel);
  }

  private void initLeftUpperPanelUI() {
    this.leftPanel = new JPanel();
    this.leftPanel.setBorder(BorderFactory.createTitledBorder("Datos Básicos"));
    this.leftPanel.setPreferredSize(new Dimension(440, 450));

    this.nameLabel = new JLabel("Nombre:");
    this.leftPanel.add(nameLabel);
    this.nameTextField = new JTextField(19);
    this.nameTextField.setName("Nombre");
    this.nameTextField.setPreferredSize(new Dimension(350, 25));
    this.leftPanel.add(nameTextField);

    this.rutLabel = new JLabel("RUT:");
    this.leftPanel.add(rutLabel);
    this.rutTextField = new JTextField(10);
    this.rutTextField.setName("RUT");
    this.rutTextField.setToolTipText("RUT con puntos y guión");
    this.rutTextField.setPreferredSize(new Dimension(350, 25));
    this.leftPanel.add(rutTextField);

    this.addressLabel = new JLabel("Dirección:");
    this.leftPanel.add(addressLabel);
    this.addressTextField = new JTextField(32);
    this.addressTextField.setName("Dirección");
    this.addressTextField.setPreferredSize(new Dimension(350, 25));
    this.leftPanel.add(addressTextField);

    this.birthDateLabel = new JLabel("Fecha de Nacimiento:");
    this.leftPanel.add(birthDateLabel);
    this.birthDateChooser = new JDateChooser();
    this.birthDateChooser.setName("Fecha de Nacimiento");
    this.birthDateChooser.setLocale(new Locale("es"));
    this.birthDateChooser.setMaxSelectableDate(new Date());
    this.birthDateChooser.setPreferredSize(new Dimension(290, 25));
    this.leftPanel.add(birthDateChooser);

    this.phoneLabel = new JLabel("Teléfono:");
    this.leftPanel.add(phoneLabel);
    this.phoneTextField = new JTextField(10);
    this.phoneTextField.setName("Teléfono");
    this.phoneTextField.setPreferredSize(new Dimension(350, 25));
    this.leftPanel.add(phoneTextField);

    this.emailLabel = new JLabel("E-mail:");
    this.leftPanel.add(emailLabel);
    this.emailTextField = new JTextField(18);
    this.emailTextField.setName("E-Mail");
    this.emailTextField.setPreferredSize(new Dimension(350, 25));
    this.leftPanel.add(emailTextField);

    this.sexLabel = new JLabel("Sexo:");
    this.leftPanel.add(sexLabel);
    this.sexButtonGroup = new ButtonGroup();
    this.maleRadioButton = new JRadioButton("Hombre");
    this.maleRadioButton.setName("M");
    this.femaleRadioButton = new JRadioButton("Mujer");
    this.femaleRadioButton.setName("F");
    this.otherRadioButton = new JRadioButton("Otro");
    this.otherRadioButton.setName("O");
    this.sexButtonGroup.add(maleRadioButton);
    this.sexButtonGroup.add(femaleRadioButton);
    this.sexButtonGroup.add(otherRadioButton);
    this.leftPanel.add(maleRadioButton);
    this.leftPanel.add(femaleRadioButton);
    this.leftPanel.add(otherRadioButton);

    JSeparator topSeparator = new JSeparator();
    topSeparator.setPreferredSize(new Dimension(400, 0));
    this.leftPanel.add(topSeparator);

    this.previsionLabel = new JLabel("Previsión:");
    this.leftPanel.add(previsionLabel);
    this.previsionButtonGroup = new ButtonGroup();
    this.fonasaRadioButton = new JRadioButton("Fonasa");
    this.isapreRadioButton = new JRadioButton("Isapre");
    this.naRadioButton = new JRadioButton("N/A");
    this.previsionButtonGroup.add(fonasaRadioButton);
    this.previsionButtonGroup.add(isapreRadioButton);
    this.previsionButtonGroup.add(naRadioButton);
    this.leftPanel.add(fonasaRadioButton);
    this.leftPanel.add(isapreRadioButton);
    this.leftPanel.add(naRadioButton);

    JSeparator bottomSeparator = new JSeparator();
    bottomSeparator.setPreferredSize(new Dimension(400, 0));
    this.leftPanel.add(bottomSeparator);

    this.stateLabel = new JLabel("Estado:");
    this.leftPanel.add(stateLabel);
    this.stateButtonGroup = new ButtonGroup();
    this.activeButton = new JRadioButton("Activo");
    this.recoveredButton = new JRadioButton("Recuperado");
    this.deadButton = new JRadioButton("Fallecido");
    this.stateButtonGroup.add(activeButton);
    this.stateButtonGroup.add(recoveredButton);
    this.stateButtonGroup.add(deadButton);
    this.leftPanel.add(activeButton);
    this.leftPanel.add(recoveredButton);
    this.leftPanel.add(deadButton);

    this.upperPanel.add(leftPanel);
  }

  private void initRightUpperPanelUI() {
    this.rightPanel = new JPanel();
    this.rightPanel.setBorder(BorderFactory.createTitledBorder("Información Médica"));
    this.rightPanel.setPreferredSize(new Dimension(440, 450));

    this.chronicDeceasesLabel = new JLabel("Enfermedades Crónicas:");
    this.rightPanel.add(chronicDeceasesLabel);
    this.chronicDeceasesTextField = new JTextField(25);
    this.chronicDeceasesTextField.setName("Enfermedades Crónicas");
    this.chronicDeceasesTextField.setPreferredSize(new Dimension(350, 25));
    this.rightPanel.add(chronicDeceasesTextField);

    this.otherDeceasesLabel = new JLabel("Otras Enfermedades:");
    this.rightPanel.add(otherDeceasesLabel);
    this.otherDeceasesTextField = new JTextField(26);
    this.otherDeceasesTextField.setName("Otras Enfermedades");
    this.otherDeceasesTextField.setPreferredSize(new Dimension(350, 25));
    this.rightPanel.add(otherDeceasesTextField);

    this.contactPhoneLabel = new JLabel("Tél. Contacto:");
    this.rightPanel.add(contactPhoneLabel);
    this.contactPhoneTextField = new JTextField(30);
    this.contactPhoneTextField.setName("Tél. Contacto");
    this.contactPhoneTextField.setPreferredSize(new Dimension(350, 25));
    this.rightPanel.add(contactPhoneTextField);

    this.pcrLabel = new JLabel("Fecha PCR:");
    this.rightPanel.add(pcrLabel);
    this.pcrDateChooser = new JDateChooser();
    this.pcrDateChooser.setName("Fecha PCR");
    this.pcrDateChooser.setLocale(new Locale("es"));
    this.pcrDateChooser.setMaxSelectableDate(new Date());
    this.pcrDateChooser.setPreferredSize(new Dimension(350, 25));
    this.rightPanel.add(pcrDateChooser);

    this.cesfamLabel = new JLabel("Cesfam:");
    this.rightPanel.add(cesfamLabel);
    this.cesfamComboBox = new JComboBox<>(new String[] {""});
    for (String cesfamName : this.app.getCesfamNames()) {
      this.cesfamComboBox.addItem(cesfamName);
    }
    this.cesfamComboBox.setName("Cesfam");
    this.cesfamComboBox.setPrototypeDisplayValue(COMBOBOX_DISPLAY_VALUE);
    this.cesfamComboBox.addActionListener(this);
    this.rightPanel.add(cesfamComboBox);

    this.cesfamDirectorLabel = new JLabel("Director del Cesfam:");
    this.rightPanel.add(cesfamDirectorLabel);
    this.cesfamDirectorTextField = new JTextField(27);
    this.cesfamDirectorTextField.setPreferredSize(new Dimension(350, 25));
    this.cesfamDirectorTextField.setName("Director del Cesfam");
    this.rightPanel.add(cesfamDirectorTextField);

    JSeparator separator = new JSeparator();
    separator.setPreferredSize(new Dimension(400, 0));
    this.rightPanel.add(separator);

    this.healthEstablishmentCheckBoxLabel = new JLabel("Paciente Internado");
    this.rightPanel.add(healthEstablishmentCheckBoxLabel);
    this.healthEstablishmentCheckBox = new JCheckBox();
    this.healthEstablishmentCheckBox.addActionListener(this);
    this.rightPanel.add(healthEstablishmentCheckBox);

    this.healthEstablishmentPanel = new JPanel();
    this.healthEstablishmentPanel.setBorder(
        BorderFactory.createTitledBorder("Residencia Sanitara Internación"));
    this.healthEstablishmentPanel.setPreferredSize(new Dimension(400, 150));
    this.rightPanel.add(healthEstablishmentPanel);

    this.healthEstablishmentNameLabel = new JLabel("Nombre:");
    this.healthEstablishmentPanel.add(healthEstablishmentNameLabel);
    this.healthEstablishmentNameTextField = new JTextField(28);
    this.healthEstablishmentNameTextField.setName("Residencia Sanitara Internación > Nombre");
    this.healthEstablishmentNameTextField.setPreferredSize(new Dimension(350, 25));
    this.healthEstablishmentNameTextField.setEnabled(false);
    this.healthEstablishmentPanel.add(healthEstablishmentNameTextField);

    this.medicLabel = new JLabel("Médico:");
    this.healthEstablishmentPanel.add(medicLabel);
    this.medicTextField = new JTextField(28);
    this.medicTextField.setName("Residencia Sanitara Internación > Médico");
    this.medicTextField.setPreferredSize(new Dimension(350, 25));
    this.medicTextField.setEnabled(false);
    this.healthEstablishmentPanel.add(medicTextField);

    this.medicationLabel = new JLabel("Medicamentos:");
    this.healthEstablishmentPanel.add(medicationLabel);
    this.medicationTextField = new JTextField(24);
    this.medicationTextField.setName("Residencia Sanitara Internación > Medicamentos");
    this.medicationTextField.setPreferredSize(new Dimension(350, 25));
    this.medicationTextField.setEnabled(false);
    this.healthEstablishmentPanel.add(medicationTextField);

    this.medicalProceduresLabel = new JLabel("Procedimientos Méd.");
    this.healthEstablishmentPanel.add(medicalProceduresLabel);
    this.medicalProceduresTextField = new JTextField(21);
    this.medicalProceduresTextField.setName(
        "Residencia Sanitara Internación > Procedimientos Méd.");
    this.medicalProceduresTextField.setPreferredSize(new Dimension(350, 25));
    this.medicalProceduresTextField.setEnabled(false);
    this.healthEstablishmentPanel.add(medicalProceduresTextField);

    this.upperPanel.add(rightPanel);
  }

  private void initLowerPanelUI() {
    this.lowerPanel = new JPanel();

    this.addPatientButton = new JButton("Añadir Paciente");
    this.addPatientButton.addActionListener(this);
    this.addPatientButton.setPreferredSize(new Dimension(500, 30));
    this.lowerPanel.add(addPatientButton);

    this.add(lowerPanel);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.addPatientButton) {
      if (this.allRequiredFieldsAreSet()) {
        String patientRUT = this.rutTextField.getText();
        if (this.app.getPatients().stream()
            .anyMatch(p -> p.getPatientBasicInfo().getRut().equals(patientRUT))) {
          JOptionPane.showMessageDialog(
              new JFrame(),
              "El Paciente con RUT " + patientRUT + " ya se encuentra registrado!.",
              "Info",
              JOptionPane.INFORMATION_MESSAGE);
          return;
        }

        String patientName = this.nameTextField.getText();

        if (!Validate.isValidRUT(patientRUT)) {
          JOptionPane.showMessageDialog(
              new JFrame(),
              "El RUT " + patientRUT + " es inválido!" + "\nPor favor, ingrese un RUT válido.",
              "Info",
              JOptionPane.INFORMATION_MESSAGE);
          return;
        }

        String patientAddress = this.addressTextField.getText();
        Date patientBirthDate = this.birthDateChooser.getDate();
        String patientPhone = this.phoneTextField.getText();
        if (!Validate.isValidMobileNumber(patientPhone)) {
          JOptionPane.showMessageDialog(
              new JFrame(),
              "El número de teléfono del paciente '"
                  + patientPhone
                  + "' es inválido!"
                  + "\nPor favor, ingrese un número de teléfono válido.",
              "Info",
              JOptionPane.INFORMATION_MESSAGE);
          return;
        }

        String patientEmail = this.emailTextField.getText();
        if (!Validate.isValidEmail(patientEmail)) {
          JOptionPane.showMessageDialog(
              new JFrame(),
              "El e-mail "
                  + patientEmail
                  + " es inválido!"
                  + "\nPor favor, ingrese un e-mail válido.",
              "Info",
              JOptionPane.INFORMATION_MESSAGE);
          return;
        }

        JRadioButton patientSexSelectedButton = this.getButtonGroupSelectedButton(sexButtonGroup);
        assert patientSexSelectedButton != null;
        String patientSex = patientSexSelectedButton.getName();

        JRadioButton patientPrevisionSelectedButton =
            this.getButtonGroupSelectedButton(previsionButtonGroup);
        assert patientPrevisionSelectedButton != null;
        String patientPrevision = patientPrevisionSelectedButton.getText();

        JRadioButton patientStateSelectedButton =
            this.getButtonGroupSelectedButton(stateButtonGroup);
        assert patientStateSelectedButton != null;
        String patientState = patientStateSelectedButton.getText();

        String patientChronicDeceases = this.chronicDeceasesTextField.getText();
        String patientOtherDeceases = this.otherDeceasesTextField.getText();

        String patientContactPhone = this.contactPhoneTextField.getText();
        if (!Validate.isValidMobileNumber(patientContactPhone)) {
          JOptionPane.showMessageDialog(
              new JFrame(),
              "El número de teléfono de contacto '"
                  + patientPhone
                  + "' es inválido!"
                  + "\nPor favor, ingrese un número de teléfono válido.",
              "Info",
              JOptionPane.INFORMATION_MESSAGE);
          return;
        }

        Date patientPCRDate = this.pcrDateChooser.getDate();
        String patientCesfamName = (String) this.cesfamComboBox.getSelectedItem();
        Cesfam patientCesfam = this.app.getCesfamByName(patientCesfamName);
        patientCesfam.setDirectorName(this.cesfamDirectorTextField.getText());

        PatientBasicInfo patientBasicInfo =
            new PatientBasicInfo(
                patientName,
                patientRUT,
                patientAddress,
                patientBirthDate,
                patientPhone,
                patientEmail,
                patientPrevision,
                patientSex);

        HealthEstablishment healthEstablishment = null;
        if (this.healthEstablishmentCheckBox.isSelected()) {
          String healthEstablishmentName = this.healthEstablishmentNameTextField.getText();
          String medic = this.medicTextField.getText();
          String medication = this.medicationTextField.getText();
          String medicalProcedures = this.medicalProceduresTextField.getText();

          healthEstablishment =
              new HealthEstablishment(
                  healthEstablishmentName, medic, medication, medicalProcedures);
        }

        PatientMedicalInfo patientMedicalInfo =
            new PatientMedicalInfo(
                patientChronicDeceases,
                patientOtherDeceases,
                patientPCRDate,
                patientContactPhone,
                PatientState.fromString(patientState),
                healthEstablishment,
                patientCesfam);

        Patient patient = new Patient(patientBasicInfo, patientMedicalInfo);
        this.app.getPatients().add(patient);
        this.app.getPatientManager().registerNewPatient(patient);
        this.app.getPatientsTabPanel().updatePatientsTable(this.app.getPatients());
        this.app.getPatientsTabPanel().resetFilters();
        this.app.getTabs().setSelectedIndex(0);
        this.clearAllFields();
      }
    }

    if (e.getSource() == this.healthEstablishmentCheckBox) {
      if (this.healthEstablishmentCheckBox.isSelected()) {
        this.healthEstablishmentNameTextField.setEnabled(true);
        this.medicTextField.setEnabled(true);
        this.medicationTextField.setEnabled(true);
        this.medicalProceduresTextField.setEnabled(true);
      } else {
        this.healthEstablishmentNameTextField.setEnabled(false);
        this.healthEstablishmentNameTextField.setText("");
        this.medicTextField.setEnabled(false);
        this.medicTextField.setText("");
        this.medicationTextField.setEnabled(false);
        this.medicationTextField.setText("");
        this.medicalProceduresTextField.setEnabled(false);
        this.medicalProceduresTextField.setText("");
      }
    }
  }

  private boolean allRequiredFieldsAreSet() {
    if (this.sexButtonGroup.getSelection() == null) {
      JOptionPane.showMessageDialog(
          new JFrame(),
          "No ha seleccionado un Sexo para el paciente!\nPor favor, seleccione una opción",
          "Info",
          JOptionPane.INFORMATION_MESSAGE);
      return false;
    }

    if (this.previsionButtonGroup.getSelection() == null) {
      JOptionPane.showMessageDialog(
          new JFrame(),
          "No ha seleccionado una Previsión para el paciente!\nPor favor, seleccione una opción",
          "Info",
          JOptionPane.INFORMATION_MESSAGE);
      return false;
    }

    if (this.stateButtonGroup.getSelection() == null) {
      JOptionPane.showMessageDialog(
          new JFrame(),
          "No ha seleccionado un Estado para el paciente!\nPor favor, seleccione una opción",
          "Info",
          JOptionPane.INFORMATION_MESSAGE);
      return false;
    }

    if (this.healthEstablishmentCheckBox.isSelected()) {
      List<Component> subFields =
          new ArrayList<>(List.of(this.healthEstablishmentPanel.getComponents()));
      for (Component subField : subFields) {
        if (subField instanceof JTextField) {
          JTextField textField = (JTextField) subField;

          if (textField.getText().isBlank()) {
            JOptionPane.showMessageDialog(
                new JFrame(),
                "El campo "
                    + textField.getName()
                    + " está vacío!\nPor favor rellene todos los campos",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
            return false;
          }
        }
      }
    }

    List<Component> fields = new ArrayList<>();
    fields.addAll(List.of(this.leftPanel.getComponents()));
    fields.addAll(List.of(this.rightPanel.getComponents()));
    for (Component field : fields) {
      if (field instanceof JTextField) {
        JTextField textField = (JTextField) field;

        if (textField.getText().isBlank()) {
          JOptionPane.showMessageDialog(
              new JFrame(),
              "El campo "
                  + textField.getName()
                  + " está vacío!\nPor favor rellene todos los campos",
              "Info",
              JOptionPane.INFORMATION_MESSAGE);
          return false;
        }
      }

      if (field instanceof JDateChooser) {
        JDateChooser dateChooser = (JDateChooser) field;
        if (dateChooser.getDate() == null) {
          JOptionPane.showMessageDialog(
              new JFrame(),
              "La fecha "
                  + dateChooser.getName()
                  + " está vacío!\nPor favor, seleccione una fecha válida",
              "Info",
              JOptionPane.INFORMATION_MESSAGE);
          return false;
        }
      }

      if (field instanceof JComboBox<?>) {
        JComboBox<String> comboBox = (JComboBox<String>) field;
        if (comboBox.getSelectedIndex() == 0) {
          JOptionPane.showMessageDialog(
              new JFrame(),
              "El selector "
                  + comboBox.getName()
                  + " está vacío!\nPor favor, seleccione una opción",
              "Info",
              JOptionPane.INFORMATION_MESSAGE);
          return false;
        }
      }
    }

    return true;
  }

  private void clearAllFields() {
    List<Component> fields = new ArrayList<>();
    fields.addAll(List.of(this.leftPanel.getComponents()));
    fields.addAll(List.of(this.rightPanel.getComponents()));
    fields.addAll(List.of(this.healthEstablishmentPanel.getComponents()));
    for (Component field : fields) {
      if (field instanceof JTextField) {
        JTextField textField = (JTextField) field;
        textField.setText("");
      }

      if (field instanceof JDateChooser) {
        JDateChooser dateChooser = (JDateChooser) field;
        dateChooser.setDate(null);
      }

      this.cesfamComboBox.setSelectedIndex(0);
      this.sexButtonGroup.clearSelection();
      this.previsionButtonGroup.clearSelection();
      this.stateButtonGroup.clearSelection();
    }
  }

  private JRadioButton getButtonGroupSelectedButton(ButtonGroup buttonGroup) {
    Iterator<AbstractButton> abstractButtonIterator = buttonGroup.getElements().asIterator();
    while (abstractButtonIterator.hasNext()) {
      JRadioButton radioButton = (JRadioButton) abstractButtonIterator.next();
      if (radioButton.isSelected()) {
        return radioButton;
      }
    }

    return null;
  }
}
