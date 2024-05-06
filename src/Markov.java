import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Title: Markov.java
 * Abstract: This program reads in a file and create a list of words that follow a word.
 * A sentence is formed by randomly selecting a word that follows the current word.
 * Author: Stacy Kirchner
 * Date: 13 November 2022
 */

public class Markov {
    private final static String PUNCTUATION = "__$";
    private final static String PUNCTUATION_MARKS = ".!?$";
    private HashMap<String, ArrayList<String>> words = new HashMap<String, ArrayList<String>>();
    private String prevWord = "";

    /**
     * constructor for Markov
     * sets prevWord to PUNCTUATION
     * adds PUNCTUATION as a key for words with the value of a new ArrayList
     */
    public Markov() {
        words.put(PUNCTUATION, new ArrayList<String>());
        prevWord = PUNCTUATION;
    }

    /**
     * returns HashMap words
     */
    public HashMap<String, ArrayList<String>> getWords(){

        return words;
    }

    /**
     * @param filename: is used to retrieve the content of a file as a String,
     *                then the content is passed to addLine() method;
     *                Catches IOExceptions if found
     */
    public void addFromFile (String filename) {
        String input;
        File f = new File((filename));
        try{
            Scanner scan = new Scanner(f);
            while(scan.hasNextLine() && scan!= null){
                input = scan.next();
                addLine(input);
            }
        }
        catch (IOException e){
            System.out.println("Could not locate " + filename);
            e.printStackTrace();
        }
    }

    /**
     * @param word: is split into individual words whenever there is a whitespace,
     *            if the String is not empty
     */
    void addLine(String word) {
        if (word.length() != 0) {
            String[] word2 = word.trim().split("\\s+");
                for (int i = 0; i < word2.length; i++) {
                    addWord(word2[i]);
                }
        }
        else{
            return;
        }
    }

    /**
     * prevWord is checked to see if it ends with punctuation,
     * if so the current word is added under the PUNCTUATION key of words;
     * if prevWord does not end in punctuation,
     * prevWord is checked to see if words has it as a key already,
     * when prevWord has already been added as a key, the current word should be added to the array list
     * when prevWord does not have a key in words it is added as a key, with a new array list
     */
    void addWord(String word) {
        if(endsWithPunctuation(prevWord)) {
            words.get(PUNCTUATION).add(word);
        }
        else if(!(endsWithPunctuation(prevWord))){
            if(words.containsKey(prevWord)){
                words.get(prevWord).add(word);
            }
            else{
                words.put(prevWord, new ArrayList<String>());
                words.get(prevWord).add(word);
            }
        }
        else {
        }
        prevWord = word;
    }

    /**
     * uses a random word from the values under PUNCTUATION, that word becomes the current word
     * if the current word it is added to the sentence being built along with a space. A new random word then
     * becomes the  current word and this loop continues under a word that ends with punctuation is found
     * @return the sentence being built
     */
    public String getSentence(){
        String curWord;
        curWord = randomWord(PUNCTUATION);
        String sb = "";
        while (!endsWithPunctuation(curWord)) {
            sb = sb + curWord + " ";
            curWord = randomWord(curWord);
            if (endsWithPunctuation(curWord)) {
                sb = sb + curWord;
                break;
            }
        }

        return sb.toString();
    }

    /**
     *
     * @param word: is used as a key in the HashMap words to get the value of it as ArrayList
     * @return a random word from the words in the ArrayList as a String
     */
    String randomWord(String word){
        Random random = new Random();
        ArrayList<String> wordsFromKey = new ArrayList<>();
        wordsFromKey = words.get(word);
        String randomWord = words.get(word).get(random.nextInt(wordsFromKey.size()));
        return randomWord.toString();
    }

    /**
     * @param word: is checked to see if the last char of a word is one
     *            of the punctuation markS found in PUNCTUATION_MARKS
     * @return true if word ends with punctuation,
     * and false if word does not end with punctuation
     */
    public static boolean endsWithPunctuation(String word){
        boolean check = true;
        for(int i = 0; i < PUNCTUATION_MARKS.length()-1; i++){
            if(PUNCTUATION_MARKS.charAt(i) == word.charAt(word.length()-1)){
                check = true;
                break;
            }
            else{
                check = false;
            }

        }
        return check;
    }

    /**
     * @return the toString of HashMap words
     */
    public String toString(){

        return words.toString();
    }

}