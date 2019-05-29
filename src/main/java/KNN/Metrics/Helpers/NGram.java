package KNN.Metrics.Helpers;

public class NGram implements IDistanceBetweenStrings
{

    //ilosc takich samych charow w obu porownywanych stringach
    private final int N;

    public NGram(int n)
    {
        this.N=n;
    }

    //dystans miedzy Stringami nie moze byc liczony w zwykly euklidesowy sposob i trzeba zastosowac metode n-Gram
    //oblicza ngram (przyklad 3.71 w pliku wykladowym dolaczonym do projektu)
    @Override
    public double countDistance(String left, String right)
    {
        int grams=0;
        double distance=1.0;
        if(left.length()>=N)
        {
            for(int i =0; i<left.length() - N + 1; i++)
            {
                String helper = left.substring(i,i+N);
                if(right.contains(helper))
                    grams++;
            }
            distance = grams / (findMax(left.length(), right.length()) - N + 1.0);
        }

        return distance;
    }
}
