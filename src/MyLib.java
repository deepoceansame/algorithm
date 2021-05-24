import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class MyLib {

    private static class Node<T> {
        public T val;
        public Node<T> next;
        public Node<T> prev;

        public Node(T value) {
            val = value;
        }
    }

    private static class MyList<T> {
        public Node<T> point;
        public Node<T> headNode;
        public Node<T> finaNode;
        public int nowIndex = 0;
        public int daxiao=0;

        public MyList(Node<T> a) {
            point = a;
            headNode = a;
            finaNode = a;
            if (a!=null)
                daxiao++;
        }

        public void moveRight() {
            if (point.next == null)
                ;
            else{
                point = point.next;
                nowIndex++;
            }
        }

        public void moveToHead(){
            point=headNode;
            nowIndex=0;
        }
        public void moveToFinal(){
            point=finaNode;
            nowIndex=daxiao-1;
        }
        public void moveLeft() {
            if (point.prev==null)
                ;
            else{
                point = point.prev;
                nowIndex--;
            }
        }

        public void replace(T a) {
            point.val = a;
        }

        public void add(T a) {
            if (point==null){
                Node<T> newnode=new Node<T>(a);
                point=newnode;
                headNode=newnode;
                finaNode=newnode;
                daxiao++;
            }
            else{
                if (point.prev == null) {
                    Node<T> newnode = new Node<>(a);
                    point.prev = newnode;
                    newnode.next = point;
                    headNode = newnode;
                }
                else {
                    Node<T> newnode = new Node<>(a);
                    point.prev.next = newnode;
                    newnode.prev = point.prev;
                    newnode.next = point;
                    point.prev = newnode;
                }
                daxiao++;
                nowIndex++;
            }
        }
        public void addBack(T a){
            if (point==null){
                Node<T> newnode=new Node<T>(a);
                point=newnode;
                headNode=newnode;
                finaNode=newnode;
            }
            else{
                if (point.next==null){
                    Node<T> newnode=new Node<>(a);
                    point.next=newnode;
                    newnode.prev=point;
                    finaNode=newnode;
                }
                else{
                    Node<T> newnode=new Node<>(a);
                    point.next.prev=newnode;
                    newnode.prev=point;
                    newnode.next=point.next;
                    point.next=newnode;
                }
            }
            daxiao++;
            moveRight();
        }
        public void delete() {
            if (point.prev == null && point.next != null) {
                Node<T> newHead = point.next;
                headNode = newHead;
                newHead.prev = null;
                point = newHead;
            }
            else if(point.prev==null && point.next==null){
                point=null;
                headNode=null;
                finaNode=null;
            }
            else if (point.prev!=null && point.next==null){
                point=finaNode.prev;
                finaNode=point;
                finaNode.next=null;
                nowIndex--;
            }
            else{
                Node<T> qian=point.prev;
                Node<T> hou=point.next;
                qian.next=hou;
                hou.prev=qian;
                point=hou;
            }
            daxiao--;
        }

        public void cutAndGive(int a,MyList<T> alist){
            Node<T> pastFinal =finaNode;
            point=headNode;
            int yuandaxiao=daxiao;
            for (int i=0;i<a;i++){
                moveRight();
            }
            Node<T> begin=point;
            if (begin.prev!=null) {
                finaNode = begin.prev;
                point=finaNode;
                finaNode.next=null;
                daxiao=a;
                begin.prev = null;
            }
            else{
                point=null;
                headNode=null;
                finaNode=null;
                daxiao=0;
                nowIndex=0;
            }
            if (alist.daxiao==0){
                alist.addBack(null);
                alist.point.next=begin;
                begin.prev=alist.point;
                alist.delete();
                alist.daxiao= yuandaxiao-a;
                alist.finaNode=pastFinal;
            }
            else{
                alist.point=alist.finaNode;
                alist.point.next=begin;
                alist.finaNode=pastFinal;
                alist.daxiao= alist.daxiao+yuandaxiao-a;
            }
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
            while (a>1 && comp.compare((T)arr[a],(T)arr[father(a)])>0){ //默认是大顶堆
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

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public char[] nextCharArray() {
            return next().toCharArray();
        }

        public boolean hasNext() {
            try {
                String string = reader.readLine();
                if (string == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(string);
                return tokenizer.hasMoreTokens();
            } catch (IOException e) {
                return false;
            }
        }

        public BigInteger nextBigInteger() {
            return new BigInteger(next());
        }

        public BigDecimal nextBigDecinal() {
            return new BigDecimal(next());
        }
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
                if (node.right!=null)
                    node.right.pare=node;
            }
            else if (cmp<0){
                node.left=remove(t,node.left);
                if (node.left!=null)
                    node.left.pare=node;
            }
            else if (node.left!=null && node.right!=null){
                node.val=findMin(node.right);
                node.right=remove(node.val,node.right);
                if (node.right!=null)
                    node.right.pare=node;
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

        public static void main(String[] args) {
            MyAVL<Integer> aavl=new MyAVL<>(null);
            aavl.insert(16);
            aavl.insert(15);
            aavl.insert(17);
            aavl.insert(14);
            aavl.remove(15);
            System.out.println(aavl.size(aavl.root));
        }
    }

    private static class MyTreeArray{
        int[] A;
        int[] C;
        int len;
        public MyTreeArray(int[] a){
            len=a.length;
            A=a;
            C=new int[len];
            for (int i=1;i<len;i++){
                update(i,a[i]);
            }
        }

        public int lowbit(int x){
            return x&(-x);
        }

        public void update(int i,int k){
            A[i]=A[i]+k;
            while(i<C.length){
                C[i]+=k;
                i+=lowbit(i);
            }
        }

        public int getPreSum(int n){
            if (n>=C.length){
                n=C.length-1;
            }
            if (n<=0){
                return 0;
            }
            int sum=0;
            while(n>=1){
                sum+=C[n];
                n-=lowbit(n);
            }
            return sum;
        }

        public int getPosSum(int n){
            if (n>=C.length){
                return 0;
            }
            if (n<=0){
                n=1;
            }
            return getPreSum(C.length-1)-getPreSum(n-1);
        }

    }

    private static class Discretization{
        int[] discrete;
        int size;
        public Discretization(int[] nums){
            Set<Integer> set=new HashSet<>();
            for (int num:nums){
                set.add(num);
            }
            size=set.size();
            discrete=new int[size];
            int ind=0;
            for (int num:set){
                discrete[ind]=num;
                ind++;
            }
            Arrays.sort(discrete);
        }
        public int getId(int x){
            return Arrays.binarySearch(discrete,x)+1;
        }
    }

    public static void main(String[] args) {

    }
}
