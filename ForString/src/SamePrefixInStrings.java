import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class SamePrefixInStrings {
    public static void main(String[] args) {
        InputReader in=new InputReader(System.in);
        PrintWriter out=new PrintWriter(System.out);
        int n=in.nextInt();
        char[][] biao=new char[n][];
        int len=0;
        for (int i=0;i<n;i++){
            biao[i]=in.next().toCharArray();
            len=Math.max(len,biao[i].length);
        }
        int pre=0;
        int hou=0;

        boolean fid=false;
        while(true){
            char c=biao[0][pre];
            for (int i=1;i<n;i++){
                if (biao[i][pre]!=c){
                    fid=true;
                }
                if (fid)
                    break;
            }
            if (!fid){
                pre++;
            }
            else{
                break;
            }
            if (pre==len){
                break;
            }
        }

        fid=false;
        while(true){
            char c=biao[0][biao[0].length-1-hou];
            for (int i=1;i<n;i++){
                if (biao[i][biao[i].length-1-hou]!=c){
                    fid=true;
                }
                if (fid)
                    break;
            }
            if (!fid){
                hou++;
            }
            else{
                break;
            }
            if (hou==len){
                break;
            }
        }

        out.println(pre*hou);
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
}
