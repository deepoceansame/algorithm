import java.io.*;
import java.util.ArrayList;

public class LargestInPath {
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

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int vernum=in.nextInt();
        MyEWDGraph g=new MyEWDGraph(vernum,vernum-1);
        int querynum=in.nextInt();
        int[] query=new int[querynum];
        int va=0;
        int vb=0;
        int we=0;
        int maxwe=0;
        for (int i=0;i<vernum-1;i++){
            va=in.nextInt()-1;
            vb=in.nextInt()-1;
            we=in.nextInt();
            maxwe=Math.max(maxwe,we);
            g.addEdge(new WDEdge(va,vb,we));
            g.addEdge(new WDEdge(vb,va,we));
        }
        for (int i=0;i<querynum;i++){
            query[i]=in.nextInt();
        }
        SolDFS dfs=new SolDFS(g);
        int[][] matri= dfs.matri;
        int[] count=new int[maxwe+1];
        for (int i=0;i<g.V;i++){
            for (int j=i+1;j<g.V;j++){
                count[matri[i][j]]++;
            }
        }
        int que=0;
        int maxans=0;
        for (int i=1;i<=maxwe;i++){
            maxans+=count[i];
        }
        int ans=0;
        for (int i=0;i<querynum;i++){
            que=query[i];
            if (que<=0){
                out.print(0+" ");
            }
            else if (que>maxwe){
                out.print(maxans+" ");
            }
            else{
                for (int j=1;j<=que;j++){
                    ans+=count[j];
                }
                out.print(ans+" ");
                ans=0;
            }
        }

        out.close();
    }

    public static class SolDFS{
        MyEWDGraph g;
        int[][] matri;
        int[] isVisited;
        public SolDFS(MyEWDGraph g){
            this.g=g;
            matri=new int[g.V][g.V];
            isVisited=new int[g.V];
            for (int i=0;i<g.V;i++){
                for (int j=0;j<g.V;j++){
                    isVisited[j]=0;
                }
                dfs(i,i,0);
            }
        }

        private void dfs(int v,int s,int be){
            isVisited[v]=1;
            int to=0;
            for (WDEdge e:g.adj(v)){
                to=e.to;
                if (isVisited[to]==0){
                    if(be>e.weight){
                        matri[s][to]=be;
                        dfs(to,s,be);
                    }
                    else{
                        matri[s][to]=e.weight;
                        dfs(to,s,e.weight);
                    }
                }
            }
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
