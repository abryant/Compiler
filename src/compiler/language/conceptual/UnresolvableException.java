package compiler.language.conceptual;

import compiler.language.ast.ParseInfo;

/*
 * Created on 29 Mar 2011
 */

/**
 * An exception that is thrown when resolve() is called on an object which does not have enough data to know whether or not it can resolve a name.
 * If this is thrown, it indicates that an object needs further initialisation.
 * @author Anthony Bryant
 */
public class UnresolvableException extends Exception
{
  private static final long serialVersionUID = 1L;

  private ParseInfo parseInfo;

  /**
   * Creates a new UnresolvableException with the specified message.
   * @param message - the message to embed in this exception
   * @param parseInfo - the ParseInfo of the name that could not be resolved
   */
  public UnresolvableException(String message, ParseInfo parseInfo)
  {
    super(message);
    this.parseInfo = parseInfo;
  }

  /**
   * @return the parseInfo of the unresolved name
   */
  public ParseInfo getParseInfo()
  {
    return parseInfo;
  }

}
