package pages.elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PastEventCard {
    private String lang;
    private String name;
    private Date date;
    private String regInfo;
    private List<EventSpeaker> speakers = new ArrayList<EventSpeaker>();

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRegInfo() {
        return regInfo;
    }

    public void setRegInfo(String regInfo) {
        this.regInfo = regInfo;
    }

    public List<EventSpeaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<EventSpeaker> speakers) {
        this.speakers = speakers;
    }

    public void addSpeaker(EventSpeaker speaker){
        speakers.add(speaker);
    }

    @Override
    public String toString() {
        String stringOfSpeakers = "Спикеры: ";
        for (EventSpeaker speaker:speakers){
            stringOfSpeakers = stringOfSpeakers + speaker.toString() + "; ";
        }
        return String.format("\nСобытие: %s\nЯзык: %s\nДата: %s\nРегистрация: %s\n%s",name,lang,date,regInfo,stringOfSpeakers);
    }
}