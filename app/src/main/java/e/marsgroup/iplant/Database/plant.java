package e.marsgroup.iplant.Database;


/**
 * Created by rawan on 3/18/2018.
 */

public class plant {
   int id;
   String name;
   String info;
   String waterinfo;
   String season;
   String imgurl;
   String moreinfoURL;
   long estimatedEpochSeconds; //Estimated Time to finish

    public plant() {
    }

    public plant(int id, String name, String info,String waterinfo, String season, String imgurl, String moreinfoURL, long estimatedEpochSeconds) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.waterinfo=waterinfo;
        this.season = season;
        this.imgurl = imgurl;
        this.moreinfoURL = moreinfoURL;
        this.estimatedEpochSeconds = estimatedEpochSeconds;
    }

    public String getWaterinfo() {
        return waterinfo;
    }

    public void setWaterinfo(String waterinfo) {
        this.waterinfo = waterinfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getMoreinfoURL() {
        return moreinfoURL;
    }

    public void setMoreinfoURL(String moreinfoURL) {
        this.moreinfoURL = moreinfoURL;
    }

    public long getEstimatedEpochSeconds() {
        return estimatedEpochSeconds;
    }

    public void setEstimatedEpochSeconds(long estimatedEpochSeconds) {
        this.estimatedEpochSeconds = estimatedEpochSeconds;
    }
}
