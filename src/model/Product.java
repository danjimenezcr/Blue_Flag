package model;

import java.util.Date; // if needed

public class Product {

    private int id;
    private String description;
    private String photoUrl;
    private double cost;
    private Date createdDateTime;
    private String createdBy;
    private Date updatedDateTime;
    private String updatedBy;
    private int authorizedEntityId;

    public Product() {
    }

    public Product(int id, String description, String photoUrl, double cost,
                   Date createdDateTime, String createdBy, Date updatedDateTime,
                   String updatedBy, int authorizedEntityId) {
        this.id = id;
        this.description = description;
        this.photoUrl = photoUrl;
        this.cost = cost;
        this.createdDateTime = createdDateTime;
        this.createdBy = createdBy;
        this.updatedDateTime = updatedDateTime;
        this.updatedBy = updatedBy;
        this.authorizedEntityId = authorizedEntityId;
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

    public int getAuthorizedEntityId() {
        return authorizedEntityId;
    }

    public void setAuthorizedEntityId(int authorizedEntityId) {
        this.authorizedEntityId = authorizedEntityId;
    }
}
