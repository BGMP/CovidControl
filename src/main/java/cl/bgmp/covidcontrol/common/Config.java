package cl.bgmp.covidcontrol.common;

import java.awt.*;

public interface Config {

  String getTitle();

  boolean isResizable();

  int getWidth();

  int getHeight();

  Dimension getDimensions();

  Component getInitialLocation();
}
