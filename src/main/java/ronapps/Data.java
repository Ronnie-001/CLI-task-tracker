package ronapps;

public class Data {

    private int id;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;


    public Data(int id, String description, String createdAt) {
        this.id = id;
        this.description = description;
        this.status = "todo";
        this.createdAt = createdAt;
    }

    public int getID() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStatus() {
        return this.status;
    }

    public String getTimeCreated() {
        return this.createdAt;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }
}
