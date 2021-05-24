import java.util.Random;

public class RandomValue {
    public static void main(String[] args) {
        /*Random random=new Random();
        int zhibiao= 0;
        StringBuilder s=new StringBuilder();
        for (int i=0;i<100;i++){
            zhibiao=random.nextInt(20);
            if (zhibiao<=9)
                s.append(random.nextInt(10));
            else if (zhibiao==10)
                s.append("H");
            else if (zhibiao==11)
                s.append("L");
            else if (zhibiao==12)
                s.append("x");
            else if (zhibiao==13)
                s.append("r").append(random.nextInt(10));
            else if (zhibiao>=14)
                s.append("I");
        }
        System.out.println(s);*/

        /*Random randomb=new Random();
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(1).append("\n");
        int po1=randomb.nextInt(1000);
        stringBuilder.append(po1).append("\n");
        for (int i=0;i<po1;i++){
            stringBuilder.append(Math.random()>0.5?"-":"").append(randomb.nextInt(10000)).append(" ").append(randomb.nextInt(999999999)).append("\n");
        }
        int po2=randomb.nextInt(1000);
        stringBuilder.append(po2).append("\n");
        for (int i=0;i<po2;i++){
            stringBuilder.append(Math.random()>0.5?"-":"").append(randomb.nextInt(10000)).append(" ").append(randomb.nextInt(999999999)).append("\n");
        }
        System.out.println(stringBuilder);*/


       /* Random randomc=new Random();
        StringBuilder stringBuilder1=new StringBuilder();
        stringBuilder1.append(10).append("\n");
        for (int i=0;i<10;i++){
            stringBuilder1.append(20).append("\n");
            for (int j=0;j<20;j++){
                stringBuilder1.append(randomc.nextInt(300000)).append(" ");
            }
            stringBuilder1.append("\n");
        }
        System.out.println(stringBuilder1);*/

        StringBuilder stringBuilder2=new StringBuilder();
        stringBuilder2.append(1).append("\n");
        Random randomd=new Random();
        int caozuo=randomd.nextInt(20);
        stringBuilder2.append("3DcyBvYEoLVh3rMJk3lKWIuuZLxPHaGyRZonzrWS94oOObAWcsCZUw9rUjsCkj8NtJmH2PJt5LumsgBqx8LxrrtXna").append("\n");
        stringBuilder2.append(caozuo).append("\n");
        for (int i=0;i<caozuo;i++){
            stringBuilder2.append(1).append(" ").append('\'').append(" ").append(randomd.nextInt(91)).append("\n");
        }
        System.out.println(stringBuilder2);
    }
}
