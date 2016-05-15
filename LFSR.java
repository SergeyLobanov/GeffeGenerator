package lab3;

/**
 * Class represents linear feedback shift register
 * Created by Сергей on 14.05.2016.
 */
public class LFSR {
    // characteristic polynomial of lfsr
    private long polynomial;
    // characteristic polynomial
    private int polynomialDegree;
    // initial state of lfsr
    private long initialState;
    // initial state of lfsr
    private long currentState;
    // generated output sequence
    private StringBuffer outputSequence;

    public LFSR(Initializer initPolynomial, Initializer initialState) {
        this.polynomialDegree = initPolynomial.getDegree();
        this.polynomial = initPolynomial.getValue() ^ ((long)1 << polynomialDegree);
        setInitialState(initialState.getValue());
    }

    public LFSR(Initializer initPolynomial, long initialState) {
        this.polynomialDegree = initPolynomial.getDegree();
        this.polynomial = initPolynomial.getValue() ^ ((long)1 << polynomialDegree);
        setInitialState(initialState);
    }

    public LFSR(Initializer initPolynomial) {
        this(initPolynomial, 1);
    }

    // get initial state that produce given gamma with output length limit N
    // and threshold value for statistic R - C
    public long getKey(String correct, int C, int N) {
        long size = ((long) 1 << polynomialDegree);
        correct = correct.substring(0, N); // override output string

        for (long i = 0; i < size; i++) {
            setInitialState(i);
            shift(N);
            if (isCorrect(correct, C)) {
                break;
            }
        }
        System.out.println("Binary initial state is: " + getBinaryInitialState());
        System.out.println("Decimal initial state is: " + getInitialState());
        System.out.println("end");

        return initialState;
    }

    // get initial state of l3
    public long getKeyOfL3(String correct, String l1sequence, String l2sequence) {
        long size = ((long) 1 << polynomialDegree);
        String temp;

        for (long i = 0; i < size; i++) {
            setInitialState(i);
            shift(l1sequence.length());
            temp = getOutputSequence();
            if (isCorrectL3(temp, correct, l1sequence, l2sequence)) {
                break;
            }
        }
        System.out.println("Binary initial state is: " + getBinaryInitialState());
        System.out.println("Decimal initial state is: " + getInitialState());
        System.out.println("end");

        return initialState;
    }

    // check l3 candidate by known l1, l2 and generators output sequence
    public boolean isCorrectL3(String temp, String correct, String l1sequence, String l2sequence) {
        for (int i = 0; i < temp.length(); i++) {
            if (((temp.charAt(i) == '0') && (l1sequence.charAt(i) != correct.charAt(i)))
                    || ((temp.charAt(i) == '1') && (l2sequence.charAt(i) != correct.charAt(i)))) {
                return false;
            }
        }

        return true;
    }

    // check by special condition R < C
    public boolean isCorrect(String correct, int C) {
        String temp = getOutputSequence();
        int lengthN = correct.length();

        int statisticR = countStatisticR(correct, temp, lengthN);
        //System.out.println(statisticR);
        return (statisticR < C);
    }

    public int countStatisticR(String correct, String temp, int lengthN) {
        int statisticR = 0;

        for (int i = 0; i < lengthN; i++) {
            if (correct.charAt(i) != temp.charAt(i)) {
                statisticR++;
            }
        }
        return statisticR;
    }

    // simple shift of register
    public int shift() {
        int out = (int) (currentState & 1);

        int tempBit = (Long.bitCount(currentState & polynomial)) & 1;
        currentState >>= 1;
        currentState ^= ((long)tempBit << (polynomialDegree - 1));

        outputSequence.append(out);

        return out;
    }

    // n - shifts of register
    public void shift(int n) {
        for (int i = 0; i < n + 1; i++) { // n + 1 because first shift is not considered
            shift();
        }
    }

    // get initial state that produce given gamma in general
    public long getKeyHard(String correct) {
        long size = ((long) 1 << polynomialDegree);

        for (long i = 0; i < size; i++) {
            setInitialState(i);
            shift(correct.length());
            if (isCorrect(correct)) {
                break;
            }
        }
        System.out.println("Binary initial state is: " + getBinaryInitialState());
        System.out.println("Decimal initial state is: " + getInitialState());
        System.out.println("end");

        return initialState;
    }

    // check by general condition (p = 1/4)
    public boolean isCorrect(String correct) {
        String temp = getOutputSequence();
        int lengthN = correct.length();

        int statisticR = countStatisticR(correct, temp, lengthN);
        //System.out.println((double) statisticR / lengthN);
        return ((double) statisticR / lengthN) - (1. / 4) < 0.01;
    }

    public void setInitialState(long initialState) {
        this.initialState = initialState;
        this.currentState = this.initialState;
        refreshOutputSequence();
    }

    private void refreshOutputSequence() {
        this.outputSequence = new StringBuffer();
    }

    public long getCurrentState() {
        return currentState;
    }

    public String getBinaryCurrentState() {
        return Long.toBinaryString(currentState);
    }

    public long getPolynomial() {
        return polynomial;
    }

    public int getPolynomialDegree() {
        return polynomialDegree;
    }

    public long getInitialState() {
        return initialState;
    }

    public String getBinaryInitialState() {
        return Long.toBinaryString(initialState);
    }

    public String getOutputSequence() {
        return outputSequence.substring(1);
    }
}
