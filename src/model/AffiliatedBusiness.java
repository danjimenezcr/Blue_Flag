package model;

import java.util.Date;

public class AffiliatedBusiness {

    private int autorizedEntityId; 
    private String name;
    private String openHour;
    private String closeHour;
    private String manager;
    private String contact;
    private int districtId;
    private int businessTypeId;
    private String createdBy;
    private Date createdDateTime;
    private String updatedBy;
    private Date updatedDateTime;

    public AffiliatedBusiness() {
    }


    public AffiliatedBusiness(int autorizedEntityId, String name, String openHour, String closeHour,
                              String manager, String contact, int districtId, int businessTypeId,
                              String createdBy, Date createdDateTime, String updatedBy, Date updatedDateTime) {
        this.autorizedEntityId = autorizedEntityId;
        this.name = name;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.manager = manager;
        this.contact = contact;
        this.districtId = districtId;
        this.businessTypeId = businessTypeId;
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

    public String getOpenHour() { 
        return openHour; 
    }

    public void setOpenHour(String openHour) { 
        this.openHour = openHour; 
    }

    public String getCloseHour() { 
        return closeHour; 
    }

    public void setCloseHour(String closeHour) { 
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

    public int getDistrictId() { 
        return districtId; 
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId; 
    }

    public int getBusinessTypeId() { 
        return businessTypeId;
    }
    public void setBusinessTypeId(int businessTypeId) { 
        this.businessTypeId = businessTypeId; 
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

