/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.dataStrucure;

import java.sql.Date;

/**
 *
 * @author Libra
 */
public class BatchNInterest {

    private Date date;
    private int interest;
    private int div;

    public BatchNInterest(Date dateIn, int instIn) {
        date = dateIn;
        interest = instIn;
    }

    public BatchNInterest(int divIn, int instIn) {
        div = divIn;
        interest = instIn;
    }

    public Date getDate() {
        return date;
    }

    public int getInterest() {
        return interest;
    }
    
    public int getDiv(){
        return div;
    }
}
