public class Card {
    private String value;
    
    // Constructor
    public Card(String value) {
        this.value = value;
    }
    
    // Get the value of the card
    public String getValue() {
        return value;
    }
    
    // Check if this is a special action card
    public boolean isSpecialCard() {
        return value.equals("J") || value.equals("R") || value.equals("S") || value.equals("W");
    }
    
    // Check if this is a number card
    public boolean isNumberCard() {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    // String representation of the card
    @Override
    public String toString() {
        return value;
    }
    
    // Check if two cards have the same value
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Card card = (Card) obj;
        return value.equals(card.getValue());
    }
} 