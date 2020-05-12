package be.belfius.games.domain;

public class Category {
	private int id;
	private String category_name;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCategory_name() {
		return category_name;
	}
	
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	// OPTION 1/6B : INFO FIRST GAME CATEGORY
	public String toString() {
		return
		  "category id     : " + id + "\r" 
		+ "category name   : " + category_name;
	}


}
