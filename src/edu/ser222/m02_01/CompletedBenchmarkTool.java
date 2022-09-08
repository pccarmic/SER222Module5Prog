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
        int size = 131136;

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
        double b_value;
        b_value = Math.log(t2/t1);
        return Math.abs(b_value);
    }

    @Override
    public double benchmarkInsertionSort(Integer[] small, Integer[] large) {
        Stopwatch stopwatch;
        double b_value, t1, t2;

        stopwatch = new Stopwatch();
        insertionSort(small);
        t1 = stopwatch.elapsedTime();

        stopwatch = new Stopwatch();
        insertionSort(large);
        t2 = stopwatch.elapsedTime();

        b_value = computeDoublingFormula(t1, t2);

        return b_value;
    }

    @Override
    public double benchmarkShellsort(Integer[] small, Integer[] large) {
        Stopwatch stopwatch;
        double b_value, t1, t2;

        stopwatch = new Stopwatch();
        shellsort(small);
        t1 = stopwatch.elapsedTime();

        stopwatch = new Stopwatch();
        shellsort(large);
        t2 = stopwatch.elapsedTime();

        b_value = computeDoublingFormula(t1, t2);

        return b_value;
    }

    @Override
    public void runBenchmarks(int size) {
        Stopwatch stopwatch;
        String format = "#.###";
        DecimalFormat decimalFormat = new DecimalFormat(format);
        double insert_t1, insert_t2, shell_t1, shell_t2;
        String insert_bin_b, insert_half_b, insert_randInt_b;
        String shell_bin_b, shell_half_b, shell_randInt_b;

        //generate arrays of data
        Integer bin1[] = generateTestDataBinary(size);
        Integer bin2[] = generateTestDataBinary(size*2);
        Integer half1[] = generateTestDataHalves(size);
        Integer half2[] = generateTestDataHalves(size*2);
        Integer randInt1[] = generateTestDataHalfRandom(size);
        Integer randInt2[] = generateTestDataHalfRandom(size*2);

        /*****************************************************************************
         * CALCULATE THE EMPIRICAL B VALUES FOR EACH ARRAY TYPE USING INSERTION SORT *
         *****************************************************************************/
        insert_bin_b = decimalFormat.format(benchmarkInsertionSort(bin1, bin2));
        insert_half_b = decimalFormat.format(benchmarkInsertionSort(half1, half2));
        insert_randInt_b = decimalFormat.format(benchmarkInsertionSort(randInt1, randInt2));

        /*****************************************************************************
         *   CALCULATE THE EMPIRICAL B VALUES FOR EACH ARRAY TYPE USING SHELL SORT   *
         *****************************************************************************/
        shell_bin_b = decimalFormat.format(benchmarkShellsort(bin1, bin2));
        shell_half_b = decimalFormat.format(benchmarkShellsort(half1, half2));
        shell_randInt_b = decimalFormat.format(benchmarkShellsort(randInt1, randInt2));


        System.out.println("\t\tInsertion\tShellsort");
        System.out.println("   Bin\t" + insert_bin_b + "\t\t\t" + shell_bin_b);
        System.out.println("  Half\t" + insert_half_b + "\t\t\t" + shell_half_b);
        System.out.println("RanInt\t" + insert_randInt_b + "\t\t\t" + shell_randInt_b);

    }
}