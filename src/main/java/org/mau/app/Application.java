package org.mau.app;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jboss.logging.Logger;
import org.mau.app.entrypoints.http.Configuration;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class Application {

  private static final Logger LOG = Logger.getLogger(Application.class);
  
  @Inject
  ObjectMapper mapper;

  public void onStart(@Observes StartupEvent event) {
    try {
      byte[] enconded = Files.readAllBytes(Path.of("config.json"));
      String configJson = Charset.defaultCharset().decode(ByteBuffer.wrap(enconded)).toString();
      LOG.debugv("Reading configuration from file config.json: {0}", configJson);
      Configuration config = mapper.readValue(configJson, Configuration.class);
      setConfiguration(config);
    } catch (IOException e) {
      LOG.errorv("Error on try read configuration from file config.json: {0}", e.getMessage());
      generateConfigJson();
    }
  }

  public void onStop(@Observes ShutdownEvent event) {
    LOG.debug("Stoping system!");
  }

  private void generateConfigJson() {
    Configuration config = Configuration.builder()
        .servers("localhost:9200")
        .topic("my.topic")
        .port("8090")
        .build();

    try {
      FileWriter writer = new FileWriter("config.json");
      String configJson = mapper.writeValueAsString(config);
      writer.write(configJson);
      writer.close();
    } catch (IOException e) {
      LOG.errorv("Error on try config.json: {0}", e.getMessage());
    }
  }

  private void setConfiguration(Configuration configuration) {
    LOG.debugv("Set configuration: {0}", configuration);
    System.setProperty("quarkus.http.port", configuration.getPort());
    System.setProperty("kafka.bootstrap.servers", configuration.getServers());
    System.setProperty("mp.messaging.outgoing.default.topic", configuration.getTopic());

    Properties properties = System.getProperties();
    System.out.println(properties.getProperty("mp.messaging.outgoing.default.topic"));
  }

}
