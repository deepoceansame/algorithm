import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class CountLInString {
    public static void main(String[] args) throws IOException {
        InputReader in=new InputReader(System.in);
        PrintWriter out=new PrintWriter(System.out);
        int zhixincishu=in.nextInt();
        BigInteger a=null;
        BigInteger b=null;
        for (int i=0;i<zhixincishu;i++){
            a=in.nextBigInteger();
            b=in.nextBigInteger();
            if (a.compareTo(b)>0)
                out.println(0);
            else
            out.println(countLfrom1(b).subtract(countLfrom1(a.subtract(new BigInteger("1")))));
        }
        out.close();
        /*BigInteger bn=null;
        BigInteger an=null;
        long ans=0;
        for (int i=1;i<30000;i++){
            bn=new BigInteger(i+"");
            bn=countLfrom1(bn);
            ans=count(i);
            an=new BigInteger(ans+"");
            if (bn.compareTo(an)!=0){
                System.out.println(i);
            }
        }*/
        //for (int i=1;i<100;i++){
         //   System.out.println(countLfrom1(new BigInteger(""+i)));
        //}

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
    public static BigInteger countLfrom1(BigInteger pos){
        if (pos.compareTo(new BigInteger("1"))<=0){
            if (pos.compareTo(new BigInteger("0"))<=0){
                return new BigInteger("0");
            }
            else
                return new BigInteger("1");
        }
        int log2=log2(pos);
        BigInteger offset=new BigInteger("2").pow(log2);
        if (checkp2(pos)){
            return (new BigInteger("2").pow(log2-1).add(new BigInteger("1")));
        }
        if (offset.compareTo(pos)>=0){
            offset=new BigInteger("2").pow(log2-1);
        }
        if (checkp2m1(pos)){
            return offset;
        }
        else{
            BigInteger tuchu=pos.subtract(offset);
            BigInteger a=offset.subtract(tuchu).subtract(new BigInteger("1"));
            BigInteger b=offset.subtract(new BigInteger("1"));
            BigInteger cb=countLfrom1(b);
            BigInteger ca=countLfrom1(a);
            BigInteger coffset=countLfrom1(offset);
            BigInteger c=coffset.add(tuchu.subtract(cb.subtract(ca)));
            return c;
        }
    }

    public static long count(long pos){
        long log=log2(pos)+1;
        String s=getStringL(log);
        long count=0;
        for (int i=0;i<pos;i++){
            if (s.charAt(i)=='L')
                count++;
        }
        return count;
    }

    public static String getStringL(long n){
        if (n==1){
            return "L";
        }
        else{
            return getStringL(n-1)+"L"+getStringR(n-1);
        }
    }

    public static String getStringR(long n){
        if (n==1){
            return "R";
        }
        else{
            return getStringL(n-1)+"R"+getStringR(n-1);
        }
    }



    public static boolean checkp2(BigInteger num){
        if (num.compareTo(new BigInteger("0"))<0)
            return false;
        return (num.and(num.subtract(new BigInteger("1")))).compareTo(new BigInteger("0"))==0;
    }

    public static boolean checkp2m1(BigInteger num){
        if (num.compareTo(new BigInteger("0"))<0)
            return false;
        num=num.add(new BigInteger("1"));
        return (num.and(num.subtract(new BigInteger("1")))).compareTo(new BigInteger("0"))==0;
    }

    public static int log2(BigInteger num){
        return (int)(Math.log(num.doubleValue())/Math.log(2));
    }
    public static long log2(long num){
        return (long)(Math.log(num)/Math.log(2));
    }

}
