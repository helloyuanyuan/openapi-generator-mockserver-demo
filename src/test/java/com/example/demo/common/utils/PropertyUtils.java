package com.example.demo.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertyUtils {

  static PropertyUtils instance;
  Properties properties;

  public static PropertyUtils getInstance() {
    if (null == instance) {
      instance = new PropertyUtils();
    }
    return instance;
  }

  public PropertyUtils() {
    String propertiesFile = loadProperties("environment/env.properties").getProperty("env");
    propertiesFile = "environment/" + propertiesFile + ".properties";
    this.properties = loadProperties(propertiesFile);
  }

  public static synchronized Properties loadProperties(String propertiesFile) {
    Properties properties = new Properties();
    InputStream in = null;
    try {
      in = PropertyUtils.class.getClassLoader().getResourceAsStream(propertiesFile);
      properties.load(new InputStreamReader(in, "UTF-8"));
    } catch (FileNotFoundException e) {
      log.error(propertiesFile, "FILE NOT FOUND!");
    } catch (IOException e) {
      log.error(propertiesFile, "LOADING IOException!");
    } finally {
      try {
        if (null != in) {
          in.close();
        }
      } catch (IOException e) {
        log.error(propertiesFile, "CLOSING IOException!");
      }
    }
    return properties;
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  public String getUrl(String host, int port) {
    return "http://" + host + ":" + port;
  }
}
