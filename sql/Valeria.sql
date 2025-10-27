/*creacion tablas Valeria*/

ALTER SESSION SET CURRENT_SCHEMA = AD;

-- /*CREATE TABLE DISTRICT*/
-- CREATE TABLE District
-- (
--     id              NUMBER(6),
--     name            VARCHAR(25)
--         CONSTRAINT District_name_nn NOT NULL,
--     updatedBy       VARCHAR(25),
--     updatedDateTime DATE,
--     createdBy       VARCHAR(25)
--         CONSTRAINT District_createdBy_nn NOT NULL,
--     createdDateTime DATE,
--     cityId          NUMBER(6)
--         CONSTRAINT District_CityId_nn NOT NULL
-- );

-- ALTER TABLE District
-- ADD CONSTRAINT pk_District PRIMARY KEY (id)
-- USING INDEX
-- TABLESPACE AD_IND PCTFREE 20
-- STORAGE ( INITIAL 10K NEXT 10K PCTINCREASE 0);

-- ALTER TABLE District
-- ADD CONSTRAINT fk_District_CityId FOREIGN KEY (CityId) REFERENCES City(Id);

-- /*CREATE TABLE CITY*/
-- CREATE TABLE City
-- (
--     id              NUMBER(6),
--     name            VARCHAR(25)
--         CONSTRAINT City_name_nn NOT NULL,
--     updatedBy       VARCHAR(25),
--     updatedDateTime DATE,
--     createdBy       VARCHAR(25)
--         CONSTRAINT City_createdBy_nn NOT NULL,
--     createdDateTime DATE,
--     ProvinceId      NUMBER(6)
--         CONSTRAINT City_ProvinceId_nn NOT NULL
-- );

-- ALTER TABLE City
-- ADD CONSTRAINT pk_City PRIMARY KEY (id)
-- USING INDEX
-- TABLESPACE AD_IND PCTFREE 20
-- STORAGE ( INITIAL 10K NEXT 10K PCTINCREASE 0);

-- ALTER TABLE City
--     ADD CONSTRAINT fk_City_ProvinceId FOREIGN KEY (ProvinceId) REFERENCES Province (Id);

/*CREATE TABLE PROVINCE*/
-- CREATE TABLE Province
-- (
--     id              NUMBER(6),
--     name            VARCHAR(25)
--         CONSTRAINT Province_name_nn NOT NULL,
--     updatedBy       VARCHAR(25),
--     updatedDateTime DATE,
--     createdBy       VARCHAR(25)
--         CONSTRAINT Province_createdBy_nn NOT NULL,
--     createdDateTime DATE,
--     CountryId       NUMBER(6)
--         CONSTRAINT Province_CountryId_nn NOT NULL
-- );
--
-- ALTER TABLE Province
-- ADD CONSTRAINT pk_Province PRIMARY KEY (id)
-- USING INDEX
-- TABLESPACE AD_IND PCTFREE 20
-- STORAGE ( INITIAL 10K NEXT 10K PCTINCREASE 0);

-- ALTER TABLE Province
-- ADD CONSTRAINT fk_Province_CountryId FOREIGN KEY (CountryId) REFERENCES Country(Id);

-- /*CREATE TABLE COUNTRY*/
-- CREATE TABLE Country (
--     id NUMBER(6),
--     name VARCHAR(25) CONSTRAINT Country_name_nn NOT NULL,
--     updatedBy VARCHAR(25),
--     updatedDateTime DATE,
--     createdBy VARCHAR(25) CONSTRAINT Country_createdBy_nn NOT NULL,
--     createdDateTime DATE
-- );

-- ALTER TABLE Country
-- ADD CONSTRAINT pk_Country PRIMARY KEY (id)
-- USING INDEX
-- TABLESPACE AD_IND PCTFREE 20
-- STORAGE ( INITIAL 10K NEXT 10K PCTINCREASE 0);

-- /*CREATE TABLE PRODUCT*/
-- CREATE TABLE Product (
--     id NUMBER(6),
--     description VARCHAR(25) CONSTRAINT Product_description_nn NOT NULL,
--     updatedBy VARCHAR(25),
--     updatedDateTime DATE,
--     createdBy VARCHAR(25) CONSTRAINT Product_createdBy_nn NOT NULL,
--     createdDateTime DATE,
--     photoUrl VARCHAR(25), CONSTRAINT Product_photoUrl_nn NOT NULL,
--     cost FLOAT CONSTRAINT Product_cost_NN NOT NULL, CONSTRAINT Product_cost_MIN CHECK (price > 0),
--     authorizedEntityId NUMBER(6) CONSTRAINT Product_authorizedEntityId_nn NOT NULL
-- );

-- ALTER TABLE Product
-- ADD CONSTRAINT pk_Product PRIMARY KEY (id)
-- USING INDEX
-- TABLESPACE AD_IND PCTFREE 20
-- STORAGE ( INITIAL 10K NEXT 10K PCTINCREASE 0);

-- ALTER TABLE Product
-- ADD CONSTRAINT fk_Product_authorizedEntityId FOREIGN KEY (authorizedEntityId) REFERENCES authorizedEntity(Id);

-- /*CREATE TABLE PRODUCTXUSER*/
-- CREATE TABLE ProductXUser (
--     id NUMBER(6),
--     userId NUMBER(6) CONSTRAINT ProductXUser_userId_nn NOT NULL,
--     updatedBy VARCHAR(25),
--     updatedDateTime DATE,
--     createdBy VARCHAR(25) CONSTRAINT ProductXUser_createdBy_nn NOT NULL,
--     createdDateTime DATE,
--     quantity NUMBER CONSTRAINT ProductXUser_quantity_MIN CHECK (quantity > 0),
--     productId NUMBER(6) CONSTRAINT ProductXUser_productId_nn NOT NULL
-- );
--
-- ALTER TABLE ProductXUser
-- ADD CONSTRAINT pk_ProductXUser PRIMARY KEY (id)
-- USING INDEX
-- TABLESPACE AD_IND PCTFREE 20
-- STORAGE ( INITIAL 10K NEXT 10K PCTINCREASE 0);

-- ALTER TABLE ProductXUser
-- ADD CONSTRAINT fk_ProductXUser_userId FOREIGN KEY (userId) REFERENCES user(Id);
--
-- ALTER TABLE ProductXUser
-- ADD CONSTRAINT fk_ProductXUser_productId FOREIGN KEY (productId) REFERENCES product(Id);

-- /*CREATE TABLE PointsConvertion*/
-- CREATE TABLE PointsConvertion (
--     id NUMBER(6),
--     name NUMBER(6) CONSTRAINT PointsConvertion_name_nn NOT NULL,
--     updatedBy VARCHAR(25),
--     updatedDateTime DATE,
--     createdBy VARCHAR(25) CONSTRAINT PointsConvertion_createdBy_nn NOT NULL,
--     createdDateTime DATE,
--     pointsPerKg NUMBER CONSTRAINT PointsConvertion_pointsPerKg_MIN CHECK (pointsPerKg > 0),
--     valueInCurrency NUMBER CONSTRAINT PointsConvertion_valueInCurrency_MIN CHECK (valueInCurrency > 0),
--     CurrencyId NUMBER(6) CONSTRAINT PointsConvertion_CurrencyId_nn NOT NULL,
--     MaterialTypeId NUMBER(6) CONSTRAINT PointsConvertion_MaterialTypeId_nn NOT NULL
-- );

-- ALTER TABLE PointsConvertion
-- ADD CONSTRAINT pk_PointsConvertion PRIMARY KEY (id)
-- USING INDEX
-- TABLESPACE AD_IND PCTFREE 20
-- STORAGE ( INITIAL 10K NEXT 10K PCTINCREASE 0);

-- ALTER TABLE PointsConvertion
-- ADD CONSTRAINT fk_PointsConvertion_CurrencyId FOREIGN KEY (CurrencyId) REFERENCES Currency(Id);
--
-- ALTER TABLE PointsConvertion
-- ADD CONSTRAINT fk_PointsConvertion_MaterialTypeId FOREIGN KEY (MaterialTypeId) REFERENCES MaterialType(Id);

-- /*CREATE TABLE CURRENCY*/
-- CREATE TABLE Currency (
--     id NUMBER(6),
--     code VARCHAR(25) CONSTRAINT Currency_code_nn NOT NULL,
--     updatedBy VARCHAR(25),
--     updatedDateTime DATE,
--     createdBy VARCHAR(25) CONSTRAINT Currency_createdBy_nn NOT NULL,
--     createdDateTime DATE,
--     symbol varchar(25) CONSTRAINT Currency_symbol_nn NOT NULL
-- );

-- ALTER TABLE Currency
-- ADD CONSTRAINT pk_Currency PRIMARY KEY (id)
-- USING INDEX
-- TABLESPACE BLUE_IND PCTFREE 20
-- STORAGE ( INITIAL 10K NEXT 10K PCTINCREASE 0);

-- CREATE SEQUENCES
-- CREATE SEQUENCE s_district
-- START WITH 0
-- INCREMENT BY 1
-- MINVALUE 0
-- MAXVALUE 1000000
-- NOCACHE
-- NOCYCLE;
--
-- CREATE SEQUENCE s_city
-- START WITH 0
-- INCREMENT BY 1
-- MINVALUE 0
-- MAXVALUE 1000000
-- NOCACHE
-- NOCYCLE;
--
-- CREATE SEQUENCE s_province
-- START WITH 0
-- INCREMENT BY 1
-- MINVALUE 0
-- MAXVALUE 1000000
-- NOCACHE
-- NOCYCLE;
--
-- CREATE SEQUENCE s_country
-- START WITH 0
-- INCREMENT BY 1
-- MINVALUE 0
-- MAXVALUE 1000000
-- NOCACHE
-- NOCYCLE;
--
-- CREATE SEQUENCE s_product
-- START WITH 0
-- INCREMENT BY 1
-- MINVALUE 0
-- MAXVALUE 1000000
-- NOCACHE
-- NOCYCLE;
--
-- CREATE SEQUENCE s_productxuser
-- START WITH 0
-- INCREMENT BY 1
-- MINVALUE 0
-- MAXVALUE 1000000
-- NOCACHE
-- NOCYCLE;
--
-- CREATE SEQUENCE s_pointsconvertion
-- START WITH 0
-- INCREMENT BY 1
-- MINVALUE 0
-- MAXVALUE 1000000
-- NOCACHE
-- NOCYCLE;
--
-- CREATE SEQUENCE s_currency
-- START WITH 0
-- INCREMENT BY 1
-- MINVALUE 0
-- MAXVALUE 1000000
-- NOCACHE
-- NOCYCLE;
