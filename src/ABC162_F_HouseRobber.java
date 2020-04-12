import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;


public class ABC162_F_HouseRobber implements Runnable
{
    final static int mod = (int) (1e9 + 7);
    @Override
    public void run() {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int n = in.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }

        getRes(w, arr, n);
        w.flush();
        w.close();
    }

    private static void getRes(PrintWriter w, int[] arr, int l) {
        // need to choose exact l / 2 numbers.
        // we only heve 1 or 2 chances to skip the pick.
        // if l is even, we can skip 1 time, odd: 2times.
        int chance = l % 2 + 1;
        // dp[i][j] is the maximum sum for first i nums, when we still have j chances to skip

        long[][] dp = new long[l][chance + 1];
        for (long[] d : dp) {
            Arrays.fill(d, Long.MIN_VALUE);
        }
        dp[l - 1][0] = arr[l - 1];
        for (int i = l - 3; i >= 0; i -= 2) {
            dp[i][0] = arr[i] + dp[i + 2][0];
        }
        w.println(helper(arr, dp, 0, chance));

//        for (int j = 0; j <= chance; j++) {
//            for (int i = 0; i < l; i++) {
//                System.out.print(dp[i][j] + " ");
//            }
//            System.out.println();
//        }

    }

    private static long helper(int[] arr, long[][] dp, int index, int chance) {
        long res = 0;
        int l = arr.length;
        if (index >= l) return 0;
        if (dp[index][chance] != Long.MIN_VALUE) return dp[index][chance];
        if (chance == 0) {
            return (long)1e16;
        }

        res = Math.max(helper(arr, dp, index + 1, chance - 1), helper(arr, dp, index + 2, chance) + arr[index]);
        dp[index][chance] = res;
        return res;
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
        new Thread(null, new ABC162_F_HouseRobber(),"Main",1<<27).start();
    }

}
