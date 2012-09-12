package com.spiru.dev.timeTaskProfessor_addon.Utils;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import com.spiru.dev.timeTaskProfessor_addon.TimeTaskAddOnJPanel;

public class JPanelEditor extends JPanel {
	
	//******** ElementInputPanel
	private JPanel elementInputPanel;
	private JLabel labelCaption;
	private JTextField textfieldCaption;
	private JButton buttonColor;
	private JButton buttonAddElement;
	private JButton buttonDeleteElement;
	//******** DatePointInputPanel
	private JPanel datePointInputPanel;
	private JLabel labelDate;
	private JTextField textfieldDate;
	private JCheckBox checkboxVisible;
	private JButton buttonAddDate;
	private JButton buttonDeleteDate;
	//*******
	TimeTaskAddOnJPanel panel;
	MyMouseListener listener;
		
	public JPanelEditor(TimeTaskAddOnJPanel panel, MyMouseListener listener){
		this.setLayout(null);
		this.panel = panel;
		this.listener = listener;
		elementInputPanel = new JPanel();
		elementInputPanel.setLayout(null);
		elementInputPanel.setBorder(new LineBorder(Color.DARK_GRAY,1));
		elementInputPanel.setBounds(0,0,400,65);
		
		labelCaption = new JLabel("Ereignis: ");
		labelCaption.setBounds(5,7,60,20);
		elementInputPanel.add(labelCaption);
		
		textfieldCaption = new JTextField();
		textfieldCaption.setBounds(70,5,320,25);
		elementInputPanel.add(textfieldCaption);
		
		buttonColor = new JButton("Color");
		buttonColor.setBounds(5,35,100,25);
		buttonColor.setFocusable(false);
		buttonColor.setForeground(Color.BLUE);
		buttonColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JColorChooser clr = new JColorChooser();
                Color color = clr.showDialog(buttonColor, "Choose Color", Color.white);
                buttonColor.setForeground(color);		
			}
		});
		elementInputPanel.add(buttonColor);
		
		buttonAddElement = new JButton("Add");
		buttonAddElement.setBounds(115,35,100,25);
		buttonAddElement.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				addElement();
				
			}
			
		});
		elementInputPanel.add(buttonAddElement);
		
		buttonDeleteElement = new JButton("Delete");
		buttonDeleteElement.setBounds(225,35,100,25);
		buttonDeleteElement.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				deleteElement();
				
			}
			
		});
		elementInputPanel.add(buttonDeleteElement);
		this.add(elementInputPanel);
		
		datePointInputPanel = new JPanel();			
		datePointInputPanel.setLayout(null);
		datePointInputPanel.setBorder(new LineBorder(Color.DARK_GRAY,1));
		datePointInputPanel.setBounds(0,64,400,66);
		
		labelDate = new JLabel("Datum: ");
		labelDate.setBounds(5,5,60,20);
		datePointInputPanel.add(labelDate);
		
		textfieldDate = new JTextField();
		textfieldDate.addKeyListener(new MyKeyListener());
		textfieldDate.setBounds(70,5,320,25);
		datePointInputPanel.add(textfieldDate);
		
		checkboxVisible = new JCheckBox("Visible: ",true);
		checkboxVisible.setHorizontalTextPosition(SwingConstants.LEFT);
		checkboxVisible.setBounds(5,38,70,20);
		datePointInputPanel.add(checkboxVisible);
		
		buttonAddDate = new JButton("AddDate");
		buttonAddDate.setBounds(85,35,100,25);
		buttonAddDate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				addDate();				
			}			
		});
		datePointInputPanel.add(buttonAddDate);
		
		buttonDeleteDate = new JButton("DeleteDate");
		buttonDeleteDate.setBounds(195,35,100,25);
		buttonDeleteDate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				deleteDate();				
			}			
		});
		datePointInputPanel.add(buttonDeleteDate);
		
		this.add(datePointInputPanel);					
	}
	
	private void deleteDate(){
		panel.deleteDate();
	}
	
	private void addDate(){
		if (textfieldDate.getText().equals(""))
			return;
		DatePoint d = new DatePoint(textfieldDate.getText(),checkboxVisible.isSelected());		
		panel.addDatePoint(d);
	}
	
	private void addElement(){
		String caption = textfieldCaption.getText();
		Color color = buttonColor.getForeground();
		if (!caption.equals("")){
			DragElement e = new DragElement(caption,color,listener, panel.getCountOfElements());
			panel.addElement(e);
		}		
	}
	
	private void deleteElement(){		
		panel.deleteElement();
	}
}
