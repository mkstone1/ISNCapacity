package com.example.isncapacity.Services;

import com.example.isncapacity.Model.Disk;
import com.example.isncapacity.Model.Hypervisor;
import com.example.isncapacity.Model.VM;
import com.example.isncapacity.Repository.*;
import com.example.isncapacity.Utility.DatabaseConnectionManager;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UpdateDataService {
    private final String serverListPath = "staticinfo/ServerList.txt";
    private final DiskRepository diskRepository = new DiskRepository();
    private final ExtractRepository extractRepository = new ExtractRepository();
    private final HypervisorRepository hypervisorRepository = new HypervisorRepository();
    private final VmRepository vmRepository = new VmRepository();

    public UpdateDataService() {
    }

    public void updateData(){
        //Create serverlist for powershell
        ArrayList<Hypervisor> allHypervisors=  hypervisorRepository.getAllHyperVisors();
        createServerList(allHypervisors);

        //Run data extract
        try {
            runPowershell();
        }
        catch (IOException e){
            System.out.println("Failed to get VM info");
        }

        //Creates db extract and get ID
        extractRepository.saveExtractToDB();
        String thisExtractID = extractRepository.getLastCreatedExtractID();


        //Create all VMs and disks
        ArrayList<Hypervisor> hvList = createVMsAndDisks(allHypervisors);


        for(Hypervisor hv : hvList){
            // get vms and unassigned disks
            ArrayList<VM> allVMsOnHypervisor = hv.getAllVmsOnHyperVisor();
            ArrayList<Disk> allUnassignedDiskOnHV = hv.getUnassignedDisks();



            for(VM vm : allVMsOnHypervisor){
                //check if vm is assigned to customer and assign newly created vms
                String customerID = vmRepository.getCustomerIDFromLastExtract(vm.getVmName());
                if(customerID == null || customerID.equals("")){
                vmRepository.saveVMToDB(hv,thisExtractID, vm);
                }
                else{
                    vmRepository.saveVMToDB(hv,thisExtractID, vm, customerID);
                }
                //Save vm disk to DB
                diskRepository.saveVMDiskToDB(vm.getVmDisks(), vm.getVmID(),thisExtractID);
            }

            //Save unassigned disks to db
            diskRepository.saveHypervisorDiskToDB(hv.getUnassignedDisks(), hv.getHypervisorID(),thisExtractID);

            for(Disk disk : allUnassignedDiskOnHV){
                //Check if unassigned disk should be assigned and assign if necessary
                String vmName = diskRepository.getVMNameOfPreviousExrtactDisk(disk, Integer.toString(Integer.parseInt(thisExtractID)-1));
                String vmID = diskRepository.getLatestVersionOfVmIDFromName(vmName, thisExtractID);
                if(!(vmID.equals(""))){
                    diskRepository.saveBindNewDiskToVM(disk, vmRepository.getVMObjectFromID(vmID));
                }
            }
        }


        cleanupTempFiles(hvList);

        System.out.println("Data update complete");

    }


    public void runPowershell() throws IOException {

        String line;

        String command = "powershell.exe & \"C:\\Users\\marks\\IdeaProjects\\ISNCapacity\\powershell\\getDataWithRemoteConnect";

        Process powerShellProcess = Runtime.getRuntime().exec(command);

        powerShellProcess.getOutputStream().close();

        BufferedReader stdout = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));
        while ((line = stdout.readLine()) != null) {
            System.out.println("Output: " + line);

        }

        stdout.close();

        BufferedReader stderr = new BufferedReader(new InputStreamReader(powerShellProcess.getErrorStream()));
        while ((line = stderr.readLine()) != null) {
            System.out.println("Output: " + line);
        }
        stderr.close();

        System.out.println("Getting data finished");
    }

    public ArrayList<Hypervisor> createVMsAndDisks(ArrayList<Hypervisor> allHypervisors) {

        for (Hypervisor hv: allHypervisors){
            hypervisorRepository.createVMsForHypervisor(hv);
            vmRepository.getRAMForVMs(hv);
            vmRepository.setStateOfVM(hv);
            hv.setUnassignedDisks(diskRepository.getVHDXData(hv));
        }

        return allHypervisors;
    }



    private void cleanupTempFiles(ArrayList<Hypervisor> hvList){
        for(Hypervisor hv: hvList){
            String destination = "C:\\Users\\marks\\IdeaProjects\\ISNCapacity\\staticinfo\\";
            destination += hv.getHypervisorName();
            try{
                FileUtils.deleteDirectory(new File(destination));

            }
            catch (IOException e) {
                System.out.println("Failed to cleanup local files");
            }
        }
        File file = new File("C:\\Users\\marks\\IdeaProjects\\ISNCapacity\\staticinfo\\ServerList.txt");
        file.delete();

    }

    private void createServerList(ArrayList<Hypervisor> allHypervisors){
        File file = new File("C:\\Users\\marks\\IdeaProjects\\ISNCapacity\\staticinfo\\ServerList.txt");
        ArrayList<String> names = new ArrayList<>();
        try {
            file.createNewFile();
            PrintStream  writer = new PrintStream(file);
            for(Hypervisor hv : allHypervisors){
                names.add(hv.getHypervisorName());
                writer.append(hv.getHypervisorName());
                writer.append("\n");

            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Failed to create server list file");
        } catch (IOException e) {
            e.printStackTrace();
        }



    }





}



