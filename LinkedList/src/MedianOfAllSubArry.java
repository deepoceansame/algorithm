import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;



public class MedianOfAllSubArry {
    static MyPQ<Integer> dading=null;
    static MyPQ<Integer> xiaoding=null;

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        int zhixincishu=in.nextInt();

        Integer [][] answers=new Integer[zhixincishu][];
        int daxiao=0;
        int[] shuzhu=new int[300000];
        for(int i=0;i<zhixincishu;i++){
            daxiao=in.nextInt();
            answers[i]=new Integer[daxiao];
             dading=new MyPQ<>(daxiao/2+2,(x,y)->x-y);
             xiaoding=new MyPQ<>(daxiao/2+2,(x,y)->y-x);
            for (int j=0;j<daxiao;j++){
                shuzhu[j]=in.nextInt();
            }
            for (int j=0;j<daxiao;j=j+2){
                if (j==0){
                    dading.offer(shuzhu[j]);
                    answers[i][0]= shuzhu[j];
                }
                else{
                    addNum(shuzhu[j-1]);
                    addNum(shuzhu[j]);
                    if (dading.size()>xiaoding.size()){
                        answers[i][j/2]=dading.peek();
                    }
                    else{
                        answers[i][j/2]=xiaoding.peek();
                    }
                }
            }
            dading.clear();
            xiaoding.clear();
        }
        for (int i=0;i<zhixincishu;i++){
            for (int j=0;answers[i][j]!=null;j++){
                if (answers[i][j+1]==null){
                    System.out.print(answers[i][j]);
                    if (i!=zhixincishu-1)
                    System.out.println();
                }
                else
                System.out.print(answers[i][j]+" ");
            }
        }
    }

    public static void addNum(Integer a){
        if (dading.size()==xiaoding.size()){
            if (a>=dading.peek())
                xiaoding.offer(a);
            else
                dading.offer(a);
        }

        else if (dading.size()>xiaoding.size()){
            if (a<=dading.peek()){
                xiaoding.offer(dading.poll());
                dading.offer(a);
            }
            else{
                xiaoding.offer(a);
            }
        }

        else {
            if (a<xiaoding.peek()){
                dading.offer(a);
            }
            else{
                dading.offer(xiaoding.poll());
                xiaoding.offer(a);
            }
        }
    }



    private static class MyPQ<T>{
        Object[] arr;
        Comparator<T> comp;
        int size=0;
        int shuzhudaxiao;
        public MyPQ (int shuzhudaxiao,Comparator<T> comparator){
            arr=new Object[shuzhudaxiao];
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
            for (int i=0;i<size+1;i++){
                arr[i]=null;
            }
            size=0;
        }
    }
    private static class Reader {
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

