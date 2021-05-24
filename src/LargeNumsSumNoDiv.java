import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class LargeNumsSumNoDiv {
    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);
        int zhixincishu=in.nextInt();
        int num=0;
        int[] numss=null;
        for (int i=0;i<zhixincishu;i++){
            num=in.nextInt();
            numss=new int[num];
            for (int j=0;j<num;j++){
                numss[j]=in.nextInt();
            }
            Arrays.sort(numss);
            int[] nums=new int[num];
            int qiannum=-1;
            int op=0;
            for (int j=0;j<num;j++){
                if (numss[j]!=qiannum){
                    nums[op]=numss[j];
                    qiannum=numss[j];
                    op++;
                }
            }
            int max1=nums[op-1];
            int max2=0;
            for (int j=op-1;j>=1&&nums[j]>=max1/2;j--){
                for (int k=j-1;k>=0;k--){
                    if (nums[j]%nums[k]!=0) {
                        max2 = Math.max(max2,nums[j] + nums[k]);
                    }
                }
            }
            int max12=Math.max(max1,max2);
            int max3=0;

            for (int j=op-1;j>=2&&nums[j]>=max12/3;j--){
                for (int k=j-1;k>=1;k--){
                    for (int l=k-1;l>=0;l--){
                        if (nums[j]%nums[k]!=0 && nums[j]%nums[l]!=0 && nums[k]%nums[l]!=0){
                            max3=Math.max(nums[j]+nums[k]+nums[l],max3);
                        }
                    }
                }
            }

            int maxz=Math.max(max12,max3);
            if (i!=zhixincishu-1)
            out.println(maxz);
            else
                out.print(maxz);

        }

        out.close();
    }


}

 class Reader {
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
