Import java.util.Date;

public class Currency {
    private int id;
    private String code;
    private String updatedBy;
    private Date updatedDateTime;
    private String createdBy;
    private Date createdDateTime;
    private String symbol;

    public Currency() {}

    public Currency(int id, String code, String updatedBy, Date updatedDateTime, String createdBy, Date createdDateTime, String symbol) {
        this.id = id;
        this.code = code;
        this.updatedBy = updatedBy;
        this.updatedDateTime = updatedDateTime;
        this.createdBy = createdBy;
        this.createdDateTime = createdDateTime;
        this.symbol = symbol;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
