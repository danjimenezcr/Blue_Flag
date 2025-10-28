package model;

import java.util.Date;

public class AffiliatedBusiness {

    private int autorizedEntityId;
    private String createdBy;
    private int businessTypeId;
    private Date createdDateTime;
    private String updatedBy;
    private Date updatedDateTime;

    // Constructor vacío
    public AffiliatedBusiness() {
    }

    // Constructor con parámetros
    public AffiliatedBusiness(int autorizedEntityId, String createdBy, int businessTypeId,
                              Date createdDateTime, String updatedBy, Date updatedDateTime) {
        this.autorizedEntityId = autorizedEntityId;
        this.createdBy = createdBy;
        this.businessTypeId = businessTypeId;
        this.createdDateTime = createdDateTime;
        this.updatedBy = updatedBy;
        this.updatedDateTime = updatedDateTime;
    }

    // Getters y Setters
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

    public int getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(int businessTypeId) {
        this.businessTypeId = businessTypeId;
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
}
