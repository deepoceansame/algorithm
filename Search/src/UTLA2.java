import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class UTLA2 {

    public static void main(String[] args) throws IOException {
        InputReader in=new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int zhixincishu = in.nextInt();
        int[] arr=null;
        char[] deterChars=null;
        MyQueue<NakaNode> que=null;
        int answer=0;
        for (int j = 0; j < zhixincishu; j++) {
            String s = in.next();
            Bao b = getArr(s);
            arr = b.arr;
            ArrayList<Character> deterChar = new ArrayList<>();
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] != 0) {
                    deterChar.add((char) i);
                }
            }
            deterChars = new char[deterChar.size()];
            for (int i = 0; i < deterChar.size(); i++) {
                deterChars[i] = deterChar.get(i);
            }
            if (deterChar.size()==0){
                out.println(0);
                continue;
            }
            que=new MyQueue();
            que.enqueue(new NakaNode("",0,0));
            answer=getBeautyQueueMode(que, b.tarr,b.arr,deterChars);
            out.println(answer);
        }
        out.close();
    }

    public static Bao getArr(String s) {
        int[][] tarr = new int[256][256];
        int[] arr = new int[256];
        ArrayList<Character> vowels = new ArrayList<>(6);
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
        for (int i = 0; i < s.length() - 1; i++) {
            char front = s.charAt(i);
            char rear = s.charAt(i + 1);
            if (!vowels.contains(front) && !vowels.contains(rear)) {
                tarr[front][rear]++;
                tarr[rear][front]++;
                arr[front]++;
                arr[rear]++;
            }
        }
        return new Bao(tarr, arr);
    }


    public static int getBeautyQueueMode(MyQueue<NakaNode> que,int[][] tarr,int[] arr,char[] deterChar){
        int beauty=0;
        NakaNode nownode=null;
        while(!que.isEmpty()){
            nownode=que.dequeue();
            int existBeautyForUpperThisCen = nownode.existedBeauty;
            char nowdeterChar = deterChar[nownode.cen];

            String charsHaveUpperTemp = nownode.charsHaveUpper + deterChar[nownode.cen];
            existBeautyForUpperThisCen = nownode.existedBeauty + arr[nowdeterChar];
            for (int i = 0; i < charsHaveUpperTemp.length(); i++) {
                if (charsHaveUpperTemp.charAt(i)==nowdeterChar){
                    existBeautyForUpperThisCen = existBeautyForUpperThisCen - tarr[nowdeterChar][charsHaveUpperTemp.charAt(i)];
                }
                else{
                    existBeautyForUpperThisCen = existBeautyForUpperThisCen - 2 * tarr[nowdeterChar][charsHaveUpperTemp.charAt(i)];
                }
            }
            if (nownode.cen==deterChar.length-1){
                beauty=Math.max(beauty,nownode.existedBeauty);
                beauty=Math.max(beauty,existBeautyForUpperThisCen);

            }
            else{
                if (charsHaveUpperTemp.length()<deterChar.length/2+2){
                que.enqueue(new NakaNode(nownode.charsHaveUpper, nownode.existedBeauty,nownode.cen+1));
                que.enqueue(new NakaNode(charsHaveUpperTemp,existBeautyForUpperThisCen,nownode.cen+1));
                }
            }
        }
        return beauty;
    }

    static class NakaNode{
        String charsHaveUpper;
        int existedBeauty;
        int cen;

        public NakaNode(String charsHaveUpper, int existedBeauty, int cen) {
            this.charsHaveUpper = charsHaveUpper;
            this.existedBeauty = existedBeauty;
            this.cen = cen;
        }
    }
    static class Bao {
        int[][] tarr;
        int[] arr;

        public Bao(int[][] tarr, int[] arr) {
            this.tarr = tarr;
            this.arr = arr;
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

    private static class MyQueue<T>{
        public MyList<T> list;
        public MyQueue(){
            list=new MyList<>(null);
        }

        public void enqueue(T t){
            list.moveToFinal();
            list.addBack(t);
        }

        public T dequeue(){
            if (list.point==null){
                return null;
            }
            list.moveToHead();
            T t=list.point.val;
            list.delete();
            return t;
        }

        public boolean isEmpty(){
            return list.daxiao==0;
        }

        public int size(){
            return list.daxiao;
        }

        public T getFront(){
            return list.getHead();
        }

        public boolean contain(T t){
            boolean c=false;
            list.moveToHead();
            for (int i=0;i<list.daxiao;i++){
                c=t.equals(list.point.val);
                if (c)
                    break;
            }
            return c;
        }
    }
}

