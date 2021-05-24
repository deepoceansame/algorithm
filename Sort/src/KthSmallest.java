import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class KthSmallest {
    public static void main(String[] args) throws IOException {
       Reader in=new Reader();
       int shuzhudaxiao=in.nextInt();
       int k=in.nextInt();
       int[] shuzhu=new int[shuzhudaxiao];
       for (int i=0;i<shuzhudaxiao;i++){
           shuzhu[i]= in.nextInt();
       }
        System.out.println(findKth(shuzhu,k,0,shuzhudaxiao-1));
    }

    public static int findKth (int[] list,int k,int left,int right){
        int quedingwei=ready(list,left,right);
        int yudingwei=left+k-1;
        if (yudingwei==quedingwei)
            return list[quedingwei];
        else if (quedingwei>yudingwei)
            return findKth(list,k,left,quedingwei-1);
        else
            return findKth(list,k-quedingwei+left-1,quedingwei+1,right);
    }

    public static int ready(int[] list,int left,int right) {
        int temp1=list[left];
        int temp2=list[right];
        int temp3=list[(left+right)/2];
        int zhongwei=zhongweishu(temp1,temp2,temp3);
        if (zhongwei==temp2){
            list[left]=temp2;
            list[right]=temp1;
        }
        else if(zhongwei==temp3){
            list[left]=temp3;
            list[(left+right)/2]=temp1;
        }


        int pivot=list[left];
        while(left<right){
            while(right>left && list[right]>=pivot)
                right--;
            if (right>left)
                list[left]=list[right];
            while(right>left && list[left]<=pivot)
                left++;
            if (right>left)
                list[right]=list[left];
        }
        list[left]=pivot;
        return left;
    }

    public static int zhongweishu(int a,int b,int c){
        int k=Math.max(a,Math.max(c,b));
        int j=Math.min(a,Math.min(c,b));
        int zhongwei=a+b+c-k-j;
        if (zhongwei==k)
            return j;
        else
            return zhongwei;
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
