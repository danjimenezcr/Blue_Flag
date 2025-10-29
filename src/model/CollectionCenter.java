package model;

import java.sql.Timestamp;
import java.util.Date; // if needed
public class CollectionCenter {

    private int autorizedEntityId;
    private String name;
    private Timestamp openHour;
    private Timestamp closeHour;
    private String manager;
    private String contact;
    private District district;
    private CenterType centerType;
    private String createdBy;
    private Date createdDateTime;
    private String updatedBy;
    private Date updatedDateTime;

    public CollectionCenter() {
    }

    public CollectionCenter(Integer autorizedEntityId, String name, Timestamp openHour, Timestamp closeHour, String manager, String contact, CenterType centerType, String createdBy, Date createdDateTime, String updatedBy, Date updatedDateTime, District district)
    {
        this.autorizedEntityId = autorizedEntityId;
        this.name = name;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.manager = manager;
        this.contact = contact;
        this.district = district;
        this.centerType = centerType;
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

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public CenterType getCenterType() {
        return centerType;
    }

    public void setCenterType(CenterType centerType) {
        this.centerType = centerType;
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
}
