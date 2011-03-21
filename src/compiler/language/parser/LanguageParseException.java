package compiler.language.parser;

import parser.ParseException;

import compiler.language.ast.ParseInfo;

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

  private ParseInfo parseInfo;

  /**
   * Creates a new LanguageParseException with the specified message and ParseInfo
   * @param message - the message to indicate the cause of the parsing error
   * @param parseInfo - the ParseInfo to indicate where the parsing error took place
   */
  public LanguageParseException(String message, ParseInfo parseInfo)
  {
    super(message);
    this.parseInfo = parseInfo;
  }

  /**
   * Creates a new LanguageParseException with the specified message, cause, and ParseInfo
   * @param message - the message to indicate the cause of the parsing error
   * @param cause - the original cause of this LanguageParseException
   * @param parseInfo - the ParseInfo to indicate where the parsing error took place
   */
  public LanguageParseException(String message, Throwable cause, ParseInfo parseInfo)
  {
    super(message, cause);
    this.parseInfo = parseInfo;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }


}
