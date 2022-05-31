package com.example.isncapacity.Repository;

import com.example.isncapacity.Model.Hypervisor;
import com.example.isncapacity.Model.VM;
import com.example.isncapacity.Utility.DatabaseConnectionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class HypervisorRepository {
    private final DiskRepository diskRepository = new DiskRepository();
    private final VmRepository vmRepository = new VmRepository();
    private final ExtractRepository extractRepository = new ExtractRepository();

    public HypervisorRepository(){
    }

    public ArrayList<Hypervisor> getAllHyperVisors(){
        String lastExtractID = extractRepository.getLastCreatedExtractID();
        ArrayList<Hypervisor> hvFromLastExtract = getAllHypervisorFromDB();

        for(Hypervisor hv: hvFromLastExtract){
            hv.setAllVmsOnHyperVisor(vmRepository.allVMsonHypervisor(hv.getHypervisorID(),lastExtractID));
            hv.setUnassignedDisks(diskRepository.getUnassignedDisksFromLatestExtract(hv, lastExtractID));
            for(VM vm : hv.getAllVmsOnHyperVisor()){
                vm.setDisk(diskRepository.getVHDXForVM(vm, lastExtractID));
            }
        }
        return hvFromLastExtract;
    }


    public ArrayList<Hypervisor> getAllHypervisorFromDB(){
        ArrayList<Hypervisor> hvFromLastExtract = new ArrayList<>();
        ArrayList<String> hypervisorInfo = new ArrayList<>();
            try {
                Connection conn = DatabaseConnectionManager.getConnection();
                PreparedStatement psts = conn.prepareStatement("SELECT * from hypervisor");
                ResultSet resultSet = psts.executeQuery();

                while(resultSet.next()){
                    hypervisorInfo.add(resultSet.getString(1));
                    hypervisorInfo.add(resultSet.getString(2));
                    hypervisorInfo.add(resultSet.getString(3));
                    hypervisorInfo.add(resultSet.getString(4));
                }

            }

            catch (SQLException e){
                System.out.println("Cannot connect to database or error in database");
                e.printStackTrace();
            }

        for(int i = 0; i < hypervisorInfo.size()-1;i +=4 ){
            hvFromLastExtract.add(new Hypervisor(hypervisorInfo.get(i), hypervisorInfo.get(i+1), hypervisorInfo.get(i+2), hypervisorInfo.get(i+3)));
        }
        return hvFromLastExtract;
    }


    public void createVMsForHypervisor(Hypervisor hv) {
        final String pathToCPUData = "staticInfo/" + hv.getHypervisorName() + "/CPUInfo.csv";


        File file = new File(pathToCPUData);
        String input = "";
        String name = "";
        String cpu = "";
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            scanner.nextLine();
            while (scanner.hasNext()) {
                input = scanner.nextLine();
                name = input.substring(1, input.indexOf(",") - 1);
                cpu = input.substring(input.indexOf(",") + 2, input.length() - 1);
                hv.addVmToHypervisor(new VM(name, cpu));


            }
        } catch (FileNotFoundException e) {
            System.out.println("Cant find CPU data for hypervisor " + hv.getHypervisorName());
        }
    }

    public void createHypervisor(String hypervisorName,String hypervisorRAM, String storageSpace){
        Connection conn = DatabaseConnectionManager.getConnection();
        try{
            PreparedStatement ppst = conn.prepareStatement(
                    "insert into hypervisor(hypervisor_name, memory, storage_space) values(?,?,?)");
            ppst.setString(1,hypervisorName);
            ppst.setString(2,hypervisorRAM);
            ppst.setString(3,storageSpace);
            ppst.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("DB ERROR");
        }

    }


}
