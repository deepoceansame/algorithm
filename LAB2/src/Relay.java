import java.util.Scanner;

public class Relay {
    static int[] dianbiao=null;
    static int renshu=0;


    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int treeshu=in.nextInt();
        dianbiao=new int[treeshu+1];
        renshu=in.nextInt();
        dianbiao[treeshu]=in.nextInt();
        int hi=dianbiao[treeshu];
        int lo=0;
        int mid=0;
        for (int i=0;i<treeshu;i++){
            dianbiao[i]=in.nextInt();
        }
        while(lo<hi){
            mid=(lo+hi)/2;
            if (check(mid)){
                hi=mid;
            }
            else{
                lo=mid+1;
            }
        }
        System.out.println(hi);
    }

    public static boolean check(int a){
        int count=1;
        boolean checkAnswer=true;
        int nengliangshu=a;
        for (int i=0;i<dianbiao.length-1;i++){
            if (dianbiao[i+1]-dianbiao[i]>nengliangshu ){
                if (nengliangshu==a){
                    checkAnswer=false;
                    break;
                }
                else{
                    nengliangshu=a;
                    i=i-1;
                    count++;
                    if (count>renshu){
                        checkAnswer=false;
                        break;
                    }
                }
            }
            else{
                nengliangshu=nengliangshu-dianbiao[i+1]+dianbiao[i];
            }
        }
        return checkAnswer;
    }

}
