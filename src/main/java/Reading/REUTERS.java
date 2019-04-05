package Reading;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "REUTERS")
@XmlAccessorType(XmlAccessType.FIELD)
public class REUTERS
{

    @XmlAttribute(name = "NEWID")
    private String NEWID;
    @XmlAttribute(name = "OLDID")
    private String OLDID;
    private String BODY;
    private String PLACES;
    private String TOPICS;

    public REUTERS() {}
    public REUTERS(String places, String body, String topic)
    {

        PLACES = places;
        BODY = body;
        TOPICS = topic;
        /*OLDID = "11";
        NEWID = "12";*/
    }
    public String getNEWID() {return NEWID;}
    public void setNEWID(String nEWID) {NEWID = nEWID;}
    public String getOLDID() {return OLDID;}
    public void setOLDID(String oLDID) {OLDID = oLDID;}
    public String getBODY() {return BODY;}
    public void setBODY(String bODY) {BODY = bODY;}
    public String getPLACES() {return PLACES;}
    public void setPLACES(String pLACES) {PLACES = pLACES;}
    public String getTOPICS() {return TOPICS;}
    public void setTOPICS(String tOPICS) {TOPICS = tOPICS;}
}

