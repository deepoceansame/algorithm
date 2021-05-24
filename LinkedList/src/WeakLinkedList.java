public class WeakLinkedList {
    public static void main(BlockLinkedList[] args) {
        Iterator<Integer> ll=new Iterator<>(null);
        ll.addBack(14);
        ll.addBack(13);
        System.out.println(ll.point.val);
        ll.moveLeft();
        ll.add(12);
        ll.moveLeft();
        System.out.println(ll.point.val);
        System.out.println(ll.nowIndex);
        System.out.println(ll.daxiao);
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

        public void add(T a) { //在前面加一个节点
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
    }
}
