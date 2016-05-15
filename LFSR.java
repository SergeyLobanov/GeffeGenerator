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
        setInitialState(initialState);
    }

    public LFSR(Initializer initPolynomial) {
        this(initPolynomial, new Initializer(1));
    }

    public long getKey(String correct) {
        long size = ((long) 1 << polynomialDegree);

        for (long i = 0; i < size; i++) {
            setInitialState(i);
            //System.out.println(getBinaryInitialState());
            shift(correct.length() + 1); // because first shift is not considered
            if (isCorrect(correct)) {
                System.out.println(getBinaryInitialState());
                break;
            }
        }
        System.out.println(Long.toBinaryString(currentState));
        System.out.println("end");

        return currentState;
    }

    public boolean isCorrect(String correct) {
        String temp = getOutputSequence();
        int lengthN = correct.length();
        int statisticR = 0;

        for (int i = 0; i < lengthN; i++) {
            if (correct.charAt(i) != temp.charAt(i)) {
                statisticR++;
            }
        }

        //System.out.println((double) statisticR / lengthN);

        return ((double) statisticR / lengthN) - (1. / 4) < 0.01;
    }

    public int shift() {
        int out = (int) (currentState & 1);

        int tempBit = (Long.bitCount(currentState & polynomial)) & 1;
        currentState >>= 1;
        currentState ^= ((long)tempBit << (polynomialDegree - 1));

        outputSequence.append(out);

        return out;
    }

    public void shift(int n) {
        for (int i = 0; i < n; i++) {
            shift();
        }
    }

    public void setInitialState(Initializer initialState) {
        this.initialState = initialState.getValue();
        this.currentState = this.initialState;
        setOutputSequence();
    }

    public void setInitialState(long initialState) {
        this.initialState = initialState;
        this.currentState = this.initialState;
        setOutputSequence();
    }

    private void setOutputSequence() {
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
        //return outputSequence.toString();
        return outputSequence.substring(1);
    }
}
