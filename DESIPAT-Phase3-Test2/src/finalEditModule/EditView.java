/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalEditModule;

import DAO.*;
import assetreg.numericTextfield;
import finalEditModule.EditController.Listener;
import finalUniveral.AbstractNavGUI;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import dataModel.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 *
 * @author JT
 */
public class EditView extends AbstractNavGUI implements ActionListener {

    protected JLabel bg;
    private JLabel editasset;
    private JLabel successful;
    private ImageIcon[] editassetIcon;
    private String assetType;
    private JTextField assetOwner;
    private JTextField assetCustodian;
    private numericTextfield financial;
    private JTextField storageLocation;
    private AssetModel details = AssetModel.getInstance();
    private Listener l;
 
    protected Connection connect;
    private JComboBox assetName = new JComboBox();
    private JComboBox typelist = new JComboBox();
    private JComboBox classificationlist = new JComboBox();
    private JComboBox confidentialitylist = new JComboBox();
    private JComboBox integritylist = new JComboBox();
    private JComboBox availabilitylist = new JComboBox();
    public String[] month = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private JComboBox dateAcquiredYear = new JComboBox();
    private JComboBox dateAcquiredMonth = new JComboBox();
    private JComboBox dateAcquiredDay = new JComboBox();
    private JComboBox retentionPeriodFromYear = new JComboBox();
    private JComboBox retentionPeriodFromMonth = new JComboBox();
    private JComboBox retentionPeriodFromDay = new JComboBox();
    private JComboBox retentionPeriodToYear = new JComboBox();
    private JComboBox retentionPeriodToMonth = new JComboBox();
    private JComboBox retentionPeriodToDay = new JComboBox();
    private JComboBox maintenanceSched = new JComboBox();

    Calendar calendar = Calendar.getInstance();

    public EditView() {
        makePanel();
    }

    public void populateYear(JComboBox year, int startYear) {
        year.setModel(new DefaultComboBoxModel());
        for (int i = startYear; i <= 2030; i++) {
            year.addItem(i);
        }
    }

    public void populateMonth() {
        ResourceBundle rb = ResourceBundle.getBundle("months");
        Enumeration<String> settings = rb.getKeys();
        for (int i = 1; i <= 12; i++) {
            getDateAcquiredMonth().addItem(rb.getString(Integer.toString(i)));
            getRetentionPeriodFromMonth().addItem(rb.getString(Integer.toString(i)));
            getRetentionPeriodToMonth().addItem(rb.getString(Integer.toString(i)));
        }
    }

    public void populateDay(JComboBox year, JComboBox month, JComboBox day) {
        calendar.set(Integer.parseInt(year.getSelectedItem().toString()), month.getSelectedIndex(), 1);

        day.setModel(new DefaultComboBoxModel());

        for (int i = 1; i <= calendar.getActualMaximum(calendar.DAY_OF_MONTH); i++) {
            day.addItem(i);
        }

    }

    public void checkDate(Date dateAcquired, Date retentionDate) {
        if (dateAcquired.after(retentionDate)) {
            getRetentionPeriodToYear().setBackground(Color.getHSBColor(353, 0.58f, 0.92f));
            getRetentionPeriodToMonth().setBackground(Color.getHSBColor(353, 0.58f, 0.92f));
            getRetentionPeriodToDay().setBackground(Color.getHSBColor(353, 0.58f, 0.92f));
        } else {
            getRetentionPeriodToYear().setBackground(Color.white);
            getRetentionPeriodToMonth().setBackground(Color.white);
            getRetentionPeriodToDay().setBackground(Color.white);
        }
    }

    public void populateDetails(AssetModel det) {
        int type, dAY, rAY, mS, conf, integ, avail, classif;
        type = dAY = rAY = mS = conf = integ = avail = classif = 0;
        getAssetOwner().setText(det.getAssetowner());
        getAssetCustodian().setText(det.getAssetcustodian());

        for (int i = 0; i < getTypelist().getItemCount(); i++) {
            getTypelist().setSelectedIndex(i);
            if (getTypelist().getSelectedItem().toString().equals(det.getType())) {
                type = i;
            }
        }
        getTypelist().setSelectedIndex(type);

        //Date acquired
        String[] dateAcquiredString;
        String[] retentionDateString;

        dateAcquiredString = det.getDateAcquired().toString().split("-");
        retentionDateString = det.getRetentionPeriodTo().toString().split("-");

        for (int i = 0; i < getDateAcquiredYear().getItemCount(); i++) {
            getDateAcquiredYear().setSelectedIndex(i); 
            if (getDateAcquiredYear().getSelectedItem().toString().equals(dateAcquiredString[0])) {
                dAY = i;
            }
        }
        getDateAcquiredYear().setSelectedIndex(dAY);
        getDateAcquiredMonth().setSelectedIndex(Integer.parseInt(dateAcquiredString[1]) - 1);
        getDateAcquiredDay().setSelectedIndex(Integer.parseInt(dateAcquiredString[2]) - 1);

        for (int i = 0; i < getRetentionPeriodToYear().getItemCount(); i++) {
            getRetentionPeriodToYear().setSelectedIndex(i);
            if (getRetentionPeriodToYear().getSelectedItem().toString().equals(retentionDateString[0])) {
                rAY = i;
            }
        }
        getRetentionPeriodToYear().setSelectedIndex(rAY);
        getRetentionPeriodToMonth().setSelectedIndex(Integer.parseInt(retentionDateString[1]) - 1);
        getRetentionPeriodToDay().setSelectedIndex(Integer.parseInt(retentionDateString[2]) - 1);
        
        for (int i = 0; i < getRetentionPeriodFromYear().getItemCount(); i++) {
            getRetentionPeriodFromYear().setSelectedIndex(i);
            if (getRetentionPeriodFromYear().getSelectedItem().toString().equals(dateAcquiredString[0])) {
                rAY = i;
            }
        }
        getRetentionPeriodFromYear().setSelectedIndex(rAY);
        getRetentionPeriodFromMonth().setSelectedIndex(Integer.parseInt(dateAcquiredString[1]) - 1);
        getRetentionPeriodFromDay().setSelectedIndex(Integer.parseInt(dateAcquiredString[2]) - 1);

        
        //Maintenance Schedule
        for (int i = 0; i < getMaintenanceSched().getItemCount(); i++) {
            getMaintenanceSched().setSelectedIndex(i);
            if (getMaintenanceSched().getSelectedItem().toString().equals(det.getSchedule())) {
                mS = i;
            }
        }
        getMaintenanceSched().setSelectedIndex(mS);

        //Confidentiality
        for (int i = 0; i < getConfidentialitylist().getItemCount(); i++) {
            getConfidentialitylist().setSelectedIndex(i);
            if (getConfidentialitylist().getSelectedItem().toString().equals(det.getConfidentiality())) {
                conf = i;
            }
        }
        getConfidentialitylist().setSelectedIndex(conf);

        //Integrity
        for (int i = 0; i < getIntegritylist().getItemCount(); i++) {
            getIntegritylist().setSelectedIndex(i);
            if (getIntegritylist().getSelectedItem().toString().equals(det.getIntegrity())) {
                integ = i;
            }
        }
        getIntegritylist().setSelectedIndex(integ);

        //Availability
        for (int i = 0; i < getAvailabilitylist().getItemCount(); i++) {
            getAvailabilitylist().setSelectedIndex(i);
            if (getAvailabilitylist().getSelectedItem().toString().equals(det.getAvailability())) {
                avail = i;
            }
        }
        getAvailabilitylist().setSelectedIndex(avail);

        //Classification
        for (int i = 0; i < getClassificationlist().getItemCount(); i++) {
            getClassificationlist().setSelectedIndex(i);
            if (getClassificationlist().getSelectedItem().toString().equals(det.getClassification())) {
                classif = i;
            }
        }
        getClassificationlist().setSelectedIndex(classif);

        getFinancial().setText(Integer.toString(det.getFinancial()));
        getStorageLocation().setText(det.getStoragelocation());
    }

    @Override
    public void initialize() throws ClassNotFoundException, SQLException {
        setFinancial(new numericTextfield());

        // ADD ASSETNAMES IN COMBOBOX
        try {
            ArrayList<String> name = AssetDAO.getInstance().getAllAssetNames();

            for (int i = 0; i < name.size(); i++) {
                getAssetName().addItem(name.get(i));
            }
            try {
                AssetDAO.getInstance().getAssetDetails(getAssetName().getSelectedItem().toString());
            } catch (Exception e) {
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // ADD ASSET TYPES IN COMBOBOX
        try {
            ArrayList<String> atmdl = AssetTypeDAO.getInstance().getAllAssetTypes();
            for (int i = 0; i < atmdl.size(); i++) {
                getTypelist().addItem(atmdl.get(i).toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // ADD CLASSIFICATION IN COMBOBOX
        try {

            ArrayList<String> cmdl = ClassificationDAO.getInstance().getAllClassification();
            for (int i = 0; i < cmdl.size(); i++) {
                getClassificationlist().addItem(cmdl.get(i).toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // ADD VALUE LIST IN COMBOBOX
        try {
            ArrayList<String> vtmdl = ValueTypeDAO.getInstance().getAllValueType();
            for (int i = 0; i < vtmdl.size(); i++) {
                getConfidentialitylist().addItem(vtmdl.get(i).toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            ArrayList<String> vtmdl = ValueTypeDAO.getInstance().getAllValueType();
            for (int i = 0; i < vtmdl.size(); i++) {
                getAvailabilitylist().addItem(vtmdl.get(i).toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            ArrayList<String> vtmdl = ValueTypeDAO.getInstance().getAllValueType();
            for (int i = 0; i < vtmdl.size(); i++) {
                getIntegritylist().addItem(vtmdl.get(i).toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            ArrayList<String> msmdl = MaintenanceSchedDAO.getInstance().getAllMaintenanceSchedule();
            for (int i = 0; i < msmdl.size(); i++) {
                getMaintenanceSched().addItem(msmdl.get(i).toString());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        populateYear(getDateAcquiredYear(), Calendar.getInstance().get(Calendar.YEAR));
        //populateYear(retentionPeriodToYear, calendar.getInstance().get(Calendar.YEAR));
        populateMonth();
        getTypelist().setSelectedIndex(0);
        getTypelist().addActionListener(this);
        getClassificationlist().setSelectedIndex(0);
        getClassificationlist().addActionListener(this);
        getConfidentialitylist().setSelectedIndex(0);
        getConfidentialitylist().addActionListener(this);
        getIntegritylist().setSelectedIndex(0);
        getIntegritylist().addActionListener(this);
        getAvailabilitylist().setSelectedIndex(0);
        getAvailabilitylist().addActionListener(this);

        getRetentionPeriodFromDay().setEnabled(false);
        getRetentionPeriodFromMonth().setEnabled(false);
        getRetentionPeriodFromYear().setEnabled(false);
        getRetentionPeriodToYear().setEnabled(false);
        getRetentionPeriodToMonth().setEnabled(false);
        getRetentionPeriodToDay().setEnabled(false);


        getAssetName().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AssetDAO.getInstance().getAssetDetails(getAssetName().getSelectedItem().toString());
                    populateDetails(getDetails());

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        setEditassetIcon(new ImageIcon[2]);
        getEditassetIcon()[0] = new ImageIcon(this.getClass().getClassLoader().getResource("finalEditModule/editasset.png"));
        getEditassetIcon()[1] = new ImageIcon(this.getClass().getClassLoader().getResource("finalEditModule/editasset.png"));

        bg = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("finalEditModule/editassetbg.png")));

        setEditasset(new JLabel(getEditassetIcon()[0]));
        setSuccessful(new JLabel("The asset has been successfully added!"));

        getSuccessful().setForeground(Color.WHITE);
        getSuccessful().setFont(new Font("calibri", Font.ITALIC, 20));
        getSuccessful().setVisible(false);


        setAssetOwner(new JTextField());
        setAssetCustodian(new JTextField());
        setStorageLocation(new JTextField());
        //    financial = new JTextField();
        bg = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("finalEditModule/editassetbg.png")));

        getEditasset().addMouseListener(l);
        getTypelist().addMouseListener(l);
        getClassificationlist().addMouseListener(l);
        getConfidentialitylist().addMouseListener(l);
        getAvailabilitylist().addMouseListener(l);
        getIntegritylist().addMouseListener(l);
        getStorageLocation().addMouseListener(l);
        getAssetName().addMouseListener(l);
        getAssetOwner().addMouseListener(l);
        getFinancial().addMouseListener(l);
        getAssetCustodian().addMouseListener(l);

    }

    @Override
    public void setBounds() {
        bg.setBounds(0, 0, bg.getIcon().getIconWidth(), bg.getIcon().getIconHeight());
        getEditasset().setBounds(720, 477, getEditasset().getIcon().getIconWidth(), getEditasset().getIcon().getIconHeight());
        getTypelist().setBounds(300, 95, 110, 30);
        getClassificationlist().setBounds(300, 430, 110, 30);
        getConfidentialitylist().setBounds(300, 300, 110, 30);
        getIntegritylist().setBounds(300, 343, 110, 30);
        getAvailabilitylist().setBounds(300, 383, 110, 30);
        getSuccessful().setBounds(500, 450, 500, 30);

        getFinancial().setBounds(300, 258, 110, 30);
        getStorageLocation().setBounds(300, 471, 110, 30);
        getAssetName().setBounds(340, 20, 200, 24);
        getAssetOwner().setBounds(340, 51, 200, 24);
        getAssetCustodian().setBounds(693, 20, 170, 24);

        getDateAcquiredYear().setBounds(300, 135, 110, 30);
        getDateAcquiredMonth().setBounds(410, 135, 110, 30);
        getDateAcquiredDay().setBounds(520, 135, 110, 30);
        getRetentionPeriodFromYear().setBounds(300, 175, 90, 30);
        getRetentionPeriodFromMonth().setBounds(390, 175, 90, 30);
        getRetentionPeriodFromDay().setBounds(480, 175, 90, 30);
        getRetentionPeriodToYear().setBounds(600, 175, 90, 30);
        getRetentionPeriodToMonth().setBounds(690, 175, 90, 30);
        getRetentionPeriodToDay().setBounds(780, 175, 90, 30);
        getMaintenanceSched().setBounds(300, 215, 110, 30);


    }

    @Override
    public void setPanelSize() {
        this.setSize(bg.getIcon().getIconWidth(), bg.getIcon().getIconHeight());
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void addToPanel() {
        this.add(getEditasset());
        this.add(getTypelist());
        this.add(getDateAcquiredYear());
        this.add(getDateAcquiredMonth());
        this.add(getDateAcquiredDay());
        this.add(getRetentionPeriodFromYear());
        this.add(getRetentionPeriodFromMonth());
        this.add(getRetentionPeriodFromDay());
        this.add(getRetentionPeriodToYear());
        this.add(getRetentionPeriodToMonth());
        this.add(getRetentionPeriodToDay());
        this.add(getMaintenanceSched());
        this.add(getClassificationlist());
        this.add(getConfidentialitylist());
        this.add(getIntegritylist());
        this.add(getAvailabilitylist());
        this.add(getSuccessful());
        this.add(getAssetName());
        this.add(getAssetCustodian());
        this.add(getFinancial());
        this.add(getStorageLocation());
        this.add(getAssetOwner());

        this.add(bg);
    }

    @Override
    public void makePanel() {
        try {
            initialize();
            setBounds();
            setPanelSize();
            addToPanel();
            populateDay(getDateAcquiredYear(), getDateAcquiredMonth(), getDateAcquiredDay());
            populateYear(getRetentionPeriodToYear(), Integer.parseInt(getDateAcquiredYear().getSelectedItem().toString()));
            populateDay(getRetentionPeriodToYear(), getRetentionPeriodToMonth(), getRetentionPeriodToDay());
            try {
                populateDetails(getDetails());
            } catch (Exception ex) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * @return the successful
     */
    public JLabel getSuccessful() {
        return successful;
    }

    /**
     * @param successful the successful to set
     */
    public void setSuccessful(JLabel successful) {
        this.successful = successful;
    }

    /**
     * @return the editassetIcon
     */
    public ImageIcon[] getEditassetIcon() {
        return editassetIcon;
    }

    /**
     * @param editassetIcon the editassetIcon to set
     */
    public void setEditassetIcon(ImageIcon[] editassetIcon) {
        this.editassetIcon = editassetIcon;
    }

    /**
     * @return the assetType
     */
    public String getAssetType() {
        return assetType;
    }

    /**
     * @param assetType the assetType to set
     */
    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    /**
     * @return the assetOwner
     */
    public JTextField getAssetOwner() {
        return assetOwner;
    }

    /**
     * @param assetOwner the assetOwner to set
     */
    public void setAssetOwner(JTextField assetOwner) {
        this.assetOwner = assetOwner;
    }

    /**
     * @return the assetCustodian
     */
    public JTextField getAssetCustodian() {
        return assetCustodian;
    }

    /**
     * @param assetCustodian the assetCustodian to set
     */
    public void setAssetCustodian(JTextField assetCustodian) {
        this.assetCustodian = assetCustodian;
    }

    /**
     * @return the financial
     */
    public numericTextfield getFinancial() {
        return financial;
    }

    /**
     * @param financial the financial to set
     */
    public void setFinancial(numericTextfield financial) {
        this.financial = financial;
    }

    /**
     * @return the storageLocation
     */
    public JTextField getStorageLocation() {
        return storageLocation;
    }

    /**
     * @param storageLocation the storageLocation to set
     */
    public void setStorageLocation(JTextField storageLocation) {
        this.storageLocation = storageLocation;
    }

    /**
     * @return the assetName
     */
    public JComboBox getAssetName() {
        return assetName;
    }

    /**
     * @param assetName the assetName to set
     */
    public void setAssetName(JComboBox assetName) {
        this.assetName = assetName;
    }

    /**
     * @return the typelist
     */
    public JComboBox getTypelist() {
        return typelist;
    }

    /**
     * @param typelist the typelist to set
     */
    public void setTypelist(JComboBox typelist) {
        this.typelist = typelist;
    }

    /**
     * @return the classificationlist
     */
    public JComboBox getClassificationlist() {
        return classificationlist;
    }

    /**
     * @param classificationlist the classificationlist to set
     */
    public void setClassificationlist(JComboBox classificationlist) {
        this.classificationlist = classificationlist;
    }

    /**
     * @return the confidentialitylist
     */
    public JComboBox getConfidentialitylist() {
        return confidentialitylist;
    }

    /**
     * @param confidentialitylist the confidentialitylist to set
     */
    public void setConfidentialitylist(JComboBox confidentialitylist) {
        this.confidentialitylist = confidentialitylist;
    }

    /**
     * @return the integritylist
     */
    public JComboBox getIntegritylist() {
        return integritylist;
    }

    /**
     * @param integritylist the integritylist to set
     */
    public void setIntegritylist(JComboBox integritylist) {
        this.integritylist = integritylist;
    }

    /**
     * @return the availabilitylist
     */
    public JComboBox getAvailabilitylist() {
        return availabilitylist;
    }

    /**
     * @param availabilitylist the availabilitylist to set
     */
    public void setAvailabilitylist(JComboBox availabilitylist) {
        this.availabilitylist = availabilitylist;
    }

    /**
     * @return the dateAcquiredYear
     */
    public JComboBox getDateAcquiredYear() {
        return dateAcquiredYear;
    }

    /**
     * @param dateAcquiredYear the dateAcquiredYear to set
     */
    public void setDateAcquiredYear(JComboBox dateAcquiredYear) {
        this.dateAcquiredYear = dateAcquiredYear;
    }

    /**
     * @return the dateAcquiredMonth
     */
    public JComboBox getDateAcquiredMonth() {
        return dateAcquiredMonth;
    }

    /**
     * @param dateAcquiredMonth the dateAcquiredMonth to set
     */
    public void setDateAcquiredMonth(JComboBox dateAcquiredMonth) {
        this.dateAcquiredMonth = dateAcquiredMonth;
    }

    /**
     * @return the dateAcquiredDay
     */
    public JComboBox getDateAcquiredDay() {
        return dateAcquiredDay;
    }

    /**
     * @param dateAcquiredDay the dateAcquiredDay to set
     */
    public void setDateAcquiredDay(JComboBox dateAcquiredDay) {
        this.dateAcquiredDay = dateAcquiredDay;
    }

    /**
     * @return the retentionPeriodFromYear
     */
    public JComboBox getRetentionPeriodFromYear() {
        return retentionPeriodFromYear;
    }

    /**
     * @param retentionPeriodFromYear the retentionPeriodFromYear to set
     */
    public void setRetentionPeriodFromYear(JComboBox retentionPeriodFromYear) {
        this.retentionPeriodFromYear = retentionPeriodFromYear;
    }

    /**
     * @return the retentionPeriodFromMonth
     */
    public JComboBox getRetentionPeriodFromMonth() {
        return retentionPeriodFromMonth;
    }

    /**
     * @param retentionPeriodFromMonth the retentionPeriodFromMonth to set
     */
    public void setRetentionPeriodFromMonth(JComboBox retentionPeriodFromMonth) {
        this.retentionPeriodFromMonth = retentionPeriodFromMonth;
    }

    /**
     * @return the retentionPeriodFromDay
     */
    public JComboBox getRetentionPeriodFromDay() {
        return retentionPeriodFromDay;
    }

    /**
     * @param retentionPeriodFromDay the retentionPeriodFromDay to set
     */
    public void setRetentionPeriodFromDay(JComboBox retentionPeriodFromDay) {
        this.retentionPeriodFromDay = retentionPeriodFromDay;
    }

    /**
     * @return the retentionPeriodToYear
     */
    public JComboBox getRetentionPeriodToYear() {
        return retentionPeriodToYear;
    }

    /**
     * @param retentionPeriodToYear the retentionPeriodToYear to set
     */
    public void setRetentionPeriodToYear(JComboBox retentionPeriodToYear) {
        this.retentionPeriodToYear = retentionPeriodToYear;
    }

    /**
     * @return the retentionPeriodToMonth
     */
    public JComboBox getRetentionPeriodToMonth() {
        return retentionPeriodToMonth;
    }

    /**
     * @param retentionPeriodToMonth the retentionPeriodToMonth to set
     */
    public void setRetentionPeriodToMonth(JComboBox retentionPeriodToMonth) {
        this.retentionPeriodToMonth = retentionPeriodToMonth;
    }

    /**
     * @return the retentionPeriodToDay
     */
    public JComboBox getRetentionPeriodToDay() {
        return retentionPeriodToDay;
    }

    /**
     * @param retentionPeriodToDay the retentionPeriodToDay to set
     */
    public void setRetentionPeriodToDay(JComboBox retentionPeriodToDay) {
        this.retentionPeriodToDay = retentionPeriodToDay;
    }

    /**
     * @return the maintenanceSched
     */
    public JComboBox getMaintenanceSched() {
        return maintenanceSched;
    }

    /**
     * @param maintenanceSched the maintenanceSched to set
     */
    public void setMaintenanceSched(JComboBox maintenanceSched) {
        this.maintenanceSched = maintenanceSched;
    }

    public void setEditAssetListener(MouseListener listener) {
        getEditasset().addMouseListener(listener);
    }

    /**
     * @return the editasset
     */
    public JLabel getEditasset() {
        return editasset;
    }

    /**
     * @param editasset the editasset to set
     */
    public void setEditasset(JLabel editasset) {
        this.editasset = editasset;
    }

    public void setDateAcquiredYearListener(ActionListener listener) {
        dateAcquiredYear.addActionListener(listener);
    }

    public void setDateAcquiredMonthListener(ActionListener listener) {
        dateAcquiredMonth.addActionListener(listener);
    }

    public void setDateAcquiredDayListener(ActionListener listener) {
        dateAcquiredDay.addActionListener(listener);
    }

    public void setDateRetentionYearListener(ActionListener listener) {
        dateAcquiredYear.addActionListener(listener);
    }

    public void setDateRetentionMonthListener(ActionListener listener) {
        dateAcquiredMonth.addActionListener(listener);
    }

    public void setDateRetentionDayListener(ActionListener listener) {
        dateAcquiredDay.addActionListener(listener);
    }

    public void setEditAssetBtnListener(MouseListener listener) {
        editasset.addMouseListener(listener);
    }
    
    public void setAssetComboBoxListener(ActionListener listener) {
        assetName.addActionListener(listener);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the details
     */
    public AssetModel getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(AssetModel details) {
        this.details = details;
    }

    /**
     * @param l the l to set
     */
    public void setL(Listener l) {
        this.l = l;
    }
}
