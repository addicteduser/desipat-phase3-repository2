package finalDeleteModule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import dataAccessObjects.systemLogDAO;
import finalLoginModule.LoginView;

public class DeleteController {
	private DeleteView deleteView;
	private DeleteModel deleteModel;

	public DeleteController(DeleteView view, DeleteModel model) {
		this.deleteView = view;
		this.deleteModel = model;
		
		try {
			deleteModel.getAllAsset();
			deleteModel.getAllUserModel();
			
			updateAssetsList();
			updateUsersList();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Database is empty!");
		}
		
        this.deleteView.addBtnDelAssetListener(new DeleteAssetListener());
		this.deleteView.addBtnDelUserListener(new DeleteUserListener());
	}

	public class DeleteAssetListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				delete(0);
			} catch (SQLException ex) {
				Logger.getLogger(DeleteView.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
	}

	public class DeleteUserListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				delete(1);
			} catch (SQLException ex) {
				Logger.getLogger(DeleteView.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
	}

	// DELETE
	public void updateAssetsList() {
		// Add Asset Names in ComboBox
		for (int i = 0; i < deleteModel.assetName.size(); i++) {
			deleteView.getAssetList().addItem(deleteModel.assetName.get(i));
		}
	}

	public void updateUsersList() throws SQLException {
		// Add all Usernames in ComboBox
		for (int i = 0; i < deleteModel.usermdl.size(); i++)
			deleteView.getUserList().addItem(deleteModel.usermdl.get(i).getUsername());
	}

	public void delete(int flag) throws SQLException {

		// delete assets
		if (flag == 0) {
			String selectedItem = "";
			selectedItem = deleteView.getAssetList().getSelectedItem()
					.toString();

			deleteModel.adao.deleteAsset(selectedItem);
			deleteView.getAssetList().removeAllItems();
			
			deleteModel.getAllAsset();

			updateAssetsList();
			systemLogDAO.getInstance().saveAccess(
					"Deleted asset " + selectedItem,
					LoginView.getTxtUsername().getText());

		}

		// delete users
		else {
			String selectedItem = "";

			selectedItem = deleteView.getUserList().getSelectedItem()
					.toString();

			if (deleteView.getName().equals(selectedItem)) {
				JOptionPane.showMessageDialog(null,
						"You are not allowed to delete your own account.");
			}

			else
				deleteModel.udao.deleteUser(selectedItem);
			deleteView.getUserList().removeAllItems();
			
			deleteModel.getAllUserModel();
			
			updateUsersList();
			systemLogDAO.getInstance().saveAccess(
					"Deleted user " + selectedItem,
					LoginView.getTxtUsername().getText());

		}
	}
	
	/*
	 * GET COMPONENTS
	 */
	public DeleteView getDeleteView() {
		return deleteView;
	}
	
	public DeleteModel getDeleteModel() {
		return deleteModel;
	}
}
