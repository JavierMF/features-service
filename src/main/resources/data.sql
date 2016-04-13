insert into Product (name) values ('Product_1'), ('Product_2');
insert into Feature (name,product_id) values ('Feature_1',1), ('Feature_2',1), ('Feature_A',2), ('Feature_B',2);
insert into Product_Configuration (name,product_id) values ('Product_1_Configuration_1',1),('Product_1_Configuration_2',1);
insert into Product_Configuration (name,product_id) values ('Product_2_Configuration_1',2),('Product_2_Configuration_2',2);
insert into Product_Configuration_Actived_Features (product_configuration_id, actived_features_id) values (1,1);
insert into Product_Configuration_Actived_Features (product_configuration_id, actived_features_id) values (1,2);
insert into Product_Configuration_Actived_Features (product_configuration_id, actived_features_id) values (2,2);
insert into Product_Configuration_Actived_Features (product_configuration_id, actived_features_id) values (3,3);
insert into Product_Configuration_Actived_Features (product_configuration_id, actived_features_id) values (3,4);
