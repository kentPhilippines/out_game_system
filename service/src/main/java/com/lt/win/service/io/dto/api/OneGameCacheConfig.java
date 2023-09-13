package com.lt.win.service.io.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class OneGameCacheConfig implements Serializable {

  private static final long serialVersionUID = 2300809614897675576L;

  @JsonProperty(value = "SiteSessionToken")
  private String siteSessionToken;

  @JsonProperty(value = "GameSessionTokens")
  private List<String> gameSessionTokens;

  @JsonProperty(value = "GameIds")
  private Map<String,String> gameids;

  @JsonProperty(value = "EWSSessionId")
  private String eWSSessionId;

  @JsonProperty(value = "Token")
  private String token;
}
