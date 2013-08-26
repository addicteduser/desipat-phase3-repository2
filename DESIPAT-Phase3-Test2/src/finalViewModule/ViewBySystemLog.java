package finalViewModule;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import dataAccessObjects.*;
import dataModel.*;
import finalUniveral.AbstractViewNavPanel;
/**
 *
 * @author Aram
 */
public class ViewBySystemLog extends AbstractViewNavPanel implements ActionListener{

	protected JLabel bg;
	protected JLabel systemlogBG;    
	protected JLabel viewlog;

	protected JLabel[] access;
	protected JLabel[] username;
	protected JLabel[] date;
	protected JLabel next;
	protected JLabel back;

	protected ImageIcon nextIcon;
	protected ImageIcon backIcon;

	protected JLabel viewsystemlog;
	protected int count;
	protected int current;

	protected Font itemsFont;

	protected Connection connect;

	protected ImageIcon[] viewAssetIcon;
	protected ViewBySystemLog.Listener l;
	protected JPanel panel;
	protected JLabel panelbutton;

	JPanel Panel = new JPanel();

	ArrayList<AssetModel> amdls;
	ArrayList<AssetModel> amdl;

	ArrayList<SystemLogModel> slmdl;
	UserModel umdl;


	JComboBox datelist = new JComboBox();

	public JPanel getPanel(){
		return this.Panel;
	}

	public void setPanel(JPanel Panel){
		this.Panel = Panel;
	}

	private String user;

	public ViewBySystemLog(String username) {
		super();
		this.user = username;
	}

	@Override
	public void initialize() {
		nextIcon = new ImageIcon(this.getClass().getClassLoader().getResource("finalViewModule/next.png"));
		backIcon = new ImageIcon(this.getClass().getClassLoader().getResource("finalViewModule/back.png"));
		// ADD dates in combox
		try {
			ArrayList<SystemLogModel> slmdl = SystemLogDAO.getInstance().getAllDates();
			for (int i = 0; i < slmdl.size(); i++) {
				datelist.addItem(slmdl.get(i).getDate());
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}


		bg = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("finalViewModule/systemlogbg.jpg")));
		systemlogBG = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("finalViewModule/systemlogbg.jpg")));
		viewlog = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("finalViewModule/view.png")));
		viewsystemlog = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("finalViewModule/viewsystemlog.png")));
		try {
			count = getDateCount();
		} catch (SQLException ex) {
			Logger.getLogger(ViewBySystemLog.class.getName()).log(Level.SEVERE, null, ex);
		}

		datelist.setSelectedIndex(0);
		datelist.addActionListener(this);

		next = new JLabel(nextIcon);
		back = new JLabel(backIcon);

		systemlogBG.setVisible(true);
		datelist.setVisible(true);
		viewlog.setVisible(true);

		itemsFont = new Font("Calibri", Font.BOLD, 20);

		l = new ViewBySystemLog.Listener();

		viewsystemlog.addMouseListener(l);
		datelist.addMouseListener(l);
		viewlog.addMouseListener(l);
		next.addMouseListener(l);
		back.addMouseListener(l);
	}

	@Override
	public void setBounds() {
		bg.setBounds(0, 0, bg.getIcon().getIconWidth(), bg.getIcon().getIconHeight());
		viewsystemlog.setBounds(690, 23, viewsystemlog.getIcon().getIconWidth(), viewsystemlog.getIcon().getIconHeight());
		next.setBounds(700, 350, next.getIcon().getIconWidth(), next.getIcon().getIconHeight());
		back.setBounds(650, 350, back.getIcon().getIconWidth(), back.getIcon().getIconHeight());
		datelist.setBounds(400, 24, 150, 30);
		viewlog.setBounds(570, 30,viewlog.getIcon().getIconWidth(), viewlog.getIcon().getIconHeight());
	}

	@Override
	public void setFrame() {
		Panel.setSize(bg.getIcon().getIconWidth(), bg.getIcon().getIconHeight());
		Panel.setLayout(null);
		Panel.setVisible(true);
	}

	@Override
	public void addToFrame() {
		Panel.add(back);
		Panel.add(next);
		Panel.add(datelist);

		Panel.add(viewlog);

		Panel.add(systemlogBG);

		Panel.add(bg);

		systemlogBG.setVisible(false);
	}


	public void viewResults() {
		viewlog.setVisible(false);
		try {
			count = getDateCount();
		} catch (SQLException ex) {
			Logger.getLogger(ViewBySystemLog.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			viewSystemLogItems();
			SystemLogDAO.getInstance().saveAccess("Viewed system log", user);
		} catch (SQLException ex ) {
			Logger.getLogger(ViewBySystemLog.class.getName()).log(Level.SEVERE, null, ex);
		}
		back.setVisible(true);
		next.setVisible(true);
		try {
			count = getDateCount();
		} catch (SQLException ex) {
			Logger.getLogger(ViewBySystemLog.class.getName()).log(Level.SEVERE, null, ex);
		}
	}


	private int getDateCount() throws SQLException {
		return SystemLogDAO.getInstance().getDateContent(datelist.getSelectedItem().toString()).size();
	}


	private void viewSystemLogItems() throws SQLException {
		slmdl = SystemLogDAO.getInstance().getDateContent(datelist.getSelectedItem().toString());
		count = getDateCount();

		access = new JLabel[count];
		username = new JLabel[count];
		date = new JLabel[count];

		for (int i = 0; i < count; i++) {
			access[i] = new JLabel(slmdl.get(i).getAccess());
			username[i] = new JLabel(slmdl.get(i).getUsername());
			date[i] = new JLabel(slmdl.get(i).getDate().toString());


			access[i].setFont(itemsFont);
			username[i].setFont(itemsFont);
			date[i].setFont(itemsFont);
			access[i].setForeground(Color.WHITE);
			username[i].setForeground(Color.WHITE);
			date[i].setForeground(Color.WHITE);
			access[i].setToolTipText(access[i].getText());
			username[i].setToolTipText(username[i].getText());
			access[i].setVisible(false);
			username[i].setVisible(false);
			date[i].setVisible(false);
			Panel.add(access[i]);
			Panel.add(username[i]);
			Panel.add(date[i]);

			Panel.add(systemlogBG);
			Panel.add(bg);
			setSystemLogBounds(access[i], username[i], date[i], i);
		}

		setSystemLogVisible();

	}
	public void change(JPanel panel, JLabel panelbutton) {
		if (this.panel != null && this.panelbutton != null) {
			this.remove(this.panel);
			this.panelbutton.addMouseListener(l);
			this.panelbutton.setEnabled(true);
		}

		this.panel = panel;
		Panel.add(panel);
		this.panel.setBounds(0, bg.getIcon().getIconHeight(), panel.getWidth(), panel.getHeight());
		this.panelbutton = panelbutton;
		panelbutton.removeMouseListener(l);
		panelbutton.setEnabled(false);
	}


	private void setSystemLogBounds(JLabel access, JLabel username, JLabel date, int i){

		switch (i % 5) {
		case 0:
			access.setBounds(97,140,500,30);
			username.setBounds(375,140,500,30);
			date.setBounds(620, 140, 500, 30);
			break;
		case 1:
			access.setBounds(97,170,500,30);
			username.setBounds(367,170,500,30);
			date.setBounds(620, 170, 500, 30);
			break;
		case 2:
			access.setBounds(97,200,500,30);
			username.setBounds(367,200,500,30);
			date.setBounds(620, 200, 500, 30);
			break;
		case 3:
			access.setBounds(97,230,500,30);
			username.setBounds(367,230,500,30);
			date.setBounds(620, 230, 500, 30);
			break;
		case 4:
			access.setBounds(97,260,500,30);
			username.setBounds(367,260,500,30);
			date.setBounds(620, 260, 500, 30);
			break;

		}

		systemlogBG.setBounds(0, 0, systemlogBG.getIcon().getIconWidth(), systemlogBG.getIcon().getIconHeight());
	}



	private void setSystemLogVisible() {

		for (int i = 0; i < count; i++) {
			if (i < current || i >= current + 5) {
				access[i].setVisible(false);
				username[i].setVisible(false);
				date[i].setVisible(false);
			} else {
				access[i].setVisible(true);
				username[i].setVisible(true);
				date[i].setVisible(true);
			}
		}
	}

	public class Listener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) throws NullPointerException {

			if(e.getSource() == viewlog){
				viewResults();
			} 

			if (e.getSource() == next) {
				if (current + 5 < count) {
					current += 5;
					setSystemLogVisible();
				}
			} else if (e.getSource() == back) {
				if (current - 5 >= 0) {
					current -= 5;
					setSystemLogVisible();
				}
			} 
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}
		@Override
		public void mouseExited(MouseEvent e) {

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
