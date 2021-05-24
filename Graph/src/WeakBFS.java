import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WeakBFS {


    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int v=in.nextInt();
        int e=in.nextInt();
        int t1=in.nextInt()-1;
        int t2=in.nextInt()-1;
        ArrayListGraph g=new ArrayListGraph(v,e);
        for (int i=0;i<e;i++){
            g.addEdge(in.nextInt()-1,in.nextInt()-1);
        }
        BFS b1=new BFS(g,t1,t2);
        BFS b2=new BFS(g,t2,t1);
        int c1=b1.count-1;
        int c2=b2.count-1;
        out.println((v-2-c1)*(v-2-c2));
        out.close();
    }

    private static class BFS{
        boolean[] marked;
        MyQueue<Integer> que=new MyQueue<>();
        int count;

        public BFS(ArrayListGraph g,int s,int b){
            marked=new boolean[g.V];
            que.enqueue(s);
            marked[b]=true;
            marked[s]=true;
            count=0;
            while(!que.isEmpty()){
                int now= que.dequeue();
                count++;
                for (int v:g.adj(now)) {
                    if (!marked[v]){
                        que.enqueue(v);
                        marked[v]=true;
                    }
                }
            }
        }
    }

    private static class ArrayListGraph{
        public int V;
        public int E;
        ArrayList<Integer>[] adj;

        public  ArrayListGraph(int v,int e) {
            V=v;
            E=e;
            adj=new ArrayList[V];
            for (int i=0;i<V;i++){
                adj[i]=new ArrayList<>(50);
            }
        }

        public ArrayList<Integer> adj(int v){
            return adj[v];
        }

        public void addEdge(int either,int other){
            adj(either).add(other);
            adj(other).add(either);
        }
    }

    private static class MyQueue<T>{
        public MyList<T> list;
        public MyQueue(){
            list=new MyList<>(null);
        }

        public void enqueue(T t){
            list.moveToFinal();
            list.addBack(t);
        }

        public T dequeue(){
            if (list.point==null){
                return null;
            }
            list.moveToHead();
            T t=list.point.val;
            list.delete();
            return t;
        }

        public boolean isEmpty(){
            return list.daxiao==0;
        }

        public int size(){
            return list.daxiao;
        }

        public T getFront(){
            return list.getHead();
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
            if (finaNode==null){
                return;
            }
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
