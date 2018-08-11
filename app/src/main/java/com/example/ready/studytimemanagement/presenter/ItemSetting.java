package com.example.ready.studytimemanagement.presenter;

public class ItemSetting {
    String menuTitle;

    public ItemSetting(String menuTitle){
        this.menuTitle = menuTitle;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }
    @Override
    public String toString() {
        return "ItemSetting{" +
                "menutitle='" + menuTitle+ '\''+'}' ;
    }
}
