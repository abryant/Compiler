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
 * 
 */
public abstract class Tokenizer
{
  
  private LinkedList<Token> tokens;
  private boolean finished = false;
  
  /**
   * Creates a new Tokenizer.
   */
  public Tokenizer()
  {
    tokens = new LinkedList<Token>();
  }
  
  /**
   * Generates a token to be used by the parser.
   * @return the generated token
   */
  protected abstract Token generateToken();
  
  /**
   * Generates the next token and adds it to the list.
   */
  private final void readNextToken()
  {
    Token generated = generateToken();
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
   */
  public final boolean isFinished()
  {
    if (!finished && tokens.isEmpty())
    {
      readNextToken();
    }
    return finished && tokens.isEmpty();
  }
  
  /**
   * @return the next token to be parsed, or null if there are no more tokens.
   */
  public final Token next()
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
   */
  public final Token lookahead(int offset)
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
