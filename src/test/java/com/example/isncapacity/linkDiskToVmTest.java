package com.example.isncapacity;

import com.example.isncapacity.Model.Disk;
import com.example.isncapacity.Repository.DiskRepository;
import com.example.isncapacity.Repository.VmRepository;
import com.example.isncapacity.Services.DiskService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class linkDiskToVmTest {


    @Test
    void linkDiskToVM(){
        //Arrange
        DiskService diskService = new DiskService();
        VmRepository vmRepository = new VmRepository();
        DiskRepository diskRepository = new DiskRepository();
        String diskName = "vm3disk1";
        String vmName = "vm3";

        String expectedResult = "vm3disk1";


       // Act
        diskService.linkDiskToVM(vmName,diskName);

        //Assert
        assertEquals(expectedResult,
                diskRepository.getVHDXForVM(vmRepository.getVMObjectFromName(vmName,"3"), "3").get(0).getDiskName());

           }
}
