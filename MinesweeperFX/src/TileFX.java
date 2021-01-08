import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TileFX extends StackPane {
	Text message;
	Rectangle button;
	ImageView mine;
	ImageView flag;
	int size;
	boolean testFlag = false;

	public TileFX(int Size, boolean isMine)
	{
		size = Size;
		message = new Text();
		
		button = new Rectangle(30,30);
		if(isMine) {
			mine = new ImageView("Images/Mine.png");
			mine.setFitHeight(size);
			mine.setFitWidth(size);
			getChildren().add(mine);
		} else {
			getChildren().add(message);
		}
		
		button.setFill(Color.GRAY);
		button.setStroke(Color.BLACK);
		
		
		getChildren().add(button);
		
		
		button.setOnMouseClicked(e -> {
			if(!Main.testLost) { //Spageti code.... FIX DET!!!!!!!!!!!!!!!!!!
			switch (e.getButton()) {
				case PRIMARY:
					clicked();
					if(isMine) {
						System.out.println("bomb");
						Main.testLost = true;
					}
					break;
				case SECONDARY:
					setFlag(testFlag);
					System.out.println("flag");
					break;
			}
			}
		});
	}
	
	public StackPane getTileFX() 
	{
		return this;
	}
	
	/*
	public void setMine() 
	{
		//getChildren().removeAll();
		mine = new ImageView("Images/Mine.png");
		mine.setFitHeight(size);
		mine.setFitWidth(size);
		
		getChildren().set(0,mine);
		System.out.println("imgae added");
		
		//getChildren().add(button);
	}
	*/
	
	public void setNumber(int numb) 
	{
		if(numb != 0) {
			message.setText(String.valueOf(numb));
		}
	}
	
	public void clicked()
	{
		for(int i = 0; i < getChildren().size(); i++) 
		{
			if(getChildren().get(i).equals(flag)) {
				return;
			}
		}
		button.setFill(null);
	}
	
	public void setFlag(boolean hasFlag) 
	{
		if (!hasFlag) {
			System.out.println("has no flag");
			flag = new ImageView("Images/Flag.png");
			flag.setFitHeight(size);
			flag.setFitWidth(size);
			getChildren().add(flag);
			testFlag = true;
		} else {
			removeFlag();
			testFlag = false;
		}
	}
	
	
	public void removeFlag()
	{
		for(int i = 0; i < getChildren().size(); i++)
		{
			if(getChildren().get(i).equals(flag)) 
			{
				getChildren().remove(i);
			}
		}		
	}
}