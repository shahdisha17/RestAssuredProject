package com.qa.test.restAPI;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.RestAPIBase.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase {

	TestBase testBase;
	String serviceURL;
	String apiURL;
	String url;
	CloseableHttpResponse closeableHttpResponse;
	
	
	@BeforeMethod
	public void setup(){
		testBase=new TestBase();
	    serviceURL= prop.getProperty("URL");
		apiURL=prop.getProperty("serviceURL");
		
        url=serviceURL+apiURL;
	}
	
	@Test(priority=1)
	public void getAPITestwithoutHeader() throws ClientProtocolException, IOException {
		RestClient restClient= new RestClient();
		closeableHttpResponse=restClient.get(url);

		//Ststus Code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Print status code :" +statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");

		//JSON string
		JSONObject responseJson= new JSONObject(responseString);
		System.out.println("print response :" +responseJson);
		
		//Single value assertion
		String perPageValue= TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of per page is-->" +perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);
		
		String totalValue= TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("Value of total page is-->" +totalValue);
		
		//Assertion and converted String to Integer
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		
		//Array value
		String lastName=TestUtil.getValueByJPath(responseJson, "/data[2]/last_name");
		String id=TestUtil.getValueByJPath(responseJson, "/data[2]/id");
		String avatar=TestUtil.getValueByJPath(responseJson, "/data[2]/avatar");
		String firstName=TestUtil.getValueByJPath(responseJson, "/data[2]/first_name");
		
		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);

		//All header
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for(Header headers :headerArray) {
			allHeaders.put(headers.getName(), headers.getValue());

		}
		System.out.println("Headers array :" +allHeaders);

	}
	

	@Test(priority=2)
	public void getAPITestwithHeaders() throws ClientProtocolException, IOException {
		RestClient restClient= new RestClient();
		HashMap<String,String> headerMap=new HashMap<String,String>();
		headerMap.put("Content-Type","application/json");
		
		closeableHttpResponse=restClient.get(url);
		

		//Status Code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Print status code :" +statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");

		//JSON string
		JSONObject responseJson= new JSONObject(responseString);
		System.out.println("print response :" +responseJson);
		
		//Single value assertion
		String perPageValue= TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of per page is-->" +perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);
		
		String totalValue= TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("Value of total page is-->" +totalValue);
		
		//Assertion and converted String to Integer
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		
		//Array value
		String lastName=TestUtil.getValueByJPath(responseJson, "/data[2]/last_name");
		String id=TestUtil.getValueByJPath(responseJson, "/data[2]/id");
		String avatar=TestUtil.getValueByJPath(responseJson, "/data[2]/avatar");
		String firstName=TestUtil.getValueByJPath(responseJson, "/data[2]/first_name");
		
		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);

		//All header
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for(Header headers :headerArray) {
			allHeaders.put(headers.getName(), headers.getValue());

		}
		System.out.println("Headers array :" +allHeaders);

	}
}
