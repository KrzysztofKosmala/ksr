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
      m.runKNN();




       /* DATA.show();*/
    }
}
