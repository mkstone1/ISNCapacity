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

public class VmRepository {


    public ArrayList<VM> allVMsonHypervisor(String hvID, String lastExtractID){
        ArrayList<VM> allVmsOnHypervisor = new ArrayList<>();
        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("" +
                    "SELECT vm_id, vm_name, dynamic_ram, max_ram, min_ram, cpus, state from vm where extract_id = ? AND hypervisor_id = ?");
            psts.setString(1,lastExtractID);
            psts.setString(2, hvID);
            String vmID = "";
            String vmName = "";
            int dynamicRam = 0;
            int max_ram = 0;
            int min_ram = 0;
            int cpus = 0;
            String state = "";
            ResultSet resultSet = psts.executeQuery();



            while(resultSet.next()){
                vmID = resultSet.getString(1);
                vmName = resultSet.getString(2);
                dynamicRam = resultSet.getInt(3);
                max_ram = resultSet.getInt(4);
                min_ram = resultSet.getInt(5);
                cpus  = resultSet.getInt(6);
                state = resultSet.getString(7);
                allVmsOnHypervisor.add(new VM(vmID,vmName,dynamicRam,max_ram,min_ram,cpus,state));
            }

        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
        }


        return allVmsOnHypervisor;
    }

    public VM getVMObjectFromName(String VMName, String lastExtractID){

        VM vm = null;
        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("" +
                    "SELECT vm_id, vm_name, dynamic_ram, max_ram, min_ram, cpus, state from vm where extract_id = ? AND vm_name = ?");
            psts.setString(1,lastExtractID);
            psts.setString(2, VMName);
            String vmID = "";
            String vmName = "";
            int dynamicRam = 0;
            int max_ram = 0;
            int min_ram = 0;
            int cpus = 0;
            String state = "";
            ResultSet resultSet = psts.executeQuery();



            while(resultSet.next()){
                vmID = resultSet.getString(1);
                vmName = resultSet.getString(2);
                dynamicRam = resultSet.getInt(3);
                max_ram = resultSet.getInt(4);
                min_ram = resultSet.getInt(5);
                cpus  = resultSet.getInt(6);
                state = resultSet.getString(7);
                vm = new VM(vmID,vmName,dynamicRam,max_ram,min_ram,cpus,state);
            }

        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
        }


        return vm;
    }

    public VM getVMObjectFromID(String VMID){
        VM vm = null;
        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("" +
                    "SELECT vm_id, vm_name, dynamic_ram, max_ram, min_ram,cpus, state FROM vm WHERE vm_id = ?");
            psts.setString(1, VMID);
            String vmID = "";
            String vmName = "";
            int dynamicRam = 0;
            int max_ram = 0;
            int min_ram = 0;
            int cpus = 0;
            String state = "";
            ResultSet resultSet = psts.executeQuery();



            while(resultSet.next()){
                vmID = resultSet.getString(1);
                vmName = resultSet.getString(2);
                dynamicRam = resultSet.getInt(3);
                max_ram = resultSet.getInt(4);
                min_ram = resultSet.getInt(5);
                cpus  = resultSet.getInt(6);
                state = resultSet.getString(7);
                vm = new VM(vmID,vmName,dynamicRam,max_ram,min_ram,cpus, state);
            }

        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
        }


        return vm;
    }

    public void saveVMToDB(Hypervisor hv, String thisExtractID, VM vm){
        Connection conn = DatabaseConnectionManager.getConnection();
        try{
            PreparedStatement ppst = conn.prepareStatement(
                    "insert into VM(vm_name, dynamic_ram, max_ram, min_ram, cpus, extract_id, hypervisor_id, state) values(?,?,?,?,?,?,?,?)");
            ppst.setString(1,vm.getVmName());
            ppst.setInt(2,vm.isDynamicRam());
            ppst.setInt(3,vm.getMaxRAM());
            ppst.setInt(4,vm.getMinRAM());
            ppst.setInt(5,vm.getCPUs());
            ppst.setString(6,thisExtractID);
            ppst.setString(7,hv.getHypervisorID());
            ppst.setString(8, vm.getState());
            ppst.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("DB ERROR");
        }
        vm.setVmID(getLastCreatedVMID());

    }
    public void saveVMToDB(Hypervisor hv, String thisExtractID, VM vm, String  customerID){
        Connection conn = DatabaseConnectionManager.getConnection();
        try{
            PreparedStatement ppst = conn.prepareStatement(
                    "insert into VM(vm_name, dynamic_ram, max_ram, min_ram, cpus, extract_id, hypervisor_id, state, customer_id) values(?,?,?,?,?,?,?,?,?)");
            ppst.setString(1,vm.getVmName());
            ppst.setInt(2,vm.isDynamicRam());
            ppst.setInt(3,vm.getMaxRAM());
            ppst.setInt(4,vm.getMinRAM());
            ppst.setInt(5,vm.getCPUs());
            ppst.setString(6,thisExtractID);
            ppst.setString(7,hv.getHypervisorID());
            ppst.setString(8, vm.getState());
            ppst.setString(9,customerID);
            ppst.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Failed to save vm to DB");
        }
        vm.setVmID(getLastCreatedVMID());

    }

    private String getLastCreatedVMID(){
        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("SELECT vm_id from vm order by vm_id DESC LIMIT 1");
            ResultSet resultSet = psts.executeQuery();
            String ID = "";
            while(resultSet.next()){
                ID = resultSet.getString(1);
            }
            return ID;
        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
            return "";
        }
    }

    public String getLatestVmIDFromName(String vmID){
        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("SELECT vm_id from vm where vm_name = ? order by vm_id DESC limit 1");
            psts.setString(1,vmID);
            ResultSet resultSet = psts.executeQuery();
            String ID = "";
            while(resultSet.next()){
                ID = resultSet.getString(1);
            }
            return ID;
        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
            return "";
        }
    }

    public void linkVmToCustomer (String vmID,  String CustomerID){
        try{
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement ppst = conn.prepareStatement(
                "UPDATE vm SET customer_id = ? where vm_id = ?");

            ppst.setString(1,CustomerID);
            ppst.setString(2, vmID);
            ppst.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("DB ERROR");
        }
    }

    public String getCustomerIDFromLastExtract(String vmName){
        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("SELECT customer_id from vm where vm_name = ? order by vm_id DESC limit 1");
            psts.setString(1,vmName);
            ResultSet resultSet = psts.executeQuery();
            String ID = "";
            while(resultSet.next()){
                ID = resultSet.getString(1);
            }
            return ID;
        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
            return "";
        }

    }

    public ArrayList<VM> getLatestVmFromCustomerID(String customerID, String lastExtractID){
        ArrayList<VM> allVmsOnHypervisor = new ArrayList<>();
        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("" +
                    "SELECT vm_id, vm_name, dynamic_ram, max_ram, min_ram, cpus, state from vm where extract_id = ? AND customer_id = ?");
            psts.setString(1,lastExtractID);
            psts.setString(2, customerID);
            String vmID = "";
            String vmName = "";
            int dynamicRam = 0;
            int max_ram = 0;
            int min_ram = 0;
            int cpus = 0;
            String state = "";
            ResultSet resultSet = psts.executeQuery();



            while(resultSet.next()){
                vmID = resultSet.getString(1);
                vmName = resultSet.getString(2);
                dynamicRam = resultSet.getInt(3);
                max_ram = resultSet.getInt(4);
                min_ram = resultSet.getInt(5);
                cpus  = resultSet.getInt(6);
                state = resultSet.getString(7);
                allVmsOnHypervisor.add(new VM(vmID,vmName,dynamicRam,max_ram,min_ram,cpus,state));
            }

        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
        }


        return allVmsOnHypervisor;
    }

    public ArrayList<VM> getVMsFromHypervisorWithNoCustomerAssigned(String hypervisorID, String lastExtractID){
        ArrayList<VM> allVmsOnHypervisor = new ArrayList<>();
        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("" +
                    "SELECT vm_id, vm_name, dynamic_ram, max_ram, min_ram, cpus, state from vm where extract_id = ? AND hypervisor_id = ? AND customer_id is null");
            psts.setString(1,lastExtractID);
            psts.setString(2, hypervisorID);
            String vmID = "";
            String vmName = "";
            int dynamicRam = 0;
            int max_ram = 0;
            int min_ram = 0;
            int cpus = 0;
            String state = "";
            ResultSet resultSet = psts.executeQuery();



            while(resultSet.next()){
                vmID = resultSet.getString(1);
                vmName = resultSet.getString(2);
                dynamicRam = resultSet.getInt(3);
                max_ram = resultSet.getInt(4);
                min_ram = resultSet.getInt(5);
                cpus  = resultSet.getInt(6);
                state = resultSet.getString(7);
                allVmsOnHypervisor.add(new VM(vmID,vmName,dynamicRam,max_ram,min_ram,cpus,state));
            }

        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
        }


        return allVmsOnHypervisor;
    }

    public void removeVmFromCustomer(String vmId){
        try{
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement ppst = conn.prepareStatement(
                    "UPDATE vm SET customer_id = null where vm_id = ?");
            ppst.setString(1, vmId);
            ppst.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("DB ERROR");
        }
    }

    public String getHypervisorIDFromVmID(String vmID){
        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("SELECT hypervisor_id from vm where vm_id = ?");
            psts.setString(1,vmID);
            ResultSet resultSet = psts.executeQuery();
            String ID = "";
            while(resultSet.next()){
                ID = resultSet.getString(1);
            }
            return ID;
        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
            return "";
        }

    }


    public void getRAMForVMs(Hypervisor hv) {
        final String pathToRAMData = "staticInfo/" + hv.getHypervisorName() + "/RAMInfo.csv";
        File file = new File(pathToRAMData);
        String input = "";
        String name = "";
        String isDynamic = "";
        String minRAM = "";
        String startupRAM = "";
        String maxRAM = "";

        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            scanner.nextLine();
            while (scanner.hasNext()) {
                input = scanner.nextLine();
                int firstSplit = input.indexOf(",");
                int secondSplit = input.indexOf(",", firstSplit + 1);
                int thirdSplit = input.indexOf(",", secondSplit + 1);
                int fourthSplit = input.indexOf(",", thirdSplit + 1);
                name = input.substring(1, firstSplit - 1);
                isDynamic = input.substring(firstSplit + 2, secondSplit - 1);
                minRAM = input.substring(secondSplit + 2, thirdSplit - 1);
                startupRAM = input.substring(thirdSplit + 2, fourthSplit - 1);
                maxRAM = input.substring(fourthSplit + 2, input.length() - 1);
                hv.getVmBasedOnName(name).addRamToVM(isDynamic, minRAM, startupRAM, maxRAM);

            }
        } catch (FileNotFoundException e) {
            System.out.println("Cant find RAM data for hypervisor " + hv.getHypervisorName());
        }
    }

    public void setStateOfVM(Hypervisor hv) {
        final String pathToStateData = "staticInfo/" + hv.getHypervisorName() + "/StateInfo.csv";


        File file = new File(pathToStateData);
        String input = "";
        String name = "";
        String state = "";
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            scanner.nextLine();
            while (scanner.hasNext()) {
                input = scanner.nextLine();
                name = input.substring(1, input.indexOf(",") - 1);
                state = input.substring(input.indexOf(",") + 2, input.length() - 1);
                for(VM vm : hv.getAllVmsOnHyperVisor()){
                    if(vm.getVmName().equals(name)){
                        vm.setState(state);
                        break;
                    }
                }


            }
        } catch (FileNotFoundException e) {
            System.out.println("Cant find State data for hypervisor " + hv.getHypervisorName());
        }
    }




}
