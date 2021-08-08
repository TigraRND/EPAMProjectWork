package pages.elements;

import java.util.Date;

public abstract class AbstractCard {
    private String lang;
    private String name;
    private Date date;
    private String regInfo;

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

    @Override
    public String toString() {
        return String.format("\nСобытие: %s\nЯзык: %s\nДата: %s\nРегистрация: %s",name,lang,date,regInfo);
    }
}
