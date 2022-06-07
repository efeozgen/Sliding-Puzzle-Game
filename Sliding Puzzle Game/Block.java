
//Musa Özkan 150121058
//Efe Özgen 150121077
//That is an object file to store the taken information and partially handles it
import javafx.scene.image.Image;

public class Block {
	private Image img;
	private int Id;
	private String name;
	private String aspectInfo;
	private boolean isstatic;
	private boolean topSide;
	private boolean bottomSide;
	private boolean rightSide;
	private boolean leftSide;

	public Block() {

	}

	/*
	 * takes and stores the info taken and determines which block obbject will get
	 * which image according to its type
	 */
	public Block(int Id, String name, String aspectInfo) {
		setId(Id);
		setName(name);
		setAspectInfo(aspectInfo);
		if (name.equals("Starter")) {
			setIsstatic(true);
			if (aspectInfo.equals("Vertical")) {
				setImg(new Image("images\\starterVertical.png"));
				setBottomSide(true);
			} else {
				setImg(new Image("images\\starterHorizontal.png"));
				setLeftSide(true);
			}
		} else if (name.equals("End")) {
			setIsstatic(true);
			if (aspectInfo.equals("Vertical")) {
				setImg(new Image("images\\endVertical.png"));
				setBottomSide(true);
			} else {
				setImg(new Image("images\\endHorizontal.png"));
				setLeftSide(true);
			}
		} else if (name.equals("PipeStatic")) {
			setIsstatic(true);
			if (aspectInfo.equals("Vertical")) {
				setImg(new Image("images\\staticVertical.png"));
				setTopSide(true);
				setBottomSide(true);
			} else if (aspectInfo.equals("Horizontal")) {
				setImg(new Image("images\\staticHorizontal.png"));
				setLeftSide(true);
				setRightSide(true);
			} else if (aspectInfo.equals("00")) {
				setImg(new Image("images\\static00.png"));
				setLeftSide(true);
				setTopSide(true);
			} else if (aspectInfo.equals("01")) {
				setImg(new Image("images\\static01.png"));
				setTopSide(true);
				setRightSide(true);
			} else if (aspectInfo.equals("10")) {
				setImg(new Image("images\\static10.png"));
				setLeftSide(true);
				setBottomSide(true);
			} else if (aspectInfo.equals("11")) {
				setImg(new Image("images\\static11.png"));
				setBottomSide(true);
				setRightSide(true);
			}
		} else if (name.equals("Pipe")) {
			setIsstatic(false);
			if (aspectInfo.equals("Vertical")) {
				setImg(new Image("images\\pipeVertical.png"));
				setBottomSide(true);
				setTopSide(true);
			} else if (aspectInfo.equals("Horizontal")) {
				setImg(new Image("images\\pipeHorizontal.png"));
				setLeftSide(true);
				setRightSide(true);
			} else if (aspectInfo.equals("00")) {
				setImg(new Image("images\\pipe00.png"));
				setLeftSide(true);
				setTopSide(true);
			} else if (aspectInfo.equals("01")) {
				setImg(new Image("images\\pipe01.png"));
				setTopSide(true);
				setRightSide(true);
			} else if (aspectInfo.equals("10")) {
				setImg(new Image("images\\pipe10.png"));
				setLeftSide(true);
				setBottomSide(true);
			} else if (aspectInfo.equals("11")) {
				setImg(new Image("images\\pipe11.png"));
				setBottomSide(true);
				setRightSide(true);
			}
		} else if ((name.equals("Empty") && (aspectInfo.equals("Free")))) {
			setIsstatic(false);
			setImg(new Image("images\\emptyFree.png"));
		} else if (name.equals("Empty") && (aspectInfo.equals("none"))) {
			setIsstatic(false);
			setImg(new Image("images\\empty.png"));
		}
	}

	// Setters and Getters

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
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

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public boolean isIsstatic() {
		return isstatic;
	}

	public void setIsstatic(boolean isstatic) {
		this.isstatic = isstatic;
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

	// Setters and Getters
}
