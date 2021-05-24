import org.junit.Test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class testRBTree {
    private static final boolean RED =true;
    private static final boolean BLACK =false;

    private static class MyRBTree<V extends Comparable<V>>{
        RBNode<V> root;

        public void insert(V v){
            root=insert(root,v);
            root.col=BLACK;
        }

        public RBNode<V> insert(RBNode<V> h,V v){
            if (h==null)
                return new RBNode<>(v,1,RED);

            int cmp=v.compareTo(h.val);
            if (cmp<0) h.left=insert(h.left,v);
            else if(cmp>0) h.right=insert(h.right,v);
            else h.val=v;

            if (isRed(h.right) && !isRed(h.left)) h=rotateLeft(h);
            if (isRed(h.left) && isRed(h.left.left)) h=rotateRight(h);
            if (isRed(h.left) && isRed(h.right)) flipColors(h);

            h.N=size(h.left)+size(h.right)+1;

            return h;
        }

        public RBNode<V> insertd(RBNode<V> h,V v){
            if (h==null)
                return new RBNode<>(v,1,RED);

            int cmp=v.compareTo(h.val);
            if (cmp<0) h.left=insertd(h.left,v);
            else h.right=insertd(h.right,v);

            if (isRed(h.right) && !isRed(h.left)) h=rotateLeft(h);
            if (isRed(h.left) && isRed(h.left.left)) h=rotateRight(h);
            if (isRed(h.left) && isRed(h.right)) flipColors(h);

            h.N=size(h.left)+size(h.right)+1;

            return h;
        }

        public void insertd(V v){
            root=insertd(root,v);
            root.col=BLACK;
        }

        public void delete(V v){
            V vtodele=findRightMin(v);
            if (vtodele==null)
                return;
            else{
                deletexia(vtodele);
                RBNode<V> node=root;
                int cmp=0;
                if (vtodele.compareTo(v)!=0){
                    cmp=v.compareTo(node.val);
                    while(cmp!=0){
                        cmp=v.compareTo(node.val);
                        if (cmp>0){
                            node=node.right;
                        }
                        else if (cmp<0){
                            node=node.left;
                        }
                        else{
                            break;
                        }
                    }
                    node.val=vtodele;
                }
            }
        }

        public V findRightMin(V v){
            RBNode<V> node=root;
            int cmp=0;
            while(node!=null&&node.val!=v){
                cmp=v.compareTo(node.val);
                if (cmp>0){
                    node=node.right;
                }
                else if (cmp<0){
                    node=node.left;
                }
                else{
                    break;
                }
            }
            if (node==null){
                return null;
            }
            else{
                if (node.right==null){
                    return node.val;
                }

                else{
                    node=node.right;
                    while(node.left!=null)
                        node=node.left;
                    return node.val;
                }
            }

        }

        public void deletexia(V v){
            root=deletexia(root,v);
            if (root==null){
                int a=0;
            }
            if (root!=null)
            root.col=BLACK;
        }

        public RBNode<V> deletexia(RBNode<V> node,V v){
            int cmp=v.compareTo(node.val);
            if (cmp>0){
                if (isRed(node.right)){
                    node.right=deletexia(node.right,v);
                }

                else if (isRed(node.right.left)){
                    node.right=deletexia(node.right,v);
                }

                else{
                    if (isRed(node.left)){
                        if (!isRed(node.left.right.left)){
                            RBNode<V> a=node;
                            node=node.left;
                            node.col=BLACK;
                            node.right.col=RED;
                            a.left=node.right;
                            node.right=a;
                            a.right.col=RED;
                            a.col=BLACK;//
                            a.N=size(a.right)+size(a.left)+1;
                            node.N=size(node.left)+size(node.right)+1;
                            node.right=deletexia(node.right,v); //1
                        }
                        else{
                            RBNode<V> a=node;
                            node=node.left.right;
                            RBNode<V> I =node.right;
                            RBNode<V> J=a.right.left;
                            a.left.right=node.left;
                            node.left.col=BLACK;
                            node.left=a.left;
                            node.right=a.right;
                            node.col=a.col;
                            a.right.left=a;
                            a.col=RED;
                            a.left=I;
                            a.right=J;
                            node.left.N=size(node.left.left)+size(node.left.right)+1;
                            a.N=size(I)+size(J)+1;
                            node.right.N=size(a)+size(node.right.right)+1;
                            node.N=size(node.left)+size(node.right)+1;
                            node.right=deletexia(node.right,v); //2
                        }
                    }

                    else{
                        if (isRed(node.left.left)){
                            RBNode<V> F=node.left.right;
                            RBNode<V> G=node.right.left;
                            RBNode<V> a=node;
                            node=node.left;
                            node.left.col=BLACK;
                            node.right=a.right;
                            node.col=a.col;
                            a.right.left=a;
                            a.col=RED;
                            a.left=F;
                            a.right=G;
                            a.N=size(F)+size(G)+1;
                            node.right.N=size(a)+size(node.right.right)+1;
                            node.N=size(node.right)+size(node.left)+1;
                            node.right=deletexia(node.right,v); //3
                        }

                        else{
                            flipColors(node);
                            node.right=deletexia(node.right,v); //4
                        }
                    }
                }
            }
            else if (cmp<0){
                if (isRed(node.left.left)){
                    node.left=deletexia(node.left,v);
                }
                else{
                    if (isRed(node.left)){
                        node.left=deletexia(node.left,v);//5
                    }
                    else if (!isRed(node.right.left)){
                        flipColors(node);
                        node.left=deletexia(node.left,v);//6
                    }
                    else {
                        RBNode<V> a=node;
                        node=node.right.left;
                        RBNode<V> H=node.right;
                        RBNode<V> G=node.left;
                        node.col=a.col;
                        node.right=a.right;
                        node.left=a;
                        a.col=BLACK;
                        a.left.col=RED;
                        a.right=G;
                        node.right.left=H;
                        node.right.N=size(node.right.left)+size(node.right.right)+1;
                        a.N=size(a.left)+size(a.right)+1;
                        node.N=size(node.left)+size(node.right)+1;
                        node.left=deletexia(node.left,v);//7
                    }
                }
            }
            else{
                if (node.left==null && node.right==null){
                    return null;
                }
                else{
                    node.left.col=node.col;
                    node=node.left;
                }
            }

            if (isRed(node.right) && !isRed(node.left)) node=rotateLeft(node);
            if (isRed(node.left) && isRed(node.left.left)) node=rotateRight(node);
            if (isRed(node.left) && isRed(node.right)) flipColors(node);
            node.N=size(node.left)+size(node.right)+1;
            return node;
        }



        public void deleted(V v){
            V vtodele=findRightMin(v);
            if ((Integer)vtodele==9616){
                int a=0;
            }
            if (vtodele==null)
                return;
            else{
                deletexiad(vtodele);
                RBNode<V> node=root;
                int cmp=0;
                if (vtodele.compareTo(v)!=0){
                    cmp=v.compareTo(node.val);
                    while(cmp!=0){
                        cmp=v.compareTo(node.val);
                        if (cmp>0){
                            node=node.right;
                        }
                        else if (cmp<0){
                            node=node.left;
                        }
                        else{
                            break;
                        }
                    }
                    node.val=vtodele;
                }
            }
        }

        public void deletexiad(V v){
            root=deletexiad(root,v);
            root.col=BLACK;
        }

        public RBNode<V> deletexiad(RBNode<V> node,V v){
            int cmp=v.compareTo(node.val);
            if (cmp==0 && node.right!=null&&node.left!=null)
                cmp=1;
            if (cmp>0){
                if (isRed(node.right)){
                    node.right=deletexiad(node.right,v);
                }

                else if (isRed(node.right.left)){
                    node.right=deletexiad(node.right,v);
                }

                else{
                    if (isRed(node.left)){
                        if (!isRed(node.left.right.left)){
                            RBNode<V> a=node;
                            node=node.left;
                            node.col=BLACK;
                            node.right.col=RED;
                            a.left=node.right;
                            node.right=a;
                            a.right.col=RED;
                            a.col=BLACK;//
                            a.N=size(a.right)+size(a.left)+1;
                            node.N=size(node.left)+size(node.right)+1;
                            node.right=deletexiad(node.right,v); //1
                        }
                        else{
                            RBNode<V> a=node;
                            node=node.left.right;
                            RBNode<V> I =node.right;
                            RBNode<V> J=a.right.left;
                            a.left.right=node.left;
                            node.left.col=BLACK;
                            node.left=a.left;
                            node.right=a.right;
                            node.col=a.col;
                            a.right.left=a;
                            a.col=RED;
                            a.left=I;
                            a.right=J;
                            node.left.N=size(node.left.left)+size(node.left.right)+1;
                            a.N=size(I)+size(J)+1;
                            node.right.N=size(a)+size(node.right.right)+1;
                            node.N=size(node.left)+size(node.right)+1;
                            node.right=deletexiad(node.right,v); //2
                        }
                    }

                    else{
                        if (isRed(node.left.left)){
                            RBNode<V> F=node.left.right;
                            RBNode<V> G=node.right.left;
                            RBNode<V> a=node;
                            node=node.left;
                            node.left.col=BLACK;
                            node.right=a.right;
                            node.col=a.col;
                            a.right.left=a;
                            a.col=RED;
                            a.left=F;
                            a.right=G;
                            a.N=size(F)+size(G)+1;
                            node.right.N=size(a)+size(node.right.right)+1;
                            node.N=size(node.right)+size(node.left)+1;
                            node.right=deletexiad(node.right,v); //3
                        }

                        else{
                            flipColors(node);
                            node.right=deletexiad(node.right,v); //4
                        }
                    }
                }
            }
            else if (cmp<0){
                if (node.left==null){
                    int a=0;
                }///
                if (isRed(node.left.left)){
                    node.left=deletexiad(node.left,v);
                }
                else{
                    if (isRed(node.left)){
                        node.left=deletexiad(node.left,v);//5
                    }
                    else if (!isRed(node.right.left)){
                        flipColors(node);
                        node.left=deletexiad(node.left,v);//6
                    }
                    else {
                        RBNode<V> a=node;
                        node=node.right.left;
                        RBNode<V> H=node.right;
                        RBNode<V> G=node.left;
                        node.col=a.col;
                        node.right=a.right;
                        node.left=a;
                        a.col=BLACK;
                        a.left.col=RED;
                        a.right=G;
                        node.right.left=H;
                        node.right.N=size(node.right.left)+size(node.right.right)+1;
                        a.N=size(a.left)+size(a.right)+1;
                        node.N=size(node.left)+size(node.right)+1;
                        node.left=deletexiad(node.left,v);//7
                    }
                }
            }
            else{
                if (node.left==null && node.right==null){
                    return null;
                }
                else{
                    node.left.col=node.col;
                    node=node.left;
                }
            }

            if (isRed(node.right) && !isRed(node.left)) node=rotateLeft(node);
            if (isRed(node.left) && isRed(node.left.left)) node=rotateRight(node);
            if (isRed(node.left) && isRed(node.right)) flipColors(node);
            node.N=size(node.left)+size(node.right)+1;
            return node;
        }



        private boolean isRed(RBNode<V> node){
            if (node==null)
                return false;
            else
                return node.col==RED;
        }

        private RBNode<V> rotateLeft(RBNode<V> h){
            RBNode<V> x=h.right;
            h.right=x.left;
            x.left=h;
            x.col=h.col;
            h.col=RED;
            x.N=h.N;
            h.N=1+size(h.left)+size(h.right);
            return x;
        }

        private RBNode<V> rotateRight(RBNode<V> h){
            RBNode<V> x=h.left;
            h.left=x.right;
            x.right=h;
            x.col=h.col;
            h.col=RED;
            x.N=h.N;
            h.N=1+size(h.left)+size(h.right);
            return x;
        }

        private void flipColors(RBNode<V> h){
            h.col=!h.col;
            h.left.col=!h.left.col;
            h.right.col=!h.right.col;
        }

        private int size(RBNode<V> h){
            if (h==null)
                return 0;
            else
                return h.N;
        }

        public void print(RBNode<V> node){
            if (node==null)
                return;
            else{
                print(node.left);
                System.out.print(node.val+" ");
                print(node.right);
            }
        }

        public V findKth(int k){
            RBNode<V> node=root;
            int now=size(node.left)+1;
            while (node!=null&&now!=k) {
                if (now > k) {
                    node = node.left;
                    if (node!=null){
                        now=now-size(node.right)-1;
                    }
                }
                else if (now<k){
                    node=node.right;
                    if (node!=null){
                        now=now+size(node.left)+1;
                    }
                }
                else
                    break;
            }
            if (node==null)
                return null;
            else
                return node.val;
        }

        public int kthOf(V v){
            RBNode<V> node=root;
            int nowind=size(node.left)+1;
            int cmp=v.compareTo(node.val);
            while(cmp!=0&&node!=null){
                if (cmp>0){
                    node=node.right;
                    if (node!=null)
                    nowind=nowind+size(node.left)+1;
                }
                else{
                    node=node.left;
                    if (node!=null)
                    nowind=nowind-size(node.right)-1;
                }
                if (node!=null)
                    cmp=v.compareTo(node.val);
            }
            if (node==null)
                return -1;
            else
                return nowind;
        }

        public V qianqv(V v){
            RBNode<V> anode=null;
            RBNode<V> node=root;
            int cmp=0;
            while(node!=null){
                cmp=v.compareTo(node.val);
                if (cmp<=0){
                    node=node.left;
                }
                else{
                    if (anode==null){
                        anode=node;
                    }
                    else if (node.val.compareTo(anode.val)>0){
                        anode=node;
                    }
                    node=node.right;
                }
            }
            if (anode==null)
                return null;
            else
                return anode.val;
        }

        public V houji(V v){
            RBNode<V> anode=null;
            RBNode<V> node=root;
            int cmp=0;
            while(node!=null){
                cmp=v.compareTo(node.val);
                if (cmp>=0){
                    node=node.right;
                }
                else{
                    if (anode==null){
                        anode=node;
                    }
                    else if (node.val.compareTo(anode.val)<0){
                        anode=node;
                    }
                    node=node.left;
                }
            }
            if (anode==null)
                return null;
            else
                return anode.val;
        }
    }

    private static class RBNode<V extends Comparable<V>>{
        V val;
        boolean col;
        RBNode<V> left;
        RBNode<V> right;
        int N;

        public RBNode(V v,int N,boolean col){
            val=v;
            this.N=N;
            this.col=col;
        }
    }

    public static void main(String[] args) throws IOException {
        MyRBTree<Integer> at=new MyRBTree<>();
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int caozuo=in.nextInt();
        int op=0;
        int zhi=0;
        for (int i=0;i<caozuo;i++){
            op=in.nextInt();
            if (i==65565){
                int a=0;
            }
            if (op==1){
                zhi=in.nextInt();
                at.insert(zhi);
            }
            else if (op==2){
                zhi=in.nextInt();
                if (zhi==9616){
                    int a=0;
                }
                at.delete(zhi);
            }
            else if (op==3){
                zhi=in.nextInt();
                out.println(at.kthOf(zhi));
            }
            else if (op==4){
                zhi=in.nextInt();
                if (zhi==4888){
                    int a=0;
                }
                at.insert(zhi);
                out.println(at.findKth(zhi));
            }
            else if (op==5){
                zhi=in.nextInt();
                if (zhi==3766){
                    int a=0;
                }
                out.println(at.qianqv(zhi));
            }
            else{
                zhi=in.nextInt();
                out.println(at.houji(zhi));
            }
        }
        out.close();
        /*MyRBTree<Integer> bt=new MyRBTree<>();
        for (int i=1;i<=10;i++){
            bt.insertd(i);
        }
        bt.insertd(3);
        bt.insertd(3);
        bt.insertd(3);
        bt.insertd(9);
        bt.insertd(9);
        bt.insertd(10);
        bt.print(bt.root);
        System.out.println();
        System.out.println(bt.qianqiu(3));
        System.out.println(bt.houji(3));*/
    }

    @Test
    public void test (){
        MyRBTree<Integer> bt=new MyRBTree<>();
        bt.insertd(9609);
        bt.insertd(9300);
        bt.insertd(9909);
        bt.insertd(9616);
        bt.insertd(9616);
        bt.deleted(9609);
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
