import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class isHeap {

    public static void main(String[] args) throws IOException {
       Reader in=new Reader();
       PrintWriter out=new PrintWriter(System.out);
       int zhixincishu=in.nextInt();
       int nnum=0;
       int fa,ch=0;
       Nodee[] nodes=null;
       boolean answer=true;
       Nodee root=null;
       for (int i=0;i<zhixincishu;i++){
           answer=true;
           nnum=in.nextInt();
           nodes=new Nodee[nnum];
           for (int j=0;j<nnum;j++){
               nodes[j]=new Nodee(in.nextInt());
           }

           for (int j=0;j<nnum-1;j++){
                fa=in.nextInt()-1;
                ch=in.nextInt()-1;
                if (nodes[fa].left==null){
                    nodes[fa].left=nodes[ch];
                    nodes[ch].isSon=true;
                }
                else if (nodes[fa].right==null){
                    nodes[fa].right=nodes[ch];
                    nodes[ch].isSon=true;
                    if (nodes[fa].left==nodes[ch])
                        answer=false;
                }
                else
                    answer=false;
           }

           if (!answer){
               out.println("Case #"+(i+1)+": NO");
               continue;
           }

           for (int j=0;j<nnum;j++){
               if (!nodes[j].isSon){
                   root=nodes[j];
                   break;
               }
           }

           answer=(isDading(root,nnum)||isXiaoding(root,nnum));
           if (!answer){
               out.println("Case #"+(i+1)+": NO");
           }
           else{
               out.println("Case #"+(i+1)+": YES");
           }
       }

       out.close();
    }

    public static boolean isDading(Nodee root,int n){
        boolean answer=true;
        Nodee[] nodes=new Nodee[n+1];
        nodes[1]=root;
        int i=1;
        while(i<=n){
            if (nodes[i]==null){
                answer=false;
                break;
            }
            if (nodes[i].left!=null){
                if (2*i>n){
                    answer=false;
                    break;
                }
                if (nodes[i].val<nodes[i].left.val){
                    answer=false;
                    break;
                }
                nodes[2*i]=nodes[i].left;
            }
            if (nodes[i].right!=null){
                if (2*i+1>n){
                    answer=false;
                    break;
                }
                if (nodes[i].val<nodes[i].right.val){
                    answer=false;
                    break;
                }
                nodes[2*i+1]=nodes[i].right;
            }
            i++;
        }
        return answer;
    }

    public static boolean isXiaoding(Nodee root,int n){
        boolean answer=true;
        Nodee[] nodes=new Nodee[n+1];
        nodes[1]=root;
        int i=1;
        while(i<=n){
            if (nodes[i]==null){
                answer=false;
                break;
            }
            if (nodes[i].left!=null){
                if (2*i>n){
                    answer=false;
                    break;
                }
                if (nodes[i].val>nodes[i].left.val){
                    answer=false;
                    break;
                }
                nodes[2*i]=nodes[i].left;
            }
            if (nodes[i].right!=null){
                if (2*i+1>n){
                    answer=false;
                    break;
                }
                if (nodes[i].val>nodes[i].right.val){
                    answer=false;
                    break;
                }
                nodes[2*i+1]=nodes[i].right;
            }
            i++;
        }
        return answer;
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

    private static class Nodee{
        public boolean isSon;
        public int val;
        public Nodee right;
        public Nodee left;
        public Nodee(int a){
            val=a;
            isSon=false;
        }
    }
}
