/**
 * Programm zur Konvertierung von aus Moodle exportierten Übungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 */

package de.christophjobst.main;

import java.security.SecureRandom;
import java.math.BigInteger;

public class RandomIdentifierGenerator {

	private SecureRandom random = new SecureRandom();

	public String getRandomID() {
		return new BigInteger(51, random).toString(32);
	}
}
