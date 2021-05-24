import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Mahjong {

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int geshu=in.nextInt();
        Lanran[] lans=new Lanran[geshu];
        for (int i=0;i<geshu;i++){
            lans[i]=new Lanran(in.nextInt(),in.nextInt());
        }
        Arrays.sort(lans,new LanranComparator());
        int[] tvs=new int[geshu];
        for (int i=0;i<geshu;i++){
            tvs[i]=lans[i].tv;
        }
        Discretization dis=new Discretization(tvs);
        int[] tong=new int[dis.size+1];
        MyTreeArray treeArray=new MyTreeArray(tong);
        int[] tans=new int[tvs.length];
        int id=0;
        for (int i=0;i<tvs.length;i++){
            id=dis.getId(tvs[i]);
            treeArray.update(id,1);
            tans[i]=treeArray.getPosSum(id+1);
        }
        int[] tans1=new int[geshu];
        for (int i=0;i<geshu;i++){
            tans1[i]=i;
        }
        int[] ans=new int[geshu];
        for (int i=0;i<geshu;i++){
            ans[i]=tans1[i]-tans[i];
        }
        int[] lasAns=new int[geshu];
        for (int i=0;i<geshu;i++){
            lasAns[ans[i]]++;
        }
        for (int i=0;i<geshu;i++){
            out.println(lasAns[i]);
        }
        out.close();
    }

    private static class Lanran{
        int tv;
        int fv;
        int ind;
        public Lanran(int tv,int fv){
            this.tv=tv;
            this.fv=fv;
        }
    }

    private static class MyTreeArray{
        int[] A;
        int[] C;
        int len;
        public MyTreeArray(int[] a){
            len=a.length;
            A=a;
            C=new int[len];
            for (int i=1;i<len;i++){
                update(i,a[i]);
            }
        }

        public int lowbit(int x){
            return x&(-x);
        }

        public void update(int i,int k){
            A[i]=A[i]+k;
            while(i<C.length){
                C[i]+=k;
                i+=lowbit(i);
            }
        }

        public int getPreSum(int n){
            if (n>=C.length){
                n=C.length-1;
            }
            if (n<=0){
                return 0;
            }
            int sum=0;
            while(n>=1){
                sum+=C[n];
                n-=lowbit(n);
            }
            return sum;
        }

        public int getPosSum(int n){
            if (n>=C.length){
                return 0;
            }
            if (n<=0){
                n=1;
            }
            return getPreSum(C.length-1)-getPreSum(n-1);
        }

    }

    private static class Discretization{
        int[] discrete;
        int size;
        public Discretization(int[] nums){
            Set<Integer> set=new HashSet<>();
            for (int num:nums){
                set.add(num);
            }
            size=set.size();
            discrete=new int[size];
            int ind=0;
            for (int num:set){
                discrete[ind]=num;
                ind++;
            }
            Arrays.sort(discrete);
        }
        public int getId(int x){
            return Arrays.binarySearch(discrete,x)+1;
        }
    }

    private static class LanranComparator implements Comparator<Lanran>{
        @Override
        public int compare(Lanran o1, Lanran o2) {
            if (o1.fv> o2.fv){
                return 1;
            }
            else if (o1.fv<o2.fv){
                return -1;
            }
            else {
                if (o1.tv>o2.tv){
                    return 1;
                }
                else{
                    return -1;
                }
            }
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
