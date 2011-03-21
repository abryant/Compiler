package compiler.language.conceptual;

import compiler.language.ast.ParseInfo;

/*
 * Created on 16 Feb 2011
 */

/**
 * An exception that is thrown whenever a name conflict is detected.
 * This happens when two fields (or packages, classes etc) are found to have the same name.
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
