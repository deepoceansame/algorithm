import java.util.Scanner;

public class BiggestFrontAbstractBack {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int zhixincishu=in.nextInt();
        int geshu=0;
        int[] answers=new int[zhixincishu];
        for (int i=0;i<zhixincishu;i++){
        geshu=in.nextInt();
        int[] biggestBefore=new int[geshu];
        int[] numberEnter=new int[geshu];
        int answer=0;
        for (int j=0;j<geshu;j++){
            if (j==0){
                numberEnter[j]=in.nextInt();
            }
            else if (j==1){
                biggestBefore[j]= numberEnter[0];
                numberEnter[j]=in.nextInt();
                answer=biggestBefore[j]-numberEnter[j];
            }
            else{
                biggestBefore[j]=Math.max(biggestBefore[j-1],numberEnter[j-1]);
                numberEnter[j]=in.nextInt();
                answer=Math.max(answer,biggestBefore[j]-numberEnter[j]);
                }
            }
            answers[i]=answer;
        }
        for (int i=0;i<zhixincishu;i++){
            if (i==answers.length)
                System.out.print(answers);
            else
                System.out.print(answers[i]+" ");
        }
    }
}
