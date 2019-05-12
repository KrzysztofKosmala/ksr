package Extractors;

import java.util.*;

public class Extractor
{

    //potrzebne do normalizacji (jesli wielowatek to trzeba to zabezpieczyc!)
    private double MAX_VALUE_OF_ATTRIBUTE = 0;
    private double MIN_VALUE_OF_ATTRIBUTE = 0;
    //
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

    //OPCJE
    //tutaj multithreading powinien byc
    public void run()
    {
        articlesList.forEach(article ->
        {
            extractFirstAttribute(article);
            extractSecondAttribute(article);
            extractThirdAttribute(article);
            extractFourthAttribute(article);
        });
    }
    public void normalizeVectors()
    {
        for(Article article : articlesList)
        {
            HelperForArticle[] helper = article.getAttributes();
            for(int i = 0; i<helper.length; i++)
            {
                if(helper[i]!=null)
                    if(helper[i].isDoubleValue())
                        article.setDoubleAttribute(i,normalizeAttribute(helper[i]));
            }
        }
    }

    //EKSTRAKTORY
    //wystepowanie slow kluczowego ktore musi byc podobne w stopniu minimum 0.8(JaccardSimilarity) do slowa kluczowego zapisanego w keyWordsMap
    private void extractFirstAttribute(Article article)
    {
        double value = (double)countKeyWordsInArticle(article);

        setNewMinMaxValueOfAttributeIfIsNeeded(value);
        //jakies zabezpieczenie
        article.addAttribute(0, new HelperForArticle(value));
    }
    //ilosc slow w teksie
    private void extractSecondAttribute(Article article)
    {
        double value = (double)article.getBody().size();

        setNewMinMaxValueOfAttributeIfIsNeeded(value);
        //jakies zabezpieczenie
        article.addAttribute(1, new HelperForArticle(value));
    }
    //pierwsze słowo kluczowe wystepujace w tekscie
    private void extractThirdAttribute(Article article)
    {
        boolean helper = true;
        for(String word : article.getBody())
        {
            if(helper)
            for (ArrayList<String> allKeyWords : keyWordsMap.values())
            {
                if(helper)
                for (String keyWord : allKeyWords)
                {
                    if(calculateJaccardSimilarity(word, keyWord)>0.8)
                    {
                        article.addAttribute(2, new HelperForArticle(keyWord));

                       helper = false;
                       break;
                    }
                }
            }
        }
    }
    //numer indexu w ktorym znajduje sie pierwsze znalezione slowo kluczowe
    private void extractFourthAttribute(Article article)
    {
        //jakies zabezpieczenie
        if(article.getAttributes()[2] != null)
        {
            double value = (double)article.getBody().indexOf(article.getAttributes()[2].getString());

            setNewMinMaxValueOfAttributeIfIsNeeded(value);
            article.addAttribute(3, new HelperForArticle(value));
        }
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
    private Double normalizeAttribute(HelperForArticle value)
    {
        return ((value.getDouble() - MIN_VALUE_OF_ATTRIBUTE)
                / (MAX_VALUE_OF_ATTRIBUTE - MIN_VALUE_OF_ATTRIBUTE))
                * (MAX_RANGE_OF_NORMALIZED_ATTRIBUTE - MIN_RANGE_OF_NORMALIZED_ATTRIBUTE) + MIN_RANGE_OF_NORMALIZED_ATTRIBUTE;
    }
    private void setNewMinMaxValueOfAttributeIfIsNeeded(double value)
    {
        if(isBiggerThanCurrentMaxValue(value))
            MAX_VALUE_OF_ATTRIBUTE =value;

        if(isLessThanCurrentMinValue(value))
            MIN_VALUE_OF_ATTRIBUTE =value;
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
