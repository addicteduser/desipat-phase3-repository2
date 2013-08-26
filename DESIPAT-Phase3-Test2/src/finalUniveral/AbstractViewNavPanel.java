/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalUniveral;

import java.sql.SQLException;
import javax.swing.JPanel;

/**
 *
 * @author Aram
 */
public abstract class AbstractViewNavPanel extends JPanel{


	public abstract void initialize();
	public abstract void setBounds();
	public abstract void setFrame();
	public abstract void addToFrame();

	public final void createPanel(){
		initialize();
		setBounds();
		setFrame();
		addToFrame();
	}
	
	public abstract JPanel getPanel();





}
