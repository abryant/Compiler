package compiler.parser;

/*
 * Created on 7 Apr 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class ParseException extends Exception
{

  /**
   * Creates a new ParseException with the specified message.
   * @param message - the message for the ParseException
   */
  public ParseException(String message)
  {
    super(message);
  }

  /**
   * Creates a new ParseException with the specified message and cause.
   * @param message - the message for the ParseException
   * @param cause - the original cause of the ParseException
   */
  public ParseException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
