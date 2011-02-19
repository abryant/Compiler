package compiler.language.conceptual;

import compiler.language.ast.ParseInfo;

/*
 * Created on 16 Feb 2011
 */

/**
 * @author Anthony Bryant
 */
public class NameConflictException extends Exception
{

  private static final long serialVersionUID = 1L;

  private ParseInfo[] parseInfo;

  /**
   * Creates a new blank NameConflictException.
   * @param parseInfo - the parsing information associated with the name conflict
   */
  public NameConflictException(ParseInfo... parseInfo)
  {
    this.parseInfo = parseInfo;
  }

  /**
   * Creates a new NameConflictException with the specified message and array of ParseInfo objects.
   * @param message - the message for this exception
   * @param parseInfo - the parsing information associated with the name conflict
   */
  public NameConflictException(String message, ParseInfo... parseInfo)
  {
    super(message);
    this.parseInfo = parseInfo;
  }

  /**
   * @return the parseInfo
   */
  public ParseInfo[] getParseInfo()
  {
    return parseInfo;
  }

}
