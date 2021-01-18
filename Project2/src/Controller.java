public class Controller {
	private Model model;
	
	public Controller(Model model) {																//Constructor
		this.model = model;
	}
	
	public void leftClick(int x, int y) {															//Handles left click by user.
		if (this.model.getGameStatus().get() || this.model.getTile(x, y).flagOnTile().get()) {
			return;
		} 
		this.model.getTile(x, y).setVisible();
		this.model.reveal(x, y);
		if (model.getTile(x, y).bombOnTile().get() || model.gameWon()) {
			this.model.setGameStatus(true);
		} 
	}
	
	public void rightClick(int x, int y) {															//Handles right click by user.
		if(this.model.getGameStatus().get()) {
			return;
		}
		this.model.getTile(x, y).toggleFlag();
		if (model.gameWon()) {
			this.model.setGameStatus(true);
		}
	}
	
	public int getNeighbours(int x, int y) {														//Returns integer value of number of adjacent bombs.
		return model.getTile(x, y).getNeighbours().intValue();
	}
	
}