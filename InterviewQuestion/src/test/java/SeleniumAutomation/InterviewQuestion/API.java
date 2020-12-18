package SeleniumAutomation.InterviewQuestion;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class API {
	
	@BeforeTest
	public String Date() {
		Calendar cal  = Calendar.getInstance();
	    //subtracting a day
	    cal.add(Calendar.DATE, -1);
	    SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
	    String oneDayBefore = s.format(new Date(cal.getTimeInMillis()));
	    //System.out.println(oneDayBefore);
		return oneDayBefore;
	}
	
	
	@Test(priority = 1)
	@Parameters("InCorrectURl")
	public void RatesAPI(String InCorrectURl) {
		given().
		when().
			get(InCorrectURl+ "latest").
		then().
			assertThat().
				statusCode(200).
	        and().
	        	 body(containsString("INR")).
	        and().
	       		body("base", equalTo("EUR")).
		 	and().
		 		body("date", equalTo(Date()));
		System.out.println("Status Code & Response Body Correct");
		}
	
	@Test(priority = 2)
	@Parameters("InCorrectURl")
	public void RatesWrongAPI(String InCorrectURl) {
		
		given().
		when().
			get(InCorrectURl).
		then().
			assertThat().
				statusCode(400).
	        and().
		 		body("error", equalTo("time data 'api' does not match format '%Y-%m-%d'"));
		System.out.println("Status Code For Incorrect URL");
		}
	
	@Test(priority = 3)
	@Parameters({"InCorrectURl","RandomPastDate"})
	public void RatesAPIForDate(String InCorrectURl, String RandomPastDate) {
		given().
		when().
			get(InCorrectURl + RandomPastDate).
		then().
		assertThat().
			statusCode(200).
        and().
        	 body(containsString("INR")).
        and().
       		body("base", equalTo("EUR")).
	 	and().
	 		body("date", equalTo(RandomPastDate));
		System.out.println("Status Code & Response Body Correct For Past Random Date");
		}
	
	@Test(priority = 4)
	@Parameters({"InCorrectURl","nextDate"})
	public void RatesAPINextDate(String InCorrectURl, String nextDate) {
		given().
		when().
			get(InCorrectURl + nextDate).
		then().
		assertThat().
			statusCode(200).
        and().
        	 body(containsString("INR")).
        and().
       		body("base", equalTo("EUR")).
	 	and().
	 		body("date", equalTo(Date()));
		System.out.println("Status Code & Response Body Correct For Next Random Date");
			//Date in response is not fixed some time it came US date - 1, Some time It show Indian time zone Date
		}
}
