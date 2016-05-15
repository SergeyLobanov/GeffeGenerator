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
        int C = 45;
        int N = 132;

        //System.out.println(outputSequence);

        Initializer polynomial1 = new Initializer(30, 6, 4, 1);
        Initializer polynomial2 = new Initializer(31, 3, 1);
        Initializer polynomial3 = new Initializer(32, 7, 5, 3, 2, 1);

        LFSR lfsr1 = new LFSR(polynomial1);
        LFSR lfsr2 = new LFSR(polynomial2);
        LFSR lfsr3 = new LFSR(polynomial3);

        long t1 = System.nanoTime();
        System.out.println(lfsr1.getKey(outputSequence, C, N));
        System.out.println(lfsr2.getKey(outputSequence, C, N));
        System.out.println(lfsr3.getKeyOfL3(outputSequence, lfsr1.getOutputSequence(), lfsr2.getOutputSequence()));
        long t2 = System.nanoTime();

        System.out.println("Calculation time: " + (t2-t1)/1000000000.0 + "\n");
        //lfsr1.setInitialState(163315675); //correct L1 initial state 1001101110111111111111011011
        //lfsr1.shift(outputSequence.length());
        //System.out.println(lfsr1.isCorrect(outputSequence));

/*
        lfsr1.shift(outputSequence.length());
        System.out.println(lfsr1.getOutputSequence());
        System.out.println(outputSequence);
        System.out.println(lfsr1.isCorrect(outputSequence));
*/
        //System.out.println(lfsr1.getKey(outputSequence));


        GeffeGenerator geffe = new GeffeGenerator(lfsr1, lfsr2, lfsr3);

        geffe.step(outputSequence.length());
        System.out.println(geffe.getGamma());
        System.out.println(outputSequence);
        /*
        System.out.println(lfsr1.getOutputSequence());
        System.out.println(lfsr2.getOutputSequence());
        System.out.println(lfsr3.getOutputSequence());
*/

    }
}
