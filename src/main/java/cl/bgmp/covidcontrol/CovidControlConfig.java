package cl.bgmp.covidcontrol;

import cl.bgmp.covidcontrol.common.Config;
import java.awt.*;

public class CovidControlConfig implements Config {
  private static final Component CENTER = null;

  private String title;
  private boolean isResizable;
  private int width;
  private int height;
  private Dimension dimensions;
  private Component initialLocation;

  public CovidControlConfig() {
    this.title = "Aplicación de Control COVID-19";
    this.isResizable = false;
    this.width = 900;
    this.height = 600;
    this.dimensions = new Dimension(this.width, this.height);
    this.initialLocation = CENTER;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public boolean isResizable() {
    return isResizable;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public Dimension getDimensions() {
    return dimensions;
  }

  @Override
  public Component getInitialLocation() {
    return initialLocation;
  }
}
