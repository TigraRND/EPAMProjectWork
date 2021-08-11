package pages.elements;

public class EventSpeaker {
    private String name;
    private String job;

    public EventSpeaker(String name, String job) {
        this.name = name;
        this.job = job;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", name, job);
    }
}