package APIProject;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AllResponse_Test {
	
	@Test
	void TestAllResponse(){
		
		given().
			get("https://reqres.in/api/users?page=2").
		then().
			statusCode(200).
			body("data.id[1]",equalTo(8)).
			body("data.first_name",hasItems("Michael", "Lindsay")).
			//body("data.email", hasItem("lindsay.ferguson@reqres.in")).
		log().all();
		
		
			
		
		
		
	}

}
