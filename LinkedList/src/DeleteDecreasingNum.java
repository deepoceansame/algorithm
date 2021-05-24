import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.IllegalFormatCodePointException;

public class DeleteDecreasingNum {
    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        int zhixincishu=in.nextInt();
        int geshu=0;
        MyList<MyList<Integer>> l1=new MyList<>(null);
        int[] zancun=new int[100000];
        Integer[][] answers=new Integer[zhixincishu][];
        boolean create=false;
        l1.addBack(new MyList<Integer>(null) );
        for (int i=0;i<zhixincishu;i++){

            geshu=in.nextInt();
            answers[i]=new Integer[geshu+1];
            for (int j=0;j<geshu;j++){
                zancun[j]=in.nextInt();
            }

            for (int j=0;j<geshu;j++){
                if (!((j>0 && zancun[j-1]>zancun[j])||(j<geshu-1 && zancun[j]>zancun[j+1]))){
                    if (create){
                        if (l1.point.val.daxiao!=0){
                        l1.addBack(new MyList<>(null));
                        l1.point.val.addBack(zancun[j]);
                        }
                        else{
                            l1.point.val.addBack(zancun[j]);
                        }
                        create=false;
                    }
                    else
                    l1.point.val.addBack(zancun[j]);
                }
                else{
                    create=true;
                }
            }
            l1.moveToHead();
            l1.moveRight();
            int num=0;
            boolean nowisfinal=false;
            while(l1.daxiao>1){
                MyList<Integer> qian=l1.point.prev.val;
                MyList<Integer> now=l1.point.val;
                if (now.daxiao==0){
                    l1.delete();
                    now=l1.point.val;
                }
                if (l1.nowIndex==l1.daxiao-1){
                    nowisfinal=true;
                }

                if (now.getHead()>=qian.getFinal()){
                    now.cutAndGive(0,qian);
                    l1.delete();
                    l1.moveLeft();
                }
                else if (now.getHead()<qian.getFinal() && now.daxiao==1){
                    num=now.getHead();
                    qian.moveToFinal();
                    qian.delete();
                    now.moveToHead();
                    now.delete();
                    if (qian.daxiao==0){
                        l1.moveLeft();
                        l1.delete();
                    }
                    l1.delete();
                    if (!nowisfinal){
                    int zhibiao=0;
                        while (zhibiao==0){
                        if (l1.daxiao==0)
                            break;
                        MyList<Integer> templ=l1.point.val;
                        if (templ.getHead()<num){
                            if (templ.daxiao==1){
                                if (l1.nowIndex==l1.daxiao-1){
                                    zhibiao=1;
                                }
                                num= templ.getHead();
                                l1.delete();
                            }
                            else{
                                templ.moveToHead();
                                templ.delete();
                                l1.moveLeft();
                                break;
                            }
                        }
                             else{
                                break;
                             }
                        }
                    }
                }

                else if (now.getHead()<qian.getFinal() && now.daxiao>1){
                    qian.moveToFinal();
                    qian.delete();
                    now.moveToHead();
                    now.delete();
                    if (qian.daxiao==0){
                        l1.moveLeft();
                        l1.delete();
                    }
                }


                if (l1.daxiao==0){
                    break;
                }
                if (l1.nowIndex==l1.daxiao-1){
                    l1.moveToHead();
                    l1.moveRight();
                    nowisfinal=false;
                }
                else{
                    l1.moveRight();
                }
            }

            int ind=0;
            l1.moveToHead();
            for (int j=0;j<l1.daxiao;j++){
                MyList<Integer> templ=l1.point.val;
                templ.moveToHead();
                for (int k=0;k<templ.daxiao;k++){
                    answers[i][ind]=templ.point.val;
                    ind++;
                    templ.moveRight();
                }
                l1.moveRight();
            }


            l1=new MyList<>(null);
            l1.addBack(new MyList<>(null));
        }


        for (int i=0;i<zhixincishu;i++){
            int j=0;
            while(answers[i][j]!=null){
                if (answers[i][j+1]==null){
                    System.out.print(answers[i][j]);
                }
                else
                System.out.print(answers[i][j]+" ");
                j++;
            }
            if (i!=zhixincishu-1)
            System.out.println();
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
                point.next=null;
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
                finaNode.prev=null;
                finaNode=point;
                finaNode.next=null;
                nowIndex--;
            }
            else{
                Node<T> qian=point.prev;
                Node<T> hou=point.next;
                point.next=null;
                point.prev=null;
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
    private static class Reader {
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
