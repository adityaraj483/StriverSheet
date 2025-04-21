import java.util.List;

public class SortingAlgorithm {

    //1. selection sort
    public int[] selectionSort(int[] nums) {
        int n = nums.length;
        for(int i=0;i<n;i++){
            int index = i;
            int val = nums[i];
            for(int j=i;j<n;j++){
                if(nums[j] < val){
                    val = nums[j];
                    index = j;
                }
            }
            swap(nums, i, index);
        }
        return nums;
    }
    void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    //2. Bubble sort
    public static void bubbleSort(List<Integer> a) {
        int n = a.size();
        for(int i=0;i<n;i++){
            for(int j=0;j<n-1;j++){
                if(a.get(j) > a.get(j+1)){
                    int temp = a.get(j);
                    a.set(j, a.get(j+1));
                    a.set(j+1, temp);

                }
            }
        }
    }
    //3. insertion sort
    public void insertionSort1(int n, List<Integer> arr) {
        for(int i=1;i<n;i++){
            int curr = arr.get(i);
            int j = i-1;
            while(j>=0 && arr.get(j) > curr){
                arr.set(j+1, arr.get(j));
                j--;
            }
            arr.set(j+1, curr);
        }
    }

    // 4. Merge Sort----------
    void mergeSort(int arr[], int l, int r) {
        // code here
        if(l< r){
            int m = (l+r)/2;
            mergeSort(arr, l, m);
            mergeSort(arr, m+1, r);
            merge(arr, l, m, r);
        }
    }

    void merge(int[] arr, int l, int m, int r){
        int[] temp = new int[r-l+1];
        int l1 =l, m1 = m+1, k=0;

        while(l1 <= m && m1 <=r){
            if(arr[l1] <= arr[m1]){
                temp[k++] = arr[l1++];
            }else{
                temp[k++] = arr[m1++];
            }
        }
        while(l1 <= m){
            temp[k++] = arr[l1++];
        }
        while(m1 <=r){
            temp[k++] = arr[m1++];
        }

        k=0;
        for(int i=l;i<=r;i++){
            arr[i] = temp[k++];
        }
    }

    //5. Quick sort-------
    static void quickSort(int arr[], int low, int high) {
        // code here
        if(low < high){
            int index = partition(arr, low, high);
            quickSort(arr, low, index-1);
            quickSort(arr, index+1, high);
        }
    }

    static int partition(int[] arr, int low, int high) {
        // your code here

        int pivot = arr[low];

        int i = low, j = high;

        while(i < j){

            while(arr[i] <= pivot && i <= high-1)
                i++;

            while(arr[j] >= pivot && j >= low+1)
                j--;

            if(i<j){
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        arr[low] = arr[j];
        arr[j] = pivot;
        return j;
    }
}
