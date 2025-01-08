package ronapps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

    private int id;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;


    public Data(int id ,String description, String createdAt) {
        this.id = id;
        this.description = description;
        this.status = "todo";
        this.createdAt = createdAt;
    }

    public Data () {};

    public int getID() {
        return this.id;
    }

    public void setID(int newID) {
        this.id = newID;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    public String getTimeCreated() {
        return this.createdAt;
    }

    public void setUpdateTime(String newTime) {
        this.updatedAt = newTime;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }
}
