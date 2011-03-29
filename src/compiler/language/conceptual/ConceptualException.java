package compiler.language.conceptual;

import compiler.language.ast.ParseInfo;

/*
 * Created on 23 Dec 2010
 */

/**
 * An exception that is thrown if something is conceptually wrong with the program. For example if an access specifier or modifier is invalid.
 * TODO: eventually, everywhere throwing this should be replaced by printing an error message in a standard way
 * @author Anthony Bryant
 */
public class ConceptualException extends Exception
{
  private static final long serialVersionUID = 1L;

  private ParseInfo[] parseInfo;

  /**
   * Creates a new ConceptualException with the specified message and ParseInfo
   * @param message - the message for this ConceptualException
   * @param parseInfo - all of the parsing information specifying where the mistake occurred (this can contain multiple ParseInfo objects)
   */
  public ConceptualException(String message, ParseInfo... parseInfo)
  {
    super(message);
    this.parseInfo = parseInfo;
  }

  /**
   * Creates a new ConceptualException with the specified message and ParseInfo
   * @param message - the message for this ConceptualException
   * @param cause - the cause of this exception
   * @param parseInfo - all of the parsing information specifying where the mistake occurred (this can contain multiple ParseInfo objects)
   */
  public ConceptualException(String message, Throwable cause, ParseInfo... parseInfo)
  {
    super(message, cause);
    this.parseInfo = parseInfo;
  }

  /**
   * @return all of the parseInfo objects related to this exception
   */
  public ParseInfo[] getParseInfo()
  {
    return parseInfo;
  }

}
