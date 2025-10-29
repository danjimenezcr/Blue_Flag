ALTER SESSION SET CURRENT_SCHEMA = BLUE;

CALL USERTYPESMANAGER.INSERTUSERTYPE('admin');
CALL USERTYPESMANAGER.INSERTUSERTYPE('external');

CALL LABELSMANAGER.INSERTLABEL('Home');
CALL LABELSMANAGER.INSERTLABEL('Cellular');

CALL GENDERSMANAGER.INSERTGENDER('Cis Man');
CALL GENDERSMANAGER.INSERTGENDER('Cis Women');
CALL GENDERSMANAGER.INSERTGENDER('Trans Women');
CALL GENDERSMANAGER.INSERTGENDER('Trans Men');

CALL IDTYPEMANAGER.INSERTIDTYPE('National ID');
CALL IDTYPEMANAGER.INSERTIDTYPE('Passport');
CALL IDTYPEMANAGER.INSERTIDTYPE('Permanent Residence');
CALL IDTYPEMANAGER.INSERTIDTYPE('Temporal Residence');

CALL COUNTRYMANAGER.INSERTCOUNTRY('Costa Rica');

CALL PROVINCEMANAGER.INSERTPROVINCE('San Jose', 0);
CALL PROVINCEMANAGER.INSERTPROVINCE('Heredia', 0);
CALL PROVINCEMANAGER.INSERTPROVINCE('Cartago', 0);
CALL PROVINCEMANAGER.INSERTPROVINCE('Alajuela', 0);
CALL PROVINCEMANAGER.INSERTPROVINCE('Guanacaste', 0);
CALL PROVINCEMANAGER.INSERTPROVINCE('Puntarenas', 0);
CALL PROVINCEMANAGER.INSERTPROVINCE('Limon', 0);

CALL CITYMANAGER.INSERTCITY('Santa Ana', 0);
CALL DISTRICTMANAGER.INSERTDISTRICT('Santa Ana', 0);
CALL DISTRICTMANAGER.INSERTDISTRICT('Pozos', 0);


CALL USERMANAGER.INSERTUSER('Danny', '02/07/2002', 'danjimenezcr', null, 'Jimenez', 'Sevilla',
     'Jimenez02', null,0, 0, 0,0, 'Condomium Santa Ana Park B204','504440584');


-----------------------------------------------------------------------------------

--Inserts Alexis--

CALL adminCollectionCenter.insertCollectionCenter('Municipalidad de Cartago', TO_TIMESTAMP('08:00 AM', 'HH:MI AM'), TO_TIMESTAMP('04:00 PM', 'HH:MI AM'), 'Mario Redondo', '2550-4500', '3', '1', :new_id);

CALL adminCollectionCenter.insertCollectionCenter('Recicladora Hernandez Cartin', TO_TIMESTAMP('08:00 AM', 'HH:MI AM'), TO_TIMESTAMP('05:00 PM', 'HH:MI AM'), 'Hernandez Cartin', '7015-3945', '3', '1',:new_id);

CALL adminAffiliatedBusiness.insertAffiliatedBusiness('Quantum Lifecycle Partners', TO_TIMESTAMP('08:00 AM', 'HH:MI AM'), TO_TIMESTAMP('05:00 PM', 'HH:MI AM'), 'Gary Diamond', '8895-9594', '3', '2',:new_id);

CALL adminCollectionCenter.insertCollectionCenter('Universidad de Costa Rica', TO_TIMESTAMP('06:00 AM', 'HH:MI AM'), TO_TIMESTAMP('06:00 PM', 'HH:MI AM'), 'Carlos Araya Leandro', '2511-6748', '1', '1',:new_id);

CALL adminAffiliatedBusiness.insertAffiliatedBusiness('Ecolones Paseo Metrópoli', TO_TIMESTAMP('09:00 AM', 'HH:MI AM'), TO_TIMESTAMP('08:00 PM', 'HH:MI AM'), 'Rolando Rodríguez Torres', '2573-0900', '3', '1',:new_id);

CALL adminCollectionCenter.insertCollectionCenter('Tecnologico de Costa Rica de Costa Rica', TO_TIMESTAMP('04:00 AM', 'HH:MI AM'), TO_TIMESTAMP('10:00 PM', 'HH:MI AM'), 'María Estrada Sánchez', '2573 7851', '3', '1',:new_id);

CALL adminMaterialType.insertMaterialType('Plastic');
CALL adminMaterialType.insertMaterialType('Metal');
CALL adminMaterialType.insertMaterialType('Paper');
CALL adminMaterialType.insertMaterialType('Electronics');
CALL adminMaterialType.insertMaterialType('PaperBoard');
CALL adminMaterialType.insertMaterialType('aluminum');
CALL adminMaterialType.insertMaterialType('Organics');
CALL adminMaterialType.insertMaterialType('Scrap');
CALL adminMaterialType.insertMaterialType('Glass');

CALL adminCenterType.insertCenterType('Multi-material: accepts all types of materials');
CALL adminCenterType.insertCenterType('Mono-material: one type of materials');
CALL adminCenterType.insertCenterType('Electronics: accepts only Electronics, computers, cellphone');
CALL adminCenterType.insertCenterType('Organics: Food waste or plant pruning (composting)');
CALL adminCenterType.insertCenterType('Dangerous: Batteries, oils, light bulbs, expired medications');

CALL adminBusinessType.insertBusinessType('collectors: receive materials');
CALL adminBusinessType.insertBusinessType('Sponsors: support or fund recycling projects');
CALL adminBusinessType.insertBusinessType('Processors: transform or sell recyclable materials');

CALL adminTMXCenter.insertTMXCenter('1','1');
CALL adminTMXCenter.insertTMXCenter('1','3');
CALL adminTMXCenter.insertTMXCenter('1','5');
CALL adminTMXCenter.insertTMXCenter('1','6');
CALL adminTMXCenter.insertTMXCenter('1','7');

CALL adminTMXCenter.insertTMXCenter('2','1');
CALL adminTMXCenter.insertTMXCenter('2','3');
CALL adminTMXCenter.insertTMXCenter('2','5');
CALL adminTMXCenter.insertTMXCenter('2','6');
CALL adminTMXCenter.insertTMXCenter('2','7');
CALL adminTMXCenter.insertTMXCenter('2','8');

CALL adminTMXCenter.insertTMXCenter('4','1');
CALL adminTMXCenter.insertTMXCenter('4','3');
CALL adminTMXCenter.insertTMXCenter('4','5');
CALL adminTMXCenter.insertTMXCenter('4','6');
CALL adminTMXCenter.insertTMXCenter('4','7');

CALL adminTMXCenter.insertTMXCenter('5','1');
CALL adminTMXCenter.insertTMXCenter('5','3');
CALL adminTMXCenter.insertTMXCenter('5','5');
CALL adminTMXCenter.insertTMXCenter('5','6');
CALL adminTMXCenter.insertTMXCenter('5','7');




