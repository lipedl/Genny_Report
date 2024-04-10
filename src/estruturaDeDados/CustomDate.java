/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estruturaDeDados;

import org.joda.time.DateTime;

/**
 *
 * @author user
 */
public class CustomDate {

    DateTime date;

    public CustomDate() {
        date = new DateTime();
    }

    public String getFormatedDate() {
        if (date.getDayOfMonth() < 10) {
            if (date.getMonthOfYear() < 10) {
                return "0" + date.getDayOfMonth() + "/0" + date.getMonthOfYear()
                        + "/" + date.getYear();
            } else {
                return "0" + date.getDayOfMonth() + "/" + date.getMonthOfYear()
                        + "/" + date.getYear();
            }
        } else {
            return date.getDayOfMonth() + "/" + date.getMonthOfYear()
                    + "/" + date.getYear();
        }
    }
}
