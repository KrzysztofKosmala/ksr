package Extractors;

import java.util.*;

public class Extractor
{
    //STATIC
    //potrzebne do normalizacji (jesli wielowatek to trzeba to zabezpieczyc!)
    private static double MAX_VALUE_OF_ATTRIBUTE = 0;
    private static double MIN_VALUE_OF_ATTRIBUTE = 0;
    private final static int MAX_RANGE_OF_NORMALIZED_ATTRIBUTE = 1;
    private final static int MIN_RANGE_OF_NORMALIZED_ATTRIBUTE = 0;

    //ELSE
    private final List<Article> articlesList;
    private final HashMap<String, ArrayList<String>> keyWordsMap;



    public Extractor(List<Article> articles, HashMap<String, ArrayList<String>> keyWords)
    {
        this.articlesList = articles;
        this.keyWordsMap = keyWords;
    }
    //tutaj multithreading powinien byc
    public void run()
    {
        //watek
        articlesList.forEach(this::extractFirstAttribute);
        //watek
        articlesList.forEach(this::extractSecondAttribute);
    }


    //EKSTRAKTORY
    //wystepowanie slow kluczowego ktore musi byc podobne w stopniu minimum 0.8(JaccardSimilarity) do slowa kluczowego zapisanego w keyWordsMap
    private void extractFirstAttribute(Article article)
    {
        double value = (double)countKeyWordsInArticle(article);
        if(isBiggerThanCurrentMaxValue(value))
            MAX_VALUE_OF_ATTRIBUTE =value;

        if(isLessThanCurrentMinValue(value))
            MIN_VALUE_OF_ATTRIBUTE =value;

        article.addAttribute(0, value);
    }
    //ilosc slow w teksie
    private void extractSecondAttribute(Article article)
    {
        double value = (double)article.getBody().size();
        if(isBiggerThanCurrentMaxValue(value))
            MAX_VALUE_OF_ATTRIBUTE =value;

        if(isLessThanCurrentMinValue(value))
            MIN_VALUE_OF_ATTRIBUTE =value;

        article.addAttribute(1, value);
    }
    //czestotliwosc wystepowania slow kluczowych
    private void extractThirdAttribute(Article article)
    {

    }


    //OBLICZNIA
    private  Double calculateJaccardSimilarity(CharSequence left, CharSequence right) {
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
    private  int countKeyWordsInArticle(Article article)
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
    private boolean isBiggerThanCurrentMaxValue(double value)
    {
        return value > MAX_VALUE_OF_ATTRIBUTE;
    }

    private boolean isLessThanCurrentMinValue(double value)
    {
        return value < MIN_VALUE_OF_ATTRIBUTE;
    }

    public void normalizeVectors()
    {
        for(Article article : articlesList)
        {
            double[] helper = article.getAttributes();
            for(int i = 0; i<helper.length; i++)
            {
                article.addAttribute(i,normalizeAttribute(helper[i]));
            }
        }
    }

    private double normalizeAttribute(double value)
    {
        return ((value - MIN_VALUE_OF_ATTRIBUTE)
                / (MAX_VALUE_OF_ATTRIBUTE - MIN_VALUE_OF_ATTRIBUTE))
                * (MAX_RANGE_OF_NORMALIZED_ATTRIBUTE - MIN_RANGE_OF_NORMALIZED_ATTRIBUTE) + MIN_RANGE_OF_NORMALIZED_ATTRIBUTE;
    }



    //GETTERS

    public List<Article> getArticlesList()
    {
        return articlesList;
    }

    public HashMap<String, ArrayList<String>> getKeyWordsMap()
    {
        return keyWordsMap;
    }
}
