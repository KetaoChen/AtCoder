import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class abc151_E_Max_Min_Sums {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int n = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        int m = in.nextInt();
        in.nextLine();

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }
        System.out.println(getMax(arr, m));
    }

    private static int getMax(int[] arr, int m) {
        Arrays.sort(arr);
        long sum = 0;
        int mod = 1000000007;
        int l = arr.length;
        long base = 1;
        long[] inv = invArray(l - m + 1, mod);
        for (int i = 0; i <= l - m; i++) {
            //System.out.println(arr[m + i - 1] + " " + arr[l - m - i] + " " + base);
            sum = (sum + base * arr[m + i - 1] - base * arr[l - m - i]) % mod;
            base = (base * (m + i) % mod) * (inv[i + 1] % mod) % mod;
        }

        return (int) ((sum + mod) % mod);
    }

    public  static long[] invArray(int n, int mod){
        long[] inv = new long[n + 1];
        inv[1] = 1;
        for (int i = 2; i <= n; i++) {
            inv[i] = (mod - mod / i) * inv[mod % i] % mod;
        }

        return inv;
    }

    private static long[] getInverseMod(int i, int mod) {
        if (mod == 0) {
            return new long[]{1, 0};
        }
        long[] temp = getInverseMod(mod, i  % mod);
        return new long[]{temp[1], temp[0] - i / mod * temp[1]};
    }
}
