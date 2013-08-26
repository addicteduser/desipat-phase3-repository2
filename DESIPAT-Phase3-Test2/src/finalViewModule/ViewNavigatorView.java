package finalViewModule;

import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewNavigatorView extends JPanel {
	protected JLabel bg;
	public JLabel viewassethistory;
	public JLabel viewassetdetails;
	public JLabel viewsystemlog;

	protected ImageIcon[] viewassethistoryIcon;
	protected ImageIcon[] viewassetdetailsIcon;
	protected ImageIcon[] viewsystemlogIcon;

	public static String username;
	public static String usertype;

	private JPanel currentPanel;
	private JLabel currentButton;

	public ViewNavigatorView (String usertype, String username) {
		this.usertype = usertype;
		this.username = username;
		try {
			initialize();
			setBounds();
			setFrame();
			addToFrame();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}

	private void initialize() throws SQLException, ClassNotFoundException {

		viewassethistoryIcon = new ImageIcon[2];  
		viewassethistoryIcon[0] = new ImageIcon(this.getClass().getClassLoader().getResource("finalViewModule/viewassethistory.png"));
		viewassethistoryIcon[1] = new ImageIcon(this.getClass().getClassLoader().getResource("finalViewModule/viewassethistory.png"));

		viewassetdetailsIcon = new ImageIcon[2];
		viewassetdetailsIcon[0] = new ImageIcon(this.getClass().getClassLoader().getResource("finalViewModule/viewassetdetails.png"));
		viewassetdetailsIcon[1] = new ImageIcon(this.getClass().getClassLoader().getResource("finalViewModule/viewassetdetails.png"));

		viewsystemlogIcon = new ImageIcon[2];
		viewsystemlogIcon[0] = new ImageIcon(this.getClass().getClassLoader().getResource("finalViewModule/viewsystemlog.png"));
		viewsystemlogIcon[1] = new ImageIcon(this.getClass().getClassLoader().getResource("finalViewModule/viewsystemlog.png"));


		bg = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("finalViewModule/viewassetnavigator.png")));
		viewassethistory = new JLabel(viewassethistoryIcon[0]);
		viewassetdetails = new JLabel(viewassetdetailsIcon[0]);
		viewsystemlog = new JLabel(viewsystemlogIcon[0]);

		viewassethistory.setDisabledIcon(viewassethistoryIcon[1]);
		viewassetdetails.setDisabledIcon(viewassetdetailsIcon[1]);
		viewsystemlog.setDisabledIcon(viewsystemlogIcon[1]);
	}

	public boolean isAdmin(String usertype){
		if("Admin".equals(usertype))
			return true;
		return false;
	}

	private void setBounds()  {
		bg.setBounds(0, 0, bg.getIcon().getIconWidth(), bg.getIcon().getIconHeight());
		viewassethistory.setBounds(475, 23, viewassethistory.getIcon().getIconWidth(), viewassethistory.getIcon().getIconHeight());
		viewassetdetails.setBounds(275, 23, viewassetdetails.getIcon().getIconWidth(), viewassetdetails.getIcon().getIconHeight());
		viewsystemlog.setBounds(690, 23, viewsystemlog.getIcon().getIconWidth(), viewsystemlog.getIcon().getIconHeight());


	}

	private void setFrame() {
		this.setLayout(null);
		this.setVisible(true);
	}

	private void addToFrame() {
		if(!(isAdmin(usertype))){
			viewassethistory.setVisible(false);
			viewsystemlog.setVisible(false);


		}

		this.add(viewassetdetails);
		this.add(viewassethistory);

		this.add(viewsystemlog);

		this.add(bg);

	}

	public void setExitIcons(){
		viewassethistory.setIcon(viewassethistoryIcon[0]);
		viewassetdetails.setIcon(viewassetdetailsIcon[0]);
		viewsystemlog.setIcon(viewsystemlogIcon[0]);
	}

	public void changeCurrentPanel(JPanel panel, JLabel panelbutton) {
		if (this.currentPanel != null && this.currentButton != null) {
			this.remove(this.currentPanel);
			this.currentButton.setEnabled(true);
		}

		setExitIcons();
		this.currentPanel = panel;
		this.add(currentPanel);
		this.currentPanel.setBounds(0, bg.getIcon().getIconHeight(), panel.getWidth(), panel.getHeight());
		this.currentButton = panelbutton;
		this.setSize(890, 690);
	}
	
	public void addButtonListeners(MouseListener listener) {
		viewassethistory.addMouseListener(listener);
		viewassetdetails.addMouseListener(listener);
		viewsystemlog.addMouseListener(listener);
    }

	public JLabel getBg() {
		return bg;
	}

	public JLabel getViewassethistory() {
		return viewassethistory;
	}

	public JLabel getViewassetdetails() {
		return viewassetdetails;
	}

	public JLabel getViewsystemlog() {
		return viewsystemlog;
	}

	public ImageIcon[] getViewassethistoryIcon() {
		return viewassethistoryIcon;
	}

	public ImageIcon[] getViewassetdetailsIcon() {
		return viewassetdetailsIcon;
	}

	public ImageIcon[] getViewsystemlogIcon() {
		return viewsystemlogIcon;
	}

	public static String getUsername() {
		return username;
	}

	public static String getUsertype() {
		return usertype;
	}

	public JPanel getCurrentPanel() {
		return currentPanel;
	}

	public JLabel getCurrentButton() {
		return currentButton;
	}


}
