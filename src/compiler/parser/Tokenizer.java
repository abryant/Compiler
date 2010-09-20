package compiler.parser;

import java.util.LinkedList;

/*
 * Created on 5 Apr 2010
 */

/**
 * An abstract tokenizer that parses some input source into tokens. Tokens can consist of any Java object and an associated type.
 * Subclasses will implement the actual tokenizing stage. This class provides a higher level interface to the parser by allowing the parser to lookahead.
 *
 * @author Anthony Bryant
 * @param <T> - the enum that holds all possible values for the token type
 *
 */
public abstract class Tokenizer<T extends Enum<T>>
{

  private LinkedList<Token<T>> tokens;
  private boolean finished = false;

  /**
   * Creates a new Tokenizer.
   */
  public Tokenizer()
  {
    tokens = new LinkedList<Token<T>>();
  }

  /**
   * Generates a token to be used by the parser.
   * @return the generated token
   * @throws ParseException - if an Exception occurs while reading the token
   */
  protected abstract Token<T> generateToken() throws ParseException;

  /**
   * Generates the next token and adds it to the list.
   * @throws ParseException - if an Exception occurs while reading the token
   */
  private final void readNextToken() throws ParseException
  {
    Token<T> generated = generateToken();
    if (generated == null)
    {
      finished = true;
    }
    else
    {
      tokens.add(generated);
    }
  }

  /**
   * @return true if there are no more tokens, false otherwise
   * @throws ParseException - if an Exception occurs while reading the next token
   */
  public final boolean isFinished() throws ParseException
  {
    if (!finished && tokens.isEmpty())
    {
      readNextToken();
    }
    return finished && tokens.isEmpty();
  }

  /**
   * @return the next token to be parsed, or null if there are no more tokens.
   * @throws ParseException - if an Exception occurs while reading the next token
   */
  public final Token<T> next() throws ParseException
  {
    if (tokens.isEmpty())
    {
      readNextToken();
    }
    // if the queue is still empty (i.e. we have reached the end of input) poll will return null
    return tokens.pollFirst();
  }

  /**
   * Looks ahead in the tokens list to find the token offset positions after the current one.
   * This means that <code>lookahead(1)</code> will return the next token, and <code>lookahead(n)</code> where n &lt; 1 will result in an {@link IllegalArgumentException}
   * @param offset - the number of tokens to look ahead
   * @return the nth token after the current one, or null if the end of input is reached before n tokens of lookahead have been reached
   * @throws ParseException - if an Exception occurs while reading the next token
   */
  public final Token<T> lookahead(int offset) throws ParseException
  {
    if (offset < 1)
    {
      throw new IllegalArgumentException("Cannot lookahead by less than 1.");
    }
    // translate the offset into the position in the list (an offset of 1 means tokens.get(0))
    offset--;
    while (offset >= tokens.size() && !finished)
    {
      readNextToken();
    }
    if (offset >= tokens.size())
    {
      return null;
    }
    return tokens.get(offset);
  }

}
