package stepDefinition;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.junit.Cucumber;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.junit.Assert;
import org.junit.runner.RunWith;

import base.BaseClass;

@RunWith(Cucumber.class)
public class StepDefinitions {

	RestAssured restAssured;
	String endPoint;
	String dateURL;
	Response response;
	String resp;
	String specificDate;

	@Given("^User set \"([^\"]*)\" API endpoint$")
	public void user_set_API_endpoint(String arg1) throws Exception {
		if (arg1.equalsIgnoreCase("valid"))
			endPoint = "https://api.ratesapi.io";
	}

	@When("^User set the \"([^\"]*)\" date$")
	public void user_set_the_date(String arg1) throws Exception {
		if(arg1.equalsIgnoreCase("latest"))	
			dateURL = "/api/latest";
		
		if(arg1.equalsIgnoreCase("incorrect format"))
			dateURL = "/api/foo";
	}

	@When("^User set the \"([^\"]*)\" date as year\"([^\"]*)\" month\"([^\"]*)\" date\"([^\"]*)\"$")
	public void user_set_the_date_as_year_month_date(String specific, String year, String month, String date) throws Exception {
		
			specificDate = year+"-"+month+"-"+date;
			dateURL = "/api/"+specificDate;
	}
	
	@When("^Send GET HTTP request$")
	public void send_GET_HTTP_request() throws Exception {

		RestAssured.baseURI = endPoint;
		response = given().log().all()
				.when().get(dateURL)
				.then().log().all().extract().response();
	}
	
	@Then("^API call gets success with status code as \"([^\"]*)\"$")
	public void api_call_gets_success_with_status_code_as(String arg1) throws Exception {

		Assert.assertEquals(Integer.parseInt(arg1), response.getStatusCode());
	}

	@Then("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
	public void in_response_body_is(String arg1, String arg2) throws Exception {
		resp = response.asString();
		JsonPath js = new JsonPath(resp);
		if(arg1.equalsIgnoreCase("base")) {
			Assert.assertEquals(js.get(arg1).toString(),arg2);
		}
		if(arg1.equalsIgnoreCase("date") && arg2.equalsIgnoreCase("latest date")) {
			Assert.assertEquals(js.get(arg1).toString(),BaseClass.toDate());
		}
		
		if(arg1.equalsIgnoreCase("date") && arg2.equalsIgnoreCase("specific date")) {
			Assert.assertEquals(js.get(arg1).toString(),BaseClass.subOneDay(specificDate));
		}
	}

	@Then("^currencies exists in \"([^\"]*)\"$")
	public void currencies_exists_in(String arg1) throws Exception {
		JsonPath js = new JsonPath(resp);
		Assert.assertTrue(js.get(arg1).toString().length()>0);
	}
	
	

}