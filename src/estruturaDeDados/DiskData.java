/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estruturaDeDados;

/**
 *
 * @author user
 */
public class DiskData {

    private String storage;
    private String diskName;
    private String fullLabel;

    public DiskData(String storage, String diskName) {
        this.diskName = diskName;
        this.storage = storage;
    }

    public DiskData(String fullLabel) {
        this.fullLabel = fullLabel;
    }

    public String getFullLabel() {
        return this.fullLabel;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

}
