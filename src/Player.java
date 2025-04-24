public class Player {
    private String name;
    private SortedCardQueue cards;
    private Stack discardedCards;
    private int score;
    private int setCount;
    private boolean hasWildRecovery;
    
    // Constructor
    public Player(String name) {
        this.name = name;
        this.cards = new SortedCardQueue(20); // Max possible cards
        this.discardedCards = new Stack(20);
        this.score = 0;
        this.setCount = 0;
        this.hasWildRecovery = false;
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public SortedCardQueue getCards() {
        return cards;
    }
    
    public Stack getDiscardedCards() {
        return discardedCards;
    }
    
    public int getScore() {
        return score;
    }
    
    public int getSetCount() {
        return setCount;
    }
    
    public boolean hasWildRecovery() {
        return hasWildRecovery;
    }
    
    // Setters
    public void setWildRecovery(boolean hasWildRecovery) {
        this.hasWildRecovery = hasWildRecovery;
    }
    
    // Add a card to player's hand
    public void addCard(Card card) {
        cards.addSorted(card);
    }
    
    // Discard a card to player's discard pile
    public void discardCard(Card card) {
        discardedCards.push(card);
    }
    
    // Add points to player's score
    public void addScore(int points) {
        this.score += points;
    }
    
    // Increment set count
    public void incrementSetCount() {
        this.setCount++;
    }
    
    // Check if player has a card with the specified value
    public boolean hasCard(String value) {
        return cards.containsCard(value);
    }
    
    // Count how many cards with the specified value the player has
    public int countCards(String value) {
        return cards.countCards(value);
    }
    
    // Remove all cards with the specified value from player's hand
    public Queue removeAllCards(String value) {
        return cards.removeAllCards(value);
    }
    
    // Check if player has a complete set of 4 cards with the same value
    public boolean hasCompleteSet(String value) {
        return countCards(value) == 4;
    }
    
    // Check if player needs to discard cards (hand size > 9)
    public int getExcessCardCount() {
        int handSize = cards.size();
        return handSize > 9 ? handSize - 9 : 0;
    }
    
    // Get the top card from the discard pile
    public Card getTopDiscardedCard() {
        if (discardedCards.isEmpty()) {
            return null;
        }
        return (Card) discardedCards.peek();
    }
    
    // Display player's cards
    public String displayCards() {
        return cards.displayCards();
    }
    
    // Display player's discarded cards
    public String displayDiscardedCards() {
        if (discardedCards.isEmpty()) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        Stack tempStack = new Stack(discardedCards.size());
        
        while (!discardedCards.isEmpty()) {
            Card card = (Card) discardedCards.pop();
            sb.append(card.getValue()).append(" ");
            tempStack.push(card);
        }
        
        // Restore the stack
        while (!tempStack.isEmpty()) {
            discardedCards.push(tempStack.pop());
        }
        
        return sb.toString().trim();
    }
} 