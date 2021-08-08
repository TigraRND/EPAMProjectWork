package pages.elements;

public class UpcomingEventCard extends AbstractCard {
    private String format;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "\nТип: " + format + super.toString();
    }
}
