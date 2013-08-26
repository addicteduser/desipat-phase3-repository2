/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalEditModule;

import finalAddModule.AddView;
import finalUniveral.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;

/**
 *
 * @author Vilson
 */
public class EditModel {
    private String username;
    private java.sql.Date dateAcquiredSQL;
    private java.sql.Date retentionPeriodFromSQL;
    private java.sql.Date retentionPeriodToSQL;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the dateAcquiredSQL
     */
    public java.sql.Date getDateAcquiredSQL() {
        return dateAcquiredSQL;
    }

    /**
     * @param dateAcquiredSQL the dateAcquiredSQL to set
     */
    public void setDateAcquiredSQL(java.sql.Date dateAcquiredSQL) {
        this.dateAcquiredSQL = dateAcquiredSQL;
    }

    /**
     * @return the retentionPeriodFromSQL
     */
    public java.sql.Date getRetentionPeriodFromSQL() {
        return retentionPeriodFromSQL;
    }

    /**
     * @param retentionPeriodFromSQL the retentionPeriodFromSQL to set
     */
    public void setRetentionPeriodFromSQL(java.sql.Date retentionPeriodFromSQL) {
        this.retentionPeriodFromSQL = retentionPeriodFromSQL;
    }

    /**
     * @return the retentionPeriodToSQL
     */
    public java.sql.Date getRetentionPeriodToSQL() {
        return retentionPeriodToSQL;
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
