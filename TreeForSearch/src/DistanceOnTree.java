import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DistanceOnTree {
    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int zhixincishu=in.nextInt();
        int citnum=0;
        int renshu=0;
        int a=0;
        int b=0;
        int hei=0;
        Nodee[] cits=null;
        Nodee aroot=null;
        for (int i=0;i<zhixincishu;i++){
            citnum=in.nextInt();
            renshu=in.nextInt();
            cits=new Nodee[citnum+1];
            for (int j=1;j<=citnum;j++){
                cits[j]=new Nodee(j);
            }

            for (int j=0;j<citnum-1;j++){
                a=in.nextInt();
                b=in.nextInt();
                cits[a].nodees.add(cits[b]);
                cits[b].nodees.add(cits[a]);
            }

            for (int j=0;j<renshu;j++){
                hei=in.nextInt();
                cits[hei].haspeople=true;
                if (j==0){
                    aroot=cits[hei];
                }
            }
            Nodee k=zuiyuandian(aroot,aroot,0,0).node;
            for (int j=1;j<=citnum;j++) {
                cits[j].isvis=false;
            }
            int answer=zuiyuandian(k,k,0,0).dis;
            if (answer%2==0)
                out.println(answer/2);
            else
                out.println(answer/2+1);
        }
        out.close();
    }

    public static comb zuiyuandian(Nodee now,Nodee nma,int ima,int dis){
        now.isvis=true;
        if (now.haspeople){
            if (dis>ima){
                nma=now;
                ima=dis;
            }
        }

        boolean isleaf=true;
        for (Nodee a: now.nodees){
            isleaf=a.isvis;
            if (!isleaf)
                break;
        }
        if (isleaf){
            return new comb(nma,ima);
        }

        else{
           for (Nodee a:now.nodees){
              if (!a.isvis){
                comb tt=zuiyuandian(a,nma,ima,dis+1);
                if (tt.dis>ima){
                    ima=tt.dis;
                    nma=tt.node;
                }
              }
           }

           return new comb(nma,ima);
        }
    }

    private static class Nodee{
        public int num;
        public boolean haspeople;
        public boolean isvis;
        public ArrayList<Nodee> nodees;
        public Nodee(int a){
            num=a;
            isvis=false;
            haspeople=false;
            nodees=new ArrayList<>(10);
        }
    }

    private static class comb{
        Nodee node;
        int dis;
        public comb(Nodee a,int b){
            node=a;
            dis=b;
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
