import java.util.Scanner;

public class BinarySort {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int timushu=in.nextInt();
        int labshu=in.nextInt();
        int[] diff =new int[timushu];
        int[] ener =new int[labshu];
        int ene=0;
        for (int i=0;i<timushu;i++){
            diff[i]=in.nextInt();
        }
        for (int j=0;j<labshu;j++){
            ener[j]=in.nextInt();
        }
        for (int i=0;i<labshu;i++){
            ene=ener[i];
            int lo=0;
            int hi= diff.length-1;
            int mid=0;
            while(true){
                mid=(lo+hi)/2;
                if (diff[mid]==ene){
                    System.out.println("Accept");
                    break;
                }
                if (lo==hi){
                    break;
                }
                else{
                    if (diff[mid]>ene){
                        hi=mid;
                    }
                    else
                        lo=mid+1;
                }
            }
            if (diff[mid]==ene)
                continue;
            else{
                if (diff[hi]>ene)
                    System.out.println(ene-diff[hi-1]);
                else
                    System.out.println(ene-diff[hi]);
            }
        }
    }
}
