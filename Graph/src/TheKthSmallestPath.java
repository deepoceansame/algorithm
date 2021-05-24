import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class TheKthSmallestPath {

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
        int toIndex;
        public WDEdge(int from,int to,int weight){
            this.from=from;
            this.to=to;
            this.weight=weight;
            toIndex=0;
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

    private static class Path implements Comparable<Path>{
        int from;
        int to;
        int cito;
        int toIndex;
        long weight;
        public Path(int f,int t,int ct,int ti,long w){
            from=f;
            to=t;
            cito=ct;
            toIndex=ti;
            weight=w;
        }

        @Override
        public int compareTo(Path o) {
            return (int) (weight-o.weight);
        }
    }

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        int zhixincishu=in.nextInt();
        int v=0;
        int e=0;
        int q=0;
        long[] answer=new long[50000];
        PriorityQueue<Path> pq=null;
        PrintWriter out=new PrintWriter(System.out);
        for (int i=0;i<zhixincishu;i++){
            v=in.nextInt();
            e=in.nextInt();
            q=in.nextInt();
            pq=new PriorityQueue<>(e);
            MyEWDGraph g=new MyEWDGraph(v,e);
            for (int j=0;j<e;j++){
                g.addEdge(new WDEdge(in.nextInt()-1,in.nextInt()-1,in.nextInt()));
            }
            for (int j=0;j<v;j++){
                g.adj(j).sort((WDEdge::compareTo));
                for (int k=0;k<g.adj(j).size();k++){
                    g.adj(j).get(k).toIndex=k;
                }
            }

            for (int j=0;j<v;j++){
                WDEdge edge=null;
                if (g.adj(j).size()>0)
                    edge=g.adj(j).get(0);
                if (edge!=null)
                pq.offer(new Path(edge.from,edge.to,edge.from,0,edge.weight));
            }

            int cnt=0;
            while(pq.size()>0 && cnt<50000){
                Path pa=pq.poll();
                WDEdge aedge=g.adj(pa.cito).get(pa.toIndex);
                answer[cnt]=pa.weight;
                cnt++;


                WDEdge newedge1=null;
                if (g.adj(pa.to).size()>0)
                newedge1=g.adj(pa.to).get(0);
                if (newedge1!=null){
                    pq.offer(new Path(pa.from,newedge1.to,pa.to,0,pa.weight+newedge1.weight));
                }


                WDEdge newedge2=null;
                if (g.adj(pa.cito).size()>pa.toIndex+1)
                newedge2=g.adj(pa.cito).get(pa.toIndex+1);
                if (newedge2!=null){
                    pq.offer(new Path(pa.from,newedge2.to, pa.cito, pa.toIndex+1, pa.weight-aedge.weight+newedge2.weight));
                }
            }
            for (int j=0;j<q;j++){
                out.println(answer[in.nextInt()-1]);
            }
        }

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
            arr=new Object[shuzhudaxiao];
            size=0;
        }
    }
}
