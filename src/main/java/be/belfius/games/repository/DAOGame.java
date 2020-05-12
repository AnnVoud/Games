package be.belfius.games.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import be.belfius.games.domain.Category;
import be.belfius.games.domain.Difficulty;
import be.belfius.games.domain.Game;
import be.belfius.games.domain.Out2Objects;
import be.belfius.games.domain.Out3Objects;
import be.belfius.games.exceptions.RepoException;

public class DAOGame {
	// OPTION 2 : INFO FIFTH GAME
	public Game showFifthGame() throws RepoException {
		Game game = new Game();
		try (Statement s = new DAOGameRepository().connectGameDB().createStatement()){			

			s.execute("select * from game where id = 5");

			ResultSet rs = s.getResultSet();

			if (rs != null) {
				rs.next();
				game.setId(rs.getInt("id"));
				game.setGame_name(rs.getString("game_name"));
				game.setEditor(rs.getString("editor"));
				game.setAuthor(rs.getString("author"));
				game.setYear_edition(rs.getInt("year_edition"));
				game.setAge(rs.getString("age"));
				game.setMin_players(rs.getInt("min_players"));
				game.setMax_players(rs.getInt("max_players"));
				game.setCategory_id(rs.getInt("category_id"));
				game.setPlay_duration(rs.getString("play_duration"));
				game.setDifficulty_id(rs.getInt("difficulty_id"));
				game.setPrice(rs.getBigDecimal("price"));
				game.setImage(rs.getString("image"));
			}

			s.close();

		} catch (SQLException e) {
			throw new RepoException(e.getMessage());
		}
		return game;
	}

	// OPTION 4 : GAME OF YOUR CHOICE
	public Game showAGameOfYourChoice(String myInputString) throws RepoException {
		Game game = new Game();
		try (PreparedStatement s = new DAOGameRepository().connectGameDB().prepareStatement("select * from game where UPPER(game_name) like  ? ")){
			
			String temp = "%" + myInputString.toUpperCase() + "%";
			s.setString(1, temp);

			ResultSet rs = s.executeQuery();

			if (rs.next()) {
				game.setId(rs.getInt("id"));
				game.setGame_name(rs.getString("game_name"));
				game.setEditor(rs.getString("editor"));
				game.setAuthor(rs.getString("author"));
				game.setYear_edition(rs.getInt("year_edition"));
				game.setAge(rs.getString("age"));
				game.setMin_players(rs.getInt("min_players"));
				game.setMax_players(rs.getInt("max_players"));
				game.setCategory_id(rs.getInt("category_id"));
				game.setPlay_duration(rs.getString("play_duration"));
				game.setDifficulty_id(rs.getInt("difficulty_id"));
				game.setPrice(rs.getBigDecimal("price"));
				game.setImage(rs.getString("image"));
			}

			s.close();

		} catch (SQLException e) {
			throw new RepoException(e.getMessage());
		}
		return game;
	}
	
	// OPTION 5/8 : LIST ALL GAMES - SORTED BY NAME
	public ArrayList<Game> showAllGames() throws RepoException {
		ArrayList<Game> foundGames = new ArrayList<Game>();

		try (Statement s = new DAOGameRepository().connectGameDB().createStatement()) {

			ResultSet rs = s.executeQuery("Select * from game order by game_name");

			while (rs.next()) {
				Game oneFoundGame = new Game();
				oneFoundGame.setGame_name(rs.getString("game_name"));
				oneFoundGame.setEditor(rs.getString("editor"));
				oneFoundGame.setPrice(rs.getBigDecimal("price"));
				oneFoundGame.setDifficulty_id(rs.getInt("difficulty_id"));
				oneFoundGame.setId(rs.getInt("id"));
				foundGames.add(oneFoundGame);
			}

			s.close();

		} catch (SQLException e) {
			throw new RepoException(e.getMessage());
		}
		return foundGames;
	}

	// OPTION 6A : LIST ALL GAMES & CATEGORIES
	public ArrayList<Out2Objects<String,String>> showAllGamesAndCategories() throws RepoException {
		ArrayList<Out2Objects<String,String>> foundGames = new ArrayList<>();
		try (Statement s = new DAOGameRepository().connectGameDB().createStatement()){

			ResultSet rs = s.executeQuery(
					"Select G.game_name as game_name,  C.category_name as cat_name from game G, category C where G.category_id=C.id order by game_name");

			while (rs.next()) {
				Out2Objects<String,String> foundGC = new Out2Objects<String, String>();

				foundGC.setObj1(rs.getString("game_name"));
				foundGC.setObj2(rs.getString("cat_name"));

				foundGames.add(foundGC);
			}

			s.close();

		} catch (SQLException e) {
			throw new RepoException(e.getMessage());
		}
		return foundGames;
	}

	// OPTION 6B : GAME, CATEGORY & DIFFICULTY GAME OF CHOICE
	public Out3Objects<Game, Category, Difficulty> showGameCatDiff(String searchName) throws RepoException {
		Out3Objects<Game, Category, Difficulty> myGameCatDiff = new Out3Objects<Game, Category, Difficulty>();
		Game game = new Game();

		try (PreparedStatement s = new DAOGameRepository().connectGameDB().prepareStatement("select * from game where UPPER(game_name) like  ? ")){
			// OPHALEN GAME-INFO

			String temp = "%" + searchName.toUpperCase() + "%";
			s.setString(1, temp);

			ResultSet rs = s.executeQuery();

			if (rs.next()) {
				game.setId(rs.getInt("id"));
				game.setGame_name(rs.getString("game_name"));
				game.setEditor(rs.getString("editor"));
				game.setAuthor(rs.getString("author"));
				game.setYear_edition(rs.getInt("year_edition"));
				game.setAge(rs.getString("age"));
				game.setMin_players(rs.getInt("min_players"));
				game.setMax_players(rs.getInt("max_players"));
				game.setCategory_id(rs.getInt("category_id"));
				game.setPlay_duration(rs.getString("play_duration"));
				game.setDifficulty_id(rs.getInt("difficulty_id"));
				game.setPrice(rs.getBigDecimal("price"));
				game.setImage(rs.getString("image"));

				myGameCatDiff.setObj1(game);
			}

			s.close();

		} catch (SQLException e) {
			throw new RepoException(e.getMessage());
		}

		if (game.getGame_name() != null) {
			try (PreparedStatement s = new DAOGameRepository().connectGameDB().prepareStatement("select * from category where id =  ? ")){
				// OPHALEN CATEGORY-INFO
				Category category = new Category();
				
				s.setInt(1, game.getCategory_id());

				ResultSet rs = s.executeQuery();

				if (rs.next()) {
					category.setId(rs.getInt("id"));
					category.setCategory_name(rs.getString("category_name"));
				}

				myGameCatDiff.setObj2(category);

				s.close();

			} catch (SQLException e) {
				throw new RepoException(e.getMessage());
			}
		}

		if (game.getGame_name() != null) {
			try (PreparedStatement s = new DAOGameRepository().connectGameDB().prepareStatement("select * from difficulty where id =  ? ")){
				// OPHALEN DIFFICULTY-INFO
				Difficulty difficulty = new Difficulty();				

				s.setInt(1, game.getDifficulty_id());

				ResultSet rs = s.executeQuery();

				if (rs.next()) {
					difficulty.setId(rs.getInt("id"));
					difficulty.setDifficulty_name(rs.getString("difficulty_name"));
				}

				myGameCatDiff.setObj3(difficulty);

				s.close();

			} catch (SQLException e) {
				throw new RepoException(e.getMessage());
			}
		}

		if (game.getGame_name() != null) {
			return myGameCatDiff;
		} else {
			return null;
		}
	}
	
	// OPTION 7A : INFO GAME FOR LIST BORROWED GAMES
	public Game showGameById(int gameId) throws RepoException {
		Game game = new Game();
		try (PreparedStatement s = new DAOGameRepository().connectGameDB().prepareStatement("select * from game where id =  ? ")){
			
			s.setInt(1, gameId);

			ResultSet rs = s.executeQuery();

			if (rs.next()) {
				game.setId(rs.getInt("id"));
				game.setGame_name(rs.getString("game_name"));
				game.setEditor(rs.getString("editor"));
				game.setAuthor(rs.getString("author"));
				game.setYear_edition(rs.getInt("year_edition"));
				game.setAge(rs.getString("age"));
				game.setMin_players(rs.getInt("min_players"));
				game.setMax_players(rs.getInt("max_players"));
				game.setCategory_id(rs.getInt("category_id"));
				game.setPlay_duration(rs.getString("play_duration"));
				game.setDifficulty_id(rs.getInt("difficulty_id"));
				game.setPrice(rs.getBigDecimal("price"));
				game.setImage(rs.getString("image"));
			}

			s.close();

		} catch (SQLException e) {
			throw new RepoException(e.getMessage());
		}
		return game;
	}

}
