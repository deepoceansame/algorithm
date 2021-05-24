import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ThreeSquareDP {
    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int num=in.nextInt();
        int[] weight=new int[num+1];
        for (int i=1;i<=num;i++){
            weight[i]=in.nextInt();
        }
        int[][] opt=new int[num+1][num+1];
        for (int i=1;i<=num;i++){
            opt[i][i]=weight[i];
        }
        int j=0;
        int tempval=0;
        int zuo=0;
        int you=0;
        int zuo1=0;
        int you1=0;
        for (int cha=1;cha<=num-1;cha++){
            for (int base=1;base+cha<=num;base++){

                j=base+cha;
                tempval=Integer.MAX_VALUE;
                for (int k=base;k<j;k++){
                    if (k==base){
                        zuo=0;
                    }
                    else{
                        zuo=opt[base][k];
                    }

                    if (k==j-1){
                        you=0;
                    }
                    else{
                        you=opt[k+1][j];
                    }
                    if (base==1 && j==3){
                        int a=0;
                    }
                    for (int ti=base;ti<=k;ti++){
                        zuo1+=weight[ti];
                    }
                    for (int ti=k+1;ti<=j;ti++){
                        you1+=weight[ti];
                    }
                    tempval=Math.min(tempval,zuo+you+you1+zuo1);
                    zuo1=0;
                    you1=0;
                }
                opt[base][j]=tempval;

            }
        }

        out.println(opt[1][num]);

        out.close();
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
