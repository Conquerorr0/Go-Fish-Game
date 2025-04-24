import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HighScoreTable {
    private Queue scoreQueue;
    private final int MAX_SIZE = 10;
    private final String FILENAME = "HighScoreTable.txt";
    
    // Constructor
    public HighScoreTable() {
        scoreQueue = new Queue(MAX_SIZE + 1); // +1 for temporary storage when inserting
        loadFromFile();
    }
    
    // Load high scores from file
    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            // Skip the header line
            String line = reader.readLine();
            
            // Read each score entry
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[parts.length - 1]);
                    addScore(name, score, false); // Don't save to file yet
                }
            }
        } catch (IOException e) {
            // File might not exist yet, that's okay
            System.out.println("Yüksek skor tablosu oluşturulacak.");
        } catch (NumberFormatException e) {
            System.out.println("Skor dosyası format hatası: " + e.getMessage());
        }
    }
    
    // Save high scores to file
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            writer.write("High Score Table");
            writer.newLine();
            
            // Create a temporary queue to restore the original queue after writing
            Queue tempQueue = new Queue(scoreQueue.size());
            
            while (!scoreQueue.isEmpty()) {
                ScoreEntry entry = (ScoreEntry) scoreQueue.dequeue();
                writer.write(entry.getName() + "\t" + entry.getScore());
                writer.newLine();
                tempQueue.enqueue(entry);
            }
            
            // Restore the original queue
            while (!tempQueue.isEmpty()) {
                scoreQueue.enqueue(tempQueue.dequeue());
            }
        } catch (IOException e) {
            System.out.println("Yüksek skor tablosu kaydedilemedi: " + e.getMessage());
        }
    }
    
    // Add a new score to the table
    public boolean addScore(String name, int score, boolean saveToFile) {
        ScoreEntry newEntry = new ScoreEntry(name, score);
        boolean added = false;
        boolean isHighScore = false;
        
        // Create a temporary queue to store scores in order
        Queue tempQueue = new Queue(MAX_SIZE + 1);
        
        // If the score queue is empty, just add the new entry
        if (scoreQueue.isEmpty()) {
            tempQueue.enqueue(newEntry);
            added = true;
            isHighScore = true;
        } else {
            // Process each existing score
            while (!scoreQueue.isEmpty()) {
                ScoreEntry currentEntry = (ScoreEntry) scoreQueue.dequeue();
                
                // If we haven't added the new entry yet and its score is higher
                if (!added && newEntry.getScore() > currentEntry.getScore()) {
                    tempQueue.enqueue(newEntry);
                    added = true;
                    isHighScore = true;
                }
                
                // If we haven't added the new entry yet and its score equals current entry's score
                // but name comes after current entry alphabetically
                else if (!added && newEntry.getScore() == currentEntry.getScore() &&
                         newEntry.getName().compareTo(currentEntry.getName()) > 0) {
                    tempQueue.enqueue(currentEntry);
                    tempQueue.enqueue(newEntry);
                    added = true;
                    isHighScore = true;
                } else {
                    tempQueue.enqueue(currentEntry);
                }
            }
            
            // If we haven't added the new entry yet and the table isn't full
            if (!added && tempQueue.size() < MAX_SIZE) {
                tempQueue.enqueue(newEntry);
                added = true;
                isHighScore = true;
            }
        }
        
        // Transfer scores back to the score queue, limiting to MAX_SIZE
        int count = 0;
        while (!tempQueue.isEmpty() && count < MAX_SIZE) {
            scoreQueue.enqueue(tempQueue.dequeue());
            count++;
        }
        
        // Save to file if requested
        if (saveToFile) {
            saveToFile();
        }
        
        return isHighScore;
    }
    
    // Display the high score table
    public void displayTable() {
        System.out.println("High Score Table");
        
        if (scoreQueue.isEmpty()) {
            System.out.println("Henüz yüksek skor yok.");
            return;
        }
        
        // Create a temporary queue to restore the original queue after displaying
        Queue tempQueue = new Queue(scoreQueue.size());
        
        while (!scoreQueue.isEmpty()) {
            ScoreEntry entry = (ScoreEntry) scoreQueue.dequeue();
            System.out.println(entry.getName() + "\t" + entry.getScore());
            tempQueue.enqueue(entry);
        }
        
        // Restore the original queue
        while (!tempQueue.isEmpty()) {
            scoreQueue.enqueue(tempQueue.dequeue());
        }
    }
    
    // Inner class to represent a score entry
    private class ScoreEntry {
        private String name;
        private int score;
        
        public ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }
        
        public String getName() {
            return name;
        }
        
        public int getScore() {
            return score;
        }
    }
} 