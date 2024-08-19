Feature: Validating Booking APIs

Scenario Outline: Verify if Booking is created using CreateBookingAPI
			Given Add Booking Payload with "<firstname>" "<lastname>" <totalprice>
			When user calls "AddBookingAPI" with "POST" http request
			Then the api call got success with status code 200
			And Verify Booking_id created maps to "<firstname>" using "getBookingAPI"
Examples:
	 | firstname 	 | lastname | totalprice		|
	 |	rahul      | Patil 		| 	456         |
	 |	Joker@m    | Mudhe 		| 	3645        |
   |	.Juk    	 | .lop 		| 	3        		|
   |	     	 		 | .lop 		| 	3        		|
   
Scenario Outline: Verify Booking Creation with Invalid Data
			Given Add Booking Payload with <firstname> "<lastname>" <totalprice>
			When user calls "AddBookingAPI" with "POST" http request
			Then the api call got success with status code 500
			
Examples:
	 | firstname 	 | lastname | totalprice		|
	 |	456      	 | Patil 		| 	456         |
	 |	567      	 | Mudhe 		| 	364       |
						

Scenario: Verify if Delete Booking Functionality is working
				
				Given BookingId
    		When user calls "deleteBookingAPI" with "DELETE" http request
      	Then the api call got success with status code 201 
				
				
Scenario: Verify if Update Booking Functionality is working
				
				Given Update Booking Payload with "JUK" "TUK" and BookingID
    		When user calls "putBookingAPI" with "PUT" http request
      	Then the api call got success with status code 200 
				
Scenario: Verify if Token is Getting Generated Successfully
				
				Given Create Token Payload with "admin" and "password123"
    		When user calls "CreatTokenAPI" with "POST" http request
      	Then the api call got success with status code 200 
      	And Verify if Token is Generated Succesfully in Response
				
				
Scenario: Verify Booking Deletion with Invalid ID
				Given BookingId is set to an invalid ID "99999"
				When user calls "deleteBookingAPI" with "DELETE" http request
				Then the api call got success with status code 405
				

				
Scenario: Verify Token Generation with Invalid Credentials
				Given Create Token Payload with "invalidUser" and "invalidPassword"
				When user calls "CreatTokenAPI" with "POST" http request
				Then the api call got success with status code 200
				And "reason" in response body is "Bad credentials"
				
Scenario: Verify Booking Update with Invalid ID
					Given Update Booking Payload with "JUK" "TUK" and "65748"
    			When user calls "putBookingAPI" with "PUT" http request
      		Then the api call got success with status code 405
				
Scenario: Verify Booking retrieval with an invalid Booking ID
					Given Invalid BookingID
					When user calls "getBookingAPI" with "GET" http request
					Then the api call got success with status code 404
					
Scenario: Verify the list of all bookings can be retrieved
    Given the API endpoint is set to retrieve all bookings
   When user calls "getAllBookingsAPI" with "GET" http request
    Then the api call got success with status code 200
    And the list of bookings is retrieved successfully
    And the response contains at least one booking ID
					
#				Given Add Booking Payload with "<firstname>" "<lastname>" "<totalprice>"
#				When user calls "AddBookingAPI" with "POST" http request
#				Then the api call got success with status code 200
#				Then Verify Booking_id created maps to "<firstname>" using "getBookingAPI"
    #		Given BookingId
    #		When user calls "deleteBookingAPI" with "DELETE" http request
    #		Then the api call got success with status code 200 
