import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class BitWise {
    public static void main(String[] args) {
        InputReader in=new InputReader(System.in);
        PrintWriter out=new PrintWriter(System.out);
        int operationNum=in.nextInt();
        int upperBound=in.nextInt();
        int[] oparr=new int[operationNum];
        String op="";
        int t=0;
        int maxSize=-1;
        String[] binForm=new String[operationNum];
        for (int i=0;i<operationNum;i++){
            op=in.next();
            if (op.equals("AND")){
                oparr[i]=1;
            }
            else if (op.equals("OR")){
                oparr[i]=2;
            }
            else{
                oparr[i]=3;
            }
            t=in.nextInt();
            binForm[i]=Integer.toBinaryString(t);
            maxSize=Math.max(binForm[i].length(),maxSize);
        }
        int[] resultCondition=new int[maxSize];
        int oneResult=1;
        int zeroResult=0;
        int offset=0;
        int nownum=-1;
        for (int i=maxSize-1;i>=0;i--){
            for (int j=0;j<operationNum;j++){
                offset=maxSize-i-1;
                if (offset>binForm[j].length()-1){
                    nownum=0;
                }
                else{
                    nownum=Integer.parseInt(binForm[j].charAt(binForm[j].length()-1-offset)+"");
                }
                if (oparr[j]==1){
                    oneResult=oneResult&nownum;
                    zeroResult=zeroResult&nownum;
                }
                else if (oparr[j]==2){
                    oneResult=oneResult|nownum;
                    zeroResult=zeroResult|nownum;
                }
                else{
                    oneResult=oneResult^nownum;
                    zeroResult=zeroResult^nownum;
                }
            }
            if (oneResult==0&&zeroResult==0){
                resultCondition[i]=0;
            }
            else if (oneResult==1&&zeroResult==1){
                resultCondition[i]=1;
            }
            else if (oneResult==0&&zeroResult==1){
                resultCondition[i]=2;
            }
            else{
                resultCondition[i]=3;
            }
            oneResult=1;
            zeroResult=0;
        }
        char[] result=new char[maxSize];
        String temps="";
        int tempInt=0;
        for (int i=0;i<maxSize;i++){
            if (resultCondition[i]==0){
                result[i]='0';
            }
            else if (resultCondition[i]==1){
                result[i]='0';
            }
            else if (resultCondition[i]==2){
                result[i]='0';
            }
            else {
                result[i]='1';
                temps=String.valueOf(result);
                tempInt=Integer.parseInt(temps,2);
                if (tempInt>upperBound){
                    result[i]='0';
                }
            }
        }
        char[] lasRes=new char[maxSize];
        for (int i=0;i<maxSize;i++){
            if (resultCondition[i]==0){
                lasRes[i]='0';
            }
            else if (resultCondition[i]==1){
                lasRes[i]='1';
            }
            else if (resultCondition[i]==2){
                if (result[i]=='0'){
                    lasRes[i]='1';
                }
                else{
                    lasRes[i]='0';
                }
            }
            else{
                if (result[i]=='1'){
                    lasRes[i]='1';
                }
                else{
                    lasRes[i]='0';
                }
            }
        }
        temps=String.valueOf(lasRes);
        out.println(Integer.parseInt(temps,2));
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
}
