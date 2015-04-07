package interpolation;

public class Gauss {

    private double a = 0;
    private double b = 0;
    private double g = 0;
    private double e = 0;
    private double delta = 0;
    private int n = 0;

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setG(double g) {
        this.g = g;
    }

    public void setE(double e) {
        this.e = e;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

   public void setN(int n) {
        this.n = n;
    }

    public double fx(double x) {
        return (a*Math.cos(e*x)*Math.sin(Math.tan(b/(x-g))));
    }

    public double dfx(double x) {
        return ((1/delta)*(fx(x+delta)-fx(x)));
    }

    private double ax[];
    private double ay[];
    private int size;
    private int start;
    private int end;

    public void storeFx(double step) {
        size = 0;
        int i;
        double j;
        for (j = -100; j < 201; j += step) {
            size++;
        }
        ax = new double[size];
        ay = new double[size];
        for (i = 0, j = -100; i < size; i++, j += step) {
            ax[i] = j;
            ay[i] = fx(j);
        }
    }

    private int order = 4;

    public void setPnx(double step, double begin, double end) {
        storeFx(step);
        int rx = 0;
        this.start = 0;
        storeFx(step);
        do {
            rx++;
            this.start++;
        } while (ax[rx] < begin);
        this.end = start;
        for (double g = begin; g < end; g += step, this.end++);
    }

    public double pnx(double x) {
        double diff[][] = new double[size+1][order+1];
        double p,h,y1,y2,y3,y4;
        int i,j;

        h=ax[start+1]-ax[start];

        for(i = 0; i < end; i++)
            diff[i][1] = ay[i+1] - ay[i];

        for(j = 2; j <= order; j++)
            for(i = 0; i <= end - j; i++)
                diff[i][j] = diff[i+1][j-1] - diff[i][j-1];

        i = 0;
        do {
            i++;
        } while(ax[i] < x);
        i--;
        p=(x-ax[i])/h;
        y1=p*diff[i][1];
        y2=p*(p-1)*diff[i-1][2]/2;
        y3=p*(p-1)*(p+1)*diff[i-2][3]/6;
        y4=p*(p-1)*(p+1)*(p-2)*diff[i-3][4]/24;
        return (ay[i]+y1+y2+y3+y4);
    }

//    public double pnxb(double x) {
//        double ax[] = new double[1000001*n];
//        double ay[] = new double[1000001*n];
//        double diff[][] = new double[1000001*n][order+1];
//        double p, h, y1, y2, y3, y4;
//        int i,j;
//
//        for (i = 0; i <= n; i++) {
//            ax[i] = i;
//            ay[i] = fx(i);
//        }
//
//        h = ax[1] - ax[0];
//        for (i = 0; i <= n - 1; i++)
//            diff[i][1] = ay[i+1] - ay[i];
//        for (j = 2; j <= order; j++)
//            for (i = 0; i <= n - j; i++)
//                diff[i][j] = diff[i+1][j-1] - diff[i][j-1];
//        i = 0;
//        do {
//            i++;
//        } while (ax[i] < x);
//
//        i--;
//        p = (x-ax[i])/h;
//        y1 = p*diff[i][1];
//        y2 = p*(p-1)*diff[i-1][2]/2;
//        y3 = p*(p-1)*(p+1)*diff[i-2][3]/6;
//        y4 = p*(p-1)*(p+1)*(p-2)*diff[i-3][4]/24;
//        return (ay[i]+y1+y2+y3+y4);
//    }

    public double rnx(double x) {
        return Math.abs(pnx(x) - fx(x-0.001));
    }

    public double dpnx(double x) {
        return ((1/delta)*(pnx(x+delta)-pnx(x)));
    }
}
