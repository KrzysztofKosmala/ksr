import Extractors.DATA;
import Reading.DOCUMENT;
import Reading.Reader;

import javax.xml.bind.JAXBException;

public class Main
{

    public static void main(String[] args) throws JAXBException
    {




       // System.out.println( DATA.generatedStopList.toArray().toString());
        //DATA.setAllReuters();
      System.out.println( DATA.getPreparedSinglePlacesArticles().get(2).getBody());




       /* DATA.show();*/
    }
}
