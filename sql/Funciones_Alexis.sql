------- PAQUETE AUTORIZED ENTITY--------
ALTER SESSION SET CURRENT_SCHEMA = AD;

ALTER SESSION SET CURRENT_SCHEMA = AD;

CREATE OR REPLACE PACKAGE adminAutorizedEntity AS
  PROCEDURE insertAutorizedEntity(pName VARCHAR2, pOpenHour TIMESTAMP, pCloseHour TIMESTAMP, pManager VARCHAR2, pContact VARCHAR2, pDistrictId NUMBER, pNewId OUT NUMBER );
  PROCEDURE updateAutorizedEntity(pId NUMBER, pName VARCHAR2, pOpenHour TIMESTAMP, pCloseHour TIMESTAMP, pManager VARCHAR2, pContact VARCHAR2, pDistrictId NUMBER);
  PROCEDURE removeAutorizedEntity(pId NUMBER);
  FUNCTION getAutorizedEntity(pId NUMBER DEFAULT NULL, pName VARCHAR2 DEFAULT NULL, pManager VARCHAR2 DEFAULT NULL, pDistrictId NUMBER DEFAULT NULL) RETURN SYS_REFCURSOR;
END adminAutorizedEntity;

CREATE OR REPLACE PACKAGE BODY adminAutorizedEntity AS

  PROCEDURE insertAutorizedEntity(pName VARCHAR2, pOpenHour TIMESTAMP, pCloseHour TIMESTAMP, pManager VARCHAR2, pContact VARCHAR2, pDistrictId  NUMBER, pNewId OUT NUMBER) 
    AS
BEGIN
    INSERT INTO AutorizedEntity (id, name, openHour, closeHour, manager, contact, districtId)
    VALUES (s_AutorizedEntity.NEXTVAL, pName, pOpenHour, pCloseHour, pManager, pContact, pDistrictId)
    RETURNING id INTO pNewId;
  EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while inserting Authorized Entity.');
  END insertAutorizedEntity;

  PROCEDURE updateAutorizedEntity(pId NUMBER, pName VARCHAR2, pOpenHour TIMESTAMP, pCloseHour TIMESTAMP, pManager VARCHAR2, pContact VARCHAR2, pDistrictId NUMBER) 
    AS
    eNoId EXCEPTION;
  BEGIN
    UPDATE AutorizedEntity
       SET name = NVL(pName, name),
           openHour = NVL(pOpenHour, openHour),
           closeHour = NVL(pCloseHour, closeHour),
           manager = NVL(pManager, manager),
           contact = NVL(pContact, contact),
           districtId = NVL(pDistrictId, districtId)
     WHERE id = pId;

    IF SQL%ROWCOUNT = 0 THEN
      RAISE updateAutorizedEntity.eNoId;
    END IF;
  EXCEPTION
    WHEN updateAutorizedEntity.eNoId THEN
      RAISE_APPLICATION_ERROR(-20001, 'Authorized Entity not found.');
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while updating Authorized Entity.');
  END updateAutorizedEntity;


  PROCEDURE removeAutorizedEntity(pId NUMBER) AS
    eNoId EXCEPTION;
  BEGIN
    DELETE FROM AffiliatedBusiness WHERE AutorizedEntityId = pId;
    DELETE FROM CollectionCenter WHERE AutorizedEntityId = pId;
    DELETE FROM AutorizedEntity WHERE id = pId;

    IF SQL%ROWCOUNT = 0 THEN
      RAISE eNoId;
    END IF;
  EXCEPTION
    WHEN eNoId THEN
      RAISE_APPLICATION_ERROR(-20001, 'Authorized Entity does not exist in the database.');
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while deleting Authorized Entity.');
  END removeAutorizedEntity;


  FUNCTION getAutorizedEntity(pId NUMBER   DEFAULT NULL, pName VARCHAR2 DEFAULT NULL, pManager VARCHAR2 DEFAULT NULL, pDistrictId NUMBER   DEFAULT NULL)
RETURN SYS_REFCURSOR AS
    vcAuthorizedEntity SYS_REFCURSOR;
  BEGIN
    OPEN vcAuthorizedEntity FOR
      SELECT
        ae.id,
        ae.name,
        ae.openHour,
        ae.closeHour,
        ae.manager,
        ae.contact,
        ae.districtId
      FROM AutorizedEntity ae
     WHERE ae.id        = NVL(pId, ae.id)
       AND ae.name      LIKE '%' || NVL(pName, ae.name) || '%'
       AND ae.manager   LIKE '%' || NVL(pManager, ae.manager) || '%'
       AND ae.districtId= NVL(pDistrictId, ae.districtId);
    RETURN (vcAuthorizedEntity);
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20001, 'Authorized Entity No found.');
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while fetching Authorized Entity.');
  END getAutorizedEntity;

END adminAutorizedEntity;

------- PAQUETE AFFILIATED BUSINESS--------

ALTER SESSION SET CURRENT_SCHEMA = AD;

CREATE OR REPLACE PACKAGE adminAffiliatedBusiness AS
  PROCEDURE insertAffiliatedBusiness(pName VARCHAR2, pOpenHour TIMESTAMP, pCloseHour TIMESTAMP, pManager  VARCHAR2, pContact VARCHAR2, pDistrictId NUMBER, pBusinessTypeId NUMBER, pNewId OUT NUMBER  );
  PROCEDURE updateAffiliatedBusiness(pId NUMBER,    pName VARCHAR2,pOpenHour TIMESTAMP, pCloseHour TIMESTAMP, pManager VARCHAR2, pContact VARCHAR2, pDistrictId NUMBER, pBusinessTypeId NUMBER);
  PROCEDURE removeAffiliatedBusiness(pId NUMBER); 
  FUNCTION  getAffiliatedBusiness(pId NUMBER DEFAULT NULL, pName VARCHAR2 DEFAULT NULL, pBusinessTypeId NUMBER DEFAULT NULL, pDistrictId NUMBER DEFAULT NULL) 
  RETURN SYS_REFCURSOR;
END adminAffiliatedBusiness;

CREATE OR REPLACE PACKAGE BODY adminAffiliatedBusiness AS

  PROCEDURE insertAffiliatedBusiness(pName VARCHAR2, pOpenHour TIMESTAMP, pCloseHour TIMESTAMP, pManager VARCHAR2, pContact VARCHAR2, pDistrictId NUMBER, pBusinessTypeId NUMBER,pNewId OUT NUMBER) 
    AS
  BEGIN
    adminAutorizedEntity.insertAutorizedEntity(
      pName, pOpenHour, pCloseHour, pManager, pContact, pDistrictId, pNewId
    );

    INSERT INTO AffiliatedBusiness (AutorizedEntityId, BusinessTypeId)
    VALUES (pNewId, pBusinessTypeId);
  EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while inserting Affiliated Business.');
  END insertAffiliatedBusiness;

  PROCEDURE updateAffiliatedBusiness(
    pId NUMBER,
    pName VARCHAR2,
    pOpenHour TIMESTAMP,
    pCloseHour TIMESTAMP,
    pManager VARCHAR2,
    pContact VARCHAR2,
    pDistrictId NUMBER,
    pBusinessTypeId NUMBER
  ) AS
    eNoId EXCEPTION;
  BEGIN
    adminAutorizedEntity.updateAutorizedEntity(
      pId, pName, pOpenHour, pCloseHour, pManager, pContact, pDistrictId
    );

    UPDATE AffiliatedBusiness
       SET BusinessTypeId = NVL(pBusinessTypeId, BusinessTypeId)
     WHERE AutorizedEntityId = pId;

    IF SQL%ROWCOUNT = 0 THEN
      INSERT INTO AffiliatedBusiness (AutorizedEntityId, BusinessTypeId)
      VALUES (pId, pBusinessTypeId);
    END IF;

  EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while updating Affiliated Business.');
  END updateAffiliatedBusiness;

  PROCEDURE removeAffiliatedBusiness(pId NUMBER) AS
    eNoId EXCEPTION;
  BEGIN
    DELETE FROM AffiliatedBusiness WHERE AutorizedEntityId = pId;
    adminAutorizedEntity.removeAutorizedEntity(pId);
  EXCEPTION
    WHEN removeAffiliatedBusiness.eNoId THEN
      RAISE_APPLICATION_ERROR(-20001, 'Affiliated Business not found.');
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while deleting Affiliated Business.');
  END removeAffiliatedBusiness;


  FUNCTION getAffiliatedBusiness(
    pId NUMBER DEFAULT NULL,
    pName VARCHAR2 DEFAULT NULL,
    pBusinessTypeId NUMBER DEFAULT NULL,
    pDistrictId NUMBER DEFAULT NULL
  ) RETURN SYS_REFCURSOR AS
    vcAffiliatedBusiness SYS_REFCURSOR;
  BEGIN
    OPEN vcAffiliatedBusiness FOR
      SELECT
        ae.id,
        ae.name,
        ae.openHour,
        ae.closeHour,
        ae.manager,
        ae.contact,
        ae.districtId,
        ab.BusinessTypeId
      FROM AutorizedEntity ae
      JOIN AffiliatedBusiness ab
        ON ab.AutorizedEntityId = ae.id
     WHERE ae.id = NVL(pId, ae.id)
       AND ae.name LIKE '%' || NVL(pName, ae.name) || '%'
       AND ab.BusinessTypeId = NVL(pBusinessTypeId, ab.BusinessTypeId)
       AND ae.districtId = NVL(pDistrictId, ae.districtId);
    RETURN (vcAffiliatedBusiness);
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20001, 'Affiliated Business No found.');
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while fetching Affiliated Business.');
  END getAffiliatedBusiness;

END adminAffiliatedBusiness;

------- PAQUETE BUSINESSTYPE --------

ALTER SESSION SET CURRENT_SCHEMA = AD;

CREATE OR REPLACE PACKAGE adminBusinessType IS
PROCEDURE insertBusinessType(description_I VARCHAR);
FUNCTION getBusinessType(bId NUMBER DEFAULT NULL, bDescription VARCHAR DEFAULT NULL) RETURN SYS_REFCURSOR;
PROCEDURE removeBusinessType (id_I NUMBER);
PROCEDURE updateBusinessType (id_I NUMBER, description_I VARCHAR);
END adminBusinessType;

------- BODY BUSINESSTYPE --------

ALTER SESSION SET CURRENT_SCHEMA = AD;
CREATE OR REPLACE PACKAGE BODY adminBusinessType AS

PROCEDURE insertBusinessType(description_I VARCHAR)
AS
BEGIN
    INSERT INTO BusinessType (id, description)
    VALUES (s_BusinessType.NEXTVAL,description_I);
EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20001, 'Unexpected error occurred.');
    COMMIT;
END;

FUNCTION getBusinessType(bId NUMBER DEFAULT NULL, bDescription VARCHAR DEFAULT NULL)
RETURN SYS_REFCURSOR
    AS
        vcBusinessType SYS_REFCURSOR;
    BEGIN
        OPEN vcBusinessType FOR
            SELECT 
                id,
                CREATEDBY,
                CREATEDDATE,
                UPDATEDBY,
                UPDATEDDATE,
                description
            FROM 
                BusinessType
            WHERE 
                id = NVL(bId, id)
                AND description LIKE '%' || NVL(bDescription, description) || '%';
    EXCEPTION            
        when NO_DATA_FOUND then
            RAISE_APPLICATION_ERROR(-20001, 'type of Business No found.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');
        RETURN (vcBusinessType);
        COMMIT;
END getBusinessType;

PROCEDURE removeBusinessType(id_I NUMBER)
IS
eNoBT exception;
BEGIN
    DELETE FROM BusinessType
    WHERE id = nvl(id_I, id);

    if SQL%NOTFOUND then
        raise removeBusinessType.eNoBT ;
        end if;

    exception
        when removeBusinessType.eNoBT THEN
            RAISE_APPLICATION_ERROR(-20001, 'The type of Business does not exist in the database.');
        when OTHERS then
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');
    COMMIT;
END;

PROCEDURE updateBusinessType(id_I NUMBER, description_I VARCHAR)
IS
            eNobtId EXCEPTION;
        BEGIN
            UPDATE BusinessType
            SET description = NVL(description_I, description)
            WHERE id = id_I;

            IF SQL%NOTFOUND then
                raise updateBusinessType.eNobtId;
            END IF;

            COMMIT;
            EXCEPTION
                when updateBusinessType.eNobtId then
                    RAISE_APPLICATION_ERROR(-20001, 'No Type of business found.');
                when OTHERS then
                    RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');
END;

END adminBusinessType;

------- PAQUETE CENTERTYPE --------
ALTER SESSION SET CURRENT_SCHEMA = AD;

CREATE OR REPLACE PACKAGE adminCenterType IS
PROCEDURE insertCenterType(description_I VARCHAR);
FUNCTION getCenterType(cId NUMBER DEFAULT NULL, cDescription VARCHAR DEFAULT NULL) RETURN SYS_REFCURSOR;
PROCEDURE removeCenterType (id_I NUMBER);
PROCEDURE updateCenterType (id_I NUMBER, description_I VARCHAR);
END adminCenterType;

------- BODY CENTERTYPE --------

ALTER SESSION SET CURRENT_SCHEMA = AD;
CREATE OR REPLACE PACKAGE BODY adminCenterType AS

PROCEDURE insertCenterType(description_I VARCHAR)
AS
BEGIN
    INSERT INTO CenterType (id, description)
    VALUES (s_CenterType.NEXTVAL, description_I);

EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20001, 'Unexpected error occurred.');
    COMMIT;
END;

FUNCTION getCenterType(cId NUMBER DEFAULT NULL, cDescription VARCHAR DEFAULT NULL)
RETURN SYS_REFCURSOR
    AS
        vcCenterType SYS_REFCURSOR;
    BEGIN
        OPEN vcCenterType FOR
            SELECT 
                id,
                description
            FROM 
                CenterType
            WHERE 
                id = NVL(cId, id)
                AND description LIKE '%' || NVL(cDescription, description) || '%';
    EXCEPTION           
        when NO_DATA_FOUND then
            RAISE_APPLICATION_ERROR(-20001, 'type of Center No found.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');
        RETURN (vcCenterType);
END getCenterType;

PROCEDURE removeCenterType(id_I NUMBER)
IS
eNoCT exception;
BEGIN
    DELETE FROM CenterType
    WHERE id = nvl(id_I, id);

    if SQL%NOTFOUND then
        raise removeCenterType.eNoCT ;
        end if;

    exception
        when removeCenterType.eNoCT THEN
            RAISE_APPLICATION_ERROR(-20001, 'The type of Center does not exist in the database.');
        when OTHERS then
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');
    COMMIT;
END;

PROCEDURE updateCenterType(id_I NUMBER, description_I VARCHAR)
IS
            eNoctId EXCEPTION;
        BEGIN
            UPDATE CenterType
            SET description = NVL(description_I, description)
            WHERE id = id_I;

            IF SQL%NOTFOUND then
                raise updateCenterType.eNoctId;
            END IF;

            COMMIT;
            EXCEPTION
                when updateCenterType.eNoctId then
                    RAISE_APPLICATION_ERROR(-20001, 'No Type of Collection Center found.');
                when OTHERS then
                    RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');
END;

END adminCenterType;

------- PAQUETE MATERIALTYPE --------
ALTER SESSION SET CURRENT_SCHEMA = AD;

CREATE OR REPLACE PACKAGE adminMaterialType IS
PROCEDURE insertMaterialType(name_I VARCHAR);
FUNCTION getMaterialType(mId NUMBER DEFAULT NULL, mName VARCHAR DEFAULT NULL) RETURN SYS_REFCURSOR;
PROCEDURE removeMaterialType (id_I NUMBER);
PROCEDURE updateMaterialType (id_I NUMBER, name_I VARCHAR);
END adminMaterialType;

------- BODY MATERIALTYPE --------

ALTER SESSION SET CURRENT_SCHEMA = AD;
CREATE OR REPLACE PACKAGE BODY adminMaterialType AS

PROCEDURE insertMaterialType(name_I VARCHAR)
AS
BEGIN
    INSERT INTO MaterialType (id, name)
    VALUES (s_MaterialType.NEXTVAL, name_I);

EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20001, 'Unexpected error occurred.');
    COMMIT;
END;

FUNCTION getMaterialType(mId NUMBER DEFAULT NULL, mName VARCHAR DEFAULT NULL)
RETURN SYS_REFCURSOR
    AS
        vcMaterialType SYS_REFCURSOR;
    BEGIN
        OPEN vcMaterialType FOR
            SELECT 
                id,
                CREATEDBY,
                CREATEDDATE,
                UPDATEDBY,
                UPDATEDDATE,
                name
            FROM 
                MaterialType
            WHERE 
                id = NVL(mId, id)
                AND name LIKE '%' || NVL(mName, name) || '%';
    EXCEPTION            
        when NO_DATA_FOUND then
            RAISE_APPLICATION_ERROR(-20001, 'type of material No found.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');
        RETURN (vcMaterialType);
END getMaterialType;

PROCEDURE removeMaterialType(id_I NUMBER)
IS
eNoMT exception;
BEGIN
    DELETE FROM MaterialType
    WHERE id = nvl(id_I, id);
    
    if SQL%NOTFOUND then
        raise removeMaterialType.eNoMT  ;
        end if;

    exception
        when removeMaterialType.eNoMT THEN
            RAISE_APPLICATION_ERROR(-20001, 'The type of Material does not exist in the database.');
        when OTHERS then
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');
    COMMIT;
END;

PROCEDURE updateMaterialType(id_I NUMBER, name_I VARCHAR)
IS
            eNomtId EXCEPTION;
        BEGIN
            UPDATE MaterialType
            SET name = NVL(name_I, name)
            WHERE id = id_I;

            IF SQL%NOTFOUND then
                raise updateMaterialType.eNomtId;
            END IF;

            COMMIT;
            EXCEPTION
                when updateMaterialType.eNomtId then
                    RAISE_APPLICATION_ERROR(-20001, 'No Type of material found.');
                when OTHERS then
                    RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');
END;

END adminMaterialType;

------- PAQUETE COLLECTION CENTER --------
ALTER SESSION SET CURRENT_SCHEMA = AD;

ALTER SESSION SET CURRENT_SCHEMA = AD;

CREATE OR REPLACE PACKAGE adminCollectionCenter AS
    PROCEDURE insertCollectionCenter(pName VARCHAR2, pOpenHour TIMESTAMP, pCloseHour TIMESTAMP, pManager VARCHAR2, pContact VARCHAR2, pDistrictId NUMBER, pCenterTypeId NUMBER, pNewId OUT NUMBER);
    PROCEDURE updateCollectionCenter(pId NUMBER, pName VARCHAR2, pOpenHour TIMESTAMP,pCloseHour TIMESTAMP, pManager VARCHAR2, pContact VARCHAR2, pDistrictId NUMBER, pCenterTypeId NUMBER);
    PROCEDURE removeCollectionCenter(pId NUMBER);
    FUNCTION getCollectionCenter(pId NUMBER DEFAULT NULL, pName VARCHAR2 DEFAULT NULL, pCenterTypeId NUMBER DEFAULT NULL, pDistrictId NUMBER DEFAULT NULL)
    RETURN SYS_REFCURSOR;
END adminCollectionCenter;

------- BODY COLLECTION CENTER--------
CREATE OR REPLACE PACKAGE BODY adminCollectionCenter AS

  PROCEDURE insertCollectionCenter(
    pName VARCHAR2,
    pOpenHour TIMESTAMP,
    pCloseHour TIMESTAMP,
    pManager VARCHAR2,
    pContact VARCHAR2,
    pDistrictId NUMBER,
    pCenterTypeId NUMBER,
    pNewId OUT NUMBER) 
    AS
  BEGIN
    adminAutorizedEntity.insertAutorizedEntity(
      pName, pOpenHour, pCloseHour, pManager, pContact, pDistrictId, pNewId
    );
    INSERT INTO CollectionCenter (AutorizedEntityId, CenterTypeId)
    VALUES (pNewId, pCenterTypeId);
  EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while inserting Collection Center.');
  END insertCollectionCenter;

  PROCEDURE updateCollectionCenter(
    pId NUMBER,
    pName VARCHAR2,
    pOpenHour TIMESTAMP,
    pCloseHour TIMESTAMP,
    pManager VARCHAR2,
    pContact VARCHAR2,
    pDistrictId NUMBER,
    pCenterTypeId NUMBER
  ) AS
  BEGIN
    adminAutorizedEntity.updateAutorizedEntity(
      pId, pName, pOpenHour, pCloseHour, pManager, pContact, pDistrictId
    );
    UPDATE CollectionCenter
       SET CenterTypeId = NVL(pCenterTypeId, CenterTypeId)
     WHERE AutorizedEntityId = pId;

    IF SQL%ROWCOUNT = 0 THEN
      INSERT INTO CollectionCenter (AutorizedEntityId, CenterTypeId)
      VALUES (pId, pCenterTypeId);
    END IF;
  EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while updating Collection Center.');
  END updateCollectionCenter;

  PROCEDURE removeCollectionCenter(pId NUMBER) AS
  BEGIN
    DELETE FROM CollectionCenter WHERE AutorizedEntityId = pId;
    adminAutorizedEntity.removeAutorizedEntity(pId);
  EXCEPTION
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while deleting Collection Center.');
  END removeCollectionCenter;

  FUNCTION getCollectionCenter(
    pId  NUMBER   DEFAULT NULL,
    pName VARCHAR2 DEFAULT NULL,
    pCenterTypeId NUMBER   DEFAULT NULL,
    pDistrictId   NUMBER   DEFAULT NULL) 
    RETURN SYS_REFCURSOR AS
    vcCollectionCenter SYS_REFCURSOR;
  BEGIN
    OPEN vcCollectionCenter FOR
      SELECT
        ae.id,
        ae.name,
        ae.openHour,
        ae.closeHour,
        ae.manager,
        ae.contact,
        ae.districtId,
        cc.CenterTypeId
      FROM AutorizedEntity ae
      JOIN CollectionCenter cc
        ON cc.AutorizedEntityId = ae.id
     WHERE ae.id            = NVL(pId, ae.id)
       AND ae.name          LIKE '%' || NVL(pName, ae.name) || '%'
       AND cc.CenterTypeId  = NVL(pCenterTypeId, cc.CenterTypeId)
       AND ae.districtId    = NVL(pDistrictId, ae.districtId);
    RETURN (vcCollectionCenter);
  EXCEPTION
    when NO_DATA_FOUND then
        RAISE_APPLICATION_ERROR(-20001, 'Collection Center No found.');
    WHEN OTHERS THEN
      RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while fetching Collection Center.');
  END getCollectionCenter;

END adminCollectionCenter;

------- PAQUETE MATERIAL TYPE X COLLECTION CENTER --------
ALTER SESSION SET CURRENT_SCHEMA = AD;

CREATE OR REPLACE PACKAGE adminTMXCenter IS
PROCEDURE insertTMXCenter(AutorizedEntityid_I NUMBER,  materialTypeid_I NUMBER);
FUNCTION getTMXCenter(txcId NUMBER DEFAULT NULL, txcAutorizedEntityid NUMBER DEFAULT NULL, txcMaterialTypeid NUMBER DEFAULT NULL) RETURN SYS_REFCURSOR;
PROCEDURE removeTMXCenter (id_I NUMBER);
FUNCTION getListaMateriales(id_I NUMBER DEFAULT NULL) RETURN SYS_REFCURSOR;
PROCEDURE updateTMXCenter (id_I NUMBER ,AutorizedEntityid_I NUMBER,  materialTypeid_I NUMBER);
END adminTMXCenter;

------- BODY MATERIAL TYPE X COLLECTION CENTER--------
ALTER SESSION SET CURRENT_SCHEMA = AD;
CREATE OR REPLACE PACKAGE BODY adminTMXCenter AS

PROCEDURE insertTMXCenter(AutorizedEntityid_I NUMBER,  materialTypeid_I NUMBER)
AS
BEGIN
    INSERT INTO TMXCenter (id, AutorizedEntityid, materialTypeid)
    VALUES (s_MTypeXCollectionCenter.NEXTVAL, AutorizedEntityid_I,  materialTypeid_I);

EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20001, 'Unexpected error occurred.');
    COMMIT;
END;

FUNCTION getTMXCenter(txcId NUMBER DEFAULT NULL, txcAutorizedEntityid NUMBER DEFAULT NULL, txcMaterialTypeid NUMBER DEFAULT NULL)
RETURN SYS_REFCURSOR
AS
    vcTMXCenter SYS_REFCURSOR;
BEGIN
    OPEN vcTMXCenter FOR
        SELECT 
            tmxc.id,
            tmxc.AutorizedEntityid,
            tmxc.CREATEDBY,
            tmxc.CREATEDDATE,
            tmxc.UPDATEDBY,
            tmxc.UPDATEDDATE,
            ae.name AS CollectionCenter,
            mt.name AS MaterialType
        FROM 
            MaterialTypeXCollectionCenter tmxc
            JOIN CollectionCenter cc ON tmxc.AutorizedEntityid = cc.AutorizedEntityid 
            INNER JOIN MaterialType mt ON tmxc.materialTypeid = mt.id
            JOIN AutorizedEntity ae ON cc.AutorizedEntityid = ae.id 
        WHERE 
            tmxc.id = NVL(txcId, tmxc.id)
        AND mt.id = NVL(txcMaterialTypeid, mt.id)
        AND cc.AutorizedEntityid = NVL(txcAutorizedEntityid,cc.AutorizedEntityid);

    RETURN vcTMXCenter;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20001, 'No Type material x Collection Center found.');
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');
END getTMXCenter;

PROCEDURE removeTMXCenter(id_I NUMBER)
IS
eNoTMXC exception;
BEGIN
    DELETE FROM TMXCenter
    WHERE id = NVL(id_I, id);

    if SQL%NOTFOUND then
        raise removeTMXCenter.eNoTMXC;
        end if;

    exception
        when removeTMXCenter.eNoTMXC THEN
            RAISE_APPLICATION_ERROR(-20001, ' Material Type x Collection Center does not exist in the database.');
        when OTHERS then
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');
    COMMIT;
END;

FUNCTION getListaMateriales(id_I NUMBER DEFAULT NULL)
RETURN SYS_REFCURSOR
AS
    vcListaMateriales SYS_REFCURSOR;
BEGIN
    OPEN vcListaMateriales FOR
        SELECT 
            tmc.AutorizedEntityid AS collection_center,
            EXTRACT(YEAR FROM uc.createdDateTime) AS year,
            EXTRACT(MONTH FROM uc.createdDateTime) AS month,
            COUNT(DISTINCT tmc.materialTypeId) AS total_tipos_material,
            SUM(uc.kilograms) AS total_kilograms
        FROM TMXCenter tmc
        JOIN UserXCollectionCenter uc
            ON tmc.AutorizedEntityid = uc.CollectionCenter
        WHERE tmc.AutorizedEntityid = NVL(id_I, tmc.AutorizedEntityid)
        GROUP BY 
            tmc.AutorizedEntityid,
            EXTRACT(YEAR FROM uc.createdDateTime),
            EXTRACT(MONTH FROM uc.createdDateTime)
        ORDER BY year, month;

    RETURN vcListaMateriales;

EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while listing materials by center.');
END getListaMateriales;

PROCEDURE updateTMXCenter (id_I NUMBER ,AutorizedEntityid_I NUMBER,  materialTypeid_I NUMBER)
IS
            eNotxcId EXCEPTION;
        BEGIN
            UPDATE MaterialTypeXCollectionCenter
            SET AutorizedEntityid = NVL(AutorizedEntityid_I, AutorizedEntityid),
                materialTypeid = NVL(materialTypeid_I, materialTypeid)
            WHERE id = id_I;

            IF SQL%NOTFOUND then
                raise updateTMXCenter.eNotxcId;
            END IF;

            COMMIT;
            EXCEPTION
                when updateTMXCenter.eNotxcId then
                    RAISE_APPLICATION_ERROR(-20001, 'No Material Type x Collection Center found.');
                when OTHERS then
                    RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');
END;

END adminTMXCenter;

------- PAQUETE USER X COLLECTIONCENTER --------
ALTER SESSION SET CURRENT_SCHEMA = AD;

CREATE OR REPLACE PACKAGE adminUserXCollectionCenter IS
PROCEDURE insertUserXCollectionCenter(userId_I NUMBER, CollectionC_I NUMBER, pointsConvertionKey_I NUMBER, kilograms_I NUMBER);
Function getUserXCollectionCenter(uxcId NUMBER DEFAULT NULL, uxcUserid NUMBER DEFAULT NULL, uxcCollection NUMBER DEFAULT NULL, uxcPointsConvertionKey NUMBER DEFAULT NULL) return SYS_REFCURSOR;
PROCEDURE removeUserXCollectionCenter (id_I NUMBER);
PROCEDURE updateUserXCollectionCenter(id_I NUMBER, userId_I NUMBER, CollectionC_I NUMBER, pointsConvertionKey_I NUMBER, kilograms_I NUMBER);
END adminUserXCollectionCenter;

------- BODY USER X COLLECTIONCENTER--------
ALTER SESSION SET CURRENT_SCHEMA = AD;
CREATE OR REPLACE PACKAGE BODY adminUserXCollectionCenter AS

PROCEDURE insertUserXCollectionCenter(userId_I NUMBER, CollectionC_I NUMBER, pointsConvertionKey_I NUMBER, kilograms_I NUMBER)
AS
BEGIN
    INSERT INTO UserXCollectionCenter (id, userId ,CollectionCenter,pointsConvertionKey, kilograms)
    VALUES (s_UserXCollectionCenter.NEXTVAL,userId_I, CollectionC_I, pointsConvertionKey_I, kilograms_I);

EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20001, 'Unexpected error occurred.');
    COMMIT;
END;

FUNCTION getUserXCollectionCenter(uxcId NUMBER DEFAULT NULL, uxcUserid NUMBER DEFAULT NULL, uxcCollection NUMBER DEFAULT NULL, uxcPointsConvertionKey NUMBER DEFAULT NULL)
RETURN SYS_REFCURSOR
AS
    vcUserXCollectionCenter SYS_REFCURSOR;
BEGIN
    OPEN vcUserXCollectionCenter FOR
        SELECT 
            usxc.id,
            usxc.kilograms,
            usxc.CREATEDBY,
            usxc.CREATEDDATE,
            usxc.UPDATEDBY,
            usxc.UPDATEDDATE,
            pc.pointsPerKg AS Points,
            pc.name AS Material,
            ae.name AS CollectionCenter,
            us.name AS User
        FROM 
            UserXCollectionCenter usxc
            INNER JOIN AutorizedEntity ae ON usxc.AutorizedEntityid = ae.id 
            INNER JOIN User us ON usxc.userId = us.id 
            INNER JOIN PointsConvertion pc ON usxc.pointsConvertionKey = pc.id
        WHERE 
            usxc.id = NVL(uxcId, usxc.id)
        AND us.id = NVL(uxcUserid, us.id)
        AND ae.id = NVL(uxcCollection,ae.id)
        AND pc.id = NVL(uxcPointsConvertionKey, pc.id);

    RETURN vcUserXCollectionCenter;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20001, 'No User x Collection Center found.');
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');

END getUserXCollectionCenter;

PROCEDURE removeUserXCollectionCenter(id_I NUMBER)
IS
eNoUXC exception;
BEGIN
    DELETE FROM UserXCollectionCenter
    WHERE id = NVL(id_I, id);

    if SQL%NOTFOUND then
        raise removeUserXCollectionCenter.eNoUXC;
        end if;

    exception
        when removeUserXCollectionCenter.eNoUXC THEN
            RAISE_APPLICATION_ERROR(-20001, ' User x Collection Center does not exist in the database.');
        when OTHERS then
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');
    COMMIT;
END removeUserXCollectionCenter;

PROCEDURE updateUserXCollectionCenter(id_I NUMBER, userId_I NUMBER, CollectionC_I NUMBER, pointsConvertionKey_I NUMBER, kilograms_I NUMBER)
IS
            eNouxcId EXCEPTION;
        BEGIN
            UPDATE MaterialTypeXCollectionCenter
            SET userId = NVL(userId_I, userId),
                CollectionCenter = NVL(CollectionC_I, CollectionCenter),
                pointsConvertionKey = NVL(pointsConvertionKey_I, pointsConvertionKey),
                kilograms = NVL(kilograms_I, kilograms)
            WHERE id = id_I;

            IF SQL%NOTFOUND then
                raise updateUserXCollectionCenter.eNouxcId;
            END IF;

            COMMIT;
            EXCEPTION
                when updateUserXCollectionCenter.eNouxcId then
                    RAISE_APPLICATION_ERROR(-20001, 'No User x Collection Center found.');
                when OTHERS then
                    RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');
END updateUserXCollectionCenter;

END adminUserXCollectionCenter;
