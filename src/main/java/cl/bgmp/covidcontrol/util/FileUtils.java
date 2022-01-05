package cl.bgmp.covidcontrol.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public interface FileUtils {

  /**
   * Retrieves a file as an {@link InputStream} from resources.
   *
   * @param clazz The class where this method is called at
   * @param path Path to the resource
   * @return The requested file as an InputStream from the resources directory
   */
  static InputStream resourceAsStream(Class<?> clazz, String path) {
    return clazz.getClassLoader().getResourceAsStream(path);
  }

  /**
   * Retrieves a {@link File} from resources.
   *
   * @param clazz The class where this method is called at
   * @param path Path to the resource
   * @return The requested {@link File} from the resources directory or {@code null} if not found
   */
  static File resourceAsFile(Class<?> clazz, String path) {
    final URL resource = clazz.getClassLoader().getResource(path);
    if (resource == null) return null;

    return new File(resource.getFile());
  }

  /**
   * Reads a {@link File} from resources and returns its contents as a {@link String}.
   *
   * @param clazz The class where this method is called at
   * @param path Path to the resource
   * @return The requested {@link File} from the resources directory
   */
  static String resourceAsString(Class<?> clazz, String path) {
    InputStream inputStream = resourceAsStream(clazz, path);
    if (inputStream == null) return null;

    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

    StringBuilder sb = new StringBuilder();
    bufferedReader.lines().forEach(l -> sb.append(l).append("\n"));

    return sb.toString();
  }

  static ArrayList<String[]> getCSVData(File csvFile) {
    ArrayList<String[]> data = new ArrayList<>();
    BufferedReader reader;
    String line;

    try {
      reader =
          new BufferedReader(
              new InputStreamReader(new FileInputStream(csvFile), StandardCharsets.UTF_8));
      while ((line = reader.readLine()) != null) {
        data.add(line.split(","));
      }

    } catch (IOException ignored) {
      return null;
    }

    return data;
  }
}
