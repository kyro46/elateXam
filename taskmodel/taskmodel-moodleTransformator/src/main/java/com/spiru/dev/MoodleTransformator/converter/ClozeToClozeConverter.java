/**
 * Programm zur Konvertierung von aus Moodle exportierten öbungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package com.spiru.dev.MoodleTransformator.converter;

import java.io.UnsupportedEncodingException;

import com.spiru.dev.MoodleTransformator.main.RandomIdentifierGenerator;

import generated.Quiz.Question;
import de.thorstenberger.taskmodel.complex.jaxb.ClozeSubTaskDef.Cloze;
import de.thorstenberger.taskmodel.complex.jaxb.ClozeSubTaskDef.Cloze.Gap;
import de.thorstenberger.taskmodel.complex.jaxb.ClozeSubTaskDef;

public class ClozeToClozeConverter {

	public static ClozeSubTaskDef processing(Question question) {

		RandomIdentifierGenerator rand = new RandomIdentifierGenerator();

		ClozeSubTaskDef subTask = new ClozeSubTaskDef();

		// Allgemeine Angaben pro Frage
		subTask.setTrash(false);
		subTask.setInteractiveFeedback(false);
		subTask.setCorrectionHint(" ");
		subTask.setHint(question.getName().getText().toString());

		// Spezielle Angaben pro Frage
		subTask.setId(question.getName().getText().toString() + "_"
				+ rand.getRandomID());

		String problem = "Lösen Sie folgenden Lückentext.";
		// Konvertierung des String in separaten Block,
		// falls bei höufiger Nutzung Auslagerung nötig
		try {
			byte[] bytes = problem.getBytes("UTF-8");
			problem = new String(bytes);
			// System.out.println(problem);
			subTask.setProblem(problem);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		subTask.setCloze(clozeParser(question.getQuestiontext().getText()));


		return subTask;
	}

	private static Cloze clozeParser(String moodleText) {

		Cloze cloze = new Cloze();
		int klammeraufindex = 0;
		int klammerzuindex = 0;

		int shortanswerindex = 0;
		int multichoiceindex = 0;
		int numericalindex = 0;
		int gapIndexToUse = 0;
		
		shortanswerindex = moodleText.indexOf(":SHORTANSWER:");
		multichoiceindex = moodleText.indexOf(":MULTICHOICE:");
		numericalindex = moodleText.indexOf(":NUMERICAL:");		
		
		
		// Die { und } suchen nachdem man :SHORTANSWER: :MULTICHOICE: oder
		// :NUMERICAL: gefunden hat

		do {
			gapIndexToUse = Math.max(shortanswerindex, Math.max(multichoiceindex, numericalindex));

				klammeraufindex = moodleText.lastIndexOf("{", gapIndexToUse);

				if (klammeraufindex != -1) {

					if (klammerzuindex == 0) {
						cloze.getTextOrGap().add(
								moodleText.substring(klammerzuindex,
										klammeraufindex));
					} else {
						cloze.getTextOrGap().add(
								moodleText.substring(klammerzuindex + 1,
										klammeraufindex));
					}

					klammerzuindex = moodleText.indexOf("}", klammeraufindex);
					cloze.getTextOrGap().add(
							gapBuilder(moodleText.substring(klammeraufindex+1,
									klammerzuindex)));
				} else {
					cloze.getTextOrGap().add(
							moodleText.substring(klammerzuindex + 1));
				}

			shortanswerindex = moodleText.indexOf(":SHORTANSWER:",gapIndexToUse+1);
			multichoiceindex = moodleText.indexOf(":MULTICHOICE:",gapIndexToUse+1);
			numericalindex = moodleText.indexOf(":NUMERICAL:", gapIndexToUse+1);
			
		} while (klammeraufindex != -1);
		return cloze;
	}

	private static Gap gapBuilder(String input) {

		Gap gap = new Gap();
		gap.setIgnoreCase(true);
		String correctAnswer;

		int nextAnswer = 0;
		System.out.println(input);
		while((nextAnswer = input.indexOf("=",nextAnswer+1)) != -1){

			if (input.indexOf("=",nextAnswer+1) == -1){
			correctAnswer = input.substring(nextAnswer+1);
			System.out.println(correctAnswer);
			} else {
				correctAnswer = input.substring(nextAnswer+1, input.indexOf("=",nextAnswer+1));
				System.out.println(correctAnswer);
			}

			gap.getCorrect().add(correctAnswer);
			
			correctAnswer = new String();

		}
		
		return gap;
	}

}
