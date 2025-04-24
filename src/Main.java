import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean playAgain = true;
		
		while (playAgain) {
			// Create and initialize game
			Game game = new Game();
			game.init();
			
			// Play the game
			game.play();
			
			// Ask if player wants to play again
			System.out.print("Play Again? ");
			String answer = scanner.nextLine();
			playAgain = answer.equalsIgnoreCase("E") || answer.equalsIgnoreCase("Y");
		}
		
		scanner.close();
	}

}
