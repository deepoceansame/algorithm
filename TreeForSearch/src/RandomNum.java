import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class RandomNum {
    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter out = new PrintWriter("D:\\intellij idea\\test\\TreeForSearch\\src\\ran2.txt");
        Random r1 = new Random();
        StringBuilder s1=new StringBuilder();
        int caozuo = 10000;
        int op = 0;
        int zhi = 0;
        int k=0;
        int ind=0;
        s1.append(caozuo).append("\n");
        ArrayList<Integer> list=new ArrayList<>(10000);
        for (int i=0;i<caozuo;i++){
            if (i==caozuo-1){
                int a=0;
            }
            k=r1.nextInt(100);
            if (k<40){
                op=1;
            }
            else if (k>=40 && k<60){
                op=2;
            }
            else if (k>=60 && k<70){
                op=3;
            }
            else if (k>=70 && k<80){
                op=4;
            }
            else if (k>=80 && k<90){
                op=5;
            }
            else
                op=6;
            if (op!=1 && list.size()==0){
                op=1;
            }
            if (op==1){
                zhi=r1.nextInt(10000);
                s1.append(1).append(" ").append(zhi).append("\n");;
                list.add(zhi);
            }
            else if (op==2){
                ind=r1.nextInt(list.size());
                zhi=list.get(ind);
                list.remove(ind);
                s1.append(2).append(" ").append(zhi).append("\n");
            }
            else if (op==3){
                ind=r1.nextInt(list.size());
                zhi=list.get(ind);
                s1.append(3).append(" ").append(zhi).append("\n");
            }
            else if (op==4){
                ind=r1.nextInt(list.size());
                s1.append(4).append(" ").append(ind).append("\n");
            }
            else if (op==5){
                ind=r1.nextInt(list.size());
                zhi=list.get(ind);
                s1.append(5).append(" ").append(zhi).append("\n");
            }
            else{
                ind=r1.nextInt(list.size());
                zhi=list.get(ind);
                s1.append(6).append(" ").append(zhi).append("\n");
            }
        }
        out.println(s1);
    }
}
