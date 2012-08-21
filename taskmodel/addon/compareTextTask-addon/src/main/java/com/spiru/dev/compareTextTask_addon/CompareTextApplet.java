package com.spiru.dev.compareTextTask_addon;

import java.applet.Applet;

public class CompareTextApplet extends Applet {
	/**
	 * Initialization method that will be called after the applet is loaded into
	 * the browser.
	 */
	public void init() {
		String text = "Die bestehende Codebasis des elateXam-Projektes ist sehr umfangreich, wodurch es schwierig ist, sich einen Überblick zu verschaffen, was genau in einem bestimmten Codeabschnitt passiert.\n"
				+ "Es muss stets unterschieden werden, welche Klassen nur als Mapper auf XML-Daten agieren, und welche Klassen tatsächlich wesentliche Implementierungen enthalten.\n"
				+ "Ein besseres Verständniss wichtiger Codeabschnitte ist nötog, mögliche Seiteneffekte überschauen und vermeiden zu können.\n"
				+ "\n"
				+ "Vorgehensmodelle sind nötig, um sich als Programmierer in Projekte mit sehr viel Quelltext (50k+ Zeilen) hineinzuarbeiten. Die ersten Schritte bestehen darin, sich einen Überblick\n"
				+ "über die Projektstruktur und über die verwendeten Technologien zu verschaffen.\n"
				+ "\n"
				+ "Bei konkreten Problemen gilt es, herausfinden, welche Klassen betroffen sind und welche dieser involvierten Klassen für die Problematik relevante Implementationen enthalten.\n"
				+ "Um effektiv in kurzer Zeit ans Ziel zu gelangen, werden nur die Codeabschnitte genauer betrachtet, die mehr tun als nur lesend auf Daten zugreifen.\n"
				+ "\n"
				+ "Ein Problem, welches im elateXam zum Vorschein kam, war, dass es an einigen Stellen Get-Methoden gibt (bei deren Aufruf man also damit rechnet, dass nur Lesend auf Daten zugegriffen wird),\n"
				+ "die Methodenaufrufe enthalten, die zu schwer erkennbaren Seiteneffekten führen können. So ruft beispielsweise der Getter TaskletContainerImpl.getTasklet() die Methode Tasklet.update() auf,\n"
				+ "wodurch nach Ablauf der Prüfungszeit die Methode ComplexTaskletImpl.submit() aufgerufen wird. Diese Vorgehensweise der Altentwickler hat die Fehlersuche enorm erschwert, zumal dieses Verhalten in keiner Weise dokumentiert war.\n"
				+ "\n"
				+ "Als Möglichkeit in solchen Fällen herauszufinden, von wo aus es zu bestimmten Methodenaufrufen kommt, bleibt dann oft nur noch eine Volltextsuche innerhalb des Projektes durchzuführen.\n"
				+ "Außerdem lässt sich mit Print-Anweisungen prüfen, ob eine Methode, mit deren Aufruf man rechnet, auch wirklich aufgerufen wird.";
		String xmldef = "<?xml version='1.0' encoding='UTF-8' ?><!DOCTYPE api SYSTEM 'CompletionXml.dtd'><api language='HTML'><keywords><keyword name='var' type='tag'><desc>Defines a variable</desc></keyword><keyword name='video' type='tag'><desc>Defines a video</desc></keyword></keywords></api>";
		CompareTextPanel jpanel = new CompareTextPanel(text, xmldef);
		//jpanel.setSize(600, 400);
		add(jpanel);
		this.setSize(800, 400);
	}
	@Override
	public void start() {
	}
	@Override
	public void stop() {
	}
}
