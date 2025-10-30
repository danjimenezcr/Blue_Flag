package model;

import java.util.Date; // if needed


public class UserXCollectionCenter {
    private int id;
    private User user;
    private String createdBy;
    private Date createdDateTime;
    private String updatedBy;
    private Date updatedDateTime;
    private CollectionCenter collectionCenter;
    private PointsConvertion pointsConvertion;
    private int kilograms;

    
    public UserXCollectionCenter() {}

    public UserXCollectionCenter(int id, User user, String createdBy, Date createdDateTime, String updatedBy, Date updatedDateTime, CollectionCenter collectionCenter, PointsConvertion pointsConvertion, int kilograms)
    {
        this.id = id;
        this.user = user;
        this.createdBy = createdBy;
        this.createdDateTime = createdDateTime;
        this.updatedBy = updatedBy;
        this.updatedDateTime = updatedDateTime;
        this.collectionCenter = collectionCenter;
        this.pointsConvertion = pointsConvertion;
        this.kilograms = kilograms;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return user;
    }

    public void setUserId(User user) {
        this.user = user;
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

    public CollectionCenter getCollectionCenter() {
        return collectionCenter;
    }

    public void setCollectionCenter(CollectionCenter collectionCenter) {
        this.collectionCenter = collectionCenter;
    }

    public PointsConvertion getPointsConvertion() {
        return pointsConvertion;
    }

    public void setPointsConvertion(PointsConvertion pointsConvertion) {
        this.pointsConvertion = pointsConvertion;
    }

    public int getKilograms() {
        return kilograms;
    }

    public void setKilograms(int kilograms) {
        this.kilograms = kilograms;
    }

    public String toString() {
        return "id=" + id;
    }

}
