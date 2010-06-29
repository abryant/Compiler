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

  private IState startState;
  private Tokenizer tokenizer;

  /**
   * Creates a new parser to parse the input from the specified Tokenizer, starting in the specified start state.
   * @param startState - the state to start the parser in
   * @param tokenizer - the tokenizer to read input from
   */
  public Parser(IState startState, Tokenizer tokenizer)
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
    Deque<IState> stateStack = new LinkedList<IState>();
    Deque<Token> tokenStack = new LinkedList<Token>();
    stateStack.addFirst(startState);

    Token lookahead = tokenizer.next();

    while (true)
    {
      IState state = stateStack.peekFirst();

      Action action = state.getAction(lookahead);
      if (action == null)
      {
        // find the list of possible types, and throw an exception containing it
        Object[] expectedTypes = state.getExpectedTerminalTypes();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < expectedTypes.length; i++)
        {
          buffer.append(expectedTypes[i]);
          if (i != expectedTypes.length - 1)
          {
            buffer.append(", ");
          }
        }
        throw new ParseException("Unexpected token: " + lookahead + ". Expected one of: " + buffer.toString());
      }

      if (action.isAccept())
      {
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

}
