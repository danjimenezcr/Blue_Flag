package model;

import java.nio.file.FileStore;
import java.sql.Timestamp;
import java.util.Date;

public class AffiliatedBusiness {

    private int autorizedEntityId; 
    private String name;
    private Timestamp openHour;
    private Timestamp closeHour;
    private String manager;
    private String contact;
    private District district;
    private BusinessType businessType;
    private String createdBy;
    private Date createdDateTime;
    private String updatedBy;
    private Date updatedDateTime;

    public AffiliatedBusiness() {
    }


    public AffiliatedBusiness(int autorizedEntityId, String name, Timestamp openHour, Timestamp closeHour,
                              String manager, String contact, District district, BusinessType businessType,
                              String createdBy, Date createdDateTime, String updatedBy, Date updatedDateTime) {
        this.autorizedEntityId = autorizedEntityId;
        this.name = name;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.manager = manager;
        this.contact = contact;
        this.district = district;
        this.businessType = businessType;
        this.createdBy = createdBy;
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

    public String getName() { 
        return name; 
    }

    public void setName(String name) {
         this.name = name; 
    }

    public Timestamp getOpenHour() {
        return openHour; 
    }

    public void setOpenHour(Timestamp openHour) {
        this.openHour = openHour; 
    }

    public Timestamp getCloseHour() {
        return closeHour; 
    }

    public void setCloseHour(Timestamp closeHour) {
        this.closeHour = closeHour; 
    }

    public String getManager() { 
        return manager; 
    }

    public void setManager(String manager) { 
        this.manager = manager; 
    }

    public String getContact() { 
        return contact; 
    }

    public void setContact(String contact) { 
        this.contact = contact; 
    }

    public District getDistrictId() {
        return district;
    }

    public void setDistrictId(District district) {
        this.district = district;
    }

    public BusinessType getBusinessTypeId() {
        return businessType;
    }
    public void setBusinessTypeId(BusinessType businessType) {
        this.businessType = businessType;
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

    @Override
    public String toString() {
        return "AffiliatedBusiness{" +
                "autorizedEntityId=" + autorizedEntityId +
                ", name='" + name + '\'' +
                ", openHour=" + openHour +
                ", closeHour=" + closeHour +
                ", manager='" + manager + '\'' +
                ", contact='" + contact + '\'' +
                ", district=" + (district != null ? district.toString() : "null") +
                ", businessType=" + (businessType != null ? businessType.toString() : "null") +
                ", createdBy='" + createdBy + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDateTime=" + updatedDateTime +
                '}';
    }

}

