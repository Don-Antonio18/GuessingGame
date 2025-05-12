import java.util.Scanner;

public class NumberGuesser {
    private int numGuesses;
    private int guessesLeft;
    private final static int easy = 10;
    private final static int medium = 5;
    private final static int hard = 3;


    public NumberGuesser() {
        this.numGuesses = 0;
        this.guessesLeft = 5;
    }

    public void setDifficulty(int difficulty) {
       switch (difficulty) {
            case 1:
                this.guessesLeft = easy;
                break;
            case 2:
                this.guessesLeft = medium;
                break;
            case 3:
                this.guessesLeft = hard;
                break;
            default:
                System.out.println("Invalid difficulty level. Defaulting to 5 guesses.");
                this.guessesLeft = 5;
        }
    }

    public String getDifficultyLevel(int difficulty) {
        String difficultyLevel = "";
        switch (difficulty) {
            case 1:
                difficultyLevel = "EASY";
                break;
            case 2:
                difficultyLevel = "MEDIUM";
                break;
            case 3:
                difficultyLevel = "HARD";
                break;

            default:
                break;
        }
        return difficultyLevel;
    }
    
    public void setNumGuesses(int numGuesses) {
        this.numGuesses = numGuesses;
    }
    public int getNumGuesses() {
        return numGuesses;
    }
    
    public int getGuessesLeft(){
        return guessesLeft;
    }

    public void addStrike() {
        numGuesses ++;
        guessesLeft --;
    }
    
    

    public int getUserInput(Scanner scanner) {
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= 0 && input <= 20) {
                    return input;
                } else {
                    System.out.println("Please enter a number between 0 and 20");
                    scanner.nextLine(); // clear the buffer
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number between 0 and 20");
                scanner.nextLine(); // clear the buffer
            }
        }
    }
    
    public String processPostGameChoice(Scanner scanner) {
        return scanner.next().toLowerCase();
    }
    
    public String processUserInput(Scanner scanner, int computerGuess)
    {
        int userGuess = this.getUserInput(scanner);
        String validation = "";
        //! WHEN USER INPUTS INCORRECT NUMBER
        if (userGuess != computerGuess)
        {
            validation = "incorrect";

            // Provide hints based on user input 
            if (userGuess < computerGuess) {
                System.out.println("Hint: Try a higher number!");
            } else {
                System.out.println("Hint: Try a lower number!");
            }
        }
        
        //! WHEN USER INPUTS CORRECT NUMBER
        if (userGuess == computerGuess)
        {
            validation = "correct";
        }
        
        return validation;

    }

    public String getAdditionalHint(int computerGuess) {
        String hint = "";

        if (computerGuess % 2 > 0) {
            hint = "Here's another hint: it's an odd number!";
        }
        if (computerGuess % 2 == 0 ) {
            hint = "Here's another hint: it's an even number!";
        }
        return hint;
    }

}
