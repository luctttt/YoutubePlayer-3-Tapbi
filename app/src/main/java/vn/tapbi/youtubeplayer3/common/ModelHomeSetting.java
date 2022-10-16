package vn.tapbi.youtubeplayer3.common;

public class ModelHomeSetting {
    private int icon;
    private String text;

    public ModelHomeSetting() {
    }

    public ModelHomeSetting(int icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
