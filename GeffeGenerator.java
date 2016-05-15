package lab3;

/**
 * Class represents pseudorandom number generator
 * Created by Сергей on 14.05.2016.
 */
public class GeffeGenerator {
    // linear feedback shift registers
    private LFSR lfsr1;
    private LFSR lfsr2;
    private LFSR lfsr3;
    // gammas
    private StringBuffer gamma;

    public GeffeGenerator(LFSR lfsr1, LFSR lfsr2, LFSR lfsr3) {
        this.lfsr1 = lfsr1;
        this.lfsr2 = lfsr2;
        this.lfsr3 = lfsr3;
        this.gamma = new StringBuffer();
    }

    public int step() {
        int x = lfsr1.shift();
        int y = lfsr2.shift();
        int s = lfsr3.shift();

        int out = (s & x) ^ ((1 ^ s) & y);
        //System.out.println("x "+x+" y "+y+" s "+s+" out "+out+"     ");
        gamma.append(out);

        return out;
    }

    public void step(int n) {
        for (int i = 0; i < n + 1; i++){
            step();
        }
    }

    public String getGamma() {
        return gamma.substring(1);
    }
}
