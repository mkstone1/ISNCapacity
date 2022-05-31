package com.example.isncapacity.Model;

import java.util.ArrayList;

public class VM {
    private String vmID;
    private String vmName;
    private boolean dynamicRam;
    private int maxRAM;
    private int minRAM;
    private int CPUs;
    private ArrayList<Disk> vmDisks = new ArrayList<>();
    private String state;

    public boolean getDynamicRam(){
        return dynamicRam;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setVmID(String vmID) {
        this.vmID = vmID;
    }

    public String getVmID() {
        return vmID;
    }

    public VM (String vmName, String CPUs){
        this.vmName = vmName;
        this.CPUs = Integer.parseInt(CPUs);
    }

    public VM (String vmID, String vmName, int dynamicRam, int maxRAM, int minRAM, int CPUs, String state){
        this.vmID = vmID;
        this.vmName = vmName;
        this.maxRAM = maxRAM;
        this.minRAM = minRAM;
        this.CPUs = CPUs;
        this.state = state;
        if(dynamicRam == 1){
            this.dynamicRam = true;
        }
        else{
            this.dynamicRam=false;
        }
    }

    public ArrayList<Disk> getVmDisks() {
        return vmDisks;
    }

    public void addDisk(Disk disk){
        vmDisks.add(disk);
    }
    public void setDisk(ArrayList<Disk> disks){
        vmDisks = disks;
    }

    public int isDynamicRam() {
        if(dynamicRam == true){
            return 1;
        }
        else{
            return 0;
        }
    }

    public void setDynamicRam(boolean dynamicRam) {
        this.dynamicRam = dynamicRam;
    }

    public int getMaxRAM() {
        return maxRAM;
    }

    public void setMaxRAM(int maxRAM) {
        this.maxRAM = maxRAM;
    }

    public int getMinRAM() {
        return minRAM;
    }

    public void setMinRAM(int minRAM) {
        this.minRAM = minRAM;
    }

    public String getVmName() {
        return vmName;
    }

    public int getCPUs() {
        return CPUs;
    }

    @Override
    public String toString() {
        return "VM{" +
                "vmName='" + vmName + '\'' +
                ", CPUs=" + CPUs +
                '}';
    }

    public void addRamToVM(String isDynamic, String minRAM, String startupRAM, String maxRAM) {
        Long startupRamLong = Long.parseLong(startupRAM) / 1024 / 1024;
        int startupRamInt = startupRamLong.intValue();

        Long minRamLong = Long.parseLong(minRAM) / 1024 / 1024  ;
        int minRamInt = minRamLong.intValue();

        Long maxRamLong = Long.parseLong(maxRAM) / 1024 / 1024 ;
        int maxRamInt = maxRamLong.intValue();

        if (isDynamic.equals("True")) {
            if (maxRamInt == 1048576) {
                this.setMaxRAM(startupRamInt);
                //CATCH ALL THESE AND WRITE TO FILE. THEY HAVE DYNAMIC MEMORY AND MAXRAM SET TO 1048576
            } else {
                this.setMaxRAM(maxRamInt);
                this.setMinRAM(minRamInt);
            }
        } else {
            this.setMaxRAM(startupRamInt);
        }
    }



}
