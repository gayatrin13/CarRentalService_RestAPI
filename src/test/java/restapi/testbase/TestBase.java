package restapi.testbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import io.restassured.RestAssured;

public class TestBase {
	String url;
	public Properties props;

	public TestBase() {
		props = new Properties();
		try {
			props.load(new FileInputStream(new File(
					System.getProperty("user.dir") + "\\src\\test\\java\\restapi\\config\\config.properties")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url = props.getProperty("baseURI");
		System.out.println(url);
		RestAssured.baseURI = url;

	}
}
