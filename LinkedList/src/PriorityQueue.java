import java.util.Comparator;

public class PriorityQueue {

    public static void main(String[] args) {
        MyPQ<Integer> apq=new MyPQ<>(10,(x,y)->x-y);
        apq.offer(5);
        apq.offer(5);
        apq.offer(5);
        apq.offer(5);
        apq.offer(5);

        System.out.println(apq.peek());
        System.out.println(apq.poll());
        
    }

    private static class MyPQ<T>{
        Object[] arr;
        Comparator<T> comp;
        int size=0;
        int shuzhudaxiao;
        public MyPQ (int shuzhudaxiao,Comparator<T> comparator){
            arr=new Object[shuzhudaxiao];
            comp=comparator;
            arr[0]=null;
            this.shuzhudaxiao=shuzhudaxiao;
        }

        public int left(int father){
            return father*2;
        }

        public int right(int father){
            return father*2+1;
        }

        public int father(int child){
            return child/2;
        }

        public void exch(int a,int b){
            Object temp=arr[a];
            arr[a]=arr[b];
            arr[b]=temp;
        }

        public void goUp(int a){
            while (a>1 && comp.compare((T)arr[a],(T)arr[father(a)])>0){ //默认是大顶堆
                exch(a,father(a));
                a=father(a);
            }
        }

        public void goDown(int a){
            while(2*a<=size){
                int j=2*a;
                if (j<size && comp.compare((T)arr[j+1],(T)arr[j])>0) j++;
                if (comp.compare((T)arr[a],(T)arr[j])<0)
                    exch(a,j);
                a=j;
            }
        }

        public void offer(T a){
            arr[size+1]=a;
            size++;
            goUp(size);
        }

        public T peek(){
            return (T)arr[1];
        }

        public T poll(){
            T toReturn =(T)arr[1];
            exch(1,size);
            arr[size]=null;
            size--;
            goDown(1);
            return toReturn;
        }

        public int size(){
            return size;
        }

        public void clear(){
            arr=new Object[shuzhudaxiao];
            size=0;
        }
    }
}
