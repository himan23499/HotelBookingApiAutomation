package resources;

import java.util.*;

import pojo.AddBookingDate;
import pojo.BookingDates;

public class TestDataBuild {
	
	public AddBookingDate addBookingPayload(Object firstname,String lastname,int totalPrice) {
		List<String> l=new ArrayList<>();
	
		AddBookingDate a = new AddBookingDate();
		a.setFirstname(firstname);
		a.setLastname(lastname);
		a.setTotalprice(totalPrice);
		a.setDepositpaid(true);
		BookingDates bk=new BookingDates();
		bk.setCheckin("2019-04-04");
		bk.setCheckout("2019-04-08");
//		List<BookingDates> li=new ArrayList<>();
//		li.add(bk);
		a.setBookingdates(bk);
		a.setAdditionalneeds("Dinner");
		return a;
	}
}
