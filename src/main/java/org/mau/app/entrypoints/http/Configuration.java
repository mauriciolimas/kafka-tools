package org.mau.app.entrypoints.http;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Configuration {
  
  private String servers;
  private String topic;
  private String port;
}
