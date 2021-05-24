

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Merge {
    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        int zhixincishu=in.nextInt();
        long[][] answer=new long[zhixincishu][];
        for (int i=0;i<zhixincishu;i++){
            int alength=in.nextInt();
            int blength=in.nextInt();
            long[] arr1=new long[alength];
            long[] arr2=new long[blength];
            for (int j=0;j<alength;j++){
                arr1[j]=in.nextInt();
            }
            for (int j=0;j<blength;j++){
                arr2[j]=in.nextInt();
            }
            answer[i]=combine(arr1,arr2);
        }
        for (int i=0;i<zhixincishu;i++){
            for (int j=0;j<answer[i].length;j++){
                if (j==answer[i].length-1)
                    System.out.println(answer[i][j]);
                else
                    System.out.print(answer[i][j]+" ");
            }
        }
    }

    public static  long[] combine(long[] arr1,long[] arr2){
        int alength=arr1.length;
        int blength=arr2.length;
        long[] arr3=new long[alength+blength];
        int ind1=0;
        int ind2=0;
        int ind3=0;
        while(true){
            if (ind3>=arr3.length)
                break;
            if (ind1==arr1.length){
                while(ind3<arr3.length){
                    arr3[ind3]=arr2[ind2];
                    ind2++;
                    ind3++;
                }
                break;
            }
            else if (ind2==arr2.length){
                while(ind3<arr3.length){
                    arr3[ind3]=arr1[ind1];
                    ind1++;
                    ind3++;
                }
                break;
            }


            if (arr1[ind1]<arr2[ind2]){
                arr3[ind3]=arr1[ind1];
                ind3++;
                ind1++;
            }
            else if (arr1[ind1]>arr2[ind2]){
                arr3[ind3]=arr2[ind2];
                ind3++;
                ind2++;
            }
            else {
                arr3[ind3]=arr1[ind1];
                ind3++;
                ind1++;
                arr3[ind3]=arr2[ind2];
                ind3++;
                ind2++;
            }
        }
        return arr3;
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

