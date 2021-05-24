import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class BlockLinkedList {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in=new InputReader(inputStream);
        PrintWriter out=new PrintWriter(outputStream);
        int zhixincishu=in.nextInt();
        MyList<Character> answers=new MyList<>(null);

        BlockList<Character> blockList=null;
        int sqrt=0;
        String s="";
        int caozuoshu=0;
        int operatioRepre=0;
        for (int i=0;i<zhixincishu;i++){


            s=in.next();
            caozuoshu=in.nextInt();
            sqrt=(int)Math.sqrt((double)(s.length()+caozuoshu))+1;
            blockList=new BlockList<>(sqrt);
            char insertChar=' ';
            int loc=1;
            for (int j=0;j<s.length();j++){
                blockList.add(s.charAt(j));
            }
            for (int j=0;j<caozuoshu;j++){
                operatioRepre= in.nextInt();
                if (operatioRepre==1){
                    insertChar=in.next().charAt(0);
                    loc=in.nextInt();
                    blockList.insert(loc,insertChar);
                }
                else if (operatioRepre==2){
                    loc=in.nextInt();
                    answers.addBack(blockList.get(loc));
                }
            }
        }

        answers.moveToHead();
        for (int i=0;i< answers.daxiao;i++){
            out.println(answers.point.val);
            answers.moveRight();
        }
        out.close();
    }


    private static class BlockList<T>{
        int maxsize;
        int daxiao=0;
        public MyList<MyList<T>> zhulian=new MyList<>(null);

        public BlockList(int a){
            maxsize=a;
        }

        public void add(T t){
            if (zhulian.point==null){
                zhulian.addBack(new MyList<>(null));
                zhulian.point.val.addBack(t);
                daxiao++;
            }
            else{
                zhulian.moveToFinal();
                MyList<T> templ=zhulian.point.val;
                templ.moveToFinal();
                templ.addBack(t);
                daxiao++;
                if (templ.daxiao>=maxsize)
                    liekai();
            }
        }

        public void insert(int a,T t){
            if (a<=daxiao){
            zhulian.moveToHead();
            int zhibiao=zhulian.point.val.daxiao;
            while (zhibiao < a) {
                zhulian.moveRight();
                zhibiao=zhibiao+zhulian.point.val.daxiao;
            }
            zhibiao=zhibiao-zhulian.point.val.daxiao;
            int indexInChildList=a-zhibiao;
            MyList<T> listOfTheNum=zhulian.point.val;
            listOfTheNum.moveToHead();
            for (int i=0;i<indexInChildList-1;i++){
                listOfTheNum.moveRight();
            }
            listOfTheNum.add(t);
            daxiao++;
            if (listOfTheNum.daxiao>=maxsize)
                liekai();
            }
            else{
                add(t);
            }
        }

        public T get(int a){
            if (a>daxiao)
                a=daxiao;
            zhulian.moveToHead();
            int zhibiao=zhulian.point.val.daxiao;
            while (zhibiao < a) {
                zhulian.moveRight();
                zhibiao=zhibiao+zhulian.point.val.daxiao;
            }
            zhibiao=zhibiao-zhulian.point.val.daxiao;
            int indexInChildList=a-zhibiao;
            MyList<T> listOfTheNum=zhulian.point.val;
            listOfTheNum.moveToHead();
            for (int i=0;i<indexInChildList-1;i++){
                listOfTheNum.moveRight();
            }
            return listOfTheNum.point.val;
        }

        public void liekai(){
            MyList<T> templ1=zhulian.point.val;
            zhulian.addBack(new MyList<>(null));
            MyList<T> templ2=zhulian.point.val;
            templ1.cutAndGive(maxsize/2+1,templ2);
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
}


