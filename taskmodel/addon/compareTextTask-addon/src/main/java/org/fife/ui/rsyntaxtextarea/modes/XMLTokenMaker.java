/* The following code was generated by JFlex 1.4.1 on 2/17/12 7:12 PM */

/*
 * 01/24/2005
 *
 * XMLTokenMaker.java - Generates tokens for XML syntax highlighting.
 * 
 * This library is distributed under a modified BSD license.  See the included
 * RSyntaxTextArea.License.txt file for details.
 */
package org.fife.ui.rsyntaxtextarea.modes;

import java.io.*;
import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.*;


/**
 * Scanner for XML.
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
 *   <li>The generated <code>XMLTokenMaker.java</code> file will contain two
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

public class XMLTokenMaker extends AbstractMarkupTokenMaker {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** lexical states */
  public static final int INTAG = 4;
  public static final int DTD = 3;
  public static final int INATTR_DOUBLE = 5;
  public static final int YYINITIAL = 0;
  public static final int COMMENT = 1;
  public static final int CDATA = 7;
  public static final int INATTR_SINGLE = 6;
  public static final int PI = 2;

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\6\1\4\1\0\1\3\23\0\1\6\1\12\1\11\1\22"+
    "\1\30\1\22\1\7\1\25\5\22\1\2\1\42\1\24\12\27\1\23"+
    "\1\10\1\5\1\44\1\21\1\43\1\22\1\16\1\26\1\14\1\15"+
    "\17\26\1\17\6\26\1\13\1\0\1\20\1\0\1\1\1\0\4\26"+
    "\1\40\1\35\1\26\1\31\1\36\2\26\1\37\3\26\1\33\2\26"+
    "\1\34\1\32\2\26\1\41\3\26\3\0\1\22\uff81\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\5\0\2\1\1\0\2\2\1\3\1\4\1\5\1\6"+
    "\2\1\1\7\4\1\1\10\2\1\1\11\1\1\1\12"+
    "\1\13\1\14\2\15\1\16\1\17\1\20\1\21\1\22"+
    "\1\1\1\23\3\1\1\24\1\25\1\4\1\26\1\6"+
    "\5\0\1\27\4\0\1\30\1\31\5\0\1\32\1\33"+
    "\3\0\1\34\1\35\6\0\1\36";

  private static int [] zzUnpackAction() {
    int [] result = new int[77];
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
    "\0\0\0\45\0\112\0\157\0\224\0\271\0\336\0\u0103"+
    "\0\u0128\0\u014d\0\u0172\0\u0197\0\u01bc\0\u01e1\0\u0206\0\u022b"+
    "\0\u0172\0\u0250\0\u0275\0\u029a\0\u02bf\0\u0172\0\u02e4\0\u0309"+
    "\0\u0172\0\u032e\0\u0172\0\u0172\0\u0172\0\u0353\0\u0378\0\u0172"+
    "\0\u0172\0\u039d\0\u0172\0\u0172\0\u03c2\0\u0172\0\u03e7\0\u040c"+
    "\0\u0431\0\u0456\0\u047b\0\u04a0\0\u0172\0\u0172\0\u04c5\0\u04ea"+
    "\0\u050f\0\u0534\0\u0559\0\u0172\0\u057e\0\u05a3\0\u05c8\0\u05ed"+
    "\0\u0612\0\u0172\0\u0637\0\u065c\0\u0681\0\u06a6\0\u06cb\0\u0172"+
    "\0\u0172\0\u06f0\0\u0715\0\u073a\0\u075f\0\u0172\0\u0784\0\u07a9"+
    "\0\u075f\0\u07ce\0\u07f3\0\u0818\0\u0172";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[77];
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
    "\3\11\1\12\1\13\1\14\1\15\1\16\35\11\2\17"+
    "\1\20\1\17\1\21\24\17\1\22\3\17\1\23\3\17"+
    "\1\24\3\17\4\25\1\26\36\25\1\27\1\25\4\30"+
    "\1\31\1\32\5\30\1\33\4\30\1\34\1\35\23\30"+
    "\3\36\1\37\1\0\1\36\1\15\2\36\1\40\7\36"+
    "\1\41\2\36\1\42\1\43\16\36\1\44\11\45\1\46"+
    "\33\45\25\47\1\46\17\47\20\50\1\51\24\50\4\11"+
    "\4\0\40\11\1\12\2\0\1\15\1\0\35\11\46\0"+
    "\1\52\10\0\1\53\1\0\4\52\3\0\1\52\1\54"+
    "\1\0\1\52\2\0\11\52\1\0\1\55\4\0\1\15"+
    "\2\0\1\15\36\0\6\16\1\0\1\16\1\56\34\16"+
    "\2\17\1\0\1\17\1\0\24\17\1\0\3\17\1\0"+
    "\3\17\1\0\3\17\2\0\1\57\74\0\1\60\44\0"+
    "\1\61\3\0\1\62\47\0\1\63\3\0\4\25\1\0"+
    "\36\25\1\0\1\25\21\0\1\64\23\0\4\30\2\0"+
    "\5\30\1\0\4\30\2\0\23\30\12\0\1\65\32\0"+
    "\4\36\1\0\1\36\1\0\2\36\1\0\7\36\1\0"+
    "\2\36\2\0\16\36\1\0\3\36\1\37\1\0\1\36"+
    "\1\15\2\36\1\0\7\36\1\0\2\36\2\0\16\36"+
    "\22\0\1\41\23\0\11\45\1\0\33\45\25\47\1\0"+
    "\17\47\20\50\1\0\24\50\20\0\1\66\25\0\2\52"+
    "\11\0\4\52\3\0\1\52\2\0\2\52\1\0\12\52"+
    "\4\0\1\67\10\0\1\70\32\0\1\71\12\0\4\71"+
    "\3\0\1\71\2\0\1\71\2\0\11\71\24\0\1\72"+
    "\55\0\1\73\45\0\1\74\50\0\1\75\46\0\1\76"+
    "\5\0\1\77\63\0\1\100\25\0\1\101\56\0\1\102"+
    "\31\0\2\71\11\0\4\71\3\0\1\71\2\0\2\71"+
    "\1\0\12\71\35\0\1\103\34\0\1\104\61\0\1\74"+
    "\46\0\1\105\4\0\1\106\57\0\1\107\52\0\1\104"+
    "\10\0\1\74\34\0\1\110\21\0\2\111\4\0\2\111"+
    "\1\0\2\111\4\105\1\111\1\0\2\111\1\105\1\111"+
    "\14\105\3\111\16\0\1\112\52\0\1\105\37\0\1\113"+
    "\43\0\1\114\41\0\1\115\31\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[2109];
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
    "\5\0\2\1\1\0\2\1\1\11\5\1\1\11\4\1"+
    "\1\11\2\1\1\11\1\1\3\11\2\1\2\11\1\1"+
    "\2\11\1\1\1\11\6\1\2\11\5\0\1\11\4\0"+
    "\1\1\1\11\5\0\2\11\3\0\1\1\1\11\6\0"+
    "\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[77];
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

	/**
	 * Type specific to XMLTokenMaker denoting a line ending with an unclosed
	 * double-quote attribute.
	 */
	public static final int INTERNAL_ATTR_DOUBLE			= -1;


	/**
	 * Type specific to XMLTokenMaker denoting a line ending with an unclosed
	 * single-quote attribute.
	 */
	public static final int INTERNAL_ATTR_SINGLE			= -2;


	/**
	 * Token type specific to XMLTokenMaker denoting a line ending with an
	 * unclosed XML tag; thus a new line is beginning still inside of the tag.
	 */
	public static final int INTERNAL_INTAG					= -3;

	/**
	 * Token type specific to XMLTokenMaker denoting a line ending with an
	 * unclosed DOCTYPE element.
	 */
	public static final int INTERNAL_DTD					= -4;

	/**
	 * Token type specific to XMLTokenMaker denoting a line ending with an
	 * unclosed, locally-defined DTD in a DOCTYPE element.
	 */
	public static final int INTERNAL_DTD_INTERNAL			= -5;

	/**
	 * Token type specific to XMLTokenMaker denoting a line ending with an
	 * unclosed comment.  The state to return to when this comment ends is
	 * embedded in the token type as well.
	 */
	public static final int INTERNAL_IN_XML_COMMENT			= -(1<<11);

	/**
	 * Whether closing markup tags are automatically completed for HTML.
	 */
	private static boolean completeCloseTags;

	/**
	 * Whether the DTD we're currently in is a locally-defined one.  This
	 * field is only valid when in a DOCTYPE element (the <DTD> state).
	 */
	private boolean inInternalDtd;

	/**
	 * The state we were in prior to the current one.  This is used to know
	 * what state to resume after an MLC ends.
	 */
	private int prevState;


	/**
	 * Constructor.  This must be here because JFlex does not generate a
	 * no-parameter constructor.
	 */
	public XMLTokenMaker() {
	}


	static {
		completeCloseTags = true;
	}


	/**
	 * Adds the token specified to the current linked list of tokens as an
	 * "end token;" that is, at <code>zzMarkedPos</code>.
	 *
	 * @param tokenType The token's type.
	 */
	private void addEndToken(int tokenType) {
		addToken(zzMarkedPos,zzMarkedPos, tokenType);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 * @see #addToken(int, int, int)
	 */
	private void addHyperlinkToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so, true);
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
	@Override
	public void addToken(char[] array, int start, int end, int tokenType, int startOffset) {
		super.addToken(array, start,end, tokenType, startOffset);
		zzStartRead = zzMarkedPos;
	}


	/**
	 * Returns whether markup close tags should be completed.  For XML, the
	 * default value is <code>true</code>.
	 *
	 * @return Whether closing markup tags are completed.
	 * @see #setCompleteCloseTags(boolean)
	 */
	@Override
	public boolean getCompleteCloseTags() {
		return completeCloseTags;
	}


	/**
	 * Static version of {@link #getCompleteCloseTags()}.  This hack is
	 * unfortunately needed for applications to be able to query this value
	 * without instantiating this class.
	 *
	 * @return Whether closing markup tags are completed.
	 * @see #setCompleteCloseTags(boolean)
	 */
	public static boolean getCompleteCloseMarkupTags() {
		return completeCloseTags;
	}


	/**
	 * Always returns <tt>false</tt>, as you never want "mark occurrences"
	 * working in XML files.
	 *
	 * @param type The token type.
	 * @return Whether tokens of this type should have "mark occurrences"
	 *         enabled.
	 */
	@Override
	public boolean getMarkOccurrencesOfTokenType(int type) {
		return false;
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
	@Override
	public Token getTokenList(Segment text, int initialTokenType, int startOffset) {

		resetTokenList();
		this.offsetShift = -text.offset + startOffset;
		prevState = YYINITIAL;
		inInternalDtd = false;

		// Start off in the proper state.
		int state = TokenTypes.NULL;
		switch (initialTokenType) {
			case TokenTypes.COMMENT_MULTILINE:
				state = COMMENT;
				break;
			case INTERNAL_DTD:
				state = DTD;
				break;
			case INTERNAL_DTD_INTERNAL:
				state = DTD;
				inInternalDtd = true;
				break;
			case INTERNAL_ATTR_DOUBLE:
				state = INATTR_DOUBLE;
				break;
			case INTERNAL_ATTR_SINGLE:
				state = INATTR_SINGLE;
				break;
			case TokenTypes.MARKUP_PROCESSING_INSTRUCTION:
				state = PI;
				break;
			case INTERNAL_INTAG:
				state = INTAG;
				break;
			case TokenTypes.MARKUP_CDATA:
				state = CDATA;
				break;
			default:
				if (initialTokenType<-1024) { // INTERNAL_IN_XML_COMMENT - prevState
					int main = -(-initialTokenType & 0xffffff00);
					switch (main) {
						default: // Should never happen
						case INTERNAL_IN_XML_COMMENT:
							state = COMMENT;
							break;
					}
					prevState = -initialTokenType&0xff;
				}
				else { // Shouldn't happen
					state = TokenTypes.NULL;
				}
		}

		start = text.offset;
		s = text;
		try {
			yyreset(zzReader);
			yybegin(state);
			return yylex();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return new DefaultToken();
		}

	}


	/**
	 * Sets whether markup close tags should be completed.
	 *
	 * @param complete Whether closing markup tags are completed.
	 * @see #getCompleteCloseTags()
	 */
	public static void setCompleteCloseTags(boolean complete) {
		completeCloseTags = complete;
	}


	/**
	 * Refills the input buffer.
	 *
	 * @return      <code>true</code> if EOF was reached, otherwise
	 *              <code>false</code>.
	 */
	private boolean zzRefill() {
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
	public final void yyreset(java.io.Reader reader) {
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
  public XMLTokenMaker(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public XMLTokenMaker(java.io.InputStream in) {
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
    while (i < 116) {
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
  @Override
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
        case 19: 
          { yybegin(INTAG); addToken(start,zzStartRead, TokenTypes.MARKUP_TAG_ATTRIBUTE_VALUE);
          }
        case 31: break;
        case 3: 
          { addNullToken(); return firstToken;
          }
        case 32: break;
        case 11: 
          { inInternalDtd = false;
          }
        case 33: break;
        case 4: 
          { addToken(TokenTypes.MARKUP_TAG_DELIMITER); yybegin(INTAG);
          }
        case 34: break;
        case 24: 
          { int count = yylength();
									addToken(zzStartRead,zzStartRead+1, TokenTypes.MARKUP_TAG_DELIMITER);
									addToken(zzMarkedPos-(count-2), zzMarkedPos-1, TokenTypes.MARKUP_TAG_NAME);
									yybegin(INTAG);
          }
        case 35: break;
        case 16: 
          { addToken(TokenTypes.MARKUP_TAG_DELIMITER); /* Not valid but we'll still accept it */
          }
        case 36: break;
        case 5: 
          { addToken(TokenTypes.WHITESPACE);
          }
        case 37: break;
        case 27: 
          { start = zzStartRead; prevState = zzLexicalState; yybegin(COMMENT);
          }
        case 38: break;
        case 29: 
          { int temp = zzStartRead; addToken(start,zzStartRead-1, TokenTypes.FUNCTION); start = temp; prevState = zzLexicalState; yybegin(COMMENT);
          }
        case 39: break;
        case 25: 
          { int temp = zzMarkedPos; addToken(start,zzStartRead+2, TokenTypes.COMMENT_MULTILINE); start = temp; yybegin(prevState);
          }
        case 40: break;
        case 2: 
          { addToken(TokenTypes.IDENTIFIER);
          }
        case 41: break;
        case 9: 
          { addToken(start,zzStartRead-1, TokenTypes.FUNCTION); addEndToken(inInternalDtd ? INTERNAL_DTD_INTERNAL : INTERNAL_DTD); return firstToken;
          }
        case 42: break;
        case 10: 
          { inInternalDtd = true;
          }
        case 43: break;
        case 30: 
          { addToken(TokenTypes.DATA_TYPE); start = zzMarkedPos; yybegin(CDATA);
          }
        case 44: break;
        case 6: 
          { addToken(TokenTypes.DATA_TYPE);
          }
        case 45: break;
        case 23: 
          { yybegin(YYINITIAL); addToken(start,zzStartRead+1, TokenTypes.MARKUP_PROCESSING_INSTRUCTION);
          }
        case 46: break;
        case 21: 
          { start = zzMarkedPos-2; inInternalDtd = false; yybegin(DTD);
          }
        case 47: break;
        case 20: 
          { int count = yylength();
									addToken(zzStartRead,zzStartRead, TokenTypes.MARKUP_TAG_DELIMITER);
									addToken(zzMarkedPos-(count-1), zzMarkedPos-1, TokenTypes.MARKUP_TAG_NAME);
									yybegin(INTAG);
          }
        case 48: break;
        case 22: 
          { start = zzMarkedPos-2; yybegin(PI);
          }
        case 49: break;
        case 8: 
          { addToken(start,zzStartRead-1, TokenTypes.MARKUP_PROCESSING_INSTRUCTION); return firstToken;
          }
        case 50: break;
        case 14: 
          { start = zzMarkedPos-1; yybegin(INATTR_DOUBLE);
          }
        case 51: break;
        case 15: 
          { yybegin(YYINITIAL); addToken(TokenTypes.MARKUP_TAG_DELIMITER);
          }
        case 52: break;
        case 12: 
          { if (!inInternalDtd) { yybegin(YYINITIAL); addToken(start,zzStartRead, TokenTypes.FUNCTION); }
          }
        case 53: break;
        case 17: 
          { start = zzMarkedPos-1; yybegin(INATTR_SINGLE);
          }
        case 54: break;
        case 28: 
          { int temp=zzStartRead; addToken(start,zzStartRead-1, TokenTypes.COMMENT_MULTILINE); addHyperlinkToken(temp,zzMarkedPos-1, TokenTypes.COMMENT_MULTILINE); start = zzMarkedPos;
          }
        case 55: break;
        case 26: 
          { int temp=zzStartRead; yybegin(YYINITIAL); addToken(start,zzStartRead-1, TokenTypes.MARKUP_CDATA); addToken(temp,zzMarkedPos-1, TokenTypes.DATA_TYPE);
          }
        case 56: break;
        case 18: 
          { addToken(TokenTypes.OPERATOR);
          }
        case 57: break;
        case 7: 
          { addToken(start,zzStartRead-1, TokenTypes.COMMENT_MULTILINE); addEndToken(INTERNAL_IN_XML_COMMENT - prevState); return firstToken;
          }
        case 58: break;
        case 13: 
          { addToken(TokenTypes.MARKUP_TAG_ATTRIBUTE);
          }
        case 59: break;
        case 1: 
          { 
          }
        case 60: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            switch (zzLexicalState) {
            case INTAG: {
              addToken(start,zzStartRead-1, INTERNAL_INTAG); return firstToken;
            }
            case 78: break;
            case DTD: {
              addToken(start,zzStartRead-1, TokenTypes.FUNCTION); addEndToken(inInternalDtd ? INTERNAL_DTD_INTERNAL : INTERNAL_DTD); return firstToken;
            }
            case 79: break;
            case INATTR_DOUBLE: {
              addToken(start,zzStartRead-1, TokenTypes.MARKUP_TAG_ATTRIBUTE_VALUE); addEndToken(INTERNAL_ATTR_DOUBLE); return firstToken;
            }
            case 80: break;
            case YYINITIAL: {
              addNullToken(); return firstToken;
            }
            case 81: break;
            case COMMENT: {
              addToken(start,zzStartRead-1, TokenTypes.COMMENT_MULTILINE); addEndToken(INTERNAL_IN_XML_COMMENT - prevState); return firstToken;
            }
            case 82: break;
            case CDATA: {
              addToken(start,zzStartRead-1, TokenTypes.MARKUP_CDATA); return firstToken;
            }
            case 83: break;
            case INATTR_SINGLE: {
              addToken(start,zzStartRead-1, TokenTypes.MARKUP_TAG_ATTRIBUTE_VALUE); addEndToken(INTERNAL_ATTR_SINGLE); return firstToken;
            }
            case 84: break;
            case PI: {
              addToken(start,zzStartRead-1, TokenTypes.MARKUP_PROCESSING_INSTRUCTION); return firstToken;
            }
            case 85: break;
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
