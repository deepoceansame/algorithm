public class BiggestSubSum {
    public static int biggestsubsum(int[] list,int left,int right ){
        int mid=(left+right)/2;
        if (left==right){
            if (list[left]>0)
                return list[left];
            else
                return 0;
        }
        int biggestLeftSum=biggestsubsum(list,left,mid);
        int biggestRightSum=biggestsubsum(list,mid+1,right);

        int biggestLeftMiddleSum=0;
        int middleLeftSum=0;

        for (int i=mid;i>=0;i--){
            middleLeftSum+=list[i];
            if (middleLeftSum>biggestLeftMiddleSum)
                biggestLeftMiddleSum=middleLeftSum;
        }

        int biggestRightMiddleSum=0;
        int middleRightSum=0;

        for (int i=mid+1;i<list.length;i++){
            middleRightSum+=list[i];
            if (middleRightSum>biggestRightMiddleSum)
                biggestRightMiddleSum=middleRightSum;
        }

        int biggestMiddleSum=biggestRightMiddleSum+biggestLeftMiddleSum;
        int result=Math.max(biggestLeftSum,biggestRightSum);
        result=Math.max(result,biggestMiddleSum);

        return result;
    }

    public static void main(String[] args) {
        int[] list={-1,2,3,4,5,-6,9};
        System.out.println(biggestsubsum(list,0,list.length-1));
    }
}
