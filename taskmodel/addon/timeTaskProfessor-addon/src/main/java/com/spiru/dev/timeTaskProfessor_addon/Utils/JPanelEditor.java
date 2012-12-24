package com.spiru.dev.timeTaskProfessor_addon.Utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import com.spiru.dev.timeTaskProfessor_addon.TimeTaskAddOnJPanel;

public class JPanelEditor extends JPanel {

	private JPanel panelElement;
	private JPanel panelObject;
	private TimeTaskAddOnJPanel pan;
	private int countObjects = 1;

	//****************************** Element
	private JTextField elementText;
	private final JButton buttonColor = new JButton();
	private JButton buttonAddSymbol;
	//****************************** Object
	private List<String> elements = new ArrayList<String>();
	private JComboBox objConnectionToSymbolList;
	private JTextField objectText;
	private JCheckBox checkboxVisible;
	private JSpinner pos;
	private JButton buttonAddObject;
	//****************************** 
	private ExMouseListener listener;
	private MyMouseListener mouseListener;
	private List<DragElement> listElements = new ArrayList<DragElement>();
	private Symbol symbolToEdit = null;
	private DragElement editDragElement = null;


	public JPanelEditor(int width, TimeTaskAddOnJPanel pan, MyMouseListener mouseListener){	
		this.pan = pan;
		this.setLayout(null);		
		this.mouseListener = mouseListener;
		this.add(createPanelElement(width));
		this.add(createPanelObject(width));

		this.setBounds(0, 0, width, panelElement.getHeight()+panelObject.getHeight());
		this.setPreferredSize(new Dimension(width, panelElement.getHeight()+panelObject.getHeight()));
		listener = new ExMouseListener(this);		
	}

	private JPanel createPanelElement(int width){
		panelElement = new JPanel();
		panelElement.setLayout(null);		
		panelElement.setBounds(0, 0, width, 80);
		panelElement.setPreferredSize(new Dimension(width, 80));
		panelElement.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel elementCaption = new JLabel("EventText:");		
		elementCaption.setBounds(10, 8, 70, 35);
		panelElement.add(elementCaption);

		elementText = new JTextField();
		elementText.setBounds(75, 10, width-100, 30);
		panelElement.add(elementText);

		JLabel elementColor = new JLabel("Color: ");
		elementColor.setBounds(10, 40, 50, 35);
		panelElement.add(elementColor);

		buttonColor.setBounds(75, 46, 25, 25);
		buttonColor.setFocusable(false);
		buttonColor.setBackground(Color.BLUE);
		buttonColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JColorChooser clr = new JColorChooser();
				Color color = clr.showDialog(buttonColor, "Choose Color", Color.white);
				buttonColor.setBackground(color);		
			}
		});
		panelElement.add(buttonColor);

		buttonAddSymbol = new JButton("Add Event");
		buttonAddSymbol.setBounds(105, 45, 150, 26);
		buttonAddSymbol.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				addElement();	
			}
		});
		panelElement.add(buttonAddSymbol);		

		JButton buttonDelete = new JButton("Delete Event");
		buttonDelete.setBounds(260, 45, 150, 26);
		buttonDelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				removeElement();	
			}
		});
		panelElement.add(buttonDelete);			

		return panelElement;		
	}

	private JPanel createPanelObject(int width){
		panelObject = new JPanel();
		panelObject.setLayout(null);		
		panelObject.setBounds(0, panelElement.getHeight()-1, width, 145);
		panelObject.setPreferredSize(new Dimension(width, 145));
		panelObject.setBorder(BorderFactory.createLineBorder(Color.black));

		JLabel objectCaption = new JLabel("DateText:");		
		objectCaption.setBounds(10, 8, 70, 35);
		panelObject.add(objectCaption);

		objectText = new JTextField();
		objectText.setBounds(75, 10, width-100, 30);
		panelObject.add(objectText);

		checkboxVisible = new JCheckBox("Visible:       ",true);
		checkboxVisible.setHorizontalTextPosition(SwingConstants.LEFT);
		checkboxVisible.setBounds(5,45,90,20);
		panelObject.add(checkboxVisible);

		JLabel position = new JLabel("Position: ");
		position.setBounds(95, 38, 70, 35);
		panelObject.add(position);

		pos = new JSpinner();
		pos.setBounds(148, 44, 70, 25);
		SpinnerNumberModel model = new SpinnerNumberModel();
		model.setMinimum(1);
		//model.setMaximum(100);
		model.setStepSize(1);
		model.setValue(1);
		pos.setModel(model);
		panelObject.add(pos);

		JLabel labelConnect = new JLabel("Connect with:");
		labelConnect.setBounds(10, 75, 95, 25);
		panelObject.add(labelConnect);

		elements.add("none");
		objConnectionToSymbolList = new JComboBox(elements.toArray());
		objConnectionToSymbolList.setBounds(100, 75, width-140, 25);
		panelObject.add(objConnectionToSymbolList);

		buttonAddObject = new JButton("Add Date");
		buttonAddObject.setBounds(100, 110, 145, 26);
		buttonAddObject.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				addObject();				
			}
		});
		panelObject.add(buttonAddObject);

		JButton buttonDelete = new JButton("Delete Date");
		buttonDelete.setBounds(250, 110, 150, 26);
		buttonDelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				removeObject();				
			}
		});
		panelObject.add(buttonDelete);	

		return panelObject;
	}
	
	public List<DragElement> getEvents(){
		return listElements;
	}

	public void editObject(DatePoint dp){
		if (dp == null){
			closeObjectView();
			buttonAddObject.setText("Add Date");
			return;
		}
		// EditObject 
		checkboxVisible.setSelected(dp.isDateVisible());
		pos.setValue(dp.getPos());
		objectText.setText(dp.getCaption());
		buttonAddObject.setText("Edit Date");
		objConnectionToSymbolList.setSelectedItem("none");
		if (dp.getSymbol() != null){
			for(DragElement n: listElements){
				if (n.getId() == dp.getSymbol().getId()){
					objConnectionToSymbolList.setSelectedItem(n.getCaption());
					break;
				}
			}			
		}		
	}

	private void editObject(){		
		DatePoint dp = listener.getSelection();
		dp.setCaption(objectText.getText());
		dp.setDateVisible(checkboxVisible.isSelected());
		dp.setPos((Integer)pos.getValue());
		connectWithSymbol(dp);
		pan.sortObjects();		
		closeObjectView();
	}

	public void closeObjectView(){
		objectText.setText("");
		checkboxVisible.setSelected(true);
		listener.clearSelection();
		buttonAddObject.setText("Add Date");
		pos.setValue(countObjects);
		objConnectionToSymbolList.setSelectedItem("none");
	}

	private void removeObject(){		
		if (listener.getSelection() != null){
			pan.removeSymbol(listener.getSelection().getSymbol());
			pan.removeObject(listener.getSelection());
			countObjects--;
			pos.setValue(countObjects);
			closeSymbolView();
			closeObjectView();
			mouseListener.clearSelection();
		}
	}	

	private void connectWithSymbol(DatePoint dp){		
		// Find Element 
		for(DragElement n:listElements){
			if(n.getCaption().equals(objConnectionToSymbolList.getSelectedItem())){				
				int x = dp.getX()+dp.getWidth()/2;
				int y = dp.getY()-100;
				Symbol sym = new Symbol(new Point(x-10,y), n.getColor(), n.getId());				
				ConnectionLine line = new ConnectionLine(sym,x,110);
				sym.setConnectionLine(line);	
				pan.removeSymbol(dp.getSymbol());
				dp.setSymbol(sym);				
				pan.addSymbol(sym);				
				break;
			}
		}
		if (objConnectionToSymbolList.getSelectedItem().equals("none")){
			pan.removeSymbol(dp.getSymbol());
			dp.setSymbol(null);
		}
	}

	private void addObject(){				
		if (buttonAddObject.getText().equals("Edit Date")){
			editObject();
			return;
		}
		if (!objectText.getText().equals("")){
			DatePoint dp = new DatePoint(objectText.getText(), checkboxVisible.isSelected());
			if (pan.addObject(dp, (Integer)(pos.getValue()))){
				countObjects++;
				pos.setValue(countObjects);
				dp.addMouseListener(listener);
				dp.setBackground(Color.PINK);
				dp.setMarked(false);				
				connectWithSymbol(dp);
			}
		}
		closeSymbolView();
		closeObjectView();
		mouseListener.clearSelection();
	}

	private void editSymbol(){
		mouseListener.clearSelection();
		if (symbolToEdit != null){
			symbolToEdit.setBackground(buttonColor.getBackground());
		}
		if (editDragElement != null){			
			editDragElement.setColor(buttonColor.getBackground());			
			if (elementText.getText() != null && !elementText.getText().equals("none") 
					&& !elementText.getText().equals("")){				
				elements.remove(editDragElement.getCaption());
				elements.add(elementText.getText());								
				objConnectionToSymbolList.removeAllItems();				
				for(String k:elements){
					objConnectionToSymbolList.addItem(k);					
				}
				editDragElement.setCaption(elementText.getText());				
			}							
		}
		closeSymbolView();
	}

	public void closeSymbolView(){
		buttonAddSymbol.setText("Add Event");
		elementText.setText("");
		Random r = new Random();
		buttonColor.setBackground(new Color(r.nextInt(240)+10,r.nextInt(240)+10,r.nextInt(240)+10));
	}

	public void editElement(DragElement e, List<Symbol> symbols){
		editDragElement = e;
		buttonAddSymbol.setText("Edit Event");
		buttonColor.setBackground(e.getColor());
		elementText.setText(e.getCaption());
		for(Symbol n:symbols){
			if(n.getId() == editDragElement.getId()){
				symbolToEdit = n;
				break;
			}
		}		
	}

	public void editSymbol(Symbol sym){
		symbolToEdit = sym;		
		buttonAddSymbol.setText("Edit Event");
		buttonColor.setBackground(sym.getBackground());
		for(DragElement n:listElements){
			if (n.getId() == symbolToEdit.getId()){
				elementText.setText(n.getCaption());
				editDragElement = n;
				break;
			}
		}		
	}
	
	public void loadSymbols(String[][] symbolsList, String[][] elementsList){
		// idEvent, x, y, xOnLine
		// fuer alle symbole
		for(int i=0; i<symbolsList.length; i++){
			// finde id von DragElement
			String name = null;
			for(int k=0; k<elementsList.length; k++){				
				if(symbolsList[i][0].equals(elementsList[k][0])){
					name = elementsList[k][1];					
					break;
				}
			}
			// finde Element nach name
			for(DragElement n:listElements){
				if(n.getCaption().equals(name)){
					Symbol sym = new Symbol(new Point(Integer.parseInt(symbolsList[i][1]), Integer.parseInt(symbolsList[i][2])), n.getColor(), n.getId());
					if(symbolsList[i][3].length()>0){
						ConnectionLine line = new ConnectionLine(sym, Integer.parseInt(symbolsList[i][3]), 110);
						sym.setConnectionLine(line);
					}
					pan.addSymbol(sym);					
					break;
				}
			}			
		}		
	}
	
	public void loadElements(String[][] elementsList){
		// id, name, color
		for(int i=0; i<elementsList.length; i++){
			DragElement el = new DragElement(elementsList[i][1], new Color(Integer.parseInt(elementsList[i][2])));
			pan.addElement(el);
			objConnectionToSymbolList.addItem(el.getCaption());
			el.addMouseListener(mouseListener);
			listElements.add(el);
			elements.add(el.getCaption());
		}
	}
	
	public void loadObjects(String[][] objList, String[][] elementsList){
		// id, name, visible, connected
		for(int i=0; i<objList.length; i++){
			DatePoint dp = null;
			if (objList[i][2].equals("true"))
				dp = new DatePoint(objList[i][1], true);
			else
				dp = new DatePoint(objList[i][1], false);
			pan.addObject(dp, Integer.parseInt(objList[i][0]));
			countObjects++;
			dp.addMouseListener(listener);
			dp.setBackground(Color.PINK);
			dp.setMarked(false);
			objConnectionToSymbolList.setSelectedItem("none");
			// find Element
			for(int k=0; k<elementsList.length; k++){
				if(objList[i][3].equals(elementsList[k][0])){
					objConnectionToSymbolList.setSelectedItem(elementsList[k][1]);
					break;
				}
			}
			connectWithSymbol(dp);
		}
		pos.setValue(countObjects);
		objConnectionToSymbolList.setSelectedItem("none");
	}

	private void addElement(){
		if (buttonAddSymbol.getText().equals("Add Event")){
			if (elementText.getText().equals("") || elementText.getText().equals("none")){
				//mache ausgabe oder so...
				return;
			}
			// Auf Rückgabewert achten!
			DragElement e = new DragElement(elementText.getText(), buttonColor.getBackground());
			if (pan.addElement(e)){			
				objConnectionToSymbolList.addItem(e.getCaption());
				e.addMouseListener(mouseListener);
				listElements.add(e);
				elements.add(elementText.getText());
			}
		}
		else{
			editSymbol();
		}
		closeSymbolView();
		closeObjectView();
		mouseListener.clearSelection();
	}

	private void removeElement(){
		pan.removeSymbol(symbolToEdit);
		pan.removeElement(editDragElement, symbolToEdit);
		elements.remove(editDragElement.getCaption());	
		listElements.remove(editDragElement);
		objConnectionToSymbolList.removeItem(editDragElement.getCaption());						
		if (symbolToEdit != null){
			symbolToEdit.setConnectionLine(null);
			symbolToEdit = null;		
		}
		editDragElement = null;
		mouseListener.clearSelection();
		closeSymbolView();
		closeObjectView();
		mouseListener.clearSelection();
	}

}

class ExMouseListener implements MouseListener{

	private DatePoint dp;
	private JPanelEditor pan;

	public ExMouseListener(JPanelEditor pan){
		this.pan = pan;
	}

	public void mouseClicked(MouseEvent e) {		
		DatePoint temp = (DatePoint)e.getComponent();
		if (dp != null && temp == dp){
			dp.setMarked(false);
			dp = null;
			pan.editObject(null);
		}
		else{
			if (dp != null)
				dp.setMarked(false);
			dp = temp;
			dp.setMarked(true);
			pan.editObject(dp);
		}

	}

	public DatePoint getSelection(){
		return dp;
	}

	public void clearSelection(){
		if (dp != null)
			dp.setMarked(false);
		dp = null;
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

