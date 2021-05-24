import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class NiXvDuiShu {
    public static long count = 0;

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        int zhixincishu=in.nextInt();
        long[] answers=new long[zhixincishu];
        int daxiao=0;
        int[] tempList =new int[100000];
        for (int i=0;i<zhixincishu;i++){
            daxiao=in.nextInt();
            int[] shuzu=new int[daxiao];
            for (int j=0;j<daxiao;j++){
                shuzu[j]=in.nextInt();
            }
            mergeSort(shuzu,0,shuzu.length-1,tempList);
            answers[i]=count;
            count=0;
        }
        for (int i=0;i<answers.length;i++){
                System.out.println(answers[i]);
        }
    }

    public static void mergeSort(int[] list, int lo, int hi, int[] tempList) {
        if (lo < hi) {
            int mi = lo + (hi - lo) / 2;
            mergeSort(list, lo, mi, tempList);
            mergeSort(list, mi + 1, hi, tempList);
            calCount(list,lo,hi,tempList);
        }
    }

    public static void calCount(int[] list, int lo, int hi, int[] tempList) {
        int mi = lo + (hi - lo) / 2;
        int pos1 = mi;
        int pos2 = hi;
        int pos3 = hi;
        while (pos3 >= lo) {
            if (pos1 < lo || pos2 <=mi) {
                if (pos1<lo){
                    while(pos3>=lo){
                        tempList[pos3]=list[pos2];
                        pos3--;
                        pos2--;
                    }
                }
                else{
                    while(pos3>=lo){
                        tempList[pos3]=list[pos1];
                        pos3--;
                        pos1--;
                    }
                }
            }
            else {
                if (list[pos1] > list[pos2]) {
                    count = count + pos2 - mi;
                    tempList[pos3] = list[pos1];
                    pos1--;
                    pos3--;
                }
                else if (list[pos2] > list[pos1]) {
                    tempList[pos3] = list[pos2];
                    pos2--;
                    pos3--;
                }
                else {
                    tempList[pos3]=list[pos2];
                    pos3--;
                    pos2--;
                }
            }
        }

        for (int i=lo;i<=hi;i++){
            list[i]=tempList[i];
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
