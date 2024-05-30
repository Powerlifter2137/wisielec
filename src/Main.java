import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

// Main class that extends Application to create a JavaFX application
public class Main extends Application {
    private HangmanGame game; // Instance of the HangmanGame logic class
    private Label wordDisplay; // Label to display the current state of the word
    private Label message; // Label to display messages to the user
    private TextField guessInput; // TextField for user input of guesses
    private ImageView imageView; // ImageView to display the current hangman image
    private Image[] images; // Array to hold the hangman images
    private Button guessButton; // Button for making guesses
    private Button playAgainButton; // Button to reset the game
    private String[] words = {"obama", "monster", "impreza", "gitara", "guzik", "uber", "woda", "piwo", "alkohol", "papieros", "taniec"}; // Array of words
    private Random random = new Random(); // Random instance to select a word


    @Override
    public void start(Stage primaryStage) {
        // Load hangman images
        images = new Image[7];
        try {
            for (int i = 0; i < images.length; i++) {
                images[i] = new Image(new FileInputStream("assets/png" + (i) + ".png"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Initialize UI components
        wordDisplay = new Label();
        message = new Label("Guess a letter:");
        guessInput = new TextField();
        guessInput.setMaxWidth(100); // Set maximum width for input field
        guessButton = new Button("Guess");
        playAgainButton = new Button("Play Again");
        imageView = new ImageView(); // Display the initial hangman image

        // Set action for the guess button
        guessButton.setOnAction(e -> {
            String input = guessInput.getText().toLowerCase(); // Get input and convert to lowercase
            if (input.length() == 1) { // Ensure input is a single character
                char guess = input.charAt(0);
                // Check the guess and update the message label accordingly
                if (game.isAlreadyGuessed(guess)) {
                    message.setText("Letter already guessed!");
                }
                else if (game.guess(guess)) {
                    message.setText("Correct!");
                } else {
                    message.setText("Incorrect!");
                    imageView.setImage(images[game.getAttempts()]);
                }
                // Update the word display with the current state of the word
                wordDisplay.setText(game.getWordDisplay());
                guessInput.clear(); // Clear the input field

                // Check if the game is over and update the UI accordingly
                if (game.isGameOver()) {
                    if (game.isWon()) {
                        message.setText("Congratulations! You won!");
                    } else {
                        message.setText("Game Over! The word was " + game.getWordToGuess());
                        imageView.setImage(images[game.getAttempts()]);
                    }
                     // Disable input and button after game is over
                     guessButton.setDisable(true);
                     guessInput.setDisable(true);
                     playAgainButton.setDisable(false);
                }
            }
        }); 

        // Set action for the play again button
        playAgainButton.setOnAction(e -> resetGame());

        VBox root = new VBox(10,imageView, wordDisplay, message, guessInput, guessButton, playAgainButton);
        root.setPadding(new Insets(10)); // Set padding around the layout

        // Create and set the scene
        Scene scene = new Scene(root, 400, 500); // Adjusted height to accommodate image
        primaryStage.setTitle("Hangman Game"); // Set the title of the window
        primaryStage.setScene(scene); // Set the scene
        primaryStage.show(); // Show the window

        resetGame();
    }

    // Method to reset the game
    private void resetGame() {
        String wordToGuess = words[random.nextInt(words.length)]; // Select a random word
        game = new HangmanGame(wordToGuess, 6); // Initialize the game with the new word
        wordDisplay.setText(game.getWordDisplay());
        message.setText("Guess a letter:");
        guessInput.clear();
        guessButton.setDisable(false);
        guessInput.setDisable(false);
        playAgainButton.setDisable(true); // Disable play again button until the game is over
        imageView.setImage(images[0]); // Reset to the initial image
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}