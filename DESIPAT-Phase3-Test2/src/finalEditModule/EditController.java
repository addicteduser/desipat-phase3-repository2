/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalEditModule;

import DAO.AssetDAO;
import DAO.assetHistoryDAO;
import DAO.systemLogDAO;
import Model.AssetModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/**
 *
 * @author Vilson
 */
public class EditController {

    private EditModel model;
    private EditView view;

    public EditController(EditModel model, EditView view, String username) {
        this.model = model;
        this.view = view;
        this.model.setUsername(username);
        initListeners();
    }
    
      public void initListeners() {
        view.setL(new Listener());
        view.setEditAssetBtnListener(new Listener());
        view.setDateAcquiredYearListener(new cbDateYearListener());
        view.setDateAcquiredMonthListener(new cbDateMonthListener());
        view.setDateAcquiredDayListener(new cbDateDayListener());
        view.setDateRetentionYearListener(new cbDateYearListener());
        view.setDateRetentionMonthListener(new cbDateMonthListener());
        view.setDateRetentionDayListener(new cbDateDayListener());
        view.setAssetComboBoxListener(new assetComboListener());
    }
    
    public void compareDetails() throws SQLException {
        if (!AssetModel.getInstance().getAssetowner().equals(view.getAssetOwner().getText())) {
            systemLogDAO.getInstance().saveAccess("Edited " + view.getAssetName().getSelectedItem().toString() + "'s owner", model.getUsername());
            assetHistoryDAO.getInstance().saveHistory(view.getAssetName().getSelectedItem().toString(), AssetDAO.getInstance().getAssetIdentifier(view.getAssetName().getSelectedItem().toString()), view.getAssetOwner().getText(), model.getDateAcquiredSQL());
            //OwnerDAO.getInstance().saveOwner(assetOwner.getText(), AssetDAO.getInstance().getAssetIdentifier(assetName.getSelectedItem().toString()), assetName.getSelectedItem().toString());
        }
        if (!AssetModel.getInstance().getAssetcustodian().equals(view.getAssetCustodian().getText())) {
            systemLogDAO.getInstance().saveAccess("Edited " + view.getAssetName().getSelectedItem().toString() + "'s custodian", model.getUsername());
        }
        if (!AssetModel.getInstance().getType().equals(view.getTypelist().getSelectedItem().toString())) {
            systemLogDAO.getInstance().saveAccess("Edited " + view.getAssetName().getSelectedItem().toString() + "'s type", model.getUsername());
        }
        if (!AssetModel.getInstance().getDateAcquired().equals(model.getDateAcquiredSQL())) {
            systemLogDAO.getInstance().saveAccess("Edited " + view.getAssetName().getSelectedItem().toString() + "'s acquire date", model.getUsername());
        }
        if (!AssetModel.getInstance().getRetentionPeriodFrom().equals(model.getDateAcquiredSQL())) {
            systemLogDAO.getInstance().saveAccess("Edited " + view.getAssetName().getSelectedItem().toString() + "'s retention start date", model.getUsername());
        }
        if (!AssetModel.getInstance().getRetentionPeriodTo().equals(model.getRetentionPeriodToSQL())) {
            systemLogDAO.getInstance().saveAccess("Edited " + view.getAssetName().getSelectedItem().toString() + "'s retention end date", model.getUsername());
        }
        if (!AssetModel.getInstance().getSchedule().equals(view.getMaintenanceSched().getSelectedItem().toString())) {
            systemLogDAO.getInstance().saveAccess("Edited " + view.getAssetName().getSelectedItem().toString() + "'s maintenance schedule", model.getUsername());
        }
        if (!(AssetModel.getInstance().getFinancial() == Integer.parseInt(view.getFinancial().getText()))) {
            systemLogDAO.getInstance().saveAccess("Edited " + view.getAssetName().getSelectedItem().toString() + "'s financial", model.getUsername());
        }
        if (!AssetModel.getInstance().getConfidentiality().equals(view.getConfidentialitylist().getSelectedItem().toString())) {
            systemLogDAO.getInstance().saveAccess("Edited " + view.getAssetName().getSelectedItem().toString() + "'s confidentiality", model.getUsername());
        }
        if (!AssetModel.getInstance().getIntegrity().equals(view.getIntegritylist().getSelectedItem().toString())) {
            systemLogDAO.getInstance().saveAccess("Edited " + view.getAssetName().getSelectedItem().toString() + "'s integrity", model.getUsername());
        }
        if (!AssetModel.getInstance().getAvailability().equals(view.getAvailabilitylist().getSelectedItem().toString())) {
            systemLogDAO.getInstance().saveAccess("Edited " + view.getAssetName().getSelectedItem().toString() + "'s availability", model.getUsername());
        }
        if (!AssetModel.getInstance().getClassification().equals(view.getClassificationlist().getSelectedItem().toString())) {
            systemLogDAO.getInstance().saveAccess("Edited " + view.getAssetName().getSelectedItem().toString() + "'s classification", model.getUsername());
        }
        if (!AssetModel.getInstance().getStoragelocation().equals(view.getStorageLocation().getText())) {
            systemLogDAO.getInstance().saveAccess("Edited " + view.getAssetName().getSelectedItem().toString() + "'s storage location", model.getUsername());
        }

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

    public class assetComboListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
                try {
                    AssetDAO.getInstance().getAssetDetails(view.getAssetName().getSelectedItem().toString());
                    view.populateDetails(view.getDetails());

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

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
        }
    }

    public class Listener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {

            if (e.getSource() == view.getEditasset() && view.blankCheck()) {

                model.setDateAcquiredSQL(model.parseDateAcquired(view.getDateAcquiredDay(), view.getDateAcquiredMonth(), view.getDateAcquiredYear()));
                model.setRetentionPeriodFromSQL(model.getDateAcquiredSQL());
                model.setRetentionPeriodToSQL(model.parseDateAcquired(view.getRetentionPeriodToDay(), view.getRetentionPeriodToMonth(), view.getRetentionPeriodToYear()));
                view.checkDate(model.getDateAcquiredSQL(), model.getRetentionPeriodToSQL());


                try {
                    compareDetails();
                    AssetModel.getInstance().setAssetname(view.getAssetName().getSelectedItem().toString());
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
                    AssetDAO.getInstance().editAsset(AssetModel.getInstance());


                } catch (SQLException ex) {
                    Logger.getLogger(EditView.class.getName()).log(Level.SEVERE, null, ex);
                }

                view.getSuccessful().setText("The asset has been successfully updated");
                view.getSuccessful().setBounds(535, 450, 500, 30);
                view.getSuccessful().setVisible(true);

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
            if (e.getSource() == view.getEditasset()) {
                view.getEditasset().setIcon(view.getEditassetIcon()[1]);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.getSource() == view.getEditasset()) {
                view.getEditasset().setIcon(view.getEditassetIcon()[0]);
                view.getSuccessful().setVisible(false);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
    }
    
    public EditView getEditView() {
    	return view;
    }
}
