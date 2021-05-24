import java.util.Scanner;

public class MoFanText {
    public static void main(BlockLinkedList[] args) {
        Scanner in = new Scanner(System.in);
        int zhixincishu = Integer.parseInt(in.nextLine());
        int[][] answers=new int[zhixincishu][];
        int length = 0;
        String s = "";
        for (int j = 0; j < zhixincishu; j++) {
            length = Integer.parseInt(in.nextLine());
            s = in.nextLine();
            Node eol = new Node(-1);
            Iterator walker = new Iterator(eol);
            for (int i = 0; i < s.length(); i++) {
                if (Character.isDigit(s.charAt(i)))
                    walker.add(Integer.parseInt(s.charAt(i) + ""));
                else {
                    if (s.charAt(i) == 'r') {
                        if (i+1==s.length()){

                        }
                        else{
                        int num = Integer.parseInt(s.charAt(i + 1) + "");
                        walker.replace(num);
                        i++;}
                    } else if (s.charAt(i) == 'I') {
                        walker.point = walker.headNode;
                    } else if (s.charAt(i) == 'H') {
                        walker.moveLeft();
                    } else if (s.charAt(i) == 'L') {
                        walker.moveRight();
                    } else if (s.charAt(i) == 'x') {
                        walker.delete();
                    }
                }
            }
            answers[j]=new int[walker.daxiao-1];
            walker.point= walker.headNode;
            for (int i=0;i<walker.daxiao-1;i++){
                answers[j][i]=walker.point.val;
                walker.moveRight();
            }
        }
        for (int i = 0; i < zhixincishu; i++) {
            for (int j=0;j<answers[i].length;j++){
                System.out.print(answers[i][j]);
            }
            System.out.println();
        }
    }

    private static class Node {
        public int val;
        public Node next;
        public Node prev;

        public Node(int value) {
            val = value;
        }
    }

    private static class Iterator {
        public Node point;
        public Node headNode;
        public Node finaNode;
        public int daxiao = 0;

        public Iterator(Node a) {
            point = a;
            headNode = a;
            finaNode = a;
            daxiao = 1;
        }

        public void moveRight() {
            if (point.next == null)
                ;
            else
                point = point.next;
        }

        public void moveLeft() {
            if (point.prev==null)
                ;
            else
                point = point.prev;
        }

        public void replace(int a) {
            if (point.val == -1) {
                point.val = a;
                Node newfinal = new Node(-1);
                point.next = newfinal;
                newfinal.prev = point;
                this.finaNode = newfinal;
                daxiao++;
            } else
                point.val = a;
        }

        public void add(int a) {
            if (point.prev == null) {
                Node newnode = new Node(a);
                point.prev = newnode;
                newnode.next = point;
                headNode = newnode;
            } else {
                Node newnode = new Node(a);
                point.prev.next = newnode;
                newnode.prev = point.prev;
                newnode.next = point;
                point.prev = newnode;
            }
            daxiao++;
        }
        public void delete() {
            if (point.prev == null && point.next != null) {
                Node newHead = point.next;
                headNode = newHead;
                newHead.prev = null;
                point = newHead;
                daxiao--;
            } else if (point.val == -1) {
                ;
            } else {
                Node qian = point.prev;
                Node hou = point.next;
                qian.next = hou;
                hou.prev = qian;
                point = hou;
                daxiao--;
            }

        }
    }
}
