package com.spiru.dev.groupingTaskProfessor_addon.Utils;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.spiru.dev.groupingTaskProfessor_addon.GroupingTaskAddOnJPanel;

public class JPanelEditor extends JPanel implements MouseMotionListener {

	private JLabel labelCaption;
	private JLabel labelCount;
	private JTextField textfield;
	private JTextField count;
	private JButton buttonAdd;
	private MyMouseListener listener;
	private List<DragElement> elementList;
	private GroupingTaskAddOnJPanel panel;
	private JButton buttonDelete;
	private JLabel labelDuplicate;
	
	public JPanelEditor(int x, int y, int breit, int hoch, MyMouseListener listener, List<DragElement> elementList, GroupingTaskAddOnJPanel panel){
		this.setLayout(null);		
		this.setBounds(x,y,breit,hoch);
		this.listener = listener;
		this.elementList = elementList;
		this.panel = panel;
		this.addMouseMotionListener(this);
		labelCaption = new JLabel("Text: ");
		labelCaption.setBounds(10,10,50,10);		
		labelCount = new JLabel("Anzahl (n = \u221e):");		
		labelCount.setBounds(10,42,100,10);
		textfield = new JTextField();		
		textfield.setBounds(60,5,breit-70,25);
		count = new JTextField();	
		count.setBounds(110,35,60, 25);
		count.addKeyListener(new MyKeyListener(count));
		buttonAdd = new JButton("Add");
		buttonAdd.setBounds(190,35,80,25);
		
	    buttonAdd.addActionListener( new ActionListener() {
	          public void actionPerformed(ActionEvent e) {
	            buttonAddAction();
	          }
	        } );	    
	    
	    buttonDelete = new JButton("Delete");
	    buttonDelete.setBounds(280,35,100,25);
	    buttonDelete.addActionListener( new ActionListener() {
	          public void actionPerformed(ActionEvent e) {
	        	  buttonDeleteAction();	        	  
	          }
	        } );
		/*
		SpinnerNumberModel model = new SpinnerNumberModel();
		model.setMinimum(0);
		model.setMaximum(100);
		model.setStepSize(1);
		model.setValue(0);
		count.setModel(model);	
		*/
		labelDuplicate = new JLabel("Name schon vorhanden!");
		labelDuplicate.setForeground(Color.RED);
		labelDuplicate.setBounds(buttonDelete.getX()+buttonDelete.getWidth()+10, buttonDelete.getY(),200,20);
		labelDuplicate.setVisible(false);
		this.add(labelDuplicate);
		
		this.add(labelCaption);
		this.add(labelCount);
		this.add(textfield);
		this.add(count);
		this.add(buttonAdd);
		this.add(buttonDelete);
	}	
	
	private void buttonAddAction(){		
		if (textfield.getText().equals("") || count.getText().equals(""))
			return;
		String anzahl = "";
		boolean ok = true;
		if (count.getText().equals("n"))
			anzahl = "\u221e";
		else{
			try{
				Integer.parseInt(count.getText());
				anzahl = count.getText();
			}
			catch (Exception e){
				// Fehler! keine integer Zahl!
				count.setForeground(Color.RED);	
				ok=false;
			}	
		}
		if (ok){
			DragElement e = new DragElement(textfield.getText(),anzahl, ""+elementList.size(), listener);
			for(DragElement n: elementList){
				if (n.getCaption().equals(e.getCaption())){
					e = null;
					labelDuplicate.setVisible(true);
					break;
				}
			}
			if (e != null)
				elementList.add(e);		
			panel.addElements();
			textfield.setText("");
			count.setText("1");
		}			
		textfield.requestFocus();
	}
	
	private void buttonDeleteAction(){
		panel.deleteElement();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		labelDuplicate.setVisible(false);
		
	}
}
