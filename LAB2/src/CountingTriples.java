import java.util.Scanner;

public class CountingTriples {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int yuansugeshu=in.nextInt();
        int m=in.nextInt();
        int[] a=new int[yuansugeshu];
        for (int i=0;i<yuansugeshu;i++){
            a[i]=in.nextInt();
        }
        int lo=0;
        int hi=0;
        long count=0;

        for (int mid=1;mid<a.length-1;mid++){
            lo=mid-1;
            hi=mid+1;
            while(true){
                if (a[lo]+a[mid]+a[hi]==m){
                    count++;
                    if (lo==0&&hi==a.length-1)
                        break;
                    else if (lo==0&&hi!=a.length-1){
                        int k=0;
                        while(hi+k+1<a.length){
                            if (a[hi+k+1]==a[hi])
                                k++;
                            else
                                break;
                        }
                        count=count+k;
                        if (hi+k==a.length-1)
                            break;
                        else if (hi+k+1<a.length)
                            hi=hi+k+1;
                    }
                    else if (lo!=0&&hi==a.length-1){
                        int k=0;
                        while(lo-k-1>=0){
                            if (a[lo-k-1]==a[lo])
                                k++;
                            else
                                break;
                        }
                        count+=k;
                        if (lo-k==0)
                            break;
                        else if (lo-k-1>=0)
                            lo=lo-k-1;
                    }
                    else{
                        int k=0; int j=0;
                        while(hi+k+1<a.length){
                            if (a[hi+k+1]==a[hi])
                                k++;
                            else
                                break;
                        }
                        while(lo-j-1>=0){
                            if (a[lo-j-1]==a[lo])
                                j++;
                            else
                                break;
                        }
                        count=count+k*j+k+j;
                        if (hi+k==a.length-1)
                            break;
                        else if (hi+k+1<a.length)
                            hi=hi+k+1;
                        if (lo-j==0)
                            break;
                        else if (lo-j-1>=0)
                            lo=lo-j-1;
                    }
                }
                if (a[lo]+a[mid]+a[hi]>m){
                    if (lo==0)
                        break;
                    else
                        lo--;
                }
                if (a[lo]+a[mid]+a[hi]<m){
                    if (hi==a.length-1)
                        break;
                    else
                        hi++;
                }
            }
        }
        System.out.println(count);
    }
}
