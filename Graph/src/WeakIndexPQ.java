public class WeakIndexPQ {
    private static class MyIndexPQ<T extends Comparable<? super T>>{
        public Object[] items;
        public int[] pos;
        public int[] arr;
        public int size;

        public MyIndexPQ(int cap){
            items=new Object[cap];
            pos=new int[cap];
            arr=new int[cap+1];
            size=0;
        }

        public boolean contain(int key){
            return items[key]!=null;
        }

        public void sink(int k){
            T zuo=null;
            T now=null;
            while(2*k<=size){
                now=((T)items[arr[k]]);
                zuo=(T)items[arr[2*k]];
                int j=2*k;
                if (j<size && ((T)items[arr[j+1]]).compareTo(zuo)<0) j++;
                if (now.compareTo((T)items[arr[j]])>0)
                    exch(k,j);
                k=j;
            }
        }

        public void goup(int k){
            while (k>1 && ((T)items[arr[k]]).compareTo((T)items[arr[k/2]])<0){
                exch(k,k/2);
                k=k/2;
            }
        }

        public void exch(int a,int b){
            pos[arr[a]]=b;
            pos[arr[b]]=a;
            int temp=arr[a];
            arr[a]=arr[b];
            arr[b]=temp;
        }

        public void insert(int k,T t){
            size++;
            pos[k]=size;
            arr[size]=k;
            items[k]=t;
            goup(size);
        }

        public void change(int k,T t){
            items[k]=t;
            sink(pos[k]);
            goup(pos[k]);
        }

        public int pop(){
            int temp=arr[1];
            exch(1,size);
            arr[size]=0;
            size--;
            sink(1);
            pos[temp]=0;
            items[temp]=null;
            return temp;
        }

        public void delete(int k){
            int a=pos[k];
            exch(a,size);
            arr[size]=0;
            size--;
            if (a<=size){
            goup(a);
            sink(a);
            }
            items[k]=null;
            pos[k]=0;
        }
    }

    public static void main(String[] args) {
        MyIndexPQ<Integer> pq=new MyIndexPQ<>(8);
        pq.insert(0,3);
        pq.insert(1,9);
        pq.insert(2,8);
        pq.insert(3,10);
        pq.insert(4,2);
        pq.insert(5,4);
        pq.insert(6,6);
        pq.insert(7,3);
        pq.change(3,1);
        pq.change(6,0);
        pq.delete(0);
        pq.delete(1);
        pq.delete(2);
        System.out.println(pq.contain(6));
    }
}
