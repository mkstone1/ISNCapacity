package com.example.isncapacity.Services;

import com.example.isncapacity.Model.Customer;
import com.example.isncapacity.Model.VM;
import com.example.isncapacity.Repository.CustomerRepository;
import com.example.isncapacity.Repository.ExtractRepository;
import com.example.isncapacity.Repository.HypervisorRepository;
import com.example.isncapacity.Repository.VmRepository;

import java.util.ArrayList;

public class VMService {
    CustomerRepository customerRepository = new CustomerRepository();
    VmRepository vmRepo = new VmRepository();
    ExtractRepository extractRepository = new ExtractRepository();

    public void linkVmToCustomer(String vmName, String customerName){

        String customerID = customerRepository.getCustomerIDFromName(customerName);
        String vmID = vmRepo.getLatestVmIDFromName(vmName);
        vmRepo.linkVmToCustomer(vmID,customerID);
    }

    public VM getVmFromID(String ID){
        return vmRepo.getVMObjectFromID(ID);
    }

    public ArrayList<VM> getVmsFromCustomer(Customer customer){
        return vmRepo.getLatestVmFromCustomerID(customerRepository.getCustomerIDFromName(customer.getName()), extractRepository.getLastCreatedExtractID());
    }

    public ArrayList<VM> getVMsFromHypervisorWithNoCustomerAssigned(String hypervisorID){
        return  vmRepo.getVMsFromHypervisorWithNoCustomerAssigned(hypervisorID, extractRepository.getLastCreatedExtractID());
    }

    public void removeVmFromCustomer (String vmID){
        vmRepo.removeVmFromCustomer(vmID);
    }

}
