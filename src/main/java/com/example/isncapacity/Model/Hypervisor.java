package com.example.isncapacity.Model;

import java.util.ArrayList;


public class Hypervisor {
    private String hypervisorName;
    private int memory;
    private int storage;
    private String hypervisorID;
    private ArrayList<VM> allVmsOnHyperVisor = new ArrayList<>();
    private ArrayList<Disk> unassignedDisks = new ArrayList<>();

    public void setAllVmsOnHyperVisor(ArrayList<VM> allVmsOnHyperVisor) {
        this.allVmsOnHyperVisor = allVmsOnHyperVisor;
    }

    public void setHypervisorID(String hypervisorID) {
        this.hypervisorID = hypervisorID;
    }

    public String getHypervisorID() {
    return hypervisorID;
    }

    public Hypervisor(String hypervisorName) {
        this.hypervisorName = hypervisorName;
    }
    public Hypervisor(String hypervisorID , String hypervisorName , String memory, String storageSpace) {
        this.hypervisorName = hypervisorName;
        this.hypervisorID = hypervisorID;
        this.memory = Integer.parseInt(memory);
        this.storage = Integer.parseInt(storageSpace);
    }

    public ArrayList<Disk> getUnassignedDisks() {
        return unassignedDisks;
    }

    public Disk getDiskByID(String diskID){
        Disk diskToReturn = null;
        for (Disk disk: unassignedDisks){
            if(disk.getDiskID().equals(diskID)){
                diskToReturn = disk;
            }
        }
        return diskToReturn;
    }

    public String getHypervisorName() {
        return hypervisorName;
    }

    public void setUnassignedDisks(ArrayList<Disk> unassignedDisks) {
        this.unassignedDisks = unassignedDisks;
    }

    public ArrayList<VM> getAllVmsOnHyperVisor() {
        return allVmsOnHyperVisor;
    }


    public void addVmToHypervisor(VM vm){
        allVmsOnHyperVisor.add(vm);
    }

    public VM getVmBasedOnName(String name){
        VM vmToReturn = null;
        for (VM vm : allVmsOnHyperVisor){
            if(vm.getVmName().equals(name)){
                vmToReturn= vm;
            }
        }
        return vmToReturn;
    }

}


