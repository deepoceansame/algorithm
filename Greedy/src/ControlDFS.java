import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

public class ControlDFS {
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

        public void sortAdj(){
            for (ArrayList<WDEdge> list:adj){
                if (list.size()==3){
                    int a=0;
                }
                list.sort(WDEdge::compareTo);
            }
        }
    }



    private static class WDEdge implements Comparable<WDEdge> {
        int from;
        int to;
        int hurdle;
        public static long[] investTotal;
        public static long[] profitTotal;
        public static long[] incomeTotal;
        public WDEdge(int from,int to,int hurdle){
            this.from=from;
            this.to=to;
            this.hurdle=hurdle;
        }

        @Override
        public int compareTo(WDEdge o1) {
            if (profitTotal[to]>0&&profitTotal[o1.to]<0){
                return -1;
            }
            else if (profitTotal[o1.to]>0&&profitTotal[to]<0){
                return 1;
            }
            else if (profitTotal[to]>0&&profitTotal[o1.to]>0){
                if (investTotal[to]<investTotal[o1.to]){
                    return -1;
                }
                else return 1;
            }
            else{
                if (incomeTotal[to]>incomeTotal[o1.to]){
                    return -1;
                }
                else return 1;
            }
        }


    }

    private static class DfsTotal{
        boolean[] isVisited;
        boolean[] isVisited2;
        MyEWDGraph g;
        long[] investTotal;
        long[] incomeTotal;
        long[] profitTotal;
        long[] resourceTotal;
        long[] resource;
        public DfsTotal(MyEWDGraph g,long[] resource){
            this.g=g;
            this.resource=resource;
            isVisited=new boolean[g.V];
            isVisited2=new boolean[g.V];
            investTotal=new long[g.V];
            incomeTotal=new long[g.V];
            profitTotal=new long[g.V];
            resourceTotal=new long[g.V];
            investDFS(0);
            resourceDFS(0);
            for (int i=0;i<g.V;i++){
                incomeTotal[i]=resourceTotal[i]-investTotal[i];
                profitTotal[i]=incomeTotal[i]-investTotal[i];
            }
            WDEdge.profitTotal=profitTotal;
            WDEdge.incomeTotal=incomeTotal;
            WDEdge.investTotal=investTotal;
        }

        public long investDFS(int v){
            isVisited[v]=true;
            for (WDEdge e:g.adj(v)){
                if (!isVisited[e.to]){
                    investTotal[e.to]=e.hurdle;
                    investTotal[v]+=investDFS(e.to);
                }
            }
            return investTotal[v];
        }

        public long resourceDFS(int v){
            isVisited2[v]=true;
            resourceTotal[v]=resource[v];
            for (WDEdge e:g.adj(v)){
                if (!isVisited2[e.to]){
                    resourceTotal[v]+=resourceDFS(e.to);
                }
            }
            return resourceTotal[v];
        }
    }

    private static class Walk{
        MyEWDGraph g;
        long ans=0;
        long hp=0;
        long[] resource;
        boolean[] isvisited;
        public Walk(MyEWDGraph g,long[] resource){
            this.g=g;
            isvisited=new boolean[g.V];
            this.resource=resource;
            iniAns(0);
        }

        public void iniAns(int v){
            isvisited[v]=true;
            hp+=resource[v];
            for (WDEdge e:g.adj(v)){
                if (!isvisited[e.to]){
                    if (hp>=e.hurdle){
                        hp-=e.hurdle;
                    }
                    else{
                        ans+=e.hurdle-hp;
                        hp=0;
                    }
                    iniAns(e.to);
                    if (hp>=e.hurdle){
                        hp-=e.hurdle;
                    }
                    else{
                        ans+=e.hurdle-hp;
                        hp=0;
                    }
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int vnum=in.nextInt();
        long[] resource=new long[vnum];
        for (int i=0;i<vnum;i++){
            resource[i]=in.nextInt();
        }
        MyEWDGraph g=new MyEWDGraph(vnum,vnum-1);
        int from=0;
        int to=0;
        int weight=0;
        for (int i=0;i<vnum-1;i++){
            from=in.nextInt();
            to=in.nextInt();
            weight=in.nextInt();
            g.addEdge(new WDEdge(from-1,to-1,weight));
            g.addEdge(new WDEdge(to-1,from-1,weight));
        }
        DfsTotal dfsTotal=new DfsTotal(g,resource);
        g.sortAdj();
        Walk walk=new Walk(g,resource);
        out.print(walk.ans);
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
