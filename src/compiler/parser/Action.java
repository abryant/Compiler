package compiler.parser;

import java.util.Deque;

/*
 * Created on 21 Jun 2010
 */

/**
 * Represents an action in the parse table.
 *
 * @author Anthony Bryant
 */
public abstract class Action
{

  /**
   * Returns whether this action is an accept action.
   * This defaults to false in the parent class, and is overridden by AcceptAction to return true.
   * @return true if this action is an accept action, false otherwise.
   */
  public boolean isAccept()
  {
    return false;
  }

  /**
   * Performs this action and returns whether the token was used.
   * @param token - the current token, to add to the token stack
   * @param stateStack - the stack of states
   * @param tokenStack - the stack of tokens (including terminals and non-terminals)
   * @return true if the token was added to the token stack (or used in some other way), false otherwise
   */
  public abstract boolean perform(Token token, Deque<IState> stateStack, Deque<Token> tokenStack);
}
