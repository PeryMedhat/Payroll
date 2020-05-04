CREATE DATABASE `payroll-schema` /*!40100 DEFAULT CHARACTER SET big5 */;

CREATE TABLE `payroll-schema`.`commonID` (
  `commonID_id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `code` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `deleted` int(11) NOT NULL,
  PRIMARY KEY (`commonID_id`),
  UNIQUE KEY `commonID_id_UNIQUE` (`commonID_id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=big5;


CREATE TABLE `payroll-schema`.`companyCommonID` (
  `companyCommonID_id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `code` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `delimited` int(11) NOT NULL,
  PRIMARY KEY (`companyCommonID_id`),
  UNIQUE KEY `companyCommonID_id_UNIQUE` (`companyCommonID_id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=big5;


CREATE TABLE `payroll-schema`.`emp_struct_parent` (
  `parent_id` int(11) NOT NULL AUTO_INCREMENT,
  `commonID_id` int(11) NOT NULL,
  PRIMARY KEY (`parent_id`),
  UNIQUE KEY `parent_id_UNIQUE` (`parent_id`),
  KEY `fk_emp-struct-parent_1_idx` (`commonID_id`),
  CONSTRAINT `fk_emp_struct_parent_1` FOREIGN KEY (`commonID_id`) REFERENCES `commonID` (`commonID_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=big5;


CREATE TABLE `payroll-schema`.`company_struct_parent` (
  `companyParent_id` int(11) NOT NULL AUTO_INCREMENT,
  `companyCommonID_id` int(11) NOT NULL,
  PRIMARY KEY (`companyParent_id`),
  UNIQUE KEY `companyParent_id_UNIQUE` (`companyParent_id`),
  KEY `fk_company_struct_parent_1_idx` (`companyCommonID_id`),
  CONSTRAINT `fk_company_struct_parent_1` FOREIGN KEY (`companyCommonID_id`) REFERENCES `companyCommonID` (`companyCommonID_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=big5;


CREATE TABLE `payroll-schema`.`emp_struct_subparent` (
  `subparent_id` int(11) NOT NULL AUTO_INCREMENT,
  `has_parent` int(11) DEFAULT NULL,
  `parent_code` varchar(45) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `commonID_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`subparent_id`),
  UNIQUE KEY `subparent_id_UNIQUE` (`subparent_id`),
  KEY `parent_id_idx` (`parent_id`),
  KEY `fk_emp-struct-subparent_1_idx` (`commonID_id`),
  CONSTRAINT `fk_emp_struct_subparent_1` FOREIGN KEY (`commonID_id`) REFERENCES `commonID` (`commonID_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_emp_struct_subparent_2` FOREIGN KEY (`parent_id`) REFERENCES `emp_struct_parent` (`parent_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=big5;


CREATE TABLE `payroll-schema`.`company_struct_subparent` (
  `companySubparent_id` int(11) NOT NULL AUTO_INCREMENT,
  `companyCommonID_id` int(11) NOT NULL,
  `has_parent` int(11) DEFAULT NULL,
  `parent_code` varchar(45) DEFAULT NULL,
  `companyParent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`companySubparent_id`),
  UNIQUE KEY `subparent_id_UNIQUE` (`companySubparent_id`),
  KEY `fk_company_struct_subparent_1_idx` (`companyCommonID_id`),
  KEY `fk_company_struct_subparent_2_idx` (`companyParent_id`),
  CONSTRAINT `fk_company_struct_subparent_1` FOREIGN KEY (`companyCommonID_id`) REFERENCES `companyCommonID` (`companyCommonID_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_company_struct_subparent_2` FOREIGN KEY (`companyParent_id`) REFERENCES `company_struct_parent` (`companyParent_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=big5;

CREATE TABLE `payroll-schema`.`emp_struct_child` (
  `child_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `subparent_id` int(11) DEFAULT NULL,
  `commonID_id` int(11) NOT NULL,
  PRIMARY KEY (`child_id`),
  UNIQUE KEY `child_id_UNIQUE` (`child_id`),
  KEY `fk_emp-struct-child_3_idx` (`commonID_id`),
  KEY `fk_emp_struct_child_2_idx` (`parent_id`),
  KEY `fk_emp_struct_child_3_idx` (`subparent_id`,`parent_id`),
  CONSTRAINT `fk_emp_struct_child_1` FOREIGN KEY (`commonID_id`) REFERENCES `commonID` (`commonID_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_emp_struct_child_2` FOREIGN KEY (`parent_id`) REFERENCES `emp_struct_parent` (`parent_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=big5;


CREATE TABLE `payroll-schema`.`company_struct_child` (
  `companyChild_id` int(11) NOT NULL AUTO_INCREMENT,
  `companyParent_id` int(11) DEFAULT NULL,
  `companySubparent_id` int(11) DEFAULT NULL,
  `companyCommonID_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`companyChild_id`),
  UNIQUE KEY `companyChild_id_UNIQUE` (`companyChild_id`),
  KEY `fk_company_struct_child_3_idx` (`companySubparent_id`),
  KEY `fk_company_struct_child_2_idx` (`companyParent_id`),
  KEY `fk_company_struct_child_1_idx` (`companyCommonID_id`),
  CONSTRAINT `fk_company_struct_child_1` FOREIGN KEY (`companyCommonID_id`) REFERENCES `companyCommonID` (`companyCommonID_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_company_struct_child_2` FOREIGN KEY (`companyParent_id`) REFERENCES `company_struct_parent` (`companyParent_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_company_struct_child_3` FOREIGN KEY (`companySubparent_id`) REFERENCES `company_struct_subparent` (`companySubparent_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=big5;


CREATE TABLE `country` (
  `country_id` varchar(45) NOT NULL,
  `code` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=big5;


CREATE TABLE `currency` (
  `currency_id` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`currency_id`)
) ENGINE=InnoDB DEFAULT CHARSET=big5;



CREATE TABLE `paytype` (
  `paytype_id` int(11) NOT NULL AUTO_INCREMENT,
  `paytype_commId_id` int(11) NOT NULL,
  `interval` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `inputvalue` varchar(45) NOT NULL,
  `taxes` varchar(45) DEFAULT NULL,
  `gl_assignemnt` varchar(45) DEFAULT NULL,
  `cost_center` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`paytype_id`),
  KEY `fk_paytype_1_idx` (`paytype_commId_id`),
  KEY `fk_paytype_2_idx` (`interval`),
  KEY `fk_paytype_3_idx` (`inputvalue`),
  KEY `fk_paytype_4_idx` (`type`),
  CONSTRAINT `fk_paytype_1` FOREIGN KEY (`paytype_commId_id`) REFERENCES `paytype_commId` (`paytype_commId_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=big5;




CREATE TABLE `paytype_commId` (
  `paytype_commId_id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `code` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`paytype_commId_id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=big5;

CREATE TABLE `paytype_inputvalue` (
  `code` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=big5;

CREATE TABLE `paytype_interval` (
  `code` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=big5;




CREATE TABLE `paytype_type` (
  `code` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=big5;












