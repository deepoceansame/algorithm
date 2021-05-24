import java.util.Scanner;

public class ThreeD_Print {
    public static char[][] threeDPrint(int a,int b,int c){
        char[][] answer=new char[2*b+2*c+1][2*b+2*a+1];
        int pivotX=0;
        int pivotY=0;
        for (int i=0;i<=b;i++){
            pivotX=2*i;
            pivotY=2*b+2*a-2*i;
            answer[pivotX][pivotY]='+';
            if (i==b){
                for (int j=1;j<=a;j++) {
                    answer[pivotX][pivotY - 2 * j + 1] = '-';
                    answer[pivotX][pivotY - 2 * j] = '+';
                }
                for (int j=1;j<=c;j++){
                    answer[pivotX+2*j-1][pivotY]='|';
                    answer[pivotX+2*j][pivotY]='+';
                }
            }
            else{
                answer[pivotX+1][pivotY-1]='/';
                for (int j=1;j<=a;j++){
                    answer[pivotX][pivotY-2*j+1]='-';
                    answer[pivotX][pivotY-2*j]='+';
                    answer[pivotX+1][pivotY-2*j-1]='/';
                    answer[pivotX+1][pivotY-2*j]='.';
                }
                for (int j=1;j<=c;j++){
                    answer[pivotX+2*j-1][pivotY]='|';
                    answer[pivotX+2*j][pivotY]='+';
                    answer[pivotX+2*j][pivotY-1]='.';
                    answer[pivotX+2*j+1][pivotY-1]='/';
                }
            }
        }
        for (int i=1;i<=c;i++){
            for (int j=1;j<=a;j++){
                answer[2*b+2*i-1][2*j-2]='|';
                answer[2*b+2*i-1][2*j-1]='.';
                answer[2*b+2*i][2*j-2]='+';
                answer[2*b+2*i][2*j-1]='-';
            }
        }
        for (int i=0;i<2*b;i++){
            for (int j=0;j<2*b-i;j++){
                answer[i][j]='.';
                answer[2*b+2*c-i][2*b+2*a-j]='.';
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        Scanner in =new Scanner(System.in);
        int zhixincishu=in.nextInt();
        char[][][] answers=new char[zhixincishu][][];
        int a=0;int b=0; int c=0;
        for (int i=0;i<zhixincishu;i++){
            a=in.nextInt();
            b=in.nextInt();
            c=in.nextInt();
            answers[i]=threeDPrint(a,b,c);
        }
        for (int i=0;i<zhixincishu;i++){
            for (int j=0;j<answers[i].length;j++){
                for (int k=0;k<answers[i][0].length;k++){
                System.out.print(answers[i][j][k]);
                if (k==answers[i][0].length-1){
                    System.out.println();
                    }
                }
            }
        }
    }
}
