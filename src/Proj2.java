import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

/***********************************************
 * @file Proj2.java
 * @description Fills a sorted array list and a shuffled array list with contents
 *              from SP500 data. Then it uses BST and AVL Tree to search and insert elements
 * @author Alex Warren
 * @date October 20, 2025
 ***********************************************/

public class Proj2 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        // Store data in an ArrayList
        ArrayList<SP500> dataList = new ArrayList<>();

        int count = 0;
        while (inputFileNameScanner.hasNextLine() && count < numLines) {
            String[] parts = inputFileNameScanner.nextLine().split(",");
            if (parts.length >= 2) {
                String symbol = parts[0].trim();
                double price = Double.parseDouble(parts[1].trim());
                dataList.add(new SP500(symbol, price));
                count++;
            }
        }
        inputFileNameScanner.close();

        // Create sorted and randomized copies
        ArrayList<SP500> sortedList = new ArrayList<>(dataList);
        Collections.sort(sortedList);

        ArrayList<SP500> randomList = new ArrayList<>(dataList);
        Collections.shuffle(randomList);

        // Create four trees (BST and AVL)
        BST<SP500> bstSorted = new BST<>();
        BST<SP500> bstRandom = new BST<>();
        AvlTree<SP500> avlSorted = new AvlTree<>();
        AvlTree<SP500> avlRandom = new AvlTree<>();

        long start, end;

        // BST Sorted Insert
        start = System.nanoTime();
        for (SP500 stock : sortedList) bstSorted.insert(stock);
        end = System.nanoTime();
        long bstSortedInsertTime = end - start;

        // BST Random Insert
        start = System.nanoTime();
        for (SP500 stock : randomList) bstRandom.insert(stock);
        end = System.nanoTime();
        long bstRandomInsertTime = end - start;

        // AVL Sorted Insert
        start = System.nanoTime();
        for (SP500 stock : sortedList) avlSorted.insert(stock);
        end = System.nanoTime();
        long avlSortedInsertTime = end - start;

        // AVL Random Insert
        start = System.nanoTime();
        for (SP500 stock : randomList) avlRandom.insert(stock);
        end = System.nanoTime();
        long avlRandomInsertTime = end - start;

        // BST Sorted Search
        start = System.nanoTime();
        for (SP500 stock : dataList) bstSorted.search(stock);
        end = System.nanoTime();
        long bstSortedSearchTime = end - start;

        // BST Random Search
        start = System.nanoTime();
        for (SP500 stock : dataList) bstRandom.search(stock);
        end = System.nanoTime();
        long bstRandomSearchTime = end - start;

        // AVL Sorted Search
        start = System.nanoTime();
        for (SP500 stock : dataList) avlSorted.contains(stock);
        end = System.nanoTime();
        long avlSortedSearchTime = end - start;

        // AVL Random Search
        start = System.nanoTime();
        for (SP500 stock : dataList) avlRandom.contains(stock);
        end = System.nanoTime();
        long avlRandomSearchTime = end - start;

        // Print results
        System.out.println("Results for " + numLines + " lines:");
        System.out.println("BST Sorted: Insert time = " + bstSortedInsertTime + " ns, Search time = " + bstSortedSearchTime + " ns");
        System.out.println("BST Random: Insert time = " + bstRandomInsertTime + " ns, Search time = " + bstRandomSearchTime + " ns");
        System.out.println("AVL Sorted: Insert time = " + avlSortedInsertTime + " ns, Search time = " + avlSortedSearchTime + " ns");
        System.out.println("AVL Random: Insert time = " + avlRandomInsertTime + " ns, Search time = " + avlRandomSearchTime + " ns");

        // Append results to output.txt
        FileOutputStream outputStream = new FileOutputStream("output.txt", true);
        String csvLine = numLines + "," +
                bstSortedInsertTime + "," + bstSortedSearchTime + "," +
                bstRandomInsertTime + "," + bstRandomSearchTime + "," +
                avlSortedInsertTime + "," + avlSortedSearchTime + "," +
                avlRandomInsertTime + "," + avlRandomSearchTime + "\n";
        outputStream.write(csvLine.getBytes());
        outputStream.close();
    }
}
