package be.belfius.games.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import be.belfius.games.domain.Category;
import be.belfius.games.exceptions.RepoException;

public class DAOCategory {

	// OPTION 1 : INFO FIRST GAME CATEGORY
	public Category showFirstGameCategory() throws RepoException {
		Category category = new Category();
		try (Statement s = new DAOGameRepository().connectGameDB().createStatement()) {
			s.execute("select * from category where id = 1");

			ResultSet rs = s.getResultSet();

			if (rs != null) {
				rs.next();

				category.setId(rs.getInt("id"));
				category.setCategory_name(rs.getString("category_name"));
			}

			s.close();

		} catch (SQLException e) {
			throw new RepoException(e.getMessage());
		}
		return category;
	}
}
