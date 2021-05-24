import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class LargeNumsSumNoDiv2 {
    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<Integer>> alist = new ArrayList<>();
        Reader in = new Reader();
        PrintWriter out = new PrintWriter(System.out);
        int zhixincishu = in.nextInt();
        int num = 0;
        int[] nums = null;
        int nownum = 0;
        int sum = 0;
        int maxsum = 0;
        boolean candivide = false;
        for (int i = 0; i < zhixincishu; i++) {
            num = in.nextInt();
            nums = new int[num];
            for (int j = 0; j < num; j++) {
                nums[j] = in.nextInt();
            }
            Arrays.sort(nums);
            for (int j = num - 1; j >= 0; j--) {
                nownum = nums[j];
                if (alist.size() == 0) {
                    alist.add(new ArrayList<Integer>());
                    alist.get(0).add(nownum);
                }
                else {
                    for (int k=0;k<alist.size();k++) {
                        ArrayList<Integer> list=alist.get(k);
                        if (list.size() == 3){
                            if (k==alist.size()-1){
                                alist.add(new ArrayList<>());
                                alist.get(alist.size() - 1).add(nownum);
                                break;
                            }
                            else
                            continue;
                        }
                        for (Integer a : list) {
                            candivide = candivide || a % nownum == 0;
                            if (candivide)
                                break;
                        }
                        if (!candivide) {
                            list.add(nownum);
                        }
                        else {
                            if (k== alist.size() - 1) {
                                alist.add(new ArrayList<>());
                                alist.get(alist.size() - 1).add(nownum);
                                break;
                            }
                            candivide = false;
                        }
                    }
                }
            }
            for (ArrayList<Integer> list : alist) {
                sum = 0;
                for (Integer a : list) {
                    sum = sum + a;
                }
                maxsum = Math.max(maxsum, sum);
            }
            if (i == zhixincishu - 1) {
                out.print(maxsum);
            } else
                out.println(maxsum);

            maxsum=0;
            alist=new ArrayList<>();
        }
        out.close();
    }

    private static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

}
