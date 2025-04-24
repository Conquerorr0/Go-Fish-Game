import java.util.Random;
import java.util.Scanner;

public class Game {
    private Player humanPlayer;
    private AIPlayer computerPlayer;
    private Queue deckQueue;
    private HighScoreTable highScoreTable;
    private Random random;
    private Scanner scanner;
    private Player currentPlayer;
    private boolean gameOver;
    private boolean repeatTurn;
    
    // Delay durations (milliseconds) for different information types
    private static final int SHORT_DELAY = 1000;     // 1 second for short messages
    private static final int MEDIUM_DELAY = 2000;    // 2 seconds for medium messages
    private static final int ACTION_DELAY = 3000;    // 3 seconds for actions (draw card, use special card)
    private static final int TURN_CHANGE_DELAY = 4000; // 4 seconds for turn changes and game status
    private static final int GAME_END_DELAY = 5000;  // 5 seconds for end game information
    
    // Constructor
    public Game() {
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.deckQueue = new Queue(40); // Maximum number of cards possible
        this.highScoreTable = new HighScoreTable();
        this.gameOver = false;
        this.repeatTurn = false;
    }
    
    // Delay methods for different information types
    private void shortDelay() {
        try {
            Thread.sleep(SHORT_DELAY);
        } catch (InterruptedException e) {
            // Continue if interrupted
        }
    }
    
    private void mediumDelay() {
        try {
            Thread.sleep(MEDIUM_DELAY);
        } catch (InterruptedException e) {
            // Continue if interrupted
        }
    }
    
    private void actionDelay() {
        try {
            Thread.sleep(ACTION_DELAY);
        } catch (InterruptedException e) {
            // Continue if interrupted
        }
    }
    
    private void turnChangeDelay() {
        try {
            Thread.sleep(TURN_CHANGE_DELAY);
        } catch (InterruptedException e) {
            // Continue if interrupted
        }
    }
    
    private void gameEndDelay() {
        try {
            Thread.sleep(GAME_END_DELAY);
        } catch (InterruptedException e) {
            // Continue if interrupted
        }
    }
    
    // Initialize the game
    public void init() {
        // Get player name
        System.out.println("What's your name?");
        System.out.print("You: ");
        String playerName = scanner.nextLine();
        
        // Create players
        this.humanPlayer = new Player(playerName);
        this.computerPlayer = new AIPlayer();
        
        // Initialize deck
        initializeDeck();
        
        // Deal cards to players
        dealCards();
        
        // Randomly determine who starts
        boolean humanStarts = random.nextBoolean();
        if (humanStarts) {
            currentPlayer = humanPlayer;
            System.out.println("Who will start the game is determined now!.. The game starts with \"" + humanPlayer.getName() + "\"!");
        } else {
            currentPlayer = computerPlayer;
            System.out.println("Who will start the game is determined now!.. The game starts with Computer!");
        }
        
        turnChangeDelay(); // Longer delay for game start
    }
    
    // Initialize the deck
    private void initializeDeck() {
        // Create number cards (4 copies each of cards numbered 1 to 6)
        for (int value = 1; value <= 6; value++) {
            for (int copy = 0; copy < 4; copy++) {
                deckQueue.enqueue(new Card(Integer.toString(value)));
            }
        }
        
        // Shuffle the deck
        shuffleDeck();
    }
    
    // Shuffle the deck
    private void shuffleDeck() {
        // Create a temporary array to hold cards
        int size = deckQueue.size();
        Object[] tempArray = new Object[size];
        
        // Move all cards to the array
        for (int i = 0; i < size; i++) {
            tempArray[i] = deckQueue.dequeue();
        }
        
        // Shuffle the array
        for (int i = size - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Object temp = tempArray[i];
            tempArray[i] = tempArray[j];
            tempArray[j] = temp;
        }
        
        // Put cards back into the queue
        for (int i = 0; i < size; i++) {
            deckQueue.enqueue(tempArray[i]);
        }
    }
    
    // Add special action cards to the deck
    private void addSpecialCards() {
        deckQueue.enqueue(new Card("J")); // Joker
        deckQueue.enqueue(new Card("R")); // Repeat
        deckQueue.enqueue(new Card("S")); // Skip
        deckQueue.enqueue(new Card("W")); // Wild Recovery
        
        // Shuffle the deck again after adding special cards
        shuffleDeck();
    }
    
    // Deal initial cards to players
    private void dealCards() {
        // Deal 7 cards to each player
        for (int i = 0; i < 7; i++) {
            if (!deckQueue.isEmpty()) {
                humanPlayer.addCard((Card) deckQueue.dequeue());
            }
            if (!deckQueue.isEmpty()) {
                computerPlayer.addCard((Card) deckQueue.dequeue());
            }
        }
        
        // Add special cards to the deck after dealing
        addSpecialCards();
    }
    
    // Display game state
    private void displayGameState() {
        if (currentPlayer == humanPlayer) {
            System.out.println("\nTurn: " + humanPlayer.getName());
        } else {
            System.out.println("\nTurn: Computer");
        }
        System.out.println("Table");
        
        // Display deck
        StringBuilder deckStr = new StringBuilder();
        Queue tempDeck = new Queue(deckQueue.size());
        while (!deckQueue.isEmpty()) {
            Card card = (Card) deckQueue.dequeue();
            deckStr.append(card.getValue()).append(" ");
            tempDeck.enqueue(card);
        }
        while (!tempDeck.isEmpty()) {
            deckQueue.enqueue(tempDeck.dequeue());
        }
        System.out.println(deckStr.toString().trim());
        
        // Display player hands with correct spacing and alignment
        // Create base labels for both players
        String computerBaseLabel = "Computer";
        String humanBaseLabel = humanPlayer.getName();
        
        // Determine the longer label
        int maxLabelLength = Math.max(computerBaseLabel.length(), humanBaseLabel.length());
        
        // Create properly formatted labels with the same width
        String humanLabel = humanBaseLabel + ": " + " ".repeat(Math.max(0, maxLabelLength - humanBaseLabel.length()));
        String computerLabel = computerBaseLabel + ": " + " ".repeat(Math.max(0, maxLabelLength - computerBaseLabel.length()));
        
        System.out.println(humanLabel + humanPlayer.displayCards() + "\tset:" + humanPlayer.getSetCount() + "\tScore:" + humanPlayer.getScore());
        System.out.println(computerLabel + computerPlayer.displayCards() + "\tset:" + computerPlayer.getSetCount() + "\tScore:" + computerPlayer.getScore());
        
        // Display discarded cards
        System.out.println("DiscardedCards (" + humanPlayer.getName() + "): " + humanPlayer.displayDiscardedCards());
        System.out.println("DiscardedCards (Computer): " + computerPlayer.displayDiscardedCards());
        
        turnChangeDelay(); // Long delay for game state display
    }
    
    // Display final game state
    private void displayFinalGameState() {
        // Create base labels for both players
        String computerBaseLabel = "Computer";
        String humanBaseLabel = humanPlayer.getName();
        
        // Determine the longer label
        int maxLabelLength = Math.max(computerBaseLabel.length(), humanBaseLabel.length());
        
        // Create properly formatted labels with the same width
        String humanLabel = humanBaseLabel + ": " + " ".repeat(Math.max(0, maxLabelLength - humanBaseLabel.length()));
        String computerLabel = computerBaseLabel + ": " + " ".repeat(Math.max(0, maxLabelLength - computerBaseLabel.length()));
        
        System.out.println(humanLabel + humanPlayer.displayCards() + "\tset:" + humanPlayer.getSetCount() + "\tScore:" + humanPlayer.getScore() + "\tDiscardedCards (" + humanPlayer.getName() + "): " + humanPlayer.displayDiscardedCards());
        System.out.println(computerLabel + computerPlayer.displayCards() + "\tset:" + computerPlayer.getSetCount() + "\tScore:" + computerPlayer.getScore() + "\tDiscardedCards (Computer): " + computerPlayer.displayDiscardedCards());
        
        gameEndDelay(); // Very long delay for final state
    }
    
    // Play the game
    public void play() {
        while (!gameOver) {
            // Display game state
            displayGameState();
            
            // Handle current player's turn
            if (currentPlayer == humanPlayer) {
                handleHumanTurn();
            } else {
                handleComputerTurn();
            }
            
            // Check for game over conditions
            checkGameOver();
            
            // Switch players if not repeating turn
            if (!repeatTurn) {
                currentPlayer = (currentPlayer == humanPlayer) ? computerPlayer : humanPlayer;
            } else {
                repeatTurn = false;
            }
            
            turnChangeDelay(); // Long delay for turn change
        }
        
        // Display final game state
        System.out.println("Game is over (There is no card left in the deck).");
        displayFinalGameState();
        
        // Determine winner
        determineWinner();
        
        // Ask to play again
        System.out.println("\nPlay\tagain?");
    }
    
    // Handle human player's turn
    private void handleHumanTurn() {
        // Check if player has Wild Recovery
        if (humanPlayer.hasWildRecovery()) {
            handleWildRecovery(humanPlayer);
            return;
        }
        
        // Ask player for a card to request
        System.out.print(humanPlayer.getName() + " asks: ");
        String requestedCard = scanner.nextLine();
        
        // Validate the request
        while (!humanPlayer.hasCard(requestedCard)) {
            System.out.println("Hatalı kart! Elinizdeki bir kartı seçmelisiniz.");
            requestedCard = scanner.nextLine();
        }
        
        System.out.println(humanPlayer.getName() + " asks: " + requestedCard);
        shortDelay(); // Short delay for request confirmation
        
        // Opponent has the requested card
        if (computerPlayer.hasCard(requestedCard)) {
            // Transfer all cards from opponent to player
            Queue transferredCards = computerPlayer.removeAllCards(requestedCard);
            while (!transferredCards.isEmpty()) {
                humanPlayer.addCard((Card) transferredCards.dequeue());
            }
            
            // Check for complete set
            checkForCompleteSet(humanPlayer, requestedCard);
            
            // Check if player needs to discard
            handleExcessCards(humanPlayer);
        } else {
            // Opponent doesn't have the card, draw from deck
            System.out.println("Computer says \"Draw from the Table!\"");
            mediumDelay(); // Medium delay before drawing
            
            if (deckQueue.isEmpty()) {
                System.out.println("Deste boş, kart çekilemedi!");
                return;
            }
            
            Card drawnCard = (Card) deckQueue.dequeue();
            if (drawnCard.isSpecialCard()) {
                System.out.println(humanPlayer.getName() + " draws a \"" + drawnCard.getValue() + "\" card!");
            } else {
                System.out.println(humanPlayer.getName() + " draws a \"" + drawnCard.getValue() + "\" card!");
            }
            actionDelay(); // Longer delay for card draw
            
            // Handle special action cards
            if (drawnCard.isSpecialCard()) {
                handleSpecialCard(humanPlayer, drawnCard);
            } else {
                // Add the drawn card to player's hand
                humanPlayer.addCard(drawnCard);
                
                // Check for complete set
                checkForCompleteSet(humanPlayer, drawnCard.getValue());
                
                // Check if player needs to discard
                handleExcessCards(humanPlayer);
            }
        }
    }
    
    // Handle computer player's turn
    private void handleComputerTurn() {
        // Check if AI has Wild Recovery
        if (computerPlayer.hasWildRecovery()) {
            handleWildRecovery(computerPlayer);
            return;
        }
        
        // AI decides which card to ask for
        String requestedCard = computerPlayer.findBestCardToAsk();
        computerPlayer.rememberAskedCard(requestedCard);
        System.out.println("Computer asks: " + requestedCard);
        shortDelay(); // Short delay for request
        
        // Human player has the requested card
        if (humanPlayer.hasCard(requestedCard)) {
            // Transfer all cards from human to AI
            Queue transferredCards = humanPlayer.removeAllCards(requestedCard);
            while (!transferredCards.isEmpty()) {
                computerPlayer.addCard((Card) transferredCards.dequeue());
            }
            
            // Check for complete set
            checkForCompleteSet(computerPlayer, requestedCard);
            
            // Check if AI needs to discard
            handleExcessCards(computerPlayer);
        } else {
            // Human doesn't have the card, draw from deck
            System.out.println(humanPlayer.getName() + " says \"Draw from the Table!\"");
            mediumDelay(); // Medium delay before drawing
            
            if (deckQueue.isEmpty()) {
                System.out.println("Deste boş, kart çekilemedi!");
                return;
            }
            
            Card drawnCard = (Card) deckQueue.dequeue();
            System.out.println("Computer draws " + drawnCard.getValue());
            actionDelay(); // Longer delay for card draw
            
            // Handle special action cards
            if (drawnCard.isSpecialCard()) {
                handleSpecialCard(computerPlayer, drawnCard);
            } else {
                // Add the drawn card to AI's hand
                computerPlayer.addCard(drawnCard);
                
                // Check for complete set
                checkForCompleteSet(computerPlayer, drawnCard.getValue());
                
                // Check if AI needs to discard
                handleExcessCards(computerPlayer);
            }
        }
    }
    
    // Handle Wild Recovery
    private void handleWildRecovery(Player player) {
        if (player == humanPlayer) {
            System.out.println("Reminder! You have a W card. You can also request cards from your trash pile. Do you want to use your W card? (Y/N)");
            System.out.print("Your choice: ");
            String choice = scanner.nextLine();
            
            if (choice.equalsIgnoreCase("Y")) {
                Card topCard = player.getTopDiscardedCard();
                if (topCard != null) {
                    System.out.println(topCard.getValue() + " was drawn from discarded cards");
                    player.addCard(topCard);
                    // Remove top card from discard pile
                    player.getDiscardedCards().pop();
                    actionDelay(); // Action delay for card recovery
                    
                    // Check for complete set
                    checkForCompleteSet(player, topCard.getValue());
                    
                    // Check if player needs to discard
                    handleExcessCards(player);
                } else {
                    System.out.println("Çöp yığınınız boş!");
                }
            }
        } else {
            // AI decides whether to use Wild Recovery
            if (computerPlayer.shouldUseWildRecovery()) {
                Card topCard = player.getTopDiscardedCard();
                System.out.println("Computer uses Wild Recovery to get " + topCard.getValue() + " from its discard pile");
                player.addCard(topCard);
                // Remove top card from discard pile
                player.getDiscardedCards().pop();
                actionDelay(); // Action delay for card recovery
                
                // Check for complete set
                checkForCompleteSet(player, topCard.getValue());
                
                // Check if AI needs to discard
                handleExcessCards(player);
            }
        }
        
        // Reset Wild Recovery flag
        player.setWildRecovery(false);
    }
    
    // Handle special action cards
    private void handleSpecialCard(Player player, Card card) {
        String cardValue = card.getValue();
        
        switch (cardValue) {
            case "J": // Joker
                player.addScore(5);
                System.out.println("5 points added!");
                actionDelay(); // Action delay for special card
                
                if (player == humanPlayer) {
                    System.out.println("It's a chance to ask for any rank from the opponent's deck");
                    System.out.print(player.getName() + " asks: ");
                    String requestedCard = scanner.nextLine();
                    System.out.println(player.getName() + " asks: " + requestedCard);
                    mediumDelay(); // Medium delay for request
                    
                    // Transfer cards from opponent
                    if (computerPlayer.hasCard(requestedCard)) {
                        Queue transferredCards = computerPlayer.removeAllCards(requestedCard);
                        while (!transferredCards.isEmpty()) {
                            player.addCard((Card) transferredCards.dequeue());
                        }
                        
                        // Check for complete set
                        checkForCompleteSet(player, requestedCard);
                        
                        // Check if player needs to discard
                        handleExcessCards(player);
                    } else {
                        System.out.println("Computer says \"I don't have that card!\"");
                    }
                } else {
                    // AI chooses a card
                    String requestedCard = computerPlayer.findBestCardToAsk();
                    computerPlayer.rememberAskedCard(requestedCard);
                    System.out.println("Computer asks: " + requestedCard);
                    mediumDelay(); // Medium delay for AI request
                    
                    // Transfer cards from human
                    if (humanPlayer.hasCard(requestedCard)) {
                        Queue transferredCards = humanPlayer.removeAllCards(requestedCard);
                        while (!transferredCards.isEmpty()) {
                            player.addCard((Card) transferredCards.dequeue());
                        }
                        
                        // Check for complete set
                        checkForCompleteSet(player, requestedCard);
                        
                        // Check if AI needs to discard
                        handleExcessCards(player);
                    } else {
                        System.out.println(humanPlayer.getName() + " says \"I don't have that card!\"");
                    }
                }
                break;
                
            case "R": // Repeat
                player.addScore(5);
                System.out.println("5 points added!");
                System.out.println(player.getName() + " will play twice more.");
                actionDelay(); // Action delay for special card
                repeatTurn = true;
                break;
                
            case "S": // Skip
                player.addScore(-5);
                System.out.println("5 points decrease!");
                System.out.println("It is now opponent's turn.");
                actionDelay(); // Action delay for special card
                // Skip is already handled by not setting repeatTurn
                break;
                
            case "W": // Wild Recovery
                player.addScore(5);
                System.out.println("5 points added!");
                System.out.println("It's a chance to check the top card of the discard pile instead of requesting from the opponent in the next turn.");
                actionDelay(); // Action delay for special card
                player.setWildRecovery(true);
                break;
        }
    }
    
    // Check for complete set (4 cards of the same value)
    private void checkForCompleteSet(Player player, String cardValue) {
        if (player.hasCompleteSet(cardValue)) {
            System.out.println(cardValue + " " + cardValue + " " + cardValue + " " + cardValue);
            player.removeAllCards(cardValue);
            player.incrementSetCount();
            player.addScore(10);
            System.out.println("10 points added!");
            actionDelay(); // Action delay for set completion
        }
    }
    
    // Handle excess cards (more than 9 cards)
    private void handleExcessCards(Player player) {
        int excessCount = player.getExcessCardCount();
        
        if (excessCount > 0) {
            player.addScore(-10);
            System.out.println("10 points decrease!");
            System.out.println("ATTENTION! You should discard " + excessCount + " card from your deck.");
            mediumDelay(); // Medium delay for discarding notification
            
            if (player == humanPlayer) {
                // Human player chooses cards to discard
                for (int i = 1; i <= excessCount; i++) {
                    System.out.print("Which card will be discarded? (" + i + "): ");
                    String cardToDiscard = scanner.nextLine();
                    
                    // Validate the discard
                    while (!player.hasCard(cardToDiscard)) {
                        System.out.println("Hatalı kart! Elinizdeki bir kartı seçmelisiniz.");
                        cardToDiscard = scanner.nextLine();
                    }
                    
                    // Remove and discard the card
                    Queue removedCards = player.removeAllCards(cardToDiscard);
                    player.discardCard((Card) removedCards.dequeue());
                    
                    // Put back any extra cards of the same value
                    while (!removedCards.isEmpty()) {
                        player.addCard((Card) removedCards.dequeue());
                    }
                }
            } else {
                // AI chooses cards to discard
                for (int i = 0; i < excessCount; i++) {
                    String cardToDiscard = computerPlayer.findCardToDiscard();
                    System.out.println("Computer discards " + cardToDiscard);
                    
                    // Remove and discard the card
                    Queue removedCards = player.removeAllCards(cardToDiscard);
                    player.discardCard((Card) removedCards.dequeue());
                    
                    // Put back any extra cards of the same value
                    while (!removedCards.isEmpty()) {
                        player.addCard((Card) removedCards.dequeue());
                    }
                }
            }
        }
    }
    
    // Check if the game is over
    private void checkGameOver() {
        // Game is over if the deck is empty
        if (deckQueue.isEmpty()) {
            gameOver = true;
        }
        
        // Game is over if all sets have been formed
        int totalSets = humanPlayer.getSetCount() + computerPlayer.getSetCount();
        if (totalSets == 6) { // 6 sets possible with cards 1-6
            gameOver = true;
        }
    }
    
    // Determine the winner and update high score table
    private void determineWinner() {
        if (humanPlayer.getScore() > computerPlayer.getScore()) {
            System.out.println(humanPlayer.getName() + " won!");
            // Update high score table only if human wins
            highScoreTable.addScore(humanPlayer.getName(), humanPlayer.getScore(), true);
        } else if (computerPlayer.getScore() > humanPlayer.getScore()) {
            System.out.println("Computer won!");
            // No high score update for computer
        } else {
            System.out.println("It's a tie!");
        }
        
        // Display high score table
        highScoreTable.displayTable();
        
        gameEndDelay(); // Very long delay for game end
    }
} 