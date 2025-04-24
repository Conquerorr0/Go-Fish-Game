public class AIPlayer extends Player {
    private static final int MEMORY_SIZE = 2;
    private Queue lastAskedCards;
    
    // Constructor
    public AIPlayer() {
        super("Computer");
        this.lastAskedCards = new Queue(MEMORY_SIZE);
    }
    
    // Remember the last asked card
    public void rememberAskedCard(String value) {
        if (lastAskedCards.size() >= MEMORY_SIZE) {
            lastAskedCards.dequeue(); // Remove oldest card
        }
        lastAskedCards.enqueue(value);
    }
    
    // Check if a card value is in the memory
    private boolean isInMemory(String value) {
        boolean found = false;
        Queue tempQueue = new Queue(MEMORY_SIZE);
        
        while (!lastAskedCards.isEmpty()) {
            String remembered = (String) lastAskedCards.dequeue();
            if (remembered.equals(value)) {
                found = true;
            }
            tempQueue.enqueue(remembered);
        }
        
        // Restore the memory queue
        while (!tempQueue.isEmpty()) {
            lastAskedCards.enqueue(tempQueue.dequeue());
        }
        
        return found;
    }
    
    // Find the card that the AI has the most of (and isn't in memory)
    public String findBestCardToAsk() {
        String bestCard = null;
        int maxCount = 0;
        
        // Create a temporary queue for traversal
        Queue tempQueue = new Queue(getCards().size());
        
        // Create a temporary queue to keep track of processed card values
        Queue processedValues = new Queue(10);
        
        // We need to traverse all cards to find counts
        while (!getCards().isEmpty()) {
            Card card = (Card) getCards().dequeue();
            
            // Only consider number cards
            if (card.isNumberCard()) {
                String value = card.getValue();
                
                // Check if we've already processed this value
                boolean alreadyProcessed = false;
                Queue tempProcessed = new Queue(processedValues.size());
                
                while (!processedValues.isEmpty()) {
                    String processedValue = (String) processedValues.dequeue();
                    if (processedValue.equals(value)) {
                        alreadyProcessed = true;
                    }
                    tempProcessed.enqueue(processedValue);
                }
                
                // Restore the processed values queue
                while (!tempProcessed.isEmpty()) {
                    processedValues.enqueue(tempProcessed.dequeue());
                }
                
                // Only process this value if we haven't seen it yet
                if (!alreadyProcessed) {
                    processedValues.enqueue(value);
                    
                    // Count this card value in the entire hand
                    int count = countCards(value);
                    
                    // Check if this value is better than current best
                    if (bestCard == null || (count > maxCount && !isInMemory(value))) {
                        bestCard = value;
                        maxCount = count;
                    }
                }
            }
            
            tempQueue.enqueue(card);
        }
        
        // Restore the cards queue
        while (!tempQueue.isEmpty()) {
            getCards().enqueue(tempQueue.dequeue());
        }
        
        // If we found nothing or all options are in memory and we have no different cards
        if (bestCard == null) {
            // Look for any card, even if it's in memory
            tempQueue = new Queue(getCards().size());
            processedValues = new Queue(10);
            
            while (!getCards().isEmpty()) {
                Card card = (Card) getCards().dequeue();
                if (card.isNumberCard()) {
                    String value = card.getValue();
                    
                    // Check if we've already processed this value
                    boolean alreadyProcessed = false;
                    Queue tempProcessed = new Queue(processedValues.size());
                    
                    while (!processedValues.isEmpty()) {
                        String processedValue = (String) processedValues.dequeue();
                        if (processedValue.equals(value)) {
                            alreadyProcessed = true;
                        }
                        tempProcessed.enqueue(processedValue);
                    }
                    
                    // Restore the processed values queue
                    while (!tempProcessed.isEmpty()) {
                        processedValues.enqueue(tempProcessed.dequeue());
                    }
                    
                    // Only process this value if we haven't seen it yet
                    if (!alreadyProcessed) {
                        processedValues.enqueue(value);
                        int count = countCards(value);
                        
                        if (bestCard == null || count > maxCount) {
                            bestCard = value;
                            maxCount = count;
                        }
                    }
                }
                tempQueue.enqueue(card);
            }
            
            // Restore the cards queue
            while (!tempQueue.isEmpty()) {
                getCards().enqueue(tempQueue.dequeue());
            }
        }
        
        return bestCard;
    }
    
    // Find the card that the AI has the least of to discard
    public String findCardToDiscard() {
        String cardToDiscard = null;
        int minCount = Integer.MAX_VALUE;
        
        // Create a temporary queue for traversal
        Queue tempQueue = new Queue(getCards().size());
        
        // Create a temporary queue to keep track of processed card values
        Queue processedValues = new Queue(10);
        
        // Traverse all cards to find the one with the minimum count
        while (!getCards().isEmpty()) {
            Card card = (Card) getCards().dequeue();
            
            // Only consider number cards
            if (card.isNumberCard()) {
                String value = card.getValue();
                
                // Check if we've already processed this value
                boolean alreadyProcessed = false;
                Queue tempProcessed = new Queue(processedValues.size());
                
                while (!processedValues.isEmpty()) {
                    String processedValue = (String) processedValues.dequeue();
                    if (processedValue.equals(value)) {
                        alreadyProcessed = true;
                    }
                    tempProcessed.enqueue(processedValue);
                }
                
                // Restore the processed values queue
                while (!tempProcessed.isEmpty()) {
                    processedValues.enqueue(tempProcessed.dequeue());
                }
                
                // Only process this value if we haven't seen it yet
                if (!alreadyProcessed) {
                    processedValues.enqueue(value);
                    
                    int count = countCards(value);
                    
                    if (count < minCount) {
                        cardToDiscard = value;
                        minCount = count;
                    }
                }
            }
            
            tempQueue.enqueue(card);
        }
        
        // Restore the cards queue
        while (!tempQueue.isEmpty()) {
            getCards().enqueue(tempQueue.dequeue());
        }
        
        return cardToDiscard;
    }
    
    // Decide whether to use the Wild Recovery card
    public boolean shouldUseWildRecovery() {
        if (!hasWildRecovery()) {
            return false;
        }
        
        // Get the top card from the discard pile
        Card topDiscarded = getTopDiscardedCard();
        if (topDiscarded == null) {
            return false;
        }
        
        // Check if AI has the matching card in hand
        return hasCard(topDiscarded.getValue());
    }
} 