import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;


public class ABC163_E_Active_Infants_Dp_Greedy_Sort implements Runnable
{
    final static int mod = (int) (1e9 + 7);
    @Override
    public void run() {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int n = in.nextInt();
        int[][] arr = new int[n + 1][2];
        for (int i = 1; i <= n; i++) {
            arr[i][0] = in.nextInt();
            arr[i][1] = i;
        }

        getRes(w, arr);
        w.flush();
        w.close();
    }

    private static void getRes(PrintWriter w, int[][] arr) {
        // 1. first observation is that we need to consider how to put the largest number.
        // 2. the largest number should go to the first position or the last position.
        // 3. after we sort the numbers, when we add a new number into the array, it can only be in the first or last.
        // 4. but the contribution is also related to positions, so want to know which position this number will go.

        // dp[i][j] is the maximum value we can get when we put j number in front of our first ith number.
        // we do not need to care what are those j numbers, nor the order of the first ith number.
        // the new number will go to the j + 1 position, or i + j + 1 position.

        Arrays.sort(arr, (a, b) -> (a[0] - b[0]));
        int l = arr.length - 1;
        long[][] dp = new long[l + 1][l + 1];
        for (int i = 1; i <= l; i++) {
            for (int j = 0; j <= l - i; j++) {
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j] + (Math.abs(i + j - arr[i][1]) * (long)arr[i][0]));
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j + 1] + (Math.abs(j + 1 - arr[i][1]) * (long)arr[i][0]));
            }
        }

        w.println(dp[l][0]);
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
        new Thread(null, new ABC163_E_Active_Infants_Dp_Greedy_Sort(),"Main",1<<27).start();
    }

}
