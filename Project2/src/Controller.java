public class Controller {
	private Model model;
	
	public Controller(Model model) {																//Constructor
		this.model = model;
	}
	
	public void leftClick(int x, int y) {															//Handles left click by user.
		if (this.model.getGameStatus().intValue() != 0 || this.model.getTile(x, y).flagOnTile().get()) {
			return;
		} 
		this.model.getTile(x, y).setVisible();
		this.model.reveal(x, y);
		if (model.getTile(x, y).bombOnTile().get()) {
			this.model.setGameStatus(1);
		} else if (model.gameWon()) {
			this.model.setGameStatus(2);
		}
	}
	
	public void rightClick(int x, int y) {															//Handles right click by user.
		if(this.model.getGameStatus().intValue() != 0) {
			return;
		}
		this.model.getTile(x, y).toggleFlag();
		if (model.gameWon()) {
			this.model.setGameStatus(2);
		}
	}
	
	public int getNeighbours(int x, int y) {														//Returns integer value of number of adjacent bombs.
		return model.getTile(x, y).getNeighbours().intValue();
	}
	
	public Model updateModel(int w, int h, int mines) {
		this.model = new Model(w, h, mines);
		return this.model;
	}
}