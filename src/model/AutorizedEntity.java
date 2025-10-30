package model;

import java.sql.Timestamp;

public class AutorizedEntity {

    private int id;
    private String name;
    private Timestamp openHour;
    private Timestamp closeHour;
    private String manager;
    private String contact;
    private District district;

    public AutorizedEntity() {
    }

    public AutorizedEntity(int id, String name, Timestamp openHour, Timestamp closeHour,
                           String manager, String contact, District district) {
        this.id = id;
        this.name = name;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.manager = manager;
        this.contact = contact;
        this.district = district;
    }

    // Getters y Setters
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

    @Override
    public String toString()  {
        return name;
    }

}
