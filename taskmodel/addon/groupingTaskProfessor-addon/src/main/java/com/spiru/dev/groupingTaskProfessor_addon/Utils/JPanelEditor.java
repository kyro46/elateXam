package com.spiru.dev.groupingTaskProfessor_addon.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.spiru.dev.groupingTaskProfessor_addon.GroupingTaskAddOnJPanel;

public class JPanelEditor extends JPanel {

	private JLabel labelCaption;
	private JLabel labelCount;
	private JTextField textfield;
	private JSpinner count;
	private JButton buttonAdd;
	private MyMouseListener listener;
	private List<Element> elementList;
	private GroupingTaskAddOnJPanel panel;
	private JButton buttonSave;
	private JButton buttonDelete;
	
	public JPanelEditor(int x, int y, int breit, int hoch, MyMouseListener listener, List<Element> elementList, GroupingTaskAddOnJPanel panel){
		this.setLayout(null);		
		this.setBounds(x,y,breit,hoch);
		this.listener = listener;
		this.elementList = elementList;
		this.panel = panel;
		labelCaption = new JLabel("Text: ");
		labelCaption.setBounds(10,10,50,10);		
		labelCount = new JLabel("Anzahl:");		
		labelCount.setBounds(10,42,50,10);
		textfield = new JTextField();		
		textfield.setBounds(60,5,breit-70,25);
		count = new JSpinner();	
		count.setBounds(60,35,60, 25);
		buttonAdd = new JButton("Add");
		buttonAdd.setBounds(130,35,80,25);
		
	    buttonAdd.addActionListener( new ActionListener() {
	          public void actionPerformed(ActionEvent e) {
	            buttonAddAction();
	          }
	        } );
	    
	    buttonSave = new JButton("Save");
	    buttonSave.setBounds(310,35,80,25);
	    buttonSave.addActionListener( new ActionListener() {
	          public void actionPerformed(ActionEvent e) {
	        	 buttonSaveAction();	        	  
	          }
	        } );
	    
	    buttonDelete = new JButton("Delete");
	    buttonDelete.setBounds(220,35,80,25);
	    buttonDelete.addActionListener( new ActionListener() {
	          public void actionPerformed(ActionEvent e) {
	        	  buttonDeleteAction();	        	  
	          }
	        } );
		
		SpinnerNumberModel model = new SpinnerNumberModel();
		model.setMinimum(0);
		model.setMaximum(100);
		model.setStepSize(1);
		model.setValue(0);
		count.setModel(model);	
		
		this.add(labelCaption);
		this.add(labelCount);
		this.add(textfield);
		this.add(count);
		this.add(buttonAdd);
		this.add(buttonSave);
		this.add(buttonDelete);
	}
	
	private void buttonAddAction(){
		if (!textfield.getText().equals("")){
			String anzahl = count.getValue()+"";
			if (anzahl.equals("0"))
				anzahl = "n";
			Element e = new Element(textfield.getText(),anzahl,listener);
			for(Element n: elementList){
				if (n.getCaption().equals(e.getCaption())){
					e = null;
					break;
				}
			}
			if (e != null)
				elementList.add(e);		
			panel.addElements();
		}
	}
	
	private void buttonSaveAction(){
		panel.save();
	}
	
	private void buttonDeleteAction(){
		panel.deleteElement();
	}
}
