import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SCCCollapse {
    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int info=in.nextInt();
        int nos=in.nextInt();
        int t=in.nextInt();
        sofi[] ss=new sofi[info];
        int[] date=new int[nos];
        for (int i=0;i<info;i++){
            ss[i]=new sofi(in.nextInt()-1,in.nextInt()-1);
        }
        for (int i=0;i<nos;i++){
            date[i]=in.nextInt();
        }
        MyDGraph g=new MyDGraph(nos,0);
        int a=0;
        int b=0;
        for (int i=0;i<info;i++){
            a=ss[i].s1;
            b=ss[i].s2;
            if (date[a]-date[b]==-1)
                g.addEdge(a,b);
            else if(date[b]-date[a]==-1)
                g.addEdge(b,a);
            else if(date[b]==t-1&&date[a]==0)
                g.addEdge(b,a);
            else if (date[a]==t-1&&date[b]==0)
                g.addEdge(a,b);
        }
        Collapse cc=new Collapse(g);
        MyDGraph cg=cc.cg;
        int[] idnum=cc.idnum;
        int answer=Integer.MAX_VALUE;
        for (int i=0;i<cg.V;i++){
            if (cg.adj(i).size()==0){
                if (idnum[i]<answer)
                    answer=idnum[i];
            }
        }
        out.println(answer);
        out.close();
    }

    private static class sofi{
        int s1;
        int s2;
        sofi(int a,int b){
            s1=a;
            s2=b;
        }
    }
    private static class Collapse{
        MyDGraph cg;
        int[] id;
        int[] idnum;
        public Collapse(MyDGraph g){
            Kosaraju kos=new Kosaraju(g);
            id= kos.id;
            int count=kos.count;
            idnum=new int[count];
            for (int i=0;i<g.V;i++){
                idnum[id[i]]++;
            }
            MyDGraph dg=new MyDGraph(count,0);
            for (int i=0;i<g.V;i++){
                for (int a: g.adj(i)){
                    if (id[i]!=id[a]){
                        dg.addEdge(id[i],id[a]);
                        dg.E++;
                    }
                }
            }
            cg=dg;
        }
    }

    private static class MyDGraph {
        int V;
        int E;
        ArrayList<Integer>[] adj;

        public MyDGraph(int v, int e) {
            V = v;
            E = e;
            adj = new ArrayList[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new ArrayList<Integer>(50);
            }
        }

        public void addEdge(int from, int to) {
            adj[from].add(to);
        }

        public ArrayList<Integer> adj(int v) {
            return adj[v];
        }

        public MyDGraph gr(){
            MyDGraph gr=new MyDGraph(V,E);
            ArrayList<Integer> alist=null;
            for (int i=0;i<V;i++){
                alist=adj[i];
                for (int b:alist){
                    gr.addEdge(b,i);
                }
            }
            return gr;
        }
    }

    private static class DepthFirstOrder{
        MyStack<Integer> reversePost;
        boolean[] marked;


        public DepthFirstOrder(MyDGraph g){
            reversePost=new MyStack<>();
            marked=new boolean[g.V];

            for (int i=0;i<g.V;i++){
                if (!marked[i])
                    dfs(g,i);
            }
        }

        public void dfs(MyDGraph g,int v){
            marked[v]=true;
            ArrayList<Integer> alist=g.adj(v);
            for (int a:alist){
                if (!marked[a])
                    dfs(g,a);
            }
            reversePost.push(v);
        }
    }

    private static class Kosaraju{
        boolean[] marked;
        int[] id;
        int count;
        public Kosaraju(MyDGraph g){
            marked=new boolean[g.V];
            id=new int[g.V];
            count=0;
            MyDGraph gr=g.gr();
            DepthFirstOrder dfo=new DepthFirstOrder(gr);
            MyStack<Integer> sta=dfo.reversePost;
            int a=0;
            while (!sta.isEmpty()){
                a=sta.pop();
                if (!marked[a]) {
                    dfs(g, a);
                    count++;
                }
            }
        }

        public void dfs(MyDGraph g,int v){
            marked[v]=true;
            id[v]=count;
            for (int a:g.adj(v)){
                if (!marked[a])
                    dfs(g,a);
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

    private static class MyStack<T> {
        MyList<T> list;

        public MyStack() {
            list = new MyList<>(null);
        }

        public void push(T t) {
            list.addBack(t);
        }

        public T pop() {
            if (list.point == null) {
                return null;
            }
            T t = list.getFinal();
            list.delete();
            return t;
        }

        public T peek() {
            return list.getFinal();
        }

        public boolean isEmpty() {
            return list.daxiao == 0;
        }

        public int size() {
            return list.daxiao;
        }

        public void clear() {
            list = new MyList<>(null);
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
