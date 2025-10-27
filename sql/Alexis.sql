ALTER SESSION SET CURRENT_SCHEMA = AD;
/*
--Creacion Tabla AutorizedEntity--
CREATE TABLE AutorizedEntity (
    id NUMBER(6),
    name VARCHAR(25) CONSTRAINT  AutorizedEntity_name_nn NOT NULL,
    openHour VARCHAR(6),
    closeHour VARCHAR(6),
    manager VARCHAR(25) CONSTRAINT  AutorizedEntity_manager_nn NOT NULL,
    contact VARCHAR(25),
    DistrictId NUMBER(6) CONSTRAINT AutorizedEntity_DistrictId_nn NOT NULL
);

--Asignacion de PK y FK en AutorizedEntity--
*/
-- ALTER TABLE AutorizedEntity
-- ADD CONSTRAINT pk_AutorizedEntity PRIMARY KEY (id)
-- USING INDEX
-- TABLESPACE AD_IND PCTFREE 20
-- STORAGE ( INITIAL 10K NEXT 10K PCTINCREASE 0);

-- ALTER TABLE AutorizedEntity
-- ADD CONSTRAINT fk_AutorizedEntity_DistrictId FOREIGN KEY (DistrictId) REFERENCES District(id);

-- --Creacion Tabla AffiliatedBusiness--
-- CREATE TABLE AffiliatedBusiness (
--     AutorizedEntityid NUMBER(6),
--     createdBy VARCHAR(25) CONSTRAINT AfBusiness_createdBy_nn NOT NULL,
--     BusinessTypeid NUMBER(6) CONSTRAINT AfBusiness_BusinessTypeid_nn NOT NULL,
--     createdDateTime DATE,
--     updatedBy VARCHAR(25),
--     updatedDateTime DATE
-- );

--Asignacion de PK y FK en AffiliatedBusiness--

-- ALTER TABLE AffiliatedBusiness
-- ADD CONSTRAINT pk_AutorizedEntityid PRIMARY KEY (AutorizedEntityid)
-- USING INDEX
-- TABLESPACE AD_IND PCTFREE 20
-- STORAGE ( INITIAL 10K NEXT 10K PCTINCREASE 0);

-- ALTER TABLE AffiliatedBusiness
-- ADD CONSTRAINT fk_AfBusiness_AEid FOREIGN KEY (AutorizedEntityid) REFERENCES AutorizedEntity(id);
--
-- ALTER TABLE AffiliatedBusiness
-- ADD CONSTRAINT fk_AfBusiness_BusinessType FOREIGN KEY (BusinessTypeid) REFERENCES BusinessType(id);

--Creacion Tabla BusinessType--
-- CREATE TABLE BusinessType
-- (
--     id              NUMBER(6),
--     description     VARCHAR(30),
--     createdBy       VARCHAR(25)
--         CONSTRAINT BusinessType_createdBy_nn NOT NULL,
--     createdDateTime DATE,
--     updatedBy       VARCHAR(25),
--     updatedDateTime DATE
-- );

--Asignacion de PK en BusinessType--

-- ALTER TABLE BusinessType
-- ADD CONSTRAINT pk_BusinessType PRIMARY KEY (id)
-- USING INDEX
-- TABLESPACE AD_IND PCTFREE 20
-- STORAGE ( INITIAL 10K NEXT 10K PCTINCREASE 0);

-- --Creacion Tabla CollectionCenter--
-- CREATE TABLE CollectionCenter (
--     AutorizedEntityid NUMBER(6),
--     centerTypeid NUMBER (6) CONSTRAINT CollectionC_centerTypeid_nn NOT NULL,
--     createdBy VARCHAR(25) CONSTRAINT CollectionC_createdBy_nn NOT NULL,
--     createdDateTime DATE,
--     updatedBy VARCHAR(25),
--     updatedDateTime DATE
-- );

--Asignacion de PK y FK en CollectionCenter--

-- ALTER TABLE CollectionCenter
-- ADD CONSTRAINT pk_AutorizedEntityid_C PRIMARY KEY (AutorizedEntityid)
-- USING INDEX
-- TABLESPACE AD_IND PCTFREE 20
-- STORAGE ( INITIAL 10K NEXT 10K PCTINCREASE 0);

-- ALTER TABLE CollectionCenter
-- ADD CONSTRAINT fk_CollectionC_AEid FOREIGN KEY (AutorizedEntityid) REFERENCES AutorizedEntity(id);
--
-- ALTER TABLE CollectionCenter
-- ADD CONSTRAINT fk_CollectionC_CenterType FOREIGN KEY (centerTypeid) REFERENCES CenterType(id);

--Creacion Tabla CenterType--
-- CREATE TABLE CenterType(
--     id NUMBER(6),
--     description VARCHAR(30),
--     createdBy VARCHAR(25) CONSTRAINT CenterType_createdBy_nn NOT NULL,
--     createdDateTime DATE,
--     updatedBy VARCHAR(25),
--     updatedDateTime DATE
-- );

--Asignacion de PK en CenterType--

-- ALTER TABLE CenterType
-- ADD CONSTRAINT pk_CenterType PRIMARY KEY (id)
-- USING INDEX
-- TABLESPACE AD_IND PCTFREE 20
-- STORAGE ( INITIAL 10K NEXT 10K PCTINCREASE 0);

-- --Creacion Tabla MaterialType--
-- CREATE TABLE MaterialType(
--     id NUMBER(6),
--     name VARCHAR(25) CONSTRAINT MaterialType_name_nn NOT NULL,
--     createdBy VARCHAR(25) CONSTRAINT MaterialType_createdBy_nn NOT NULL,
--     createdDateTime DATE,
--     updatedBy VARCHAR(25),
--     updatedDateTime DATE
-- );

--Asignacion de PK en MaterialType--

-- ALTER TABLE MaterialType
-- ADD CONSTRAINT pk_MaterialType PRIMARY KEY (id)
-- USING INDEX
-- TABLESPACE AD_IND PCTFREE 20
-- STORAGE ( INITIAL 10K NEXT 10K PCTINCREASE 0);


--Creacion Tabla MaterialTypeXCollectionCenter--
-- CREATE TABLE MaterialTypeXCollectionCenter (
--     id NUMBER(6),
--     AutorizedEntityid NUMBER(6) CONSTRAINT TMXCenter_AutorizedEntityid_nn NOT NULL,
--     createdBy VARCHAR(25) CONSTRAINT TMXCenter_createdBy_nn NOT NULL,
--     createdDateTime DATE,
--     updatedBy VARCHAR(25),
--     updatedDateTime DATE,
--     materialTypeid NUMBER(6) CONSTRAINT TMXCenter_materialTypeid_nn NOT NULL
-- );

--Asignacion de PK y FK en MaterialTypeXCollectionCenter--

-- ALTER TABLE MaterialTypeXCollectionCenter
-- ADD CONSTRAINT pk_TMXCenter PRIMARY KEY (id)
-- USING INDEX
-- TABLESPACE AD_IND PCTFREE 20
-- STORAGE ( INITIAL 10K NEXT 10K PCTINCREASE 0);

ALTER TABLE MaterialTypeXCollectionCenter
ADD CONSTRAINT fk_AutorizedEntity_TMXCenter FOREIGN KEY (AutorizedEntityid) REFERENCES CollectionC(AutorizedEntityid);

ALTER TABLE MaterialTypeXCollectionCenter
ADD CONSTRAINT fk_TMXCenter_MaterialType FOREIGN KEY (materialTypeid) REFERENCES MaterialType(id);

--Creacion Tabla UserXCollectionCenter--
-- CREATE TABLE UserXCollectionCenter(
--     id NUMBER(6),
--     userId NUMBER(6) CONSTRAINT UXC_userId_nn NOT NULL,
--     createdBy VARCHAR(25) CONSTRAINT UXC_createdBy_nn NOT NULL,
--     createdDateTime DATE,
--     updatedBy VARCHAR(25),
--     updatedDateTime DATE,
--     CollectionCenter NUMBER(6) CONSTRAINT UXC_CollectionCenter_nn NOT NULL,
--     pointsConvertionKey NUMBER(6) CONSTRAINT UXC_pointsConvertionKey_nn NOT NULL,
--     kilograms VARCHAR(5)
-- );ALTER TABLE UserXCollectionCenter
-- -- ADD CONSTRAINT pk_UXC PRIMARY KEY (id)
-- -- USING INDEX
-- -- TABLESPACE AD_IND PCTFREE 20
-- -- STORAGE ( INITIAL 10K NEXT 10K PCTINCREASE 0);

--Asignacion de PK y FK en UserXCollectionCenter--

--

-- ALTER TABLE UserXCollectionCenter
-- ADD CONSTRAINT fk_UXC_CollectionC FOREIGN KEY (CollectionCenter) REFERENCES CollectionC(AutorizedEntityid);
--
-- ALTER TABLE UserXCollectionCenter
-- ADD CONSTRAINT fk_UXC_PointsC FOREIGN KEY (pointsConvertionKey) REFERENCES PointsConvertion(id);
--
-- ALTER TABLE UserXCollectionCenter
-- ADD CONSTRAINT fk_UXC_userId FOREIGN KEY (userId) REFERENCES User(id);


-- Secuencias
-- CREATE SEQUENCE s_AutorizedEntity
-- START WITH 0
-- INCREMENT BY 1
-- MINVALUE 0
-- MAXVALUE 1000000
-- NOCACHE
-- NOCYCLE;
--
-- CREATE SEQUENCE s_AffiliatedBusiness
-- START WITH 0
-- INCREMENT BY 1
-- MINVALUE 0
-- MAXVALUE 1000000
-- NOCACHE
-- NOCYCLE;
--
-- CREATE SEQUENCE s_BusinessType
-- START WITH 0
-- INCREMENT BY 1
-- MINVALUE 0
-- MAXVALUE 1000000
-- NOCACHE
-- NOCYCLE;
--
-- CREATE SEQUENCE s_CenterType
-- START WITH 0
-- INCREMENT BY 1
-- MINVALUE 0
-- MAXVALUE 1000000
-- NOCACHE
-- NOCYCLE;
--
-- CREATE SEQUENCE s_CollectionCenter
-- START WITH 0
-- INCREMENT BY 1
-- MINVALUE 0
-- MAXVALUE 1000000
-- NOCACHE
-- NOCYCLE;
--
-- CREATE SEQUENCE s_MaterialType
-- START WITH 0
-- INCREMENT BY 1
-- MINVALUE 0
-- MAXVALUE 1000000
-- NOCACHE
-- NOCYCLE;
--
-- CREATE SEQUENCE s_MTypeXCollectionCenter
-- START WITH 0
-- INCREMENT BY 1
-- MINVALUE 0
-- MAXVALUE 1000000
-- NOCACHE
-- NOCYCLE;
--
-- CREATE SEQUENCE s_UserXCollectionCenter
-- START WITH 0
-- INCREMENT BY 1
-- MINVALUE 0
-- MAXVALUE 1000000
-- NOCACHE
-- NOCYCLE;





