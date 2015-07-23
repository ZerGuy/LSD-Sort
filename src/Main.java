import java.util.Arrays;
import java.util.Random;

public class Main {
    private static final int MAX = 1000000;
    private static final int TEST_NUM = 100;

    public static void main(String[] args) {

        int[] data, data1;
        long start = 0;
        long stop = 0;

        long qTimeAvg = 0;
        long lsdTimeAvg = 0;

        for (int i = 0; i < TEST_NUM; i++) {
            data = generateRandoms();
            data1 = Arrays.copyOf(data, data.length);

            // Quick
            start = System.nanoTime();
            Arrays.sort(data);
            stop = System.nanoTime();

            qTimeAvg += stop - start;

            // LSD
            start = System.nanoTime();
            lsdSort(data1);
            stop = System.nanoTime();

            lsdTimeAvg += stop - start;

            if(!Arrays.equals(data, data1)){
                System.err.println("ERR");
                break;
            }
        }

        qTimeAvg /= TEST_NUM;
        lsdTimeAvg /= TEST_NUM;
        System.out.println("Q Elapsed AVG:   " + qTimeAvg);
        System.out.println("LSD Elapsed AVG: " + lsdTimeAvg);
        System.out.println("ratio: " + (double)qTimeAvg/lsdTimeAvg);

    }


    private static int[] generateRandoms() {
        int[] data = new int[MAX];
        Random random = new Random();

        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt(MAX);
        }
        return data;
    }


    // LSD sort
    private static void lsdSort(int[] arr) {
        int W = 4;
        int R = 256;

        int N = arr.length;
        int[] aux = new int[N];

        int[] count = new int[R + 1];
        for (int d = 0; d < W; d++) {

            for (int anArr : arr) {
                count[((anArr >> 8 * d) & 0xFF) + 1]++;
            }

            for (int r = 0; r < R; r++) {
                count[r + 1] += count[r];
            }

            for (int anArr : arr) {
                aux[count[(anArr >> 8 * d) & 0xFF]++] = anArr;
            }

            System.arraycopy(aux, 0, arr, 0, N);

            Arrays.fill(count, 0);
        }

    }

}
