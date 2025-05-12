import java.util.ArrayList;
import java.util.Random;

public class ComputerGuesser {
	private int randomIndex;
	private int randNum;
	private ArrayList<Integer> numberList;
	
	public ComputerGuesser(ArrayList<Integer> numberList) {
		this.randomIndex = 0;
		this.randNum = 0;
		this.numberList = numberList;
	}
	
	public int guessNum() {
		Random random = new Random();
		randomIndex = random.nextInt(numberList.size()); // Generates a random index
		randNum = numberList.get(randomIndex); // Gets the number at the random index
		return randNum; // Returns the guessed number
	}

}

