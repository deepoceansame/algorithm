import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WeakMST {

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

    private static class Prim{
        MyIndexPQ<Integer> pq;
        int[] disTo;
        WEdge[] edgeTo;
        boolean[] marked;
        long zong=0;
        public Prim(WGraph g){
            pq=new MyIndexPQ<>(g.V);
            disTo=new int[g.V];
            edgeTo=new WEdge[g.V];
            marked=new boolean[g.V];
            for (int i=0;i<g.V;i++){
                disTo[i]=Integer.MAX_VALUE;
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
                    if (disTo[w]>e.weight){
                        disTo[w]=e.weight;
                        edgeTo[w]=e;
                        if (pq.contain(w)) pq.change(w,e.weight);
                        else pq.insert(w,e.weight);
                    }
                }
            }
        }
    }
    private static class MyIndexPQ<T extends Comparable<? super T>>{
        public Object[] items;
        public int[] pos;
        public int[] arr;
        public int size;

        public MyIndexPQ(int cap){
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
                if (j<size && ((T)items[arr[j+1]]).compareTo(zuo)<0) j++;
                if (now.compareTo((T)items[arr[j]])>0)
                    exch(k,j);
                k=j;
            }
        }

        public void goup(int k){
            while (k>1 && ((T)items[arr[k]]).compareTo((T)items[arr[k/2]])<0){
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
        int v=in.nextInt();
        int e=in.nextInt();
        WGraph g=new WGraph(v,e);
        for (int i=0;i<e;i++){
            g.addEdge(new WEdge(in.nextInt()-1,in.nextInt()-1,in.nextInt()));
        }
        Prim p=new Prim(g);
        out.println(p.zong);
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
}
