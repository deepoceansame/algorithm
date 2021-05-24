import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class NoDirectNoWeightGraph {


    private static class Graph{
        ArrayList<Integer>[] adj;
        int V;
        int E;
        public Graph(int V,int E){
            this.V=V;
            this.E=E;
            adj=new ArrayList[V];
        }

        public void addEdge(int a,int b){
            adj(a).add(b);
            adj(b).add(a);
        }

        public ArrayList<Integer> adj(int v){
            if (adj[v]==null){
                adj[v]=new ArrayList<>();
            }
            return adj[v];
        }
    }



    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int nodeNum=in.nextInt();
        long[] nodeWeight=new long[nodeNum];
        Graph g=new Graph(nodeNum,nodeNum-1);
        for (int i=0;i<nodeNum;i++){
            nodeWeight[i]=in.nextLong();
        }
        for (int i=0;i<nodeNum-1;i++){
            g.addEdge(in.nextInt()-1,in.nextInt()-1);
        }
        nodeWeightDfs nodeWeightDfs=new nodeWeightDfs(g,nodeWeight);
        BeautyNumOfSource beautyNumOfSource=new BeautyNumOfSource(g,nodeWeight);
        RootNum rootNum=new RootNum(g,nodeWeightDfs.treeWeight, beautyNumOfSource.beautyNum);
        long ans=0;
        for (long a:rootNum.eachRootAns){
            ans=Math.max(ans,a);
        }
        out.println(ans);
        out.close();
    }

    private static class RootNum{
        long[] nodeTreeWeight;
        Graph g;
        int[] isVisited;
        long[] eachRootAns;
        public RootNum(Graph g,long[] nodeTreeWeight,long sourceRoot){
            this.g=g;
            this.nodeTreeWeight=nodeTreeWeight;
            isVisited=new int[g.V];
            eachRootAns=new long[g.V];
            eachRootAns[0]=sourceRoot;
            getRootNum(0,sourceRoot);
        }

        public void getRootNum(int v,long vsVal){
            isVisited[v]=1;
            for (int a:g.adj(v)){
                if (isVisited[a]==0){
                    eachRootAns[a]=vsVal+nodeTreeWeight[0]-2*nodeTreeWeight[a];
                    getRootNum(a,eachRootAns[a]);
                }
            }
        }
    }
    private static class BeautyNumOfSource{
        long[] nodeWeight;
        int[] isVisited;
        Graph g;
        long beautyNum;
        public BeautyNumOfSource(Graph g,long[] nodeWeight){
            this.g=g;
            this.nodeWeight=nodeWeight;
            isVisited=new int[g.V];
            beautyNum=getBeautyNum(0,0);
        }

        public long getBeautyNum(int v,int len){
            long ans=nodeWeight[v]*len;
            isVisited[v]=1;
            for (int a:g.adj(v)) {
                if (isVisited[a] == 0) {
                    ans+=getBeautyNum(a,len+1);
                }
            }
            return ans;
        }
    }
    private static class nodeWeightDfs{
        Graph g;
        long[] nodeWeight;
        long[] treeWeight;
        int[] isVisited;
        public nodeWeightDfs(Graph g,long[] nodeWeight){
            this.g=g;
            this.nodeWeight=nodeWeight;
            isVisited=new int[g.V];
            treeWeight=new long[g.V];
            getWeight(0);
        }

        private long getWeight(int v){
            isVisited[v]=1;
            treeWeight[v]=nodeWeight[v];
            for (int a:g.adj(v)){
                if (isVisited[a]==0)
                    treeWeight[v]+=getWeight(a);
            }
            return treeWeight[v];
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



