package Reading;/*package Extractors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.io.File;
import java.io.IOException;

public class Reader
{

   File input =  new File("C:\\Users\\Krzysiek\\Desktop\\Repozytorium\\ksr\\txt\\reut2-000.xml");
   Document doc;
public Reader()
{
    {
        try
        {
            doc = Jsoup.parse(input, "UTF-8");
*//* doc.select("REUTERS").forEach
                    (
                        article ->
                            {
                                DATA.addArticle(new REUTERS(article.getElementsByTag("BODY").toString(),article.getElementsByTag("PLACES").toString(),article.getElementsByTag("TOPICS").toString()));
                            }
                     );



    {
                System.out.println(row.getElementById("PLACES").toString());
                DATA.addArticle(new REUTERS(row.select("BODY").toString(),row.select("PLACES").toString(),row.select("TOPICS").toString()));

            }*//*


            System.out.println("tutej " + doc.select("REUTERS").get(0).getElementsByTag("TITLE").toString());
            //System.out.println(doc.select("BODY").get(1).toString());
            //System.out.println(doc.getElementsByTag("REUTERS").get(0).getElementsByTag("TEXT").select("BODY").toString());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
}*/

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;


public class Reader {
    private static boolean isTest = false;
    private DOCUMENT document;
    private static String actualPath = "reuters.xml";
    private static String testPath = "reutersTest.xml";
    public Reader() {}
    public static DOCUMENT read() throws JAXBException
    {
        JAXBContext ctx = JAXBContext.newInstance(DOCUMENT.class);
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        DOCUMENT document;
        if (isTest)
        document = (DOCUMENT) unmarshaller.unmarshal(new File(testPath));
        else
             document = (DOCUMENT) unmarshaller.unmarshal(new File(actualPath));

        return document;
    }

    public DOCUMENT getDOCUMENT() {return document;}
}
