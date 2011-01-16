package compiler.language.translator.conceptual;

/*
 * Created on 23 Dec 2010
 */

/**
 * @author Anthony Bryant
 */
public class ScopeException extends Exception
{

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new ScopeException.
   */
  public ScopeException()
  {
    // do nothing
  }

  /**
   * Creates a new ScopeException with the specified message.
   * @param message - the message for this exception
   */
  public ScopeException(String message)
  {
    super(message);
  }
}
