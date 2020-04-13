import java.io.*;
import java.util.InputMismatchException;


public class ABC162_D_Dp_TwoSide implements Runnable
{
    final static int mod = (int) (1e9 + 7);
    @Override
    public void run() {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int n = Integer.parseInt(in.nextLine());
        String s =  in.nextLine();
        getRes(w, s, n);
        w.flush();
        w.close();
    }

    private static void getRes(PrintWriter w, String s, int l) {
        // 1. count the number of each character before and after this index.
        // c[l][0]: R, c[l][1]: G, c[l][2]: B
        int[][][] count = new int[l][2][3];
        for (int i = 1; i < l; i++) {
            char cl = s.charAt(i - 1), cr = s.charAt(l - i);
            int typel = cl == 'R' ? 0 : cl == 'G' ? 1 : 2;
            int typer = cr == 'R' ? 0 : cr == 'G' ? 1 : 2;
            for (int t = 0; t < 3; t++) {
                count[i][0][t] = count[i - 1][0][t];
                count[l - i - 1][1][t] = count[l - i][1][t];
            }
            count[i][0][typel]++;
            count[l - i - 1][1][typer]++;
        }

//        for (int i = 0; i < l; i++) {
//            System.out.print(count[i][1][2] + " ");
//        }
//        System.out.println();
        // 2. loop each index
        // Left*mid*Right
        long res = 0;
        for (int i = 0; i < l; i++) {
            char c = s.charAt(i);
            int t = c == 'R' ? 0 : c == 'G' ? 1 : 2;
            res += t == 0 ? (count[i][0][1] * count[i][1][2] + count[i][0][2] * count[i][1][1]) :
                    t == 1 ? (count[i][0][0] * count[i][1][2] + count[i][0][2] * count[i][1][0]) :
                            count[i][0][0] * count[i][1][1] + count[i][0][1] * count[i][1][0];

            int left = i - 1, right = i + 1;
            while (left >= 0 && right < l) {
                if (s.charAt(left) != s.charAt(right) && s.charAt(left) != s.charAt(i) && s.charAt(right) != s.charAt(i)) {
                    res--;
                }
                left--;
                right++;
            }
        }
        w.println(res);
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
        new Thread(null, new ABC162_D_Dp_TwoSide(),"Main",1<<27).start();
    }

}
