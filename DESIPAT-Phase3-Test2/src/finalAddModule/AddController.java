/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalAddModule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import DAO.AssetDAO;
import DAO.assetHistoryDAO;
import DAO.systemLogDAO;
import Model.AssetModel;

/**
 *
 * @author Vilson
 */
public class AddController {

    private AddView view;
    private AddModel model;
    private String username;

    public AddController(AddModel model, AddView view, String username) {
        this.view = view;
        this.model = model;
        this.username = username;

        view.setDateAcquiredYear(view.populateYear(view.getDateAcquiredYear(), Calendar.getInstance().get(Calendar.YEAR)));
        initListeners();
        
    }

    public void initListeners(){
        view.setListener(new btnAddListener());
        view.setAddAssetBtnListener(new btnAddListener());
        view.setDateAcquiredYearListener(new cbDateYearListener());
        view.setDateAcquiredMonthListener(new cbDateMonthListener());
        view.setDateAcquiredDayListener(new cbDateDayListener());
        view.setDateRetentionYearListener(new cbDateYearListener());
        view.setDateRetentionMonthListener(new cbDateMonthListener());
        view.setDateRetentionDayListener(new cbDateDayListener());
    }
    public class cbDateYearListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            view.populateDay(view.getDateAcquiredYear(), view.getDateAcquiredMonth(), view.getDateAcquiredDay());
        }
    }

    public class cbDateMonthListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            view.populateDay(view.getDateAcquiredYear(), view.getDateAcquiredMonth(), view.getDateAcquiredDay());
        }
    }

    public class cbDateDayListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.populateDay(view.getDateAcquiredYear(), view.getDateAcquiredMonth(), view.getRetentionPeriodFromDay());
            view.populateYear(view.getRetentionPeriodFromYear(), Integer.parseInt(view.getDateAcquiredYear().getSelectedItem().toString()));
            view.getRetentionPeriodFromYear().setSelectedItem(view.getDateAcquiredYear().getSelectedItem());
            view.getRetentionPeriodFromMonth().setSelectedItem(view.getDateAcquiredMonth().getSelectedItem());
            view.getRetentionPeriodFromDay().setSelectedItem(view.getDateAcquiredDay().getSelectedItem());

            view.getRetentionPeriodToYear().setEnabled(true);
            view.getRetentionPeriodToMonth().setEnabled(true);
            view.getRetentionPeriodToDay().setEnabled(true);


            view.getT().start();
//                populateYear(retentionPeriodToYear, Integer.parseInt(dateAcquiredYear.getSelectedItem().toString()));

        }
    }

    public class btnAddListener implements MouseListener {

        @Override
        public void mousePressed(MouseEvent e) {
        	if (e.getSource() == view.getAddasset()) {
        		System.out.println("i work!!!");
        	}

            if (e.getSource() == view.getAddasset() && view.blankCheck()) {

                try {
                    model.setDateAcquiredSQL(DateUtil.utilDateToSqlDate(model.getDateAcquiredSQL()));
                    model.setRetentionPeriodFromSQL(model.getDateAcquiredSQL());
                    model.setRetentionPeriodToSQL(DateUtil.utilDateToSqlDate(view.getRetentionDate()));

                } catch (ParseException ex) {
                    Logger.getLogger(AddView.class.getName()).log(Level.SEVERE, null, ex);
                }

                AssetModel.getInstance().setAssetname(view.getAssetName().getText());
                AssetModel.getInstance().setAssetowner(view.getAssetOwner().getText());
                AssetModel.getInstance().setAssetcustodian(view.getAssetCustodian().getText());
                AssetModel.getInstance().setType(view.getTypelist().getSelectedItem().toString());
                AssetModel.getInstance().setDateAcquired(model.getDateAcquiredSQL());
                AssetModel.getInstance().setRetentionPeriodFrom(model.getRetentionPeriodFromSQL());
                AssetModel.getInstance().setRetentionPeriodTo(model.getRetentionPeriodToSQL());
                AssetModel.getInstance().setSchedule(view.getMaintenanceSched().getSelectedItem().toString());
                AssetModel.getInstance().setFinancial(Integer.parseInt(view.getFinancial().getText()));
                AssetModel.getInstance().setConfidentiality(view.getConfidentialitylist().getSelectedItem().toString());
                AssetModel.getInstance().setIntegrity(view.getIntegritylist().getSelectedItem().toString());
                AssetModel.getInstance().setAvailability(view.getAvailabilitylist().getSelectedItem().toString());
                AssetModel.getInstance().setClassification(view.getClassificationlist().getSelectedItem().toString());
                AssetModel.getInstance().setStoragelocation(view.getStorageLocation().getText());

                try {
                    AssetDAO.getInstance().addAsset(AssetModel.getInstance());
                    systemLogDAO.getInstance().saveAccess("Added asset " + view.getAssetName().getText(), username);
                    assetHistoryDAO.getInstance().saveHistory(view.getAssetName().getText(), AssetDAO.getInstance().getAssetIdentifier(view.getAssetName().getText()), view.getAssetOwner().getText(), model.getDateAcquiredSQL());
                    //OwnerDAO.getInstance().saveOwner(assetOwner.getText(), AssetDAO.getInstance().getAssetIdentifier(assetName.getText()), assetName.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(AddView.class.getName()).log(Level.SEVERE, null, ex);
                }

                view.getAssetName().setText("");
                view.getAssetCustodian().setText("");
                view.getFinancial().setText("");
                view.getAssetOwner().setText("");
                view.getStorageLocation().setText("");

                view.getSuccessful().setText("The asset has been successfully added");
                view.getSuccessful().setBounds(555, 450, 500, 30);
                view.getSuccessful().setVisible(true);

            } else if (e.getSource() == view.getAssetName()) {
                view.getAssetName().setText("");
            } else if (e.getSource() == view.getAssetOwner()) {
                view.getAssetOwner().setText("");
            } else if (e.getSource() == view.getAssetCustodian()) {
                view.getAssetCustodian().setText("");
            } else if (e.getSource() == view.getFinancial()) {
                view.getFinancial().setText("");
            } else if (e.getSource() == view.getStorageLocation()) {
                view.getStorageLocation().setText("");


            }

            //System.out.println(ex.getMessage());


        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (e.getSource() == view.getAddasset()) {
                view.getAddasset().setIcon(view.getAddassetIcon()[1]);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.getSource() == view.getAddasset()) {
                view.getAddasset().setIcon(view.getAddassetIcon()[0]);
                view.getSuccessful().setVisible(false);
            }
        }

        @Override
        public void mouseClicked(MouseEvent me) {
        	
        }
    }
    
    /*
     * GET COMPONENTS
     */
    public AddView getAddView() {
    	return view;
    }
   
    public AddModel getAddModel() {
    	return model;
    }
}
