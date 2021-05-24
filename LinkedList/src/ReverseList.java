public class ReverseList {


    public static void main(String[] args) {
       alian aa=new alian();
       aa.add(1);
       aa.add(2);
       aa.add(3);
       aa.add(4);
       aa.add(5);
       aa.add(6);
       aa.add(7);
       aa.add(8);
       kNodek p1=aa.head;
       kNodek p2=aa.head.next;
       kNodek p3=aa.head.next.next;
       p1.next=null;
       while(p2!=null){
           p2.next=p1;
           p1=p2;
           p2=p3;
           if (p3!=null)
           p3=p3.next;
       }

        System.out.println(p1.a);
       while(p1.next!=null){
           p1=p1.next;
           System.out.println(p1.a);
       }
    }

    private static class kNodek{
        public int a;
        public kNodek next;
        public kNodek(int a){
            this.a=a;
        }
    }

    private static class alian{
        public kNodek head;
        public alian(){
            head=null;
        }

        public void add(int b){
            kNodek nn=new kNodek(b);
            if (head==null)
                head=nn;
            else{
                kNodek nm=head;
                while(nm.next!=null){
                    nm=nm.next;
                }
                nm.next=nn;
            }
        }


    }
}
