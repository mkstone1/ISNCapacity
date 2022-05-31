CREATE TABLE `data_extract` (
                                `extract_id` int unsigned NOT NULL AUTO_INCREMENT,
                                `time` varchar(20) DEFAULT NULL,
                                PRIMARY KEY (`extract_id`)
);

CREATE TABLE `hypervisor` (
                              `hypervisor_id` int unsigned NOT NULL AUTO_INCREMENT,
                              `hypervisor_name` varchar(15) NOT NULL,
                              `memory` int unsigned,
                              `storage_space` int unsigned,
                              PRIMARY KEY (`hypervisor_id`)

);

CREATE TABLE `customer` (
                            `customer_id` int unsigned NOT NULL AUTO_INCREMENT,
                            `customer_name` varchar(30) NOT NULL,
                            `customer_abbreviation` varchar(3) NOT NULL,
                            PRIMARY KEY (`customer_id`)
);


CREATE TABLE `vm` (
                      `vm_id` int unsigned NOT NULL AUTO_INCREMENT,
                      `vm_name` varchar(40) DEFAULT NULL,
                      `dynamic_ram` tinyint DEFAULT NULL,
                      `max_ram` int DEFAULT NULL,
                      `min_ram` int DEFAULT NULL,
                      `cpus` int DEFAULT NULL,
                      `state` varchar(10) DEFAULT NULL,
                      `extract_id` int unsigned DEFAULT NULL,
                      `hypervisor_id` int unsigned DEFAULT NULL,
                      `customer_id` int unsigned DEFAULT NULL,
                      PRIMARY KEY (`vm_id`),
                      KEY `extract_id_idx` (`extract_id`),
                      KEY `hv_id_vm_idx` (`hypervisor_id`),
                      KEY `customer_id_idx` (`customer_id`),
                      CONSTRAINT `customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
                      CONSTRAINT `extract_id_vm` FOREIGN KEY (`extract_id`) REFERENCES `data_extract` (`extract_id`),
                      CONSTRAINT `hv_id_vm` FOREIGN KEY (`hypervisor_id`) REFERENCES `hypervisor` (`hypervisor_id`)
);

CREATE TABLE `disk` (
                        `disk_id` int unsigned NOT NULL AUTO_INCREMENT,
                        `disk_name` varchar(40) DEFAULT NULL,
                        `path` varchar(100) DEFAULT NULL,
                        `file_size` int DEFAULT NULL,
                        `max_size` int DEFAULT NULL,
                        `extract_id` int unsigned DEFAULT NULL,
                        `vm_id` int unsigned DEFAULT NULL,
                        `hypervisor_id` int unsigned DEFAULT NULL,
                        PRIMARY KEY (`disk_id`),
                        KEY `extract_id_idx` (`extract_id`),
                        KEY `vm_id_disk_idx` (`vm_id`),
                        KEY `hv_id_disk_idx` (`hypervisor_id`),
                        CONSTRAINT `extract_id_disk` FOREIGN KEY (`extract_id`) REFERENCES `data_extract` (`extract_id`),
                        CONSTRAINT `hv_id_disk` FOREIGN KEY (`hypervisor_id`) REFERENCES `hypervisor` (`hypervisor_id`),
                        CONSTRAINT `vm_id_disk` FOREIGN KEY (`vm_id`) REFERENCES `vm` (`vm_id`)
);