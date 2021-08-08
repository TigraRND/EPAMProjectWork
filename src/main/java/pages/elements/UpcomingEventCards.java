package pages.elements;

public class UpcomingEventCards extends AbstractCard {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "\nТип: " + type + super.toString();
    }
}
