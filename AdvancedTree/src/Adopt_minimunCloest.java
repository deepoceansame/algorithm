import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Adopt_minimunCloest {

    public static void main(String[] args) throws IOException {
        MyAVL<Integer> pet=new MyAVL<>(null);
        MyAVL<Integer> man=new MyAVL<>(null);
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int zong=in.nextInt();
        int fla=0;
        int val=0;
        int zhi=0;
        long answer=0;
        for (int i=0;i<zong;i++){
            fla=in.nextInt();
            val=in.nextInt();
            if (fla==0){
                if (man.root==null)
                    pet.insert(val);
                else{
                    zhi=findMinClose(man,val);
                    answer+=Math.abs(val-zhi);
                    man.remove(zhi);
                }
            }
            else{
                if (pet.root==null)
                    man.insert(val);
                else{
                    zhi=findMinClose(pet,val);
                    answer+=Math.abs(val-zhi);
                    pet.remove(zhi);
                }
            }
        }
        out.println(answer);
        out.close();
    }

    public static int findMinClose(MyAVL<Integer> avl,int val){
        AVLNode<Integer> a=avl.root;
        boolean findBig=false;
        boolean findSmall=false;
        int big=0;
        int small=0;
        int answer=0;
        while(a!=null){
            if (a.val==val)
                return a.val;
            else if (a.val>val){
                findBig=true;
                if (big==0)
                    big=a.val;
                else
                    big=Math.min(a.val,big);
                a=a.left;
            }
            else{
                findSmall=true;
                if (small==0)
                    small=a.val;
                else
                    small=Math.max(small,a.val);
                a=a.right;
            }
        }
        if (!findBig)
            return small;
        if (!findSmall)
            return big;
        int bigcha=big-val;
        int smallcha=val-small;
        if (bigcha>smallcha)
            return small;
        if (smallcha>bigcha)
            return big;
        else
            return small;
    }

    private static class AVLNode<T extends Comparable<? super T>>{
        int height;
        int size;
        T val;
        AVLNode<T> right;
        AVLNode<T> left;
        AVLNode<T> pare;
        public AVLNode(T t){
            val=t;
            height=0;
            pare=null;
        }

    }

    private static class MyAVL<T extends Comparable<? super T>>{
        public AVLNode<T> root;
        public MyAVL(T t){
            if (t==null){

            }
            else{
                root=new AVLNode<>(t);
            }
        }

        public int height(AVLNode<T> node){
            if (node==null)
                return -1;
            else
                return node.height;
        }

        public int size(AVLNode<T> node){
            if (node==null)
                return 0;
            else
                return node.size;
        }
        public void insert(T t){
            root=insert(t,root);
        }
        public AVLNode<T> insert(T t,AVLNode<T> node){
            if (node==null){
                return balance(new AVLNode<>(t));
            }
            else{
                int cmp=t.compareTo(node.val);
                if (cmp<0){
                    node.left=insert(t,node.left);
                    node.left.pare=node;
                }
                else if (cmp>0){
                    node.right=insert(t,node.right);
                    node.right.pare=node;
                }
                else{
                }
            }
            return balance(node);
        }

        public AVLNode<T> balance(AVLNode<T> node){
            if (node==null){
                return node;
            }
            else{
                if (height(node.left)-height(node.right)>1) {
                    if (height(node.left.left) >= height(node.left.right)) {
                        node=rotateWithLeft(node);
                    }
                    else{
                        node=doubleWithLeft(node);
                    }
                }
                else if (height(node.right)-height(node.left)>1){
                    if (height(node.right.right) >= height(node.right.left)){
                        node=rotateWithRight(node);
                    }
                    else{
                        node=doubleWithRight(node);
                    }
                }
                node.height=Math.max(height(node.right),height(node.left))+1;
                node.size=size(node.left)+size(node.right)+1;
                return node;
            }
        }

        public AVLNode<T> rotateWithLeft(AVLNode<T> n2){
            AVLNode<T> n1=n2.left;
            n2.left=n1.right;
            n1.right=n2;
            n1.pare=n2.pare;
            if (n2.left!=null)
                n2.left.pare=n2;
            n2.pare=n1;
            n2.size=1+size(n2.right)+size(n2.left);
            n1.size=1+size(n2)+size(n1.left);
            n2.height=Math.max(height(n2.left),height(n2.right))+1;
            n1.height=Math.max(height(n1.left),height(n1.right))+1;
            return n1;
        }

        public AVLNode<T> rotateWithRight(AVLNode<T> n2){
            AVLNode<T> n1=n2.right;
            n2.right=n1.left;
            n1.left=n2;
            n1.pare=n2.pare;
            if (n2.right!=null)
                n2.right.pare=n2;
            n2.pare=n1;
            n2.size=1+size(n2.right)+size(n2.left);
            n1.size=1+size(n2)+size(n1.right);
            n2.height=Math.max(height(n2.left),height(n2.right))+1;
            n1.height=Math.max(height(n1.left),height(n1.right))+1;
            return n1;
        }

        public AVLNode<T> doubleWithLeft(AVLNode<T> n3){
            n3.left=rotateWithRight(n3.left);
            return rotateWithLeft(n3);
        }

        public AVLNode<T> doubleWithRight(AVLNode<T> n3){
            n3.right=rotateWithLeft(n3.right);
            return rotateWithRight(n3);
        }


        public T findMin(AVLNode<T> node){
            if (node==null)
                return null;
            while(node.left!=null)
                node=node.left;
            return node.val;
        }

        public T findK(AVLNode<T> node,int k){
            if (node==null || k>size(node) || k<0){
                return null;
            }
            if (size(node.left)+1==k)
                return node.val;
            else{
                T t=null;
                int nowin= size(node.left)+1;
                if (k>nowin){
                    t=findK(node.right,k-nowin);
                }
                else{
                    t=findK(node.left,k);
                }
                return t;
            }
        }

        public AVLNode<T> remove(T t,AVLNode<T> node){
            if (node==null)
                return null;
            int cmp=t.compareTo(node.val);
            if (cmp>0){
                node.right=remove(t,node.right);
            }
            else if (cmp<0){
                node.left=remove(t,node.left);
            }
            else if (node.left!=null && node.right!=null){
                node.val=findMin(node.right);
                node.right=remove(node.val,node.right);
            }
            else{
                node=node.left!=null?node.left:node.right;
            }
            return balance(node);
        }
        public void remove(T t){
            root=remove(t,root);
        }


        public void print(AVLNode<T> node){
            if (node==null)
                return;
            else{
                print(node.left);
                System.out.println(node.val+" "+node.pare.val);
                print(node.right);
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
