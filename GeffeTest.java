package lab3;

import java.io.IOException;
import java.util.HashMap;

import static lab1.Entropy.readFile;

/**
 * Created by Сергей on 14.05.2016.
 */
public class GeffeTest {

    public static void main(String[] args) throws IOException {
        // hard task
        String outputSequence = readFile("src/lab3/sequence/var4.txt");

        int C1 = 81;
        int N1 = 258;
        int C2 = 83;
        int N2 = 265;

        Initializer polynomial1 = new Initializer(30, 6, 4, 1);
        Initializer polynomial2 = new Initializer(31, 3);
        Initializer polynomial3 = new Initializer(32, 7, 5, 3, 2, 1);

        LFSR lfsr1 = new LFSR(polynomial1);
        LFSR lfsr2 = new LFSR(polynomial2);
        LFSR lfsr3 = new LFSR(polynomial3);


        long t1 = System.nanoTime();
        System.out.println(lfsr1.getKeys(outputSequence.substring(0, N1), C1, N1));
        long t2 = System.nanoTime();
        System.out.println("Calculation L1 time: " + (t2-t1)/1000000000.0 + "\n");
        System.out.println(lfsr2.getKeys(outputSequence.substring(0, N2), C2, N2));
        t2 = System.nanoTime();
        System.out.println("Calculation L2 time: " + (t2-t1)/1000000000.0 + "\n");
        System.out.println(lfsr3.getKeyOfL3(outputSequence.substring(0, Math.min(N1,N2)),
                lfsr1.getCandidates(), lfsr2.getCandidates(), Math.min(N1,N2)));
        t2 = System.nanoTime();
        System.out.println("Calculation L3 time: " + (t2-t1)/1000000000.0 + "\n");

        // var4 answer
        lfsr1.setInitialState(651497879);
        lfsr2.setInitialState(1259760270);
        lfsr3.setInitialState(2229332000L);

        GeffeGenerator geffe = new GeffeGenerator(lfsr1, lfsr2, lfsr3);

        geffe.step(outputSequence.length());

        System.out.println(lfsr1.getOutputSequence());
        System.out.println(lfsr2.getOutputSequence());
        System.out.println(lfsr3.getOutputSequence());
        System.out.println(geffe.getGamma());
        System.out.println(outputSequence);
    }
}
