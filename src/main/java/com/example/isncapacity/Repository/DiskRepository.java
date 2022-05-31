package com.example.isncapacity.Repository;

import com.example.isncapacity.Model.Disk;
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

public class DiskRepository {


    public ArrayList<Disk> getVHDXData(Hypervisor hv) {
        ArrayList<Disk> allDisksOnHypervisor = new ArrayList<>();
        final String pathToDiskData = "staticInfo/" + hv.getHypervisorName() + "/DiskInfo.csv";
        File file = new File(pathToDiskData);
        String input = "";
        String path = "";
        String fileSize = "";
        String maxSize ="";

        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            scanner.nextLine();
            while (scanner.hasNext()) {
                input = scanner.nextLine();
                int firstSplit = input.indexOf(",");
                int secondSplit = input.indexOf(",", firstSplit + 1);
                path = input.substring(1, firstSplit - 1);
                fileSize = input.substring(firstSplit + 2, secondSplit - 1);
                maxSize = input.substring(secondSplit + 2, input.length() - 1);

                Long fileSizeLong = Long.parseLong(fileSize) / 1024 / 1024 / 1024;
                int fileSizeInt = fileSizeLong.intValue();

                Long maxSizeLong = Long.parseLong(maxSize) / 1024 / 1024 / 1024;
                int  maxSizeInt = maxSizeLong.intValue();

                allDisksOnHypervisor.add(new Disk(path,fileSizeInt,maxSizeInt));

            }
        } catch (FileNotFoundException e) {
            System.out.println("Cant find CPU file for hypervisor " + hv.getHypervisorName());
        }
        return allDisksOnHypervisor;
    }

    public ArrayList<Disk> getVHDXForVM (VM VM, String lastExtractID){
        ArrayList<Disk> disksOnVM = new ArrayList<>();
        String diskID = "";
        String diskName ="";
        String path ="";
        int file_size = 0;
        int max_size = 0;

        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("SELECT disk_id,disk_name, path, file_size, max_size from disk where extract_id = ? AND vm_id = ?");
            psts.setString(1,lastExtractID);
            psts.setString(2, VM.getVmID());
            ResultSet resultSet = psts.executeQuery();

            while(resultSet.next()){
                diskID = resultSet.getString(1);
                diskName = resultSet.getString(2);
                path = resultSet.getString(3);
                file_size = resultSet.getInt(4);
                max_size = resultSet.getInt(5);
                disksOnVM.add(new Disk(diskID,diskName,path,file_size,max_size));
            }

        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
        }


        return disksOnVM;

    }

    public ArrayList<Disk> getUnassignedDisksFromLatestExtract (Hypervisor hv, String lastExtractID){
        ArrayList<Disk> unassignedDisks = new ArrayList<>();
        String diskID = "";
        String diskName ="";
        String path ="";
        int file_size = 0;
        int max_size = 0;

        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("SELECT disk_id,disk_name, path, file_size, max_size from disk where extract_id = ? AND hypervisor_id = ?");
            psts.setString(1,lastExtractID);
            psts.setString(2, hv.getHypervisorID());
            ResultSet resultSet = psts.executeQuery();

            while(resultSet.next()){
                diskID = resultSet.getString(1);
                diskName = resultSet.getString(2);
                path = resultSet.getString(3);
                file_size = resultSet.getInt(4);
                max_size = resultSet.getInt(5);
                unassignedDisks.add(new Disk(diskID,diskName,path,file_size,max_size));
            }

        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
        }


        return unassignedDisks;

    }

    public Disk getDiskObjectFromName(String diskToFind, String lastExtractID){
        Disk disk = null;
        String diskID = "";
        String diskName ="";
        String path ="";
        int file_size = 0;
        int max_size = 0;

        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("SELECT disk_id,disk_name, path, file_size, max_size from disk where extract_id = ? AND disk_name = ?");
            psts.setString(1,lastExtractID);
            psts.setString(2, diskToFind);
            ResultSet resultSet = psts.executeQuery();

            while(resultSet.next()){
                diskID = resultSet.getString(1);
                diskName = resultSet.getString(2);
                path = resultSet.getString(3);
                file_size = resultSet.getInt(4);
                max_size = resultSet.getInt(5);
                disk = new Disk(diskID,diskName,path,file_size,max_size);
            }

        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
        }


        return disk;

    }

    public Disk getDiskFromId(String diskIDToFind){
        Disk disk = null;
        String diskID = "";
        String diskName ="";
        String path ="";
        int file_size = 0;
        int max_size = 0;

        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("SELECT disk_id,disk_name, path, file_size, max_size from disk where disk_id = ?");
            psts.setString(1, diskIDToFind);
            ResultSet resultSet = psts.executeQuery();

            while(resultSet.next()){
                diskID = resultSet.getString(1);
                diskName = resultSet.getString(2);
                path = resultSet.getString(3);
                file_size = resultSet.getInt(4);
                max_size = resultSet.getInt(5);
                disk = new Disk(diskID,diskName,path,file_size,max_size);
            }

        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
        }


        return disk;

    }

    public void saveVMDiskToDB(ArrayList<Disk> disks, String vmID, String thisExtractID){
        Connection conn = DatabaseConnectionManager.getConnection();
        for(Disk disk : disks){
            try{
                PreparedStatement ppst = conn.prepareStatement(
                        "insert into disk(disk_name, path, file_size, max_size, extract_id, vm_id ) values(?,?,?,?,?,?)");
                ppst.setString(1,disk.getDiskName());
                ppst.setString(2,disk.getPath());
                ppst.setInt(3,disk.getFileSize());
                ppst.setInt(4,disk.getMaxSize());
                ppst.setString(5,thisExtractID);
                ppst.setString(6,vmID);
                ppst.executeUpdate();
            }
            catch (SQLException e){
                System.out.println("DB ERROR");
            }
            disk.setDiskID(getLastCreatedDiskID());

        }
    }

    public void saveHypervisorDiskToDB(ArrayList<Disk> disks, String hypervisorID, String thisExtractID){
        Connection conn = DatabaseConnectionManager.getConnection();
        for(Disk disk : disks){
            try{
                PreparedStatement ppst = conn.prepareStatement(
                        "insert into disk(disk_name, path, file_size, max_size, extract_id, hypervisor_id ) values(?,?,?,?,?,?)");
                ppst.setString(1,disk.getDiskName());
                ppst.setString(2,disk.getPath());
                ppst.setInt(3,disk.getFileSize());
                ppst.setInt(4,disk.getMaxSize());
                ppst.setString(5,thisExtractID);
                ppst.setString(6,hypervisorID);
                ppst.executeUpdate();
            }
            catch (SQLException e){
                System.out.println("DB ERROR");
            }
            disk.setDiskID(getLastCreatedDiskID());

        }
    }

    private String getLastCreatedDiskID(){
        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("SELECT disk_id from disk order by disk_id DESC LIMIT 1");
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

    public void saveBindNewDiskToVM(Disk disk, VM vm){

        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("UPDATE disk SET vm_id = ?, hypervisor_id=null WHERE disk_id =?");
            psts.setString(1,vm.getVmID());
            psts.setString(2,disk.getDiskID());
            psts.executeUpdate();
        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
        }

    }

    public String getVMNameOfPreviousExrtactDisk(Disk disk, String extractID){
        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("SELECT vm_name from disk \n" +
                    "left join vm on disk.vm_id=vm.vm_id\n" +
                    "where disk.disk_name = ? AND disk.extract_id = ?");
            psts.setString(1, disk.getDiskName());
            psts.setString(2, extractID);
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

    public String getLatestVersionOfVmIDFromName(String vmName, String extractID){
        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("SELECT vm_id from vm where vm_name = ? AND extract_id = ?");
            psts.setString(1, vmName);
            psts.setString(2, extractID);
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

    public void unbindDiskFromVm(String diskID, String hypervisorID){
        try {
            Connection conn = DatabaseConnectionManager.getConnection();
            PreparedStatement psts = conn.prepareStatement("UPDATE disk SET vm_id = null, hypervisor_id=? WHERE disk_id =?");
            psts.setString(1,hypervisorID);
            psts.setString(2,diskID);
            psts.executeUpdate();
        }

        catch (SQLException e){
            System.out.println("Cannot connect to database or error in database");
            e.printStackTrace();
        }
    }




}
