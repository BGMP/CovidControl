package cl.bgmp.covidcontrol;

import cl.bgmp.covidcontrol.gui.App;
import javax.swing.*;

public class CovidControl {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(App::new);
  }
}
