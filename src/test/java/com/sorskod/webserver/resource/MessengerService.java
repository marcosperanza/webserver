package com.sorskod.webserver.resource;

import javax.inject.Singleton;

@Singleton
public class MessengerService {
    String getMessage() {
      return "Hello.";
    }
  }