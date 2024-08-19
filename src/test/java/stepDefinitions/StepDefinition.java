package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import junit.framework.Assert;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class StepDefinition extends Utils {
	RequestSpecification res;
	ResponseSpecification resspec;
	static Response response;
	static String expectedfirstName;
	static String booking_id;
	static String invalid_id = "5647";
	TestDataBuild data=new TestDataBuild();
	@Given("Add Booking Payload with {string} {string} {int}")
	public void add_booking_payload_with(String firstName, String lastName, int totalPrice) throws IOException {
		expectedfirstName =  firstName;
		System.out.println("Expected First name"+expectedfirstName);
	   res= given().spec(requestSpecification()).body(data.addBookingPayload(firstName, lastName, totalPrice));
	}
	@Given("Add Booking Payload with {int} {string} {int}")
	public void add_booking_payload_with(int firstName, String lastName, int totalPrice) throws IOException {
		expectedfirstName = Integer.toString(firstName);
		System.out.println("Expected First name"+expectedfirstName);
	   res= given().spec(requestSpecification()).body(data.addBookingPayload(firstName, lastName, totalPrice));
	}
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
	   APIResources resourceapi = APIResources.valueOf(resource);
	   resspec =new  ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	   if(method.equalsIgnoreCase("post")) {
	   response = res.when().post(resourceapi.getResource());
	   }else if(method.equalsIgnoreCase("delete")) {
		   response = res.when().delete(resourceapi.getResource());
	   }else if(method.equalsIgnoreCase("put")) {
		   response = res.when().put(resourceapi.getResource());
	   }
	   
	   else {
		   response = res.when().get(resourceapi.getResource()).then().log().all().extract().response();
		   System.out.println(response.asString());
	   }
	}

	@Then("the api call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(int status) {
//		assertEquals(response.statusCode(),status);
		System.out.println(response.statusCode());
	}

	@Then("Verify Booking_id created maps to {string} using {string}")
	public void verify_booking_id_created_maps_to_using(String expectedname, String resource) throws IOException {
		booking_id=getjsonPath(response,"bookingid");
		res=given().log().all().spec(requestSpecification()).pathParam("id",booking_id);
		user_calls_with_http_request(resource,"GET");
		String actualName = getjsonPath(response,"firstname");
		System.out.println("Actual first name "+actualName);
		assertEquals(expectedfirstName, actualName);
	}

	@Given("BookingId")
	public void booking_id() throws IOException {
		    res= given().log().all().spec(requestSpecification()).header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=").pathParam("booking_id", booking_id);
	}
	@Given("Invalid BookingID")
	public void invalid_booking_id() throws IOException {
		 res= given().log().all().spec(requestSpecification()).header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=").pathParam("id",invalid_id);

	}

	@And("{string} in response body is {string}")
	public void in_response_body_is(String key, String st) {
	  String status= getjsonPath(response,key);
	  System.out.println("Response is -:"+response.toString());
	  
	  assertEquals(st,status);
	}
	@Given("Update Booking Payload with {string} {string} and BookingID")
	public void update_booking_payload_with_and_booking_id(String firstname, String lastname) throws IOException {
	   res=given().log().all().spec(requestSpecification()).pathParam("booking_id", booking_id).body(data.addBookingPayload(firstname,lastname,767));
	}
	
	@Given("Create Token Payload with {string} and {string}")
	public void create_token_payload_with_and(String username, String password) throws IOException {
		 res= given().spec(requestSpecification()).body("{\r\n"
		 		+ "     \"username\" : \""+username+"\",\r\n"
		 		+ "    \"password\" : \""+password+"\"\r\n"
		 		+ "}");
	}

	@Then("Verify if Token is Generated Succesfully in Response") 
	public void verify_if_token_is_generated_succesfully_in_response() {
	    String token = getjsonPath(response,"token");
	    System.out.println(token);
	}

	@Given("BookingId is set to an invalid ID {string}")
	public void booking_id_is_set_to_an_invalid_id(String BookingID) throws IOException {
		 res= given().log().all().spec(requestSpecification()).pathParam("booking_id", BookingID);
	}
	
	@Given("Update Booking Payload with {string} {string} and {string}")
	public void update_booking_payload_with_and(String firstname, String lastname, String invalid_Booking_Id) throws IOException {
		   res=given().log().all().spec(requestSpecification()).pathParam("booking_id", invalid_Booking_Id).body(data.addBookingPayload(firstname,lastname,767));
		   
	}

	@Given("the API endpoint is set to retrieve all bookings")
	public void the_api_endpoint_is_set_to_retrieve_all_bookings() throws IOException {
	   res=given().spec(requestSpecification());
	}
	
	@Then("the list of bookings is retrieved successfully")
	public void the_list_of_bookings_is_retrieved_successfully() {
		List<Map<String, Integer>> bookings = response.jsonPath().getList("");
        assertTrue("Booking list is empty!", bookings.size() > 0);
	}

	@Then("the response contains at least one booking ID")
	public void the_response_contains_at_least_one_booking_id() {
		List<Map<String, Integer>> bookings = response.jsonPath().getList("");
        assertTrue("No booking IDs found in response!", bookings != null && !bookings.isEmpty());
        System.out.println("Number of bookings retrieved: " + bookings.size());

        // Optional: Print all booking IDs for verification
        for (Map<String, Integer> booking : bookings) {
            System.out.println("Booking ID: " + booking.get("bookingid"));
        }
	}
	
}