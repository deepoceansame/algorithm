import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class SameFixInTwoStrings {

    public static void main(String[] args) {
        InputReader in=new InputReader(System.in);
        PrintWriter out=new PrintWriter(System.out);
        int n=in.nextInt();
        String[] s=new String[n];
        for (int i=0;i<n;i++){
            s[i]=in.next();
        }

        int q=in.nextInt();
        int answer=0;
        int i=0;
        int j=0;
        char ans=' ';
        char exans=' ';
        int a=0;
        int b=0;
        int[] temparr=null;
        for (int k=0;k<q;k++){
            i=in.nextInt();
            j=in.nextInt();
            ans=in.next().charAt(0);
            temparr=next(s[i-1]+"@"+s[j-1]);
            a=temparr[temparr.length-1];
            temparr=next(s[j-1]+"@"+s[i-1]);
            b=temparr[temparr.length-1];
            if (a==b)
                exans='=';
            else if (a>b)
                exans='>';
            else
                exans='<';
            if (exans==ans)
                answer++;
        }
        out.println(answer);
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
