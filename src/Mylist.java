import org.junit.Test;

public class Mylist<T> {
    private node head=new node();
    int length=0;

    public Mylist(){
    }
    public Mylist(T t){
        head.content=t;
        length++;
    }

    class node<T>{
        private node<T> last=null;
        private T content;
        private node<T> next=null;

        public node(T t){
            content=t;
        }

        public node(){

        }
    }

    public void add(T t){
        node now=head;
        while(now.next!=null){
            now=now.next;
        }
        now.next=new node(t);
        now.next.last=now;
        length++;
    }

    public T get(int index){
        node now=head;
        int i=0;
        while(now.next!=null && i<index){
            now=now.next;
            i++;
        }
        return (T)now.content;
    }

    public void delete(int index){
        node now=head;
        int i=0;
        while(now.next!=null && i<index){
            now=now.next;
            i++;
        }
        if (now==head){
            now.next.last=null;
            this.head=now.next;
            length--;
        }
        else if (now.next!=null){
        now.next.last=now.last;
        now.last.next=now.next;
        length--;}
        else{
            now.last.next=null;
            length--;
        }
    }

    public static void main(String[] args) {
        Mylist<Integer> list=new Mylist<>(12);
        list.add(13);
        list.add(14);
        list.add(15);
        System.out.println(list.length);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));
        list.delete(0);
        System.out.println(list.length);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
    }

}
