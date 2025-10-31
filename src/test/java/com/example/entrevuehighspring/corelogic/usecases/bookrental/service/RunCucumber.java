package com.example.entrevuehighspring.corelogic.usecases.bookrental.service;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.FEATURES_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@SelectPackages("com.example.entrevuehighspring.corelogic.usecases.bookrental.service")
@ConfigurationParameter(
   key = GLUE_PROPERTY_NAME,
   value = "com.example.entrevuehighspring.corelogic.usecases.bookrental.service"
)
public class RunCucumber {
}