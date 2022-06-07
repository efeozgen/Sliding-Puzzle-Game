

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.scene.input.*;

import java.io.*;
import java.util.*;

public class Main extends Application {

	// Datafield
	public static String fileName = "CSE1242_spring2022_project_level";
	public int levelNumber = 1;
	public int clickNumber = 0;
	public static String str = "";
	private Cell[][] cell = new Cell[4][4];
	Cell cell1;
	Cell cell2;
	private int X1;
	private int Y1;
	private int X2;
	private int Y2;
	static ArrayList<Block> listOfBlocks = new ArrayList<Block>();
	static ArrayList<String> listOfWords = new ArrayList<String>();
	static ArrayList<String> listOfNames = new ArrayList<String>();
	static ArrayList<Integer> listOfIds = new ArrayList<Integer>();
	static ArrayList<String> listOfAspectInfos = new ArrayList<String>();
	static ArrayList<Integer> levelAchievement = new ArrayList<Integer>();
	// Datafield

	/*
	 * This method takes the input from text file and saves information to
	 * arraylists then returns a boolean value according to the result of the
	 * process
	 */
	public int reader() {
		if (((levelNumber <= levelAchievement.size() + 1) && (levelNumber > 0))) {
			try {
				File txt = new File("levels\\" + fileName + levelNumber + ".txt");
				Scanner input = new Scanner(txt);
				while (input.hasNext()) {
					str += input.next();
					str += " ";
				}
				input.close();
			} catch (FileNotFoundException e) {
				return 2;
			}

			str = str.replace(",", " ");
			str = str.replace("  ", " ");
			Scanner input = new Scanner(str);
			while (input.hasNext()) {
				listOfWords.add(input.next());
			}
			input.close();
			for (int i = 0; i < 48; i++) {
				if (i % 3 == 0) {
					listOfIds.add(Integer.parseInt(listOfWords.get(i)));
				} else if (i % 3 == 1) {
					listOfNames.add(listOfWords.get(i));
				} else {
					listOfAspectInfos.add(listOfWords.get(i));
				}
			}
			for (int i = 0; i < 16; i++) {
				if ((listOfNames.get(i).equals("Starter")) || listOfNames.get(i).equals("Pipe")
						|| (listOfNames.get(i).equals("PipeStatic")) || (listOfNames.get(i).equals("End"))) {
					Pipe pipe = new Pipe(listOfIds.get(i), listOfNames.get(i), listOfAspectInfos.get(i));
					listOfBlocks.add(pipe);
				} else if (listOfNames.get(i).equals("Empty")) {
					Empty empty = new Empty(listOfIds.get(i), listOfNames.get(i), listOfAspectInfos.get(i));
					listOfBlocks.add(empty);
				}
			}
			str = "";
			listOfIds.clear();
			listOfNames.clear();
			listOfAspectInfos.clear();
			listOfWords.clear();
			return 0;
		} else {
			return 1;
		}

	}

	/*
	 * this method takes the information needed from the arraylist of blocks and
	 * creates cells. Then adds those cells to the gridPane that it took. Lastly it
	 * returns the pane that is modified.
	 */
	public GridPane gameCreater(GridPane pane) {
		int a = reader();
		if (a == 0) {
			int i = 0;
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					pane.add(cell[x][y] = (new Cell(listOfBlocks.get(i).getImg(), listOfBlocks.get(i).isIsstatic(),
							listOfBlocks.get(i).getName(), listOfBlocks.get(i).getAspectInfo(),
							listOfBlocks.get(i).isTopSide(), listOfBlocks.get(i).isBottomSide(),
							listOfBlocks.get(i).isRightSide(), listOfBlocks.get(i).isLeftSide())), y, x);
					i++;
				}
			}
			listOfBlocks.clear();
			return pane;
		} else if (a == 1) {
			pane.setDisable(true);
			return pane;
		} else {
			pane.setMaxWidth(100.0);
			pane.setDisable(true);
			return pane;
		}
	}

	/*
	 * Determines the sides that are available to entrance of the ball and
	 * interchanges the taken two cells using the condition written
	 */
	public boolean changer() {
		boolean a1 = (X2 < X1 + 1);
		boolean a2 = (X1 - 1 < X2);
		boolean b1 = (Y2 < Y1 + 1);
		boolean b2 = (Y1 - 1 < Y2);
		if ((cell2 != null) && (!((cell1).isIsstatic())) && (cell2.getName().equals("Empty"))
				&& (cell2.getAspectInfo().equals("Free"))
				&& (((!(a1 && a2)) && (b1 && b2)) || (((a1 && a2)) && !(b1 && b2))) && (Math.abs(X1 - X2) <= 1)
				&& (Math.abs(Y1 - Y2) <= 1)) {
			if (cell1.getAspectInfo().equals("Vertical")) {
				cell1.setTopSide(true);
				cell1.setBottomSide(true);
			} else if (cell1.getAspectInfo().equals("Horizontal")) {
				cell1.setLeftSide(true);
				cell1.setRightSide(true);
			} else if (cell1.getAspectInfo().equals("00")) {
				cell1.setLeftSide(true);
				cell1.setTopSide(true);
			} else if (cell1.getAspectInfo().equals("01")) {
				cell1.setTopSide(true);
				cell1.setRightSide(true);
			} else if (cell1.getAspectInfo().equals("10")) {
				cell1.setLeftSide(true);
				cell1.setBottomSide(true);
			} else if (cell1.getAspectInfo().equals("11")) {
				cell1.setBottomSide(true);
				cell1.setRightSide(true);
			}
			cell[X1][Y1] = cell2;
			cell[X2][Y2] = cell1;
			cell1 = null;
			cell2 = null;
			return true;
		} else if ((cell1 != null) && (cell2 != null)) {
			cell1 = null;
			cell2 = null;
		}
		return false;
	}

	/*
	 * It creates the primaryStage and scene and the nodes that are on the scene and
	 * regulates them according to the written methods.
	 */
	@Override
	public void start(Stage primaryStage) {
		GridPane paneFirst = new GridPane();

		Text statusText = new Text("Starting Game...");

		GridPane pane = gameCreater(paneFirst);
		BorderPane borderPane = new BorderPane();

		MenuBar leftBar = new MenuBar();
		Menu gameMenu = new Menu("Menu");
		MenuItem playGame = new MenuItem("Play Game");
		MenuItem Levels = new MenuItem("Levels");
		gameMenu.getItems().add(playGame);
		gameMenu.getItems().add(Levels);
		leftBar.getMenus().addAll(gameMenu);

		MenuBar rightBar = new MenuBar();
		Menu levelMenu = new Menu("Levels");
		Menu clickCount = new Menu("Click Count: " + clickNumber);
		rightBar.getMenus().addAll(clickCount);
		MenuItem nextLevel = new MenuItem("Next Level");
		MenuItem previousLevel = new MenuItem("Previous Level");
		MenuItem resetLevel = new MenuItem("Reset Level");
		levelMenu.getItems().add(nextLevel);
		levelMenu.getItems().add(previousLevel);
		levelMenu.getItems().add(resetLevel);
		rightBar.getMenus().addAll(levelMenu);
		Region spacer = new Region();
		spacer.getStyleClass().add("menu-bar");
		HBox.setHgrow(spacer, Priority.SOMETIMES);
		HBox menubars = new HBox(leftBar, spacer, rightBar);
		borderPane.setTop(menubars);

		VBox vbox = new VBox(250);
		vbox.setAlignment(Pos.CENTER);
		ImageView playButton = new ImageView(new Image("images\\playButton.png"));
		Button button = new Button();
		button.setStyle("-fx-background-color: transparent");
		playButton.setFitHeight(125);
		playButton.setFitWidth(150);
		playButton.relocate(0, 0);
		playButton.setStyle("-fx-background-color: transparent");
		vbox.getChildren().addAll(button, playButton);
		borderPane.setCenter(vbox);

		playButton.setOnMouseClicked(e -> {
			statusText.setText("Playing Game...");
			FadeTransition fade = new FadeTransition();
			fade.setDuration(Duration.millis(1500));
			fade.setNode(pane);
			fade.setFromValue(0);
			fade.setToValue(1);
			fade.play();
			borderPane.setCenter(pane);
		});

		Image img1 = new Image("images\\space.png");
		BackgroundImage bImg1 = new BackgroundImage(img1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true));
		Background bGround1 = new Background(bImg1);
		final StackPane levels = new StackPane();
		levels.setBackground(bGround1);

		final StackPane blockMoving = new StackPane();
		blockMoving.setStyle("-fx-background-color: transparent");

		playGame.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				borderPane.setCenter(pane);
				statusText.setText("Playing Game...");
			}
		});

		Levels.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				borderPane.setCenter(levels);
				statusText.setText("Choosing Level...");
			}
		});

		nextLevel.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				levelNumber++;
				GridPane pane1 = gameCreater(pane);
				if (pane1.isDisabled() && (pane1.getMaxWidth() == 100.0)) {
					statusText.setText("There is no Next Level! - Level " + --levelNumber);
					pane1.setDisable(false);
					pane1.setMaxWidth(400.0);
				} else if (pane1.isDisabled()) {
					statusText.setText("Next Level is not Achieved! - Level " + --levelNumber);
					pane1.setDisable(false);
				} else {
					System.out.println(clickNumber);
					clickNumber = 0;
					Image imageW = new Image("images\\win.jpg");
					BackgroundImage bGroundImgW = new BackgroundImage(imageW, BackgroundRepeat.NO_REPEAT,
							BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
					Background bGrndW = new Background(bGroundImgW);
					borderPane.setBackground(bGrndW);
					FadeTransition fade3 = new FadeTransition();
					fade3.setDuration(Duration.millis(1500));
					fade3.setNode(pane1);
					fade3.setFromValue(0);
					fade3.setToValue(1);
					fade3.play();
					borderPane.setCenter(pane1);
					statusText.setText("Next Level Loaded! - Level " + levelNumber);
				}
			}
		});

		previousLevel.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (levelNumber > 1) {
					System.out.println(clickNumber);
					clickNumber = 0;
					Image imageW = new Image("images\\white.jpg");
					BackgroundImage bGroundImgW = new BackgroundImage(imageW, BackgroundRepeat.NO_REPEAT,
							BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
					Background bGrndW = new Background(bGroundImgW);
					borderPane.setBackground(bGrndW);
					FadeTransition fade2 = new FadeTransition();
					fade2.setDuration(Duration.millis(1500));
					fade2.setNode(pane);
					fade2.setFromValue(0);
					fade2.setToValue(1);
					fade2.play();
					--levelNumber;
					borderPane.setCenter(gameCreater(pane));
					statusText.setText("Previous Level Loaded! - Level " + levelNumber);
				} else {
					statusText.setText("First Level is - Level " + levelNumber);
				}
			}
		});

		resetLevel.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.out.println(clickNumber);
				clickNumber = 0;
				clickCount.setText("Click Count: " + clickNumber);
				borderPane.setCenter(gameCreater(pane));
				statusText.setText("Level Resetted! - Level " + levelNumber);
			}
		});

		final VBox statusBar = new VBox();
		statusBar.setStyle("-fx-background-color: gainsboro");
		statusBar.getChildren().add(statusText);
		borderPane.setBottom(statusBar);

		pane.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				clickCount.setText("Click Count: " + clickNumber);
				statusText.setText("Dragging...");

				Dragboard db = pane.startDragAndDrop(TransferMode.ANY);

				ClipboardContent content = new ClipboardContent();
				content.putString(pane.getChildren().toString());
				db.setContent(content);

				cell1 = (Cell) (event.getPickResult().getIntersectedNode());
				for (int x = 0; x < 4; x++) {
					for (int y = 0; y < 4; y++) {
						if ((event.getSceneY() < 100 * (x + 1)) && (event.getSceneY() > 100 * x)
								&& (event.getSceneX() < 100 * (y + 1)) && (event.getSceneX() > 100 * y)) {
							setX1(x);
							setY1(y);
						}
					}
				}
				event.consume();
			}
		});

		pane.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getDragboard().hasString()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
			}
		});

		pane.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				cell2 = (Cell) (event.getPickResult().getIntersectedNode());
				for (int x = 0; x < 4; x++) {
					for (int y = 0; y < 4; y++) {
						if ((event.getSceneY() < 100 * (x + 1)) && (event.getSceneY() > 100 * x)
								&& (event.getSceneX() < 100 * (y + 1)) && (event.getSceneX() > 100 * y)) {
							setX2(x);
							setY2(y);
						}
					}
				}
				boolean end = true;
				if (event.getDragboard().hasString() && (cell1 != cell2)) {
					if (changer()) {
						clickNumber++;
						clickCount.setText("Click Count: " + clickNumber);

						pane.getChildren().remove(cell[X1][Y1]);
						pane.getChildren().remove(cell[X2][Y2]);
						pane.add(cell[X1][Y1], Y1, X1);
						pane.add(cell[X2][Y2], Y2, X2);

						int i = 0;
						int x = 0;
						int y = 0;
						for (int x0 = 0; x0 < 4; x0++) {
							for (int y0 = 0; y0 < 4; y0++) {
								if (cell[x0][y0].getName().equals("Starter")
										&& (cell[x0][y0].getAspectInfo().equals("Horizontal"))) {
									x = x0;
									y = y0 - 1;
									cell[x][y].setRightSide(false);
								} else if (cell[x0][y0].getName().equals("Starter")
										&& (cell[x0][y0].getAspectInfo().equals("Vertical"))) {
									x = x0 + 1;
									y = y0;
									cell[x][y].setTopSide(false);
								}
							}
						}
						while (end && (i < 16)) {
							if (cell[x][y].getAspectInfo().equals("Horizontal")) {

								if (cell[x][y].isRightSide()) {
									y = y + 1;
									if (cell[x][y].getName().equals("End")) {
										end = false;
									}
								} else if (cell[x][y].isLeftSide()) {
									y = y - 1;
									cell[x][y - 1].setRightSide(false);
									if (cell[x][y].getName().equals("End")) {
										end = false;
									}
								}
							} else if (cell[x][y].getAspectInfo().equals("Vertical")) {

								if (cell[x][y].isTopSide()) {
									x = x - 1;
									cell[x][y].setBottomSide(false);
									if (cell[x][y].getName().equals("End")) {
										end = false;
									}
								} else if (cell[x][y].isBottomSide()) {
									x = x + 1;
									cell[x][y].setTopSide(false);
									if (cell[x][y].getName().equals("End")) {
										end = false;
									}
								}
							} else if (cell[x][y].getAspectInfo().equals("00")) {
								if (cell[x][y].isTopSide()) {
									x = x - 1;
									cell[x][y].setBottomSide(false);
									if (cell[x][y].getName().equals("End")) {
										end = false;
									}
								} else if (cell[x][y].isLeftSide()) {
									y = y - 1;
									cell[x][y].setRightSide(false);
									if (cell[x][y].getName().equals("End")) {
										end = false;
									}
								}
							} else if ((cell[x][y].getAspectInfo()).equals("01")) {
								if (cell[x][y].isTopSide()) {
									x = x - 1;
									cell[x][y].setBottomSide(false);
									if (cell[x][y].getName().equals("End")) {
										end = false;
									}
								} else if (cell[x][y].isRightSide()) {
									y = y + 1;
									cell[x][y].setLeftSide(false);
									if (cell[x][y].getName().equals("End")) {
										end = false;
									}
								}
							} else if (cell[x][y].getAspectInfo().equals("10")) {
								if (cell[x][y].isLeftSide()) {
									y = y - 1;
									cell[x][y].setRightSide(false);
									if (cell[x][y].getName().equals("End")) {
										end = false;
									}
								} else if (cell[x][y].isBottomSide()) {
									x = x + 1;
									cell[x][y].setTopSide(false);
									if (cell[x][y].getName().equals("End")) {
										end = false;
									}
								}
							} else if (cell[x][y].getAspectInfo().equals("11")) {
								if (cell[x][y].isBottomSide()) {
									x = x + 1;
									cell[x][y].setTopSide(false);
									if (cell[x][y].getName().equals("End")) {
										end = false;
									}
								} else if (cell[x][y].isRightSide()) {
									y = y + 1;
									cell[x][y].setLeftSide(false);
									if (cell[x][y].getName().equals("End")) {
										end = false;
									}
								}
							}
							i++;
						}
					}
				}
				if ((cell1 == cell2) && end) {
					statusText.setText("Playing Game...");
				} else {
					clickCount.setText("Previous Levels Click Count: " + clickNumber);
					System.out.println(clickNumber);
					clickNumber = 0;
					statusText.setText("Done!");
					levelAchievement.add(1);
					levelNumber++;
					GridPane pane1 = gameCreater(pane);
					if (pane1.isDisabled() && (pane1.getMaxWidth() == 100.0)) {
						Image image2 = new Image("images\\won.png");
						BackgroundImage bGroundImg2 = new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT,
								BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
						Background bGrnd2 = new Background(bGroundImg2);
						borderPane.setBackground(bGrnd2);
						FadeTransition fade1 = new FadeTransition();
						fade1.setDuration(Duration.millis(10000));
						fade1.setNode(pane1);
						fade1.setFromValue(1);
						fade1.setToValue(0);
						fade1.play();
						statusText.setText("There is no Next Level! - Level " + --levelNumber);
						pane1.setDisable(false);
						pane1.setMaxWidth(400.0);
					} else if (pane1.isDisabled()) {
						statusText.setText("Next Level is not Achieved! - Level " + --levelNumber);
						pane1.setDisable(false);
					} else {
						Image image1 = new Image("images\\win.jpg");
						BackgroundImage bGroundImg1 = new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT,
								BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
						Background bGrnd1 = new Background(bGroundImg1);
						borderPane.setBackground(bGrnd1);
						statusText.setText("Level Achieved! - Next Level - Level " + levelNumber);
						FadeTransition fade = new FadeTransition();
						fade.setDuration(Duration.millis(10000));
						fade.setNode(pane1);
						fade.setFromValue(0);
						fade.setToValue(1);
						fade.play();
						borderPane.setCenter(pane1);
					}
				}
				event.consume();
			}
		});

		Image image = new Image("images\\entrance.png");
		BackgroundImage bGroundImg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background bGrnd = new Background(bGroundImg);
		borderPane.setBackground(bGrnd);

		Scene scene = new Scene(borderPane, 400, 450);
		primaryStage.setTitle("JavaFX");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	//Getters and Setters

	public int getX1() {
		return X1;
	}

	public void setX1(int x) {
		X1 = x;
	}

	public int getY1() {
		return Y1;
	}

	public void setY1(int y) {
		Y1 = y;
	}

	public int getX2() {
		return X2;
	}

	public void setX2(int x2) {
		X2 = x2;
	}

	public int getY2() {
		return Y2;
	}

	public void setY2(int y2) {
		Y2 = y2;
	}
	//Getters and Setters

}