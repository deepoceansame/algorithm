

import java.util.Scanner;

public class SumOfDigit {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int zhixincishu=in.nextInt();
        Long[] answers=new Long[zhixincishu];
        long a=0; int b=0;
        String as="";
        int digitSum=0;
        int index=0;
        boolean allZero=true;
        for (int i=0;i<zhixincishu;i++){
            a= in.nextLong();
            b=in.nextInt();
            as=a+"";
            char[] chars=as.toCharArray();
            for (int j=0;j< chars.length;j++){
                digitSum+=Character.getNumericValue(chars[j]);
                if (digitSum > b) {
                    index=j;
                    break;
                }
                else if (digitSum ==b){
                    index=j;
                    for (int k=j+1;k<chars.length;k++){
                        allZero=(chars[k]=='0');
                        if (allZero==false)
                            break;
                    }
                    break;
                }
            }

            if ((digitSum==b && allZero) || digitSum<b ){
                answers[i]=0L;
                allZero=true;
                digitSum=0;
                index=0;
                continue;
            }

            if (index==0){
                String newNumber="1";
                for (int j=0;j<chars.length;j++){
                    newNumber+="0";
                }
                answers[i]=Long.parseLong(newNumber)-a;
                allZero=true;
                digitSum=0;
                index=0;
            }

            else{
               String qian="";
               for (int j=0;j<index;j++)
                   qian+=chars[j];
               qian=(Long.parseLong(qian)+1)+"";
               for (int j=0;j<chars.length-index;j++)
                   qian+="0";
               answers[i]=Long.parseLong(qian)-a;
                allZero=true;
                digitSum=0;
                index=0;
            }    
        }
        for (long along:answers){
            System.out.println(along);
        }
    }
}

