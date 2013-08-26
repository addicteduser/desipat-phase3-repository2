/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalAddModule;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.Timer;

import finalUniveral.DateUtil;

/**
 *
 * @author Vilson
 */
public class AddModel {

    private java.sql.Date dateAcquiredSQL;
    private java.sql.Date retentionPeriodFromSQL;
    private java.sql.Date retentionPeriodToSQL;

    /**
     * @return the dateAcquiredSQL
     */
    public java.sql.Date getDateAcquiredSQL() {
        return dateAcquiredSQL;
    }

    /**
     * @return the retentionPeriodFromSQL
     */
    public java.sql.Date getRetentionPeriodFromSQL() {
        return retentionPeriodFromSQL;
    }

    /**
     * @return the retentionPeriodToSQL
     */
    public java.sql.Date getRetentionPeriodToSQL() {
        return retentionPeriodToSQL;
    }

    /**
     * @param dateAcquiredSQL the dateAcquiredSQL to set
     */
    public void setDateAcquiredSQL(java.sql.Date dateAcquiredSQL) {
        this.dateAcquiredSQL = dateAcquiredSQL;
    }

    /**
     * @param retentionPeriodFromSQL the retentionPeriodFromSQL to set
     */
    public void setRetentionPeriodFromSQL(java.sql.Date retentionPeriodFromSQL) {
        this.retentionPeriodFromSQL = retentionPeriodFromSQL;
    }

    /**
     * @param retentionPeriodToSQL the retentionPeriodToSQL to set
     */
    public void setRetentionPeriodToSQL(java.sql.Date retentionPeriodToSQL) {
        this.retentionPeriodToSQL = retentionPeriodToSQL;
    }
    
  
    public java.sql.Date parseDateAcquired(JComboBox dateAcquiredDay, JComboBox dateAcquiredMonth, JComboBox dateAcquiredYear){
        String stringDate1;
      

        //stringDate1 = dateAcquiredMonth.getSelectedItem().toString() + " " +dateAcquiredDay.getSelectedItem().toString() + ", " + dateAcquiredYear.getSelectedItem().toString();
        stringDate1 = dateAcquiredDay.getSelectedItem().toString() + "-" + Integer.toString(dateAcquiredMonth.getSelectedIndex()+1) + "-" + dateAcquiredYear.getSelectedItem().toString();
       
        java.sql.Date sqlDate = null;
        try {

           Date dateAcquired = new SimpleDateFormat("dd-MM-yyyy").parse(stringDate1);
           sqlDate = DateUtil.utilDateToSqlDate(dateAcquired);
          
        } catch (ParseException ex) {
          Logger.getLogger(AddView.class.getName()).log(Level.SEVERE, null, ex);

        }
        
         return sqlDate;
       // checkDate(dateAcquired, retentionDate); 

    }

}
