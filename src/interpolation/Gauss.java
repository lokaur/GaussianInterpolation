package interpolation;

public class Gauss {

    private double a = 0;
    private double b = 0;
    private double g = 0;
    private double e = 0;
    private double delta = 0;
    private int n = 0;
    private double ax[];
    private double ay[];
    private int size;
    private int start;
    private int end;
    private int intSize;

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

    private void storeFx(double step) {
        size = 0;
        double j;

        for (j = -100; j <= 100; j += step) {
            size++;
        }

        ax = new double[size];
        ay = new double[size];

        j = -100;
        for (int i = 0; i < size; i++) {
            ax[i] = j;
            ay[i] = fx(j);
            j += step;
        }
    }

    public void setPnx(double step, double begin, double end) {
        storeFx(step);
        int rx = 0;
        this.start = 0;
        do {
            rx++;
            this.start++;
        } while (ax[rx] < begin);

        this.end = this.start;

        do {
            rx++;
            this.end++;
        } while (ax[rx] < end);

        double intStep = Math.abs(end - begin) / n;
        int count = 0;
        double test = begin;
        do {
            count++;
            test += intStep;
        } while (test < end);
        intSize = (this.end - this.start) / count;
    }

    public double pnx(double x) {
        int order = 4;
        int size = end;
        double diff[][] = new double[size + 1][order + 1];
        double p, h, y1, y2, y3, y4;
        int i, j;
        h = ax[start + 1] - ax[start];

        j = 0;
        do {
            j++;
        } while (ax[j] < x);

        i = 0;
        for (int cur = j; cur < size - 1; cur += intSize) {
            diff[i][1] = ay[cur + intSize] - ay[cur];
            i++;
        }

        for (j = 2; j <= order; j++)
            for (i = 0; i <= size - j; i++)
                diff[i][j] = diff[i + 1][j - 1] - diff[i][j - 1];
        i = 0;
        do {
            i++;
        } while (ax[i] < x);
        i--;
        p = (x - ax[i]) / h;
        y1 = p * diff[i][1];
        y2 = p * (p - 1) * diff[i - 1][2] / 2;
        y3 = p * (p - 1) * (p + 1) * diff[i - 2][3] / 6;
        y4 = p * (p - 1) * (p + 1) * (p - 2) * diff[i - 3][4] / 24;
        return (ay[i] + y1 + y2 + y3 + y4);
    }

    public double rnx(double x) {
        return Math.abs(fx(x) - pnx(x));
    }

    public double dpnx(double x) {
        return ((1/delta)*(pnx(x+delta)-pnx(x)));
    }
}
