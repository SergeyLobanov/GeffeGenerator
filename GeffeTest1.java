package lab3;

import java.io.IOException;
import java.util.HashMap;

import static lab1.Entropy.readFile;

/**
 * Created by Сергей on 19.05.2016.
 */
public class GeffeTest1 {
    public static void main(String[] args) throws IOException {
        String outputSequence = readFile("src/lab3/sequence/var13+.txt");

        // work correct for easy variant for 56 minutes (a lot of L1 candidates)
        Initializer polynomial1 = new Initializer(25, 3);
        Initializer polynomial2 = new Initializer(26, 6, 2, 1);
        Initializer polynomial3 = new Initializer(27, 5, 2, 1);

        int C1 = 71;
        int N1 = 222;
        int C2 = 73;
        int N2 = 229;

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

        // var13 correct answer
        lfsr1.setInitialState(5724344);
        lfsr2.setInitialState(60592352);
        lfsr3.setInitialState(63501802);

        GeffeGenerator geffe = new GeffeGenerator(lfsr1, lfsr2, lfsr3);

        geffe.step(outputSequence.length());
        System.out.println(lfsr1.getOutputSequence());
        System.out.println(lfsr2.getOutputSequence());
        System.out.println(lfsr3.getOutputSequence());
        System.out.println(geffe.getGamma());
        System.out.println(outputSequence);
    }
}
