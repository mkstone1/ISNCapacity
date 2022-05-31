package com.example.isncapacity.Services;

import com.example.isncapacity.Model.Disk;
import com.example.isncapacity.Model.VM;
import com.example.isncapacity.Repository.DiskRepository;
import com.example.isncapacity.Repository.ExtractRepository;
import com.example.isncapacity.Repository.VmRepository;

import java.util.ArrayList;

public class DiskService {
    DiskRepository diskRepo = new DiskRepository();
    ExtractRepository extractRepository = new ExtractRepository();
    VmRepository vmRepository = new VmRepository();

    public ArrayList<Disk> getDiskFromVMID(String vmID){
        return  diskRepo.getVHDXForVM(vmRepository.getVMObjectFromID(vmID), extractRepository.getLastCreatedExtractID());
    }

    public void linkDiskToVM(String vmName, String diskName){
        VM vmToBind = vmRepository.getVMObjectFromName(vmName, extractRepository.getLastCreatedExtractID());
        Disk diskToBind =  diskRepo.getDiskObjectFromName(diskName, extractRepository.getLastCreatedExtractID());
        diskRepo.saveBindNewDiskToVM(diskToBind, vmToBind);
    }
    
    public void unbindDiskFromVm(String diskID, String vmID){
        String hypervisorID = vmRepository.getHypervisorIDFromVmID(vmID);
        diskRepo.unbindDiskFromVm(diskID , hypervisorID);
    }
}
