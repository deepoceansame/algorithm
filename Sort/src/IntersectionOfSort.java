import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class IntersectionOfSort {
    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
        int numOfLine = in.nextInt();
        int x1 = in.nextInt();
        int x2 = in.nextInt();
        int k = 0;
        int b = 0;
        boolean daang = false;
        Line[] list = new Line[numOfLine];
        Line[] tempList = new Line[numOfLine];
        for (int i = 0; i < numOfLine; i++) {
            k = in.nextInt();
            b = in.nextInt();
            list[i] = new Line(k,b);
        }
        mergeSort(list, 0, list.length - 1, tempList);
        Line huajian=list[0];
        Line huajianxiao=list[0];
        int xindaxiao=0;
        int xindaxiao2=0;
        Line[] newList=new Line[numOfLine];
        Line[] newList2=new Line[numOfLine];
        for (int i=1;i< list.length;i++){
            Line cike=list[i];
            if (cike.leftInter== huajian.leftInter){
                huajian=cike.rightInter>huajian.rightInter?cike:huajian;
                huajianxiao=cike.rightInter<huajianxiao.rightInter?cike:huajianxiao;
                if (i== list.length-1){
                    newList[xindaxiao]=huajian;
                    newList2[xindaxiao2]=huajianxiao;
                    xindaxiao++;
                    xindaxiao2++;
                }
            }
            else{
                newList[xindaxiao]=huajian;
                newList2[xindaxiao2]=huajianxiao;
                xindaxiao++;
                xindaxiao2++;
                huajian=cike;
                huajianxiao=cike;
                if (i== list.length-1){
                    newList[xindaxiao]=cike;
                    newList2[xindaxiao2]=cike;
                    xindaxiao++;
                    xindaxiao2++;
                }
            }
        }
        for (int i=0;i<xindaxiao-1;i++){
            Line qian=newList[i];
            Line hou=newList2[i+1];
            if (qian.rightInter>hou.rightInter)
                daang=true;
            if (daang)
                break;
        }
        if (daang)
            System.out.println("YES");
        else
            System.out.println("NO");
    }
    static class Line implements Comparable<Line> {
        public int leftInter;
        public int rightInter;
        public Line(int a,int b){
            leftInter=a;
            rightInter=b;
        }
        @Override
        public int compareTo(Line o) {
           if (leftInter>o.leftInter)
               return 1;
           else if (leftInter<o.leftInter)
               return -1;
           else
               return 0;
        }
    }

    public static void mergeSort(Comparable[] list,int lo,int hi,Comparable[] temparr){
        int mi=lo+(hi-lo)/2;
        if (lo<hi){
            mergeSort(list,lo,mi,temparr);
            mergeSort(list,mi+1,hi,temparr);
            merge(list,lo,hi,temparr);
        }
    }

    public static void merge(Comparable[] list,int lo,int hi,Comparable[] temparr){
        int mi=lo+(hi-lo)/2;
        int ind1=mi;
        int ind2=hi;
        int ind3=hi;
        while(ind3>=lo){
            if (ind2<=mi ||(ind1>=lo && list[ind1].compareTo(list[ind2])>0)){
                temparr[ind3]=list[ind1];
                ind1--;
            }
            else {
                temparr[ind3]=list[ind2];
                ind2--;
            }
            ind3--;
        }
        for (int i=lo;i<=hi;i++){
            list[i]=temparr[i];
        }
    }

    static class Reader {
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
