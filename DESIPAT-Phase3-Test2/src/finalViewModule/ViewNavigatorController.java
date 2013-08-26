package finalViewModule;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ViewNavigatorController {
	private ViewNavigatorView viewNavView;
	private String usertype;
	private String username;

	private ViewByAssetHistory byHistory;
	private ViewByAssetDetails byDetails;
	private ViewBySystemLog bySystemLog;

	public ViewNavigatorController(ViewNavigatorView view) {
		viewNavView = view;

		byHistory = new ViewByAssetHistory(viewNavView.getUsername());
		byHistory.createPanel();
		this.viewNavView.changeCurrentPanel(byHistory.getPanel(), viewNavView.getViewassethistory());

		this.viewNavView.addButtonListeners(new Listener());
	}

	public class Listener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getSource() == viewNavView.getViewassethistory()) {
				byHistory = new ViewByAssetHistory(viewNavView.getUsername());
				byHistory.createPanel();
				viewNavView.changeCurrentPanel(byHistory.getPanel(), viewNavView.getViewassethistory());
			} else if (e.getSource() == viewNavView.getViewassetdetails()) {
				byDetails = new ViewByAssetDetails(viewNavView.getUsername());
				byDetails.createPanel();
				viewNavView.changeCurrentPanel(byDetails.getPanel(), viewNavView.getViewassetdetails());
			} else if (e.getSource() == viewNavView.getViewsystemlog()) {
				bySystemLog = new ViewBySystemLog(viewNavView.getUsername());
				bySystemLog.createPanel();
				viewNavView.changeCurrentPanel(bySystemLog.getPanel(), viewNavView.getViewsystemlog());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == viewNavView.getViewassethistory()) {
				viewNavView.getViewassethistory().setIcon(viewNavView.getViewassethistoryIcon()[1]);
			} else if (e.getSource() == viewNavView.getViewassetdetails()) {
				viewNavView.getViewassetdetails().setIcon(viewNavView.getViewassetdetailsIcon()[1]);
			} else if (e.getSource() == viewNavView.getViewsystemlog()) {
				viewNavView.getViewsystemlog().setIcon(viewNavView.getViewsystemlogIcon()[1]);
			} 
		}
		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == viewNavView.getViewassethistory()) {
				viewNavView.getViewassethistory().setIcon(viewNavView.getViewassethistoryIcon()[0]);
			} else if (e.getSource() == viewNavView.getViewassetdetails()) {
				viewNavView.getViewassetdetails().setIcon(viewNavView.getViewassetdetailsIcon()[0]);
			} else if (e.getSource() == viewNavView.getViewsystemlog()) {
				viewNavView.getViewsystemlog().setIcon(viewNavView.getViewsystemlogIcon()[0]);
			} 
		}
	}

	/*
	 * GET COMPONENTS
	 */
	public ViewNavigatorView getViewNavView() {
		return viewNavView;
	}
}
