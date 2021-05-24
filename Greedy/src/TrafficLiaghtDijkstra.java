import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TrafficLiaghtDijkstra {
    private static class MyEWDGraph{
        int V;
        int E;
        ArrayList<WDEdge>[] adj;

        public MyEWDGraph(int v,int e){
            V=v;
            E=e;
            adj=new ArrayList[v];
            for (int i=0;i<v;i++){
                adj[i]=new ArrayList<>(50);
            }
        }

        public void addEdge(WDEdge edge){
            adj[edge.from].add(edge);
        }

        public ArrayList<WDEdge> adj(int v){
            return adj[v];
        }
    }

    private static class WDEdge implements Comparable<WDEdge> {
        int from;
        int to;
        int weight;
        public WDEdge(int from,int to,int weight){
            this.from=from;
            this.to=to;
            this.weight=weight;
        }

        @Override
        public int compareTo(WDEdge o) {
            if (weight>o.weight)
                return 1;
            else if (weight<o.weight)
                return -1;
            else
                return 0;
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

    private static class DijkstraSP{
        public WDEdge[] edgeTo;
        public long[] disTo;
        public MyIndexPQ<Long> pq;
        int[] as;
        int[] bs;

        public DijkstraSP(MyEWDGraph g,int s,int[] as,int[] bs){
            edgeTo=new WDEdge[g.V];
            disTo=new long[g.V];
            this.as=as;
            this.bs=bs;
            pq=new MyIndexPQ<>(g.V);
            for (int i=0;i<g.V;i++){
                disTo[i]=Long.MAX_VALUE;
            }
            disTo[s]=0;
            pq.insert(s,0L);
            while(pq.size>0){
                relax(g,pq.pop());
            }
        }

        public void relax(MyEWDGraph g,int v){
            int w=0;
            long t1=0;
            for (WDEdge e:g.adj(v)){
                w=e.to;
                if (bs[w]==0){
                    continue;
                }
                else{
                    t1=disTo[v]+e.weight;
                    if (t1%(as[w]+bs[w])<as[w]){
                        t1=t1+(as[w]-t1%(as[w]+bs[w]));
                    }
                    if (t1<disTo[w]){
                        if (pq.contain(w)){
                            pq.change(w,t1);
                            disTo[w]=t1;
                            edgeTo[w]=e;
                        }
                        else{
                            pq.insert(w,t1);
                            disTo[w]=t1;
                            edgeTo[w]=e;
                        }
                    }
                }
            }
        }



    }

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int V=in.nextInt();
        int E=in.nextInt();
        MyEWDGraph g=new MyEWDGraph(V,E);
        for (int i=0;i<E;i++){
            g.addEdge(new WDEdge(in.nextInt()-1,in.nextInt()-1,in.nextInt()));
        }
        int[] as=new int[V];
        int[] bs=new int[V];
        for (int i=0;i<V;i++){
            as[i]=in.nextInt();
            bs[i]=in.nextInt();
        }
        DijkstraSP dijkstraSP=new DijkstraSP(g,0,as,bs);
        out.println(dijkstraSP.disTo[V-1]);
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
