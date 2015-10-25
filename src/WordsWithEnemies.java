
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Random;

/**
 * Created by Sean on 1/23/2015.
 */
public class WordsWithEnemies {
    private int playerWins;
    private int computerWins;
    private int difficulty;
    private Scanner scanner;
    private ArrayList<String> dictionary;
    private ArrayList<Character> letters;

    public void play() {
        doInitialStuff();
        String playerWord, AIWord;
        WordsWithEnemiesRound round;

        //This code is to be repeated for many rounds. Just a simple while(true) loop, the player can kill it by typing "quit".
        int roundCt = 0;
        while(true) {
            roundCt++;
            System.out.printf("Round %d. Score -- Player: %d Computer: %d\n", roundCt, playerWins, computerWins);
            genLetters();
            playerWord = getPlayerWord(); //maybe not idiomatic Java, so feel free to give me a better idea for these names.
            AIWord = getAIWord();
            round = new WordsWithEnemiesRound(playerWord, AIWord);
            round.fire();
            if (round.winner.equals("player"))
                playerWins++;
            else if (round.winner.equals("computer"))
                computerWins++;
        }
    }

    private String getPlayerWord() {
        System.out.println("Your letters are: " + formatLetters());
        System.out.println();
        String playerWord;
        while (true) {
            System.out.print("Enter your word: ");
            playerWord = scanner.nextLine();
            if (playerWord.equals("quit!"))
                System.exit(0);
            if(verify(playerWord))
                break;
        }
        return playerWord;
    }

    private Boolean verify(String word) {
        ArrayList<Character> tempLetters = new ArrayList<Character>(letters);
        for (Character c : word.toCharArray()) {
            if (tempLetters.contains(c))
                tempLetters.remove(c);
            else {
                System.out.println("You don't have the characters to make that word!");
                return false;
            }
        } //if we made it this far, the word is valid as far as characters are concerned, but we need to make sure  it's also actually a word.
        if (dictionary.contains(word))
            return true;
        else {
            System.out.println("The dictionary doesn't contain that word.");
            return false;
        }
    }



    private String getAIWord() {
        String curWord;
        ArrayList<Character> lettersUnused;
        ArrayList<String> foundWords = new ArrayList<String>();

        for (String dictWord : dictionary) {
            curWord = "";
            lettersUnused = new ArrayList<Character>(letters);
            for (Character c : dictWord.toCharArray()) {
                if (lettersUnused.contains(c)) {
                    lettersUnused.remove(c);
                    curWord += c;
                } else break;
            }
            if (curWord.equals(dictWord)) //we made a word!
                foundWords.add(curWord);
        }
        foundWords = sortListByLength(foundWords);
        //foundWords = removeSmallWords(foundWords);
        switch (difficulty) {
            case 1: return foundWords.get(1);
            case 2: return foundWords.get(foundWords.size()/4);
            case 3: return foundWords.get(foundWords.size()/2);
            case 4: return foundWords.get(foundWords.size()*3/4);
            case 5: return foundWords.get(foundWords.size()-1);
        }
        return "";
    }

    //not used, but it could be to make level one mildly harder.
    private static ArrayList<String> removeSmallWords(ArrayList<String> words) {
        //This method expects the words to already be sorted, so it's going to go until it finds a three letter word, then stop.
        ArrayList<String> tempWords = new ArrayList<String>(words);
        for (String word : tempWords) {
            if (word.length() <=2)
                words.remove(word);
            else break;
        }
        return words;
    }

    private static ArrayList<String> sortListByLength(ArrayList<String> words) {
        Comparator<String> x = new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                if(o1.length() > o2.length())
                    return 1;

                if(o2.length() > o1.length())
                    return -1;

                return 0;
            }
        };

        Collections.sort(words,  x);
        return words;
    }

    private String formatLetters() {
        String formattedLetters = "";
        for (int i=0; i<letters.size(); i++) {
            formattedLetters+=letters.get(i);
            if (i<letters.size()-1)
                formattedLetters+="-";
        }
        return formattedLetters;
    }

    private void genLetters() {
        //The alphabet will contain all the characters from a-z, as well as the vowels repeated, and rstln repeated thrice to increase their chances of being picked.
        letters.clear();
        Random rand = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyzaeiorstlnrstln";
        for (int i=0; i<15; i++) {
            letters.add(alphabet.charAt(rand.nextInt(alphabet.length())));
        }
        if (!letters.contains('a') && !letters.contains('e') && !letters.contains('i') && !letters.contains('o') && !letters.contains('u'))
            genLetters();
    }

    public void doInitialStuff() {
        computerWins = playerWins = 0;
        letters = new ArrayList<Character>();
        scanner = new Scanner(System.in);
        dictionary = createDictionary();
        chooseDifficulty();

    }

    /*
        Get the difficulty from the user. The try--catch is there to ensure they enter a valid integer without the program dying.
        If they enter a character string, then the Integet.valueOf() will throw an exception, but I won't do anything with it. In that case, difficultyIn's value won't change.
     */
    public void chooseDifficulty() {
        int difficultyIn = -1;
        do {
            System.out.print("Choose a difficulty(1-5): ");
            String line = scanner.nextLine();
            try {
                if (line.equals("quit!"))
                    System.exit(0);
                difficultyIn = Integer.valueOf(line);
            } catch (Exception e) {
            }
        } while (difficultyIn < 1 || difficultyIn > 5);
        difficulty = difficultyIn;
    }

    public static ArrayList<String> createDictionary() {
        try {
            ArrayList<String> dictionary = new ArrayList<String>();
            FileInputStream fstream = new FileInputStream("dictionary.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String word;
            while ((word = br.readLine()) != null) {
                dictionary.add(word);
            }
            return dictionary;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }
}