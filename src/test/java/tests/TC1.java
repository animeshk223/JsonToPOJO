package tests;

import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payload.User;
import utility.Utils;

public class TC1 {
	
	User userPayload;
	
	@Test(priority = 1)
	void testJsonToPojoPost() {
		Faker faker = new Faker();
		userPayload = new User();
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
	Response response = 	given()
								.contentType(ContentType.JSON)
								.accept(ContentType.JSON)
								.body(userPayload)
							.when()
								.post("https://petstore.swagger.io/v2/user");
	
	response.then().log().all();
		
	}
	
	@Test(priority = 2)
	void testJsonToPojoGet() {
		
		Response response= given()
								.contentType(ContentType.JSON)
								.accept(ContentType.JSON)
								.body(userPayload)
						  .when()
						  		.get("https://petstore.swagger.io/v2/user/"+userPayload.getUsername());
		Utils util = new Utils();
		util.setApiResponseAsString(response);
		try {
		User user2 =	(User) util.responseToPojo(User.class);
			System.out.println("CLASS 2 KA RECORD NICHE BN GYA BHAI:");
			System.out.println("Id: " + user2.getId());
			System.out.println("FirstName: " + user2.getFirstName());
			System.out.println("LastName: " + user2.getLastName());
			System.out.println("Email: " + user2.getEmail());
			
			System.out.println("================================");
			System.out.println("CLASS 1 KA RECORD NICHE HAI BHAI:");
			System.out.println("Id: " + userPayload.getId());
			System.out.println("FirstName: " + userPayload.getFirstName());
			System.out.println("LastName: " + userPayload.getLastName());
			System.out.println("Email: " + userPayload.getEmail());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION AA GYA BHAI");
			e.printStackTrace();
		}
		
		
	}
}
