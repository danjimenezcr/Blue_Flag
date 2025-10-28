package model;

import java.util.Date; // if needed

public class Labels {

    private int id;
    private String description;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;

    public Labels() {
    }

    public Labels(int id, String description, String createdBy, Date createdDate,
                  String updatedBy, Date updatedDate) {
        this.id = id;
        this.description = description;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
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

    public String getCreatedBy() {
        return this.createdBy;
    }}
