package com.spiru.dev.groupingTask_addon.Utils;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JLabel;

import com.spiru.dev.groupingTask_addon.GroupingTaskAddOnJPanel;

public class JPanel2MouseListener implements MouseListener {

	List<Object> allPossibleElements = null;
	GroupingTaskAddOnJPanel gpanel = null;
	public JPanel2MouseListener(GroupingTaskAddOnJPanel p)
	{
		this.gpanel = p;
		this.allPossibleElements = p.getCompoentList();
	}
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub	
		Object selected = arg0.getSource();
		
		for(int i = 0; i < this.allPossibleElements.size(); i++)
		{
			if(selected.equals(this.allPossibleElements.get(i)))
			{
				System.out.println("Oadfasdfasdfki " +( this.allPossibleElements.get(i) instanceof JLabel));
//				//here we change the background of the Label
				
				if(this.allPossibleElements.get(i) instanceof JLabel)
				{
					System.out.println("Oki");
					JLabel a = ((JLabel)this.allPossibleElements.get(i));
					a.setBackground(new Color(23,23,23));
					this.gpanel.setjLabel2(a);
					break;
				}
				break;
			}
		}
	
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
