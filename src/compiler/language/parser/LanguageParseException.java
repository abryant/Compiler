package compiler.language.parser;

import parser.ParseException;

import compiler.language.LexicalPhrase;

/*
 * Created on 10 Aug 2010
 */

/**
 * An exception which represents a parse error in a source file of this language.
 * @author Anthony Bryant
 */
public class LanguageParseException extends ParseException
{

  private static final long serialVersionUID = 1L;

  private LexicalPhrase lexicalPhrase;

  /**
   * Creates a new LanguageParseException with the specified message and LexicalPhrase
   * @param message - the message to indicate the cause of the parsing error
   * @param lexicalPhrase - the LexicalPhrase to indicate where the parsing error took place
   */
  public LanguageParseException(String message, LexicalPhrase lexicalPhrase)
  {
    super(message);
    this.lexicalPhrase = lexicalPhrase;
  }

  /**
   * Creates a new LanguageParseException with the specified message, cause, and LexicalPhrase
   * @param message - the message to indicate the cause of the parsing error
   * @param cause - the original cause of this LanguageParseException
   * @param lexicalPhrase - the LexicalPhrase to indicate where the parsing error took place
   */
  public LanguageParseException(String message, Throwable cause, LexicalPhrase lexicalPhrase)
  {
    super(message, cause);
    this.lexicalPhrase = lexicalPhrase;
  }

  /**
   * @return the lexicalPhrase
   */
  public LexicalPhrase getLexicalPhrase()
  {
    return lexicalPhrase;
  }


}
