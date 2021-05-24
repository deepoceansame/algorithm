import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class LineIntoHouse {
    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int peoNum=in.nextInt();
        int houseNum=in.nextInt();
        int capac=in.nextInt();
        int range=in.nextInt();
        int[] peoPosi=new int[peoNum];
        for (int i=0;i<peoNum;i++){
            peoPosi[i]=in.nextInt();
        }
        int[] houPosi=new int[houseNum];
        for (int i=0;i<houseNum;i++){
            houPosi[i]=in.nextInt();
        }
        Arrays.sort(houPosi);
        Arrays.sort(peoPosi);
        int i=0;
        int j=0;
        int count=0;
        int ans=0;
        while(i<houseNum&&j<peoNum){
            if (peoPosi[j]<houPosi[i]){
                if (houPosi[i]-peoPosi[j]>range){
                    j++;
                }
                else{
                    ans++;
                    j++;
                    count++;
                    if (count==capac){
                        i++;
                        count=0;
                    }
                }
            }
            else{
                if (peoPosi[j]-houPosi[i]>range){
                    i++;
                    count=0;
                }
                else{
                    ans++;
                    j++;
                    count++;
                    if (count==capac){
                        i++;
                        count=0;
                    }
                }
            }
        }
        out.println(ans);
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
