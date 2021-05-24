import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class MergeSort {
    public static void main(String[] args) {
       Integer[] a={3,6,7,3,4,2,1,100,-1};
       Integer[] temparr=new Integer[9];
       mergeSort(a,0,a.length-1,temparr);
       for (Integer d:a){
           System.out.print(d+" ");
       }
    }

    public static void mergeSort(Comparable[] list,int lo,int hi,Comparable[] temparr){
        int mi=lo+(hi-lo)/2;
        if (lo<hi){
            mergeSort(list,lo,mi,temparr);
            mergeSort(list,mi+1,hi,temparr);
            merge(list,lo,hi,temparr);
        }
    }

    public static void merge(Comparable[] list,int lo,int hi,Comparable[] temparr){
        int mi=lo+(hi-lo)/2;
        int ind1=mi;
        int ind2=hi;
        int ind3=hi;
        while(ind3>=lo){
            if (ind2<=mi ||(ind1>=lo && list[ind1].compareTo(list[ind2])>0)){
                temparr[ind3]=list[ind1];
                ind1--;
            }
            else {
                temparr[ind3]=list[ind2];
                ind2--;
            }
            ind3--;
        }
        for (int i=lo;i<=hi;i++){
            list[i]=temparr[i];
        }
    }
}