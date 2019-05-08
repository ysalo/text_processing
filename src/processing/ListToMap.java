/*
 * TCSS 342 - Winter 2017
 * Assignment 4
 */

package processing;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * A class that will read a list to a hash map or a tree map.
 * @author Yaro Salo
 * @version 1.0
 *
 */
public class ListToMap {

    
    /** The map containing the words and their frequencies.*/
    private Map<String, Integer> myWords;
    
    /**
     * Private constructor to prevent instantiation.
     * @param theStructure indicates which map the user wants to use.
     */
    public ListToMap(final int theStructure) {
        mapCheck(theStructure);
    }
    
    
    /**
     * Checks which map the user wants to use.
     * @param theStructure indicates which structure the user wants to use.
     */
    private void mapCheck(final int theStructure) {
        if (theStructure == 1) {
            myWords = new TreeMap<String, Integer>();
        } else {
            myWords = new HashMap<String, Integer>();
        }
       
    }
    /**
     * Method that reads a given list to a hash map or a tree map 
     * based on an int passed in.
     * @param theList the list to read.
     * @return a tree or a hash map.
     */
    public  Map<String, Integer> 
        listToMap(final List<String> theList) {

        for (final String word: theList) {
            if (myWords.containsKey(word)) {
                myWords.put(word, myWords.get(word) + 1);
            } else {
                myWords.put(word, 1);
            }
        }
        return myWords;
    }

    /**
     * Sorts a map based on the values in descending order.
     * @param theMap the map to be sorted.
     * @param theStructure indicates which structure the user wants to use.
     * @return a sorted map.
     */
    public Map<Integer, String>
        reverseMap(final Map<String, Integer> theMap, final int theStructure) {

        
        final Map<Integer, String> reversedMap;

        if (theStructure == 1) {
            reversedMap = new TreeMap<Integer, String>();
        } else {
            reversedMap = new HashMap<Integer, String>();
        }
        
        for (final String word: theMap.keySet()) {
            reversedMap.put(theMap.get(word), word);
        }

        
        return reversedMap;
    }
    
    /**
     * Sorts the frequencies using a heap.
     * @param theMap from which to sort the frequencies.
     * @return a heap of sorted integers. s
     */
    public PriorityQueue<Integer> getSortedFreq(final Map<String, Integer> theMap) {
        final PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        
        for (final String word: theMap.keySet()) {
            heap.add(theMap.get(word));
        }
        return heap;
    }

    
}
