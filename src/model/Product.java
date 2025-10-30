package model;

import java.util.Date; 

public class Product {

    private int id;
    private String description;
    private String photoUrl;
    private double cost;
    private Date createdDateTime;
    private String createdBy;
    private Date updatedDateTime;
    private String updatedBy;
    private AutorizedEntity autorizedEntity;

    public Product() {
    }

    public Product(int id, String description, String photoUrl, double cost,
                   Date createdDateTime, String createdBy, Date updatedDateTime,
                   String updatedBy, AutorizedEntity autorizedEntity) {
        this.id = id;
        this.description = description;
        this.photoUrl = photoUrl;
        this.cost = cost;
        this.createdDateTime = createdDateTime;
        this.createdBy = createdBy;
        this.updatedDateTime = updatedDateTime;
        this.updatedBy = updatedBy;
        this.autorizedEntity = autorizedEntity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    public AutorizedEntity getAutorizedEntity() {
        return autorizedEntity;
    }

    public void setAutorizedEntity(AutorizedEntity autorizedEntity) {
        this.autorizedEntity = autorizedEntity;
    }

    @Override
    public String toString() {
        return   description; }
}
