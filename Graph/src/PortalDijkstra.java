import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PortalDijkstra {
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
        boolean isportal;
        public WDEdge(int from,int to,int weight,boolean isportal){
            this.from=from;
            this.to=to;
            this.weight=weight;
            this.isportal=isportal;
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

    private static class MyDoubleIndexPQ<T extends Comparable<? super T>>{
        public Object[][] items;
        public int[][] pos;
        public Ind[] arr;
        public int size;

        public MyDoubleIndexPQ(int rcap,int ccap){
            items=new Object[rcap][ccap];
            pos=new int[rcap][ccap];
            arr=new Ind[rcap*ccap+1];
            size=0;
        }

        public boolean contain(int row,int col){
            return items[row][col]!=null;
        }

        public void sink(int k){
            T zuo=null;
            T now=null;
            while(2*k<=size){
                now=((T)items[arr[k].r][arr[k].c]);
                zuo=(T)items[arr[2*k].r][arr[2*k].c];
                int j=2*k;
                if (j<size && ((T)items[arr[j+1].r][arr[j+1].c]).compareTo(zuo)<0) j++;
                if (now.compareTo((T)items[arr[j].r][arr[j].c])>0)
                    exch(k,j);
                k=j;
            }
        }

        public void goup(int k){
            while (k>1 && ((T)items[arr[k].r][arr[k].c]).compareTo((T)items[arr[k/2].r][arr[k/2].c])<0){
                exch(k,k/2);
                k=k/2;
            }
        }

        public void exch(int a,int b){
            pos[arr[a].r][arr[a].c]=b;
            pos[arr[b].r][arr[b].c]=a;
            Ind temp=arr[a];
            arr[a]=arr[b];
            arr[b]=temp;
        }

        public void insert(int r,int c,T t){
            size++;
            pos[r][c]=size;
            arr[size]=new Ind(r,c);
            items[r][c]=t;
            goup(size);
        }

        public void change(int r,int c,T t){
            items[r][c]=t;
            sink(pos[r][c]);
            goup(pos[r][c]);
        }

        public Ind pop(){
            Ind temp=arr[1];
            exch(1,size);
            arr[size]=null;
            size--;
            sink(1);
            pos[temp.r][temp.c]=0;
            items[temp.r][temp.c]=null;
            return temp;
        }

        public void delete(int r,int c){
            int a=pos[r][c];
            exch(a,size);
            arr[size]=null;
            size--;
            if (a<=size){
                goup(a);
                sink(a);
            }
            items[r][c]=null;
            pos[r][c]=0;
        }
    }

    private static class Ind{
        public int r;
        public int c;
        public Ind(int a,int b){
            r=a;
            c=b;
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

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int v=in.nextInt();
        int e=in.nextInt();
        MyEWDGraph g=new MyEWDGraph(v,e);
        int portalNum=in.nextInt();
        int maxp=in.nextInt();
        for (int i=0;i<e;i++){
            g.addEdge(new WDEdge(in.nextInt()-1,in.nextInt()-1,in.nextInt(),false));
        }
        for (int i=0;i<portalNum;i++){
            g.addEdge(new WDEdge(in.nextInt()-1,in.nextInt()-1,0,true));
        }
        int s=in.nextInt()-1;
        int des=in.nextInt()-1;
        DoubleDijkstra ddij=new DoubleDijkstra(g,s,maxp);
        Long min=Long.MAX_VALUE;
        for (int i=0;i<=maxp;i++){
            if (min>ddij.disTo[des][i]){
                min=ddij.disTo[des][i];
            }
        }
        out.println(min);
        out.close();
    }

    private static class DoubleDijkstra{
        long[][] disTo;
        WDEdge[][] edgeTo;
        MyDoubleIndexPQ<Long> pq;
        public DoubleDijkstra(MyEWDGraph g,int s,int maxp){
            disTo=new long[g.V][maxp+1];
            edgeTo=new WDEdge[g.V][maxp+1];
            pq=new MyDoubleIndexPQ<>(g.V,maxp+1);

            for (int i=0;i<g.V;i++){
                for (int j=0;j<=maxp;j++){
                    disTo[i][j]=Long.MAX_VALUE;
                }
            }
            for (int j=0;j<=maxp;j++){
                disTo[s][j]=0;
            }

            pq.insert(s,0, 0L);
            Ind ind=null;
            while(pq.size>0){
                ind=pq.pop();
                    relax(g,ind,maxp);
            }
        }

        public void relax(MyEWDGraph g,Ind ind,int maxq){
            int v=ind.r;
            int k= ind.c;
            for (WDEdge e:g.adj(v)){
                if (!e.isportal){
                    if (disTo[e.to][k]>disTo[v][k]+e.weight){
                        disTo[e.to][k]=disTo[v][k]+e.weight;
                        if (pq.contain(e.to,k))
                            pq.change(e.to,k,disTo[e.to][k]);
                        else
                            pq.insert(e.to,k,disTo[e.to][k]);
                    }
                }

                else{
                    if (k+1<=maxq){
                        if (disTo[e.to][k+1]>disTo[v][k]){
                            disTo[e.to][k+1]=disTo[v][k];
                            if (pq.contain(e.to,k+1)){
                                pq.change(e.to,k+1,disTo[e.to][k+1]);
                            }
                            else{
                                pq.insert(e.to,k+1,disTo[e.to][k+1]);
                            }
                        }
                    }
                }
            }
        }


    }
}
