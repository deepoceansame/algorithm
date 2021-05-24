
import org.junit.Test;

import java.math.BigInteger;
import java.util.Scanner;

public class Hanoi {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int zhixincishu=in.nextInt();
        int n=0;
        BigInteger three=new BigInteger("3");
        BigInteger[] answers=new BigInteger[zhixincishu];
        for (int i=0;i<zhixincishu;i++){
            n=in.nextInt();
            answers[i]=three.pow(n).add(new BigInteger("-1"));
        }
        for (BigInteger b:answers){
            System.out.println(b.mod(new BigInteger("1000000007")));
        }
    }

    @Test
    public void test(){
        BigInteger in=new BigInteger("217871987498122");
        BigInteger ia=new BigInteger("-220000000000000");
        System.out.println(in.add(ia));
        //217871987498122
        //220000000000000
    }
}
