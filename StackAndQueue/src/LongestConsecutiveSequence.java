import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class LongestConsecutiveSequence {
    public static MyDeque<Spec> max=new MyDeque<>();
    public static MyDeque<Spec> min=new MyDeque<>();
    public static void main(String[] args) throws IOException {
        Reader in=new Reader();

        int k=in.nextInt();
        int n=in.nextInt();
        int L=0;
        int R=0;
        int length=0;
        int cha=0;
        Spec[] nums=new Spec[n];
        for (int i=0;i<n;i++){
            nums[i]=new Spec(in.nextInt(),i);
        }
        addtoMax(nums[L]);
        addtoMin(nums[R]);
        while(R<n && L<n){
            cha=max.getFront().theInt-min.getFront().theInt;
            if (cha<=k){
                length=Math.max(R-L+1,length);
                R++;
                if (R<n){
                    addtoMax(nums[R]);
                    addtoMin(nums[R]);
                }
            }
            else{
                if (R==n-1)
                    break;
                L++;
                delFront(L);
            }
        }
        System.out.println(length);
    }

    public static void addtoMax(Spec a){
        while(!max.isEmpty() && max.getRear().theInt<=a.theInt){
            max.popRear();
        }
        max.enRear(a);
    }

    public static void addtoMin(Spec a){
        while(!min.isEmpty() && min.getRear().theInt>=a.theInt){
            min.popRear();
        }
        min.enRear(a);
    }

    public static void delFront(int L){
        while(!min.isEmpty() && min.getFront().ind<L){
            min.popFront();
        }
        while(!max.isEmpty() && max.getFront().ind<L){
            max.popFront();
        }
    }


    private static class Spec{
        public int theInt;
        public int ind;
        public Spec(int a,int ind){
            this.theInt=a;
            this.ind=ind;
        }
    }

    private static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException
        {
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

        public double nextDouble() throws IOException
        {
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

            if (c == '.')
            {
                while ((c = read()) >= '0' && c <= '9')
                {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }


    private static class MyDeque<T>{
        public MyList<T> list;

        public MyDeque(){
            list=new MyList<>(null);
        }
        public void enFront(T t){
            list.moveToHead();
            list.add(t);
        }

        public void enRear(T t){
            list.moveToFinal();
            list.addBack(t);
        }

        public T popFront(){
            if (list.point==null){
                return null;
            }
            else{
                list.moveToHead();
                T t=list.point.val;
                list.delete();
                return t;
            }
        }

        public T popRear(){
            if (list.point==null){
                return null;
            }
            else{
                list.moveToFinal();;
                T t=list.point.val;
                list.delete();;
                return t;
            }
        }

        public T getRear(){
            if (list.point==null){
                return null;
            }
            else{
                list.moveToFinal();
                return list.point.val;
            }
        }

        public T getFront(){
            if (list.point==null){
                return null;
            }
            else{
                list.moveToHead();
                return list.point.val;
            }
        }

        public int size(){
            return list.daxiao;
        }

        public void clear(){
            list=new MyList<>(null);
        }

        public boolean isEmpty(){
            return list.daxiao==0;
        }

        public void print(){
            list.moveToHead();
            for (int i=0;i< list.daxiao;i++){
                System.out.print(list.point.val+" ");
                list.moveRight();
            }
            System.out.println();
        }

        public void reserveAndAbsorb(MyDeque<T> tt){
            int k=tt.list.daxiao;
            for (int i=0;i<k;i++){
                this.enRear(tt.popRear());
            }
        }

        public void absorb(MyDeque<T> tt){
            int k=tt.list.daxiao;
            for (int i=0;i<k;i++){
                this.enRear(tt.popFront());
            }
        }
    }

    private static class Node<T> {
        public T val;
        public Node<T> next;
        public Node<T> prev;

        public Node(T value) {
            val = value;
        }
    }

    private static class MyList<T> {
        public Node<T> point;
        public Node<T> headNode;
        public Node<T> finaNode;
        public int nowIndex = 0;
        public int daxiao=0;

        public MyList(Node<T> a) {
            point = a;
            headNode = a;
            finaNode = a;
            if (a!=null)
                daxiao++;
        }

        public void moveRight() {
            if (point.next == null)
                ;
            else{
                point = point.next;
                nowIndex++;
            }
        }

        public void moveToHead(){
            point=headNode;
            nowIndex=0;
        }
        public void moveToFinal(){
            point=finaNode;
            nowIndex=daxiao-1;
        }
        public void moveLeft() {
            if (point.prev==null)
                ;
            else{
                point = point.prev;
                nowIndex--;
            }
        }

        public void replace(T a) {
            point.val = a;
        }

        public void add(T a) {
            if (point==null){
                Node<T> newnode=new Node<T>(a);
                point=newnode;
                headNode=newnode;
                finaNode=newnode;
                daxiao++;
            }
            else{
                if (point.prev == null) {
                    Node<T> newnode = new Node<>(a);
                    point.prev = newnode;
                    newnode.next = point;
                    headNode = newnode;
                }
                else {
                    Node<T> newnode = new Node<>(a);
                    point.prev.next = newnode;
                    newnode.prev = point.prev;
                    newnode.next = point;
                    point.prev = newnode;
                }
                daxiao++;
                nowIndex++;
            }
        }
        public void addBack(T a){
            if (point==null){
                Node<T> newnode=new Node<T>(a);
                point=newnode;
                headNode=newnode;
                finaNode=newnode;
            }
            else{
                if (point.next==null){
                    Node<T> newnode=new Node<>(a);
                    point.next=newnode;
                    newnode.prev=point;
                    finaNode=newnode;
                }
                else{
                    Node<T> newnode=new Node<>(a);
                    point.next.prev=newnode;
                    newnode.prev=point;
                    newnode.next=point.next;
                    point.next=newnode;
                }
            }
            daxiao++;
            moveRight();
        }
        public void delete() {
            if (point==null){

            }
            else{
                if (point.prev == null && point.next != null) {
                    Node<T> newHead = point.next;
                    point.next=null;
                    headNode = newHead;
                    newHead.prev = null;
                    point = newHead;
                    daxiao--;
                }
                else if(point.prev==null && point.next==null){
                    point=null;
                    headNode=null;
                    finaNode=null;
                    daxiao=0;
                }
                else if (point.prev!=null && point.next==null){
                    point=finaNode.prev;
                    finaNode.prev=null;
                    finaNode=point;
                    finaNode.next=null;
                    nowIndex--;
                    daxiao--;
                }
                else{
                    Node<T> qian=point.prev;
                    Node<T> hou=point.next;
                    point.next=null;
                    point.prev=null;
                    qian.next=hou;
                    hou.prev=qian;
                    point=hou;
                    daxiao--;
                }
            }
        }

        public void cutAndGive(int a,MyList<T> alist){
            if (this.daxiao==0){
                return;
            }
            Node<T> pastFinal =finaNode;
            point=headNode;
            int yuandaxiao=daxiao;
            for (int i=0;i<a;i++){
                moveRight();
            }
            Node<T> begin=point;
            if (begin.prev!=null) {
                finaNode = begin.prev;
                point=finaNode;
                finaNode.next=null;
                daxiao=a;
                begin.prev = null;
            }
            else{
                point=null;
                headNode=null;
                finaNode=null;
                daxiao=0;
                nowIndex=0;
            }
            if (alist.daxiao==0){
                alist.addBack(null);
                alist.point.next=begin;
                begin.prev=alist.point;
                alist.delete();
                alist.daxiao= yuandaxiao-a;
                alist.finaNode=pastFinal;
            }
            else{
                alist.point=alist.finaNode;
                alist.point.next=begin;
                begin.prev=alist.point;
                alist.finaNode=pastFinal;
                alist.daxiao= alist.daxiao+yuandaxiao-a;
            }
        }

        public T getHead(){
            return headNode.val;
        }

        public T getFinal(){
            return finaNode.val;
        }
    }

}
