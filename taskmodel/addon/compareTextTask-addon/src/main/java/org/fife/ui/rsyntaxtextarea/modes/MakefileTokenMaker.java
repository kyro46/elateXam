/* The following code was generated by JFlex 1.4.1 on 1/20/09 10:04 AM */

/*
 * 09/20/2008
 *
 * MakefileTokenMaker.java - Scanner for makefiles.
 * 
 * This library is distributed under a modified BSD license.  See the included
 * RSyntaxTextArea.License.txt file for details.
 */
package org.fife.ui.rsyntaxtextarea.modes;

import java.io.*;
import java.util.Stack;
import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.*;


/**
 * Scanner for makefiles.<p>
 *
 * This implementation was created using
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.1; however, the generated file
 * was modified for performance.  Memory allocation needs to be almost
 * completely removed to be competitive with the handwritten lexers (subclasses
 * of <code>AbstractTokenMaker</code>, so this class has been modified so that
 * Strings are never allocated (via yytext()), and the scanner never has to
 * worry about refilling its buffer (needlessly copying chars around).
 * We can achieve this because RText always scans exactly 1 line of tokens at a
 * time, and hands the scanner this line as an array of characters (a Segment
 * really).  Since tokens contain pointers to char arrays instead of Strings
 * holding their contents, there is no need for allocating new memory for
 * Strings.<p>
 *
 * The actual algorithm generated for scanning has, of course, not been
 * modified.<p>
 *
 * If you wish to regenerate this file yourself, keep in mind the following:
 * <ul>
 *   <li>The generated MakefileTokenMaker.java</code> file will contain two
 *       definitions of both <code>zzRefill</code> and <code>yyreset</code>.
 *       You should hand-delete the second of each definition (the ones
 *       generated by the lexer), as these generated methods modify the input
 *       buffer, which we'll never have to do.</li>
 *   <li>You should also change the declaration/definition of zzBuffer to NOT
 *       be initialized.  This is a needless memory allocation for us since we
 *       will be pointing the array somewhere else anyway.</li>
 *   <li>You should NOT call <code>yylex()</code> on the generated scanner
 *       directly; rather, you should use <code>getTokenList</code> as you would
 *       with any other <code>TokenMaker</code> instance.</li>
 * </ul>
 *
 * @author Robert Futrell
 * @version 0.5
 *
 */

public class MakefileTokenMaker extends AbstractJFlexTokenMaker {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** lexical states */
  public static final int VAR = 1;
  public static final int YYINITIAL = 0;

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\10\1\7\1\0\1\10\23\0\1\10\1\0\1\13\1\15"+
    "\1\4\2\0\1\11\1\6\1\47\1\0\1\16\1\0\1\37\1\1"+
    "\1\0\12\2\1\3\2\0\1\17\1\0\1\16\1\0\32\1\1\0"+
    "\1\12\2\0\1\1\1\14\1\20\1\32\1\43\1\21\1\24\1\25"+
    "\1\41\1\44\1\26\1\45\1\1\1\35\1\34\1\33\1\40\1\22"+
    "\1\46\1\23\1\30\1\36\1\31\1\1\1\42\1\27\2\1\1\5"+
    "\1\0\1\50\uff82\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\2\1\1\2\2\1\1\3\1\4\1\5\1\6"+
    "\1\1\1\7\1\10\14\1\2\11\1\12\1\13\1\14"+
    "\1\15\1\16\1\17\1\20\1\5\1\6\1\21\2\0"+
    "\1\22\23\1\1\23\1\24\1\20\1\21\1\22\2\1"+
    "\1\25\43\1\1\25\14\1\1\25\5\1\1\0\2\1"+
    "\1\0\1\1\1\0\1\25";

  private static int [] zzUnpackAction() {
    int [] result = new int[129];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\51\0\122\0\173\0\244\0\315\0\366\0\122"+
    "\0\u011f\0\u0148\0\u0171\0\u019a\0\u01c3\0\122\0\u01ec\0\u0215"+
    "\0\u023e\0\u0267\0\u0290\0\u02b9\0\u02e2\0\u030b\0\u0334\0\u035d"+
    "\0\u0386\0\u03af\0\u03d8\0\u0401\0\u042a\0\122\0\122\0\122"+
    "\0\122\0\122\0\122\0\u0453\0\u047c\0\122\0\u019a\0\u04a5"+
    "\0\122\0\u04ce\0\u04f7\0\u0520\0\u0549\0\u0572\0\u059b\0\u05c4"+
    "\0\u05ed\0\u0616\0\u063f\0\u0668\0\u0691\0\u06ba\0\u06e3\0\u070c"+
    "\0\u0735\0\u075e\0\u0787\0\u07b0\0\122\0\122\0\u0148\0\u0171"+
    "\0\u019a\0\u07d9\0\u0802\0\173\0\u082b\0\u0854\0\u087d\0\u08a6"+
    "\0\u08cf\0\u08f8\0\u0921\0\u094a\0\u0973\0\u099c\0\u09c5\0\u09ee"+
    "\0\u0a17\0\u0a40\0\u0a69\0\u0a92\0\u0abb\0\u0ae4\0\u0b0d\0\u0b36"+
    "\0\u0b5f\0\u0b88\0\u0bb1\0\u0bda\0\u0c03\0\u0c2c\0\u0c55\0\u0c7e"+
    "\0\u0ca7\0\u0cd0\0\u0cf9\0\u0d22\0\u0d4b\0\u0d74\0\u0d9d\0\u0dc6"+
    "\0\u0def\0\u0e18\0\u0e41\0\u0e6a\0\u0e93\0\u0ebc\0\u0ee5\0\u0f0e"+
    "\0\u0f37\0\u0f60\0\u0f89\0\u0fb2\0\u0fdb\0\u1004\0\u102d\0\u1056"+
    "\0\u107f\0\u10a8\0\u10d1\0\u10fa\0\u1123\0\u114c\0\u1175\0\u119e"+
    "\0\122";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[129];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\1\4\1\5\1\6\1\7\2\3\1\10\1\11"+
    "\1\12\1\3\1\13\1\14\1\15\1\6\1\16\1\17"+
    "\1\20\1\21\1\4\1\22\1\23\1\24\1\4\1\25"+
    "\1\4\1\26\1\27\3\4\1\3\1\30\1\4\1\31"+
    "\2\4\1\32\1\4\2\3\4\33\1\34\10\33\1\35"+
    "\31\33\1\36\1\37\52\0\2\4\1\40\14\0\17\4"+
    "\1\0\7\4\4\0\1\5\65\0\1\16\36\0\1\41"+
    "\1\42\52\0\1\11\40\0\7\12\1\0\1\12\1\43"+
    "\1\44\36\12\7\13\1\0\2\13\1\45\1\46\35\13"+
    "\7\47\1\0\2\47\1\50\1\47\1\51\34\47\7\15"+
    "\1\0\41\15\1\0\2\4\1\40\14\0\1\4\1\52"+
    "\15\4\1\0\7\4\3\0\2\4\1\40\14\0\4\4"+
    "\1\53\1\4\1\54\10\4\1\0\7\4\3\0\2\4"+
    "\1\40\14\0\1\55\16\4\1\0\7\4\3\0\2\4"+
    "\1\40\14\0\13\4\1\56\1\4\1\57\1\4\1\0"+
    "\7\4\3\0\2\4\1\40\14\0\6\4\1\60\10\4"+
    "\1\0\1\61\6\4\3\0\2\4\1\40\14\0\5\4"+
    "\1\62\11\4\1\0\7\4\3\0\2\4\1\40\14\0"+
    "\11\4\1\63\4\4\1\64\1\0\1\65\3\4\1\66"+
    "\2\4\3\0\2\4\1\40\14\0\1\67\16\4\1\0"+
    "\7\4\3\0\2\4\1\40\14\0\17\4\1\0\1\70"+
    "\6\4\3\0\2\4\1\40\14\0\3\4\1\71\13\4"+
    "\1\0\7\4\3\0\2\4\1\40\14\0\6\4\1\72"+
    "\10\4\1\0\1\73\6\4\3\0\2\4\1\40\14\0"+
    "\17\4\1\0\1\74\6\4\2\0\4\33\1\0\10\33"+
    "\1\0\31\33\7\0\1\75\1\76\42\0\7\35\1\0"+
    "\41\35\7\12\1\0\1\12\1\77\1\44\36\12\7\13"+
    "\1\0\2\13\1\45\1\100\35\13\7\47\1\0\2\47"+
    "\1\50\1\47\1\101\34\47\1\0\2\4\1\40\14\0"+
    "\1\4\1\102\15\4\1\0\7\4\3\0\2\4\1\40"+
    "\14\0\5\4\1\103\11\4\1\0\7\4\3\0\2\4"+
    "\1\40\14\0\3\4\1\104\13\4\1\0\7\4\3\0"+
    "\2\4\1\40\14\0\16\4\1\105\1\0\7\4\3\0"+
    "\2\4\1\40\14\0\1\4\1\106\15\4\1\0\7\4"+
    "\3\0\2\4\1\40\14\0\10\4\1\107\6\4\1\0"+
    "\7\4\3\0\2\4\1\40\14\0\3\4\1\110\7\4"+
    "\1\111\1\4\1\112\1\4\1\0\7\4\3\0\2\4"+
    "\1\40\14\0\3\4\1\113\13\4\1\0\7\4\3\0"+
    "\2\4\1\40\14\0\1\4\1\114\2\4\1\115\6\4"+
    "\1\116\3\4\1\0\7\4\3\0\2\4\1\40\14\0"+
    "\5\4\1\117\11\4\1\0\7\4\3\0\2\4\1\40"+
    "\14\0\3\4\1\120\13\4\1\0\7\4\3\0\2\4"+
    "\1\40\14\0\3\4\1\121\13\4\1\0\7\4\3\0"+
    "\2\4\1\40\14\0\4\4\1\122\12\4\1\0\7\4"+
    "\3\0\2\4\1\40\14\0\10\4\1\123\6\4\1\0"+
    "\7\4\3\0\2\4\1\40\14\0\16\4\1\124\1\0"+
    "\7\4\3\0\2\4\1\40\14\0\6\4\1\125\10\4"+
    "\1\0\7\4\3\0\2\4\1\40\14\0\15\4\1\126"+
    "\1\4\1\0\7\4\3\0\2\4\1\40\14\0\3\4"+
    "\1\127\13\4\1\0\7\4\3\0\2\4\1\40\14\0"+
    "\6\4\1\130\10\4\1\0\7\4\3\0\2\4\1\40"+
    "\14\0\2\4\1\131\5\4\1\132\6\4\1\0\7\4"+
    "\3\0\2\4\1\40\14\0\6\4\1\133\10\4\1\0"+
    "\7\4\3\0\2\4\1\40\14\0\17\4\1\0\4\4"+
    "\1\134\2\4\3\0\2\4\1\40\14\0\4\4\1\135"+
    "\1\4\1\135\10\4\1\0\7\4\3\0\2\4\1\40"+
    "\14\0\4\4\1\104\12\4\1\0\7\4\3\0\2\4"+
    "\1\40\14\0\10\4\1\136\6\4\1\0\7\4\3\0"+
    "\2\4\1\40\14\0\1\4\1\137\15\4\1\0\7\4"+
    "\3\0\2\4\1\40\14\0\16\4\1\140\1\0\7\4"+
    "\3\0\2\4\1\40\14\0\4\4\1\141\12\4\1\0"+
    "\7\4\3\0\2\4\1\40\14\0\4\4\1\135\12\4"+
    "\1\0\7\4\3\0\2\4\1\40\14\0\17\4\1\0"+
    "\6\4\1\104\3\0\2\4\1\40\14\0\1\4\1\114"+
    "\2\4\1\115\12\4\1\0\7\4\3\0\2\4\1\40"+
    "\14\0\5\4\1\142\11\4\1\0\7\4\3\0\2\4"+
    "\1\40\14\0\6\4\1\143\10\4\1\0\7\4\3\0"+
    "\2\4\1\40\14\0\16\4\1\104\1\0\7\4\3\0"+
    "\2\4\1\40\14\0\15\4\1\144\1\4\1\0\7\4"+
    "\3\0\2\4\1\40\14\0\4\4\1\145\12\4\1\0"+
    "\7\4\3\0\2\4\1\40\14\0\1\4\1\146\15\4"+
    "\1\0\7\4\3\0\2\4\1\40\14\0\17\4\1\0"+
    "\1\4\1\74\5\4\3\0\2\4\1\40\14\0\1\4"+
    "\1\147\15\4\1\0\7\4\3\0\2\4\1\40\14\0"+
    "\1\4\1\150\15\4\1\0\7\4\3\0\2\4\1\40"+
    "\14\0\13\4\1\104\3\4\1\0\7\4\3\0\2\4"+
    "\1\40\14\0\3\4\1\151\13\4\1\0\7\4\3\0"+
    "\2\4\1\40\14\0\11\4\1\63\5\4\1\0\7\4"+
    "\3\0\2\4\1\40\14\0\13\4\1\107\3\4\1\0"+
    "\7\4\3\0\2\4\1\40\14\0\10\4\1\152\6\4"+
    "\1\0\7\4\3\0\2\4\1\40\14\0\5\4\1\104"+
    "\11\4\1\0\7\4\3\0\2\4\1\40\14\0\16\4"+
    "\1\153\1\0\7\4\3\0\2\4\1\40\14\0\10\4"+
    "\1\154\6\4\1\0\7\4\3\0\2\4\1\40\14\0"+
    "\4\4\1\155\12\4\1\0\7\4\3\0\2\4\1\40"+
    "\14\0\1\156\16\4\1\0\7\4\3\0\2\4\1\40"+
    "\14\0\6\4\1\157\10\4\1\0\7\4\3\0\2\4"+
    "\1\40\14\0\2\4\1\104\14\4\1\0\7\4\3\0"+
    "\2\4\1\40\14\0\15\4\1\104\1\4\1\0\7\4"+
    "\3\0\2\4\1\40\14\0\13\4\1\160\3\4\1\0"+
    "\7\4\3\0\2\4\1\40\14\0\6\4\1\54\10\4"+
    "\1\0\7\4\3\0\2\4\1\40\14\0\17\4\1\0"+
    "\3\4\1\161\3\4\3\0\2\4\1\40\14\0\10\4"+
    "\1\104\6\4\1\0\7\4\3\0\2\4\1\40\14\0"+
    "\4\4\1\117\12\4\1\0\7\4\3\0\2\4\1\40"+
    "\14\0\11\4\1\162\5\4\1\0\7\4\3\0\2\4"+
    "\1\40\14\0\17\4\1\0\2\4\1\163\4\4\3\0"+
    "\2\4\1\40\14\0\16\4\1\164\1\0\7\4\3\0"+
    "\2\4\1\40\14\0\3\4\1\165\13\4\1\0\7\4"+
    "\3\0\2\4\1\40\14\0\17\4\1\0\3\4\1\166"+
    "\3\4\3\0\2\4\1\40\14\0\7\4\1\104\7\4"+
    "\1\0\7\4\3\0\2\4\1\40\14\0\1\167\16\4"+
    "\1\0\7\4\3\0\2\4\1\40\14\0\1\170\16\4"+
    "\1\0\7\4\3\0\2\4\1\40\14\0\12\4\1\171"+
    "\4\4\1\0\7\4\3\0\2\4\1\40\14\0\17\4"+
    "\1\0\1\170\6\4\3\0\2\4\1\40\14\0\3\4"+
    "\1\172\13\4\1\0\7\4\3\0\2\4\1\40\14\0"+
    "\17\4\1\173\7\4\3\0\2\4\1\40\14\0\17\4"+
    "\1\0\4\4\1\104\2\4\3\0\2\4\1\40\14\0"+
    "\14\4\1\107\2\4\1\0\7\4\3\0\2\4\1\40"+
    "\14\0\3\4\1\174\13\4\1\0\7\4\3\0\2\4"+
    "\1\40\14\0\10\4\1\121\6\4\1\0\7\4\3\0"+
    "\2\4\1\40\14\0\6\4\1\175\10\4\1\0\7\4"+
    "\42\0\1\176\11\0\2\4\1\40\14\0\1\4\1\104"+
    "\15\4\1\0\7\4\3\0\2\4\1\40\14\0\13\4"+
    "\1\177\3\4\1\0\7\4\33\0\1\200\20\0\2\4"+
    "\1\40\14\0\17\4\1\0\1\4\1\104\5\4\40\0"+
    "\1\201\12\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4551];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\11\4\1\1\11\5\1\1\11\17\1\6\11"+
    "\2\1\1\11\2\0\1\11\23\1\2\11\74\1\1\0"+
    "\2\1\1\0\1\1\1\0\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[129];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /* user code: */

	private Stack varDepths;


	/**
	 * Constructor.  This must be here because JFlex does not generate a
	 * no-parameter constructor.
	 */
	public MakefileTokenMaker() {
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 */
	private void addToken(int tokenType) {
		addToken(zzStartRead, zzMarkedPos-1, tokenType);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 */
	private void addToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param array The character array.
	 * @param start The starting offset in the array.
	 * @param end The ending offset in the array.
	 * @param tokenType The token's type.
	 * @param startOffset The offset in the document at which this token
	 *                    occurs.
	 */
	public void addToken(char[] array, int start, int end, int tokenType, int startOffset) {
		super.addToken(array, start,end, tokenType, startOffset);
		zzStartRead = zzMarkedPos;
	}


	/**
	 * Returns the text to place at the beginning and end of a
	 * line to "comment" it in a this programming language.
	 *
	 * @return The start and end strings to add to a line to "comment"
	 *         it out.
	 */
	public String[] getLineCommentStartAndEnd() {
		return new String[] { "#", null };
	}


	/**
	 * Returns whether tokens of the specified type should have "mark
	 * occurrences" enabled for the current programming language.
	 *
	 * @param type The token type.
	 * @return Whether tokens of this type should have "mark occurrences"
	 *         enabled.
	 */
	public boolean getMarkOccurrencesOfTokenType(int type) {
		return type==Token.IDENTIFIER || type==Token.VARIABLE;
	}


	/**
	 * Returns the first token in the linked list of tokens generated
	 * from <code>text</code>.  This method must be implemented by
	 * subclasses so they can correctly implement syntax highlighting.
	 *
	 * @param text The text from which to get tokens.
	 * @param initialTokenType The token type we should start with.
	 * @param startOffset The offset into the document at which
	 *        <code>text</code> starts.
	 * @return The first <code>Token</code> in a linked list representing
	 *         the syntax highlighted text.
	 */
	public Token getTokenList(Segment text, int initialTokenType, int startOffset) {

		resetTokenList();
		this.offsetShift = -text.offset + startOffset;

		s = text;
		try {
			yyreset(zzReader);
			yybegin(Token.NULL);
			return yylex();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return new DefaultToken();
		}

	}


	/**
	 * Refills the input buffer.
	 *
	 * @return      <code>true</code> if EOF was reached, otherwise
	 *              <code>false</code>.
	 * @exception   IOException  if any I/O-Error occurs.
	 */
	private boolean zzRefill() throws java.io.IOException {
		return zzCurrentPos>=s.offset+s.count;
	}


	/**
	 * Resets the scanner to read from a new input stream.
	 * Does not close the old reader.
	 *
	 * All internal variables are reset, the old input stream 
	 * <b>cannot</b> be reused (internal buffer is discarded and lost).
	 * Lexical state is set to <tt>YY_INITIAL</tt>.
	 *
	 * @param reader   the new input stream 
	 */
	public final void yyreset(java.io.Reader reader) throws java.io.IOException {
		// 's' has been updated.
		zzBuffer = s.array;
		/*
		 * We replaced the line below with the two below it because zzRefill
		 * no longer "refills" the buffer (since the way we do it, it's always
		 * "full" the first time through, since it points to the segment's
		 * array).  So, we assign zzEndRead here.
		 */
		//zzStartRead = zzEndRead = s.offset;
		zzStartRead = s.offset;
		zzEndRead = zzStartRead + s.count - 1;
		zzCurrentPos = zzMarkedPos = s.offset;
		zzLexicalState = YYINITIAL;
		zzReader = reader;
		zzAtEOF  = false;
	}




  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public MakefileTokenMaker(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public MakefileTokenMaker(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 126) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public org.fife.ui.rsyntaxtextarea.Token yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = zzLexicalState;


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 13: 
          { addToken(Token.PREPROCESSOR);
          }
        case 22: break;
        case 3: 
          { addNullToken(); return firstToken;
          }
        case 23: break;
        case 16: 
          { addToken(Token.LITERAL_CHAR);
          }
        case 24: break;
        case 15: 
          { if (varDepths==null) { varDepths = new Stack(); } else { varDepths.clear(); } varDepths.push(Boolean.FALSE); start = zzMarkedPos-2; yybegin(VAR);
          }
        case 25: break;
        case 12: 
          { if (!varDepths.empty() && varDepths.peek()==Boolean.TRUE) {
								varDepths.pop();
								if (varDepths.empty()) {
									addToken(start,zzStartRead, Token.VARIABLE); yybegin(YYINITIAL);
								}
							}
          }
        case 26: break;
        case 11: 
          { if (!varDepths.empty() && varDepths.peek()==Boolean.FALSE) {
								varDepths.pop();
								if (varDepths.empty()) {
									addToken(start,zzStartRead, Token.VARIABLE); yybegin(YYINITIAL);
								}
							}
          }
        case 27: break;
        case 4: 
          { addToken(Token.WHITESPACE);
          }
        case 28: break;
        case 21: 
          { addToken(Token.RESERVED_WORD);
          }
        case 29: break;
        case 20: 
          { varDepths.push(Boolean.FALSE);
          }
        case 30: break;
        case 18: 
          { addToken(Token.LITERAL_BACKQUOTE);
          }
        case 31: break;
        case 19: 
          { varDepths.push(Boolean.TRUE);
          }
        case 32: break;
        case 1: 
          { addToken(Token.IDENTIFIER);
          }
        case 33: break;
        case 5: 
          { addToken(Token.ERROR_CHAR); addNullToken(); return firstToken;
          }
        case 34: break;
        case 6: 
          { addToken(Token.ERROR_STRING_DOUBLE); addNullToken(); return firstToken;
          }
        case 35: break;
        case 10: 
          { int temp1 = zzStartRead; int temp2 = zzMarkedPos; addToken(start,zzStartRead-1, Token.VARIABLE); addToken(temp1, temp2-1, Token.COMMENT_EOL); addNullToken(); return firstToken;
          }
        case 36: break;
        case 17: 
          { addToken(Token.LITERAL_STRING_DOUBLE_QUOTE);
          }
        case 37: break;
        case 14: 
          { if (varDepths==null) { varDepths = new Stack(); } else { varDepths.clear(); } varDepths.push(Boolean.TRUE); start = zzMarkedPos-2; yybegin(VAR);
          }
        case 38: break;
        case 7: 
          { addToken(Token.COMMENT_EOL); addNullToken(); return firstToken;
          }
        case 39: break;
        case 2: 
          { addToken(Token.LITERAL_NUMBER_DECIMAL_INT);
          }
        case 40: break;
        case 8: 
          { addToken(Token.OPERATOR);
          }
        case 41: break;
        case 9: 
          { 
          }
        case 42: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            switch (zzLexicalState) {
            case VAR: {
              addToken(start,zzStartRead-1, Token.VARIABLE); addNullToken(); return firstToken;
            }
            case 130: break;
            case YYINITIAL: {
              addNullToken(); return firstToken;
            }
            case 131: break;
            default:
            return null;
            }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
