CREATE TABLE `hypervisor` (
                              `hypervisor_id` int unsigned NOT NULL AUTO_INCREMENT,
                              `hypervisor_name` varchar(15) NOT NULL,
                              `memory` int unsigned,
                              `storage_space` int unsigned,
                              PRIMARY KEY (`hypervisor_id`)

)