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
)