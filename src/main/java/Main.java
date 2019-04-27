import Extractors.Article;
import Extractors.DATA;
import Extractors.DATA_API;
import Manage.Manager;
import Reading.DOCUMENT;
import Reading.Reader;

import javax.xml.bind.JAXBException;

public class Main
{

    public static void main(String[] args) throws JAXBException
    {




       // System.out.println( DATA.generatedStopList.toArray().toString());
        //DATA.setAllReuters();
      Manager m =new Manager();
      m.extractAttributes();
      m.showExtractedAttributesInSinglePlacesNameTrainingSet();
      System.out.println("chuj");
      DATA_API.getSingleNameTrainingSet().forEach(Article::showAttributes);



       /* DATA.show();*/
    }
}
