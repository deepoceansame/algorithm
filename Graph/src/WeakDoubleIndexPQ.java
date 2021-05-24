public class WeakDoubleIndexPQ {
    private static class MyDoubleIndexPQ<T extends Comparable<? super T>>{
        public Object[][] items;
        public int[][] pos;
        public Ind[] arr;
        public int size;

        public MyDoubleIndexPQ(int rcap,int ccap){
            items=new Object[rcap][ccap];
            pos=new int[rcap][ccap];
            arr=new Ind[rcap*ccap+1];
            size=0;
        }

        public boolean contain(int row,int col){
            return items[row][col]!=null;
        }

        public void sink(int k){
            T zuo=null;
            T now=null;
            while(2*k<=size){
                now=((T)items[arr[k].r][arr[k].c]);
                zuo=(T)items[arr[2*k].r][arr[2*k].c];
                int j=2*k;
                if (j<size && ((T)items[arr[j+1].r][arr[j+1].c]).compareTo(zuo)<0) j++;
                if (now.compareTo((T)items[arr[j].r][arr[j].c])>0)
                    exch(k,j);
                k=j;
            }
        }

        public void goup(int k){
            while (k>1 && ((T)items[arr[k].r][arr[k].c]).compareTo((T)items[arr[k/2].r][arr[k/2].c])<0){
                exch(k,k/2);
                k=k/2;
            }
        }

        public void exch(int a,int b){
            pos[arr[a].r][arr[a].c]=b;
            pos[arr[b].r][arr[b].c]=a;
            Ind temp=arr[a];
            arr[a]=arr[b];
            arr[b]=temp;
        }

        public void insert(int r,int c,T t){
            size++;
            pos[r][c]=size;
            arr[size]=new Ind(r,c);
            items[r][c]=t;
            goup(size);
        }

        public void change(int r,int c,T t){
            items[r][c]=t;
            sink(pos[r][c]);
            goup(pos[r][c]);
        }

        public Ind pop(){
            Ind temp=arr[1];
            exch(1,size);
            arr[size]=null;
            size--;
            sink(1);
            pos[temp.r][temp.c]=0;
            items[temp.r][temp.c]=null;
            return temp;
        }

        public void delete(int r,int c){
            int a=pos[r][c];
            exch(a,size);
            arr[size]=null;
            size--;
            if (a<=size){
                goup(a);
                sink(a);
            }
            items[r][c]=null;
            pos[r][c]=0;
        }
    }

    private static class Ind{
        public int r;
        public int c;
        public Ind(int a,int b){
            r=a;
            c=b;
        }
    }

    public static void main(String[] args) {
        MyDoubleIndexPQ<Integer> pq=new MyDoubleIndexPQ<>(3,3);
        int[][] aa=new int[3][3];
        aa[0]= new int[]{3, 4, 9};
        aa[1]= new int[]{2, 1, 0};
        aa[2]= new int[]{8, 7, 5};

        pq.insert(0,0,3);
        pq.insert(0,1,4);
        pq.insert(0,2,9);
        pq.insert(1,0,2);
        pq.insert(1,1,1);
        pq.insert(1,2,6);
        pq.insert(2,0,8);
        pq.insert(2,1,7);
        pq.insert(2,2,5);
        pq.change(1,2,0);
        while (pq.size>0){
            Ind a= pq.pop();
            System.out.println(aa[a.r][a.c]);
        }
    }
}
