package compiler.parser;

import java.util.Deque;

/*
 * Created on 21 Jun 2010
 */

/**
 * Represents a shift action in the parser.
 *
 * @author Anthony Bryant
 */
public class ShiftAction extends Action
{
  private State state;

  /**
   * Creates a new Shift action to shift to the specified state
   * @param state - the state to shift to
   */
  public ShiftAction(State state)
  {
    this.state = state;
  }

  /**
   * @see compiler.parser.Action#perform(compiler.parser.Token, java.util.Deque, java.util.Deque)
   */
  @Override
  public boolean perform(Token token, Deque<State> stateStack, Deque<Token> tokenStack)
  {
    // add the next state and the next token to their stacks
    stateStack.addFirst(state);
    tokenStack.addFirst(token);
    return true;
  }
}
