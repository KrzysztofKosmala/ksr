import Extractors.Article;
import Extractors.DATA_API;
import Manage.Manager;

public class Main
{

    public static void main(String[] args)
    {




       // System.out.println( DATA.generatedStopList.toArray().toString());
        //DATA.setAllReuters();
      Manager m =new Manager();
      m.extractAttributes();
      m.normalizeAttributes();
      DATA_API.getTrainingSet().forEach(Article::showAttributes);
      System.out.println(DATA_API.getKeyWords());



       /* DATA.show();*/
    }
}
