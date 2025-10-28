
package model;

import java.util.Date; // if needed

public class PointsConvertion {
    private int id;
    private int name;
    private String updatedBy;
    private Date updatedDateTime;
    private String createdBy;
    private Date createdDateTime;
    private double pointsPerKg;
    private double valueInCurrency;
    private int currencyId;
    private int materialTypeId;

    public PointsConvertion() {}

    public PointsConvertion(int id, int name, String updatedBy, Date updatedDateTime, String createdBy, Date createdDateTime, double pointsPerKg, double valueInCurrency, int currencyId, int materialTypeId) 
    {
        this.id = id;
        this.name = name;
        this.updatedBy = updatedBy;
        this.updatedDateTime = updatedDateTime;
        this.createdBy = createdBy;
        this.createdDateTime = createdDateTime;
        this.pointsPerKg = pointsPerKg;
        this.valueInCurrency = valueInCurrency;
        this.currencyId = currencyId;
        this.materialTypeId = materialTypeId;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
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

    public double getPointsPerKg() {
        return pointsPerKg;
    }

    public void setPointsPerKg(double pointsPerKg) {
        this.pointsPerKg = pointsPerKg;
    }

    public double getValueInCurrency() {
        return valueInCurrency;
    }

    public void setValueInCurrency(double valueInCurrency) {
        this.valueInCurrency = valueInCurrency;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public int getMaterialTypeId() {
        return materialTypeId;
    }

    public void setMaterialTypeId(int materialTypeId) {
        this.materialTypeId = materialTypeId;
    }
}
