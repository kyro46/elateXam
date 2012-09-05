/**
 * Programm zur Konvertierung von aus Moodle exportierten �bungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package com.spiru.dev.MoodleTransformator.test;

import generated.Quiz;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskDef;

public class DebuggingAusgabe {

	public static void printExistingComplexTaskDefCategoryblocks(
			ComplexTaskDef complexTaskDef) {
		try {

			for (int i = 0; i < complexTaskDef.getCategory().get(0)
					.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().size(); i++) {
				System.out.println(complexTaskDef.getCategory().get(0)
						.getMcTaskBlockOrClozeTaskBlockOrTextTaskBlock().get(i)
						.getClass());
			}

		} catch (Exception e) {
			System.out
					.println("PROBLEM BEIM COMPLEXTASKDEF-CATEGORYBLOCK AUSLESEN");
			e.printStackTrace();
		}

	}

	public static void printQuestionsTypeAndAnswer(Quiz quizsammlung) {

		System.out.println(System.getProperty("file.encoding"));

		System.out.println(quizsammlung.getQuestion().toArray().length
				+ "Fragen in der Quizliste.");

		for (int i = 0; i < quizsammlung.getQuestion().toArray().length; i++) {
			try {
				if (!quizsammlung.getQuestion().get(i).getType().toString()
						.equals("category")) {
					System.out
							.println("#######################################");
					System.out.println(quizsammlung.getQuestion().get(i)
							.getType().toString());
					System.out.println(quizsammlung.getQuestion().get(i)
							.getQuestiontext().getText());

					if (quizsammlung.getQuestion().get(i).getType().toString()
							.equals("truefalse")) {

						if (quizsammlung.getQuestion().get(i).getAnswer()
								.get(0).getFraction().equals("100")) {
							System.out.println(true);
						} else
							System.out.println("false");
					} else
						;// System.out.println(quizsammlung.getQuestion().get(i).getAnswer().get(0).getText().toString());
				}
			}

			catch (Exception e) {
				System.out.println("PROBLEM BEI DER INITIALISIERUNG" + i);
			}
		}
	}

}
