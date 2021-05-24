import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class LearningDequeue {
    private static class MyDeque<T>{
        public MyList<T> list;

        public MyDeque(){
            list=new MyList<>(null);
        }
        public void enFront(T t){
            list.moveToHead();
            list.add(t);
        }

        public void enRear(T t){
            list.moveToFinal();
            list.addBack(t);
        }

        public T popFront(){
            if (list.point==null){
                return null;
            }
            else{
                list.moveToHead();
                T t=list.point.val;
                list.delete();
                return t;
            }
        }

        public T popRear(){
            if (list.point==null){
                return null;
            }
            else{
                list.moveToFinal();;
                T t=list.point.val;
                list.delete();;
                return t;
            }
        }

        public int size(){
            return list.daxiao;
        }

        public void clear(){
            list=new MyList<>(null);
        }

        public boolean isEmpty(){
            return list.daxiao==0;
        }

        public void print(){
            list.moveToHead();
            for (int i=0;i< list.daxiao;i++){
                System.out.print(list.point.val+" ");
                list.moveRight();
            }
            System.out.println();
        }

        public void reserveAndAbsorb(MyDeque<T> tt){
            int k=tt.list.daxiao;
            for (int i=0;i<k;i++){
                this.enRear(tt.popRear());
            }
        }

        public void absorb(MyDeque<T> tt){
            int k=tt.list.daxiao;
            for (int i=0;i<k;i++){
                this.enRear(tt.popFront());
            }
        }
    }

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
            if (point==null){

            }
            else{
                if (point.prev == null && point.next != null) {
                    Node<T> newHead = point.next;
                    point.next=null;
                    headNode = newHead;
                    newHead.prev = null;
                    point = newHead;
                    daxiao--;
                }
                else if(point.prev==null && point.next==null){
                    point=null;
                    headNode=null;
                    finaNode=null;
                    daxiao=0;
                }
                else if (point.prev!=null && point.next==null){
                    point=finaNode.prev;
                    finaNode.prev=null;
                    finaNode=point;
                    finaNode.next=null;
                    nowIndex--;
                    daxiao--;
                }
                else{
                    Node<T> qian=point.prev;
                    Node<T> hou=point.next;
                    point.next=null;
                    point.prev=null;
                    qian.next=hou;
                    hou.prev=qian;
                    point=hou;
                    daxiao--;
                }
            }
        }

        public void cutAndGive(int a,MyList<T> alist){
            if (this.daxiao==0){
                return;
            }
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
                begin.prev=alist.point;
                alist.finaNode=pastFinal;
                alist.daxiao= alist.daxiao+yuandaxiao-a;
            }
        }

        public T getHead(){
            return headNode.val;
        }

        public T getFinal(){
            return finaNode.val;
        }
    }

    private static class InputReader {
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

    public static void main(String[] args)  {
        InputStream inputStream=System.in;
        OutputStream outputStream=System.out;
        InputReader in=new InputReader(inputStream);
        PrintWriter out=new PrintWriter(outputStream);
        int numOfQ=0;
        int numOfOpera=0;
        int opera=0;
        int u=0;
        int v=0;
        int inVal=0;
        MyDeque<Integer>[] deques=null;
        while (in.hasNext()){
            numOfQ=in.nextInt();
            deques=new MyDeque[numOfQ];
            numOfOpera=in.nextInt();
            for (int i=0;i<numOfOpera;i++){
                opera=in.nextInt();
                if (opera==1){
                    u=in.nextInt();
                    v=in.nextInt();
                    inVal=in.nextInt();
                    if (deques[u-1]==null){
                        deques[u-1]=new MyDeque<>();
                    }
                    if (v==1){
                        deques[u-1].enRear(inVal);
                    }
                    else{
                        deques[u-1].enFront(inVal);
                    }
                }
                else if (opera==2){
                    u=in.nextInt();
                    v=in.nextInt();
                    if (deques[u-1]==null||deques[u-1].isEmpty())
                        out.println(-1);
                    else{
                        if (v==1)
                            out.println(deques[u-1].popRear());
                        else
                            out.println(deques[u-1].popFront());
                    }
                }
                else if (opera==3){
                    u=in.nextInt();
                    v=in.nextInt();
                    inVal=in.nextInt();
                    if (deques[u-1]==null){
                        deques[u-1]=new MyDeque<>();
                    }
                    if (deques[v-1]==null){
                        deques[v-1]=new MyDeque<>();
                    }
                    if (inVal==1){
                        deques[u-1].reserveAndAbsorb(deques[v-1]);
                    }
                    else{
                        deques[u-1].absorb(deques[v-1]);
                    }
                }
            }
        }
        out.close();
    }


}
