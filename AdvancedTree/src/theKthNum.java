import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;

public class theKthNum {
    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int time=in.nextInt();
        int k=in.nextInt();
        int chushi=in.nextInt();
        long answer=0;
        long zuobiao;
        long shu=0;
        long min=0;
        MyPQ<Long> pq=new MyPQ<>(1000000,(Long x,Long y)-> (int) (y-x));
        for (int i=1;i<=time;i++){
            if (answer==0)
                zuobiao=i+chushi;
            else
                zuobiao=answer+i;
            shu=getNum(zuobiao);
            if (shu<min || min==0)
                min=shu;
            if (pq.size<k){
                pq.offer(shu);
            }
            else{
                if (shu>pq.peek()){
                    pq.poll();
                    pq.offer(shu);
                }
            }

            if (i%100==0 && i>=100) {
                if (k > i)
                    answer = min;
                else
                    answer = pq.peek();


                out.print(answer + " ");
            }
        }
        out.close();
    }

    public static long getNum(long a){
        long s=a;
        while(a>0){
            s+=a%10;
            a/=10;
        }
        return s;
    }

    private static class MyPQ<T>{
        Object[] arr;
        Comparator<T> comp;
        int size=0;
        int shuzhudaxiao;
        public MyPQ (int shuzhudaxiao,Comparator<T> comparator){
            arr=new Object[shuzhudaxiao+1];
            comp=comparator;
            arr[0]=null;
            this.shuzhudaxiao=shuzhudaxiao;
        }

        public int left(int father){
            return father*2;
        }

        public int right(int father){
            return father*2+1;
        }

        public int father(int child){
            return child/2;
        }

        public void exch(int a,int b){
            Object temp=arr[a];
            arr[a]=arr[b];
            arr[b]=temp;
        }

        public void goUp(int a){
            while (a>1 && comp.compare((T)arr[a],(T)arr[father(a)])>0){
                exch(a,father(a));
                a=father(a);
            }
        }

        public void goDown(int a){
            while(2*a<=size){
                int j=2*a;
                if (j<size && comp.compare((T)arr[j+1],(T)arr[j])>0) j++;
                if (comp.compare((T)arr[a],(T)arr[j])<0)
                    exch(a,j);
                a=j;
            }
        }

        public void offer(T a){
            arr[size+1]=a;
            size++;
            goUp(size);
        }

        public T peek(){
            return (T)arr[1];
        }

        public T poll(){
            T toReturn =(T)arr[1];
            exch(1,size);
            arr[size]=null;
            size--;
            goDown(1);
            return toReturn;
        }

        public int size(){
            return size;
        }

        public void clear(){
            arr=new Object[shuzhudaxiao];
            size=0;
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
