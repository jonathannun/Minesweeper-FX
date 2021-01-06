public class Main {
	
	public static void main(String[] args) {
		Board game = new Board(20, 10);
		game.populateBoard(20);
		System.out.print(game);
	}
}
