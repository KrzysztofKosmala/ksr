package Extractors;

import Extractors.Helpers.HelperForArticle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public interface IExtractors
{
    public void extract(Article article);


    default Double calculateJaccardSimilarity(CharSequence left, CharSequence right) {
        Set<String> intersectionSet = new HashSet<>();
        Set<String> unionSet = new HashSet<>();
        boolean unionFilled = false;
        int leftLength = left.length();
        int rightLength = right.length();
        if (leftLength == 0 || rightLength == 0) {
            return 0d;
        }

        for (int leftIndex = 0; leftIndex < leftLength; leftIndex++) {
            unionSet.add(String.valueOf(left.charAt(leftIndex)));
            for (int rightIndex = 0; rightIndex < rightLength; rightIndex++) {
                if (!unionFilled) {
                    unionSet.add(String.valueOf(right.charAt(rightIndex)));
                }
                if (left.charAt(leftIndex) == right.charAt(rightIndex)) {
                    intersectionSet.add(String.valueOf(left.charAt(leftIndex)));
                }
            }
            unionFilled = true;
        }
        return (double) intersectionSet.size() / (double) unionSet.size();
    }
    default int countKeyWordsInArticle(Article article)
    {
        int occurrenceOfKeyWordsInArticle = 0;

        for(String word : article.getBody())
        {
            for(ArrayList<String> allKeyWords : keyWordsMap.values())
            {
                for (String keyWord : allKeyWords)
                {
                    if(calculateJaccardSimilarity(word,keyWord)>=0.8)
                    {
                        occurrenceOfKeyWordsInArticle++;
                    }
                }
            }
        }
        return occurrenceOfKeyWordsInArticle;
    }
    boolean isBiggerThanCurrentMaxValue(double value);
    boolean isLessThanCurrentMinValue(double value);

    void setNewMinMaxValueOfAttributeIfIsNeeded(double value);


}
