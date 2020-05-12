package be.belfius.games.services;

import java.time.LocalDateTime;
import java.util.ArrayList;

import be.belfius.games.domain.Borrow;
import be.belfius.games.domain.Borrower;
import be.belfius.games.domain.Category;
import be.belfius.games.domain.Difficulty;
import be.belfius.games.domain.DifficultySequence;
import be.belfius.games.domain.Game;
import be.belfius.games.domain.Out2Objects;
import be.belfius.games.domain.Out3Objects;
import be.belfius.games.exceptions.RepoException;
import be.belfius.games.repository.DAOBorrow;
import be.belfius.games.repository.DAOBorrower;
import be.belfius.games.repository.DAOCategory;
import be.belfius.games.repository.DAODifficulty;
import be.belfius.games.repository.DAOGame;
import be.belfius.games.repository.DAOGameRepository;

public class GameService {
	public GameService() throws RepoException {
		super();

		try {
			new DAOGameRepository().loadDriver();

		} catch (RepoException e2) {
			throw new RepoException(e2.getMyRepoMessage());
		}
	}

	// OPTION 1 - FIRST CATEGORY
	public Category showFirstGameCategory() throws RepoException {
		Category foundCategory = null;

		DAOCategory myDAOCategory = new DAOCategory();
		try {
			foundCategory = myDAOCategory.showFirstGameCategory();
		} catch (RepoException e) {
			throw new RepoException(e.getMyRepoMessage());
		}

		return foundCategory;
	}

	// OPTION 2 - FIFTH GAME
	public Game showFifthGame() throws RepoException {
		Game foundGame = null;

		DAOGame myDAOGame = new DAOGame();
		try {
			foundGame = myDAOGame.showFifthGame();
		} catch (RepoException e) {
			throw new RepoException(e.getMyRepoMessage());
		}

		return foundGame;
	}

	// OPTION 3 - FIRST BORROWER
	public Borrower showFirstBorrower() throws RepoException {
		Borrower foundBorrower = null;

		DAOBorrower myDAOBorrower = new DAOBorrower();
		try {
			foundBorrower = myDAOBorrower.showFirstBorrower();
		} catch (RepoException e) {
			throw new RepoException(e.getMyRepoMessage());
		}

		return foundBorrower;
	}

	// OPTION 4 - GAME OF YOUR CHOICE
	public Game showAGameOfYourChoice(String myInputString) throws RepoException {
		Game foundGame = null;

		DAOGame myDAOGame = new DAOGame();
		try {
			foundGame = myDAOGame.showAGameOfYourChoice(myInputString);
		} catch (RepoException e) {
			throw new RepoException(e.getMyRepoMessage());
		}

		return foundGame;
	}

	// OPTION 5 : LIST ALL GAMES - SORTED BY NAME
	public ArrayList<Game> showAllGames() throws RepoException {
		ArrayList<Game> foundGames = new ArrayList<>();

		DAOGame myDAOGame = new DAOGame();
		try {
			foundGames = myDAOGame.showAllGames();
		} catch (RepoException e) {
			throw new RepoException(e.getMyRepoMessage());
		}

		return foundGames;
	}

	// OPTION 6A : LIST ALL GAMES & CATEGORIES
	public ArrayList<Out2Objects<String, String>> showAllGamesAndCategories() throws RepoException {
		ArrayList<Out2Objects<String, String>> foundGamesCateg = new ArrayList<>();

		DAOGame myDAOGame = new DAOGame();
		try {
			foundGamesCateg = myDAOGame.showAllGamesAndCategories();
		} catch (RepoException e) {
			throw new RepoException(e.getMyRepoMessage());
		}

		return foundGamesCateg;
	}

	// OPTION 6B : GAME, CATEGORY & DIFFICULTY GAME OF CHOICE
	public Out3Objects<Game, Category, Difficulty> showGameCatDiff(String searchName) throws RepoException {
		Out3Objects<Game, Category, Difficulty> myGameCatDiff = new Out3Objects<Game, Category, Difficulty>();

		DAOGame myDAOGame = new DAOGame();
		try {
			myGameCatDiff = myDAOGame.showGameCatDiff(searchName);
		} catch (RepoException e) {
			throw new RepoException(e.getMyRepoMessage());
		}

		return myGameCatDiff;
	}

	// OPTION 7A : LIST BORROWED GAMES
	public ArrayList<Out3Objects<Borrow, Borrower, Game>> showBorrowedGames() throws RepoException {
		ArrayList<Borrow> listBorrowedGames = new ArrayList<>();
		ArrayList<Out3Objects<Borrow, Borrower, Game>> myBorrowedGames = new ArrayList<>();

		DAOBorrow myDAOBorrow = new DAOBorrow();
		DAOGame myDAOGame = new DAOGame();
		DAOBorrower myDAOBorrower = new DAOBorrower();

		try {
			listBorrowedGames = myDAOBorrow.showBorrowedGames();

			for (Borrow oneBorrow : listBorrowedGames) {
				Out3Objects<Borrow, Borrower, Game> myOut3Objects = new Out3Objects<>();
				myOut3Objects.setObj1(oneBorrow);
				myOut3Objects.setObj2(myDAOBorrower.showBorrowerbyId(oneBorrow.getBorrower_id()));
				myOut3Objects.setObj3(myDAOGame.showGameById(oneBorrow.getGame_id()));
				myBorrowedGames.add(myOut3Objects);
			}
		} catch (RepoException e) {
			throw new RepoException(e.getMyRepoMessage());
		}
		return myBorrowedGames;
	}

	// OPTION 7B : BORROWEDGAMES BY BORROWER - LIST OF BORROWERS
	// OPTION 9 : LIST OF BORROWERS - SEARCH ON (PART OF) NAME
	public ArrayList<Borrower> getAllBorrowers(String myInputString) throws RepoException {
		ArrayList<Borrower> foundBorrowers = new ArrayList<>();

		DAOBorrower myDAOBorrower = new DAOBorrower();
		try {
			foundBorrowers = myDAOBorrower.getAllBorrowers(myInputString);
		} catch (RepoException e) {
			throw new RepoException(e.getMyRepoMessage());
		}

		return foundBorrowers;
	}

	// OPTION 7B : BORROWEDGAMES BY BORROWER - SEARCH BORROWER BY NAME
	public Borrower findBorrowerByName(String myInputString) throws RepoException {
		Borrower foundBorrower = null;

		DAOBorrower myDAOBorrower = new DAOBorrower();
		try {
			foundBorrower = myDAOBorrower.findBorrowerbyName(myInputString);
		} catch (RepoException e) {
			throw new RepoException(e.getMyRepoMessage());
		}

		return foundBorrower;

	}

	// OPTION 7B : BORROWEDGAMES BY BORROWER
	public ArrayList<Out3Objects<Borrow, Borrower, Game>> showBorrowedGamesByBorrower(Borrower myBorrower)
			throws RepoException {
		ArrayList<Borrow> listBorrowedGames = new ArrayList<>();
		ArrayList<Out3Objects<Borrow, Borrower, Game>> myBorrowedGames = new ArrayList<>();

		DAOBorrow myDAOBorrow = new DAOBorrow();
		DAOGame myDAOGame = new DAOGame();
		DAOBorrower myDAOBorrower = new DAOBorrower();

		try {
			listBorrowedGames = myDAOBorrow.showBorrowedGamesByBorrower(myBorrower);

			for (Borrow oneBorrow : listBorrowedGames) {
				Out3Objects<Borrow, Borrower, Game> myOut3Objects = new Out3Objects<>();
				myOut3Objects.setObj1(oneBorrow);
				myOut3Objects.setObj2(myDAOBorrower.showBorrowerbyId(oneBorrow.getBorrower_id()));
				myOut3Objects.setObj3(myDAOGame.showGameById(oneBorrow.getGame_id()));
				myBorrowedGames.add(myOut3Objects);
			}
		} catch (RepoException e) {
			throw new RepoException(e.getMyRepoMessage());
		}
		return myBorrowedGames;
	}

	// OPTION 8 : GAMES WITH MIN DIFFICULTY
	public ArrayList<Out2Objects<Game, String>> showGamesWithMinDiff(String minDiff) throws RepoException {
		ArrayList<Game> foundGames = new ArrayList<>();
		ArrayList<Out2Objects<Game, String>> selectedGames = new ArrayList<>();
		DAODifficulty myDAODifficulty = new DAODifficulty();

		DAOGame myDAOGame = new DAOGame();
		try {
			foundGames = myDAOGame.showAllGames();
		} catch (RepoException e) {
			throw new RepoException(e.getMyRepoMessage());
		}

		int foundSequence = 999;
		for (DifficultySequence difficultySequence : DifficultySequence.values()) {
			if (difficultySequence.getDifficultyName().equals(minDiff)) {
				foundSequence = difficultySequence.getDifficultNr();
			}
		}

		for (Game oneGame : foundGames) {
			int gameDiffWeight = myDAODifficulty.getDifficultyWeight(oneGame.getDifficulty_id());

			if (gameDiffWeight >= foundSequence) {
				Out2Objects<Game, String> myGameDiff = new Out2Objects<Game, String>();
				myGameDiff.setObj1(oneGame);

				for (DifficultySequence difficultySeq : DifficultySequence.values()) {
					if (difficultySeq.getDifficultNr() == gameDiffWeight) {
						myGameDiff.setObj2(difficultySeq.getDifficultyName());
					}
				}
				selectedGames.add(myGameDiff);
			}
		}
		
		myDAODifficulty.closeConnection();
		return selectedGames;
	}

	// OPTION 10 - BORROWED GAMES TODAY
	public ArrayList<Out3Objects<Borrow, Borrower, Game>> showBorrowedGames(LocalDateTime today) throws RepoException {
		ArrayList<Borrow> listBorrowedGames = new ArrayList<>();
		ArrayList<Out3Objects<Borrow, Borrower, Game>> myBorrowedGames = new ArrayList<>();

		DAOBorrow myDAOBorrow = new DAOBorrow();
		DAOGame myDAOGame = new DAOGame();
		DAOBorrower myDAOBorrower = new DAOBorrower();

		try {
			listBorrowedGames = myDAOBorrow.showBorrowedGames(LocalDateTime.now());

			for (Borrow oneBorrow : listBorrowedGames) {
				Out3Objects<Borrow, Borrower, Game> myOut3Objects = new Out3Objects<>();
				myOut3Objects.setObj1(oneBorrow);
				myOut3Objects.setObj2(myDAOBorrower.showBorrowerbyId(oneBorrow.getBorrower_id()));
				myOut3Objects.setObj3(myDAOGame.showGameById(oneBorrow.getGame_id()));
				myBorrowedGames.add(myOut3Objects);
			}
		} catch (RepoException e) {
			throw new RepoException(e.getMyRepoMessage());
		}

		return myBorrowedGames;
	}
}
