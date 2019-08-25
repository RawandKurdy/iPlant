package e.marsgroup.iplant.Database;

/**
 * Created by rawan on 3/18/2018.
 */

public class planted {
    int id;
    int idofPlant;
    long startEpochTime; //when the user clicks on the add plant button
    long estimatedEndEpochTime; //estimated time+current time(startEpochTime)

    public planted() {
    }

    public planted(int id, int idofPlant, long startEpochTime, long estimatedEndEpochTime) {
        this.id = id;
        this.idofPlant = idofPlant;
        this.startEpochTime = startEpochTime;
        this.estimatedEndEpochTime = estimatedEndEpochTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdofPlant() {
        return idofPlant;
    }

    public void setIdofPlant(int idofPlant) {
        this.idofPlant = idofPlant;
    }

    public long getStartEpochTime() {
        return startEpochTime;
    }

    public void setStartEpochTime(long startEpochTime) {
        this.startEpochTime = startEpochTime;
    }

    public long getEstimatedEndEpochTime() {
        return estimatedEndEpochTime;
    }

    public void setEstimatedEndEpochTime(long estimatedEndEpochTime) {
        this.estimatedEndEpochTime = estimatedEndEpochTime;
    }
}
