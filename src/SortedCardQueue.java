public class SortedCardQueue extends Queue {
    
    public SortedCardQueue(int size) {
        super(size);
    }
    
    // Add a card to the queue in sorted order
    public void addSorted(Card card) {
        if (isEmpty()) {
            // If queue is empty, just add the card
            enqueue(card);
            return;
        }
        
        // Create temporary queue to store cards while sorting
        Queue tempQueue = new Queue(size() + 1);
        boolean inserted = false;
        
        // Compare and arrange cards
        while (!isEmpty()) {
            Card currentCard = (Card) dequeue();
            
            // If we haven't inserted the new card yet and it should come before the current card
            if (!inserted && compareCards(card, currentCard) <= 0) {
                tempQueue.enqueue(card);
                inserted = true;
            }
            
            // Enqueue the current card
            tempQueue.enqueue(currentCard);
        }
        
        // If we couldn't insert the card yet, it means it should be at the end
        if (!inserted) {
            tempQueue.enqueue(card);
        }
        
        // Move all cards back from temp queue to this queue
        while (!tempQueue.isEmpty()) {
            enqueue(tempQueue.dequeue());
        }
    }
    
    // Helper method to compare cards for sorting
    private int compareCards(Card card1, Card card2) {
        // Special cards come after number cards
        if (card1.isSpecialCard() && card2.isNumberCard()) {
            return 1;
        } else if (card1.isNumberCard() && card2.isSpecialCard()) {
            return -1;
        } else if (card1.isSpecialCard() && card2.isSpecialCard()) {
            // Sort by alphabetical order for special cards
            return card1.getValue().compareTo(card2.getValue());
        } else {
            // Both are number cards, sort numerically
            int num1 = Integer.parseInt(card1.getValue());
            int num2 = Integer.parseInt(card2.getValue());
            return Integer.compare(num1, num2);
        }
    }
    
    // Check if the queue contains a card with the specified value
    public boolean containsCard(String value) {
        boolean found = false;
        Queue tempQueue = new Queue(size());
        
        // Search for the card while moving all cards to a temp queue
        while (!isEmpty()) {
            Card card = (Card) dequeue();
            if (card.getValue().equals(value)) {
                found = true;
            }
            tempQueue.enqueue(card);
        }
        
        // Move all cards back from temp queue to this queue
        while (!tempQueue.isEmpty()) {
            enqueue(tempQueue.dequeue());
        }
        
        return found;
    }
    
    // Count how many cards with the given value exist in the queue
    public int countCards(String value) {
        int count = 0;
        Queue tempQueue = new Queue(size());
        
        // Count cards while moving them to temp queue
        while (!isEmpty()) {
            Card card = (Card) dequeue();
            if (card.getValue().equals(value)) {
                count++;
            }
            tempQueue.enqueue(card);
        }
        
        // Move all cards back from temp queue to this queue
        while (!tempQueue.isEmpty()) {
            enqueue(tempQueue.dequeue());
        }
        
        return count;
    }
    
    // Remove all cards with the specified value
    public Queue removeAllCards(String value) {
        Queue removedCards = new Queue(4); // At most 4 cards of the same value
        Queue tempQueue = new Queue(size());
        
        // Remove cards with the specified value
        while (!isEmpty()) {
            Card card = (Card) dequeue();
            if (card.getValue().equals(value)) {
                removedCards.enqueue(card);
            } else {
                tempQueue.enqueue(card);
            }
        }
        
        // Move all remaining cards back from temp queue to this queue
        while (!tempQueue.isEmpty()) {
            enqueue(tempQueue.dequeue());
        }
        
        return removedCards;
    }
    
    // Display all cards in the queue
    public String displayCards() {
        if (isEmpty()) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        Queue tempQueue = new Queue(size());
        
        while (!isEmpty()) {
            Card card = (Card) dequeue();
            sb.append(card.getValue()).append(" ");
            tempQueue.enqueue(card);
        }
        
        // Restore the queue
        while (!tempQueue.isEmpty()) {
            enqueue(tempQueue.dequeue());
        }
        
        return sb.toString().trim();
    }
} 