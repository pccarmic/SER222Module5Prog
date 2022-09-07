package edu.ser222.m02_01;

import java.util.Random;
import java.text.DecimalFormat;

/**
 * (basic description of the program or class)
 * 
 * Completion time: (estimation of hours spent on this program)
 *
 * @author (your name), Acuna, Sedgewick
 * @version (a version number or a date)
 */


public class CompletedBenchmarkTool implements BenchmarkTool {
    
    /***************************************************************************
     * START - SORTING UTILITIES, DO NOT MODIFY (FROM SEDGEWICK)               *
     **************************************************************************/
    
    public static void insertionSort(Comparable[] a) {
        int N = a.length;
        
        for (int i = 1; i < N; i++)
        {
            // Insert a[i] among a[i-1], a[i-2], a[i-3]... ..          
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--)
                exch(a, j, j-1);
        }
    }
    
    
    public static void shellsort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        
        while (h < N/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...
        
        while (h >= 1) {
            // h-sort the array.
            for (int i = h; i < N; i++) {
                // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)
                exch(a, j, j-h);
            }
            h = h/3;
        }
    }
    
    
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    
    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }
    
    /***************************************************************************
     * END - SORTING UTILITIES, DO NOT MODIFY                                  *
     **************************************************************************/

    //TODO: implement interface methods.

    public static void main(String args[]) {
        BenchmarkTool me = new CompletedBenchmarkTool();
        int size = 8196;
        
        //NOTE: feel free to change size here. all other code must go in the
        //      methods.
        
        me.runBenchmarks(size);
    }

    @Override
    public Integer[] generateTestDataBinary(int size) {
        Integer[] arr = new Integer[size];

        for(int i = 0; i < size; i++){
            if(i < size/2) {
                arr[i] = 0;
            } else {
                arr[i] = 1;
            }
        }
        return arr;
    }

    @Override
    public Integer[] generateTestDataHalves(int size) {
        BenchmarkTool me = new CompletedBenchmarkTool();
        Integer[] arr = new Integer[size];
        Integer[] temp_arr = new Integer[size/2];
        int input = 0;
        int temp_size = size/2;

        //recursively create the array
        if(size <= 1){
            for(int i = 0; i < size; i++){
                arr[i] = 0;
            }
        } else {
            for(int i = 0; i < size/2; i++){
                arr[i] = 0;
            }
            temp_arr = me.generateTestDataHalves(size-(size/2));

            int j = 0;
            for(int i = size/2; i < size; i++){
                arr[i] = temp_arr[j] + 1;
                j++;
            }
        }
        return arr;
    }

    @Override
    public Integer[] generateTestDataHalfRandom(int size) {
        Integer[] arr = new Integer[size];
        Random rand = new Random();

        for(int i = 0; i < size; i++){
            if(i < size/2) {
                arr[i] = 0;
            } else {
                arr[i] = rand.nextInt(Integer.MAX_VALUE);
            }
        }
        return arr;
    }

    @Override
    public double computeDoublingFormula(double t1, double t2) {
        return 0;
    }

    @Override
    public double benchmarkInsertionSort(Integer[] small, Integer[] large) {
        return 0;
    }

    @Override
    public double benchmarkShellsort(Integer[] small, Integer[] large) {
        return 0;
    }

    @Override
    public void runBenchmarks(int size) {

    }
}