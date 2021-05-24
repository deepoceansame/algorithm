import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.StringTokenizer;

public class WeakHuffmanTree {
    public static void main(String[] args) {
        InputReader in=new InputReader(System.in);
        PrintWriter out=new PrintWriter(System.out);
        int zhixincishu=in.nextInt();
        String s="";
        MyHuffmanTree huff=null;
        long ans=0;
        for (int i=0;i<zhixincishu;i++){
            s=in.next();
            ans=0;
            huff=new MyHuffmanTree(s);
            huff.writeTree(huff.root,"");
            for (int j=0;j<256;j++){
                if (huff.code[j]!=null)
                ans+=huff.freq[j]*huff.code[j].length();
            }
            out.println(ans);
        }
        out.close();
    }
    private static class MyHuffmanTree{
        public String txt;
        public int[] freq=new int[256];
        public String[] code=new String[256];
        public HfNode root;
        public MyHuffmanTree(String s){
            txt=s;
            for (int i=0;i<s.length();i++){
                freq[s.charAt(i)]++;
            }
            root=buildTree();
        }


        public HfNode buildTree(){
            MyPQ<HfNode> pq=new MyPQ<>(txt.length(), (HfNode x,HfNode y)->y.freq-x.freq);
            for (char i=0;i<256;i++){
                if (freq[i]>0)
                    pq.offer(new HfNode(i,freq[i],null,null));
            }

            while (pq.size>1){
                HfNode x=pq.poll();
                HfNode y=pq.poll();
                pq.offer(new HfNode('\0',x.freq+y.freq,x,y));
            }

            return pq.poll();
        }

        public void writeTree(HfNode node,String s){
            if (node.isleaf()){
                code[node.ch]=s;
                return;
            }

            String ls=s+'0';
            String rs=s+'1';
            writeTree(node.left,ls);
            writeTree(node.right,rs);

        }
    }

    private static class HfNode{
        public char ch;
        public int freq;
        public HfNode right,left;
        public HfNode(char ch,int freq,HfNode left,HfNode right){
            this.ch=ch;
            this.freq=freq;
            this.left=left;
            this.right=right;
        }

        public boolean isleaf(){
            return left==null&&right==null;
        }
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
            while (a>1 && comp.compare((T)arr[a],(T)arr[father(a)])>0){
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

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public char[] nextCharArray() {
            return next().toCharArray();
        }

        public boolean hasNext() {
            try {
                String string = reader.readLine();
                if (string == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(string);
                return tokenizer.hasMoreTokens();
            } catch (IOException e) {
                return false;
            }
        }

        public BigInteger nextBigInteger() {
            return new BigInteger(next());
        }

        public BigDecimal nextBigDecinal() {
            return new BigDecimal(next());
        }
    }
}
