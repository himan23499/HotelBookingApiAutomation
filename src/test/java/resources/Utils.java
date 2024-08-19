package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Utils {
	static RequestSpecification req;
	public RequestSpecification requestSpecification() throws IOException {
		if(req==null) {
			PrintStream log=new PrintStream(new FileOutputStream("logging.txt"));
			req = new RequestSpecBuilder().setBaseUri(getGlobalResource("baseUrl")).addFilter(RequestLoggingFilter.logRequestTo(log)).addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
			return req;
		}
		return req;
	}
	
	public static String getGlobalResource(String key) throws IOException {
		Properties p=new Properties();
		FileInputStream fis=new FileInputStream("D:\\Api Automation\\Api Practice\\RestfulBookerProject\\src\\test\\java\\resources\\global.properties");
		p.load(fis);
		
		return p.getProperty(key);
	}
	public String getjsonPath(Response response,String key) {
		String res = response.asString();
		JsonPath js=new JsonPath(res);
		return js.get(key).toString();
	}
}
