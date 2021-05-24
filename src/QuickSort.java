import java.util.ArrayList;
import java.util.Scanner;

public class QuickSort {
    public static int ready(int[] list,int left,int right) {
        int temp1=list[left];
        int temp2=list[right];
        int temp3=list[(left+right)/2];
        int zhongwei=zhongweishu(temp1,temp2,temp3);
        if (zhongwei==temp2){
            list[left]=temp2;
            list[right]=temp1;
        }
        else if(zhongwei==temp3){
            list[left]=temp3;
            list[(left+right)/2]=temp1;
        }


        int pivot=list[left];
        while(left<right){
            while(right>left && list[right]>=pivot)
                right--;
            list[left]=list[right];
            while(right>left && list[left]<=pivot)
                left++;
                list[right]=list[left];
        }
        list[left]=pivot;
        return left;
    }

    public static void quickSort(int[] list,int left,int right){
            if (right-left<6){
                insertionSort(list,left,right);
            }
            else{
            int mid=ready(list,left,right);
            quickSort(list,left,mid);
            quickSort(list,mid+1,right);}

    }

    public static int findK(int [] list,int left,int right,int k){
       int i=ready(list,left,right);
       int quedingwei=i-left+1;
       int yudingwei=right-left+1-k+1;
       if (quedingwei==yudingwei)
           return list[i];
       else if (yudingwei<quedingwei){
           return findK(list,left,i-1,k-(right+1-i));
       }
       else
           return findK(list,i+1,right,k);
    }
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int daxiao=in.nextInt();
        int[] shuzu=new int[daxiao];
        for (int i=0;i<daxiao;i++){
            shuzu[i]=in.nextInt();
        }
        quickSort(shuzu,0,10);
        for (int i=0;i<daxiao;i++){
            System.out.print(shuzu[i]+" ");
        }
    }

    public static int zhongweishu(int a,int b,int c){
        int k=Math.max(a,Math.max(c,b));
        int j=Math.min(a,Math.min(c,b));
        int zhongwei=a+b+c-k-j;
        if (zhongwei==k)
            return j;
        else
            return zhongwei;
    }

    public static void insertionSort(int[] a,int left,int right){
        for (int i=left+1;i<=right;i++){
            int j=i;
            while(j>left && a[j]<a[j-1]){
                int temp=a[j];
                a[j]=a[j-1];
                a[j-1]=temp;
                j--;
            }
        }
    }
}
