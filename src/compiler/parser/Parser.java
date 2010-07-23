package compiler.parser;

import java.util.Deque;
import java.util.LinkedList;

/*
 * Created on 21 Jun 2010
 */

/**
 * Implements a parser, which parses input from a tokenizer according to a parse table (which is implemented as a set of states and their associated actions)
 *
 * @author Anthony Bryant
 */
public class Parser
{

  private State startState;
  private Tokenizer tokenizer;

  /**
   * Creates a new parser to parse the input from the specified Tokenizer, starting in the specified start state.
   * @param startState - the state to start the parser in
   * @param tokenizer - the tokenizer to read input from
   */
  public Parser(State startState, Tokenizer tokenizer)
  {
    this.startState = startState;
    this.tokenizer = tokenizer;
  }

  /**
   * Parses the input from the tokenizer according to the start state
   * @return the token representing the whole of the parsed structure
   * @throws ParseException - if an error occurs while parsing
   */
  public Token parse() throws ParseException
  {
    Deque<State> stateStack = new LinkedList<State>();
    Deque<Token> tokenStack = new LinkedList<Token>();
    stateStack.addFirst(startState);

    Token lookahead = tokenizer.next();

    while (true)
    {
      State state = stateStack.peekFirst();

      Action action = state.getAction(lookahead);
      if (action == null)
      {
        String expectedTypes = getTypesString(state.getExpectedTerminalTypes());
        throw new ParseException("Unexpected token: " + lookahead + ". Expected one of: " + expectedTypes);
      }

      if (action.isAccept())
      {
        System.out.println("Accepting on " + lookahead); // TODO: remove debug output
        // perform the reduction that the accept action does
        action.perform(lookahead, stateStack, tokenStack);
        return tokenStack.removeFirst();
      }

      // perform the action (usually shift/reduce)
      boolean tokenUsed = action.perform(lookahead, stateStack, tokenStack);
      if (tokenUsed)
      {
        lookahead = tokenizer.next();
      }
    }

  }

  /**
   * Finds the string representation of a list of types.
   * @param types - the list of types to convert to a string
   * @return the string representation of the specified list of types
   */
  private static String getTypesString(Object[] types)
  {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < types.length; i++)
    {
      buffer.append(types[i]);
      if (i != types.length - 1)
      {
        buffer.append(", ");
      }
    }
    return buffer.toString();
  }

}
