import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class DivineSpiritAndInnerFire { //先用等同生命值的法术施加到所有生命值大于攻击力的兽人身上，在遍历一遍看哪个兽人更适合用加倍生命值的法术
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        int renshu = in.nextInt();
        long hptemp = 0;
        long atktemp = 0;
        long canshu = 0;
        long tempcanshu=0;
        long answer = 0;
        long p = in.nextLong();
        long q = in.nextLong();
        long tempq = q;
        Orc[] orcs = new Orc[renshu];
        Orc expectedOrc = new Orc(0, 0);
        Orc zuihoudeOrc =new Orc(0,0);
        for (int i = 0; i < renshu; i++) {
            hptemp = in.nextLong();
            atktemp = in.nextLong();
            orcs[i] = new Orc(hptemp, atktemp);
        }

        if (q == 0) {
            for (int i = 0; i < renshu; i++) {
                answer += orcs[i].atk;
            }
            System.out.println(answer);
            return;
        }

        Orc[] temparr=new Orc[renshu];
        mergeSort(orcs,0,renshu-1,temparr);

        for (int i = 0; i < Math.min(q, renshu); i++) {
            if (orcs[renshu - 1 - i].hp <= orcs[renshu - 1 - i].atk)
                break;
            else {
                orcs[renshu - 1 - i].atk = orcs[renshu - 1 - i].hp;
                orcs[renshu - i - 1].xinhuoed = true;
                tempq--;
                zuihoudeOrc=orcs[renshu - i - 1];
            }
        }
        for(int i=0;i<renshu;i++){
            if ((!orcs[i].xinhuoed)&& tempq<=0){
                tempcanshu= orcs[i].hp*powOfTwo(p)-orcs[i].atk+ zuihoudeOrc.tempatk- zuihoudeOrc.atk;
            }
            else{
                tempcanshu=orcs[i].hp*powOfTwo(p)-orcs[i].atk;

            }
            if (tempcanshu>canshu){
                expectedOrc=orcs[i];
                canshu=tempcanshu;
            }
        }

        if ((!expectedOrc.xinhuoed)&& tempq<=0){
            expectedOrc.hp= expectedOrc.hp*powOfTwo(p);
            expectedOrc.atk= expectedOrc.hp;
            zuihoudeOrc.atk= zuihoudeOrc.tempatk;
        }
        else{
            expectedOrc.hp= expectedOrc.hp*powOfTwo(p);
            expectedOrc.atk= expectedOrc.hp;
        }
        for (int i = 0; i < renshu; i++) {
            answer += orcs[i].atk;
        }
        System.out.println(answer);
    }

    public static long powOfTwo(long a) {
        if (a == 2)
            return 4L;
        if (a == 1)
            return 2L;
        if (a==0)
            return 1L;
        if (a % 2 == 0)
            return powOfTwo(a / 2) * powOfTwo(a / 2);
        else
            return 2 * powOfTwo(a / 2) * powOfTwo(a / 2);
    }

    static class Orc implements Comparable<Orc> {
        public long hp;
        public long atk;
        public long tempatk;
        public boolean xinhuoed = false;

        public Orc(long hp, long atk) {
            this.hp = hp;
            this.atk = atk;
            tempatk = atk;
        }

        @Override
        public int compareTo(Orc o) {
            long zhi1=this.hp-this.atk;
            long zhi2=o.hp-o.atk;
            if (zhi1>zhi2)
                return 1;
            else if (zhi1<zhi2)
                return -1;
            else
                return 0;
        }
    }

    public static void mergeSort(Comparable[] list,int lo,int hi,Comparable[] temparr){
        int mi=lo+(hi-lo)/2;
        if (lo<hi){
            mergeSort(list,lo,mi,temparr);
            mergeSort(list,mi+1,hi,temparr);
            merge(list,lo,hi,temparr);
        }
    }

    public static void merge(Comparable[] list,int lo,int hi,Comparable[] temparr){
        int mi=lo+(hi-lo)/2;
        int ind1=mi;
        int ind2=hi;
        int ind3=hi;
        while(ind3>=lo){
            if (ind1<lo){
                while (ind3>=lo){
                    temparr[ind3]=list[ind2];
                    ind3--;
                    ind2--;
                }
                break;
            }
            else if (ind2<mi+1){
                while (ind3>=lo){
                    temparr[ind3]=list[ind1];
                    ind3--;
                    ind1--;
                }

                break;
            }
            if (ind1>=lo && list[ind1].compareTo(list[ind2])>0){
                temparr[ind3]=list[ind1];
                ind1--;
                ind3--;
            }
            else if (ind2>=mi+1 && list[ind2].compareTo(list[ind1])>0){
                temparr[ind3]=list[ind2];
                ind2--;
                ind3--;
            }
            else{
                temparr[ind3]=list[ind1];
                ind1--;
                ind3--;
                temparr[ind3]=list[ind2];
                ind2--;
                ind3--;
            }
        }
        for (int i=lo;i<=hi;i++){
            list[i]=temparr[i];
        }
    }
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }
}
