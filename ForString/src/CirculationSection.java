import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class CirculationSection {

    public static void main(String[] args) {
        InputReader in=new InputReader(System.in);
        PrintWriter out=new PrintWriter(System.out);
        int shulian=in.nextInt();
        String s="";
        int[] a=null;
        int len=0;
        int k=0;
        int mo=0;
        for (int i=0;i<shulian;i++){
            s=in.next();
            a=next(s);
            len=s.length();
            k=a[len-1];
            mo=len%(len-k);
            if (mo==0 && k!=0)
                out.println(0);
            else
                out.println((len/(len-k)+1)*(len-k)-len);
        }
        out.close();
    }


    public static int[] next(String s) {
        int M=s.length();
        char[] p=s.toCharArray();
        int[] a = new int[M];
        int k =-1;
        a[0]=0;
        for (int i=1; i<M; i++) {
            while (k >-1 && p[k+1] != p[i]) {
                k=a[k]-1;
            }
            if (p[k+1]==p[i])
                k++;
            a[i]=k+1;
        }
        return a;
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
