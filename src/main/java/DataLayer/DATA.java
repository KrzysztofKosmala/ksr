package DataLayer;

import DataLayer.Helpers.PorterStemmer;
import Reading.REUTERS;
import Reading.Reader;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public final class DATA
{
    private final int PERCENT_OF_TRAINING_SET;
    private final int PERCENT_OF_OCCURRENCE_OF_WORD_IN_ONE_TAG_NEEDED_TO_RECOGNIZE_THIS_WORD_AS_KEYWORD;
    private final String NAME_OF_THE_NODE_WHICH_WILL_BE_CLASSIFIER;
    private final String PLACES_NODE = "PLACES";
    private final String TOPICS_NODE = "TOPICS";
    private final int AMOUNT_OF_KEY_WORDS;//rowno dla kazdego tagu
    private final String stopListPath = "StopList.txt";
    private final String keyWordsPath = "KeyWords.txt";

    private List<REUTERS> allReuters;
    private ArrayList<Article> allArticles;
    private ArrayList<Article> trainingSet;
    private ArrayList<Article> testSet;
    private HashMap<String, List<String>> keyWords;
    private List<String> stopList;
    private List<String> allowedStringsInClassifierNode;
    private ArrayList<Article> articlesWithAllowedStringsInClassifierNode;




    public DATA(int percentOfTrainingSet, String nameOfTheNodeWhichWillBeClassifier, List<String> allowedStringsInClassifierNode, boolean generateKeyWords, boolean generateStopList, int amountOfKeyWords)
    {
        this.PERCENT_OF_TRAINING_SET = percentOfTrainingSet;
        this.PERCENT_OF_OCCURRENCE_OF_WORD_IN_ONE_TAG_NEEDED_TO_RECOGNIZE_THIS_WORD_AS_KEYWORD =90;
        this.AMOUNT_OF_KEY_WORDS=amountOfKeyWords;
        this.NAME_OF_THE_NODE_WHICH_WILL_BE_CLASSIFIER=nameOfTheNodeWhichWillBeClassifier;
        this.allReuters = setAllReuters();
        this.allArticles = reutersToArticles(allReuters);
        //w XML-u tag PLACES ma wygladac DOKLADNIE tak samo jak element listy allowedStringsInClassifierNode inaczej nie bedzie on wczytywany do pamieci
        this.allowedStringsInClassifierNode=allowedStringsInClassifierNode;
        this.articlesWithAllowedStringsInClassifierNode = findArticlesWithAllowedStringsInClassifierNode();
        if(generateStopList)
        {
            this.stopList =generateStopList(articlesWithAllowedStringsInClassifierNode,3.1);

                save(stopListPath,stopList);

        }else
        {
            this.stopList = new ArrayList<>();

            this.stopList = load(stopListPath);
        }

        setTrainingAndTestSets(articlesWithAllowedStringsInClassifierNode);

        if(generateKeyWords)
        {
            this.keyWords = generateKeyWords(trainingSet, this.allowedStringsInClassifierNode);


            save(keyWordsPath, getListOfKeyWords());
        }else
        {
            List<String> allKeyWords = new  ArrayList<>();

            allKeyWords = load(keyWordsPath);

            this.keyWords = new HashMap<>();

            this.keyWords.put("All", allKeyWords);
        }
    }

    private void setTrainingAndTestSets(ArrayList<Article> articles )
    {

        int trainingPart = (int) (articles.size() * ( (double) PERCENT_OF_TRAINING_SET / 100)) ;

        ArrayList<Article> innerTrainingSet = new ArrayList<>();
        ArrayList<Article> innerTestSet = new ArrayList<>();
        int i;
        for(i=0; i<trainingPart; i++)
        {
            innerTrainingSet.add(articles.get(i));
        }
        for(int j=i; j<articles.size(); j++)
        {
            innerTestSet.add(articles.get(j));
        }
        this.testSet = prepareArticles(innerTestSet);
        this.trainingSet = prepareArticles(innerTrainingSet);

    }

    public  HashMap<String, List<String>> generateKeyWords(ArrayList<Article> articles, List<String> allowedStrings)
    {
        //      miejce, lista slow kluczowych dla tego miejsca
        HashMap<String, List<String>> keyWords = new HashMap<>();

        //      slowo , liczba jego wystapien                          dowolny zbior articles
        HashMap<String, Integer> occurrenceOfWords = countOccurrenceOfWords(articles);

        for(String allowedString : allowedStrings)
        {
            ArrayList<Article> articlesWithSpecificPlace;
            if(NAME_OF_THE_NODE_WHICH_WILL_BE_CLASSIFIER.equals(PLACES_NODE))
            articlesWithSpecificPlace = articles.stream().filter(article -> article.getPlaces().equals(allowedString)).collect(Collectors.toCollection(ArrayList::new));
            else if(NAME_OF_THE_NODE_WHICH_WILL_BE_CLASSIFIER.equals(TOPICS_NODE))
                articlesWithSpecificPlace = articles.stream().filter(article -> article.getTopic().equals(allowedString)).collect(Collectors.toCollection(ArrayList::new));
            else articlesWithSpecificPlace = new ArrayList<>();

            HashMap<String, Integer> occurrenceOfWordsInSpecificPlace = countOccurrenceOfWords(articlesWithSpecificPlace);
            HashMap<String, Integer> keys =  new HashMap<>();
            for (Map.Entry<String, Integer> entry : occurrenceOfWords.entrySet())
            {
                String key = entry.getKey();//slowo
                Integer value = entry.getValue();//ilosc wystapien we wszystkich

                if( occurrenceOfWordsInSpecificPlace.containsKey(key) )
                {
                    // ile procent wystepowania slowa ma byc w danym tagu zeby uznac slowo za kluczowe
                   if( (( occurrenceOfWordsInSpecificPlace.get(key)*100)/value) >= PERCENT_OF_OCCURRENCE_OF_WORD_IN_ONE_TAG_NEEDED_TO_RECOGNIZE_THIS_WORD_AS_KEYWORD)
                   {
                       keys.put(key, occurrenceOfWordsInSpecificPlace.get(key));
                   }
                }
            }
            //sortowanie po ilosci wystapien slowa kluczowego w konkretnym tagu ograniczenie do 10 najczesciej wystepujacych i zachowanie w mapie
            Map<String, Integer> sortedByCount = keys.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(AMOUNT_OF_KEY_WORDS/allowedStringsInClassifierNode.size())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            //do mapy slow kluczowych wkladanie jest miejsce do ktorego odnosza sie slowa kluczowe i slowa kluczowe
            keyWords.put(allowedString, new ArrayList<>(sortedByCount.keySet()));
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


    public List<String> getListOfKeyWords()
    {
        List<String> onlyKeyWords = new ArrayList<>();

        for(List<String> list : keyWords.values())
            onlyKeyWords.addAll(list);

        return  onlyKeyWords;
    }
    public ArrayList<Article> prepareArticles(ArrayList<Article> articles)
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
        if(NAME_OF_THE_NODE_WHICH_WILL_BE_CLASSIFIER.equals(PLACES_NODE))
        {
            for(REUTERS r : reuters)
            {


                if(!(r.getBODY() == null) && !(r.getPLACES() == null || r.getPLACES().equals("x")))
                {
                    helper.add(r);
                }
            }
        }else if(NAME_OF_THE_NODE_WHICH_WILL_BE_CLASSIFIER.equals(TOPICS_NODE))
        {
            for(REUTERS r : reuters)
            {


                if(!(r.getBODY() == null) && !(r.getTOPICS() == null || r.getTOPICS().equals("x")))
                {
                    helper.add(r);
                }
            }
        }
        return helper;
    }

    private ArrayList<Article> reutersToArticles(List<REUTERS> reuters)
    {
        ArrayList<Article> result =  new ArrayList<>();

        for(REUTERS r : reuters)
        {
           Article newArticle = new Article();
           newArticle.setTopic(r.getTOPICS());
           newArticle.setPlaces(r.getPLACES());
           newArticle.setBody(getWordsFromTxt(r.getBODY().replaceAll("[\\d|.|,|/|(|)|&|@|+|<|>|$|:|;|'|_|]", " ").replace("\n", " ").replace("-", " ").toLowerCase()));
           result.add(newArticle);
        }
        reuters.clear();
        return result;
    }

    public ArrayList<Article> getAllArticles()
    {
        return allArticles;
    }

    private  ArrayList<Article> findArticlesWithAllowedStringsInClassifierNode()
    {
        ArrayList<Article> allowedArticles = new ArrayList<>();

        if(NAME_OF_THE_NODE_WHICH_WILL_BE_CLASSIFIER.equals(PLACES_NODE))
        {
            for (Article article : allArticles)
            {
                if (isGoodPlace(article))
                {
                    allowedArticles.add(article);
                }
            }
        }else if(NAME_OF_THE_NODE_WHICH_WILL_BE_CLASSIFIER.equals(TOPICS_NODE))
        {
            for (Article article : allArticles)
            {
                if (isGoodTopic(article))
                {
                    allowedArticles.add(article);
                }
            }
        }
            return allowedArticles;
    }

    private  ArrayList<Article> removeStopListWordsFromArticles(ArrayList<Article> articles)
    {
        articles.forEach(article -> article.setBody(removeWordsContainedInStopList(article.getBody(), stopList)));

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

        for (String place : allowedStringsInClassifierNode)
            if (r.getPlaces().equals(place)) return true;

        return false;
    }

    private  boolean isGoodTopic(Article r)
    {

        for (String topic : allowedStringsInClassifierNode)
            if (r.getTopic().equals(topic)) return true;

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

    private   ArrayList<String> removeWordsContainedInStopList(ArrayList<String> body, List<String> stopList)
    {
        return body
                .stream().parallel()
                .filter(word -> !stopList.contains(word))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private   List<String> generateStopList(List<Article> articles, Double occurancePercentage){

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

    public void save(String fileName, List<String> list)
    {
        try{

        PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
        for (String string : list)
            pw.println(string);
        pw.close();
    } catch (FileNotFoundException e)
    {
        e.printStackTrace();
    }
    }

    public List<String> load(String fileName)
    {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {

            //br returns as stream and convert it into a List
            return br.lines().collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    public ArrayList<Article> getTrainingSet()
    {
        return trainingSet;
    }

    public ArrayList<Article> getTestSet()
    {
        return testSet;
    }

    public HashMap<String, List<String>> getKeyWords()
    {
        return keyWords;
    }

    public List<String> getStopList()
    {
        return stopList;
    }

    public List<String> getAllowedStringsInClassifierNode()
    {
        return allowedStringsInClassifierNode;
    }

    ArrayList<Article> getArticlesWithAllowedStringsInClassifierNode()
    {
        return articlesWithAllowedStringsInClassifierNode;
    }

    public String getNAME_OF_THE_NODE_WHICH_WILL_BE_CLASSIFIER()
    {
        return NAME_OF_THE_NODE_WHICH_WILL_BE_CLASSIFIER;
    }
}
