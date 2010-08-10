package compiler.parser;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BadTokenException extends Exception
{

  private Token badToken;
  private Object[] expectedTokenTypes;

  /**
   * Creates a BadTokenException with the specified token and expected token types.
   * @param badToken - the token that was found
   * @param expectedTokenTypes - the list of types of token that were expected (this could include null for the end of file token type)
   */
  public BadTokenException(Token badToken, Object[] expectedTokenTypes)
  {
    this.badToken = badToken;
    this.expectedTokenTypes = expectedTokenTypes;
  }

  /**
   * @return the badToken
   */
  public Token getBadToken()
  {
    return badToken;
  }

  /**
   * @return the expectedTokenTypes
   */
  public Object[] getExpectedTokenTypes()
  {
    return expectedTokenTypes;
  }

}
