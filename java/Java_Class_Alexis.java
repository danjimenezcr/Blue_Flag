Import java.util.Date;

/*public class AutorizedEntity {

    private int id;
    private String name;
    private String openHour;
    private String closeHour;
    private String manager;
    private String contact;
    private int districtId;

    public AutorizedEntity() {
    }

    public AutorizedEntity(int id, String name, String openHour, String closeHour,
                           String manager, String contact, int districtId) {
        this.id = id;
        this.name = name;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.manager = manager;
        this.contact = contact;
        this.districtId = districtId;
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

    public int getDistrictName() {
        return districtId;
    }

    public void setDistrictName(int districtId) {
        this.districtId = districtId;
    }
}*/

/*public class AffiliatedBusiness {

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
}*/

/*public class BusinessType {

    private int id;
    private String description;
    private String createdBy;
    private Date createdDateTime;
    private String updatedBy;
    private Date updatedDateTime;

    public BusinessType() {
    }

    public BusinessType(int id, String description, String createdBy, Date createdDateTime,
                        String updatedBy, Date updatedDateTime) {
        this.id = id;
        this.description = description;
        this.createdBy = createdBy;
        this.createdDateTime = createdDateTime;
        this.updatedBy = updatedBy;
        this.updatedDateTime = updatedDateTime;
    }

    // Getters y Setters
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
}*/

/*public class CollectionCenter {

    private int autorizedEntityId;
    private int centerTypeId;
    private String createdBy;
    private Date createdDateTime;
    private String updatedBy;
    private Date updatedDateTime;

    public CollectionCenter() {
    }

    public CollectionCenter(int autorizedEntityId, int centerTypeId, String createdBy, Date createdDateTime, String updatedBy, Date updatedDateTime) 
    {
        this.autorizedEntityId = autorizedEntityId;
        this.centerTypeId = centerTypeId;
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

    public int getCenterTypeId() {
        return centerTypeId;
    }

    public void setCenterTypeId(int centerTypeId) {
        this.centerTypeId = centerTypeId;
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
}*/

/*public class CenterType {
    private int id;
    private String description;
    private String createdBy;
    private Date createdDateTime;
    private String updatedBy;
    private Date updatedDateTime;

    public CenterType() {}

    public CenterType(int id, String description, String createdBy, Date createdDateTime, String updatedBy, Date updatedDateTime) {
        this.id = id;
        this.description = description;
        this.createdBy = createdBy;
        this.createdDateTime = createdDateTime;
        this.updatedBy = updatedBy;
        this.updatedDateTime = updatedDateTime;
    }

    // Getters y Setters

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
}*/

/*public class MaterialType {
    private int id;
    private String name;
    private String createdBy;
    private Date createdDateTime;
    private String updatedBy;
    private Date updatedDateTime;

    public MaterialType() {}

    public MaterialType(int id, String name, String createdBy, Date createdDateTime, String updatedBy, Date updatedDateTime) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.createdDateTime = createdDateTime;
        this.updatedBy = updatedBy;
        this.updatedDateTime = updatedDateTime;
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
}*/

/*public class MaterialTypeXCollectionCenter {
    private int id;
    private int autorizedEntityId;
    private String createdBy;
    private Date createdDateTime;
    private String updatedBy;
    private Date updatedDateTime;
    private int materialTypeId;

    public MaterialTypeXCollectionCenter() {}

    public MaterialTypeXCollectionCenter(int id, int autorizedEntityId, String createdBy, Date createdDateTime, String updatedBy, Date updatedDateTime, int materialTypeId) 
    {
        this.id = id;
        this.autorizedEntityId = autorizedEntityId;
        this.createdBy = createdBy;
        this.createdDateTime = createdDateTime;
        this.updatedBy = updatedBy;
        this.updatedDateTime = updatedDateTime;
        this.materialTypeId = materialTypeId;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getMaterialTypeId() {
        return materialTypeId;
    }

    public void setMaterialTypeId(int materialTypeId) {
        this.materialTypeId = materialTypeId;
    }
}*/

/*public class UserXCollectionCenter {
    private int id;
    private int userId;
    private String createdBy;
    private Date createdDateTime;
    private String updatedBy;
    private Date updatedDateTime;
    private int collectionCenter;
    private int pointsConvertionKey;
    private String kilograms;

    
    public UserXCollectionCenter() {}

    public UserXCollectionCenter(int id, int userId, String createdBy, Date createdDateTime, String updatedBy, Date updatedDateTime, int collectionCenter, int pointsConvertionKey, String kilograms) 
    {
        this.id = id;
        this.userId = userId;
        this.createdBy = createdBy;
        this.createdDateTime = createdDateTime;
        this.updatedBy = updatedBy;
        this.updatedDateTime = updatedDateTime;
        this.collectionCenter = collectionCenter;
        this.pointsConvertionKey = pointsConvertionKey;
        this.kilograms = kilograms;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getCollectionCenter() {
        return collectionCenter;
    }

    public void setCollectionCenter(int collectionCenter) {
        this.collectionCenter = collectionCenter;
    }

    public int getPointsConvertionKey() {
        return pointsConvertionKey;
    }

    public void setPointsConvertionKey(int pointsConvertionKey) {
        this.pointsConvertionKey = pointsConvertionKey;
    }

    public String getKilograms() {
        return kilograms;
    }

    public void setKilograms(String kilograms) {
        this.kilograms = kilograms;
    }
}*/

/*public class Currency {
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
}*/

/*public class PointsConvertion {
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
}*/


