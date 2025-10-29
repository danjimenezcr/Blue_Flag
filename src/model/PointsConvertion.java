
package model;

import java.util.Date; // if needed

public class PointsConvertion {
    private int id;
    private String name;
    private String updatedBy;
    private Date updatedDateTime;
    private String createdBy;
    private Date createdDateTime;
    private double pointsPerKg;
    private double valueInCurrency;
    private Currency currency;
    private MaterialType materialType;

    public PointsConvertion() {}

    public PointsConvertion(int id, String name, String updatedBy, Date updatedDateTime, String createdBy, Date createdDateTime, double pointsPerKg, double valueInCurrency, Currency currency, MaterialType materialType)
    {
        this.id = id;
        this.name = name;
        this.updatedBy = updatedBy;
        this.updatedDateTime = updatedDateTime;
        this.createdBy = createdBy;
        this.createdDateTime = createdDateTime;
        this.pointsPerKg = pointsPerKg;
        this.valueInCurrency = valueInCurrency;
        this.currency = currency;
        this.materialType = materialType;
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

    public Currency getCurrencyId() {
        return currency;
    }

    public void setCurrencyId(Currency currency) {
        this.currency = currency;
    }

    public MaterialType getMaterialTypeId() {
        return materialType;
    }

    public void setMaterialTypeId(MaterialType materialType) {
        this.materialType = materialType;
    }

    @Override
    public String toString() {
        return "PointsConvertion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pointsPerKg=" + pointsPerKg +
                ", valueInCurrency=" + valueInCurrency +
                ", currency=" + (currency != null ? currency.toString() : "null") +
                ", materialType=" + (materialType != null ? materialType.toString() : "null") +
                ", createdBy='" + createdBy + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDateTime=" + updatedDateTime +
                '}';
    }

}
