
//Musa Özkan 150121058
//Efe Özgen 150121077
//That is an object file to store the taken information and partially handles it
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Cell extends Pane {
	private boolean isstatic;
	private String name;
	private String aspectInfo;
	private Image img;
	private boolean topSide;
	private boolean bottomSide;
	private boolean rightSide;
	private boolean leftSide;

	public Cell() {

	}

	public Cell(Image img) {
		setImg(img);
	}

	/*
	 * Creates a specialized object to create the scene properly , takes the given
	 * informations and stores them, sets the taken image as background
	 */
	public Cell(Image img, boolean isstatic, String name, String aspectInfo, boolean topSide, boolean bottomSide,
			boolean rightSide, boolean leftSide) {
		setName(name);
		setAspectInfo(aspectInfo);
		setIsstatic(isstatic);
		setImg(img);
		setStyle("-fx-border-color: black; -fx-border-width: 4");
		setTopSide(topSide);
		setBottomSide(bottomSide);
		setRightSide(rightSide);
		setLeftSide(leftSide);
		this.setPrefSize(800, 800);
		BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background bGround = new Background(bImg);
		setBackground(bGround);

	}

//Getters and Setters
	public boolean isIsstatic() {
		return isstatic;
	}

	public void setIsstatic(boolean isstatic) {
		this.isstatic = isstatic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAspectInfo() {
		return aspectInfo;
	}

	public void setAspectInfo(String aspectInfo) {
		this.aspectInfo = aspectInfo;
	}

	public boolean isTopSide() {
		return topSide;
	}

	public void setTopSide(boolean topSide) {
		this.topSide = topSide;
	}

	public boolean isBottomSide() {
		return bottomSide;
	}

	public void setBottomSide(boolean bottomSide) {
		this.bottomSide = bottomSide;
	}

	public boolean isRightSide() {
		return rightSide;
	}

	public void setRightSide(boolean rightSide) {
		this.rightSide = rightSide;
	}

	public boolean isLeftSide() {
		return leftSide;
	}

	public void setLeftSide(boolean leftSide) {
		this.leftSide = leftSide;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	//Getters and Setters

}