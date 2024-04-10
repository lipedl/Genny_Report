package model;

import estruturaDeDados.Cpu;
import estruturaDeDados.DiskData;
import estruturaDeDados.DiskUsage;
import estruturaDeDados.Memory;
import estruturaDeDados.MotherBoard;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Agent {

    private Cpu cpu;
    private String gpu;
    private String publicIp;
    private String localIp;
    private ArrayList<DiskUsage> diskUsageList;
    private ArrayList<DiskData> diskDataList;
    private String platf;
    private String os;
    private String user;
    private String productModel;
    private String hostname;
    private String monitoringType;
    private String description;
    private MotherBoard mobo;
    private ArrayList<Memory> memorys;
    private int totalRam, id;
    private String client;
    private String site;
    private String[][] customFields;

    public Agent() {
    }

    public Agent(Cpu cpu, String gpu, String model, String localIp, ArrayList<DiskData> disks) {
        this.cpu = cpu;
        this.gpu = gpu;
        this.productModel = model;
        this.localIp = localIp;
        this.diskDataList = disks;
    }

    public Agent(Cpu cpu, String gpu, String localIp, String productModel, MotherBoard mobo, ArrayList<Memory> memorys, ArrayList<DiskData> disks) {
        this.cpu = cpu;
        this.gpu = gpu;
        this.localIp = localIp;
        this.productModel = productModel;
        this.mobo = mobo;
        this.memorys = memorys;
        this.diskDataList = disks;
    }

    public Agent(int id, Cpu cpu, MotherBoard mobo, String gpu, String publicIp, String localIp, ArrayList<DiskUsage> disks, String platf, String os, String user, String productModel, String hostname, String serialN, ArrayList<Memory> mem, int totalRam) {
        this.id = id;
        this.cpu = cpu;
        this.mobo = mobo;
        this.gpu = gpu;
        this.publicIp = publicIp;
        this.localIp = localIp;
        this.diskUsageList = disks;
        this.platf = platf;
        this.os = os;
        this.user = user;
        this.productModel = productModel;
        this.hostname = hostname;
        this.memorys = mem;
        this.totalRam = totalRam;
    }

    public void setCustomFields(String[][] customFields) {
        this.customFields = customFields;
    }

    public String[][] getCustomFields() {
        return customFields;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public ArrayList<DiskUsage> getDiskUsageList() {
        return diskUsageList;
    }

    public void addDiskUsage(DiskUsage disk) {
        this.diskUsageList.add(disk);
    }

    public void addDiskData(DiskData disk) {
        this.diskDataList.add(disk);
    }

    public String getPlatf() {
        return platf;
    }

    public void setPlatf(String platf) {
        this.platf = platf;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void addMemory(Memory mem) {
        this.memorys.add(mem);
    }

    public MotherBoard getMobo() {
        return mobo;
    }

    public void setMobo(MotherBoard mobo) {
        this.mobo = mobo;
    }

    public ArrayList<Memory> getMemorys() {
        return memorys;
    }

    public void setMemorys(ArrayList<Memory> memorys) {
        this.memorys = memorys;
    }

    public int getTotalRam() {
        return totalRam;
    }

    public void setTotalRam(int totalRam) {
        this.totalRam = totalRam;
    }

    public void setDiskUsageList(ArrayList<DiskUsage> disks) {
        this.diskUsageList = disks;
    }

    public int getId() {
        return this.id;
    }

    public ArrayList<DiskData> getDiskDataList() {
        return diskDataList;
    }

    public void setDiskDataList(ArrayList<DiskData> diskDataList) {
        this.diskDataList = diskDataList;
    }

    public String getMonitoringType() {
        return monitoringType;
    }

    public void setMonitoringType(String monitoringType) {
        this.monitoringType = monitoringType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClient() {
        return this.client;
    }

    public boolean isWindows() {
        if (platf.equals("windows")) {
            return true;
        } else {
            return false;
        }
    }

    public void setParam(int id, String client, String site, String os, String plat, String hostname,
            String publicIp, int totalRam, String user, String monitoringType,
            String description) {
        this.id = id;
        this.client = client;
        this.os = os;
        this.platf = plat;
        this.hostname = hostname;
        this.publicIp = publicIp;
        this.totalRam = totalRam;
        this.user = user;
        this.monitoringType = monitoringType;
        this.description = description;
        this.site = site;
    }
}
