package com.example.ready.studytimemanagement.presenter.Item;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ItemApplock {
    private String title;
    private Drawable appIcon;
    private Boolean appSwitch;
    public ItemApplock(String title, Drawable appIcon){
        this.title = title;
        this.appIcon = appIcon;
        this.appSwitch = false;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public Boolean getAppSwitch() {
        return appSwitch;
    }

    public void setAppSwitch(Boolean appSwitch) {
        this.appSwitch = appSwitch;
    }
}
