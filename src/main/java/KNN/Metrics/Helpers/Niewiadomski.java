package KNN.Metrics.Helpers;

import static java.lang.Math.*;

public class Niewiadomski implements IDistanceBetweenStrings
{

    final int N;

    public Niewiadomski(int n)
    {
        N = n;
    }

    @Override
    public double countDistance(String left, String right)
    {

        int n;
        int N;
        int counter = 0;
        String shorterWord;
        String longerWord;

        n = min(left.length(), right.length());
        N = max(left.length(), right.length());

        if(left.length() > right.length()) {
            longerWord = left;
            shorterWord = right;
        }
        else {
            longerWord = right;
            shorterWord = left;
        }

        // po n-gramach...
        for(int i = 1; i <= n; i++) {
            // po pierwszym słowie...
            for(int j = 0; j < longerWord.length() - i + 1; j++) {
                // po drugim słowie...
                for(int k = 0; k < shorterWord.length() - i + 1; k++) {
                    if(longerWord.substring(j, j + i).equals(shorterWord.substring(k, k + i))) {
                        counter++;
                        break;
                    }
                }
            }
        }

        return (2 / ((pow(N, 2) + N)) ) * counter;

    }

}
