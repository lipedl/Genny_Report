/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estruturaDeDados;

/**
 *
 * @author user
 */
public class MotherBoard {

    private String manufacture;
    private String boardModel;
    private String serialNumber;

    public MotherBoard(String manufacture, String boardModel, String serialN) {
        this.manufacture = manufacture;
        this.boardModel = boardModel;
        this.serialNumber = serialN;
    }

    public String getManufacture() {
        return manufacture;
    }

    public String getBoardModel() {
        return boardModel;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public void setBoardModel(String boardModel) {
        this.boardModel = boardModel;
    }

    public void setSerialNumber(String serialN) {
        this.serialNumber = serialN;
    }
}
