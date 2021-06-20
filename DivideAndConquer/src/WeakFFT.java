public class WeakFFT {
    private static class FFT{
        public static double pi=Math.PI;
        public Complex[] omg;
        public Complex[] omgIn;
        public Complex[] arr;
        public int n;
        public FFT(Complex[] arr){
            n=arr.length;
            this.arr=arr;
            omg=new Complex[arr.length];
            omgIn=new Complex[arr.length];
            init();
        }

        public void init(){
            for (int i=0;i<omg.length;i++){
                omg[i]=omega(i,arr.length);
                omgIn[i]=omg[i].conj();
            }
        }

        public void transform(Complex[] a,Complex[] omg){
            int lim = 0;
            Complex temp=null;
            while((1 << lim) < n) lim++;
            for(int i = 0; i < n; i++){
                int t = 0;
                for(int j = 0; j < lim; j++)
                    if(((i >> j) & 1)==1) t |= (1 << (lim - j - 1));
                if(i < t) {
                    temp=a[i];
                    a[i]=a[t];
                    a[t]=temp;
                }// i < t 的限制使得每对点只被交换一次（否则交换两次相当于没交换）
            }

            Complex[] buf=new Complex[n];
            for(int l = 2; l <= n; l *= 2){
                int m = l / 2;
                for(int j = 0; j < n; j += l)
                    for(int i = 0; i < m; i++){
                        buf[j + i] = a[j + i].add(omg[n / l * i] .mul( a[j + i + m]) );
                        buf[j + i + m] = a[j + i].sub( omg[n / l * i].mul( a[j + i + m]));
                    }
                for(int j = 0; j < n; j++)
                    a[j] = buf[j];
            }
        }

        public static Complex omega(int k,int n){
            return new Complex(Math.cos(2*pi*k/n),Math.sin(2*pi*k/n));
        }

        public void fft(){
            transform(arr,omg);
        }

        public void ifft(){
            transform(arr,omgIn);
            Complex nn=new Complex(n,0);
        }
    }

    public static void main(String[] args) {
        Complex[] a=new Complex[8];
        for (int i=0;i<8;i++){
            a[i]=new Complex(i,0);
        }
        FFT thefft=new FFT(a);
        thefft.fft();
        for (int i=0;i<8;i++){
            a[i].print();
        }
        thefft.ifft();
        for (int i=0;i<8;i++){
            a[i].print();
        }
    }

    private static class Complex { // 复数类
        double real;  // 实部
        double image; // 虚部

        Complex(double real,double image){ // 带参数的构造方法
            this.real = real;
            this.image = image;
        }

        public double getReal() {
            return real;
        }

        public void setReal(double real) {
            this.real = real;
        }

        public double getImage() {
            return image;
        }

        public void setImage(double image) {
            this.image = image;
        }

        Complex add(Complex a){ // 复数相加
            double real2 = a.getReal();
            double image2 = a.getImage();
            double newReal = real + real2;
            double newImage = image + image2;
            Complex result = new Complex(newReal,newImage);
            return result;
        }

        Complex sub(Complex a){ // 复数相减
            double real2 = a.getReal();
            double image2 = a.getImage();
            double newReal = real - real2;
            double newImage = image - image2;
            Complex result = new Complex(newReal,newImage);
            return result;
        }

        Complex mul(Complex a){ // 复数相乘
            double real2 = a.getReal();
            double image2 = a.getImage();
            double newReal = real*real2 - image*image2;
            double newImage = image*real2 + real*image2;
            Complex result = new Complex(newReal,newImage);
            return result;
        }

        Complex div(Complex a){ // 复数相除
            double real2 = a.getReal();
            double image2 = a.getImage();
            double newReal = (real*real2 + image*image2)/(real2*real2 + image2*image2);
            double newImage = (image*real2 - real*image2)/(real2*real2 + image2*image2);
            Complex result = new Complex(newReal,newImage);
            return result;
        }

        Complex conj(){
            return new Complex(real,-image);
        }

        public void print(){ // 输出
            if(image > 0){
                System.out.println(real + " + " + image + "i");
            }else if(image < 0){
                System.out.println(real + "" + image + "i");
            }else{
                System.out.println(real);
            }
        }
    }

    public static int getUpper(int a){
        int z=1;
        while (z<a){
            z*=2;
        }
        return z;
    }

}
