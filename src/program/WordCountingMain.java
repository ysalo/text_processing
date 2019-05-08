/*
 * TCSS 342 - Winter 2017
 * Assignment 4
 */

package program;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

import processing.InputProcessor;
import processing.ListToMap;


/**
 * The main program for word counting.
 * @author Yaro Salo
 * @version 1.0
 *
 */
public final class WordCountingMain {

    
    /** Input processor. */
    private static final InputProcessor PROCESSOR = new InputProcessor();
    /**
     * Private constructor to prevent instantiation.
     */
    private WordCountingMain() {
        //Utility classes should not be instantiated. 
        throw new AssertionError("Utility class instantiation."); 
    }
    
    /**
     * Starting point for the program.
     * @param theArgs command line parameter ignored in this application.
     */
    public static void main(final String[] theArgs) {
        
        //Read the file into a list and then into a map.
        final List<String> listOfWords = PROCESSOR.readFile();
        final int structure = PROCESSOR.chooseStructure();
        final int topN = PROCESSOR.topN();
        final ListToMap listToMap = new ListToMap(structure);
        final Map<String, Integer> mapOfWords = listToMap.listToMap(listOfWords);
        
        //Arrays that will hold words and corresponding counts.
        final String[] topNWords = new String[topN];
        final int[] topNCounts = new int[topN];
        
        //Begin extraction
        final long start = System.nanoTime();
        //Sort top N counts using a heap.
        final PriorityQueue<Integer> heap = listToMap.getSortedFreq(mapOfWords);
        //Swap keys and values.
        final Map<Integer, String> reversedMap = listToMap.reverseMap(mapOfWords, structure);

        //Extract the top N into two parallel arrays.
        for (int i = 0; i < topN; i++) {
            final int count = heap.poll();
            topNWords[i] = reversedMap.get(count);
            topNCounts[i] = count;
        }
        
        final long finish = System.nanoTime(); //end extraction.
        //Time in milliseconds.
        final long timeInMI = TimeUnit.NANOSECONDS.toMillis(finish - start);
        //Notify user with results.
        System.out.println("The total number of words: " + listOfWords.size());
        System.out.println("The top " + topN + " words are: ");
        for (int i = 0; i < topN; i++) {
            System.out.println(i + 1 + ". " + topNWords[i] + " = " + topNCounts[i]);
        }
        System.out.println("The time it took to extract the top " + topN + ": " 
                        + timeInMI + " milliseconds");
    }
    

}
