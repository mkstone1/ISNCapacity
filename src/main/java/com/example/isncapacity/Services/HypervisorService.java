package com.example.isncapacity.Services;

import com.example.isncapacity.Model.Hypervisor;
import com.example.isncapacity.Repository.HypervisorRepository;

import java.util.ArrayList;

public class HypervisorService {
    public final HypervisorRepository hvRepo = new HypervisorRepository();

    public ArrayList<Hypervisor> getAllHypervisors(){
        return hvRepo.getAllHyperVisors();
    }


    public int getHypervisorPlacement (String name){
        int returnVal = -1;
        for(int i = 0; i < hvRepo.getAllHyperVisors().size() ; i++){
            if (hvRepo.getAllHyperVisors().get(i).getHypervisorName().equals(name)){
                returnVal = i;
            }
        }
        return returnVal;
    }

    public void createHypervisor(String hypervisorName,String hypervisorRAM, String storageSpace){
        hvRepo.createHypervisor(hypervisorName, hypervisorRAM ,storageSpace);
    }


}
