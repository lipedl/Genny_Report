/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estruturaDeDados;

/**
 *
 * @author user
 */
public class Cpu {

    private String cpuName;
    private int cpuCoreCount;

    public Cpu(String name, int coreCount) {
        cpuName = name;
        cpuCoreCount = coreCount;
    }

    public String getCpuName() {
        return cpuName;
    }

    public int getCpuCoreCount() {
        return cpuCoreCount;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public void setCpuCoreCount(int cpuCoreCount) {
        this.cpuCoreCount = cpuCoreCount;
    }

}
