ALTER SESSION SET CURRENT_SCHEMA = BLUE;

CREATE TABLE Users
(
    id               NUMBER(6),
    firstName       VARCHAR2(25)
        CONSTRAINT TB_USERS_FIRST_NAME_NN NOT NULL,
    secondName      VARCHAR2(25),
    lastName        VARCHAR2(25)
        CONSTRAINT TB_USERS_LAST_NAME_NN NOT NULL,
    secondLastName VARCHAR2(25),
    birthDate        DATE
        CONSTRAINT TB_USERS_BIRTHDATE NOT NULL,
    username         VARCHAR2(25)
        CONSTRAINT TB_USERS_USERNAME_NN NOT NULL,
        CONSTRAINT TB_USERS_UNSERNAME_UQ UNIQUE (username),
    password         VARCHAR2(25)
        CONSTRAINT TB_USERS_PASSWORD_NN NOT NULL,
    idNumber        VARCHAR(25)
        CONSTRAINT TB_USERS_ID_NUMBER_NN NOT NULL,
    address          VARCHAR(50)
        CONSTRAINT TB_USERS_ADDRESS_NN NOT NULL,
    photoUrl        VARCHAR2(100),
    createdBy       VARCHAR2(25),
    createdDate     DATE DEFAULT SYSDATE
        CONSTRAINT TB_USERS_CREATED_DATE_NN NOT NULL,
    updatedBy       VARCHAR2(25),
    updatedDate     DATE,
    genderId        NUMBER(6),
    id_typeId       NUMBER(6)
        CONSTRAINT TB_USERS_ID_TYPE_ID_NN NOT NULL,
    userTypeId     NUMBER(6)
        CONSTRAINT TB_USERS_USER_TYPE_ID_NN NOT NULL,
    districtId      NUMBER(6)
        CONSTRAINT TB_USERS_DISTRICT_ID_NN NOT NULL,
    pointsBalance NUMBER(8) DEFAULT 0
        CONSTRAINT TB_USERS_POINTSBALANCE_MIN CHECK (pointsBalance >= 0)
);


CREATE TABLE Phones
(
    id           NUMBER(6),
    phoneNumber NUMBER(9)
        CONSTRAINT TB_PHONES_PHONE_NUMBER_NN NOT NULL,
    createdBy   VARCHAR2(25)
        CONSTRAINT TB_PHONES_CREATED_BY_NN NOT NULL,
    createdDate DATE DEFAULT SYSDATE
        CONSTRAINT TB_PHONES_CREATED_DATE_NN NOT NULL,
    updatedBy   VARCHAR2(25),
    updatedDate DATE,
    labelId     NUMBER(6)
        CONSTRAINT TB_PHONES_LABEL_ID_NN NOT NULL,
    userId      NUMBER(6)
        CONSTRAINT TB_PHONES_USER_ID_NN NOT NULL
);

CREATE TABLE Emails
(
    id            NUMBER(6),
    emailAddress VARCHAR2(50)
        CONSTRAINT TB_EMAILS_EMAIL_ADDRESS_NN NOT NULL,
    CONSTRAINT TB_EMAILS_EMAIL_ADDRESS_FORMAT CHECK (REGEXP_LIKE(emailAddress,
                                                                 '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')),
    createdBy    VARCHAR2(25)
        CONSTRAINT TB_EMAILS_CREATED_BY_NN NOT NULL,
    createdDate  DATE DEFAULT SYSDATE
        CONSTRAINT TB_EMAILS_CREATED_DATE_NN NOT NULL,
    updatedBy    VARCHAR2(25),
    updatedDate  DATE,
    labelId      NUMBER(6)
        CONSTRAINT TB_EMAILS_LABEL_ID_NN NOT NULL,
    userId       NUMBER(6)
        CONSTRAINT TB_EMAILS_USER_ID_NN NOT NULL
);

CREATE TABLE UserTypes
(
    id           NUMBER(6),
    name         VARCHAR2(25)
        CONSTRAINT TB_USER_TYPES_NAME_NN NOT NULL,
    createdBy   VARCHAR2(25)
        CONSTRAINT TB_USER_TYPES_CREATED_BY_NN NOT NULL,
    createdDate DATE DEFAULT SYSDATE
        CONSTRAINT TB_USER_TYPES_CREATED_DATE_NN NOT NULL,
    updatedBy   VARCHAR2(25),
    updatedDate DATE
);

CREATE TABLE IdType
(
    id           NUMBER(6),
    name         VARCHAR2(25)
        CONSTRAINT TB_ID_TYPES_NAME_NN NOT NULL,
    createdBy   VARCHAR2(25)
        CONSTRAINT TB_ID_TYPES_CREATED_BY_NN NOT NULL,
    createdDate DATE DEFAULT SYSDATE
        CONSTRAINT TB_ID_TYPES_CREATED_DATE_NN NOT NULL,
    updatedBy   VARCHAR2(25),
    updatedDate DATE
);

CREATE TABLE Genders
(
    id           NUMBER(6),
    name         VARCHAR2(25)
        CONSTRAINT TB_GENDERS_NAME_NN NOT NULL,
    createdBy   VARCHAR2(25)
        CONSTRAINT TB_GENDERS_CREATED_BY_NN NOT NULL,
    createdDate DATE DEFAULT SYSDATE
        CONSTRAINT TB_GENDERS_CREATED_DATE_NN NOT NULL,
    updatedBy   VARCHAR2(25),
    updatedDate DATE
);

CREATE TABLE Labels
(
    id           NUMBER(6),
    description  VARCHAR2(25)
        CONSTRAINT TB_LABELS_NAME_NN NOT NULL,
    createdBy   VARCHAR2(25)
        CONSTRAINT TB_LABELS_CREATED_BY_NN NOT NULL,
    createdDate DATE DEFAULT SYSDATE
        CONSTRAINT TB_LABELS_CREATED_DATE_NN NOT NULL,
    updatedBy   VARCHAR2(25),
    updatedDate DATE
);

--Creacion Tabla AutorizedEntity--
CREATE TABLE AutorizedEntity
(
    id         NUMBER(6),
    name       VARCHAR(25)
        CONSTRAINT AutorizedEntity_name_nn NOT NULL,
    openHour   TIMESTAMP,
    closeHour  TIMESTAMP,
    manager    VARCHAR(25)
        CONSTRAINT AutorizedEntity_manager_nn NOT NULL,
    contact    VARCHAR(25),
    districtId NUMBER(6)
        CONSTRAINT AutorizedEntity_DistrictId_nn NOT NULL
);

--Creacion Tabla AffiliatedBusiness--
CREATE TABLE AffiliatedBusiness
(
    AutorizedEntityid NUMBER(6),
    createdBy         VARCHAR(25)
        CONSTRAINT AfBusiness_createdBy_nn NOT NULL,
    BusinessTypeid    NUMBER(6)
        CONSTRAINT AfBusiness_BusinessTypeid_nn NOT NULL,
    createdDateTime   DATE,
    updatedBy         VARCHAR(25),
    updatedDateTime   DATE
);

--Creacion Tabla BusinessType--
CREATE TABLE BusinessType
(
    id              NUMBER(6),
    description     VARCHAR(30),
    createdBy       VARCHAR(25)
        CONSTRAINT BusinessType_createdBy_nn NOT NULL,
    createdDateTime DATE,
    updatedBy       VARCHAR(25),
    updatedDateTime DATE
);

--Creacion Tabla CollectionCenter--
CREATE TABLE CollectionCenter
(
    AutorizedEntityid NUMBER(6),
    centerTypeid      NUMBER(6)
        CONSTRAINT CollectionC_centerTypeid_nn NOT NULL,
    createdBy         VARCHAR(25)
        CONSTRAINT CollectionC_createdBy_nn NOT NULL,
    createdDateTime   DATE,
    updatedBy         VARCHAR(25),
    updatedDateTime   DATE
);

--Creacion Tabla CenterType--
CREATE TABLE CenterType
(
    id              NUMBER(6),
    description     VARCHAR(30),
    createdBy       VARCHAR(25)
        CONSTRAINT CenterType_createdBy_nn NOT NULL,
    createdDateTime DATE,
    updatedBy       VARCHAR(25),
    updatedDateTime DATE
);

--Creacion Tabla MaterialType--
CREATE TABLE MaterialType
(
    id              NUMBER(6),
    name            VARCHAR(25)
        CONSTRAINT MaterialType_name_nn NOT NULL,
    createdBy       VARCHAR(25)
        CONSTRAINT MaterialType_createdBy_nn NOT NULL,
    createdDateTime DATE,
    updatedBy       VARCHAR(25),
    updatedDateTime DATE
);

CREATE TABLE MaterialTypeXCollectionCenter
(
    id                NUMBER(6),
    AutorizedEntityid NUMBER(6)
        CONSTRAINT TMXCenter_AutorizedEntityid_nn NOT NULL,
    createdBy         VARCHAR(25)
        CONSTRAINT TMXCenter_createdBy_nn NOT NULL,
    createdDateTime   DATE,
    updatedBy         VARCHAR(25),
    updatedDateTime   DATE,
    materialTypeid    NUMBER(6)
        CONSTRAINT TMXCenter_materialTypeid_nn NOT NULL
);

CREATE TABLE UserXCollectionCenter
(
    id                  NUMBER(6),
    userId              NUMBER(6)
        CONSTRAINT UXC_userId_nn NOT NULL,
    createdBy           VARCHAR(25)
        CONSTRAINT UXC_createdBy_nn NOT NULL,
    createdDateTime     DATE,
    updatedBy           VARCHAR(25),
    updatedDateTime     DATE,
    CollectionCenter    NUMBER(6)
        CONSTRAINT UXC_CollectionCenter_nn NOT NULL,
    pointsConvertionKey NUMBER(6)
        CONSTRAINT UXC_pointsConvertionKey_nn NOT NULL,
    kilograms           VARCHAR(5)
);

/*CREATE TABLE DISTRICT*/
CREATE TABLE District
(
    id              NUMBER(6),
    name            VARCHAR(25)
        CONSTRAINT District_name_nn NOT NULL,
    updatedBy       VARCHAR(25),
    updatedDateTime DATE,
    createdBy       VARCHAR(25)
        CONSTRAINT District_createdBy_nn NOT NULL,
    createdDateTime DATE,
    cityId          NUMBER(6)
        CONSTRAINT District_CityId_nn NOT NULL
);

/*CREATE TABLE CITY*/
CREATE TABLE City
(
    id              NUMBER(6),
    name            VARCHAR(25)
        CONSTRAINT City_name_nn NOT NULL,
    updatedBy       VARCHAR(25),
    updatedDateTime DATE,
    createdBy       VARCHAR(25)
        CONSTRAINT City_createdBy_nn NOT NULL,
    createdDateTime DATE,
    ProvinceId      NUMBER(6)
        CONSTRAINT City_ProvinceId_nn NOT NULL
);

CREATE TABLE Province
(
    id              NUMBER(6),
    name            VARCHAR(25)
        CONSTRAINT Province_name_nn NOT NULL,
    updatedBy       VARCHAR(25),
    updatedDateTime DATE,
    createdBy       VARCHAR(25)
        CONSTRAINT Province_createdBy_nn NOT NULL,
    createdDateTime DATE,
    CountryId       NUMBER(6)
        CONSTRAINT Province_CountryId_nn NOT NULL
);

/*CREATE TABLE COUNTRY*/
CREATE TABLE Country
(
    id              NUMBER(6),
    name            VARCHAR(25)
        CONSTRAINT Country_name_nn NOT NULL,
    updatedBy       VARCHAR(25),
    updatedDateTime DATE,
    createdBy       VARCHAR(25)
        CONSTRAINT Country_createdBy_nn NOT NULL,
    createdDateTime DATE
);

/*CREATE TABLE PRODUCT*/
CREATE TABLE Product
(
    id                 NUMBER(6),
    description        VARCHAR(25)
        CONSTRAINT Product_description_nn NOT NULL,
    updatedBy          VARCHAR(25),
    updatedDateTime    DATE,
    createdBy          VARCHAR(25)
        CONSTRAINT Product_createdBy_nn NOT NULL,
    createdDateTime    DATE,
    photoUrl           VARCHAR(25)
    CONSTRAINT         Product_photoUrl_nn NOT NULL,
    cost               FLOAT
        CONSTRAINT Product_cost_NN NOT NULL,
    CONSTRAINT Product_cost_MIN CHECK (cost > 0),
    authorizedEntityId NUMBER(6)
        CONSTRAINT Product_authorizedEntityId_nn NOT NULL
);

/*CREATE TABLE PRODUCTXUSER*/
CREATE TABLE ProductXUser
(
    id              NUMBER(6),
    userId          NUMBER(6)
        CONSTRAINT ProductXUser_userId_nn NOT NULL,
    updatedBy       VARCHAR(25),
    updatedDateTime DATE,
    createdBy       VARCHAR(25)
        CONSTRAINT ProductXUser_createdBy_nn NOT NULL,
    createdDateTime DATE,
    quantity        NUMBER
        CONSTRAINT ProductXUser_quantity_MIN CHECK (quantity > 0),
    productId       NUMBER(6)
        CONSTRAINT ProductXUser_productId_nn NOT NULL
);

CREATE TABLE PointsConvertion
(
    id              NUMBER(6),
    name            NUMBER(6)
        CONSTRAINT PointsConvertion_name_nn NOT NULL,
    updatedBy       VARCHAR(25),
    updatedDateTime DATE,
    createdBy       VARCHAR(25)
        CONSTRAINT PointsConvertion_createdBy_nn NOT NULL,
    createdDateTime DATE,
    pointsPerKg     NUMBER
        CONSTRAINT PointsConvertion_pointsPerKg_MIN CHECK (pointsPerKg > 0),
    valueInCurrency NUMBER
        CONSTRAINT PointsConvertion_valueInCurrency_MIN CHECK (valueInCurrency > 0),
    CurrencyId      NUMBER(6)
        CONSTRAINT PointsConvertion_CurrencyId_nn NOT NULL,
    MaterialTypeId  NUMBER(6)
        CONSTRAINT PointsConvertion_MaterialTypeId_nn NOT NULL
);

/*CREATE TABLE CURRENCY*/
CREATE TABLE Currency
(
    id              NUMBER(6),
    code            VARCHAR(25)
        CONSTRAINT Currency_code_nn NOT NULL,
    updatedBy       VARCHAR(25),
    updatedDateTime DATE,
    createdBy       VARCHAR(25)
        CONSTRAINT Currency_createdBy_nn NOT NULL,
    createdDateTime DATE,
    symbol          varchar(25)
        CONSTRAINT Currency_symbol_nn NOT NULL
);

CREATE TABLE ChangesLog
(
    id NUMBER(8),
    columnName VARCHAR2(25),
    oldValue VARCHAR(25),
    newValue VARCHAR2(25),
    transactionType VARCHAR2(25),
    dateTime DATE,
    userName VARCHAR2(25) CONSTRAINT ChangesLog_userId_NN NOT NULL,
    tableName VARCHAR2(25) CONSTRAINT ChangesLog_tableName_NN NOT NULL,
    objectId NUMBER(6)
);

CREATE TABLE LeaderboardLogs
(
    id NUMBER(6),
    month VARCHAR2(10) CONSTRAINT LeaderboardLogs_month_NN NOT NULL,
    productXUserId NUMBER(6)
);

COMMIT;
