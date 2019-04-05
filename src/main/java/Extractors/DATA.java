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

    public static ArrayList<String> generatedStopList =generateStopList(allArticles,2.8);
    private static List<String> singlePlacesNames = Arrays.asList("west-germany","usa","france","uk","canada","japan");
    private static ArrayList<Article> singlePlacesArticles = findSinglePlacesArticles();







    private static List<REUTERS> deleteUncompliteData(List<REUTERS> reuters)
    {
        List<REUTERS> helper = new ArrayList<>();
        for(REUTERS r : reuters)
        {


            if(!(r.getBODY() == null) && !(r.getPLACES() == null))
            {
                helper.add(r);
            }
        }
        return helper;
    }

    public static ArrayList<Article> prepareArticles(ArrayList<Article> articles)
    {
        removeStopListWordsFromArticles(articles);


        return articles;
    }

    public static ArrayList<Article> reutersToArticles(List<REUTERS> reuters)
    {
        int a;
        ArrayList<Article> result =  new ArrayList<>();

        for(REUTERS r : reuters)
        {
           Article newArticle = new Article();
            if(result.size()==29)
                a=5;
           newArticle.setTopics(getWordsFromTxt(r.getTOPICS()));
           newArticle.setPlaces(getWordsFromTxt(r.getPLACES()));
           newArticle.setBody(getWordsFromTxt(r.getBODY()));
           result.add(newArticle);

        }


        return result;
    }

//dopisac metode do czegos tam czyli trim

    private static ArrayList<Article> findSinglePlacesArticles()
    {
        ArrayList<Article> allSinglePlacesArticles = new ArrayList<>();

        for(Article article : allArticles)
            if(isGoodPlace(article))
                allSinglePlacesArticles.add(article);

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
            if(r.getPlaces().toString().equals(singlePlacesNames.get(i)))
                return true;

        return false;
    }
    public static List<REUTERS> setAllReuters()
   {
        List<REUTERS> r = new ArrayList<>();
       {
           try
           {
               r = deleteUncompliteData(Reader.read().getREUTERS());
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
