/*
 * TCSS 342 - Winter 2017
 * Assignment 4
 */
package processing;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Reads a ".txt" file into a list of strings.
 * 
 * 
 * @author Yaro Salo
 * @version 1.0
 *
 */
public final class InputProcessor {

    /** A scanner to deal with user input. */
    private static final Scanner SCANNER = new Scanner(System.in);
    
    /** Prompt for map choosing.*/
    private static final String PROMPT = "Choose a data structure.\nEnter (1) for TreeMap"
                    + "Enter (2) for HashMap";
    
    /** List to contain the all the words in a file.*/
    private final List<String> myWords;
   
    /**
     * Private constructor to prevent instantiation.
     */
    public InputProcessor() {
        myWords = new ArrayList<>();
    }
    
    /** 
     * Asks for a file name, and opens that file into a buffer reader.
     *
     * @return a list of words in a given file.
     */
    public List<String> readFile() {

        System.out.println("Please enter the name of a file followed by .txt extension: ");
        final String fileName = SCANNER.next();
        fileToList(fileName);
        return myWords;
    }
    
    /**
     * Asks for user input about which structure they would like to use.
     * 
     * @return a 1 indicating a tree map and 2 indicating a hash map.
     */
    public int chooseStructure() {
        
        System.out.println(PROMPT);
        final int structure = getInteger();
        
        if (structure != 1 && structure != 2) {
            chooseStructure();
        }
        return structure;
    }
    
    
    /**
     * Asks for user input about how many top words the would like to see.
     * 
     * @return a 1 indicating a tree map and 2 indicating a hash map.
     */
    public int topN() {
        System.out.println("How many top words would you like to see");
        return getInteger();        
    }
    
    /** 
     * Helper method to get correct input.
     * 
     * @return a 1 indicating a tree map and 2 indicating a hash map.
     */
    private int getInteger() {
        
        while (!SCANNER.hasNextInt()) {
            System.out.println("Enter a number: ");
            SCANNER.next();
        }
        return SCANNER.nextInt();
        
    }
   
    /**
     * Private helper method to extract words from a file.
     * @param theName is the name of the file from which to read.
     * @return a list of words.
     */
    public List<String> fileToList(final String theName) {
        try {
            

            final FileInputStream fileIn = new FileInputStream(theName);
            int curr; 
            String word = "";
            boolean startWord = false;
            while ((curr = fileIn.read()) != -1) {
                
                 
                final char ch = (char) curr;
                
                
                if (Character.isLetter(ch)) {
                    
                    if (!startWord) {
                        
                        startWord = true;
                        word = "";    
                    }
                    word += ch;
                
                } else if (ch == '\'') {
                
                    if (startWord) {
                        word += ch;
                    }
      
                } else {
                    
                    if (startWord) {
                        word = word.toUpperCase(Locale.ENGLISH);
                        if (word.length() > 1 || "I".equals(word)) {
                            myWords.add(word);
                        }
                        startWord = false;
                    }
                }
                
            }
            fileIn.close();
    
        } catch (final IOException e) {
            System.out.println("File Could Not Be Opened!");
            readFile();
        } 
        return myWords;
       
    }
    
    @Override
    public String toString() {
        
        return myWords.toString();
    }
    


}
