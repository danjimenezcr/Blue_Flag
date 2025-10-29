package model;

import java.util.Date; // if needed

public class MaterialTypeXCollectionCenter {
    private int id;
    private AutorizedEntity authorizedEntity;
    private String createdBy;
    private Date createdDateTime;
    private String updatedBy;
    private Date updatedDateTime;
    private MaterialType materialType;

    public MaterialTypeXCollectionCenter() {}

    public MaterialTypeXCollectionCenter(int id, AutorizedEntity authorizedEntity, String createdBy, Date createdDateTime, String updatedBy, Date updatedDateTime, MaterialType materialType)
    {
        this.id = id;
        this.authorizedEntity = authorizedEntity;
        this.createdBy = createdBy;
        this.createdDateTime = createdDateTime;
        this.updatedBy = updatedBy;
        this.updatedDateTime = updatedDateTime;
        this.materialType = materialType;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AutorizedEntity getAuthorizedEntity() {
        return authorizedEntity;
    }

    public void setAuthorizedEntity(AutorizedEntity authorizedEntity) {
        this.authorizedEntity = authorizedEntity;
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

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    @Override
    public String toString() {
        return "MaterialTypeXCollectionCenter{" +
                "id=" + id +
                ", authorizedEntity=" + (authorizedEntity != null ? authorizedEntity.toString() : "null") +
                ", materialType=" + (materialType != null ? materialType.toString() : "null") +
                ", createdBy='" + createdBy + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDateTime=" + updatedDateTime +
                '}';
    }

}