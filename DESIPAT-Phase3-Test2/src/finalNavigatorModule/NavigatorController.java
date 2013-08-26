package finalNavigatorModule;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JLabel;

import dataAccessObjects.SystemLogDAO;
import finalAddModule.AddController;
import finalAddModule.AddModel;
import finalAddModule.AddView;
import finalDeleteModule.DeleteController;
import finalDeleteModule.DeleteModel;
import finalDeleteModule.DeleteView;
import finalEditModule.EditController;
import finalEditModule.EditModel;
import finalEditModule.EditView;
import finalLoginModule.LoginController;
import finalLoginModule.LoginModel;
import finalLoginModule.LoginView;
import finalViewModule.ViewNavigatorController;
import finalViewModule.ViewNavigatorView;

/**
 * CONTROLLER for Navigator
 */
public class NavigatorController {
	private NavigatorView navView;
	private NavigatorModel navModel;

	private AddController addCon;
	private DeleteController delCon;
	private EditController editCon;
	private ViewNavigatorController viewNavCon;

	public NavigatorController(NavigatorView view, NavigatorModel model) {
		this.navView = view;
		this.navModel = model;

		addCon = new AddController(new AddModel(), new AddView(), this.navModel.getUsername());
		this.navView.changeCurrentPanel(addCon.getAddView(), this.navView.getBtnAddAsset());

		this.navView.addButtonListeners(new Listener());
	}

	/*
	 * LISTENER
	 */

	/**
	 * Listener for the buttons
	 */
	public class Listener implements MouseListener {

		@Override
		public void mousePressed(MouseEvent e) {

			//implement changeCurrentPanel here
			if (e.getSource() == navView.getBtnAddAsset()) {
				addCon = new AddController(new AddModel(), new AddView(), navModel.getUsername());
				navView.changeCurrentPanel(addCon.getAddView(), navView.getBtnAddAsset());
			} else if (e.getSource() == navView.getBtnEditAsset()) {
				editCon = new EditController(new EditModel(), new EditView(), navModel.getUsername());
				navView.changeCurrentPanel(editCon.getEditView(), navView.getBtnEditAsset());
			} else if (e.getSource() == navView.getBtnDeleteAsset()) {
				delCon = new DeleteController(new DeleteView(navModel.getUsertype(), navModel.getUsername()), new DeleteModel());
				navView.changeCurrentPanel(delCon.getDeleteView(), navView.getBtnDeleteAsset());
			} else if (e.getSource() == navView.getBtnViewAsset()) {
				viewNavCon = new ViewNavigatorController(new ViewNavigatorView(navModel.getUsertype(), navModel.getUsername()));
				navView.changeCurrentPanel(viewNavCon.getViewNavView(), navView.getBtnViewAsset());
			} else if (e.getSource() == navView.getBtnLogout()){
				try {
					SystemLogDAO.getInstance().saveAccess("Logged out from the system", navModel.getUsername());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				navView.setVisible(false);
				new LoginController(new LoginView(), new LoginModel());
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == navView.getBtnAddAsset()) {
				navView.getBtnAddAsset().setIcon(navView.getAddAssetIcon()[1]);
			} else if (e.getSource() == navView.getBtnEditAsset()) {
				navView.getBtnEditAsset().setIcon(navView.getEditAssetIcon()[1]);
			} else if (e.getSource() == navView.getBtnDeleteAsset()) {
				navView.getBtnDeleteAsset().setIcon(navView.getDeleteAssetIcon()[1]);
			} else if (e.getSource() == navView.getBtnViewAsset()) {
				navView.getBtnViewAsset().setIcon(navView.getViewAssetIcon()[1]);
			} 
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == navView.getBtnAddAsset()) {
				navView.getBtnAddAsset().setIcon(navView.getAddAssetIcon()[0]);
			} else if (e.getSource() == navView.getBtnEditAsset()) {
				navView.getBtnEditAsset().setIcon(navView.getEditAssetIcon()[0]);
			} else if (e.getSource() == navView.getBtnDeleteAsset()) {
				navView.getBtnDeleteAsset().setIcon(navView.getDeleteAssetIcon()[0]);
			} else if (e.getSource() == navView.getBtnViewAsset()) {
				navView.getBtnViewAsset().setIcon(navView.getViewAssetIcon()[0]);
			} 
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}
	}
}
