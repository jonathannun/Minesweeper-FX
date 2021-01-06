public class Main {
	
	public static void main(String[] args) {
		Board game = new Board(20, 10);																//Constructs a 20 by 10 board
		game.populateBoard(20);																		//Populates board with 20 bombs
		System.out.print(game);																		//Prints board to console
	}
}
