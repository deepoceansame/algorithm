import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

public class WeakLST {
    private static class WEdge{
        int v;
        int w;
        int weight;
        public WEdge(int v,int w,int weight){
            this.v=v;
            this.w=w;
            this.weight=weight;
        }

        public int either(){
            return v;
        }

        public int other(int a){
            if (v==a)
                return w;
            else
                return v;
        }
    }

    private static class WGraph{
        ArrayList<WEdge>[] adj;
        int V;
        int E;
        public WGraph(int v,int e){
            adj=new ArrayList[v];
            V=v;
            E=e;
            for (int i=0;i<v;i++){
                adj[i]=new ArrayList<>(50);
            }
        }

        public void addEdge(WEdge edge){
            int v=edge.either();
            int w=edge.other(v);
            adj(v).add(edge);
            adj(w).add(edge);
        }

        public ArrayList<WEdge> adj(int v){
            return adj[v];
        }
    }

    private static class LPrim{
        MyMaxIndexPQ<Integer> pq;
        int[] disTo;
        WEdge[] edgeTo;
        boolean[] marked;
        long zong=0;
        public LPrim(WGraph g){
            pq=new MyMaxIndexPQ<>(g.V);
            disTo=new int[g.V];
            edgeTo=new WEdge[g.V];
            marked=new boolean[g.V];
            for (int i=0;i<g.V;i++){
                disTo[i]=Integer.MIN_VALUE;
            }
            disTo[0]=0;
            pq.insert(0,0);
            while(pq.size>0){
                visit(g,pq.pop());
            }
            for (int i=0;i<g.V;i++){
                zong+=disTo[i];
            }
        }

        public void visit(WGraph g,int v){
            marked[v]=true;
            int w=0;
            for (WEdge e:g.adj(v)){
                w=e.other(v);
                if (marked[w]) continue;
                else{
                    if (disTo[w]<e.weight){
                        disTo[w]=e.weight;
                        edgeTo[w]=e;
                        if (pq.contain(w)) pq.change(w,e.weight);
                        else pq.insert(w,e.weight);
                    }
                }
            }
        }
    }

    private static class MyMaxIndexPQ<T extends Comparable<? super T>>{
        public Object[] items;
        public int[] pos;
        public int[] arr;
        public int size;

        public MyMaxIndexPQ(int cap){
            items=new Object[cap];
            pos=new int[cap];
            arr=new int[cap+1];
            size=0;
        }

        public boolean contain(int key){
            return items[key]!=null;
        }

        public void sink(int k){
            T zuo=null;
            T now=null;
            while(2*k<=size){
                now=((T)items[arr[k]]);
                zuo=(T)items[arr[2*k]];
                int j=2*k;
                if (j<size && ((T)items[arr[j+1]]).compareTo(zuo)>0) j++;
                if (now.compareTo((T)items[arr[j]])<0)
                    exch(k,j);
                k=j;
            }
        }

        public void goup(int k){
            while (k>1 && ((T)items[arr[k]]).compareTo((T)items[arr[k/2]])>0){
                exch(k,k/2);
                k=k/2;
            }
        }

        public void exch(int a,int b){
            pos[arr[a]]=b;
            pos[arr[b]]=a;
            int temp=arr[a];
            arr[a]=arr[b];
            arr[b]=temp;
        }

        public void insert(int k,T t){
            size++;
            pos[k]=size;
            arr[size]=k;
            items[k]=t;
            goup(size);
        }

        public void change(int k,T t){
            items[k]=t;
            sink(pos[k]);
            goup(pos[k]);
        }

        public int pop(){
            int temp=arr[1];
            exch(1,size);
            arr[size]=0;
            size--;
            sink(1);
            pos[temp]=0;
            items[temp]=null;
            return temp;
        }

        public void delete(int k){
            int a=pos[k];
            exch(a,size);
            arr[size]=0;
            size--;
            if (a<=size){
                goup(a);
                sink(a);
            }
            items[k]=null;
            pos[k]=0;
        }
    }

    public static void main(String[] args) throws IOException {
       Reader in=new Reader();
       PrintWriter out=new PrintWriter(System.out);
       int n=in.nextInt();
       int m=in.nextInt();
       int[][] coe=new int[n][m];
       for (int i=0;i<n*m;i++){
           coe[i/m][i%m]=in.nextInt();
       }
       WGraph g=new WGraph(n*m,2*n*m-m-n);
       int i=0;
       int j=0;
       for (int a=0;a<n*m;a++){
           i=a/m;
           j=a%m;
           if (i<n-1){
               g.addEdge(new WEdge(a,a+m,coe[i][j]*coe[i+1][j]));
           }
           if (j<m-1){
               g.addEdge(new WEdge(a,a+1,coe[i][j]*coe[i][j+1]));
           }
       }
       LPrim pri=new LPrim(g);
       out.println(pri.zong);
       out.close();
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

    public static class PortalDijkstra {


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
                while (a>1 && comp.compare((T)arr[a],(T)arr[father(a)])>0){ //默认是大顶堆
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
    }
}
