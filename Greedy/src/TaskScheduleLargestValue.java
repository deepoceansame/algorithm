import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class TaskScheduleLargestValue {
    public static Task[] taskList1;
    public static int[] activeTime;
    public static int[] taskOfTime;
    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int n=in.nextInt();
         taskList1=new Task[n];
         activeTime=new int[n];
         taskOfTime=new int[n];
        for (int i=0;i<n;i++){
            taskList1[i]=new Task(in.nextInt(),in.nextInt(),in.nextInt());
            taskOfTime[i]=-1;
        }
        Arrays.sort(taskList1, Comparator.comparingInt(a -> a.sta));
        int x=0;
        for (int i=0;i<n;i++){
            x=Math.max(taskList1[i].sta,x+1);
            activeTime[i]=x;
        }
        Arrays.sort(taskList1,(a,b)->b.wei-a.wei);
        long answer=0;
        for (int i=0;i<n;i++){
            int sta=0;
            while (activeTime[sta]< taskList1[i].sta) sta++;
            if (find(sta,i)){
                answer+=taskList1[i].wei;
            }
        }
        out.println(answer);
        out.close();
    }

    public static boolean find(int sta,int i){
        int j=0;
        if (activeTime[sta]>taskList1[i].ter)
            return false;
        if (taskOfTime[sta]==-1){
            taskOfTime[sta]=i;
            return true;
        }
        else{
            j=taskOfTime[sta];
            if (taskList1[i].ter>taskList1[j].ter){
                return find(sta+1,i);
            }
            else{
                if (find(sta+1,j)){
                    taskOfTime[sta]=i;
                    return true;
                }
            }
        }
        return false;
    }

    private static class Task{
        int sta;
        int ter;
        int wei;
        int nowTime;

        public Task(int sta, int ter, int wei) {
            this.sta = sta;
            this.ter = ter;
            this.wei = wei;
            this.nowTime=-1;
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
