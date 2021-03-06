/*
 * TCSS 342 - Winter 2017
 * Assignment 4
 */

package tests;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

import processing.InputProcessor;
import processing.ListToMap;

/**
 * Tests the WordCounting  program for efficiency differences between HashMap and.
 * TreeMap
 * @author Yaro Salo
 * @version 1.0
 *
 */
public final class ImpericalTestingWordCount {

    /**
     * Top n words.
     */
    private static final int TOP_N = 10;
    
    /**
     * Test runs. */
    private static final int RUNS = 5;
    
    
    /**
     * Extract top 10.
     */
    private static final String TOP_TEN = "extract the top 10: ";
    /**
     * Indicate milliseconds. 
     */
    private static final String MI = "mi";
    /**
     * Private constructor to prevent instantiation.
     */
    private ImpericalTestingWordCount() {
        //Utility classes should not be instantiated. 
        throw new AssertionError("Utility class instantiation."); 
    }
    
    /**
     * The starting point of the program.
     * @param theArgs command line parameters ignored here.
     */
    public static void main(final String[] theArgs) {

        testASH();
        testTwist();
        testSource();
    }

    /**
     * Tests using ASH.txt.
     */
    private static void testASH() {
        final InputProcessor processor = new InputProcessor();
        //Read the file into a list and then into a map.
        final List<String> listOfWords = processor.fileToList("ASH.txt");

        final ListToMap listToMapTree = new ListToMap(1);
        final ListToMap listToMapHash = new ListToMap(2);
        final Map<String, Integer> mapOfWordsTree = listToMapTree.listToMap(listOfWords);
        final Map<String, Integer> mapOfWordsHash = listToMapHash.listToMap(listOfWords);
        //Arrays that will hold words and corresponding counts.
        final String[] topNWords = new String[TOP_N];
        final int[] topNCounts = new int[TOP_N];
        long time = 0;
        for (int i = 1; i <= RUNS; i++) {
            time += extraction(mapOfWordsTree, 1, topNWords, topNCounts);
        }
        
        long avarage = time / RUNS;
        System.out.println("Using ASH.txt and using TreeMap Time to extract the top 10: " 
                        +   avarage + MI);
        time = 0; 
        avarage = 0;
        for (int i = 1; i <= RUNS; i++) {
            time += extraction(mapOfWordsHash, 2, topNWords, topNCounts);
        }
        avarage = time / RUNS;
        System.out.println("Using ASH.txt and using HashMap Time to extract the top 10: " 
                        +   avarage +  MI);
    }
    
    /**
     * Tests using OliverTwist.txt.
     */
    private static void testTwist() {
        final InputProcessor processor = new InputProcessor();
        //Read the file into a list and then into a map.
        final List<String> listOfWords = processor.fileToList("OliverTwist.txt");

        final ListToMap listToMapTree = new ListToMap(1);
        final ListToMap listToMapHash = new ListToMap(2);
        final Map<String, Integer> mapOfWordsTree = listToMapTree.listToMap(listOfWords);
        final Map<String, Integer> mapOfWordsHash = listToMapHash.listToMap(listOfWords);
        //Arrays that will hold words and corresponding counts.
        final String[] topNWords = new String[TOP_N];
        final int[] topNCounts = new int[TOP_N];
        long time = 0;
        for (int i = 1; i <= RUNS; i++) {
            time += extraction(mapOfWordsTree, 1, topNWords, topNCounts);
        }
        
        long avarage = time / RUNS;
        System.out.println("Using OliverTwist and using TreeMap Time to extract the top 10: " 
                        +   avarage + MI);
        time = 0; 
        avarage = 0;
        for (int i = 1; i <= RUNS; i++) {
            time += extraction(mapOfWordsHash, 2, topNWords, topNCounts);
        }
        avarage = time / RUNS;
        System.out.println("Using OliverTwist and using HashMap Time to extract the top 10: " 
                        +   avarage + MI);
    }
    
    /**
     * Tests using OliverTwist.txt.
     */
    private static void testSource() {
        final InputProcessor processor = new InputProcessor();
        //Read the file into a list and then into a map.
        final List<String> listOfWords = processor.fileToList("source.txt");

        final ListToMap listToMapTree = new ListToMap(1);
        final ListToMap listToMapHash = new ListToMap(2);
        final Map<String, Integer> mapOfWordsTree = listToMapTree.listToMap(listOfWords);
        final Map<String, Integer> mapOfWordsHash = listToMapHash.listToMap(listOfWords);
        //Arrays that will hold words and corresponding counts.
        final String[] topNWords = new String[TOP_N];
        final int[] topNCounts = new int[TOP_N];
        long time = 0;
        for (int i = 1; i <= RUNS; i++) {
            time += extraction(mapOfWordsTree, 1, topNWords, topNCounts);
        }
        
        long avarage = time / RUNS;
        System.out.println("Using largest source and using TreeMap Time to "
                        + TOP_TEN
                        +   avarage + MI);
        time = 0; 
        avarage = 0;
        for (int i = 1; i <= RUNS; i++) {
            time += extraction(mapOfWordsHash, 2, topNWords, topNCounts);
        }
        avarage = time / RUNS;
        System.out.println("Using largest source and using HashMap Time to "
                        + TOP_TEN
                        +   avarage + MI);
    }
    
    
    /**
     * Extract the top n words.
     * @param theWords the map of words
     * @param theStructure indicates which structure is used.
     * @param theTopNWords array to hold the top n words
     * @param theTopNCounts array to hold the counts for the top n words
     * @return the time it took to extract top n words.
     */
    private static long extraction(final Map<String, Integer> theWords, 
                   final int theStructure, final String[] theTopNWords,
                   final int[] theTopNCounts) {
        final ListToMap listToMap = new ListToMap(theStructure);
        //Begin extraction
        final long start = System.nanoTime();
        //Sort top N counts using a heap.
        final PriorityQueue<Integer> heap = listToMap.getSortedFreq(theWords);
        //Swap keys and values.
        final Map<Integer, String> reversedMap = listToMap.reverseMap(theWords, theStructure);

        //Extract the top N into two parallel arrays.
        for (int i = 0; i < TOP_N; i++) {
            final int count = heap.poll();
            theTopNWords[i] = reversedMap.get(count);
            theTopNCounts[i] = count;
        }
        
        final long finish = System.nanoTime(); //end extraction.
        //Time in milliseconds.

        return TimeUnit.NANOSECONDS.toMillis(finish - start);
    }
}
