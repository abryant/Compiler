package compiler.parser;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BadTokenException extends Exception
{
  private static final long serialVersionUID = 1L;

  @SuppressWarnings("rawtypes") // generic exceptions are not allowed, so we have to suppress this
  private Token badToken;
  private Object[] expectedTokenTypes = null;

  /**
   * Creates a BadTokenException with the specified token.
   * @param <T> - the enum type that holds all possible values for the token type
   * @param badToken - the token that was found
   */
  public <T extends Enum<T>> BadTokenException(Token<T> badToken)
  {
    this.badToken = badToken;
  }

  /**
   * Creates a BadTokenException with the specified token and expected token types.
   * @param <T> - the enum type that holds all possible values for the token type
   * @param badToken - the token that was found
   * @param expectedTokenTypes - the list of types of token that were expected (this could include null for the end of file token type)
   */
  public <T extends Enum<T>> BadTokenException(Token<T> badToken, Object[] expectedTokenTypes)
  {
    this.badToken = badToken;
    this.expectedTokenTypes = expectedTokenTypes;
  }

  /**
   * @param <T> - the enum type that holds all possible values for the token type
   * @return the badToken
   */
  @SuppressWarnings("unchecked")
  public <T extends Enum<T>> Token<T> getBadToken()
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
