package model;

import java.util.Date; // if needed


public class UserXCollectionCenter {
    private int id;
    private int userId;
    private String createdBy;
    private Date createdDateTime;
    private String updatedBy;
    private Date updatedDateTime;
    private int collectionCenter;
    private int pointsConvertionKey;
    private String kilograms;

    
    public UserXCollectionCenter() {}

    public UserXCollectionCenter(int id, int userId, String createdBy, Date createdDateTime, String updatedBy, Date updatedDateTime, int collectionCenter, int pointsConvertionKey, String kilograms) 
    {
        this.id = id;
        this.userId = userId;
        this.createdBy = createdBy;
        this.createdDateTime = createdDateTime;
        this.updatedBy = updatedBy;
        this.updatedDateTime = updatedDateTime;
        this.collectionCenter = collectionCenter;
        this.pointsConvertionKey = pointsConvertionKey;
        this.kilograms = kilograms;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getCollectionCenter() {
        return collectionCenter;
    }

    public void setCollectionCenter(int collectionCenter) {
        this.collectionCenter = collectionCenter;
    }

    public int getPointsConvertionKey() {
        return pointsConvertionKey;
    }

    public void setPointsConvertionKey(int pointsConvertionKey) {
        this.pointsConvertionKey = pointsConvertionKey;
    }

    public String getKilograms() {
        return kilograms;
    }

    public void setKilograms(String kilograms) {
        this.kilograms = kilograms;
    }
}
