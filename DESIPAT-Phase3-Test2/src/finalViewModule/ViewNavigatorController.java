package finalViewModule;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import finalAddModule.AddController;
import finalAddModule.AddModel;
import finalAddModule.AddView;
import finalNavigatorModule.NavigatorController.Listener;

public class ViewNavigatorController {
	private ViewNavigatorView viewNavView;
	private String usertype;
	private String username;
	
	public ViewNavigatorController(ViewNavigatorView view) {
		viewNavView = view;
		
		//addCon = new AddController(new AddModel(), new AddView(), this.navModel.getUsername());
		//this.navView.changeCurrentPanel(addCon.getAddView(), this.navView.getBtnAddAsset());
		
		this.viewNavView.addButtonListeners(new Listener());
	}
	
	public class Listener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
           
        }

        @Override
        public void mousePressed(MouseEvent e) {
        	// change panel
            /*if (e.getSource() == viewassethistory) {
                  assethistory = new viewByAssetHistory();
                   assethistory.View();
                change( assethistory.getPanel(), viewassethistory);

            } else if (e.getSource() == viewassetdetails) {
                 assetdetails = new viewByAssetDetails();
                   assetdetails.View();
                change( assetdetails.getPanel(), viewassetdetails);
                
                
            } else if (e.getSource() == viewsystemlog) {
                systemlog = new viewBySystemLog();
                systemlog.View();
                change( systemlog.getPanel(), viewsystemlog);
            } */
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
}
