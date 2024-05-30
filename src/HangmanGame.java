import java.util.HashSet;
import java.util.Set;

// Class to handle the logic of the Hangman game
public class HangmanGame {
    private String wordToGuess; // The word to guess in the game
    private Set<Character> guessedLetters; // Set of letters guessed by the user
    private int maxAttempts; // Maximum number of incorrect attempts allowed
    private int attempts; // Current number of incorrect attempts

    // Constructor to initialize the game with the word to guess and max attempts
    public HangmanGame(String word, int maxAttempts) {
        this.wordToGuess = word;
        this.maxAttempts = maxAttempts;
        this.guessedLetters = new HashSet<>();
        this.attempts = 0;
    }

    // Method to process a guessed letter
    public boolean guess(char letter) {
        guessedLetters.add(letter); // Add the guessed letter to the set
        if (!wordToGuess.contains(String.valueOf(letter))) {
            attempts++; // Increment attempts if the guess is incorrect
            return false;
        }
        return true; // Return true if the guess is correct
    }

     // Method to get the number of incorrect attempts
     public int getAttempts() {
        return attempts;
    }

    // Method to check if a letter has already been guessed
    public boolean isAlreadyGuessed(char letter) {
        return guessedLetters.contains(letter);
    }

    // Method to get the current display of the word with guessed letters and underscores
    public String getWordDisplay() {
        StringBuilder display = new StringBuilder();
        for (char c : wordToGuess.toCharArray()) {
            if (guessedLetters.contains(c)) {
                display.append(c); // Add guessed letter to display
            } else {
                display.append('_'); // Add underscore for unguessed letters
            }
            display.append(' '); // Add space between characters for readability
        }
        return display.toString();
    }

    public String getWordToGuess(){
        return wordToGuess;
    }

    // Method to check if the game is over (either won or max attempts reached)
    public boolean isGameOver() {
        return attempts >= maxAttempts || getWordDisplay().indexOf('_') == -1;
    }

    // Method to check if the game is won (no underscores left in the display)
    public boolean isWon() {
        return getWordDisplay().indexOf('_') == -1;
    }
}