package unsolved;

import java.io.*;
import java.util.*;
import java.lang.*;


public class ABC164_F_Unsolved implements Runnable
{
    @Override
    public void run() {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int n = (int) in.nextLong();
        long[] s = new long[n], t = new long[n], u = new long[n], v = new long[n];
        for (int i = 0; i < n; i++) s[i] = in.nextLong();
        for (int i = 0; i < n; i++) t[i] = in.nextLong();
        for (int i = 0; i < n; i++) u[i] = in.nextLong();
        for (int i = 0; i < n; i++) v[i] = in.nextLong();

        getRes(n, s, t, u, v, w);


        w.flush();
        w.close();
    }

    private static void getRes(int n, long[] s, long[] t, long[] u, long[] v, PrintWriter w) {
        long[][] upper = new long[n][n], lower = new long[n][n];
        long[][] res = new long[n][n];
        long[] target = new long[n * 2];
        int count = 0;
        for (int i = 0; i < 2 * n; i++) {
            target[i] = i >= n ? v[i - n] : u[i];
        }
        boolean[] match = new boolean[2 * n];
        int[] m = new int[2 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (s[i] == 0 && t[j] == 0) {
                    lower[i][j] = u[i] | v[j];
                    if (u[i] == v[j]) {
                        match[i] = true;
                        match[j + n] = true;
                        res[i][j] = u[i];
                    }
                }
                else if (s[i] == 1 && t[j] == 0) {
                    lower[i][j] = u[i];
                    upper[i][j] = v[j];
                    if (u[i] == v[j]) {
                        match[i] = true;
                        match[j + n] = true;
                        res[i][j] = u[i];
                    }
                    if (upper[i][j] != lower[i][j] && (upper[i][j] | lower[i][j]) == lower[i][j]) {
                        w.println(-1);
                        return;
                    }
                }
                else if (s[i] == 0 && t[j] == 1) {
                    lower[i][j] = v[j];
                    upper[i][j] = u[i];
                    if (u[i] == v[j]) {
                        match[i] = true;
                        match[j + n] = true;
                        res[i][j] = u[i];
                    }
                    if (upper[i][j] != lower[i][j] && (upper[i][j] | lower[i][j]) == lower[i][j]) {
                        w.println(-1);
                        return;
                    }
                }
                else {
                    upper[i][j] = u[i] & v[j];
                    if (u[i] == v[j]) {
                        match[i] = true;
                        match[j + n] = true;
                        res[i][j] = u[i];
                    }
                }
            }
        }




        // w.println(res);
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
        new Thread(null, new ABC164_F_Unsolved(),"Main",1<<27).start();
    }

}
