import org.junit.Test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class WhichIsBetter {
    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        int zhixincishu=in.nextInt();
        int shuzhudaxiao=0;
        int[][] sortedarray=new int[zhixincishu][];
        String[] answer=new String[zhixincishu];
        for (int i=0;i<zhixincishu;i++){
            shuzhudaxiao=in.nextInt();
            int[] arr1=new int[shuzhudaxiao];
            int[] arr2=new int[shuzhudaxiao];
            for (int j=0;j<shuzhudaxiao;j++){
                arr1[j]=in.nextInt();
                arr2[j]=arr1[j];
            }
            int inser =insertionSort(arr1);
            int selec =selectionSort(arr2);
            sortedarray[i]=arr1;
            if (inser>selec)
                answer[i]="Selection Sort wins!";
            else
                answer[i]="Insertion Sort wins!";
        }
        for (int i=0;i<zhixincishu;i++){
            for (int j=0;j<sortedarray[i].length;j++){
                if (j==sortedarray[i].length-1)
                    System.out.println(sortedarray[i][j]);
                else
                    System.out.print(sortedarray[i][j]+" ");
            }
            System.out.println(answer[i]);
        }
    }



    public static int selectionSort(int[] list){
        int cost=0;
        for (int i=0;i<list.length-1;i++){
            int k=i;
            for (int j=i+1;j<list.length;j++){
                cost++;
                if (list[k]>list[j])
                    k=j;
            }
            int temp=list[i];
            list[i]=list[k];
            list[k]=temp;
            cost++;
        }
        return cost;
    }

    public static int insertionSort(int[] list){
        int cost=0;
        for (int i=1;i<list.length;i++){
            for (int j=i;j>0;j--){
                cost++;
                if (list[j-1]>list[j]){
                    int temp=list[j];
                    list[j]=list[j-1];
                    list[j-1]=temp;
                    cost++;
                }
                else{
                    break;
                }
            }
        }
        return cost;
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