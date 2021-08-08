package pages.elements;

import java.util.ArrayList;
import java.util.List;

public class PastEventCard extends AbstractCard {
    private List<EventSpeaker> speakers = new ArrayList<EventSpeaker>();

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
        return super.toString() + "\n" + stringOfSpeakers;
    }
}