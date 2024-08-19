package resources;

public enum APIResources {
	
	AddBookingAPI("/booking"),
	getBookingAPI("/booking/{id}"),
	deleteBookingAPI("/booking/{booking_id}"),
	putBookingAPI("/booking/{booking_id}"),
	CreatTokenAPI("/auth"),
	getAllBookingsAPI("/booking");
	private String resource;
	APIResources(String resource) {
		this.resource=resource;
	}
	
	public String getResource() {
		return resource;
	}
}
