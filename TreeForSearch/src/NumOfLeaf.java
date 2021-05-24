import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class NumOfLeaf {

    public static void main(String[] args) {
        InputReader in=new InputReader(System.in);
        PrintWriter out=new PrintWriter(System.out);
        int zhixincishu=in.nextInt();
        long n=0;
        long k=0;
        long le=0;
        long js=0;
        long ejs=0;
        for (int i=0;i<zhixincishu;i++){
            n=in.nextLong();
            k=in.nextLong();
            le=(long) (Math.log(n*(k-1)+1)/Math.log(k));
            js=(long) ( ((long)Math.pow(k,le)-1) / (k-1) );
            if (js==n){
                if (i!=zhixincishu-1)
                    out.println((long)Math.pow(k,le-1));
                else
                    out.print((long)Math.pow(k,le-1));
            }
            else{
                ejs=n-js;
                if (i!=zhixincishu-1)
                    out.println(ejs+((long)Math.pow(k,le-1)- qvzhengup(ejs,k) ));
                else
                    out.print(ejs+((long)Math.pow(k,le-1)- qvzhengup(ejs,k) ));
            }
        }
        out.close();
    }


    public static long qvzhengup(long a,long b){
       if (a%b==0)
           return a/b;
       else
           return a/b+1;
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
