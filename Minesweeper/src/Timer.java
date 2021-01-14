import javafx.scene.control.Label;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Timer extends Pane {
	private Timeline timeline = new Timeline();
	private int minute = 0;
	private int second = 0;
	private String clock = "";
	private boolean startCounter = false;
	
	Label label = new Label(String.format("%02d:%02d", minute,second));
	
	public Timer() {
		getChildren().add(label);
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), e-> counter()));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	public void counter() {
		if(!startCounter) {
			return;
		}
		if(second >= 59 ) {
			minute++;
			second = 0;
		} else {
			second++;
		}
		clock = String.format("%02d:%02d",minute, second);
		label.setText(clock);
	}
	
	public void setStartCounter(boolean start) {
		this.startCounter = start;
	}
}
