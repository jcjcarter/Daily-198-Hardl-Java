
import java.lang.StringBuilder;

public class WordsWithEnemiesRound {
    private StringBuilder playerWordRemainder, AIWordRemainder;
    private final String playerWord, AIWord;
    public String winner;

    public WordsWithEnemiesRound(String player, String computer) {
        this.playerWord = player;
        this.AIWord = computer;

        this.playerWordRemainder = new StringBuilder(this.playerWord);
        this.AIWordRemainder = new StringBuilder(this.AIWord);
    }

    public void fire() {
        //iterate over the first word (longest), and compare each character to AIWord. If a match is found, remove it from both words.
        //Rewind i in the event of a removal from playerWord.
        for (int i = 0; i < playerWordRemainder.length(); i++) {
            for (int j = 0; j < AIWordRemainder.length(); j++) {
                if (playerWordRemainder.charAt(i) == AIWordRemainder.charAt(j)) {
                    playerWordRemainder.deleteCharAt(i);
                    i--;
                    AIWordRemainder.deleteCharAt(j);
                    break;
                }
            }
        }
        findWinner();
    }


    private void findWinner() {
        /*
        System.out.printf("You played: %s\n", playerWord);
        System.out.printf("Computer played: %s\n", AIWord);*/
        System.out.printf("-----%s vs %s-----\n", playerWord.toUpperCase(), AIWord.toUpperCase());
        if (playerWordRemainder.length() > AIWordRemainder.length()) {
            System.out.print("You win!!-- ");
            winner = "player";
        }
        else if (playerWordRemainder.length() < AIWordRemainder.length()) {
            System.out.print("You lose-- ");
            winner="computer";
        }
        else {
            System.out.print("Tie------- ");
            winner = "tie";
        }
        if (playerWordRemainder.length() == 0)
            System.out.print("You had no characters leftover, ");
        else
            System.out.printf("You had character(s) '%s' leftover, ", format(playerWordRemainder.toString()));
        if (AIWordRemainder.length() == 0)
            System.out.println("the computer had no characters leftover.");
        else
            System.out.printf("the computer had character(s) '%s' leftover.\n", format(AIWordRemainder.toString()));
    }

    private String format(String str) {
        String strTemp = "";
        for (int i = 0; i < str.length(); i++) {
            strTemp += str.charAt(i);
            if (i < str.length() - 1)
                strTemp += "-";
        }
        return strTemp;
    }

    //These are no longer used, but I left them anyways. This is from the original version, but now this class should return who won, player or AI, and no longer handle any printing.

    private void printWinner(String winner, StringBuilder winnerLeftover, String loser, StringBuilder loserLeftover) {
        System.out.printf("The winner is %s with character(s) '%s' leftover.\n", winner, format(winnerLeftover.toString()));
        if (loserLeftover.length() > 0)
            System.out.printf("The loser is %s with character(s) '%s' leftover.\n", loser, format(loserLeftover.toString()));
        else
            System.out.printf("The loser is %s, all characters were consumed.\n", loser);
    }

    private void printTie() {
        System.out.printf("We have a tie.\n");
        if (playerWordRemainder.length() != 0) {
            System.out.printf("%s has character(s) '%s' leftover.\n", playerWord, format(playerWordRemainder.toString()));
            System.out.printf("%s has character(s) '%s' leftover.\n", AIWord, format(AIWordRemainder.toString()));
        } else
            System.out.println("All characters for both words were consumed.");
    }
}