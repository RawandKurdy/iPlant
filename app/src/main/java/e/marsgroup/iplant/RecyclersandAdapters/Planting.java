package e.marsgroup.iplant.RecyclersandAdapters;

import e.marsgroup.iplant.Database.planted;

/**
 * Created by rawan on 3/17/2018.
 */

public class Planting {

    private String imageInProgess, titleofProgress;
    planted planted;

    public e.marsgroup.iplant.Database.planted getPlanted() {
        return planted;
    }

    public void setPlanted(e.marsgroup.iplant.Database.planted planted) {
        this.planted = planted;
    }

    public String getImageInProgess() {
        return imageInProgess;
    }
    public void setImageInProgess(String imageInProgess) {
        this.imageInProgess = imageInProgess;
    }

    public String getTitleofProgress() {
        return titleofProgress;
    }
    public void setTitleofProgress(String titleofProgress) {
        this.titleofProgress = titleofProgress;
    }
}