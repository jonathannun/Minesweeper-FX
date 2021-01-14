public class Controller {
	private Model model;
	
	public Controller(Model model) {																//Constructor
		this.model = model;
	}
	
	public void restart(Model model) {
		this.model = model;
	}
	
	public void leftClick(int x, int y) {															//Handles left click by user.
		this.model.getTile(x, y).setVisible();
		this.model.reveal(x, y);
	}
	
	public void rightClick(int x, int y) {															//Handles right click by user.
		this.model.getTile(x, y).toggleFlag();
	}
	
	public int getNeighbours(int x, int y) {														//Returns integer value of number of adjacent bombs.
		return model.getTile(x, y).getNeighbours().intValue();
	}
	
}