package model;

import java.util.Date; 

public class City {

    private int id;
    private String name;
    private Date createdDateTime;
    private String createdBy;
    private Date updatedDateTime;
    private String updatedBy;
    private int provinceId;

    public City() {
    }

    public City(int id, String name, Date createdDateTime, String createdBy,
                Date updatedDateTime, String updatedBy, int provinceId) {
        this.id = id;
        this.name = name;
        this.createdDateTime = createdDateTime;
        this.createdBy = createdBy;
        this.updatedDateTime = updatedDateTime;
        this.updatedBy = updatedBy;
        this.provinceId = provinceId;
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

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
