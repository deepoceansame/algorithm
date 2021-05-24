import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class WeakTreeArray {
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

    public static void main(String[] args) throws IOException {
        /*Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int n=in.nextInt();
        int[] nums=new int[n];
        for (int i=0;i<n;i++){
            nums[i]=in.nextInt();
        }
        Discretization dis=new Discretization(nums);
        int[] tong=new int[dis.size+1];
        MyTreeArray treeArray=new MyTreeArray(tong);
        long ans=0;
        int id=0;
        for (int i=n-1;i>=0;i--){
            id=dis.getId(nums[i]);
            treeArray.update(id,1);
            ans+= treeArray.getPreSum(id-1);
        }
        out.println(ans);
        out.close();*/
        int[] a=new int[]{1,3,4,2,3};
        Discretization dis=new Discretization(a);
        int size= dis.size;
        int[] tong=new int[size+1];
        MyTreeArray myTreeArray=new MyTreeArray(tong);
        int id=1;
        int[] ans=new int[a.length];
        for (int i=0;i<a.length;i++){
            id=dis.getId(a[i]);
            myTreeArray.update(id,1);
            ans[i]=myTreeArray.getPosSum(id+1);
        }
        for (int zz:ans){
            System.out.println(zz);
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
