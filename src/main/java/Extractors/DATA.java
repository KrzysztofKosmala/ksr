package Extractors;

import Reading.REUTERS;
import Reading.Reader;

import javax.xml.bind.JAXBException;
import java.util.*;
import java.util.stream.Collectors;

public final class DATA
{
    private final int PERCENT_OF_TRAINING_SET;

    private List<REUTERS> allReuters;
    private ArrayList<Article> allArticles;
    private ArrayList<Article> singleNameTrainingSet;
    private ArrayList<Article> singleNameTestSet;
    private HashMap<String, ArrayList<String>> keyWords;
    private ArrayList<Article> preparedSinglePlacesArticles;
    private ArrayList<String> generatedStopList;
    private List<String> singlePlacesNames;
    private ArrayList<Article> singlePlacesArticles;



    public DATA(int percentOfTrainingSet)
    {
        this.PERCENT_OF_TRAINING_SET = percentOfTrainingSet;
        allReuters = setAllReuters();
        allArticles = reutersToArticles(allReuters);
        singlePlacesNames = Arrays.asList("west-germany","usa","france","uk","canada","japan");
        singlePlacesArticles = findSinglePlacesArticles();
        generatedStopList =generateStopList(singlePlacesArticles,3.1);
        setSinglePlacesTrainingAndTestSets(singlePlacesArticles);
        keyWords = generateKeyWords(singleNameTrainingSet, singlePlacesNames);
    }

    private void setSinglePlacesTrainingAndTestSets(ArrayList<Article> articles )
    {

        int trainingPart = (int) (articles.size() * ( (double) PERCENT_OF_TRAINING_SET / 100)) ;

        ArrayList<Article> singleNameTrainingSetInner = new ArrayList<>();
        ArrayList<Article> singleNameTestSetInner = new ArrayList<>();
        int i;
        for(i=0; i<trainingPart; i++)
        {
            singleNameTrainingSetInner.add(articles.get(i));
        }
        for(int j=i; j<articles.size(); j++)
        {
            singleNameTestSetInner.add(articles.get(j));
        }
        this.singleNameTestSet = prepareArticles(singleNameTestSetInner);
        this.singleNameTrainingSet = prepareArticles(singleNameTrainingSetInner);

    }

    public  HashMap<String, ArrayList<String>> generateKeyWords(ArrayList<Article> articles, List<String> placesNames)
    {
        //      miejce, lista slow kluczowych dla tego miejsca
        HashMap<String, ArrayList<String>> keyWords = new HashMap<>();

        //      slowo , liczba jego wystapien                          dowolny zbior articles
        HashMap<String, Integer> occurOfWords = countOccurrenceOfWords(articles);

        for(String place : placesNames)
        {
            ArrayList<Article> articlesWithSpecificPlace = articles.stream().filter(article -> article.getPlaces().get(0).equals(place)).collect(Collectors.toCollection(ArrayList::new));//tutaj troche smierdzi z tym 0 na sztywno
            HashMap<String, Integer> occurOfWordsInSpecificPlace = countOccurrenceOfWords(articlesWithSpecificPlace);
            HashMap<String, Integer> keys =  new HashMap<>();
            for (Map.Entry<String, Integer> entry : occurOfWords.entrySet())
            {
                String key = entry.getKey();//slowo
                Integer value = entry.getValue();//ilosc wystapien we wszystkich

                if( occurOfWordsInSpecificPlace.containsKey(key) )
                {
                    // ile procent wystepowania slowa ma byc w danym tagu zeby uznac slowo za kluczowe
                   if( ((occurOfWordsInSpecificPlace.get(key)*100)/value) >= 90 )// 90% gdzies zdefiniowac
                   {
                       keys.put(key, occurOfWordsInSpecificPlace.get(key));
                   }
                }
            }
            //sortowanie po ilosci wystapien slowa kluczowego w konkretnym tagu ograniczenie do 10 najczesciej wystepujacych i zachowanie w mapie
            Map<String, Integer> sortedByCount = keys.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(10)//zadeklarowac globalnie
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            //do mapy slow kluczowych wkladanie jest miejsce do ktorego odnosza sie slowa kluczowe i slowa kluczowe
            keyWords.put(place, new ArrayList<>(sortedByCount.keySet()));
        }

        return keyWords;
    }

    private  HashMap<String, Integer> countOccurrenceOfWords(List<Article> articles)
    {
        HashMap<String, Integer> occurOfWords = new HashMap<>();

        for(Article article : articles)
        {
            for(String word : article.getBody())
            {
                if(occurOfWords.containsKey(word))
                {
                    occurOfWords.replace(word,occurOfWords.get(word)+1);
                }else
                {
                    occurOfWords.put(word,1);
                }
            }
        }
        return occurOfWords;
    }

    public  ArrayList<Article> getPreparedSinglePlacesArticles()
    {
        return prepareArticles(singlePlacesArticles);
    }

    ArrayList<Article> prepareArticles(ArrayList<Article> articles)
    {
        ArrayList<Article> helper = removeStopListWordsFromArticles(articles);

        for(Article article : helper)
        {
            PorterStemmer stemmer;
            ArrayList<String> stemmWords = new ArrayList<>();
            String stemmWord;

            for(String w : article.getBody())
            {
                stemmer = new PorterStemmer();
                stemmer.add(w.toCharArray(), w.length());
                stemmer.stem();
                stemmWord = stemmer.toString();
                if(stemmWord.length() > 1 )
                {
                    stemmWords.add(stemmWord);
                }
            }
            article.setBody(stemmWords);

        }
        return helper;
    }

    private  List<REUTERS> deleteIncompliteData(List<REUTERS> reuters)
    {
        List<REUTERS> helper = new ArrayList<>();
        for(REUTERS r : reuters)
        {


            if(!(r.getBODY() == null) && !(r.getPLACES() == null || r.getPLACES().equals("x")))
            {
                helper.add(r);
            }
        }
        return helper;
    }

    private   ArrayList<Article> reutersToArticles(List<REUTERS> reuters)
    {
        ArrayList<Article> result =  new ArrayList<>();

        for(REUTERS r : reuters)
        {
           Article newArticle = new Article();
           newArticle.setTopics(getWordsFromTxt(r.getTOPICS()));
           newArticle.setPlaces(getWordsFromTxt(r.getPLACES()));
           newArticle.setBody(getWordsFromTxt(r.getBODY().replaceAll("[\\d|.|,|/|(|)|&|@|+|<|>|$|:|;|'|_|]", " ").replace("\n", " ").replace("-", " ").toLowerCase()));
           result.add(newArticle);
        }
        return result;
    }

    ArrayList<Article> getAllArticles()
    {
        return allArticles;
    }

    private  ArrayList<Article> findSinglePlacesArticles()
    {
        ArrayList<Article> allSinglePlacesArticles = new ArrayList<>();

        for(Article article : allArticles)
        {
            if(isGoodPlace(article))
            {
                allSinglePlacesArticles.add(article);
            }
        }

            return allSinglePlacesArticles;
    }

    private  ArrayList<Article> removeStopListWordsFromArticles(ArrayList<Article> articles)
    {
        articles.forEach(article -> article.setBody(removeWordsContainedInStopList(article.getBody(), generatedStopList)));

        return articles;
    }

    private  ArrayList<String> getWordsFromTxt(String txt)
    {
        String[] helper;
        ArrayList<String> result = new ArrayList<>();
        helper = txt.split(" |/n");
        Collections.addAll(result, helper);
        result.removeAll(Arrays.asList("", null));
        return result;
    }

    private  boolean isGoodPlace(Article r)
    {

        for (String singlePlacesName : singlePlacesNames)
            if (r.getPlaces().get(0).equals(singlePlacesName)) return true;

        return false;
    }

    private   List<REUTERS> setAllReuters()
    {
        List<REUTERS> r = new ArrayList<>();
       {
           try
           {
               r = deleteIncompliteData(Reader.read().getREUTERS());
           } catch (JAXBException e)
           {
               e.printStackTrace();
           }
       }
       return r ;

   }

    private   ArrayList<String> removeWordsContainedInStopList(ArrayList<String> body, ArrayList<String> stopList)
    {
        return body
                .stream().parallel()
                .filter(word -> !stopList.contains(word))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private   ArrayList<String> generateStopList(List<Article> articles, Double occurancePercentage){

        ArrayList<String> result = new ArrayList<>();

        HashMap<String,Integer> stopLista = countOccurrenceOfWords(articles);

        Set<String> words = stopLista.keySet();
        for(String word : words){
            if(stopLista.get(word) > articles.size()/occurancePercentage){
                result.add(word);
            }
        }
        return result;
    }

    ///////////////////////////////////////////////////////////////////////////
    public int getPERCENT_OF_TRAINING_SET()
    {
        return PERCENT_OF_TRAINING_SET;
    }

    public List<REUTERS> getAllReuters()
    {
        return allReuters;
    }

    ArrayList<Article> getSingleNameTrainingSet()
    {
        return singleNameTrainingSet;
    }

    ArrayList<Article> getSingleNameTestSet()
    {
        return singleNameTestSet;
    }

    HashMap<String, ArrayList<String>> getKeyWords()
    {
        return keyWords;
    }

    ArrayList<String> getGeneratedStopList()
    {
        return generatedStopList;
    }

    List<String> getSinglePlacesNames()
    {
        return singlePlacesNames;
    }

    ArrayList<Article> getSinglePlacesArticles()
    {
        return singlePlacesArticles;
    }
}
