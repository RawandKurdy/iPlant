package e.marsgroup.iplant.RecyclersandAdapters;

import e.marsgroup.iplant.Database.plant;

/**
 * Created by rawan on 3/17/2018.
 */

public class Plantshortinfo {

    private String image, title, brieftxt, addplant, infoofplant;
    plant p;

    public void setPlant(plant p){this.p=p;}
    public plant getPlant(){return p;}

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrieftxt() {
        return brieftxt;
    }
    public void setBrieftxt(String brieftxt) {
        this.brieftxt = brieftxt;
    }

    public String getAddplant() {
        return addplant;
    }
    public void setAddplant(String addplant) {
        this.addplant = addplant;
    }

    public String getInfoofplant() {
        return infoofplant;
    }
    public void setInfoofplant(String infoofplant) {
        this.infoofplant = infoofplant;
    }

}