Import java.util.Date;

public class District {

    private int id;
    private String name;
    private Date createdDateTime;
    private String createdBy;
    private Date updatedDateTime;
    private String updatedBy;
    private int cityId;

    public District() {
    }

    public District(int id, String name, Date createdDateTime, String createdBy,
                    Date updatedDateTime, String updatedBy, int cityId) {
        this.id = id;
        this.name = name;
        this.createdDateTime = createdDateTime;
        this.createdBy = createdBy;
        this.updatedDateTime = updatedDateTime;
        this.updatedBy = updatedBy;
        this.cityId = cityId;
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

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(Date updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}