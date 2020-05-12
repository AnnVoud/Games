package be.belfius.games.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import be.belfius.games.domain.Difficulty;
import be.belfius.games.domain.DifficultySequence;
import be.belfius.games.exceptions.RepoException;

public class DAODifficulty {
	// errors indien er altijd nieuwe connectie werd geopend
	private Connection myDiffConnection;

	public DAODifficulty() {
		super();
		this.myDiffConnection = new DAOGameRepository().connectGameDB();
	}

	// OPTION 8 : GAMES WITH MIN DIFFICULTY
	public int getDifficultyWeight(int id) throws RepoException {
		Difficulty difficulty = new Difficulty();
		int foundSequence = 0;

		if (myDiffConnection == null) {
			this.myDiffConnection = new DAOGameRepository().connectGameDB();
		}

		try (PreparedStatement s = myDiffConnection.prepareStatement("select * from difficulty where id = ?")) {

			s.setInt(1, id);
			ResultSet rs = s.executeQuery();

			if ((rs != null) & (rs.next())) {
				difficulty.setId(rs.getInt("id"));
				difficulty.setDifficulty_name(rs.getString("difficulty_name"));

				for (DifficultySequence difficultySequence : DifficultySequence.values()) {
					if (difficultySequence.getDifficultyName().equals(difficulty.getDifficulty_name())) {
						foundSequence = difficultySequence.getDifficultNr();
					}
				}
			}

			s.close();

		} catch (SQLException e) {
			throw new RepoException(e.getMessage());
		}
		return foundSequence;
	}

	public void closeConnection() {
		try {
			myDiffConnection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
