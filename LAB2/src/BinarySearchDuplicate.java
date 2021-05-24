public class BinarySearchDuplicate<T extends Comparable<? super T>> {
    public static void main(String[] args) {
        Integer[] a={1,2,2};
        System.out.println(binarySearch(a,2,0,a.length-1));
        System.out.println(binarySearchLeft(a,2,0,a.length-1));
        System.out.println(binarySearchRight(a,2,0,a.length-1));
        System.out.println();
    }

    public static <T extends Comparable<? super T>> int binarySearch(T[] ts,T t,int from,int to){  //整个数组from：0 to: ts.length-1
        int hi=to;
        int lo=from;
        int mid=0;
        if (to==from)
            mid=to;
        if (to<from)
            return -1;
        while(lo<hi){
              mid=lo+(hi-lo)/2;
              int compareAnswer =ts[mid].compareTo(t);
              if (compareAnswer==0){
                  break;
              }
              else if (compareAnswer<0){
                  lo=mid+1;                                  //can you remember 
              }
              else
                  hi=mid-1;
        }
        if (ts[mid]==t)
            return mid;
        else
            return -1;
    }

    public static <T extends Comparable<? super T>> int binarySearchLeft(T[] ts,T t,int from,int to){
        int hi=to;
        int lo=from;
        int mid=0;
        if (to==from)
            mid=to;
        if (to<from)
            return -1;
        while(lo<hi){
            mid=lo+(hi-lo)/2;
            int compareAnswer =t.compareTo(ts[mid]);
            if (compareAnswer>0)
               lo=mid+1;
            else
                hi=mid;
        }
        if (ts[hi]==t) // 这里是hi
            return hi;
        else
            return -1;
    }

    public static <T extends Comparable<? super T>> int binarySearchRight(T[] ts,T t,int from,int to){
        int hi=to;
        int lo=from;
        int mid=0;
        if (to==from)
            mid=to;
        if (to<from)
            return -1;
        while(lo<hi){
            mid=lo+(hi-lo)/2+1;                       //注意
            int compareAnswer =t.compareTo(ts[mid]); //注意
            if (compareAnswer>=0)
                lo=mid;
            else
                hi=mid-1;                //注意
        }
        if (ts[lo]==t)                //注意
            return lo;
        else
            return -1;
    }
}
