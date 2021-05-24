import com.sun.jdi.PathSearchingVirtualMachine;

public class WeakRabinKarp {
    private static class MyRabinKarp{
        public long patHash;
        public int M;
        public long wenti;
        public static int R=256;
        public static long Q=997;
        public MyRabinKarp(String pat){
            M=pat.length();
            patHash=theHash(pat,M);
            wenti=1;
            for (int i=0;i<M-1;i++){
                wenti=(wenti*R)%Q;
            }
        }

        public MyList<Integer> search(String txt){
            MyList<Integer> answer=new MyList<>(null);
            long txtHash=theHash(txt,M);
            if (txtHash==patHash){
                answer.addBack(0);
            }
            for (int i=M;i<txt.length();i++){
                txtHash=(txtHash+Q-wenti*txt.charAt(i-M)%Q)%Q;
                txtHash=(txtHash*R+txt.charAt(i))%Q;
                if (txtHash==patHash)
                    answer.addBack(i-M+1);
            }
            return answer;
        }


            public long theHash(String s,int M){
            long answer=0;
            for (int i=0;i<M;i++){
                answer=(answer*R+s.charAt(i))%Q;
            }
            return answer;
        }


        public static void main(String[] args) {
            MyRabinKarp aa=new MyRabinKarp("你有几块钱");
            MyList<Integer> answer=aa.search("他说他有三块钱，可是你有几块钱，我不知道你有几块钱，知道你有几块钱的人都死了，所以你有几块钱");
            System.out.println(answer.daxiao);
            answer.moveToHead();
            for (int i=0;i< answer.daxiao;i++){
                System.out.println(answer.point.val);
                answer.moveRight();
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
    }


}
