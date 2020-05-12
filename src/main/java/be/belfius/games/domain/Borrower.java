package be.belfius.games.domain;

public class Borrower implements Comparable<Borrower> {
	private int id;
	private String borrower_name;
	private String street;
	private String house_number;
	private String bus_number;
	private int postcode;
	private String city;
	private String telephone;
	private String email;

	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}


	public String getBorrower_name() {
		return borrower_name;
	}


	public void setBorrower_name(String borrower_name) {
		this.borrower_name = borrower_name;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getHouse_number() {
		return house_number;
	}


	public void setHouse_number(String house_number) {
		this.house_number = house_number;
	}


	public String getBus_number() {
		return bus_number;
	}


	public void setBus_number(String bus_number) {
		this.bus_number = bus_number;
	}


	public int getPostcode() {
		return postcode;
	}


	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	// OPTION 3 : INFO FIRST BORROWER
	public String printNameAndCity() {
		return
			  "borrower name : " + borrower_name + "\r" 
			+ "city          : " + city;
		}
	
	// OPTION 7B : BORROWEDGAMES BY BORROWER
	public String toString() {
		return	
		String.format("%-30s %-30s %-30s %-30s", (borrower_name == null ? "":borrower_name), (city == null ? "":city), (telephone == null ? "":telephone), (email == null ? "":email));
	}


	@Override
	public int compareTo(Borrower o) {
		return this.getBorrower_name().compareTo(o.getBorrower_name());
	}


}
