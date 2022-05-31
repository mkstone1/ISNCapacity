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
)