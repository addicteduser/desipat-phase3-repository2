/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalAddModule;

import DAO.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

import javax.swing.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Timer;

import abstractPanel.GUI;

/**
 *
 * @author JT
 */
public class AddView extends GUI implements ActionListener {

    protected JLabel bg;
    private JLabel addasset;
    private JLabel successful;
    private ImageIcon[] addassetIcon;
    protected String assetType;
    private JTextField assetName;
    private JTextField assetOwner;
    private JTextField assetCustodian;
    private numericTextfield financial;
    private JTextField storageLocation;
    MouseListener l;
    private Timer t = new Timer(100, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            populateYear(
                    getRetentionPeriodToYear(),
                    Integer.parseInt(getDateAcquiredYear().getSelectedItem().toString()));
            t.stop();
        }
    });
    private Connection connect;
    private JComboBox typelist = new JComboBox();
    private JComboBox classificationlist = new JComboBox();
    private JComboBox confidentialitylist = new JComboBox();
    private JComboBox integritylist = new JComboBox();
    private JComboBox availabilitylist = new JComboBox();
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
    private Date dateAcquired = null;
    private Date retentionDate = null;
    private Calendar calendar = Calendar.getInstance();

    public AddView() {
        makePanel();
    }
    
    
    // ok 

    public JComboBox populateYear(JComboBox year, int startYear) {
        year.setModel(new DefaultComboBoxModel());
        for (int i = startYear; i <= 2030; i++) {
            year.addItem(i);
        }
        return year;
    }
    // ok

    public void populateMonth() {
        ResourceBundle rb = ResourceBundle.getBundle("months");
        Enumeration<String> settings = rb.getKeys();
        for (int i = 1; i <= 12; i++) {
            getDateAcquiredMonth().addItem(rb.getString(Integer.toString(i)));
            getRetentionPeriodFromMonth().addItem(rb.getString(Integer.toString(i)));
            getRetentionPeriodToMonth().addItem(rb.getString(Integer.toString(i)));
        }
    }
    // ok

    public void populateDay(JComboBox year, JComboBox month, JComboBox day) {
        getCalendar().set(Integer.parseInt(year.getSelectedItem().toString()), month.getSelectedIndex(), 1);

        day.setModel(new DefaultComboBoxModel());

        for (int i = 1; i <= getCalendar().getActualMaximum(getCalendar().DAY_OF_MONTH); i++) {
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

    public void parseDate() {
        String stringDate1;
        String stringDate2;

        //stringDate1 = dateAcquiredMonth.getSelectedItem().toString() + " " +dateAcquiredDay.getSelectedItem().toString() + ", " + dateAcquiredYear.getSelectedItem().toString();
        stringDate1 = getDateAcquiredDay().getSelectedItem().toString() + "-" + Integer.toString(getDateAcquiredMonth().getSelectedIndex() + 1) + "-" + getDateAcquiredYear().getSelectedItem().toString();
        //stringDate2 = retentionPeriodToMonth.getSelectedItem().toString() + " " +retentionPeriodToDay.getSelectedItem().toString() + ", " + retentionPeriodToYear.getSelectedItem().toString();
        stringDate2 = getRetentionPeriodToDay().getSelectedItem().toString() + "-" + Integer.toString(getRetentionPeriodToMonth().getSelectedIndex() + 1) + "-" + getRetentionPeriodToYear().getSelectedItem().toString();

        try {

            setDateAcquired(new SimpleDateFormat("dd-MM-yyyy").parse(stringDate1));
            setRetentionDate(new SimpleDateFormat("dd-MM-yyyy").parse(stringDate2));


        } catch (ParseException ex) {
            Logger.getLogger(AddView.class.getName()).log(Level.SEVERE, null, ex);

        }
        checkDate(getDateAcquired(), getRetentionDate());

    }

    @Override
    public void initialize() throws ClassNotFoundException, SQLException {

        financial = new numericTextfield();

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
        populateMonth();
        populateDay(getDateAcquiredYear(),getDateAcquiredMonth(),getDateAcquiredDay());

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


        getRetentionPeriodToYear().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                populateDay(getRetentionPeriodToYear(), getRetentionPeriodToMonth(), getRetentionPeriodToDay());
                parseDate();
            }
        });


        getRetentionPeriodToMonth().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                populateDay(getRetentionPeriodToYear(), getRetentionPeriodToMonth(), getRetentionPeriodToDay());
                parseDate();
            }
        });

        getRetentionPeriodToDay().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                parseDate();

            }
        });

        setAddassetIcon(new ImageIcon[2]);
        getAddassetIcon()[0] = new ImageIcon(this.getClass().getClassLoader().getResource("pictures/addasset.png"));
        getAddassetIcon()[1] = new ImageIcon(this.getClass().getClassLoader().getResource("pictures/addasset.png"));

        bg = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("pictures/addassetbg.png")));

        addasset = new JLabel(getAddassetIcon()[0]);
        successful = new JLabel("The asset has been successfully added!");

        getSuccessful().setForeground(Color.WHITE);
        getSuccessful().setFont(new Font("calibri", Font.ITALIC, 20));
        getSuccessful().setVisible(false);

        assetName = new JTextField();
        assetOwner = new JTextField();
        assetCustodian = new JTextField();
        storageLocation = new JTextField();
        //    financial = new JTextField();

        //Class.forName("com.mysql.jdbc.Driver");
        //connect = login.connect;

        getAddasset().addMouseListener(l);
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
        getAddasset().setBounds(720, 477, getAddasset().getIcon().getIconWidth(), getAddasset().getIcon().getIconHeight());
        getTypelist().setBounds(300, 95, 110, 30);
        getClassificationlist().setBounds(300, 430, 110, 30);
        getConfidentialitylist().setBounds(300, 300, 110, 30);
        getIntegritylist().setBounds(300, 343, 110, 30);
        getAvailabilitylist().setBounds(300, 383, 110, 30);
        getSuccessful().setBounds(555, 450, 500, 30);

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
        this.add(getAddasset());
        this.add(getTypelist());
        this.add(getDateAcquiredMonth());
        this.add(getDateAcquiredDay());
        this.add(getDateAcquiredYear());
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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // Okay na

    public boolean blankCheck() {

        int i = 0;

        if (getAssetName().getText().equals("")) {
            getAssetName().setBackground(Color.getHSBColor(353, 0.58f, 0.92f));
            i++;
        } else {
            getAssetName().setBackground(Color.white);
        }

        if (getAssetCustodian().getText().equals("")) {
            getAssetCustodian().setBackground(Color.getHSBColor(353, 0.58f, 0.92f));
            i++;
        } else {
            getAssetCustodian().setBackground(Color.white);
        }

        if (getFinancial().getText().equals("")) {
            getFinancial().setBackground(Color.getHSBColor(353, 0.58f, 0.92f));
            i++;
        } else {
            getFinancial().setBackground(Color.white);
        }

        if (getStorageLocation().getText().equals("")) {
            getStorageLocation().setBackground(Color.getHSBColor(353, 0.58f, 0.92f));
            i++;
        } else {
            getStorageLocation().setBackground(Color.white);
        }

        if (getAssetOwner().getText().equals("")) {
            getAssetOwner().setBackground(Color.getHSBColor(353, 0.58f, 0.92f));
            i++;
        } else {
            getAssetOwner().setBackground(Color.white);
        }
        if (getDateAcquired() == null && getRetentionDate() == null) {
            i++;
            getSuccessful().setText("Please enter date acquired and retention period.");
            getSuccessful().setBounds(460, 450, 500, 30);
            getSuccessful().setVisible(true);
        }

        if (getRetentionPeriodToDay().getBackground().equals(Color.getHSBColor(353, 0.58f, 0.92f))
                && getRetentionPeriodToMonth().getBackground().equals(Color.getHSBColor(353, 0.58f, 0.92f))
                && getRetentionPeriodToYear().getBackground().equals(Color.getHSBColor(353, 0.58f, 0.92f))) {
            i++;
            getSuccessful().setText("Invalid Retention Period.");
            getSuccessful().setBounds(660, 450, 500, 30);
            getSuccessful().setVisible(true);
        }


        if (i == 0) {
            return true;
        }

        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
    }

    public void setListener(MouseListener listener) {
        l = listener;
    }

    /**
     * @return the assetName
     */
    public JTextField getAssetName() {
        return assetName;
    }

    /**
     * @return the assetOwner
     */
    public JTextField getAssetOwner() {
        return assetOwner;
    }

    /**
     * @return the assetCustodian
     */
    public JTextField getAssetCustodian() {
        return assetCustodian;
    }

    /**
     * @return the financial
     */
    public numericTextfield getFinancial() {
        return financial;
    }

    /**
     * @return the storageLocation
     */
    public JTextField getStorageLocation() {
        return storageLocation;
    }

    /**
     * @return the connect
     */
    public Connection getConnect() {
        return connect;
    }

    /**
     * @return the typelist
     */
    public JComboBox getTypelist() {
        return typelist;
    }

    /**
     * @return the classificationlist
     */
    public JComboBox getClassificationlist() {
        return classificationlist;
    }

    /**
     * @return the confidentialitylist
     */
    public JComboBox getConfidentialitylist() {
        return confidentialitylist;
    }

    /**
     * @return the integritylist
     */
    public JComboBox getIntegritylist() {
        return integritylist;
    }

    /**
     * @return the availabilitylist
     */
    public JComboBox getAvailabilitylist() {
        return availabilitylist;
    }

    /**
     * @return the dateAcquiredYear
     */
    public JComboBox getDateAcquiredYear() {
        return dateAcquiredYear;
    }

    /**
     * @return the dateAcquiredMonth
     */
    public JComboBox getDateAcquiredMonth() {
        return dateAcquiredMonth;
    }

    /**
     * @return the dateAcquiredDay
     */
    public JComboBox getDateAcquiredDay() {
        return dateAcquiredDay;
    }

    /**
     * @return the retentionPeriodFromYear
     */
    public JComboBox getRetentionPeriodFromYear() {
        return retentionPeriodFromYear;
    }

    /**
     * @return the retentionPeriodFromMonth
     */
    public JComboBox getRetentionPeriodFromMonth() {
        return retentionPeriodFromMonth;
    }

    /**
     * @return the retentionPeriodFromDay
     */
    public JComboBox getRetentionPeriodFromDay() {
        return retentionPeriodFromDay;
    }

    /**
     * @return the retentionPeriodToYear
     */
    public JComboBox getRetentionPeriodToYear() {
        return retentionPeriodToYear;
    }

    /**
     * @return the retentionPeriodToMonth
     */
    public JComboBox getRetentionPeriodToMonth() {
        return retentionPeriodToMonth;
    }

    /**
     * @return the retentionPeriodToDay
     */
    public JComboBox getRetentionPeriodToDay() {
        return retentionPeriodToDay;
    }

    /**
     * @return the maintenanceSched
     */
    public JComboBox getMaintenanceSched() {
        return maintenanceSched;
    }

    /**
     * @return the dateAcquired
     */
    public Date getDateAcquired() {
        return dateAcquired;
    }

    /**
     * @return the retentionDate
     */
    public Date getRetentionDate() {
        return retentionDate;
    }

    /**
     * @return the calendar
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * @return the successful
     */
    public JLabel getSuccessful() {
        return successful;
    }

    /**
     * @return the addassetIcon
     */
    public ImageIcon[] getAddassetIcon() {
        return addassetIcon;
    }

    /**
     * @param addassetIcon the addassetIcon to set
     */
    public void setAddassetIcon(ImageIcon[] addassetIcon) {
        this.addassetIcon = addassetIcon;
    }

    /**
     * @return the addasset
     */
    public JLabel getAddasset() {
        return addasset;
    }

    /**
     * @param dateAcquired the dateAcquired to set
     */
    public void setDateAcquired(Date dateAcquired) {
        this.dateAcquired = dateAcquired;
    }

    /**
     * @param retentionDate the retentionDate to set
     */
    public void setRetentionDate(Date retentionDate) {
        this.retentionDate = retentionDate;
    }

    /**
     * @param dateAcquiredYear the dateAcquiredYear to set
     */
    public void setDateAcquiredYear(JComboBox dateAcquiredYear) {
        this.dateAcquiredYear = dateAcquiredYear;
    }

    /**
     * @param dateAcquiredMonth the dateAcquiredMonth to set
     */
    public void setDateAcquiredMonth(JComboBox dateAcquiredMonth) {
        this.dateAcquiredMonth = dateAcquiredMonth;
    }

    /**
     * @param dateAcquiredDay the dateAcquiredDay to set
     */
    public void setDateAcquiredDay(JComboBox dateAcquiredDay) {
        this.dateAcquiredDay = dateAcquiredDay;
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
    
    public void setAddAssetBtnListener(MouseListener listener){
        addasset.addMouseListener(listener);
    }

    /**
     * @return the t
     */
    public Timer getT() {
        return t;
    }
}
