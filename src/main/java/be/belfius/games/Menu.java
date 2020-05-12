package be.belfius.games;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.belfius.games.domain.Game;
import be.belfius.games.domain.Out2Objects;
import be.belfius.games.domain.Out3Objects;
import be.belfius.games.domain.Borrow;
import be.belfius.games.domain.Borrower;
import be.belfius.games.domain.Category;
import be.belfius.games.domain.Difficulty;
import be.belfius.games.domain.DifficultySequence;
import be.belfius.games.exceptions.RepoException;
import be.belfius.games.services.GameService;
import be.belfius.games.services.MyScanner;

public class Menu {
	private String menuChoice;
	private String myInputString;
	private Logger logger;

	public Menu() {
		super();
		System.setProperty("org.slf4j.simpleLogger.showDateTime", "true"); // Use this setting to show the date and time
		System.setProperty("org.slf4j.simpleLogger.dateTimeFormat", "yyyy-MM-dd HH:mm:ss"); // Use this setting to
																							// format te date and
		logger = LoggerFactory.getLogger(Menu.class);
	}

	public void loopMenu(GameService myGameService) {

		do {
			menuChoice = showmenu();
			System.out.println();
			logger.trace("Option chosen : " + menuChoice);

			switch (menuChoice) {

			case "1":
				try {
					categoryPrintHeader();
					categoryPrintDetail(myGameService.showFirstGameCategory());
					categoryPrintFooter();

				} catch (RepoException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "2":
				try {
					gamePrintHeader();
					gamePrintDetail(myGameService.showFifthGame());
					gamePrintFooter();
				} catch (RepoException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "3":
				try {
					borrowerPrintHeader();
					borrowerPrintDetail(myGameService.showFirstBorrower());
					borrowerPrintFooter();
				} catch (RepoException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "4":
				myInputString = showAGameOfYourChoiceIO();
				System.out.println("");
				try {
					Game foundGame = myGameService.showAGameOfYourChoice(myInputString);
					if (foundGame.getGame_name() == null) {
						System.out.println("Sorry, we didn't found that game.");
					} else {
						gamePrintHeader();
						System.out.println(foundGame.printPart4());
						gamePrintFooter();
					}
				} catch (RepoException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "5":
				try {
					ArrayList<Game> sortedGames = new ArrayList<>();
					sortedGames = myGameService.showAllGames();
					Collections.sort(sortedGames);
					showAllGamesPrintHeader();
//					for (Game oneGame : sortedGames) {
//						System.out.println(oneGame.printPart5());
//					}

					sortedGames.forEach(game -> System.out.println(game.printPart5()));
					showAllGamesPrintFooter();
				} catch (RepoException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "6":
				try {
					ArrayList<Out2Objects<String, String>> gamesAndCategory = new ArrayList<>();
					gamesAndCategory = myGameService.showAllGamesAndCategories();

					gamesAndCategoriesPrintHeader();
					for (Out2Objects<String, String> oneGameCat : gamesAndCategory) {
						System.out.printf("%-50s %-20s %n", (String) oneGameCat.getObj1(),
								(String) oneGameCat.getObj2());
					}
					gamesAndCategoriesPrintFooter();

				} catch (RepoException e) {
					System.out.println(e.getMessage());
				}

				try {
					Out3Objects<Game, Category, Difficulty> gameCatDiff = new Out3Objects<Game, Category, Difficulty>();
					gameCatDiff = myGameService.showGameCatDiff(showAGameOfYourChoiceIO());

					if (gameCatDiff == null) {
						System.out.println("Sorry, we didn't found that game.");
					} else {
						showGameCatDiffPrintHeader();
						System.out.println(gameCatDiff.getObj1());
						System.out.println();
						System.out.println(gameCatDiff.getObj2());
						System.out.println();
						System.out.println(gameCatDiff.getObj3());
						showGameCatDiffPrintFooter();
					}

					System.out.println();

				} catch (RepoException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "7A":
				try {
					ArrayList<Out3Objects<Borrow, Borrower, Game>> gamesAndBorrow = new ArrayList<>();
					gamesAndBorrow = myGameService.showBorrowedGames();

					showBorrowedPrintHeader();
					for (Out3Objects<Borrow, Borrower, Game> oneBorrowedGame : gamesAndBorrow) {
						System.out.printf("%-35s %-35s %-15s %-15s %n",

								((Game) oneBorrowedGame.getObj3()).getGame_name(),
								((Borrower) oneBorrowedGame.getObj2()).getBorrower_name(),
								((Borrow) oneBorrowedGame.getObj1()).getBorrow_date(),
								((((Borrow) oneBorrowedGame.getObj1()).getReturn_date() == null) ? " "
										: ((Borrow) oneBorrowedGame.getObj1()).getReturn_date()));
					}
					showBorrowedGamesPrintFooter();
				} catch (RepoException e) {
					System.out.println(e.getMessage());
				}

				break;

			case "7B":
				// SHOW ALL AVAILABLE BORROWERS
				showBorrowedGamesIO();

				try {
					getAllBorrowersPrintHeader();
//					for (Borrower oneBorrower : myGameService.getAllBorrowers("")) {
//						System.out.println(oneBorrower);
//					}
					// as above, but shorter
					myGameService.getAllBorrowers("").forEach(System.out::println);

					getAllBorrowersPrintFooter();
					showBorrowedGamesIO2();
				} catch (RepoException e1) {
					System.out.println(e1.getMessage());
				}

				// SHOW ALL AVAILABLE BORROWERS WITH SEARCH NAME
				myInputString = (new MyScanner().receiveString(""));

				if (myInputString.equals("X"))
					break;

				try {
					// BORROWERS WITH A SPECIFIC NAME
					ArrayList<Borrower> borrowers = new ArrayList<>();
					borrowers = myGameService.getAllBorrowers(myInputString);

					while ((borrowers.size() > 1) & !(myInputString.equals("X"))) {
						getAllBorrowersPrintHeader();
						// Collections.sort(borrowers);
						for (Borrower oneBorrower : borrowers) {
							System.out.println(oneBorrower);
						}
						getAllBorrowersPrintFooter();

						myInputString = (new MyScanner().receiveString(
								"More than one Borrower found, please narrow your selection(name) or press X to return to the menu"));
						borrowers = myGameService.getAllBorrowers(myInputString);
					}

					if (myInputString.equals("X"))
						break;

					// FIND BORROWED GAMES
					Borrower myBorrower = myGameService.findBorrowerByName(myInputString);
					ArrayList<Out3Objects<Borrow, Borrower, Game>> gamesAndBorrow = new ArrayList<>();
					gamesAndBorrow = myGameService.showBorrowedGamesByBorrower(myBorrower);

					if (myBorrower.getBorrower_name() == null) {
						System.out.println("Sorry, there is no Borrower with a name like " + myInputString);
					} else {
						if (gamesAndBorrow.size() == 0) {
							System.out.println("Borrower " + myBorrower.getBorrower_name()
									+ " has no borrowed games for the moment.");
						} else {
							showBorrowedPrintHeader();
							for (Out3Objects<Borrow, Borrower, Game> oneBorrowedGame : gamesAndBorrow) {
								System.out.printf("%-35s %-35s %-15s %-15s %n",

										((Game) oneBorrowedGame.getObj3()).getGame_name(),
										((Borrower) oneBorrowedGame.getObj2()).getBorrower_name(),
										((Borrow) oneBorrowedGame.getObj1()).getBorrow_date(),
										((((Borrow) oneBorrowedGame.getObj1()).getReturn_date() == null) ? " "
												: ((Borrow) oneBorrowedGame.getObj1()).getReturn_date()));
							}
							showBorrowedGamesPrintFooter();
						}
					}

				} catch (RepoException e2) {
					System.out.println(e2.getMessage());
				}

				break;

			case "8":
				gamesWithMinDiffPrintHeader();
				showGamesWithMinDiffIO();
				for (DifficultySequence difficultySequence : DifficultySequence.values()) {
					System.out.println(difficultySequence.getDifficultyName());
				}
				gamesWithMinDiffPrintFooter();

				myInputString = new MyScanner().receiveString("");

				try {
					ArrayList<Out2Objects<Game, String>> foundGames = new ArrayList<>();
					foundGames = myGameService.showGamesWithMinDiff(myInputString);

					if (foundGames.size() == 0) {
						System.out.println("Sorry, we didn't found a game with a difficulty greater or equal to : "
								+ myInputString);
					} else {
						gamesWithMinDiffPrintHeader2();

						for (Out2Objects oneGame : foundGames) {
							System.out.println(((Game) oneGame.getObj1()).printPart8((String) (oneGame.getObj2())));
						}

						gamesWithMinDiffPrintFooter2();
					}

					System.out.println();
				} catch (RepoException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "9":
				myInputString = getAllBorrowersIO();
				try {
					ArrayList<Borrower> borrowers = new ArrayList<>();
					borrowers = myGameService.getAllBorrowers(myInputString);
					if (borrowers.size() == 0) {
						System.out.println("Sorry, we didn't found a borrower with a name like " + myInputString);
					} else {
						getAllBorrowersPrintHeader();
						Collections.sort(borrowers);
						for (Borrower oneBorrower : borrowers) {
							System.out.println(oneBorrower);
						}
						getAllBorrowersPrintFooter();
					}
				} catch (RepoException e) {
					System.out.println(e.getMessage());
				}
				break;

			case "10":
			case "11":

				try {
					ArrayList<Out3Objects<Borrow, Borrower, Game>> gamesAndBorrow = new ArrayList<>();
					gamesAndBorrow = myGameService.showBorrowedGames(LocalDateTime.now());
					if (gamesAndBorrow.size() == 0) {
						System.out.println("No games are borrowed on today");
					} else {
						if (menuChoice.equals("10"))  {
							showBorrowedPrintHeader();
							for (Out3Objects<Borrow, Borrower, Game> oneBorrowedGame : gamesAndBorrow) {
								System.out.printf("%-35s %-35s %-15s %-15s %n",

										((Game) oneBorrowedGame.getObj3()).getGame_name(),
										((Borrower) oneBorrowedGame.getObj2()).getBorrower_name(),
										((Borrow) oneBorrowedGame.getObj1()).getBorrow_date(),
										((((Borrow) oneBorrowedGame.getObj1()).getReturn_date() == null) ? " "
												: ((Borrow) oneBorrowedGame.getObj1()).getReturn_date()));
							}
							showBorrowedGamesPrintFooter();
						} else {
							
							try (FileWriter fileWriter = new FileWriter(new File("BorrowedGamesOnToday.txt"))){
								PrintWriter printWriter = new PrintWriter(fileWriter);
								
								showBorrowedPrintHeaderFile(printWriter);
								
								for (Out3Objects<Borrow, Borrower, Game> oneBorrowedGame : gamesAndBorrow) {
									printWriter.printf("%-35s %-35s %-15s %-15s %n",

											((Game) oneBorrowedGame.getObj3()).getGame_name(),
											((Borrower) oneBorrowedGame.getObj2()).getBorrower_name(),
											((Borrow) oneBorrowedGame.getObj1()).getBorrow_date(),
											((((Borrow) oneBorrowedGame.getObj1()).getReturn_date() == null) ? " "
													: ((Borrow) oneBorrowedGame.getObj1()).getReturn_date()));
								}

							} catch (IOException e) {
								e.printStackTrace();
							}							
						}
						
					}
				} catch (RepoException e) {
					System.out.println(e.getMessage());
				}

				break;

			case "X":
				System.out.println("Thank you for using VanOudenhove_Ann_Games. BYE BYE.");
				break;

			default:
				System.out.println("Sorry, this choice is not supported.");
			}

		} while (!menuChoice.equals("X"));
	}

	public String showmenu() {

		System.out.println();
		System.out.println("_______________________________________________________________________");
		System.out.printf("| %-2s %-62s %-3s %n", "", " What do you want to do ? Please enter you choice : ", "  |");
		System.out.printf("| %-2s  %-62s %-3s %n", "", "", " |");
		System.out.printf("| %-2s : %-62s %-3s %n", "1", "Show the first game category", "|");
		System.out.printf("| %-2s : %-62s %-3s %n", "2", "Show the fifth game", "|");
		System.out.printf("| %-2s : %-62s %-3s %n", "3", "Show the first borrower", "|");
		System.out.printf("| %-2s : %-62s %-3s %n", "4", "Show a game of your choice", "|");
		System.out.printf("| %-2s : %-62s %-3s %n", "5", "Show all games", "|");
		System.out.printf("| %-2s : %-62s %-3s %n", "6", "Show a list of games and choose a game", "|");
		System.out.printf("| %-2s : %-62s %-3s %n", "7A", "Show a list of all borrowed games", "|");
		System.out.printf("| %-2s : %-62s %-3s %n", "7B", "Show a list of borrowed games by name borrower", "|");
		System.out.printf("| %-2s : %-62s %-3s %n", "8", "Advanced search : games with minimum difficulty", "|");
		System.out.printf("| %-2s : %-62s %-3s %n", "9", "Advanced search : borrowers on name", "|");
		System.out.printf("| %-2s : %-62s %-3s %n", "10", "Advanced search : list of all borrowed games on today", "|");
		System.out.printf("| %-2s : %-62s %-3s %n", "11", "Advanced search : Print all borrowed games on today to a file", "|");
		System.out.printf("| %-2s  %-62s %-3s %n", "", "", " |");
		System.out.printf("| %-2s : %-62s %-3s %n", "X", "Exit Applic", "|");
		System.out.printf("| %-2s  %-62s %-3s %n", "", "(Use of applic is logged in logfile.log)", " |");
		System.out.println("_______________________________________________________________________");
		System.out.println();
		return new MyScanner().receiveString("");
	}

	// OPTION 1 - OUTPUT FIRST CATEGORY
	private void categoryPrintHeader() {
		System.out.println("______________________________________________________________");
//		System.out.printf("| %-10s %-45s %-3s %n", "Id", "Name","  |");
//		System.out.println("| =========================================================  |");
	}

	private void categoryPrintDetail(Category category) {
		System.out.println(category);
//		System.out.printf("| %-10s %-45s %-3s %n", category.getId(), category.getCategory_name(),"  |");
	}

	private void categoryPrintFooter() {
		System.out.println("______________________________________________________________");
	}

	// OPTION 2 - OUTPUT FIFTH GAME
	private void gamePrintHeader() {
		System.out.println("______________________________________________________________");
	}

	private void gamePrintDetail(Game game) {
		System.out.println(game);
	}

	private void gamePrintFooter() {
		System.out.println("______________________________________________________________");
	}

	// OPTION 3 - OUTPUT FIRST BORROWER
	private void borrowerPrintHeader() {
		System.out.println("______________________________________________________________");
	}

	private void borrowerPrintDetail(Borrower borrower) {
		System.out.println(borrower.printNameAndCity());
	}

	private void borrowerPrintFooter() {
		System.out.println("______________________________________________________________");
	}

	// OPTION 4/6 - INPUT GAME OF YOUR CHOICE
	private String showAGameOfYourChoiceIO() {
		System.out.println("Please enter (a part of) the gamename : ");
		return (new MyScanner().receiveString(""));
	}

	// OPTION 5 - OUTPUT SHOW ALL GAMES
	private void showAllGamesPrintHeader() {
		System.out.println(
				"__________________________________________________________________________________________________________________");
		System.out.printf("%-50s %-50s %-50s %n", "Name", "Editor", "Price");
		System.out.println(
				"__________________________________________________________________________________________________________________");
	}

	private void showAllGamesPrintFooter() {
		System.out.println(
				"__________________________________________________________________________________________________________________");
	}

	// OPTION 6A - OUTPUT GAMESANDCATEGORIES
	private void gamesAndCategoriesPrintHeader() {
		System.out.println("______________________________________________________________");
		System.out.printf("%-50s %-50s %n", "GameName", "Category");
		System.out.println("______________________________________________________________");
	}

	private void gamesAndCategoriesPrintFooter() {
		System.out.println("______________________________________________________________");
	}

	// OPTION 6B : GAME, CATEGORY & DIFFICULTY GAME OF CHOICE
	private void showGameCatDiffPrintHeader() {
		System.out.println("______________________________________________________________");
	}

	private void showGameCatDiffPrintFooter() {
		System.out.println("______________________________________________________________");
	}

	// OPTION 7A - OUTPUT BORROWEDGAMES
	private void showBorrowedPrintHeader() {
		System.out.println(
				"___________________________________________________________________________________________________");
		System.out.printf("%-35s %-30s %16s %15s %n", "GameName", "Borrower", "Borrow Date", "Return Date");
		System.out.println(
				"___________________________________________________________________________________________________");
	}

	private void showBorrowedPrintHeaderFile(PrintWriter printWriter) {
		printWriter.println(
				"___________________________________________________________________________________________________");
		printWriter.printf("%-35s %-30s %16s %15s %n", "GameName", "Borrower", "Borrow Date", "Return Date");
		printWriter.println(
				"___________________________________________________________________________________________________");
	}
	
	
	private void showBorrowedGamesPrintFooter() {
		System.out.println(
				"___________________________________________________________________________________________________");
	}

	// OPTION 7B : BORROWEDGAMES BY BORROWER - INPUT
	// OPTION 9 : LIST OF BORROWERS - SEARCH ON (PART OF) NAME
	private void getAllBorrowersPrintHeader() {
		System.out.println(
				"_______________________________________________________________________________________________________________________");
		System.out.printf("%-30s %-30s %-30s %-30s %n", "BorrowerName", "City", "Telephone", "Email");
		System.out.println(
				"_______________________________________________________________________________________________________________________");
	}

	private void getAllBorrowersPrintFooter() {
		System.out.println(
				"_______________________________________________________________________________________________________________________");
	}

	private void showBorrowedGamesIO() {
		System.out.println("These are our (found) borrowers : ");
	}

	private void showBorrowedGamesIO2() {
		System.out.println("Please enter (a part of) the Borrower Name OR X to go back to main-menu : ");
	}

	// OPTION 8 : GAMES WITH MIN DIFFICULTY - INPUT
	private void gamesWithMinDiffPrintHeader() {
		System.out.println("______________________________________________________________");
	}

	private void showGamesWithMinDiffIO() {
		System.out.println("Please enter one of the following difficulties : ");
	}

	private void gamesWithMinDiffPrintFooter() {
		System.out.println("______________________________________________________________");
	}

	private String getAllBorrowersIO() {
		System.out.println("Please enter (a part of) the borrower_name : ");
		return (new MyScanner().receiveString(""));
	}

	private void gamesWithMinDiffPrintHeader2() {
		System.out.println(
				"__________________________________________________________________________________________________________________");
		System.out.printf("%-30s %-50s %-50s %n", "Difficulty", "Name", "Editor");
		System.out.println(
				"__________________________________________________________________________________________________________________");
	}

	private void gamesWithMinDiffPrintFooter2() {
		System.out.println(
				"__________________________________________________________________________________________________________________");
	}

}
