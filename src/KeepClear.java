import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.StringTokenizer;



public class KeepClear {


    public static void main(String[] args) {
        int a=Integer.MAX_VALUE;
        int b=1;
        System.out.println(a+b);
     }

     public static void swap(int i,int t,int[] a){
        int temp=a[i];
        a[i]=a[t];
        a[t]=temp;
     }
     @Test
   public void test(){
        int a=1;
        int b=0;
         System.out.println(~a);
   }
    private static class InputReader {
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

        //         public boolean hasNext() {
//             try {
//                 return reader.ready();
//             } catch(IOException e) {
//                 throw new RuntimeException(e);
//             }
//         }
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

    /*MyDGraph g=new MyDGraph(12,20);
        g.addEdge(1,0);
        g.addEdge(0,2);
        g.addEdge(2,1);
        g.addEdge(4,0);
        g.addEdge(3,1);
        g.addEdge(3,4);
        g.addEdge(4,5);
        g.addEdge(5,3);
        g.addEdge(5,10);
        g.addEdge(10,11);
        g.addEdge(11,5);
        g.addEdge(4,6);
        g.addEdge(9,9);
        g.addEdge(9,4);
        g.addEdge(9,6);
        g.addEdge(9,7);
        g.addEdge(8,7);
        g.addEdge(7,8);
        g.addEdge(8,6);
        g.addEdge(6,3);
        Collapse cc=new Collapse(g);*/
}
