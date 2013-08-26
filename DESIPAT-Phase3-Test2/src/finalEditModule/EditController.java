package finalEditModule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;

import dataAccessObjects.AssetDAO;
import dataAccessObjects.SystemLogDAO;
import dataAccessObjects.assetHistoryDAO;
import dataModel.AssetModel;

import java.awt.Color;

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
		getView().setL(new Listener());
		getView().setEditAssetBtnListener(new Listener());
		getView().setDateAcquiredYearListener(new cbDateYearListener());
		getView().setDateAcquiredMonthListener(new cbDateMonthListener());
		getView().setDateAcquiredDayListener(new cbDateDayListener());
		getView().setDateRetentionYearListener(new cbDateYearListener());
		getView().setDateRetentionMonthListener(new cbDateMonthListener());
		getView().setDateRetentionDayListener(new cbDateDayListener());
		getView().setAssetComboBoxListener(new assetComboListener());
	}

	public void compareDetails() throws SQLException {
		if (!AssetModel.getInstance().getAssetowner().equals(view.getAssetOwner().getText())) {
			SystemLogDAO.getInstance().saveAccess("Edited " + getView().getAssetName().getSelectedItem().toString() + "'s owner", getModel().getUsername());
			assetHistoryDAO.getInstance().saveHistory(getView().getAssetName().getSelectedItem().toString(), AssetDAO.getInstance().getAssetIdentifier(getView().getAssetName().getSelectedItem().toString()), getView().getAssetOwner().getText(), getModel().getDateAcquiredSQL());
		}
		if (!AssetModel.getInstance().getAssetcustodian().equals(view.getAssetCustodian().getText())) {
			SystemLogDAO.getInstance().saveAccess("Edited " + getView().getAssetName().getSelectedItem().toString() + "'s custodian", getModel().getUsername());
		}
		if (!AssetModel.getInstance().getType().equals(view.getTypelist().getSelectedItem().toString())) {
			SystemLogDAO.getInstance().saveAccess("Edited " + getView().getAssetName().getSelectedItem().toString() + "'s type", getModel().getUsername());
		}
		if (!AssetModel.getInstance().getDateAcquired().equals(model.getDateAcquiredSQL())) {
			SystemLogDAO.getInstance().saveAccess("Edited " + getView().getAssetName().getSelectedItem().toString() + "'s acquire date", getModel().getUsername());
		}
		if (!AssetModel.getInstance().getRetentionPeriodFrom().equals(model.getDateAcquiredSQL())) {
			SystemLogDAO.getInstance().saveAccess("Edited " + getView().getAssetName().getSelectedItem().toString() + "'s retention start date", getModel().getUsername());
		}
		if (!AssetModel.getInstance().getRetentionPeriodTo().equals(model.getRetentionPeriodToSQL())) {
			SystemLogDAO.getInstance().saveAccess("Edited " + getView().getAssetName().getSelectedItem().toString() + "'s retention end date", getModel().getUsername());
		}
		if (!AssetModel.getInstance().getSchedule().equals(view.getMaintenanceSched().getSelectedItem().toString())) {
			SystemLogDAO.getInstance().saveAccess("Edited " + getView().getAssetName().getSelectedItem().toString() + "'s maintenance schedule", getModel().getUsername());
		}
		if (!(AssetModel.getInstance().getFinancial() == Integer.parseInt(view.getFinancial().getText()))) {
			SystemLogDAO.getInstance().saveAccess("Edited " + getView().getAssetName().getSelectedItem().toString() + "'s financial", getModel().getUsername());
		}
		if (!AssetModel.getInstance().getConfidentiality().equals(view.getConfidentialitylist().getSelectedItem().toString())) {
			SystemLogDAO.getInstance().saveAccess("Edited " + getView().getAssetName().getSelectedItem().toString() + "'s confidentiality", getModel().getUsername());
		}
		if (!AssetModel.getInstance().getIntegrity().equals(view.getIntegritylist().getSelectedItem().toString())) {
			SystemLogDAO.getInstance().saveAccess("Edited " + getView().getAssetName().getSelectedItem().toString() + "'s integrity", getModel().getUsername());
		}
		if (!AssetModel.getInstance().getAvailability().equals(view.getAvailabilitylist().getSelectedItem().toString())) {
			SystemLogDAO.getInstance().saveAccess("Edited " + getView().getAssetName().getSelectedItem().toString() + "'s availability", getModel().getUsername());
		}
		if (!AssetModel.getInstance().getClassification().equals(view.getClassificationlist().getSelectedItem().toString())) {
			SystemLogDAO.getInstance().saveAccess("Edited " + getView().getAssetName().getSelectedItem().toString() + "'s classification", getModel().getUsername());
		}
		if (!AssetModel.getInstance().getStoragelocation().equals(view.getStorageLocation().getText())) {
			SystemLogDAO.getInstance().saveAccess("Edited " + getView().getAssetName().getSelectedItem().toString() + "'s storage location", getModel().getUsername());
		}

	}

	/**
	 * @return the model
	 */
	public EditModel getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(EditModel model) {
		this.model = model;
	}

	/**
	 * @return the view
	 */
	public EditView getView() {
		return view;
	}

	/**
	 * @param view the view to set
	 */
	public void setView(EditView view) {
		this.view = view;
	}

	public class cbDateYearListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			getView().populateDay(getView().getDateAcquiredYear(), getView().getDateAcquiredMonth(), getView().getDateAcquiredDay());
		}
	}

	public class cbDateMonthListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			getView().populateDay(getView().getDateAcquiredYear(), getView().getDateAcquiredMonth(), getView().getDateAcquiredDay());
		}
	}

	public class assetComboListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				AssetDAO.getInstance().getAssetDetails(getView().getAssetName().getSelectedItem().toString());
				getView().populateDetails(getView().getDetails());

			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		}
	}

	public class cbDateDayListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.populateDay(getView().getDateAcquiredYear(), getView().getDateAcquiredMonth(), getView().getRetentionPeriodFromDay());
			view.populateYear(getView().getRetentionPeriodFromYear(), Integer.parseInt(getView().getDateAcquiredYear().getSelectedItem().toString()));
			view.getRetentionPeriodFromYear().setSelectedItem(getView().getDateAcquiredYear().getSelectedItem());
			view.getRetentionPeriodFromMonth().setSelectedItem(getView().getDateAcquiredMonth().getSelectedItem());
			view.getRetentionPeriodFromDay().setSelectedItem(getView().getDateAcquiredDay().getSelectedItem());
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

			if (e.getSource() == getView().getEditasset()) {

				model.setDateAcquiredSQL(model.parseDateAcquired(view.getDateAcquiredDay(), view.getDateAcquiredMonth(), view.getDateAcquiredYear()));
				model.setRetentionPeriodFromSQL(model.getDateAcquiredSQL());
				model.setRetentionPeriodToSQL(model.parseDateAcquired(view.getRetentionPeriodToDay(), view.getRetentionPeriodToMonth(), view.getRetentionPeriodToYear()));
				System.out.println(model.getDateAcquiredSQL());
				System.out.println(model.getRetentionPeriodToSQL());
				view.checkDate(model.getDateAcquiredSQL(), model.getRetentionPeriodToSQL());

				if (blankCheck()) {
					try {
						System.out.println(model.getDateAcquiredSQL());
						System.out.println(model.getRetentionPeriodToSQL());
						compareDetails();
						AssetModel.getInstance().setAssetname(view.getAssetName().getSelectedItem().toString());
						AssetModel.getInstance().setAssetowner(view.getAssetOwner().getText());
						AssetModel.getInstance().setAssetcustodian(getView().getAssetCustodian().getText());
						AssetModel.getInstance().setType(getView().getTypelist().getSelectedItem().toString());
						AssetModel.getInstance().setDateAcquired(getModel().getDateAcquiredSQL());
						AssetModel.getInstance().setRetentionPeriodFrom(getModel().getRetentionPeriodFromSQL());
						AssetModel.getInstance().setRetentionPeriodTo(getModel().getRetentionPeriodToSQL());
						AssetModel.getInstance().setSchedule(getView().getMaintenanceSched().getSelectedItem().toString());
						AssetModel.getInstance().setFinancial(Integer.parseInt(getView().getFinancial().getText()));
						AssetModel.getInstance().setConfidentiality(getView().getConfidentialitylist().getSelectedItem().toString());
						AssetModel.getInstance().setIntegrity(getView().getIntegritylist().getSelectedItem().toString());
						AssetModel.getInstance().setAvailability(getView().getAvailabilitylist().getSelectedItem().toString());
						AssetModel.getInstance().setClassification(getView().getClassificationlist().getSelectedItem().toString());
						AssetModel.getInstance().setStoragelocation(getView().getStorageLocation().getText());
						AssetDAO.getInstance().editAsset(AssetModel.getInstance());


					} catch (SQLException ex) {
						Logger.getLogger(EditView.class.getName()).log(Level.SEVERE, null, ex);
					}

					view.getSuccessful().setText("The asset has been successfully updated");
					view.getSuccessful().setBounds(535, 450, 500, 30);
					view.getSuccessful().setVisible(true);
				}
			} else if (e.getSource() == getView().getAssetOwner()) {
				getView().getAssetOwner().setText("");
			} else if (e.getSource() == getView().getAssetCustodian()) {
				getView().getAssetCustodian().setText("");
			} else if (e.getSource() == getView().getFinancial()) {
				getView().getFinancial().setText("");
			} else if (e.getSource() == getView().getStorageLocation()) {
				getView().getStorageLocation().setText("");
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == getView().getEditasset()) {
				view.getEditasset().setIcon(getView().getEditassetIcon()[1]);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == getView().getEditasset()) {
				view.getEditasset().setIcon(getView().getEditassetIcon()[0]);
				view.getSuccessful().setVisible(false);
			}
		}
	}

	public boolean blankCheck() {

		int i = 0;

		if (view.getAssetCustodian().getText().equals("")) {
			view.getAssetCustodian().setBackground(Color.getHSBColor(353, 0.58f, 0.92f));
			i++;
		} else {
			view.getAssetCustodian().setBackground(Color.white);
		}

		if (view.getFinancial().getText().equals("")) {
			view.getFinancial().setBackground(Color.getHSBColor(353, 0.58f, 0.92f));
			i++;
		} else {
			view.getFinancial().setBackground(Color.white);
		}

		if (view.getStorageLocation().getText().equals("")) {
			view.getStorageLocation().setBackground(Color.getHSBColor(353, 0.58f, 0.92f));
			i++;
		} else {
			view.getStorageLocation().setBackground(Color.white);
		}

		if (view.getAssetOwner().getText().equals("")) {
			view.getAssetOwner().setBackground(Color.getHSBColor(353, 0.58f, 0.92f));
			i++;
		} else {
			view.getAssetOwner().setBackground(Color.white);
		}
		if (model.getDateAcquiredSQL() == null && model.getRetentionPeriodToSQL() == null) {
			i++;
			view.getSuccessful().setText("Please enter date acquired and retention period.");
			view.getSuccessful().setBounds(460, 450, 500, 30);
			view.getSuccessful().setVisible(true);
		}

		if (view.getRetentionPeriodToDay().getBackground().equals(Color.getHSBColor(353, 0.58f, 0.92f))
				&& view.getRetentionPeriodToMonth().getBackground().equals(Color.getHSBColor(353, 0.58f, 0.92f))
				&& view.getRetentionPeriodToYear().getBackground().equals(Color.getHSBColor(353, 0.58f, 0.92f))) {
			i++;
			view.getSuccessful().setText("Invalid Retention Period.");
			view.getSuccessful().setBounds(660, 450, 500, 30);
			view.getSuccessful().setVisible(true);
		}

		if (i == 0) {
			return true;
		}

		return false;
	}

	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox) e.getSource();
	}

	public EditView getEditView() {
		return view;
	}
}
