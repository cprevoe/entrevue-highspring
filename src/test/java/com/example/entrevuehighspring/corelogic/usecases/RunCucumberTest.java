package com.example.entrevuehighspring.corelogic.usecases;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("com.example.entrevuehighspring.corelogic.usecases.bookrental")
@SelectClasspathResource("com/example/entrevuehighspring/corelogic/usecases/bookrental")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.example.entrevuehighspring.corelogic.usecases.bookrental")
@ConfigurationParameter(
    key = io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME,
    value = "pretty, html:target/cucumber-report.html"
)
public class RunCucumberTest {
}
