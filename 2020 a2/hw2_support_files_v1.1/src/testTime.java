import java.util.ArrayList;
import java.util.Collections;

public class testTime {
    public static void main (String[] args) {
        int size = 10; //SIZE
        ArrayList<Integer> list = new ArrayList<>(size+1);
        for (int i = 0; i <= size; i++){
            list.add(i);
        }
        Integer[] a = new Integer[size];
        for (int count = 0; count < size; count++){
            a[count] = list.remove((int)(Math.random() * list.size()));
        }

        //SortingAlgorithms.quickSort(a, true);

        Integer[] selection = new Integer[size];
        Integer[] insertion = new Integer[size];
        Integer[] merge = new Integer[size];
        Integer[] quick = new Integer[size];

        System.arraycopy(a, 0, selection, 0, a.length);
        System.arraycopy(a, 0, insertion, 0, a.length);
        System.arraycopy(a, 0, merge, 0, a.length);
        System.arraycopy(a, 0, quick, 0, a.length);

        double startTime=System.nanoTime();
        SortingAlgorithms.selectionSort(selection, false);
        double endTime=System.nanoTime();
        System.out.println("selection： "+(endTime-startTime)/100000+"ms");

        startTime=System.nanoTime();
        SortingAlgorithms.insertionSort(insertion, false);
        endTime=System.nanoTime();
        System.out.println("insertion： "+(endTime-startTime)/100000+"ms");

        startTime=System.nanoTime();
        SortingAlgorithms.mergeSort(merge, false);
        endTime=System.nanoTime();
        System.out.println("merge： "+(endTime-startTime)/100000+"ms");

        startTime=System.nanoTime();
        SortingAlgorithms.quickSort(quick, false);
        endTime=System.nanoTime();
        System.out.println("quick： "+(endTime-startTime)/100000+"ms");






//        for (int i = 0; i < a.length; i++) {
//            System.out.println(selection[i]);
//        }


    }
}
