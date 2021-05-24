import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Polynomial {
    public static void main(BlockLinkedList[] args) throws IOException {
        Reader in = new Reader();
        int zhixincishu = in.nextInt();
        int yi = 0;
        int er = 0;
        int coe = 0;
        int expo = 0;
        StringBuilder[] answers = new StringBuilder[zhixincishu];
        Iterator<Item> l1=new Iterator<>(null);
        Iterator<Item> l2=new Iterator<>(null);
        Iterator<Item> l3=new Iterator<>(null);
        for (int i = 0; i < zhixincishu; i++) {
            yi=in.nextInt();
            for (int j=0;j<yi;j++){
                coe=in.nextInt();
                expo=in.nextInt();
                l1.addBack(new Item(coe,expo));
            }
            l1.addBack(new Item(0,-1));
            l1.point=l1.headNode;

            er=in.nextInt();
            for (int j=0;j<er;j++){
                coe=in.nextInt();
                expo=in.nextInt();
                l2.addBack(new Item(coe,expo));
            }
            l2.addBack(new Item(0,-1));
            l2.point=l2.headNode;
            Item fromOne=null;
            Item fromTwo=null;
            Item fromThree=null;
            while(l2.point.next!=null || l1.point.next!=null){
                fromOne=l1.point.val;
                fromTwo=l2.point.val;
                if (l3.daxiao!=0)
                    fromThree=l3.point.val;
                if (l2.point.next==null || (l1.point.next!=null && fromOne.expo<=fromTwo.expo)){
                    if (l3.daxiao==0 || (fromThree.expo!= fromOne.expo)) {
                        l3.addBack(fromOne);
                    }
                    else{
                        l3.replace(new Item(fromThree.coeffi+ fromOne.coeffi,fromOne.expo ));
                    }
                    l1.moveRight();
                }
                else{
                    if (l3.daxiao==0 || (fromThree.expo!= fromTwo.expo)) {
                        l3.addBack(fromTwo);
                    }
                    else{
                        l3.replace(new Item(fromThree.coeffi+ fromTwo.coeffi,fromTwo.expo ));
                    }
                    l2.moveRight();
                }
            }
            l3.addBack(new Item(0,-1));
            l3.point=l3.headNode;
            fromThree=null;
            int coef=0;
            int exp=0;
            answers[i] = new StringBuilder();
            while (l3.point.next!=null) {
                fromThree =  l3.point.val;
                coef= fromThree.coeffi;
                exp=fromThree.expo;
                if (coef==0){

                }
                else if (coef>0){
                    if (coef==1){
                        if (exp==1){
                            answers[i].append("+x");
                        }
                        else if (exp==0){
                            answers[i].append("+1");
                        }
                        else{
                            answers[i].append("+x^").append(exp);
                        }
                    }
                    else{
                        if (exp==1){
                            answers[i].append("+").append(coef).append("x");
                        }
                        else if (exp==0){
                            answers[i].append("+").append(coef);
                        }
                        else{
                            answers[i].append("+").append(coef).append("x^").append(exp);
                        }
                    }
                }
                else{
                    if (coef==-1){
                        if (exp==1){
                            answers[i].append("-x");
                        }
                        else if (exp==0){
                            answers[i].append("-1");
                        }
                        else{
                            answers[i].append("-x^").append(exp);
                        }
                    }
                    else{
                        if (exp==1){
                            answers[i].append(coef).append("x");
                        }
                        else if (exp==0){
                            answers[i].append(coef);
                        }
                        else{
                            answers[i].append(coef).append("x^").append(exp);
                        }
                    }
                }
                l3.moveRight();
            }
            if (answers[i].length()==0)
                answers[i]=new StringBuilder("0");
            if (answers[i].length() != 0 && answers[i].charAt(0) == '+') {
                answers[i] = answers[i].deleteCharAt(0);
            }
            l1=new Iterator<>(null);
            l2=new Iterator<>(null);
            l3=new Iterator<>(null);
        }

        for (int i = 0; i < zhixincishu; i++) {
            System.out.println(answers[i]);
        }
    }

    static class Item {
        public int coeffi;
        public int expo;

        public Item(int a, int b) {
            coeffi = a;
            expo = b;
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

    private static class Node<T> {
        public T val;
        public Node<T> next;
        public Node<T> prev;

        public Node(T value) {
            val = value;
        }
    }

    private static class Iterator<T> {
        public Node<T> point;
        public Node<T> headNode;
        public Node<T> finaNode;
        public int nowIndex = 0;
        public int daxiao=0;

        public Iterator(Node<T> a) {
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
                daxiao++;
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
    }
}
