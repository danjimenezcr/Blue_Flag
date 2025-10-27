ALTER SESSION SET CURRENT_SCHEMA = BLUE;

-- Triggers table users
CREATE OR REPLACE TRIGGER beforeInsertUser
    BEFORE INSERT
    ON USERS
    FOR EACH ROW
    BEGIN
        :NEW.createdBy := USER;
        :NEW.createdDate := SYSDATE;

        BLUE.ChangeLogger.LogChange('ID', NULL, :NEW.id, 'INSERT', 'USERS', :NEW.id);
    END;
/
CREATE OR REPLACE TRIGGER beforeUpdateUser
    BEFORE UPDATE
    ON Users
    FOR EACH ROW
    BEGIN
        :NEW.updatedBy := USER;
        :NEW.updatedDate := SYSDATE;
    END;
/
CREATE OR REPLACE TRIGGER beforeUpdateUserPassword
    BEFORE UPDATE OF password
    ON USERS
    FOR EACH ROW
    BEGIN
        ChangeLogger.LogChange('PASSWORD', :OLD.password, :NEW.password, 'UPDATE', 'USERS', :NEW.id);
    END;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdateUserIdNumber
    BEFORE UPDATE OF idNumber
    ON USERS
    FOR EACH ROW
    BEGIN
        ChangeLogger.LogChange('IDNUMBER', :OLD.idNumber, :NEW.idNumber, 'UPDATE', 'USERS', :NEW.id);
    END;
/
CREATE OR REPLACE TRIGGER beforeUpdateUserFirstName
    BEFORE UPDATE OF firstName
    ON USERS
    FOR EACH ROW
    BEGIN
        ChangeLogger.LogChange('FIRSTNAME', :OLD.firstName, :NEW.firstName, 'UPDATE', 'USERS', :NEW.id);
    END;
/

-- Triggers table phones
CREATE OR REPLACE TRIGGER beforeInsertPhone
    BEFORE INSERT
    ON Phones
    FOR EACH ROW
    BEGIN
        :NEW.createdBy := USER;
        :NEW.createdDate := SYSDATE;

        ChangeLogger.LogChange('ID', NULL, :NEW.id, 'INSERT', 'PHONES', :NEW.id);
END beforeInsertPhone;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdatePhone
    BEFORE UPDATE
    ON Phones
    FOR EACH ROW
    BEGIN
        :NEW.updatedBy := USER;
        :NEW.updatedDate := SYSDATE;
    END;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdatePhoneNumber
    BEFORE UPDATE OF phoneNumber
    ON Phones
    FOR EACH ROW
    BEGIN
        ChangeLogger.LogChange('PHONENUMBER', :OLD.phoneNumber, :NEW.phoneNumber, 'UPDATE', 'PHONES', :NEW.id);
    END;
/

-- Triggers table emails
CREATE OR REPLACE TRIGGER BLUE.beforeInsertEmail
    BEFORE INSERT
    ON Emails
    FOR EACH ROW
    BEGIN
        :NEW.createdBy := USER;
        :NEW.createdDate := SYSDATE;

        ChangeLogger.LogChange('ID', NULL, :NEW.id, 'INSERT', 'EMAILS', :NEW.id);
    END;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdateEmail
    BEFORE UPDATE
    ON Emails
    FOR EACH ROW
    BEGIN
        :NEW.updatedBy := USER;
        :NEW.updatedDate := SYSDATE;
    END;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdateEmailAddress
    BEFORE UPDATE OF emailAddress
    ON Emails
    FOR EACH ROW
    BEGIN
        ChangeLogger.LogChange('EMAILADDRESS', :OLD.emailAddress, :NEW.emailAddress, 'UPDATE', 'EMAILS', :NEW.id);
    END;
/

-- Triggers table gender
CREATE OR REPLACE TRIGGER BLUE.beforeInsertGender
    BEFORE INSERT
    ON Genders
    FOR EACH ROW
    BEGIN
        :NEW.createdBy := USER;
        :NEW.createdDate := SYSDATE;

        ChangeLogger.LogChange('ID', NULL, :NEW.id, 'INSERT', 'GENDERS', :NEW.id);
    END;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdateGender
    BEFORE UPDATE
    ON Genders
    FOR EACH ROW
    BEGIN
        :NEW.updatedBy := USER;
        :NEW.updatedDate := SYSDATE;
    END;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdateGenderName
    BEFORE UPDATE OF name
    ON Genders
    FOR EACH ROW
    BEGIN
        ChangeLogger.LogChange('NAME', :OLD.name, :NEW.name, 'UPDATE', 'GENDERS', :NEW.id);
    END;

-- Triggers table UserType
CREATE OR REPLACE TRIGGER BLUE.beforeInsertUserType
    BEFORE INSERT
    ON UserTypes
    FOR EACH ROW
    BEGIN
        :NEW.createdBy := USER;
        :NEW.createdDate := SYSDATE;

        ChangeLogger.LogChange('ID', NULL, :NEW.id, 'INSERT', 'USERTYPES', :NEW.id);
    END;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdateUserType
    BEFORE UPDATE
    ON UserTypes
    FOR EACH ROW
    BEGIN
        :NEW.updatedBy := USER;
        :NEW.updatedDate := SYSDATE;
    END;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdateUserTypeName
    BEFORE UPDATE OF name
    ON UserTypes
    FOR EACH ROW
    BEGIN
        ChangeLogger.LogChange('NAME', :OLD.name, :NEW.name, 'UPDATE', 'USERTYPE', :NEW.id);
    END;
/
-- Triggers table IdType
CREATE OR REPLACE TRIGGER beforeInsertIdType
    BEFORE INSERT
    ON IDTYPE
    FOR EACH ROW
    BEGIN
        :NEW.createdBy := USER;
        :NEW.createdDate := SYSDATE;

        BLUE.ChangeLogger.LogChange('ID', NULL, :NEW.id, 'INSERT', 'USERTYPES', :NEW.id);
    END;
/
CREATE OR REPLACE TRIGGER beforeUpdateIdType
    BEFORE UPDATE
    ON IdType
    FOR EACH ROW
    BEGIN
        :NEW.updatedBy := USER;
        :NEW.updatedDate := SYSDATE;
    END;
/
CREATE OR REPLACE TRIGGER beforeUpdateIdTypeName
    BEFORE UPDATE OF name
    ON IDTYPE
    FOR EACH ROW
    BEGIN
        ChangeLogger.LogChange('NAME', :OLD.name, :NEW.name, 'UPDATE', 'IDTYPE', :NEW.id);
    END;
/
-- Triggers table Label
CREATE OR REPLACE TRIGGER BLUE.beforeInsertLabel
    BEFORE INSERT
    ON BLUE.Labels
    FOR EACH ROW
    BEGIN
        :NEW.createdBy := USER;
        :NEW.createdDate := SYSDATE;

        BLUE.ChangeLogger.LogChange('ID', NULL, :NEW.id, 'INSERT', 'LABELS', :NEW.id);
    END;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdateLabel
    BEFORE UPDATE
    ON BLUE.Labels
    FOR EACH ROW
    BEGIN
        :NEW.updatedBy := USER;
        :NEW.updatedDate := SYSDATE;
    END;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdateLabelDescription
    BEFORE UPDATE OF description
    ON BLUE.Labels
    FOR EACH ROW
    BEGIN
        BLUE.ChangeLogger.LogChange('DESCRIPTION', :OLD.description, :NEW.description, 'UPDATE', 'LABELS', :NEW.id);
    END;
/

CREATE OR REPLACE TRIGGER BLUE.beforeInsertDistrict
BEFORE INSERT
ON BLUE.DISTRICT
FOR EACH ROW
BEGIN
    :NEW.createdBy := USER;
    :NEW.createdDateTime := SYSDATE;

    BLUE.ChangeLogger.LogChange('ID', NULL, :NEW.id, 'INSERT', 'DISTRICT', :NEW.id);
END beforeInsertDistrict;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdateDisctrict
BEFORE UPDATE
ON BLUE.DISTRICT
FOR EACH ROW
BEGIN
    :NEW.updatedBy := USER;
    :NEW.updatedDateTime := SYSDATE;

END beforeUpdateDisctrict;
/
CREATE OR REPLACE TRIGGER BLUE.beforeInsertCity
BEFORE INSERT
ON BLUE.City
FOR EACH ROW
BEGIN
    :NEW.createdBy := USER;
    :NEW.createdDateTime := SYSDATE;

    BLUE.ChangeLogger.LogChange('ID', NULL, :NEW.id, 'INSERT', 'CITY', :NEW.id);
END beforeInsertCity;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdateCity
BEFORE UPDATE
ON BLUE.City
FOR EACH ROW
BEGIN
    :NEW.updatedBy := USER;
    :NEW.updatedDateTime := SYSDATE;

END beforeUpdateCity;
/
CREATE OR REPLACE TRIGGER BLUE.beforeInsertProvince
BEFORE INSERT
ON BLUE.Province
FOR EACH ROW
BEGIN
    :NEW.createdBy := USER;
    :NEW.createdDateTime := SYSDATE;

    BLUE.ChangeLogger.LogChange('ID', NULL, :NEW.id, 'INSERT', 'PROVINCE', :NEW.id);
END beforeInsertProvince;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdateProvince
BEFORE UPDATE
ON BLUE.Province
FOR EACH ROW
BEGIN
    :NEW.updatedBy := USER;
    :NEW.updatedDateTime := SYSDATE;
END beforeUpdateProvince;
/
CREATE OR REPLACE TRIGGER BLUE.beforeInsertCountry
BEFORE INSERT
ON BLUE.Country
FOR EACH ROW
BEGIN
    :NEW.createdBy := USER;
    :NEW.createdDateTime := SYSDATE;

    BLUE.ChangeLogger.LogChange('ID', NULL, :NEW.id, 'INSERT', 'COUNTRY', :NEW.id);
END beforeInsertCountry;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdateCountry
BEFORE UPDATE
ON BLUE.Country
FOR EACH ROW
BEGIN
    :NEW.updatedBy := USER;
    :NEW.updatedDateTime := SYSDATE;

END beforeUpdateCountry;
/
CREATE OR REPLACE TRIGGER BLUE.beforeInsertProduct
BEFORE INSERT
ON BLUE.Product
FOR EACH ROW
BEGIN
    :NEW.createdBy := USER;
    :NEW.createdDateTime := SYSDATE;

    BLUE.ChangeLogger.LogChange('ID', NULL, :NEW.id, 'INSERT', 'PRODUCT', :NEW.id);
END beforeInsertProduct;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdateProduct
BEFORE UPDATE
ON BLUE.Product
FOR EACH ROW
BEGIN
    :NEW.updatedBy := USER;
    :NEW.updatedDateTime := SYSDATE;
END beforeUpdateProduct;
/
CREATE OR REPLACE TRIGGER BLUE.beforeInsertProductXUser
BEFORE INSERT
ON BLUE.ProductXUser
FOR EACH ROW
BEGIN
    :NEW.createdBy := USER;
    :NEW.createdDateTime := SYSDATE;

    BLUE.ChangeLogger.LogChange('ID', NULL, :NEW.id, 'INSERT', 'PRODUCTXUSER', :NEW.id);
END beforeInsertProductXUser;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdateProductXUser
BEFORE UPDATE
ON BLUE.ProductXUser
FOR EACH ROW
BEGIN
    :NEW.updatedBy := USER;
    :NEW.updatedDateTime := SYSDATE;

END beforeUpdateProductXUser;
/
CREATE OR REPLACE TRIGGER BLUE.beforeInsertPointsConvertion
BEFORE INSERT
ON BLUE.PointsConvertion
FOR EACH ROW
BEGIN
    :NEW.createdBy := USER;
    :NEW.createdDateTime := SYSDATE;

    BLUE.ChangeLogger.LogChange('ID', NULL, :NEW.id, 'INSERT', 'POINTSCONVERTION', :NEW.id);
END beforeInsertPointsConvertion;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdatePointsConvertion
BEFORE UPDATE
ON BLUE.PointsConvertion
FOR EACH ROW
BEGIN
    :NEW.updatedBy := USER;
    :NEW.updatedDateTime := SYSDATE;

END beforeUpdatePointsConvertion;
/
CREATE OR REPLACE TRIGGER BLUE.beforeInsertCurrency
BEFORE INSERT
ON BLUE.Currency
FOR EACH ROW
BEGIN
    :NEW.createdBy := USER;
    :NEW.createdDateTime := SYSDATE;

    BLUE.ChangeLogger.LogChange('ID', NULL, :NEW.id, 'INSERT', 'CURRENCY', :NEW.id);
END beforeInsertCurrency;
/
CREATE OR REPLACE TRIGGER BLUE.beforeUpdateCurrency
BEFORE UPDATE
ON BLUE.Currency
FOR EACH ROW
BEGIN
    :NEW.updatedBy := USER;
    :NEW.updatedDateTime := SYSDATE;

END beforeUpdateCurrency;
/
--Creation Triggers--
CREATE OR REPLACE TRIGGER beforeInsertABusiness
BEFORE INSERT
ON BLUE.AFFILIATEDBUSINESS
FOR EACH ROW
BEGIN
     :new.createdBy := USER;
     :new.createdDateTime := SYSDATE;
END beforeInsertABusiness;
/
CREATE OR REPLACE TRIGGER beforeBusinessType
BEFORE INSERT
ON BusinessType
FOR EACH ROW
BEGIN
     :new.createdBy := USER;
     :new.createdDateTime := SYSDATE;
END beforeBusinessType;
/
CREATE OR REPLACE TRIGGER beforeCollectionCenter
BEFORE INSERT
ON CollectionCenter
FOR EACH ROW
BEGIN
     :new.createdBy := USER;
     :new.createdDateTime := SYSDATE;
END beforeCollectionCenter;
/
CREATE OR REPLACE TRIGGER beforeCenterType
BEFORE INSERT
ON CenterType
FOR EACH ROW
BEGIN
     :new.createdBy := USER;
     :new.createdDateTime := SYSDATE;
END beforeCenterType;
/
CREATE OR REPLACE TRIGGER beforeMaterialType
BEFORE INSERT
ON MaterialType
FOR EACH ROW
BEGIN
     :new.createdBy := USER;
     :new.createdDateTime := SYSDATE;
END beforeMaterialType;
/
CREATE OR REPLACE TRIGGER beforeTMXCenter
BEFORE INSERT
ON MaterialTypeXCollectionCenter
FOR EACH ROW
BEGIN
     :new.createdBy := USER;
     :new.createdDateTime := SYSDATE;
END beforeTMXCenter;
/
CREATE OR REPLACE TRIGGER beforeUserXCollectionCenter
BEFORE INSERT
ON UserXCollectionCenter
FOR EACH ROW
BEGIN
     :new.createdBy := USER;
     :new.createdDateTime := SYSDATE;
END beforeUserXCollectionCenter;
/
CREATE OR REPLACE TRIGGER beforeUpdateAfBusiness
BEFORE UPDATE
ON AffiliatedBusiness
FOR EACH ROW
BEGIN
     :new.updatedBy := USER;
     :new.updatedDateTime := SYSDATE;
END beforeUpdateAfBusiness;
/
CREATE OR REPLACE TRIGGER beforeUpdateBusinessType
BEFORE UPDATE
ON BusinessType
FOR EACH ROW
BEGIN
     :new.updatedBy := USER;
     :new.updatedDateTime := SYSDATE;
END beforeUpdateBusinessType;
/
CREATE OR REPLACE TRIGGER beforeUpdateCollectionCenter
BEFORE UPDATE
ON CollectionCenter
FOR EACH ROW
BEGIN
     :new.updatedBy := USER;
     :new.updatedDateTime := SYSDATE;
END beforeUpdateCollectionCenter;
/
CREATE OR REPLACE TRIGGER beforeUpdateCenterType
BEFORE UPDATE
ON CenterType
FOR EACH ROW
BEGIN
     :new.updatedBy := USER;
     :new.updatedDateTime := SYSDATE;
END beforeUpdateCenterType;
/
CREATE OR REPLACE TRIGGER beforeUpdateMaterialType
BEFORE UPDATE
ON MaterialType
FOR EACH ROW
BEGIN
     :new.updatedBy := USER;
     :new.updatedDateTime := SYSDATE;
END beforeUpdateMaterialType;
/
CREATE OR REPLACE TRIGGER beforeUpdateTMXCenter
BEFORE UPDATE
ON MaterialTypeXCollectionCenter
FOR EACH ROW
BEGIN
     :new.updatedBy := USER;
     :new.updatedDateTime := SYSDATE;
END beforeUpdateTMXCenter;
/
CREATE OR REPLACE TRIGGER beforeUpdateUXCollectionCenter
BEFORE UPDATE
ON UserXCollectionCenter
FOR EACH ROW
BEGIN
     :new.updatedBy := USER;
     :new.updatedDateTime := SYSDATE;
END beforeUpdateUXCollectionCenter;
/

SELECT TABLE_NAME, COUNT(1) FROM DBA_TRIGGERS WHERE OWNER = 'BLUE' GROUP BY TABLE_NAME ORDER BY TABLE_NAME
