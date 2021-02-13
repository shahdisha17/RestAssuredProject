package com.qa.test.restAPI;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.RestAPIBase.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostAPItest extends TestBase{
	
	TestBase testBase;
	String serviceURL;
	String apiURL;
	String url;
	RestClient restClient;
	Users users;
	CloseableHttpResponse closeableHttpResponse;
	
	@BeforeMethod
	public void setUp() {
		testBase=new TestBase();
		serviceURL=prop.getProperty("URL");
		apiURL=prop.getProperty("serviceURL");
		
		url=serviceURL+apiURL;
	}
	
	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException {
		RestClient restClient=new RestClient();
		HashMap<String,String> headerMap=new HashMap<String,String>();
		headerMap.put("Content-type", "application/json");
		
		//jackson-API
		ObjectMapper mapper=new ObjectMapper();
		Users users=new Users("morpheus","leader");
		
		//object to json file conversion
		mapper.writeValue(new File("C:/Users/bhagyesh/eclipse-workspace/RestAssuredProject/src/main/java/com/qa/data/users.json"), users);
		
		//object to json in String:
		String userJSONString = mapper.writeValueAsString(users);
		System.out.println(userJSONString);
		
		closeableHttpResponse = restClient.post(url, userJSONString, headerMap);
		
		//Validate response from API:
		
		//1. Check status code:
		int statusCode= closeableHttpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode,testBase.RESPONSE_STATUS_CODE_201);
		
		//2. JSONString
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");

		JSONObject responseJSON= new JSONObject(responseString);
		System.out.println("The response from API is-->" +responseJSON);
		
		//3. Validate our String created
		Users userObj= mapper.readValue(responseString, Users.class);
		System.out.println(userObj);
		
		Assert.assertTrue(users.getName().equals(userObj.getName()));
		Assert.assertTrue(users.getJob().equals(userObj.getJob()));
		
		System.out.println(userObj.getId());
		System.out.println(userObj.getCreatedAt());

    }
}