package Extractors.Helpers;

import DAO.DATA_API;

import java.util.*;

public class CalculationsForExtractors
{
    private DATA_API dataApi;

    public CalculationsForExtractors(DATA_API data_api)
    {
        this.dataApi = data_api;
    }

    Double calculateJaccardSimilarity(CharSequence left, CharSequence right) {
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
    public int countKeyWordsInBody(ArrayList<String> body)
    {
        int occurrenceOfKeyWordsInArticle = 0;

        for(String word : body)
        {
            for(String keyWord : dataApi.getKeyWords())
            {
                    if(calculateJaccardSimilarity(word,keyWord)>=0.8)
                    {
                        occurrenceOfKeyWordsInArticle++;
                    }
            }
        }
        return occurrenceOfKeyWordsInArticle;
    }

    public String findFirstKeyWord(ArrayList<String> body)
    {
        for(String word : body)
        {
                for(String keyWord : dataApi.getKeyWords())
                {
                    if(calculateJaccardSimilarity(word, keyWord)>0.8)
                    {

                        return keyWord;

                    }
                }
        }
        return "";

    }

    public double countFrequencyOfKeyWords(ArrayList<String> body)
    {
        int keyWordsInBody =countKeyWordsInBody(body);
        if(keyWordsInBody==0)
            return 0.0;
        else
        return (double)keyWordsInBody/body.size();
    }


    public String getAllKeyWordsWhichOccursInPassedBody(ArrayList<String> body)
    {
        StringBuilder keyWordsInBody = new StringBuilder().append("");

        for(String word : body)
        {
            for(String keyWord : dataApi.getKeyWords())
            {
                if(calculateJaccardSimilarity(word, keyWord)>0.8)
                {

                    keyWordsInBody.append(keyWord);

                }
            }
        }

        return keyWordsInBody.toString();
    }


}
