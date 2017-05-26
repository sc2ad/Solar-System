import java.util.ArrayList;

public class MergeSorter {
	
	
	private static ArrayList<Object> array;
	private static int length;
	private static Object[] tempMergArr;

	public static ArrayList<Object> sort(ArrayList<Object> inputArr) {
        array = new ArrayList<Object>();
        for (int i = 0; i < inputArr.size(); i++) {
        	array.add(inputArr.get(i));
        }
        length = inputArr.size();
        tempMergArr = new Object[length];
        doMergeSort(0, length - 1);
        return array;
    }
 
    private static void doMergeSort(int lowerIndex, int higherIndex) {
         
        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            // Below step sorts the left side of the array
            doMergeSort(lowerIndex, middle);
            // Below step sorts the right side of the array
            doMergeSort(middle + 1, higherIndex);
            // Now merge both sides
            mergeParts(lowerIndex, middle, higherIndex);
        }
    }
 
    private static void mergeParts(int lowerIndex, int middle, int higherIndex) {
 
        for (int i = lowerIndex; i <= higherIndex; i++) {
            tempMergArr[i] = array.get(i);
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
            if (tempMergArr[i].mass <= tempMergArr[j].mass) {
                array.set(k, tempMergArr[i]);
                i++;
            } else {
                array.set(k, tempMergArr[j]);
                j++;
            }
            k++;
        }
        while (i <= middle) {
            array.set(k, tempMergArr[i]);
            k++;
            i++;
        }
 
    }
}
