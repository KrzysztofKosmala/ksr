package Extractors;

import Reading.REUTERS;
import Reading.Reader;

import javax.xml.bind.JAXBException;
import java.util.*;
import java.util.stream.Collectors;

public final class DATA
{
    
    private static List<REUTERS> allReuters =setAllReuters();



    public static ArrayList<Article> allArticles = reutersToArticles(allReuters);

    public static ArrayList<String> generatedStopList =generateStopList(allArticles,2.1);
    private static List<String> singlePlacesNames = Arrays.asList("west-germany","usa","france","uk","canada","japan");
    private static ArrayList<Article> singlePlacesArticles = findSinglePlacesArticles();

    private static HashMap<String, String> generateKeyWords(ArrayList<Article> articles)
    {
        HashMap<String, String> keyWords = new HashMap<>();





        return keyWords;
    }

    public static ArrayList<Article> getPreparedSinglePlacesArticles()
    {
        return prepareArticles(singlePlacesArticles);
    }

    private static ArrayList<Article> prepareArticles(ArrayList<Article> articles)
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

    private static List<REUTERS> deleteIncompliteData(List<REUTERS> reuters)
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

    public static ArrayList<Article> reutersToArticles(List<REUTERS> reuters)
    {
        int a;
        ArrayList<Article> result =  new ArrayList<>();

        for(REUTERS r : reuters)
        {
           Article newArticle = new Article();

           newArticle.setTopics(getWordsFromTxt(r.getTOPICS()));
           newArticle.setPlaces(getWordsFromTxt(r.getPLACES()));
           newArticle.setBody(getWordsFromTxt(r.getBODY().replaceAll("[\\d|.|,|/|(|)|-]", "").replace("\n", " ").toLowerCase()));
           result.add(newArticle);

        }


        return result;
    }

    public static ArrayList<Article> getAllArticles()
    {
        return allArticles;
    }

    private static ArrayList<Article> findSinglePlacesArticles()
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

    private static ArrayList<Article> removeStopListWordsFromArticles(ArrayList<Article> articles)
    {
        articles.forEach(article -> article.setBody(removeWordsContainedInStopList(article.getBody(), generatedStopList)));

        return articles;
    }

    private static ArrayList<String> getWordsFromTxt(String txt)
    {
        String[] helper;
        ArrayList<String> result = new ArrayList<>();
        helper = txt.split(" |/n");
        Collections.addAll(result, helper);
        result.removeAll(Arrays.asList("", null));
        return result;
    }

    private static boolean isGoodPlace(Article r)
    {

        for(int i = 0; i < singlePlacesNames.size(); i++)
            if(r.getPlaces().get(0).equals(singlePlacesNames.get(i)))
                return true;

        return false;
    }

    public static List<REUTERS> setAllReuters()
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

    public static ArrayList<String> removeWordsContainedInStopList(ArrayList<String> body, ArrayList<String> stopList)
    {
        return body
                .stream().parallel()
                .filter(word -> !stopList.contains(word))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<String> generateStopList(List<Article> articles, Double occurancePercentage){
        Map<String,Integer> stopLista = new HashMap<>();
        ArrayList<String> result = new ArrayList<>();
        for(Article article : articles){
            for(String word : article.getBody()){
                if(stopLista.containsKey(word)){
                    stopLista.replace(word,stopLista.get(word)+1);
                }else{
                    stopLista.put(word,1);
                }
            }
        }
        Set<String> words = stopLista.keySet();
        for(String word : words){
            if(stopLista.get(word) > articles.size()/occurancePercentage){
                result.add(word);
            }
        }
        return result;
    }


}
