import java.io.*;
import java.util.InputMismatchException;


public class ABC156_E_Combo_RepeatPick implements Runnable
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
        // divide the situation into the number of rooms, that does not have people.
        // consider all room have people, res++, if 1 room is empty, res += (n - 1) * n ;
        long res = (n - 1) * (long) n % mod + 1;
        // 1 move we can only move one person, so at most we can make min(n-1, k) room empty
        k = Math.min(n - 1, k);
        // consider at least two rooms are empty.
        // if two rooms are empty, then, n people in n-2 rooms, each one has at least 1 people.
        // mean that there are only people we can choose any room to enter.
        // this combo can be combo((n-1 - i) + i, i), means we need to put i people in n-1 rooms.
        // then we need to multiply the combo of choosing empty room. combo(n, i);
        long[] inv = getInvArray(n, mod);
//        System.out.println(inv[2]);
//        System.out.println(2 * inv[2] % mod);

        long roomCombo = n, peopleCombo = n - 1;

        for (int i = 2; i <= k; i++) {
            roomCombo = ((roomCombo * (n + 1 - i) % mod) * inv[i]) % mod;
            peopleCombo = ((peopleCombo * (n - i) % mod) * inv[i]) % mod;
            long add = roomCombo * peopleCombo % mod;

            // System.out.println(i + " " + add);
            res = (res + add + mod) % mod;
        }
        w.println((res + mod) % mod);
    }


    // get the inverse of number from 1-n, when mod==p.
    public  static long[] getInvArray(long n, int p){
        long[] inv = new long[(int)n + 1];
        inv[1] = 1;
        for (int i = 2; i <= n; i++) {
            inv[i] = ((p - p / i) * inv[p % i] % p + p) % p;
        }
        return inv;
    }

    // fast Exponent
    private static long fastExp(long base, long n) {
        if (n == 1) {
            return base;
        }
        if (n % 2 == 1) {
            return base * fastExp(base,n - 1) % mod;
        }

        return fastExp(base * base % mod, n / 2);
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
        new Thread(null, new ABC156_E_Combo_RepeatPick(),"Main",1<<27).start();
    }

}
