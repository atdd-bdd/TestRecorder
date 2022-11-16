package com.kenpugh.testrecorder.runtests;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber.html"}, tags = "not @manual and not @ignore")
public class RunAutoTest {
}
