package Extractors;

import java.util.*;

public class Extractor
{
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
        //skompresowac wynik do 0-1 zamiast konwertowac do double (dodac metode ktora wezmie wartosc z int spłaszczy do 0-1 i zwroci odpowiedniego double)
        article.addAttribute(0,(double)countKeyWordsInArticle(article));
    }
    //ilosc slow w teksie
    private void extractSecondAttribute(Article article)
    {
        //skompresowac wynik do 0-1
        article.addAttribute(1,(double)article.getBody().size());
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
