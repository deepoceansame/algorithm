import java.util.Scanner;

public class EatingOrder {
    public static void main(BlockLinkedList[] args) {
        Scanner in = new Scanner(System.in);
        int zhixincishu = in.nextInt();
        int n = 0;
        int k = 0;
        int[][] answers = new int[zhixincishu][];
        for (int j = 0; j < zhixincishu; j++) {
            n=in.nextInt();
            k=in.nextInt();
            answers[j]=new int[n];
            node head = new node(1);
            Iterator walker = new Iterator(head);
            for (int i = 2; i < 1+n; i++) {
                walker.add(i);
            }
            walker.move();
            int count = 1;
            int ind = 0;
            while (walker.point != null) {
                if (count != k) {
                    walker.move();
                    count++;
                } else {
                    answers[j][ind] = walker.point.val;
                    ind++;
                    walker.delete();
                    count = 1;
                }
            }
        }
        for (int i=0;i<zhixincishu;i++){
            for (int j=0;j<answers[i].length;j++){
                if (j==answers[i].length-1){
                    System.out.print(answers[i][j]);
                    System.out.print("\n");
                }
                else
                System.out.print(answers[i][j]+" ");
            }
        }
    }

    private static class node {
        public int val;
        public node next;
        public node prev;

        public node(int value) {
            val = value;
        }
    }

    private static class Iterator {
        public node point;
        public node headNode;
        public node finaNode;
        public int daxiao = 0;

        public Iterator(node a) {
            point = a;
            headNode = a;
            finaNode = a;
            daxiao = 1;
        }

        public void move() {
            if (point.next == null)
                point = headNode;
            else
                point = point.next;
        }

        public void add(int a) {
            if (point == null) {
                node newnode = new node(a);
                point = newnode;
                this.headNode = newnode;
                this.finaNode = newnode;
            } else {
                if (point.next != null) {
                    node anode = point.next;
                    node newnode = new node(a);
                    newnode.next = anode;
                    newnode.prev = point;
                    anode.prev = newnode;
                    point.next = newnode;
                } else {
                    node newnode = new node(a);
                    point.next = newnode;
                    newnode.prev = point;
                    finaNode = newnode;
                }
            }
            daxiao++;
            move();
        }

        public void delete() {
            if (point.prev == null && point.next != null) {
                node newHead = point.next;
                headNode = newHead;
                newHead.prev = null;
                point = newHead;
            } else if (point.next == null && point.prev != null) {
                node newFina = point.prev;
                finaNode = newFina;
                newFina.next = null;
                point = headNode;
            } else if (point.next == null && point.prev == null) {
                point = null;
            } else if (point.next != null && point.prev != null) {
                node qian = point.prev;
                node hou = point.next;
                qian.next = hou;
                hou.prev = qian;
                point = hou;
            }
            daxiao--;
        }
    }
}
