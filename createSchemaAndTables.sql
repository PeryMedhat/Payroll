CREATE DATABASE `payroll-schema` /*!40100 DEFAULT CHARACTER SET big5 */;

CREATE TABLE `commonID` (
  `commonID_id` int(11) NOT NULL AUTO_INCREMENT,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `code` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`commonID_id`),
  UNIQUE KEY `commonID_id_UNIQUE` (`commonID_id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=big5;

CREATE TABLE `emp_struct_child` (
  `child_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `subparent_id` int(11) DEFAULT NULL,
  `commonID_id` int(11) NOT NULL,
  PRIMARY KEY (`child_id`),
  UNIQUE KEY `child_id_UNIQUE` (`child_id`),
  KEY `fk_emp-struct-child_3_idx` (`commonID_id`),
  KEY `fk_emp_struct_child_2_idx` (`parent_id`),
  KEY `fk_emp_struct_child_3_idx` (`subparent_id`),
  CONSTRAINT `fk_emp_struct_child_1` FOREIGN KEY (`commonID_id`) REFERENCES `commonID` (`commonID_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_emp_struct_child_2` FOREIGN KEY (`parent_id`) REFERENCES `emp_struct_parent` (`parent_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_emp_struct_child_3` FOREIGN KEY (`subparent_id`) REFERENCES `emp_struct_subparent` (`subparent_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=big5;


CREATE TABLE `emp_struct_parent` (
  `parent_id` int(11) NOT NULL AUTO_INCREMENT,
  `commonID_id` int(11) NOT NULL,
  PRIMARY KEY (`parent_id`),
  UNIQUE KEY `parent_id_UNIQUE` (`parent_id`),
  KEY `fk_emp-struct-parent_1_idx` (`commonID_id`),
  CONSTRAINT `fk_emp_struct_parent_1` FOREIGN KEY (`commonID_id`) REFERENCES `commonID` (`commonID_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=big5;

CREATE TABLE `emp_struct_subparent` (
  `subparent_id` int(11) NOT NULL AUTO_INCREMENT,
  `has_parent` int(11) DEFAULT NULL,
  `parent_code` varchar(45) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `commonID_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`subparent_id`),
  UNIQUE KEY `subparent_id_UNIQUE` (`subparent_id`),
  KEY `parent_id_idx` (`parent_id`),
  KEY `fk_emp-struct-subparent_1_idx` (`commonID_id`),
  CONSTRAINT `fk_emp_struct_subparent_1` FOREIGN KEY (`commonID_id`) REFERENCES `commonID` (`commonID_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_emp_struct_subparent_2` FOREIGN KEY (`parent_id`) REFERENCES `emp_struct_parent` (`parent_id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=big5;
