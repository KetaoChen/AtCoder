import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;


public class ABC162_E_Factors_inclusive_exclusive implements Runnable
{
    final static int mod = (int) (1e9 + 7);
    @Override
    public void run() {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int n = in.nextInt();
        int k = in.nextInt();

        getRes(w, n, k);
        w.flush();
        w.close();
    }

    private static void getRes(PrintWriter w, int n, int k) {
        // 1. find the factor of each number from 1 to k.
        List<Integer>[] multiples = new List[k + 1];
        for (int i = 0; i <= k; i++) {
            multiples[i] = new ArrayList<>();
        }
        for (int i = 1; i <= k; i++) {
            for (int f = 1; f * i <= k; f++) {
                multiples[i].add(f * i);
            }
        }

        int mod = (int) 1e9 + 7;
        long[] dp = new long[k + 1];
        // 2. starting from the larger number num, and find out the gcd of the seq is num.
        // 3. dp[i] = (number of factor)^n, for each position, we can put any one. - sum(dp[multiply]).
        // e.g. 8 have factors, 2, 4, 8.
        for (int num = k; num >= 1; num--) {
            dp[num] = fastExp(multiples[num].size(), n);
            for (int m : multiples[num]) {
                if (m > num) {
                    dp[num] = (dp[num] - dp[m]) % mod;
                }
            }
        }

//        for (int i = 0; i <= k; i++) {
//            System.out.print(dp[i] + " ");
//        }
//        System.out.println();
        // 4. time complexity: calculate the factor = nlogn, dp = nlogn. tot= nlogn
        long res = 0;
        for (int i = 1; i <= k; i++) {
            res = (res + i * dp[i]) % mod;
        }
        w.println(res);
    }



    private static long fastExp(long i, int n) {
        if (n == 1) return i;
        if (n % 2 == 0) return fastExp(i * i % mod, n / 2) % mod;
        return i * fastExp(i * i % mod, n / 2) % mod;
    }

    static class InputReader
    {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;
        private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        public InputReader(InputStream stream)
        {
            this.stream = stream;
        }

        public int read()
        {
            if (numChars==-1)
                throw new InputMismatchException();

            if (curChar >= numChars)
            {
                curChar = 0;
                try
                {
                    numChars = stream.read(buf);
                }
                catch (IOException e)
                {
                    throw new InputMismatchException();
                }

                if(numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public String nextLine()
        {
            String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
        }
        public int nextInt()
        {
            int c = read();

            while(isSpaceChar(c))
                c = read();

            int sgn = 1;

            if (c == '-')
            {
                sgn = -1;
                c = read();
            }

            int res = 0;
            do
            {
                if(c<'0'||c>'9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            }
            while (!isSpaceChar(c));

            return res * sgn;
        }

        public long nextLong()
        {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-')
            {
                sgn = -1;
                c = read();
            }
            long res = 0;

            do
            {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            }
            while (!isSpaceChar(c));
            return res * sgn;
        }

        public double nextDouble()
        {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-')
            {
                sgn = -1;
                c = read();
            }
            double res = 0;
            while (!isSpaceChar(c) && c != '.')
            {
                if (c == 'e' || c == 'E')
                    return res * Math.pow(10, nextInt());
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            }
            if (c == '.')
            {
                c = read();
                double m = 1;
                while (!isSpaceChar(c))
                {
                    if (c == 'e' || c == 'E')
                        return res * Math.pow(10, nextInt());
                    if (c < '0' || c > '9')
                        throw new InputMismatchException();
                    m /= 10;
                    res += (c - '0') * m;
                    c = read();
                }
            }
            return res * sgn;
        }

        public String readString()
        {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            StringBuilder res = new StringBuilder();
            do
            {
                res.appendCodePoint(c);
                c = read();
            }
            while (!isSpaceChar(c));

            return res.toString();
        }

        public boolean isSpaceChar(int c)
        {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public String next()
        {
            return readString();
        }

        public interface SpaceCharFilter
        {
            public boolean isSpaceChar(int ch);
        }
    }

    public static void main(String args[]) throws Exception
    {
        new Thread(null, new ABC162_E_Factors_inclusive_exclusive(),"Main",1<<27).start();
    }

}
