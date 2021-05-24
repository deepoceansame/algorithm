import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class UpperThenLowAlphabet {

    public static void main(String[] args) throws IOException {
        InputReader in=new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int zhixincishu = in.nextInt();
        int[] arr=null;
        char[] deterChars=null;
        for (int j = 0; j < zhixincishu; j++) {
            String s = in.next();
            Bao b = getArr(s);
            arr = b.arr;
            ArrayList<Character> deterChar = new ArrayList<>();
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] != 0) {
                    deterChar.add((char) i);
                }
            }
            deterChars = new char[deterChar.size()];
            for (int i = 0; i < deterChar.size(); i++) {
                deterChars[i] = deterChar.get(i);
            }
            if (deterChar.size()==0){
                out.println(0);
                continue;
            }
            int answer = getBeauty("", 0, b.tarr, b.arr, deterChars, 0);
            out.println(answer);
        }
        out.close();
    }

    public static Bao getArr(String s) {
        int[][] tarr = new int[256][256];
        int[] arr = new int[256];
        ArrayList<Character> vowels = new ArrayList<>(6);
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
        for (int i = 0; i < s.length() - 1; i++) {
            char front = s.charAt(i);
            char rear = s.charAt(i + 1);
            if (!vowels.contains(front) && !vowels.contains(rear)) {
                tarr[front][rear]++;
                tarr[rear][front]++;
                arr[front]++;
                arr[rear]++;
            }
        }
        return new Bao(tarr, arr);
    }

    public static int getBeauty(String charsHaveUpper, int existedBeauty, int[][] tarr, int[] arr, char[] deterChar, int cen) {
        int existBeautyForUpperThisCen = existedBeauty;
        int beautyForNotUpper = 0;
        int beautyForUpper = 0;
        char nowdeterChar = deterChar[cen];

        String charsHaveUpperTemp = charsHaveUpper + deterChar[cen];
        existBeautyForUpperThisCen = existedBeauty + arr[nowdeterChar];
        for (int i = 0; i < charsHaveUpperTemp.length(); i++) {
            if (charsHaveUpperTemp.charAt(i)==nowdeterChar){
                existBeautyForUpperThisCen = existBeautyForUpperThisCen - tarr[nowdeterChar][charsHaveUpperTemp.charAt(i)];
            }
            else{
            existBeautyForUpperThisCen = existBeautyForUpperThisCen - 2 * tarr[nowdeterChar][charsHaveUpperTemp.charAt(i)];
            }
        }
        if (existBeautyForUpperThisCen < 0) {
            existBeautyForUpperThisCen = 0;
        }

        if (cen == deterChar.length - 1) {
            return Math.max(existedBeauty, existBeautyForUpperThisCen);
        }
        else {
            beautyForNotUpper = getBeauty(charsHaveUpper, existedBeauty, tarr, arr, deterChar, cen + 1);
            beautyForUpper = getBeauty(charsHaveUpperTemp, existBeautyForUpperThisCen, tarr, arr, deterChar, cen +  1);
            return Math.max(beautyForNotUpper, beautyForUpper);
        }
    }

    static class Bao {
        int[][] tarr;
        int[] arr;

        public Bao(int[][] tarr, int[] arr) {
            this.tarr = tarr;
            this.arr = arr;
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
