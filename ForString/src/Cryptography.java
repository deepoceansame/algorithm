import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Cryptography {
    public static void main(String[] args) {
        InputReader in=new InputReader(System.in);
        PrintWriter out=new PrintWriter(System.out);
        char [] arr=new char[256];
        for (int i='a';i<='z';i++){
            arr[i]=in.next().charAt(0);
        }
        String s=in.next();
        char[] chars=s.toCharArray();
        for (int i=(s.length()-1)/2+1;i<s.length();i++){
            chars[i]=arr[chars[i]];
        }
        int[] neArr=next(chars);
        out.println(s.length()-neArr[neArr.length-1]);
        out.close();
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

    public static int[] next(char[] p) {
        int M=p.length;
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
}
