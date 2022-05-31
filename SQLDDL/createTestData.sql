insert into customer (customer_name, customer_abbreviation)
values ("kunde1", "kn1");

insert into customer (customer_name, customer_abbreviation)
values ("kunde2", "kn2");


insert into hypervisor(hypervisor_name, memory, storage_space)
values("hv1",1500, 7);

insert into hypervisor(hypervisor_name, memory, storage_space)
values("hv2",1500, 7);

insert into data_extract (time)
values("05-25-2022-11:30");

insert into data_extract (time)
values("05-25-2022-11:30");

insert into data_extract (time)
values("05-25-2022-11:30");

insert into vm (vm_name, dynamic_ram, max_ram, min_ram, cpus, state, extract_id, hypervisor_id, customer_id)
values("vm1", 0,8192,2048,4, "Running",3,1,1);

insert into vm (vm_name, dynamic_ram, max_ram, min_ram, cpus, state, extract_id, hypervisor_id, customer_id)
values("vm2",0,8192,2048,4, "Running", 3,1,1);

insert into vm (vm_name, dynamic_ram, max_ram, min_ram, cpus, state, extract_id, hypervisor_id, customer_id)
values("vm3",0,8192,2048,4, "Running", 3,1,2);

insert into vm (vm_name, dynamic_ram, max_ram, min_ram, cpus, state, extract_id, hypervisor_id, customer_id)
values("vm1",0,8192,2048,4, "Running", 3,2,2);

insert into disk (disk_name, path,file_size,max_size,extract_id,vm_id)
values("vm1disk1", "D:\Hyper_V\Virtual_Hard_Disks\vm1disk1", 200, 150, 3,1);

insert into disk (disk_name, path,file_size,max_size,extract_id,vm_id)
values("vm1disk1", "D:\Hyper_V\Virtual_Hard_Disks\vm1disk1", 200, 150, 3,4);

insert into disk (disk_name,path,file_size,max_size,extract_id,vm_id)
values("vm1disk2", "D:\Hyper_V\Virtual_Hard_Disks\vm1disk2", 200, 150, 3,1);

insert into disk (disk_name,path,file_size,max_size,extract_id,vm_id)
values("vm2disk1", "D:\Hyper_V\Virtual_Hard_Disks\vm2disk1", 200, 150, 3,2);

insert into disk (disk_name,path,file_size,max_size,extract_id,vm_id)
values("vm2disk2", "D:\Hyper_V\Virtual_Hard_Disks\vm2disk2", 200, 150, 3,2);

insert into disk (disk_name,path,file_size,max_size,extract_id,hypervisor_id)
values("vm3disk1", "D:\Hyper_V\Virtual_Hard_Disks\vm3disk1", 200, 150, 3,2);

insert into disk (disk_name,path,file_size,max_size,extract_id,hypervisor_id)
values("vm3disk2", "D:\Hyper_V\Virtual_Hard_Disks\vm3disk2", 200, 150, 3 ,1);

