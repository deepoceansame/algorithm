import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class NormalStableMatch {
    public static void main(String[] args) {
        InputReader in=new InputReader(System.in);
        PrintWriter out=new PrintWriter(System.out);
        int num=in.nextInt();
        Man[] mans=new Man[num];
        Woman[] womans=new Woman[num];
        Stack<Man> single=new Stack<>();
        TreeMap<String,Integer> manMap=new TreeMap<>();
        TreeMap<String,Integer> womanMap=new TreeMap<>();
        for (int i=0;i<num;i++){
            mans[i]=new Man(in.next());
            mans[i].pref=new int[num];
            manMap.put(mans[i].name,i);
            mans[i].id=i;
            single.push(mans[i]);
        }
        for (int i=0;i<num;i++){
            womans[i]=new Woman(in.next());
            womans[i].manRank=new int[num];
            womanMap.put(womans[i].name,i);
        }
        for (int i=0;i<num;i++){
            for (int j=0;j<num;j++){
                mans[i].pref[j]=womanMap.get(in.next());
            }
        }
        for (int i=0;i<num;i++){
            for (int j=0;j<num;j++){
                womans[i].manRank[manMap.get(in.next())]=j;
            }
        }
        Man manPro=null;
        Woman womanBep;
        int nowManNum=0;
        int proManNum=0;
        while(!single.isEmpty()){
            manPro=single.pop();
            womanBep=womans[manPro.pref[manPro.ind]];
            manPro.ind++;
            if (womanBep.nowMan==null){
                womanBep.nowMan=manPro;
                manPro.nowWoman=womanBep;
            }
            else{
                nowManNum=womanBep.manRank[womanBep.nowMan.id];
                proManNum=womanBep.manRank[manPro.id];
                if (proManNum<nowManNum){
                    womanBep.nowMan.nowWoman=null;
                    single.push(womanBep.nowMan);
                    womanBep.nowMan=manPro;
                    manPro.nowWoman=womanBep;
                }
                else{
                    single.push(manPro);
                }
            }
        }

        for (int i=0;i<num;i++){
            out.println(mans[i].name+" "+mans[i].nowWoman.name);
        }

        out.close();
    }

    private static class Man{
        public String name;
        int id;
        public int[] pref;
        public int ind=0;
        public Woman nowWoman;
        public Man(String s){
            name=s;
        }
    }

    private static class Woman{
        String name;
        public int[] manRank;
        Man nowMan;
        public Woman(String s){
            name=s;
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
