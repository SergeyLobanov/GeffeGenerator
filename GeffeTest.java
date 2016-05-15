package lab3;

import java.io.IOException;

import static lab1.Entropy.readFile;

/**
 * Created by Сергей on 14.05.2016.
 */
public class GeffeTest {

    public static void main(String[] args) throws IOException {
        //Initializer init = new Initializer(4, 3, 2, 1);
/*
        Initializer init = new Initializer(3, 1);
        LFSR lfsr = new LFSR(init);
        lfsr.setInitialState(6);

        for (int i = 0; i < 20; i++) {
            System.out.println("curr " + Long.toBinaryString(lfsr.getCurrentState()) + " sh " + lfsr.shift());
        }
        System.out.println(lfsr.getOutputSequence());
*/
        // given output sequence
        String outputSequence = readFile("src/lab3/sequence/var13.txt");
        //String outputSequence = readFile("src/lab3/sequence/var4.txt");

        //System.out.println(outputSequence);

        Initializer polynomial1 = new Initializer(30, 6, 4, 1);
        Initializer polynomial2 = new Initializer(31, 3, 1);
        Initializer polynomial3 = new Initializer(32, 7, 5, 3, 2, 1);

        LFSR lfsr1 = new LFSR(polynomial1);
        LFSR lfsr2 = new LFSR(polynomial2);
        LFSR lfsr3 = new LFSR(polynomial3);

        long t1 = System.nanoTime();
        System.out.println(lfsr2.getKey(outputSequence));
        long t2 = System.nanoTime();
        System.out.println("Calculation time: " + (t2-t1)/1000000000.0 + "\n");
        //lfsr1.setInitialState(163315675);
        //lfsr1.shift(outputSequence.length()+1);
        //System.out.println(lfsr1.isCorrect(outputSequence));
        System.out.println();

/*
        lfsr1.shift(outputSequence.length()+1);
        System.out.println(lfsr1.getOutputSequence());
        System.out.println(outputSequence);
        System.out.println(lfsr1.isCorrect(outputSequence));
*/
        //System.out.println(lfsr1.getKey(outputSequence));


        GeffeGenerator geffe = new GeffeGenerator(lfsr1, lfsr2, lfsr3);
        /*
        geffe.step(outputSequence.length()+1);
        System.out.println(lfsr1.getOutputSequence());
        System.out.println(lfsr2.getOutputSequence());
        System.out.println(lfsr3.getOutputSequence());
        System.out.println(geffe.getGamma());
        System.out.println(outputSequence);
*/

        /*
        Initializer initialState1 = new Initializer(7657);
        Initializer characteristic1 = new Initializer(30, 6, 4, 1);
        LFSR lfsr = new LFSR(characteristic1, initialState1);
        System.out.println(lfsr.getPolynomial());
        System.out.println(Long.toBinaryString(lfsr.getPolynomial()));
        System.out.println(lfsr.getOutputSequence());
        System.out.println(Long.toBinaryString(7657));
        System.out.println(63 - Long.numberOfLeadingZeros(7657));
        System.out.println(initialState1.getDegree());
        System.out.println(characteristic1.getDegree());
        */
    }
}
