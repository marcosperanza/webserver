package com.anritsu.common.webserver.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmitBody {
    @JsonProperty
    private String body;

    @JsonCreator
    public SubmitBody(@JsonProperty(value = "body", required = true) String body) {
      this.body = body;
    }
  }