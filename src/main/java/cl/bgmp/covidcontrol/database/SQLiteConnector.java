package cl.bgmp.covidcontrol.database;

import cl.bgmp.covidcontrol.gui.App;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO: CHECK FOR DUPLICATED RUTS
public class SQLiteConnector {
  private String url;
  private Connection connection;

  public SQLiteConnector(String filePath) {
    this.url = "jdbc:sqlite:" + filePath;
    this.connection = null;
  }

  public Connection getConnection() {
    return connection;
  }

  public Connection connect() {
    synchronized (App.class) {
      try {
        if (this.connection != null && !this.connection.isClosed()) return null;
        this.connection = DriverManager.getConnection(url);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return connection;
  }

  public void disconnect() {
    try {
      if (this.connection != null && !this.connection.isClosed()) {
        this.connection.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
