package lab3;

import java.util.HashMap;
import java.util.Map;

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
    // list of candidates on key
    private HashMap<Long, String> candidates;

    public LFSR(Initializer initPolynomial, long initialState) {
        this.polynomialDegree = initPolynomial.getDegree();
        this.polynomial = initPolynomial.getValue() ^ ((long)1 << polynomialDegree);
        setInitialState(initialState);
    }

    public LFSR(Initializer initPolynomial) {
        this(initPolynomial, 1);
    }

    public LFSR(Initializer initPolynomial, Initializer initialState) {
        this.polynomialDegree = initPolynomial.getDegree();
        this.polynomial = initPolynomial.getValue() ^ ((long)1 << polynomialDegree);
        setInitialState(initialState.getValue());
    }

    // get initial state that produce given gamma with output length limit N
    // and threshold value for statistic R - C
    public HashMap<Long, String> getKeys(String correct, int C, int N) {
        candidates = new HashMap<>();
        long size = ((long) 1 << polynomialDegree);
        int k = 1 << (polynomialDegree - 3); // to check state of process

        for (long i = 0; i < size; i++) {
            setInitialState(i);
            if((i % k) == 0) {
                System.out.println("s: "+ candidates.size() + " i " + i + " " + Long.toBinaryString(i));
            }
            shift(N);
            if (isCorrect(correct, C)) {
                System.out.println("key candidate "
                        + Long.toBinaryString(initialState) + " dec " + initialState);
                candidates.put(initialState, outputSequence.toString());
            }
        }

        for(Map.Entry<Long, String> mp : candidates.entrySet()) {
            System.out.println("key " + mp.getKey() + " val " + mp.getValue());
        }

        return candidates;
    }

    // get initial state of l3
    public HashMap<Long, String> getKeyOfL3(String correct, HashMap<Long, String> l1keys, HashMap<Long, String> l2keys, int N) {
        candidates = new HashMap<>();
        long size = ((long) 1 << polynomialDegree);
        String l1temp, l2temp;
        long key1 = 0, key2 = 0;

        int k = 1 << (polynomialDegree - 4); // to check state of process

        for (Map.Entry<Long, String> mp1 : l1keys.entrySet()) {
            l1temp = mp1.getValue();
            for (Map.Entry<Long, String> mp2 : l2keys.entrySet()) {
                l2temp = mp2.getValue();
                System.out.println(l1temp);
                System.out.println(l2temp);
                for (long i = 0; i < size; i++) {
                    setInitialState(i);
                    if((i % k) == 0) {
                        System.out.println("s: "+ candidates.size() + " i " + i + " " + Long.toBinaryString(i));
                    }
                    if (isCorrectL3(correct, l1temp, l2temp)) {
                        System.out.println("L3 key candidate "
                                + Long.toBinaryString(initialState) + " dec " + initialState);
                        candidates.put(initialState, outputSequence.toString());
                        key1 = mp1.getKey();
                        key2 = mp2.getKey();
                    }
                }
            }
        }
        System.out.println("Key L1 " + Long.toBinaryString(key1) + " dec " + key1);
        System.out.println("Key L2 " + Long.toBinaryString(key2) + " dec " + key2);
        for(Map.Entry<Long, String> mp : candidates.entrySet()) {
            System.out.println("Key L3 " + Long.toBinaryString(mp.getKey()) + " dec " + mp.getKey());
        }

        return candidates;
    }

    // check by special condition R < C
    public boolean isCorrect(String correct, int C) {
        String temp = outputSequence.toString();
        int statisticR = 0;

        for (int i = 0; i < outputSequence.length(); i++) {
            if (correct.charAt(i) != temp.charAt(i)) {
                statisticR++;
            }
        }
        //System.out.println(statisticR);
        return (statisticR < C);
    }

    // check l3 candidate by known l1, l2 and generators output sequence
    public boolean isCorrectL3(String correct, String l1sequence, String l2sequence) {
        for (int i = 0; i < correct.length(); i++) {
            int sh = shift();
            if (l1sequence.charAt(i) != l2sequence.charAt(i)) {
                if ((sh == 1) && (correct.charAt(i) != l1sequence.charAt(i))) {
                    return false;
                }
                if ((sh == 0) && (correct.charAt(i) != l2sequence.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    // simple shift of register
    public int shift() {
        int out = (int) (currentState & 1);
        int tempBit = (Long.bitCount((currentState & polynomial))) & 1;

        currentState >>= 1;
        currentState ^= ((long)tempBit << (polynomialDegree - 1));

        outputSequence.append(out);

        return out;
    }

    // n - shifts of register
    public void shift(int n) {
        for (int i = 0; i < n; i++) {
            shift();
        }
    }

    public HashMap<Long, String> getCandidates() {
        return candidates;
    }

    public void setInitialState(long initialState) {
        this.initialState = initialState;
        this.currentState = this.initialState;
        refreshOutputSequence();
    }

    public void setCandidates(HashMap<Long, String> candidates) {
        this.candidates = candidates;
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
        return outputSequence.toString();
    }
}
