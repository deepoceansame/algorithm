
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;


public class divideWater {
    public static void main(String[] args) throws IOException {
       Reader in=new Reader();
       PrintWriter out=new PrintWriter(System.out);
       int zhixincishu=in.nextInt();
       int s=0;
       int n=0;
       int m=0;
       int ans=0;
       MyQueue<ThreeCupNodes> que=null;
       for (int i=0;i<zhixincishu;i++){
            s=in.nextInt();
            n=in.nextInt();
            m=in.nextInt();
            if (s%2!=0){
                out.println("impossible");
                continue;
            }
            que=new MyQueue<>();
            ThreeCupNodes origin=new ThreeCupNodes(n,m,s,0,0);
            origin.cen=0;
            que.enqueue(origin);
            ans=cishu(que);
            if (ans==-1){
                out.println("impossible");
            }
            else
                out.println(ans);
       }
       out.close();
    }



    public static int cishu(MyQueue<ThreeCupNodes> que){
        int cishu=-1;
        ArrayList<ThreeCupNodes> visited=new ArrayList<>(50);
        ThreeCupNodes[] chNodes=null;
        while(!que.isEmpty()){
            ThreeCupNodes node=que.dequeue();
            if (isGoal(node)){
                cishu=node.cen;
                break;
            }
            visited.add(node);
            chNodes=node.getNewNodes();
            for (ThreeCupNodes nod:chNodes){
                if (!visited.contains(nod) && !que.contain(nod)){
                    nod.cen=node.cen+1;
                    que.enqueue(nod);
                }
            }
        }
        return cishu;
    }

    public static boolean isGoal(ThreeCupNodes n){
        int zong=n.mw+n.sw+n.nw;
        if (n.mw==zong/2 && n.nw==zong/2){
            return true;
        }
        else if (n.mw==zong/2 && n.sw==zong/2){
            return true;
        }
        else if (n.nw==zong/2 && n.sw==zong/2){
            return true;
        }
        else
            return false;
    }

    static class ThreeCupNodes{

        int n;
        int m;
        int sw;
        int nw;
        int mw;
        int cen;
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ThreeCupNodes that = (ThreeCupNodes) o;
            return n == that.n &&
                    m == that.m &&
                    sw == that.sw &&
                    nw == that.nw &&
                    mw == that.mw;
        }

        @Override
        public int hashCode() {
            return Objects.hash(n, m, sw, nw, mw);
        }

        public ThreeCupNodes(int n, int m, int sw, int nw, int mw) {
            this.sw = sw;
            this.n = n;
            this.m = m;
            this.nw = nw;
            this.mw = mw;
        }

        public ThreeCupNodes[] getNewNodes(){
            ArrayList<ThreeCupNodes> list=new ArrayList<>(6);
            //s>n
            if (sw>0 && nw<n){
                if (n-nw>sw){
                    list.add(new ThreeCupNodes(n,m,0,nw+sw,mw));
                }
                else list.add(new ThreeCupNodes(n,m,sw-n+nw,n,mw));
            }

            //s>m
            if (sw>0 && mw<m){
                if (m-mw>sw){
                    list.add(new ThreeCupNodes(n,m,0,nw,mw+sw));
                }
                else{
                    list.add(new ThreeCupNodes(n,m,sw-m+mw,nw,m));
                }
            }

            //n>s
            if (nw>0){
                list.add(new ThreeCupNodes(n,m,sw+nw,0,mw));
            }

            //n>m
            if (nw>0 && mw<m){
                if (m-mw>nw){
                    list.add(new ThreeCupNodes(n,m,sw,0,mw+nw));
                }
                else{
                    list.add(new ThreeCupNodes(n,m,sw,nw-m+mw,m));
                }
            }

            //m>s
            if (mw>0){
                list.add(new ThreeCupNodes(n,m,sw+mw,nw,0));
            }

            //m>n
            if (mw>0 && nw<n){
                if (n-nw>mw){
                    list.add(new ThreeCupNodes(n,m,sw,nw+mw,0));
                }
                else {
                    list.add(new ThreeCupNodes(n,m,sw,n,mw-n+nw));
                }
            }

            ThreeCupNodes[] arr=new ThreeCupNodes[list.size()];
            for (int i=0;i<list.size();i++){
                arr[i]=list.get(i);
            }
            return arr;
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

        public boolean contain(T t){
            boolean c=false;
            list.moveToHead();
            for (int i=0;i<list.daxiao;i++){
                c=t.equals(list.point.val);
                if (c)
                    break;
            }
            return c;
        }
    }
}
