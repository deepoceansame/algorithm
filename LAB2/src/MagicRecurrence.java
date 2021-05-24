import java.util.ArrayList;
import java.util.Scanner;

public class MagicRecurrence {

    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int zhixincishu=in.nextInt();
        int[] answers=new int[zhixincishu];
        for (int i=0;i<zhixincishu;i++){
            int n=in.nextInt();
            if (n==1 || n==2 || n==3){
                answers[i]=1;
                continue;
            }
            int[] chucun=new int[(n-4)/2+3];
            for (int j=0;j<(n-4)/2+3;j++){
                if (j==0 || j==1 || j==2){
                    chucun[j]=1;
                }
                else
                    chucun[j]= chucun[(j-3)/2]+chucun[(j-3)/2+1]+chucun[(j-3)/2+2];
            }
            answers[i]=chucun[(n-4)/2]+chucun[(n-4)/2+1]+chucun[(n-4)/2+2];
        }
        for (int i:answers){
            System.out.println(i);
        }
    }
}
