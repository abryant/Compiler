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

  private static final long serialVersionUID = 1L;

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
  public boolean perform(Token token, Deque<State> stateStack, Deque<Token> tokenStack) throws ParseException
  {
    System.out.println("Shifting on " + (token == null ? null : token.getType())); // TODO: remove debug output
    // add the next state and the next token to their stacks
    stateStack.addFirst(state);
    tokenStack.addFirst(token);
    return true;
  }
}
