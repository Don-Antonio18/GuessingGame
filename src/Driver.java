import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    private static final ArrayList<Integer> numberList = new ArrayList<>();

    private static int readHighscore(String highscorePath) {
        int currentHighscore = Integer.MAX_VALUE;
        try (BufferedReader reader = new BufferedReader(new FileReader(highscorePath))){
            String line = reader.readLine();
            if (line != null) {
                currentHighscore = Integer.parseInt(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading highscore: " + e.getMessage());
        }
        return currentHighscore;
    }
    
    
    public static void main(String[] args) {
       
        String filePath = "src/Numbers.txt";
        
        //! Read from numbers file 
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //? PARSE LINES INTO ARRAYLIST
                numberList.add(Integer.parseInt(line));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not locate file");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Driver driver = new Driver();
        driver.guessingGame();
    }
    
    //! GAME START
    public void guessingGame() {
        

        try (Scanner scanner = new Scanner(System.in)) {
            boolean gameRunning = true;

           
            
            while (gameRunning) {
                //! INITIALISE OBJECTS N VARIABLES
                NumberGuesser guesser = new NumberGuesser();
                ComputerGuesser computerGuesser = new ComputerGuesser(numberList);
                int computerGuess = computerGuesser.guessNum();

                //! START TIMER FOR PROGRAM
                long startTime = System.nanoTime();
                
                System.out.println("I'm thining of a number between 1 and 20.");

                System.out.print("Choose difficulty.The higher the difficulty, the less choices you have.\n[1]Easy (ten guesses)\n[2]Medium (five chances)\n[3]Hard (three changes): ");
                

                //! GET DIFFICULTY LEVEL
                int difficulty = scanner.nextInt();
                guesser.setDifficulty(difficulty);
                
                //! CURRENT GAME ROUND
                boolean roundIsActive = true;

                // WELCOME MESSAGE
                System.out.println("Great! You've selected the " + guesser.

                //todo add error handling for improper difficulty input
                getDifficultyLevel(difficulty) + " difficulty level");
                System.out.print("\nEnter your first guess (0-20): ");
                
                while (roundIsActive && guesser.getGuessesLeft() > 0) {
                    //! PROCESS USER'S INPUT
                    String validation = guesser.processUserInput(scanner, computerGuess);
                    
                    //! IF USER GUESSES WRONG
                    if (validation.equals("incorrect")) {
                        guesser.addStrike();
                        if (guesser.getGuessesLeft() > 0) {
                            System.out.print("\nUnlucky Guess! " + guesser.getGuessesLeft() + " guesses remaining. \n" + "Guess again: ");
                        }

                        //! PROVIDE HINT IF USER IS STRUGGLING
                        if (guesser.getGuessesLeft() < 2) {
                            guesser.getAdditionalHint(computerGuess);
                        }
                    }

                    
                    
                    //! IF USER GUESSES CORRECTLY
                    if (validation.equals("correct")) {

                        //! Stop measuring execution time
                        long endTime = System.nanoTime();

                        long executionTime = (endTime - startTime) / 1000000;

                        System.out.println("🥳 Hurray! You guessed it! 🎊 ");

                        System.out.println("It only took you " + guesser.getNumGuesses() + " guesses...🥱 " + "\nand " + (executionTime / 1000) + " seconds.");

                        String highscorePath =  "src/Highscores.txt";
                        int currentHighscore = readHighscore(highscorePath);

                        if (guesser.getNumGuesses() < currentHighscore) {
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(highscorePath))){
                                writer.write(String.valueOf(guesser.getNumGuesses()));
                                System.out.println("🏆 New highscore! " + guesser.getNumGuesses() + " guesses!");
                                
                            } catch (Exception e) {
                                System.err.println("Error writing new highscore: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Current highscore to beat: " + currentHighscore + " guesses");
                        }

                        roundIsActive = false; // end the round
                                        
                    }
                }
                
                if (guesser.getGuessesLeft() <= 0) {
                    System.out.println("\nGame Over! The computer guessed: " + computerGuess);
                }
                
                //! PROCESS POST GAME CHOICE
                System.out.println();
                System.out.println("Play Again? 👀? [Y]es or [N]o: ");
                
                String userChoice = guesser.processPostGameChoice(scanner);
                
                if (userChoice.equals("n")) {
                    System.out.println("Goodbye! Better luck next time! 🥺");
                    gameRunning = false;
                }
            }
        }
    }
} // class
