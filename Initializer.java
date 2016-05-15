package lab3;

/**
 * Created by Сергей on 14.05.2016.
 */
public class Initializer {
    // long representation of initialized value
    private long value;
    // value degree
    private int degree;

    // constructor with various count of arguments (degrees of polynomial) or just value
    Initializer(int ... coefficients) {
        if (coefficients.length == 1) {
            this.value = coefficients[0];
        } else {
            this.value = 1;
            for (int i : coefficients) {
                this.value ^= ((long)1 << i);
            }
        }
        setDegreeOfValue();
    }

    public int getDegree(){
        return degree;
    }

    private void setDegreeOfValue() {
        this.degree = getDegreeOfLongValue(this.value);
    }

    public static int getDegreeOfLongValue(long val) {
        return 63 - Long.numberOfLeadingZeros(val);
    }

    public long getValue() {
        return value;
    }
}
