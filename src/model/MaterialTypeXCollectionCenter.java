Import java.util.Date;

public class MaterialTypeXCollectionCenter {
    private int id;
    private int autorizedEntityId;
    private String createdBy;
    private Date createdDateTime;
    private String updatedBy;
    private Date updatedDateTime;
    private int materialTypeId;

    public MaterialTypeXCollectionCenter() {}

    public MaterialTypeXCollectionCenter(int id, int autorizedEntityId, String createdBy, Date createdDateTime, String updatedBy, Date updatedDateTime, int materialTypeId) 
    {
        this.id = id;
        this.autorizedEntityId = autorizedEntityId;
        this.createdBy = createdBy;
        this.createdDateTime = createdDateTime;
        this.updatedBy = updatedBy;
        this.updatedDateTime = updatedDateTime;
        this.materialTypeId = materialTypeId;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAutorizedEntityId() {
        return autorizedEntityId;
    }

    public void setAutorizedEntityId(int autorizedEntityId) {
        this.autorizedEntityId = autorizedEntityId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(Date updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public int getMaterialTypeId() {
        return materialTypeId;
    }

    public void setMaterialTypeId(int materialTypeId) {
        this.materialTypeId = materialTypeId;
    }
}