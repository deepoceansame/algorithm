import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class EditSequence {
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

    public static void main(String[] args) throws IOException {
        InputReader in=new InputReader(System.in);
        PrintWriter out=new PrintWriter(System.out);
        int ins=in.nextInt();
        char c=' ';
        int zhi=0;
        MyList<Integer> list=new MyList<>(null);
        boolean zuiqian=false;
        for (int i=0;i<ins;i++){
           c=in.next().charAt(0);
           if (c=='I'){
               zhi=in.nextInt();
               if (zuiqian){
                   list.add(zhi);
                   list.moveToHead();
                   zuiqian=false;
               }
               else{
               list.addBack(zhi);
               }
           }

           else if (c=='D'){
               list.delete();
               list.moveLeft();
           }

           else if (c=='L'){
               if (list.nowIndex==0){
                   if (!zuiqian){
                       zuiqian=true;
                   }
               }
               else
               list.moveLeft();
           }

           else if (c=='R'){
               if (zuiqian){
                   zuiqian=false;
               }
               else
               list.moveRight();
           }

           else{
               zhi=in.nextInt();
               out.println(sum(list,zhi));
           }
        }

        out.close();
    }

    public static long sum(MyList<Integer> list,int k){
        Node<Integer> temp=list.point;
        list.moveToHead();
        long answer= list.point.val;
        long tempan=list.point.val;
        list.moveRight();
        for (int i=1;i<k;i++){
            tempan=tempan+list.point.val;
            if (tempan>answer){
                answer=tempan;
            }
            list.moveRight();
        }
        list.point=temp;
        return answer;
    }
}
