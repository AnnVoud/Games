package be.belfius.games.domain;

public enum DifficultySequence {
	Niv1("very easy",1),
	Niv2("easy",2),
	Niv3("average",3),
	Niv4("difficult",4),
	Niv5("very difficult",5);
	
	private String DifficultyName;
	private int DifficultNr;
	
	private DifficultySequence(String difficultyName, int difficultNr) {
		DifficultyName = difficultyName;
		DifficultNr = difficultNr;
	}

	public String getDifficultyName() {
		return DifficultyName;
	}

	public void setDifficultyName(String difficultyName) {
		DifficultyName = difficultyName;
	}

	public int getDifficultNr() {
		return DifficultNr;
	}

	public void setDifficultNr(int difficultNr) {
		DifficultNr = difficultNr;
	}
	
}
