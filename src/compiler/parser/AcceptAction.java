package compiler.parser;

import java.util.Deque;

/*
 * Created on 21 Jun 2010
 */

/**
 * Represents an accept action in the parser
 *
 * @author Anthony Bryant
 */
public class AcceptAction extends Action
{
  /**
   * @see compiler.parser.Action#isAccept()
   */
  @Override
  public boolean isAccept()
  {
    return true;
  }

  /**
   * @see compiler.parser.Action#perform(compiler.parser.Token, java.util.Deque, java.util.Deque)
   */
  @Override
  public boolean perform(Token token, Deque<State> stateStack, Deque<Token> tokenStack)
  {
    // an accept action does nothing
    return false;
  }
}
