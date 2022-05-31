package com.example.isncapacity.Model;

public class Disk {
    private String diskName;
    private String diskID;
    private String path;
    private int fileSize;
    private int maxSize;

    public String getDiskID() {
        return diskID;
    }

    public void setDiskID(String diskID) {
        this.diskID = diskID;
    }

    public Disk(String path, int fileSize, int maxSize) {
        this.path = path;
        this.fileSize = fileSize;
        this.maxSize = maxSize;
        this.diskName = getProperlyPlacedDiskName(path);
    }
    public Disk(String diskID, String diskName,String path, int fileSize, int maxSize) {
        this.diskID = diskID;
        this.path = path;
        this.fileSize = fileSize;
        this.maxSize = maxSize;
        this.diskName =diskName;
    }


    public int getFileSize() {
        return fileSize;
    }

    public Disk(String diskName, int maxSize){
        this.diskName = diskName;
        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public String getDiskName() {
        return diskName;
    }

    public String getPath(){
        return path;
    }

    private String getProperlyPlacedDiskName(String path) {
        String diskName = "-1";
        int firstSplit = path.indexOf("\\");
        int secondSplit = path.indexOf("\\", firstSplit + 1);
        int thirdSplit = path.indexOf("\\", secondSplit + 1);
        int fourthSplit = path.indexOf("\\", thirdSplit + 1);
        if (fourthSplit == -1) {
            diskName = path.substring(thirdSplit + 1, path.length() - 5);
        }
        return diskName;
    }


}
