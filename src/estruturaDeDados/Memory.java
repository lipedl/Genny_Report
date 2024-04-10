/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estruturaDeDados;

/**
 *
 * @author user
 */
public class Memory {

    private long capacity;
    private String speed;
    private String manufacturer;

    public Memory(long capacity, String speed, String manufacturer) {
        this.capacity = ((capacity / 1024) / 1024) / 1024;
        this.speed = speed;
        this.manufacturer = manufacturer;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

}
