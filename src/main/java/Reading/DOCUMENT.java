package Reading;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DOCUMENT")
@XmlAccessorType(XmlAccessType.FIELD)
public class DOCUMENT
{

    @XmlElement(name = "REUTERS")
    private ArrayList<REUTERS> reuters;

    public DOCUMENT() {
        reuters = new ArrayList<REUTERS>();
    }
    public DOCUMENT(ArrayList<REUTERS> reuters) {
        this.reuters = new ArrayList<REUTERS>();
        this.reuters = reuters;
    }
    public ArrayList<REUTERS> getREUTERS() {
        if(reuters.size() != 0)
            return reuters;
        reuters = new ArrayList<REUTERS>();
        return reuters;
    }
    public void setREUTERS(ArrayList<REUTERS> reuters) {
        this.reuters = new ArrayList<REUTERS>();
        this.reuters = reuters;
    }


}
