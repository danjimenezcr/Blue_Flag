ALTER SESSION SET CURRENT_SCHEMA = BLUE;

-- ChangesLog Table Packages
CREATE OR REPLACE PACKAGE ChangeLogger AS
    PROCEDURE LogChange(columnName VARCHAR2, oldValue VARCHAR2, newValue VARCHAR2, transactionType VARCHAR2, tableName VARCHAR2, objectId VARCHAR2);

END ChangeLogger;

CREATE OR REPLACE PACKAGE BODY ChangeLogger AS
    PROCEDURE LogChange(columnName VARCHAR2, oldValue VARCHAR2, newValue VARCHAR2, transactionType VARCHAR2, tableName VARCHAR2, objectId VARCHAR2) IS
    BEGIN
        INSERT INTO ChangesLog(ID, COLUMNNAME, oldValue, newValue, transactionType, datetime, userName, TABLENAME, OBJECTID)
        VALUES (S_CHANGELOGS.nextval, LogChange.columnName, LogChange.oldValue, LogChange.newValue, LogChange.transactionType, SYSDATE, USER, LogChange.tableName, LogChange.objectId);
    END;
END ChangeLogger;


-- User Table Packages
CREATE OR REPLACE PACKAGE UserManager AS
    PROCEDURE insertUser( pFirstName varchar2, pBirthDate date, pUsername varchar2, pSecondName varchar2,
                          pLastName varchar2, pSecondLastName varchar2, pPassword varchar2, pPhotoUrl varchar2, pGenderId number, pIdType number, pUserTypeId number,
                        pDistrictId number, pAddress varchar2, pIdNumber number);
    PROCEDURE deleteUser(pUserId number);
    PROCEDURE updatePassword(pUserId number, pNewPassword varchar2);
    procedure updateUser(pUserId number, pFirstName varchar2, pBirthDate date, pLastName varchar2, pGenderId number, pDistrictId number, pAddress varchar2);
    function getUsers(pUserId number default null, pUsername varchar2, pName varchar2, pIdNumber number, pProvinceId varchar2, pDistrictId varchar2, pCityId varchar2) return sys_refcursor;
    function getUsersNoPasswordChanges(pName varchar2, pLastName varchar2, pStartDate date, pEndDate date) return sys_refcursor;
    function getUsersTotalPoints(pStartDate date, pEndDate date) return sys_refcursor;
    function getUsersRecyclingPoints(pCollectionCenterId number, pIdNumber number, pName varchar2) return sys_refcursor;
    FUNCTION getTop5Users RETURN SYS_REFCURSOR;
    function getUserByAge return sys_refcursor;
END UserManager;

create or replace package body UserManager as
    PROCEDURE insertUser(pFirstName varchar2, pBirthDate date, pUsername varchar2,
                         pSecondName varchar2,
                         pLastName varchar2, pSecondLastName varchar2, pPassword varchar2, pPhotoUrl varchar2,
                         pGenderId number, pIdType number, pUserTypeId number,
                         pDistrictId number, pAddress varchar2, pidNumber number)
        as
        begin
            insert into USERS (ID, FIRSTNAME, SECONDNAME, LASTNAME, SECONDLASTNAME, BIRTHDATE, USERNAME, PASSWORD,
                               IDNUMBER, ADDRESS, PHOTOURL, GENDERID, ID_TYPEID, USERTYPEID, DISTRICTID)
            values (S_USERS.nextval, pFirstName, pSecondName, pLastName,
                    pSecondLastName, pBirthDate, pUsername, pPassword, pidNumber,
                    pAddress, pPhotoUrl, pGenderId, pIdType, pUserTypeId, pDistrictId);
            commit;
            exception
                when OTHERS THEN
                    RAISE_APPLICATION_ERROR(-20001, 'Unexpected error ocurred.');
        end;
    PROCEDURE deleteUser(pUserId number)
        is
        eNoUserId exception;
        begin
            delete from USERS
            where USERS.id = pUserId;

            if SQL%NOTFOUND then
                raise deleteUser.eNoUserId;
            end if;

            COMMIT;

            exception
                when deleteUser.eNoUserId THEN
                    RAISE_APPLICATION_ERROR(-20001, 'User does not exist in the database.');
                when OTHERS then
                    RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');

        end;
    procedure updatePassword(pUserId number, pNewPassword varchar2)
        is
            eNoUserId exception;
        begin
            update USERS
            set password = pNewPassword
            where id = pUserId;

            if SQL%NOTFOUND then
                raise updatePassword.eNoUserId;
            end if;
            COMMIT;

            exception
                when updatePassword.eNoUserId then
                    RAISE_APPLICATION_ERROR(-20001, 'No user found.');
                when OTHERS then
                    RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');

        end;

    procedure updateUser(pUserId number, pFirstName varchar2, pBirthDate date, pLastName varchar2,
                         pGenderId number, pDistrictId number, pAddress varchar2)
        is
            eNoUserId exception;
        begin

            update USERS
            set FIRSTNAME = NVL(pFirstName, FIRSTNAME),
                BIRTHDATE = NVL(pBirthDate, BIRTHDATE),
                LASTNAME = NVL(pLastName, LASTNAME),
                GENDERID = NVL(pGenderId, GENDERID),
                DISTRICTID = NVL(pDistrictId, DISTRICTID),
                ADDRESS = NVL(pAddress, ADDRESS)
            where id = pUserId;

            if SQL%NOTFOUND then
                raise updateUser.eNoUserId;
            end if;

            COMMIT;
            exception
                when updateUser.eNoUserId then
                    RAISE_APPLICATION_ERROR(-20001, 'No user found.');
                when OTHERS then
                    RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');

        end;
    function getUsers(pUserId number default null, pUsername varchar2, pName varchar2, pIdNumber number, pProvinceId varchar2,
                      pDistrictId varchar2, pCityId varchar2)
        return sys_refcursor
        is
            vcUsers sys_refcursor;
        begin
            open vcUsers for
                select
                    u.FIRSTNAME || ' ' || u.SECONDNAME || ' ' || u.LASTNAME || ' ' || u.SECONDLASTNAME name,
                    u.BIRTHDATE,
                    u.username,
                    u.IDNUMBER,
                    u.ADDRESS,
                    d.NAME districtName,
                    c.NAME cityName,
                    p.NAME provinceName,
                    cc.name countryName,
                    u.PHOTOURL,
                    u.PASSWORD,
                    g.id gender,
                    it.NAME idTypeName,
                    ut.NAME userTypeName,
                    u.CREATEDBY,
                    u.CREATEDDATE,
                    u.UPDATEDBY,
                    u.UPDATEDDATE
                from
                    users u
                    inner join DISTRICT d on d.id = u.DISTRICTID
                    inner join city c on d.CITYID = c.ID
                    inner join province p on p.ID = c.PROVINCEID
                    inner join country cc on cc.id = p.COUNTRYID
                    inner join genders g on g.ID = u.GENDERID
                    inner join idType it on it.ID = u.ID_TYPEID
                    inner join USERTYPES ut on ut.id = u.USERTYPEID
                where
                    u.id = nvl(pUserId, u.id)
                    and (pName is null or (u.FIRSTNAME || u.SECONDNAME || u.LASTNAME || u.SECONDLASTNAME) LIKE '%' || nvl(pName, u.FIRSTNAME) ||'%')
                    and u.IDNUMBER = nvl(pIdNumber, u.IDNUMBER)
                    and p.ID = nvl(pProvinceId, p.ID)
                    and c.ID = nvl(pCityId, p.ID)
                    and d.ID = nvl(pDistrictId, p.ID)
                    and u.USERNAME = nvl(pUsername, u.USERNAME)
            ;
            RETURN (vcUsers);
            exception
                when OTHERS then
                    RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');

    end;
    function getUsersNoPasswordChanges(pName varchar2, pLastName varchar2, pStartDate date, pEndDate date)
        return sys_refcursor
        is
            vcUsers sys_refcursor;
        begin
            open vcUsers for
                select
                    u.FIRSTNAME || ' ' || u.LASTNAME name,
                    u.username,
                    u.IDNUMBER
                    
                from
                    users u
                where
                    u.id not in
                      (
                        select
                            cl.OBJECTID
                        from CHANGESLOG cl
                        where
                            cl.TRANSACTIONTYPE = 'UPDATE'
                            AND cl.COLUMNNAME = 'PASSWORD'
                            AND cl.TABLENAME = 'USERS'
                            and ( cl.DATETIME >= pStartDate or pStartDate is null)
                            and ( cl.DATETIME <= pEndDate or pEndDate is null)
                        )
                    and (pName is null or (u.FIRSTNAME || u.SECONDNAME) LIKE '%' || pName||'%')
                    and (pLastName is null or (u.LASTNAME || u.SECONDLASTNAME) LIKE '%' || pLastName ||'%')
                ;
            RETURN (vcUsers);
            exception
                when OTHERS then
                    RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');

        end;

    function getUsersTotalPoints(pStartDate date, pEndDate date)
        return sys_refcursor
        is
            vcUsers sys_refcursor;
        begin
            open vcUsers for
                with userPoints as (
                    select
                        u.id,
                        u.FIRSTNAME || ' ' || u.LASTNAME as name,
                        u.username,
                        sum(pc.POINTSPERKG * uc.KILOGRAMS) as earnedPoints,
                        sum(p.COST) as redeemedPoints,
                        (sum(pc.POINTSPERKG * uc.KILOGRAMS) - sum(p.COST)) as difference
                    from
                        users u
                        inner join USERXCOLLECTIONCENTER uc on uc.USERID = u.ID
                        inner join PointsConvertion pc on pc.ID = uc.POINTSCONVERTIONKEY
                        left join PRODUCTXUSER pu on pu.USERID = u.ID
                        inner join PRODUCT p on p.id = pu.PRODUCTID
                    where
                        (pStartDate is null or (uc.CREATEDDATETIME >= pStartDate or pu.CREATEDDATETIME >= pStartDate))
                        and (pEndDate   is null or (uc.CREATEDDATETIME <= pEndDate or pu.CREATEDDATETIME <= pEndDate))
                    group by
                        u.id, u.FIRSTNAME, u.LASTNAME, u.username
                )
                select
                    name,
                    username,
                    earnedPoints,
                    redeemedPoints,
                    difference
                from userPoints
                union all
                select
                    '*** TOTAL GENERAL ***' as name,
                    null as username,
                    sum(earnedPoints),
                    sum(redeemedPoints),
                    sum(difference)
                from userPoints;
            exception
                when OTHERS then
                    RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');
            RETURN (vcUsers);
        end;

        function getUsersRecyclingPoints(pCollectionCenterId number, pIdNumber number, pName varchar2)
            return sys_refcursor
            is
                vcUsers sys_refcursor;
            begin
                open vcUsers for
                with userPoints as (
                    select
                        u.id,
                        u.FIRSTNAME || ' ' || u.LASTNAME as name,
                        u.username,
                        sum(uc.KILOGRAMS) kgRecycled,
                        sum(pc.POINTSPERKG * uc.KILOGRAMS) as earnedPoints
                    from
                        COLLECTIONCENTER cc
                        inner join USERXCOLLECTIONCENTER uc on uc.COLLECTIONCENTER = cc.AUTORIZEDENTITYID
                        inner join PointsConvertion pc on pc.ID = uc.POINTSCONVERTIONKEY
                        inner join users u on uc.USERID = u.ID
                        inner join materialType mt on mt.id = pc.MATERIALTYPEID

                    where
                        cc.AUTORIZEDENTITYID = nvl(pCollectionCenterId, cc.AUTORIZEDENTITYID)
                        and u.IDNUMBER = nvl(pIdNumber, u.IDNUMBER)
                        and (pName is null or (u.FIRSTNAME || u.LASTNAME) like '%' || pName || '%')
                    group by
                        u.id, u.FIRSTNAME, u.LASTNAME, u.username, pc.name, uc.POINTSCONVERTIONKEY, uc.KILOGRAMS
                )
                select
                    name,
                    username,
                    earnedPoints,
                    kgRecycled
                from userPoints
                union all
                select
                    '*** TOTAL GENERAL ***' as name,
                    null as username,
                    sum(earnedPoints),
                    sum(kgRecycled)
                from userPoints;
            RETURN (vcUsers);
            exception
                when OTHERS then
                    RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');
            end;

/*4.14 Módulo de Estadísticas/ D
d) Top 5 de los usuarios con mayores puntajes a nivel general*/
        FUNCTION getTop5Users
            RETURN SYS_REFCURSOR
            AS
                vcTopUsers SYS_REFCURSOR;
            BEGIN
                OPEN vcTopUsers FOR
                    SELECT 
                        U.FIRSTNAME || ' ' || U.LASTNAME AS user_name,
                        U.USERNAME,
                        SUM(UC.kilograms * PC.pointsPerKg) AS total_points
                    FROM USERS U
                        JOIN userxcollectioncenter UC ON UC.userid = U.id
                        JOIN pointsconvertion PC ON PC.id = UC.pointsConvertion
                    GROUP BY U.FIRSTNAME, U.LASTNAME, u.USERNAME
                    ORDER BY total_points DESC
                    FETCH FIRST 5 ROWS ONLY;
            RETURN (vcTopUsers);
            EXCEPTION
                WHEN OTHERS THEN
                    RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while retrieving Top 5 users by points.');
            END;

        function getUserByAge
            return sys_refcursor
            is
            vcUsers SYS_REFCURSOR;
            begin
                open vcUsers for
                    select
                        case
                            when TRUNC(MONTHS_BETWEEN(sysdate, u.birthDate)/12) <= 18 then 'Under 19'
                            when TRUNC(MONTHS_BETWEEN(sysdate, u.birthDate)/12) between 19 and 30 then '19 - 30'
                            when TRUNC(MONTHS_BETWEEN(sysdate, u.birthDate)/12) between 31 and 45 then '31 - 45'
                            when TRUNC(MONTHS_BETWEEN(sysdate, u.birthDate)/12) between 46 and 55 then '46 - 55'
                            when TRUNC(MONTHS_BETWEEN(sysdate, u.birthDate)/12) between 56 and 65 then '56 - 65'
                            when TRUNC(MONTHS_BETWEEN(sysdate, u.birthDate)/12) between 66 and 75 then '66 - 75'
                            when TRUNC(MONTHS_BETWEEN(sysdate, u.birthDate)/12) between 76 and 85 then '76 - 85'
                            else 'Over 85'
                        end as ageGroup,
                        count(distinct u.id) as countUsers
                    from
                        USERS u
                    group by
                        case
                            when TRUNC(MONTHS_BETWEEN(sysdate, u.birthDate)/12) <= 18 then 'Under 19'
                            when TRUNC(MONTHS_BETWEEN(sysdate, u.birthDate)/12) between 19 and 30 then '19 - 30'
                            when TRUNC(MONTHS_BETWEEN(sysdate, u.birthDate)/12) between 31 and 45 then '31 - 45'
                            when TRUNC(MONTHS_BETWEEN(sysdate, u.birthDate)/12) between 46 and 55 then '46 - 55'
                            when TRUNC(MONTHS_BETWEEN(sysdate, u.birthDate)/12) between 56 and 65 then '56 - 65'
                            when TRUNC(MONTHS_BETWEEN(sysdate, u.birthDate)/12) between 66 and 75 then '66 - 75'
                            when TRUNC(MONTHS_BETWEEN(sysdate, u.birthDate)/12) between 76 and 85 then '76 - 85'
                            else 'Over 85'
                        end
                ;
                RETURN (vcUsers);
            EXCEPTION
                WHEN OTHERS THEN
                    RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while retrieving Top 5 users by points.');
            end;
end UserManager;

create or replace package phoneManager as
    procedure insertPhone(pIdUser number, pPhoneNumber number, pLabelId number);
    procedure updatePhone(pId number, pPhoneNumber number, pLabelId number);
    function getPhones(pIdUser number, pId number) return sys_refcursor;
    procedure deletePhone(pId number);
end;

create or replace package body phoneManager as
    procedure insertPhone(pIdUser number, pPhoneNumber number, pLabelId number)
        as
        begin
            insert into PHONES (ID, PHONENUMBER, LABELID, USERID)
            values(S_PHONES.nextval, pPhoneNumber, pLabelId, pIdUser);
            commit;
        exception
            when OTHERS then
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');
        end;
    procedure updatePhone(pId number, pPhoneNumber number, pLabelId number)
        is
            eInvalidId exception;
        begin
            update PHONES
            set PHONENUMBER = nvl(pPhoneNumber, PHONENUMBER),
                LABELID = nvl(pLabelId, LABELID)
            where ID = pId;

            if SQL%NOTFOUND THEN
                raise eInvalidId;
            end if;

            commit;
        exception
            when eInvalidId then
                RAISE_APPLICATION_ERROR(-20001, 'Phone not found');
            when OTHERS then
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');
        end;
    function getPhones(pIdUser number, pId number)
        return sys_refcursor
        is
        vPhones sys_refcursor;
        begin
            open vPhones for
                select
                    u.USERNAME,
                    p.PHONENUMBER,
                    l.DESCRIPTION,
                    p.CREATEDBY,
                    p.CREATEDDATE,
                    p.UPDATEDBY,
                    p.UPDATEDDATE
                from
                    PHONES p
                    inner join USERS u on u.id = p.USERID
                    inner join LABELS l on l.id = p.LABELID
                where
                    p.id = nvl(pId, p.id)
                    and p.USERID = nvl(pIdUser, p.USERID);
            return (vPhones);
        exception
            when OTHERS then
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred while getting phones.');
        end;

    procedure deletePhone(pId number)
        is
            eNoPhone exception;
        begin
            delete from PHONES
            where PHONEs.id = pId;

            if SQL%NOTFOUND then
                raise eNoPhone;
            end if;

            COMMIT;

            exception
                when eNoPhone THEN
                    RAISE_APPLICATION_ERROR(-20001, 'Phone does not exist in the database.');
                when OTHERS then
                    RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');

        end;
end phoneManager;


create or replace package emailManager as
    procedure insertEmail(pIdUser number, pEmailAddress varchar2, pLabelId number);
    procedure updateEmail(pId number, pEmailAddress varchar2, pLabelId number);
    function getEmails(pIdUser number, pId number) return sys_refcursor;
    procedure deleteEmail(pId number);
end;

create or replace package body emailManager as
    procedure insertEmail(pIdUser number, pEmailAddress varchar2, pLabelId number)
    as
    begin
        insert into EMAILS (ID, EMAILADDRESS, LABELID, USERID)
        values (S_EMAILS.nextval, pEmailAddress, pLabelId, pIdUser);
        commit;
    exception
        when OTHERS then
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');
    end;
    procedure updateEmail(pId number, pEmailAddress varchar2, pLabelId number)
        is
        eInvalidId exception;
    begin
        update EMAILS
        set EMAILADDRESS = nvl(pEmailAddress, EMAILADDRESS),
            LABELID      = nvl(pLabelId, LABELID)
        where ID = pId;

        if SQL%NOTFOUND THEN
            raise eInvalidId;
        end if;

        commit;
    exception
        when eInvalidId then
            RAISE_APPLICATION_ERROR(-20001, 'Email not found');
        when OTHERS then
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error ocurred.');
    end;
    function getEmails(pIdUser number, pId number)
        return sys_refcursor
        is
        vEmails sys_refcursor;
    begin
        open vEmails for
            select u.USERNAME,
                   e.EMAILADDRESS,
                   l.DESCRIPTION,
                   e.CREATEDBY,
                   e.CREATEDDATE,
                   e.UPDATEDBY,
                   e.UPDATEDDATE
            from EMAILS e
                     inner join USERS u on u.id = e.USERID
                     inner join LABELS l on l.id = e.LABELID
            where e.id = nvl(pId, e.id)
              and e.USERID = nvl(pIdUser, e.USERID);
        return (vEmails);
    exception
        when OTHERS then
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while getting emails.');
    end;
    procedure deleteEmail(pId number)
        is
        eInvalidId exception;
    begin
        delete
        from EMAILS
        where id = pId;

        if SQL%NOTFOUND then
            raise eInvalidId;
        end if;

    exception
        when eInvalidId then
            RAISE_APPLICATION_ERROR(-20001, 'Email not found.');
        when OTHERS then
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while deleting email.');
    end;
end emailManager;

create or replace package gendersManager as
    procedure insertGender(pName varchar2);
    procedure updateGender(pId number, pName varchar2);
    procedure deleteGender(pId number);
    function getGenders(pId number) return sys_refcursor;
end gendersManager;

create or replace package body gendersManager as
    procedure insertGender(pName varchar2)
        as
        begin
            insert into GENDERS (id, name)
            values (S_GENDERS.nextval, pName);
            commit;
        exception
            when OTHERS then
                raise_application_error(-20002, 'Unexpected error occurred while inserting gender.');
        end;
    procedure updateGender(pId number, pName varchar2)
        is
            eInvalidId exception;
        begin
            update GENDERS
            set name = pName
            where id = pId;

            if SQL%NOTFOUND then
                raise eInvalidId;
            end if;
        exception
            when eInvalidId then
                RAISE_APPLICATION_ERROR(-20001, 'Gender not found');
            when OTHERS then
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while updating gender.');
        end;

    procedure deleteGender(pId number)
        is
            eInvalidId exception;
        begin
            delete from GENDERS
            where id = pId;

            if SQL%NOTFOUND then
                raise eInvalidId;
            end if;

        exception
            when eInvalidId then
                RAISE_APPLICATION_ERROR(-20001, 'Gender not found.');
            when OTHERS then
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while deleting gender.');
        end;

    function getGenders(pId number)
        return sys_refcursor
        is
            vGenders sys_refcursor;
        begin
            open vGenders for
                select
                    g.ID,
                    NAME,
                    CREATEDBY,
                    CREATEDDATE,
                    UPDATEDBY,
                    UPDATEDDATE
                from
                    GENDERS g
                where
                    id = nvl(pId, id);
            return (vGenders);
        exception
            when OTHERS then
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while getting genders.');
        end;
end gendersManager;


create or replace package userTypesManager as
    procedure insertUserType(pName varchar2);
    procedure updateUserType(pId number, pName varchar2);
    procedure deleteUserType(pId number);
    function getUserTypes(pId number) return sys_refcursor;
end userTypesManager;

create or replace package body userTypesManager as
    procedure insertUserType (pName varchar2)
        as
        begin
            insert into USERTYPES (id, name)
            values (S_USERTYPES.nextval, pName);
            commit;
        exception
            when OTHERS then
                raise_application_error(-20002, 'Unexpected error occurred while inserting user type.');
        end;
    procedure updateUserType(pId number, pName varchar2)
        is
            eInvalidId exception;
        begin
            update USERTYPES
            set name = pName
            where id = pId;

            if SQL%NOTFOUND then
                raise eInvalidId;
            end if;
        exception
            when eInvalidId then
                RAISE_APPLICATION_ERROR(-20001, 'User Type not found');
            when OTHERS then
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while updating user type.');
        end;

    procedure deleteUserType(pId number)
        is
            eInvalidId exception;
        begin
            delete from USERTYPES
            where id = pId;

            if SQL%NOTFOUND then
                raise eInvalidId;
            end if;

        exception
            when eInvalidId then
                RAISE_APPLICATION_ERROR(-20001, 'User Type not found.');
            when OTHERS then
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while deleting user Types.');
        end;

    function getUserTypes (pId number)
        return sys_refcursor
        is
            vUserTypes sys_refcursor;
        begin
            open vUserTypes for
                select
                    ID,
                    NAME,
                    CREATEDBY,
                    CREATEDDATE,
                    UPDATEDBY,
                    UPDATEDDATE
                from
                    USERTYPES ut
                where
                    id = nvl(pId, id);
            return (vUserTypes);
        exception
            when OTHERS then
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while getting user types.');
        end;
end userTypesManager;

create or replace package idTypeManager as
    procedure insertIdType(pName varchar2);
    procedure updateIdType(pId number, pName varchar2);
    procedure deleteIdType(pId number);
    function getIdTypes(pId number) return sys_refcursor;
end idTypeManager;

create or replace package body idTypeManager as
    procedure insertIdType (pName varchar2)
        as
        begin
            insert into IDTYPE (id, name)
            values (S_IDTYPES.nextval, pName);
            commit;
        exception
            when OTHERS then
                raise_application_error(-20002, 'Unexpected error occurred while inserting id type.');
        end;
    procedure updateIdType(pId number, pName varchar2)
        is
            eInvalidId exception;
        begin
            update IDTYPE
            set name = pName
            where id = pId;

            if SQL%NOTFOUND then
                raise eInvalidId;
            end if;
        exception
            when eInvalidId then
                RAISE_APPLICATION_ERROR(-20001, 'Id Type not found');
            when OTHERS then
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while updating id type.');
        end;

    procedure deleteIdType(pId number)
        is
            eInvalidId exception;
        begin
            delete from IDTYPE
            where id = pId;

            if SQL%NOTFOUND then
                raise eInvalidId;
            end if;

        exception
            when eInvalidId then
                RAISE_APPLICATION_ERROR(-20001, 'Id Type not found.');
            when OTHERS then
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while deleting id Types.');
        end;

    function getIdTypes (pId number)
        return sys_refcursor
        is
            vIdTypes sys_refcursor;
        begin
            open vIdTypes for
                select
                    ID,
                    NAME,
                    CREATEDBY,
                    CREATEDDATE,
                    UPDATEDBY,
                    UPDATEDDATE
                from
                    USERTYPES ut
                where
                    id = nvl(pId, id);
            return (vIdTypes);
        exception
            when OTHERS then
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while getting id types.');
        end;
end idTypeManager;


create or replace package labelsManager as
    procedure insertLabel(pDescription varchar2);
    procedure updateLabel(pId number, pDescription varchar2);
    procedure deleteLabel(pId number);
    function getLabels(pId number) return sys_refcursor;
end labelsManager;

create or replace package body labelsManager as
    procedure insertLabel (pDescription varchar2)
        as
        begin
            insert into LABELS (id, DESCRIPTION)
            values (S_LABELS.nextval, pDescription);
            commit;
        exception
            when OTHERS then
                raise_application_error(-20002, 'Unexpected error occurred while inserting id type.');
        end;
    procedure updateLabel(pId number, pDescription varchar2)
        is
            eInvalidId exception;
        begin
            update LABELS
            set  DESCRIPTION = pDescription
            where id = pId;

            if SQL%NOTFOUND then
                raise eInvalidId;
            end if;
        exception
            when eInvalidId then
                RAISE_APPLICATION_ERROR(-20001, 'Label not found');
            when OTHERS then
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while updating label.');
        end;

    procedure deleteLabel(pId number)
        is
            eInvalidId exception;
        begin
            delete from LABELS
            where id = pId;

            if SQL%NOTFOUND then
                raise eInvalidId;
            end if;

        exception
            when eInvalidId then
                RAISE_APPLICATION_ERROR(-20001, 'Label not found.');
            when OTHERS then
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while deleting label.');
        end;

    function getLabels (pId number)
        return sys_refcursor
        is
            vLabels sys_refcursor;
        begin
            open vLabels for
                select
                    ID,
                    DESCRIPTION,
                    CREATEDBY,
                    CREATEDDATE,
                    UPDATEDBY,
                    UPDATEDDATE
                from
                    LABELS
                where
                    id = nvl(pId, id);
            return (vLabels);
        exception
            when OTHERS then
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while getting labels.');
        end;
end labelsManager;

-- DistrictManager Package
CREATE OR REPLACE PACKAGE DistrictManager AS
    PROCEDURE insertDistrict(pName VARCHAR2, pCityId NUMBER);
    PROCEDURE deleteDistrict(pDistrictId NUMBER);
    PROCEDURE updateDistrict(pDistrictId NUMBER, pName VARCHAR2, pCityId NUMBER);
    FUNCTION getDistricts(pDistrictId NUMBER DEFAULT NULL, pName VARCHAR2 DEFAULT NULL, pCityId NUMBER DEFAULT NULL)
        RETURN SYS_REFCURSOR;
END DistrictManager;

CREATE OR REPLACE PACKAGE BODY DistrictManager AS
    PROCEDURE insertDistrict(pName VARCHAR2, pCityId NUMBER)
    AS
    BEGIN
        INSERT INTO District (id, name, cityId)
        VALUES (s_district.NEXTVAL, pName, pCityId);
        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20001, 'Unexpected error occurred.');
    END insertDistrict;

    PROCEDURE deleteDistrict(pDistrictId NUMBER)
    IS
    eNoDistrictId EXCEPTION;
    BEGIN
        DELETE FROM District
        WHERE DISTRICT.id = pDistrictId;

        IF SQL%NOTFOUND THEN
            RAISE eNoDistrictId;
        END IF;
        COMMIT;
    EXCEPTION
        WHEN deleteDistrict.eNoDistrictId THEN
            RAISE_APPLICATION_ERROR(-20001, 'District does not exist in the database.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');

    END deleteDistrict;

    PROCEDURE updateDistrict(pDistrictId NUMBER, pName VARCHAR2, pCityId NUMBER)
    IS
        eNoDistrictId EXCEPTION;
    BEGIN
        UPDATE District
        SET name = NVL(pName, name),
            cityId = NVL(pCityId, cityId)
        WHERE id = pDistrictId;
        IF SQL%NOTFOUND THEN
            RAISE updateDistrict.eNoDistrictId;
        END IF;
        COMMIT;
    EXCEPTION
        WHEN eNoDistrictId THEN
            RAISE_APPLICATION_ERROR(-20001, 'District not found.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');

    END updateDistrict;

    FUNCTION getDistricts(
        pDistrictId NUMBER DEFAULT NULL,
        pName VARCHAR2 DEFAULT NULL,
        pCityId NUMBER DEFAULT NULL
    ) RETURN SYS_REFCURSOR
    AS
        vcDistricts SYS_REFCURSOR;
    BEGIN
        OPEN vcDistricts FOR
            SELECT
                d.name,
                d.updatedBy,
                d.updatedDateTime,
                d.createdBy,
                d.createdDateTime,
                d.cityId,
                c.name cityName
            FROM
                district d
                inner join city c ON d.cityId = c.id
            WHERE
                d.id = NVL(pDistrictId, d.id)
                AND d.name LIKE '%' || NVL(pName, d.name) || '%'
                AND c.ID = NVL(pCityId, c.Id);
        RETURN (vcDistricts);
    EXCEPTION
        when NO_DATA_FOUND then
            RAISE_APPLICATION_ERROR(-20001, 'No district found.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');

    END getDistricts;

END DistrictManager;

-- CityManager Package
CREATE OR REPLACE PACKAGE CityManager AS
    PROCEDURE insertCity(pName VARCHAR2, pProvinceId NUMBER);
    PROCEDURE deleteCity(pCityId NUMBER);
    PROCEDURE updateCity(pCityId NUMBER, pName VARCHAR2, pProvinceId NUMBER);
    FUNCTION getCities(pCityId NUMBER DEFAULT NULL, pName VARCHAR2 DEFAULT NULL, pProvinceId NUMBER DEFAULT NULL)
        RETURN SYS_REFCURSOR;
END CityManager;

CREATE OR REPLACE PACKAGE BODY CityManager AS

    PROCEDURE insertCity(pName VARCHAR2, pProvinceId NUMBER)
    AS
    BEGIN
        INSERT INTO City (id, name, provinceId)
        VALUES (s_city.NEXTVAL, pName, pProvinceId);
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20001, 'Unexpected error occurred.');
    END insertCity;

    PROCEDURE deleteCity(pCityId NUMBER)
    AS
        eNoCityId EXCEPTION;
    BEGIN
        DELETE FROM City
        WHERE City.id = pCityId;

        IF SQL%NOTFOUND THEN
            RAISE deleteCity.eNoCityId;
        END IF;
        COMMIT;
    EXCEPTION
        WHEN deleteCity.eNoCityId THEN
            RAISE_APPLICATION_ERROR(-20001, 'City does not exist in the database.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');

    END deleteCity;

    PROCEDURE updateCity(pCityId NUMBER, pName VARCHAR2, pProvinceId NUMBER)
    AS
        eNoCityId EXCEPTION;
    BEGIN
        UPDATE City
        SET name = NVL(pName, name),
            provinceId = NVL(pProvinceId, provinceId)
        WHERE id = pCityId;
        IF SQL%NOTFOUND THEN
            RAISE updateCity.eNoCityId;
        END IF;
        COMMIT;
    EXCEPTION
        WHEN updateCity.eNoCityId THEN
            RAISE_APPLICATION_ERROR(-20001, 'City not found.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');

    END updateCity;

    FUNCTION getCities(
        pCityId NUMBER DEFAULT NULL,
        pName VARCHAR2 DEFAULT NULL,
        pProvinceId NUMBER DEFAULT NULL)
    RETURN SYS_REFCURSOR
    AS
        vcCities SYS_REFCURSOR;
    BEGIN
        OPEN vcCities FOR
            SELECT
                c.name,
                c.updatedBy,
                c.updatedDateTime,
                c.createdBy,
                c.createdDateTime,
                c.provinceId
            FROM
                City c
                INNER JOIN Province p ON c.provinceId = p.id
            WHERE
                c.id = NVL(pCityId, c.id)
                AND c.name LIKE '%' || NVL(pName, c.name) || '%'
                AND p.ID = NVL(pProvinceId, p.id);
        RETURN (vcCities);
    EXCEPTION
        when NO_DATA_FOUND then
            RAISE_APPLICATION_ERROR(-20001, 'No city found.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');


    END getCities;

END CityManager;

-- ProvinceManager Package
CREATE OR REPLACE PACKAGE ProvinceManager AS
    PROCEDURE insertProvince(pName VARCHAR2, pCountryId NUMBER);
    PROCEDURE deleteProvince(pProvinceId NUMBER);
    PROCEDURE updateProvince(pProvinceId NUMBER, pName VARCHAR2, pCountryId NUMBER);
    FUNCTION getProvinces(pProvinceId NUMBER DEFAULT NULL, pName VARCHAR2 DEFAULT NULL, pCountryId NUMBER DEFAULT NULL)
        RETURN SYS_REFCURSOR;
END ProvinceManager;

CREATE OR REPLACE PACKAGE BODY ProvinceManager AS

    PROCEDURE insertProvince(pName VARCHAR2, pCountryId NUMBER)
    AS
    BEGIN
        INSERT INTO Province (id, name, countryId)
        VALUES (s_province.NEXTVAL, pName, pCountryId);
        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20001, 'Unexpected error occurred.');
    END insertProvince;

    PROCEDURE deleteProvince(pProvinceId NUMBER)
    AS
        eNoProvinceId EXCEPTION;
    BEGIN
        DELETE FROM Province
        WHERE Province.id = pProvinceId;

        IF SQL%NOTFOUND THEN
            RAISE eNoProvinceId;
        END IF;
        COMMIT;
    EXCEPTION
        WHEN deleteProvince.eNoProvinceId THEN
            RAISE_APPLICATION_ERROR(-20001, 'Province does not exist in the database.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');

    END deleteProvince;

    PROCEDURE updateProvince(pProvinceId NUMBER, pName VARCHAR2, pCountryId NUMBER)
    AS
        eNoProvinceId EXCEPTION;
    BEGIN
        UPDATE Province
        SET name = NVL(pName, name),
            countryId = NVL(pCountryId, countryId)
        WHERE id = pProvinceId;
        IF SQL%NOTFOUND THEN
            RAISE updateProvince.eNoProvinceId;
        END IF;
        COMMIT;
    EXCEPTION
        WHEN updateProvince.eNoProvinceId THEN
            RAISE_APPLICATION_ERROR(-20001, 'Province not found.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');
    END updateProvince;

    FUNCTION getProvinces(
        pProvinceId NUMBER DEFAULT NULL,
        pName VARCHAR2 DEFAULT NULL,
        pCountryId  NUMBER DEFAULT NULL)
    RETURN SYS_REFCURSOR
    AS
        vcProvinces SYS_REFCURSOR;
    BEGIN
        OPEN vcProvinces FOR
            SELECT
                p.name AS provinceName,
                p.updatedBy,
                p.updatedDateTime,
                p.createdBy,
                p.createdDateTime,
                cc.Id AS countryId
            FROM
                Province p
                INNER JOIN Country cc ON p.countryId = cc.id
            WHERE
                p.id = NVL(pProvinceId, p.id)
                AND p.name LIKE '%' || NVL(pName, p.name) || '%'
                AND cc.id = NVL(pCountryId, cc.id);
        RETURN (vcProvinces);
    EXCEPTION
        when NO_DATA_FOUND then
                RAISE_APPLICATION_ERROR(-20001, 'No province found.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');

    END getProvinces;

END ProvinceManager;

-- CountryManager Package
CREATE OR REPLACE PACKAGE CountryManager AS
    PROCEDURE insertCountry(pName VARCHAR2);
    PROCEDURE deleteCountry(pCountryId NUMBER);
    PROCEDURE updateCountry(pCountryId NUMBER, pName VARCHAR2);
    FUNCTION getCountries(pCountryId NUMBER DEFAULT NULL, pName VARCHAR2 DEFAULT NULL)
        RETURN SYS_REFCURSOR;
END CountryManager;

CREATE OR REPLACE PACKAGE BODY CountryManager AS

    PROCEDURE insertCountry(pName VARCHAR2)
    AS
    BEGIN
        INSERT INTO Country (id, name)
        VALUES (s_country.NEXTVAL, pName);
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20001, 'Unexpected error occurred.');
    END insertCountry;

    PROCEDURE deleteCountry(pCountryId NUMBER)
    AS
        eNoCountryId EXCEPTION;
    BEGIN
        DELETE FROM Country
        WHERE Country.id = pCountryId;

        IF SQL%NOTFOUND THEN
            RAISE deleteCountry.eNoCountryId;
        END IF;
        COMMIT;
    EXCEPTION
        WHEN deleteCountry.eNoCountryId THEN
            RAISE_APPLICATION_ERROR(-20001, 'Country does not exist in the database.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');

    END deleteCountry;

    PROCEDURE updateCountry(pCountryId NUMBER, pName VARCHAR2)
    AS
        eNoCountryId EXCEPTION;
    BEGIN
        UPDATE Country
        SET name = NVL(pName, name)
        WHERE id = pCountryId;

        IF SQL%NOTFOUND THEN
            RAISE eNoCountryId;
        END IF;
        COMMIT;
    EXCEPTION
        WHEN eNoCountryId THEN
            RAISE_APPLICATION_ERROR(-20001, 'Country not found.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');

    END updateCountry;

    FUNCTION getCountries(
        pCountryId NUMBER DEFAULT NULL,
        pName      VARCHAR2 DEFAULT NULL
    ) RETURN SYS_REFCURSOR
    AS
        vcCountries SYS_REFCURSOR;
    BEGIN
        OPEN vcCountries FOR
            SELECT
                name,
                createdBy,
                createdDateTime,
                updatedBy,
                updatedDateTime
            FROM
                Country
            WHERE
                id = NVL(pCountryId, id)
                AND name LIKE '%' || NVL(pName, name) || '%';
        RETURN (vcCountries);
    EXCEPTION
        when NO_DATA_FOUND then
            RAISE_APPLICATION_ERROR(-20001, 'No country found.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');

    END getCountries;

END CountryManager;

-- ProductManager Package
CREATE OR REPLACE PACKAGE ProductManager AS
    PROCEDURE insertProduct(pDescription VARCHAR2, pPhotoUrl VARCHAR2, pCost FLOAT, pAuthorizedEntityId NUMBER);
    PROCEDURE deleteProduct(pProductId NUMBER);
    PROCEDURE updateProduct(pProductId NUMBER, pDescription VARCHAR2, pPhotoUrl VARCHAR2, pCost FLOAT, pAuthorizedEntityId NUMBER);
    FUNCTION getProducts(pProductId NUMBER DEFAULT NULL, pDescription VARCHAR2 DEFAULT NULL, pAuthorizedEntityId NUMBER DEFAULT NULL) RETURN SYS_REFCURSOR;
    FUNCTION getProductsRedeemRanking RETURN SYS_REFCURSOR;
    FUNCTION getProductsByCommerce(commerce_id IN NUMBER) RETURN SYS_REFCURSOR;
    FUNCTION getTop5Products RETURN SYS_REFCURSOR;
    FUNCTION getTotalProductsRedeemed RETURN SYS_REFCURSOR;
END ProductManager;

CREATE OR REPLACE PACKAGE BODY ProductManager AS

    PROCEDURE insertProduct(pDescription VARCHAR2, pPhotoUrl VARCHAR2, pCost FLOAT, pAuthorizedEntityId NUMBER)
    AS
    BEGIN
        INSERT INTO Product (id, description, photoUrl, cost, authorizedEntityId)
        VALUES (s_product.NEXTVAL, pDescription, pPhotoUrl, pCost, pAuthorizedEntityId);
        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20001, 'Unexpected error occurred.');
    END insertProduct;

    PROCEDURE deleteProduct(pProductId NUMBER)
    AS
        eNoProductId EXCEPTION;
    BEGIN
        DELETE FROM Product
        WHERE Product.id = pProductId;

        IF SQL%NOTFOUND THEN
            RAISE deleteProduct.eNoProductId;
        END IF;
        COMMIT;
    EXCEPTION
        WHEN deleteProduct.eNoProductId THEN
            RAISE_APPLICATION_ERROR(-20001, 'Product does not exist in the database.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');

    END deleteProduct;

    PROCEDURE updateProduct(pProductId NUMBER, pDescription VARCHAR2, pPhotoUrl VARCHAR2, pCost FLOAT, pAuthorizedEntityId NUMBER)
    AS
        eNoProductId EXCEPTION;
    BEGIN
        UPDATE Product
        SET description = NVL(pDescription, description),
            photoUrl = NVL(pPhotoUrl, photoUrl),
            cost  = NVL(pCost, cost),
            authorizedEntityId = NVL(pAuthorizedEntityId, authorizedEntityId)
        WHERE id = pProductId;

        IF SQL%NOTFOUND THEN
            RAISE updateProduct.eNoProductId;
        END IF;
        COMMIT;
    EXCEPTION
        WHEN updateProduct.eNoProductId THEN
            RAISE_APPLICATION_ERROR(-20001, 'Product not found.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');

    END updateProduct;

    FUNCTION getProducts(
        pProductId NUMBER DEFAULT NULL,
        pDescription VARCHAR2 DEFAULT NULL,
        pAuthorizedEntityId NUMBER DEFAULT NULL)
    RETURN SYS_REFCURSOR
    AS
        vcProducts SYS_REFCURSOR;
    BEGIN
        OPEN vcProducts FOR
            SELECT
                p.id,
                p.description,
                p.cost,
                p.photoUrl,
                p.createdBy,
                p.createdDateTime,
                p.updatedBy,
                p.updatedDateTime,
                ae.name AS authorizedEntity
            FROM
                Product p
                INNER JOIN AUTORIZEDENTITY ae ON p.authorizedEntityId = ae.id
            WHERE
                p.id = NVL(pProductId, p.id)
                AND p.description LIKE '%' || NVL(pDescription, p.description) || '%'
                AND ae.id = NVL(pAuthorizedEntityId, ae.id);
        RETURN (vcProducts);
    EXCEPTION
        when NO_DATA_FOUND then
            RAISE_APPLICATION_ERROR(-20001, 'No product found.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');

    END getProducts;

/*4.10 Módulo de Consultas/ D
   Listado de productos con mayor solicitud de canje y sus respectivos comercios.
*/
    FUNCTION getProductsRedeemRanking
    RETURN SYS_REFCURSOR
    AS
        vcProductRanking SYS_REFCURSOR;
    BEGIN
        OPEN vcProductRanking FOR
            SELECT
                P.ID,
                P.PHOTOURL,
                P.description AS product_description,
                AE.name AS commerce_name,
                SUM(PU.quantity) AS total_canje
            FROM PRODUCT P
                INNER JOIN PRODUCTXUSER PU ON PU.productid = P.id
                INNER JOIN AUTORIZEDENTITY AE ON AE.id = P.authorizedentityid
            GROUP BY
                P.ID,
                P.PHOTOURL,
                P.description,
                AE.name
            ORDER BY
                total_canje DESC;
        RETURN (vcProductRanking);
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while retrieving Product ranking.');
    END getProductsRedeemRanking;

/*4.11 Módulo de Consultas para comercios afiliados A,B,C*/
/*A) Listado de productos que ofrecen los comercios afiliados*/
    FUNCTION getProductsByCommerce(commerce_id IN NUMBER)
    RETURN SYS_REFCURSOR
    AS
        vcProductsByCommerce SYS_REFCURSOR;
    BEGIN
        OPEN vcProductsByCommerce FOR
            SELECT
                P.ID,
                P.description AS product_description,
                P.cost AS cost,
                P.photourl AS photo
            FROM product P
                INNER JOIN AUTORIZEDENTITY AE ON AE.id = P.authorizedentityid
            WHERE P.authorizedentityid = nvl(commerce_id, P.authorizedentityid)
            ORDER BY P.description;
        RETURN (vcProductsByCommerce);
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while retrieving Products by Commerce.');
    END getProductsByCommerce;

/*B) Top 5 de productos más canjeados*/
    FUNCTION getTop5Products
    RETURN SYS_REFCURSOR
    AS
        vcTopProducts SYS_REFCURSOR;
    BEGIN
        OPEN vcTopProducts FOR
            SELECT
                P.description AS product_description,
                AE.name AS commerce_name,
                SUM(PU.quantity) AS total_redemptions
            FROM product P
                JOIN productxuser PU ON PU.productid = P.id
                JOIN AUTORIZEDENTITY AE ON AE.id = P.authorizedentityid
            GROUP BY P.description, AE.NAME
            ORDER BY total_redemptions DESC
            FETCH FIRST 5 ROWS ONLY;
        RETURN (vcTopProducts);
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while retrieving Top 5 Products.');


    END getTop5Products;

/*4.14 Módulo de Estadísticas/ D
c) Total de productos canjeados por mes y por año.*/
    FUNCTION getTotalProductsRedeemed
    RETURN SYS_REFCURSOR
    AS
        vcProductsRedeemed SYS_REFCURSOR;
    BEGIN
        OPEN vcProductsRedeemed FOR
            SELECT
                EXTRACT(YEAR FROM pu.createdDateTime)  AS year,
                EXTRACT(MONTH FROM pu.createdDateTime) AS month,
                SUM(pu.quantity) AS total_redeemed
            FROM productxuser pu
            GROUP BY
                EXTRACT(YEAR FROM pu.createdDateTime),
                EXTRACT(MONTH FROM pu.createdDateTime)
            ORDER BY
                year DESC, month DESC;
        RETURN (vcProductsRedeemed);
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while retrieving total redemptions by month and year.');
    END getTotalProductsRedeemed;
END ProductManager;

CREATE OR REPLACE PACKAGE ProductxUserManager AS
    PROCEDURE insertProductRedeem(pUserId NUMBER, pProductId  NUMBER, pQuantity   NUMBER);
    FUNCTION getProductRedeems(pUserId NUMBER DEFAULT NULL, pProductId NUMBER) return sys_refcursor;
END ProductxUserManager;

CREATE OR REPLACE PACKAGE BODY ProductxUserManager AS

    PROCEDURE insertProductRedeem(pUserId NUMBER, pProductId  NUMBER, pQuantity   NUMBER)
    IS
        vTotalUserPoints NUMBER := 0;
        vProductCost NUMBER := 0;
        vTotalCost NUMBER := 0;
        vSpentPoints NUMBER := 0;
        eNotEnoughPoints exception;
    BEGIN
        -- 1) Calcular puntos totales disponibles del usuario
        SELECT NVL(SUM(TO_NUMBER(uc.kilograms) * pc.pointsPerKg), 0)
        INTO vTotalUserPoints
        FROM UserXCollectionCenter uc
        JOIN PointsConvertion pc ON pc.id = uc.pointsConvertion
        WHERE uc.userId = pUserId;

        -- 1) Calcular puntos gastados por el  usuario
        SELECT NVL(SUM(TO_NUMBER(uc.QUANTITY) * p.COST), 0)
        INTO vSpentPoints
        FROM PRODUCTXUSER uc
            INNER JOIN PRODUCT p on p.ID = uc.PRODUCTID
        WHERE uc.userId = pUserId;

        vTotalUserPoints := vTotalUserPoints - vSpentPoints;

        -- 2) Obtener costo del producto
        SELECT cost
        INTO vProductCost
        FROM Product
        WHERE id = pProductId;

        -- 3) Calcular costo total del canje
        vTotalCost := vProductCost * pQuantity;

        -- 4) Validación de puntos suficientes
        IF vTotalUserPoints < vTotalCost THEN
            raise eNotEnoughPoints;
        end if;

        -- 5) Insertar canje en ProductXUser
        INSERT INTO ProductXUser (id, userId, productId, quantity)
        VALUES (s_productxuser.NEXTVAL, pUserId, pProductId, pQuantity);

        COMMIT;

    EXCEPTION
        WHEN eNotEnoughPoints THEN
            RAISE_APPLICATION_ERROR(-20001, 'User does not have enough points to redeem this product.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred during product redemption.');
    END insertProductRedeem;

    function getProductRedeems (pUserId NUMBER DEFAULT NULL, pProductId NUMBER)
        return sys_refcursor
        is
            vRedeems sys_refcursor;
        begin
            open vRedeems for
                select
                    u.FIRSTNAME || ' ' || u.LASTNAME Name,
                    p.DESCRIPTION,
                    pu.QUANTITY,
                    pu.QUANTITY * p.COST totalCost
                from
                    PRODUCTXUSER pu
                    inner join PRODUCT p on p.ID = pu.PRODUCTID
                    inner join USERS u on u.id = pu.USERID
                where
                    pu.USERID = nvl(pUserId, pu.USERID)
                    and pu.PRODUCTID = nvl(pProductId, pu.PRODUCTID);
            return (vRedeems);
        EXCEPTION
            WHEN OTHERS THEN
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred during product redemption.');
        end;
END ProductxUserManager;

CREATE OR REPLACE PACKAGE PointsConvertionManager AS
    PROCEDURE insertPointsConvertion(pName NUMBER, pPointsPerKg NUMBER, pValueInCurrency NUMBER, pCurrencyId NUMBER, pMaterialTypeId NUMBER);
    PROCEDURE deletePointsConvertion(pId NUMBER);
    PROCEDURE updatePointsConvertion(pId NUMBER, pName NUMBER, pPointsPerKg NUMBER, pValueInCurrency NUMBER, pCurrencyId NUMBER, pMaterialTypeId NUMBER);
    FUNCTION getPointsConvertion(pId NUMBER DEFAULT NULL, pName NUMBER DEFAULT NULL, pCurrencyId NUMBER DEFAULT NULL, pMaterialTypeId NUMBER DEFAULT NULL) RETURN SYS_REFCURSOR;
END PointsConvertionManager;

CREATE OR REPLACE PACKAGE BODY PointsConvertionManager AS

    PROCEDURE insertPointsConvertion(pName NUMBER, pPointsPerKg NUMBER, pValueInCurrency NUMBER, pCurrencyId NUMBER, pMaterialTypeId NUMBER)
    AS
    BEGIN
        INSERT INTO PointsConvertion (id, name, pointsPerKg, valueInCurrency, CurrencyId, MaterialTypeId)
        VALUES (s_pointsconvertion.NEXTVAL, pName, pPointsPerKg, pValueInCurrency, pCurrencyId, pMaterialTypeId);
        COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20001, 'Unexpected error occurred.');
    END insertPointsConvertion;

    PROCEDURE deletePointsConvertion(pId NUMBER)
    AS
        eNoRecordFound EXCEPTION;
    BEGIN
        DELETE FROM PointsConvertion
        WHERE id = pId;

        IF SQL%NOTFOUND THEN
            RAISE deletePointsConvertion.eNoRecordFound;
        END IF;
        COMMIT;
    EXCEPTION
        WHEN deletePointsConvertion.eNoRecordFound THEN
            RAISE_APPLICATION_ERROR(-20001, 'PointsConvertion record does not exist.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');
    END deletePointsConvertion;

    PROCEDURE updatePointsConvertion(pId NUMBER, pName NUMBER, pPointsPerKg NUMBER, pValueInCurrency NUMBER, pCurrencyId NUMBER, pMaterialTypeId NUMBER)
    AS
        eNoRecordFound EXCEPTION;
    BEGIN
        UPDATE PointsConvertion
        SET name = NVL(pName, name),
            pointsPerKg = NVL(pPointsPerKg, pointsPerKg),
            valueInCurrency = NVL(pValueInCurrency, valueInCurrency),
            CurrencyId = NVL(pCurrencyId, CurrencyId),
            MaterialTypeId = NVL(pMaterialTypeId, MaterialTypeId)
        WHERE id = pId;

        IF SQL%NOTFOUND THEN
            RAISE updatePointsConvertion.eNoRecordFound;
        END IF;

        COMMIT;

    EXCEPTION
        WHEN updatePointsConvertion.eNoRecordFound THEN
            RAISE_APPLICATION_ERROR(-20001, 'PointsConvertion record not found.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');
    END updatePointsConvertion;

    FUNCTION getPointsConvertion(pId NUMBER DEFAULT NULL, pName NUMBER DEFAULT NULL, pCurrencyId NUMBER DEFAULT NULL, pMaterialTypeId NUMBER DEFAULT NULL)
    RETURN SYS_REFCURSOR
    AS
        vcPointsConvertion SYS_REFCURSOR;
    BEGIN
        OPEN vcPointsConvertion FOR
            SELECT
                pc.id,
                pc.name,
                pc.pointsPerKg,
                pc.valueInCurrency,
                c.code AS currencyCode,
                m.name AS materialTypeName
            FROM
                PointsConvertion pc
                INNER JOIN Currency c ON pc.CurrencyId = c.id
                INNER JOIN MaterialType m ON pc.MaterialTypeId = m.id
            WHERE
                pc.id = NVL(pId, pc.id)
                AND pc.name LIKE '%' || NVL(pName, pc.name) || '%'
                AND c.id = NVL(pCurrencyId, c.id)
                AND m.id = NVL(pMaterialTypeId, m.id);
        RETURN (vcPointsConvertion);
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred.');
    END getPointsConvertion;
END PointsConvertionManager;

CREATE OR REPLACE PACKAGE CurrencyManager AS
    PROCEDURE insertCurrency(pCode VARCHAR2, pSymbol VARCHAR2);
    PROCEDURE deleteCurrency(pCurrencyId NUMBER);
    PROCEDURE updateCurrency(pCurrencyId NUMBER, pCode VARCHAR2, pSymbol VARCHAR2);
    FUNCTION getCurrencies(pCurrencyId NUMBER DEFAULT NULL, pCode VARCHAR2 DEFAULT NULL, pSymbol VARCHAR2 DEFAULT NULL) RETURN SYS_REFCURSOR;
END CurrencyManager;

CREATE OR REPLACE PACKAGE BODY CurrencyManager AS

    PROCEDURE insertCurrency(pCode VARCHAR2, pSymbol VARCHAR2)
        AS
        BEGIN
            INSERT INTO Currency (id, code, symbol)
            VALUES (s_currency.NEXTVAL, pCode, pSymbol);

            COMMIT;
        EXCEPTION
            WHEN OTHERS THEN
                RAISE_APPLICATION_ERROR(-20001, 'Unexpected error occurred while inserting currency.');
        END insertCurrency;

    PROCEDURE deleteCurrency(pCurrencyId NUMBER)
        AS
            eNoRecordFound EXCEPTION;
        BEGIN
            DELETE FROM Currency
            WHERE id = pCurrencyId;

            IF SQL%NOTFOUND THEN
                RAISE deleteCurrency.eNoRecordFound;
            END IF;
            COMMIT;
        EXCEPTION
            WHEN deleteCurrency.eNoRecordFound THEN
                RAISE_APPLICATION_ERROR(-20001, 'Currency not found.');
            WHEN OTHERS THEN
                RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while deleting currency.');

        END deleteCurrency;

    PROCEDURE updateCurrency(pCurrencyId NUMBER, pCode VARCHAR2, pSymbol VARCHAR2)
    AS
        eNoRecordFound EXCEPTION;
    BEGIN
        UPDATE Currency
        SET code = NVL(pCode, code),
            symbol = NVL(pSymbol, symbol)
        WHERE id = pCurrencyId;

        IF SQL%NOTFOUND THEN
            RAISE updateCurrency.eNoRecordFound;
        END IF;
        COMMIT;

    EXCEPTION
        WHEN updateCurrency.eNoRecordFound THEN
            RAISE_APPLICATION_ERROR(-20001, 'Currency not found.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while updating currency.');
        COMMIT;
    END updateCurrency;

    FUNCTION getCurrencies(pCurrencyId NUMBER DEFAULT NULL, pCode VARCHAR2 DEFAULT NULL, pSymbol VARCHAR2 DEFAULT NULL)
    RETURN SYS_REFCURSOR
    AS
        vcCurrency SYS_REFCURSOR;
    BEGIN
        OPEN vcCurrency FOR
            SELECT id, code, symbol
            FROM Currency
            WHERE id = NVL(pCurrencyId, id)
            AND code = NVL(pCode, code)
            AND symbol = NVL(pSymbol, symbol);
        RETURN (vcCurrency);
    EXCEPTION
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error occurred while retrieving currencies.');
    END getCurrencies;
END CurrencyManager;


DECLARE vUser SYS_REFCURSOR;
BEGIN
    vUser:= UserManager.getUsers(NULL, NULL, NULL, NULL, NULL, NULL, NULL);
end;