package Tests;

import DataLayer.DATA;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class DATA_TESTS
{
    @BeforeClass
    public void initialize()
    {
        DATA dataPlacesToTest = new DATA(60, "PLACES", Arrays.asList("west-germany","usa","france","uk","canada","japan"),false,false);
        DATA dataTopicsToTest = new DATA(60, "TOPICS", Arrays.asList("acq","gas","veg-oillinseedlin-oilsoy-oilsun-oilsoybeanoilseedcornsunseedgrainsorghumwheat","earn","cocoa","money-supply","dlrmoney-fx"),false,false);
    }



   /* @Test
    public void*/
}
