import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class AnotherString {

    static BigInteger[] Lxa=new BigInteger[63];
    static BigInteger[] Rxa=new BigInteger[63];
    static BigInteger[] Nxa=new BigInteger[63];
    public static void main(String[] args) {
        InputReader in=new InputReader(System.in);
        PrintWriter out=new PrintWriter(System.out);
        int zhixincishu=in.nextInt();
        BigInteger len=null;
        Lxa[1]=new BigInteger("1");
        Rxa[1]=new BigInteger("1");
        Nxa[1]=new BigInteger("0");
        for (int i=2;i<Lxa.length;i++){
            Lxa[i]=Lxa[i-1].add(Nxa[i-1]);
            Rxa[i]=Rxa[i-1].add(Lxa[i-1]);
            Nxa[i]=Nxa[i-1].add(Rxa[i-1]);
        }
        BigInteger ansL=null;
        BigInteger ansR=null;
        BigInteger ansN=null;
        for (int i=0;i<zhixincishu;i++){
            len=in.nextBigInteger();
            ansL=L(len);
            ansR=R(len);
            out.print(ansL+" "+ansR+" "+len.subtract(ansL).subtract(ansR));
            out.println();
        }
        out.close();
    }

    public static boolean checkp2(BigInteger num){
        if (num.compareTo(new BigInteger("0"))<0)
            return false;
        return (num.and(num.subtract(new BigInteger("1")))).compareTo(new BigInteger("0"))==0;
    }

    public static int getLog2(BigInteger num){
        int count=0;
        BigInteger two=new BigInteger("2");
        BigInteger one=new BigInteger("1");
        while(num.compareTo(one)>0){
            count++;
            num=num.divide(two);
        }
        return count;
    }

    public static BigInteger L(BigInteger num){
        if (num.compareTo(new BigInteger("1"))==0){
            return new BigInteger("1");
        }
        int log=getLog2(num);
        BigInteger tmp=Lx(log);
        if (checkp2(num)){
            return tmp;
        }
        else{
            BigInteger tuchu=num.subtract(new BigInteger("2").pow(log));
            return tmp.add(N(tuchu));
        }
    }

    public static BigInteger Lx(int n){
        return Lxa[n];
    }

    public static BigInteger R(BigInteger num){
        if (num.compareTo(new BigInteger("1"))==0){
            return new BigInteger("0");
        }
        int log=getLog2(num);
        BigInteger tmp=Rx(log);
        if (checkp2(num)){
            return tmp;
        }
        else{
            BigInteger tuchu=num.subtract(new BigInteger("2").pow(log));
            return tmp.add(L(tuchu));
        }
    }

    public static BigInteger Rx(int n){
        return Rxa[n];
    }

    public static BigInteger N(BigInteger num){
        if (num.compareTo(new BigInteger("1"))==0){
            return new BigInteger("0");
        }
        int log=getLog2(num);
        BigInteger tmp=Nx(log);
        if (checkp2(num)){
            return tmp;
        }
        else{
            BigInteger tuchu=num.subtract(new BigInteger("2").pow(log));
            return tmp.add(R(tuchu));
        }
    }

    public static BigInteger Nx(int n){
        return Nxa[n];
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
