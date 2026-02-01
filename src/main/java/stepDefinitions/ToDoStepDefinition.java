package stepDefinitions;

import io.cucumber.java.en.Given;

public class ToDoStepDefinition {

    @Given("user is on the App home page")
    public void user_is_on_the_app_home_page() {
        System.out.println("App launched successfully — no actions performed");
    }
}
