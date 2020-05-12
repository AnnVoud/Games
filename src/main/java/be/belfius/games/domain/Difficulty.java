package be.belfius.games.domain;

public class Difficulty {
	private int id;
	private String difficulty_name;
	
	public Difficulty() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDifficulty_name() {
		return difficulty_name;
	}

	public void setDifficulty_name(String difficulty_name) {
		this.difficulty_name = difficulty_name;
	}

	// OPTION 6B : GAME, CATEGORY & DIFFICULTY GAME OF CHOICE
	public String toString() {
		return
		  "difficulty id   : " + id + "\r" 
		+ "difficulty name : " + difficulty_name;
	}

}
