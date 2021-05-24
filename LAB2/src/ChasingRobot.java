import java.util.IllegalFormatCodePointException;
import java.util.Scanner;

public class ChasingRobot {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long xr = in.nextInt();
        long yr = in.nextInt();
        long axr=0;
        long ayr=0;
        long xc = in.nextInt();
        long yc = in.nextInt();
        long numberOfSequence = in.nextInt();
        long sequenceX=0;
        long sequenceY=0;
        long bushu=0;
        String ins = in.next();
        if (xr == xc && yr == yc)
            System.out.println(0);   // 先找出遇到机器人的时间是在机器人的第几个指令循环中 （这里用到了二分法） 二分一定要完成由index知内容的结构比如数组、表达式
        else {
            for (int i=0;i<numberOfSequence;i++){
                if (ins.charAt(i)=='U')
                    sequenceY++;
                else if (ins.charAt(i)=='D')
                    sequenceY--;
                else if (ins.charAt(i)=='R')
                    sequenceX++;
                else
                    sequenceX--;
            }

            long jvli=0;
            long lo=0;
            long mid=0;
            long hi=10000000000L;
            while(true){

                if (lo==hi)
                    break;
                mid=(lo+hi)/2;
                axr=xr+mid*sequenceX;
                ayr=yr+mid*sequenceY;
                jvli=Math.abs(axr-xc)+Math.abs(ayr-yc);
                bushu=numberOfSequence*mid;
                if (bushu<jvli)
                    lo=mid+1;
                else if (bushu>=jvli){
                    hi=mid;
                }

            }
            if (lo==10000000000L){
                System.out.println(-1);
                return;
            }

             axr=xr+sequenceX*(lo-1);
             ayr=yr+sequenceY*(lo-1);
             bushu=numberOfSequence*(lo-1);
             for (int i=0;i<numberOfSequence;i++){
                 if (ins.charAt(i)=='U')
                     ayr++;
                 else if (ins.charAt(i)=='D')
                     ayr--;
                 else if (ins.charAt(i)=='R')
                     axr++;
                 else
                     axr--;
                 bushu++;
                 jvli=Math.abs(axr-xc)+Math.abs(ayr-yc);
                 if (jvli<=bushu)
                     break;
             }
            System.out.println(bushu);
        }
    }
}
