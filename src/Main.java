
public class Main {

    public static void main(String[] args) {
        printIntro();
        WordsWithEnemies game = new WordsWithEnemies();
        game.play();

    }

    private static void printIntro() {
        System.out.println("Welcome to Words with Enemies!");
        System.out.println("The object of the game is simple.");
        System.out.println("In this game, you'll be playing against the computer, so you'll first need to pick your difficulty level.");
        System.out.println("Each round, you'll be given a list of random characters, from which you'll have to make a word, using as many characters as you can.");
        System.out.println("The computer will get the same list, and will choose a word based on your difficulty choice.");
        System.out.println("Once you input a valid word, your two words will be fired at one another!");
        System.out.println("Once the words crash together, like characters will cancel each other out.");
        System.out.println("Whoever has the most characters left after the dust settles is the winner of that round.");
        System.out.println("\n"); //print two blank lines.
        System.out.println("Quit at any time by typing \"quit!\"");
    }
}




