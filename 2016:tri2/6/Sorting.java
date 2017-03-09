// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP 103, Assignment 10
 * Name:Minping YANG
 * Usercode:
 * ID:300364234
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;

/** Code for Sorting Experiment
 *   - testing code
 *   - sorting algorithms
 *   - utility methods for creating, testing, printing, copying arrays
 */

public class Sorting{

    /* Example method for testing and timing sorting algorithms.
     *  You will need to modify and extend this heavily to do your
     *  performance testing. It should probably run tests on each of the algorithms,
     *  on different sized arrays, and multiple times on each size.
     *  Make sure you create a new array each time you sort - it is not a good test if
     *  you resort the same array after it has been sorted.
     *  Hint: if you want to copy an array, use copyArray (below)
     */
    /*#my code*/
    int testingTimes = 6;
    String testType;
    public Sorting(){
        UI.addButton("Selection Sort",() -> buttonController("Selection Sort"));
        UI.addButton("Selection Sort 2",() -> buttonController("Selection Sort 2"));
        UI.addButton("Insertion Sort", () -> buttonController("Insertion Sort"));
        UI.addButton("Insertion Sort 2",() -> buttonController("Insertion Sort 2"));
        UI.addButton("Merge Sort", () -> buttonController("Merge Sort"));
        UI.addButton("Merge Sort 2", () -> buttonController("Merge Sort 2"));
        UI.addButton("Quick Sort", () -> buttonController("Quick Sort"));
        UI.addButton("Quick Sort 2", () -> buttonController("Quick Sort 2"));
        UI.addButton("Quick Sort 3", () -> buttonController("Quick Sort 3"));
        UI.addButton("Arrays.sort", () -> buttonController("Arrays.sort"));
        UI.addButton("Tim Sort", () -> buttonController("Tim Sort"));
        UI.addSlider("Testing times(initial value is 6)",1,12,6, this::setTestingtime);
        UI.addButton("Clear Text", UI::clearText);
        UI.addButton("Test",this::testSorts);

    }

    /*#my code*/
    public void setTestingtime(double t){
        testingTimes=(int)t;
    }

    /*#my code*/
    public void buttonController(String buttonName){
        UI.printf("Processing %s for %d times......\n", buttonName, testingTimes);
        testSorts(buttonName);

    }

    public void testSorts(){
        String[] data;
        int size = 11; // 10000;
        long start;
        long time;

        UI.setDivider(1);
        UI.println("\n\n======Selection Sort=======\n");

        data = createArray(size);
        //printData(data);
        start = System.currentTimeMillis();
        selectionSort(data);  

        time =  System.currentTimeMillis() - start;

        UI.printf("Number of items:  %,d\n", data.length);
        UI.printf("Sorted correctly: %b\n", testSorted(data));
        UI.printf("Time taken:       %.3f s\n", time/1000.0);

        UI.println("\n=======DONE===========\n");
    }

    /*#my code*/
    public void testSorts(String sortType){
        String[] data;
        String[] arrayType = {"Random", "Sorted", "Reverse"};
        int[] size = {100, 1000, 10000, 100000, 1000000};
        long[] timeCostArray = new long[testingTimes];      
        long start, time;
        long[][] table2DArray = new long[5][3];

        for (int arrayTypeNum = 0; arrayTypeNum < arrayType.length; arrayTypeNum++){
            for (int arraySizeIndex = 0; arraySizeIndex < size.length; arraySizeIndex++){
                int dataSize = size[arraySizeIndex];
                if ((sortType.equals("Selection Sort") || sortType.equals("Selection Sort 2") ||
                    sortType.equals("Insertion Sort") || sortType.equals("Insertion Sort 2")|| sortType.equals("Tim Sort"))
                && dataSize > 10000) continue;
                for (int numOfTimes = 0; numOfTimes < timeCostArray.length; numOfTimes++){
                    if (arrayType[arrayTypeNum].equals("Random")){
                        data = createArray(dataSize);

                    }
                    else if (arrayType[arrayTypeNum].equals("Sorted")){
                        data = createArray(dataSize);
                        Arrays.sort(data);

                    }
                    else if (arrayType[arrayTypeNum].equals("Reverse")){
                        data = createArray(dataSize);
                        Arrays.sort(data);
                        reverseArray(data);

                    }
                    else return;

                    start = System.currentTimeMillis();
                    switch (sortType){
                        case "Selection Sort": 
                        selectionSort(data);  
                        break;
                        case "Tim Sort": 
                        timSort(data);  
                        UI.printf("Sorted correctly: %b\n", testSorted(data));
                        break;
                        
                        case "Selection Sort 2": 
                        selectionSort2(data); 
                        //UI.printf("Sorted correctly: %b\n", testSorted(data));
                        break;
                        case "Insertion Sort":
                        insertionSort(data);

                        break;
                        case "Insertion Sort 2":
                        insertionSort2(data);
                        //UI.printf("Sorted correctly: %b\n", testSorted(data));
                        break;
                        case "Merge Sort":
                        mergeSort(data);

                        break;
                        case "Merge Sort 2":
                        mergeSort(data);
                        //UI.printf("Sorted correctly: %b\n", testSorted(data));
                        break;
                        case "Quick Sort":
                        quickSort(data);

                        break;
                        case "Quick Sort 2":
                        quickSort2(data);

                        break;
                        case "Quick Sort 3":
                        quickSort3(data);
                        //UI.printf("Sorted correctly: %b\n", testSorted(data));
                        break;
                        case "Arrays.sort":
                        Arrays.sort(data);
                        //UI.printf("Sorted correctly: %b\n", testSorted(data));
                        break;
                    }
                    time = System.currentTimeMillis() - start;
                    timeCostArray[numOfTimes] = time;
                }
                table2DArray[arraySizeIndex][arrayTypeNum] = getAverage(timeCostArray);
            }
        }
        printAll(sortType, table2DArray);

    }

    /*#my code*/
    private void printAll(String sortType, long[][] table){
        UI.printf("\n\t\t\t%s\n", sortType);
        UI.printf("============================================================\n");
        UI.printf("size\t\tRandom\t\tOrdered\t\tReverse\n");
        UI.printf("100\t\t%.3fs\t\t%.3fs\t\t%.3fs\n", table[0][0] / 1000.0, table[0][1] / 1000.0, table[0][2] / 1000.0);
        UI.printf("1000\t\t%.3fs\t\t%.3fs\t\t%.3fs\n", table[1][0] / 1000.0, table[1][1] / 1000.0, table[1][2] / 1000.0);
        UI.printf("10,000\t\t%.3fs\t\t%.3fs\t\t%.3fs\n", table[2][0] / 1000.0, table[2][1] / 1000.0, table[2][2] / 1000.0);
        if (sortType.equals("Merge Sort") || sortType.equals("Quick Sort")
        || sortType.equals("Quick Sort 2") || sortType.equals("Arrays.sort")
        || sortType.equals("Merge Sort 2") || sortType.equals("Quick Sort 3")){
            UI.printf("100,000\t\t%.3fs\t\t%.3fs\t\t%.3fs\n", table[3][0] / 1000.0, table[3][1] / 1000.0, table[3][2] / 1000.0);
            UI.printf("1,000,000\t%.3fs\t\t%.3fs\t\t%.3fs\n", table[4][0] / 1000.0, table[4][1] / 1000.0, table[4][2] / 1000.0);
        }
        UI.printf("============================================================\n");
        UI.printf("* Data are the averages of %d times of testing\n\n", testingTimes);
    }

    /*#my code*/
    private long getAverage(long[] timeCostArray){
        long total = 0;
        for (long x : timeCostArray){
            total += x;
        }
        return total / timeCostArray.length;
    }
    /* =============== SWAP ================= */

    /** Swaps the specified elements of an array.
     *  Used in several of the sorting algorithms
     */
    private  void swap(String[ ] data, int index1, int index2){
        if (index1==index2) return;
        String temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
    }

    /* ===============SELECTION SORT================= */

    /** Sorts the elements of an array of String using selection sort */
    public  void selectionSort(String[ ] data){
        // for each position, from 0 up, find the next smallest item 
        // and swap it into place
        for (int place=0; place<data.length-1; place++){
            int minIndex = place;
            for (int sweep=place+1; sweep<data.length; sweep++){
                if (data[sweep].compareTo(data[minIndex]) < 0)
                    minIndex=sweep;
            }
            swap(data, place, minIndex);
        }
    }

    /*#my code*/
    public void selectionSort2(String[] data){
        for (int place = 0; place < data.length - 1; place++){
            int minIndex = place;
            int maxIndex = (data.length - 1) - place;
            for (int sweep = place; sweep < data.length - place; sweep++){
                if (data[sweep].compareTo(data[minIndex]) < 0)
                    minIndex = sweep;
                else if (data[sweep].compareTo(data[maxIndex]) > 0)
                    maxIndex = sweep;
            }
            swap(data, place, minIndex);
            if (maxIndex == place)
                swap(data, (data.length - 1) - place, minIndex);
            else
                swap(data, (data.length - 1) - place, maxIndex);
        }
    }
    /* ===============INSERTION SORT================= */
    /** Sorts the  elements of an array of String using insertion sort */
    public  void insertionSort(String[] data){
        // for each item, from 0, insert into place in the sorted region (0..i-1)
        for (int i=1; i<data.length; i++){
            String item = data[i];
            int place = i;
            while (place > 0  &&  item.compareTo(data[place-1]) < 0){
                data[place] = data[place-1];       // move right
                place--;
            }
            //new right place
            data[place]= item;
        }
    } 

    /*#my code*/
    public void insertionSort2(String[] data){
        for (int i = 1; i < data.length; i++){
            String item = data[i];
            int low = 0;
            int high = i;
            while (low < high){
                int mid = (low + high) / 2;
                if (item.compareTo(data[mid]) > 0) low = mid + 1;
                else high = mid;
            }
            for (int j = i; j > low; j--){
                data[j] = data[j - 1];
            }
            data[low] = item;
        }
    }

    /* ===============MERGE SORT================= */
    /** non-recursive, wrapper method
     *  copy data array into a temporary array 
     *  call recursive mergeSort method     
     */
    public  void mergeSort(String[] data) {
        String[] other = new String[data.length];
        for(int i=0; i<data.length; i++)
            other[i]=data[i];
        mergeSort(data, other, 0, data.length); //call to recursive merge sort method
    }

    /** Recursive mergeSort method */
    public void mergeSort(String[] data, String[] temp, int low, int high) {
        if(low < high-1) {
            int mid = ( low + high ) / 2;
            mergeSort(temp, data, low, mid);
            mergeSort(temp, data, mid, high);
            merge(temp, data, low, mid, high);
        }
    }

    /** Merge method
     *  Merge from[low..mid-1] with from[mid..high-1] into to[low..high-1]
     *  Print data array after merge using printData
     */
    public void merge(String[] from, String[] to, int low, int mid, int high) {
        int index = low;      //where we will put the item into "to"
        int indxLeft = low;   //index into the lower half of the "from" range
        int indxRight = mid; // index into the upper half of the "from" range
        while (indxLeft<mid && indxRight < high) {
            if ( from[indxLeft].compareTo(from[indxRight]) <=0 )
                to[index++] = from[indxLeft++];
            else
                to[index++] = from[indxRight++];
        }
        // copy over the remainder. Note only one loop will do anything.
        while (indxLeft<mid)
            to[index++] = from[indxLeft++];
        while (indxRight<high)
            to[index++] = from[indxRight++];
    }

    public void timSort(String[] data){
        String[] other = new String[data.length];
        for (int range = 1; range < data.length; range *= 2){
            for (int start = 0; start < data.length; start += 2 * range){
                merge2(data, other, start, start + range, start + 2 * range);
            }
        }
    }

    public void timSort(String[] from, String[] to, int low, int mid, int high){
        int index = low;      //where we will put the item into "to"
        int indxLeft = low;   //index into the lower half of the "from" range
        int indxRight = mid; // index into the upper half of the "from" range

        if(indxLeft<mid && indxRight < high){
            for(int i=indxLeft+1;i<high-mid;i++){
                String item = from[indxLeft];
                int place=i;
                while (place > 0  &&  item.compareTo(from[place-1]) < 0){
                    from[place] = from[place-1];       // move up
                    place--;
                }

            }

            while (indxLeft<mid && indxRight < high) {
                if ( from[indxLeft].compareTo(from[indxRight]) <=0 )
                    to[index++] = from[indxLeft++];
                else
                    to[index++] = from[indxRight++];
            }
        } 
        while (indxLeft<mid)
            to[index++] = from[indxLeft++];
        while (indxRight<high)
            to[index++] = from[indxRight++];
       
    }

    /*#my code*/
    public void mergeSort2 (String[] data){
        String[] other = new String[data.length];
        for (int range = 1; range < data.length; range *= 2){
            for (int start = 0; start < data.length; start += 2 * range){
                merge2(data, other, start, start + range, start + 2 * range);
            }
        }
    }

    /*#my code*/
    public void merge2(String[] data, String[] other, int low, int mid, int high) {
        if (mid >= data.length) return;
        if (high > data.length) high = data.length;
        int i = low;
        int j = mid;
        for (int k = low; k < high; k++) {
            if (i == mid) {
                other[k] = data[j];
                j++;
            }
            else if (j == high) { 
                other[k] = data[i];
                i++;
            }
            else if (data[j].compareTo(data[i]) < 0){ 
                other[k] = data[j];
                j++;
            }
            else {
                other[k] = data[i];
                i++;
            }
        }
        for (int x = low; x < high; x++){
            data[x] = other[x];
        }
    }

    /*===============QUICK SORT=================*/
    /** Sort data using QuickSort
     *  Print time taken by Quick sort
     *  Print number of times partition gets called
     */

    /** Quick sort recursive call */
    public  void quickSort(String[ ] data) {
        quickSort(data, 0, data.length);
    }

    public  void quickSort(String[ ] data, int low, int high) {
        if (high-low < 2)      // only one item to sort.
            return;
        else {     // split into two parts, mid = index of boundary
            int mid = partition(data, low, high);
            quickSort(data, low, mid);
            quickSort(data, mid, high);
        }
    }

    /** Partition into small items (low..mid-1) and large items (mid..high-1) 
     *  Print data array after partition
     */
    private int partition(String[] data, int low, int high) {
        String pivot = data[(low+high)/2];
        int left = low-1;
        int right = high;
        while( left < right ) {
            do { 
                left++;       // just skip over items on the left < pivot
            } 
            while (left<high && data[left].compareTo(pivot) < 0);

            do { 
                right--;     // just skip over items on the right > pivot
            } 
            while (right>=low &&data[right].compareTo(pivot) > 0);

            if (left < right) 
                swap(data, left, right);
        }
        return left;
    }

    /** Quick sort, second version:  simpler partition method
     *   faster or slower?  */
    public  void quickSort2(String[ ] data) {
        quickSort2(data, 0, data.length);
    }

    public  void quickSort2(String[ ] data, int low, int high) {
        if (low+1 >= high) // no items to sort.
            return;
        else {     // split into two parts, mid = index of pivot
            int mid = partition2(data, low, high);
            quickSort2(data, low, mid);
            quickSort2(data, mid+1, high);
        }
    }

    /*#my code here*/
    public void quickSort3 (String[] data){
        quickSort3(data, 0, data.length);
    }

    /*#my code here*/
    public void quickSort3 (String[] data, int low, int high){
        if (low + 1 >= high) return;
        else if (high - low <= 5) {
            for (int i = low + 1; i < high; i++){
                String item = data[i];
                int place = i;
                while (place > 0  &&  item.compareTo(data[place - 1]) < 0){
                    data[place] = data[place - 1];       // move up
                    place--;
                }
                data[place] = item;
            }
        }
        else {     // split into two parts, mid = index of pivot
            int mid = partition3(data, low, high);
            quickSort3(data, low, mid);
            quickSort3(data, mid, high);
        }
    }

    public int partition2(String[] data, int low, int high){
        swap(data, low, (low+high)/2);    // choose pivot and put at position low
        String pivot = data[low];
        int mid = low;
        for(int i = low+1; i < high; i++){  // for each item after the pivot
            if ( data[i].compareTo(pivot) <0 ){
                mid++;                      // move mid point up
                swap(data, i, mid);
            }
        }
        swap(data, low, mid);   // move pivot to the mid point
        return mid;
    }

    /*#my code here*/
    public int partition3 (String[] data, int low, int high){
        String pivot = median(data[low], data[(low + high) / 2], data[high - 1]);
        int left = low-1;
        int right = high;
        while( left < right ) {
            do {
                left++;       // just skip over items on the left < pivot
            }
            while (left<high && data[left].compareTo(pivot) < 0);

            do {
                right--;     // just skip over items on the right > pivot
            }
            while (right>=low &&data[right].compareTo(pivot) > 0);

            if (left < right)
                swap(data, left, right);
        }
        return left;
    }

    /*#my code here*/
    public String median (String a, String b, String c){
        //abc
        if (a.compareTo(b) <= 0 && b.compareTo(c) <= 0) return b;
        //cba
        else if (c.compareTo(b) <= 0 && b.compareTo(a) <= 0) return b;
        //acb
        else if (a.compareTo(c) <= 0 && c.compareTo(b) <= 0) return c;
        //bca
        else if (b.compareTo(c) <= 0 && c.compareTo(a) <= 0) return c;
        //bac
        else if (b.compareTo(a) <= 0 && a.compareTo(c) <= 0) return a; 
        //cab
        else if (c.compareTo(a) <= 0 && a.compareTo(b) <= 0) return a;    
        else return a;
    }
    /* =====   Utility methods ============================================ */
    /** Tests whether an array is in sorted order
     */
    public boolean testSorted(String[] data) {
        for (int i=1; i<data.length; i++){
            if (data[i].compareTo(data[i-1]) < 0)
                return false;
        }
        return true;
    }

    public void printData(String[] data){
        for (String str : data){
            UI.println(str);
        }
    }

    /** Constructs an array of Strings by making random String values */
    public String[] createArray(int size) {
        Random randGenerator = new Random();
        String[] data = new String[size];
        for (int i=0; i<size; i++){
            char[] chars = new char[5];
            for (int c=0; c<chars.length; c++){
                chars[c] = (char) ('a' + randGenerator.nextInt(26));
            }
            String str = new String(chars);
            data[i] = str;
        }
        return data;
    }

    /** Constructs an array of Strings by reading a file
     * The size of the array will be the specified size, unless the
     * file is too short, or size is -ve, in which cases the array will
     * contain all the tokens in the file.
     */
    public String[] readArrayFromFile(int size, String filename) {
        File file = new File(filename);
        if (!file.exists()){
            UI.println("file "+filename+" does not exist");
            return null;
        }
        List<String> temp = new ArrayList<String> ();
        try {
            Scanner scan = new Scanner(new File(filename));
            while (scan.hasNext()) 
                temp.add(scan.next());
            scan.close();
        }
        catch(IOException ex) {   // what to do if there is an io error.
            UI.println("File reading failed: " + ex);
        }
        if (temp.size() < size || size<0)
            size = temp.size();

        String[] data = new String[size];
        for (int i =0; i<size; i++){
            data[i] = temp.get(i);
        }
        return data;
    }

    /** Create a new copy of an array of data */
    public String[] copyArray(String[] data){
        String[] newData = new String[data.length];
        for (int i=0; i<data.length; i++){
            newData[i] = data[i];
        }
        return newData;
    }

    /** Create a new copy of the first size elements of an array of data */
    public String[] copyArray(String[] data, int size){
        if (size> data.length) size = data.length;
        String[] newData = new String[size];
        for (int i=0; i<size; i++){
            newData[i] = data[i];
        }
        return newData;
    }

    public void reverseArray(String[] data){
        int bot = 0;
        int top = data.length-1;
        while (bot<top){
            swap(data, bot++, top--);
        }
    }

    public static void main(String[] args){
        Sorting sorter = new Sorting();
        sorter.testSorts();
    }

}
