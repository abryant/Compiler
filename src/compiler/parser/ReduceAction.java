package compiler.parser;

import java.util.Deque;

/*
 * Created on 21 Jun 2010
 */

/**
 * Represents a reduce action in the parser.
 *
 * @author Anthony Bryant
 */
public class ReduceAction extends Action
{
  private Rule rule;
  private int typeListIndex;

  /**
   * Creates a new ReduceAction to reduce via the specified rule
   * @param rule - the rule to reduce with
   * @param typeListIndex - the index of the type list within the rule to reduce with
   */
  public ReduceAction(Rule rule, int typeListIndex)
  {
    this.rule = rule;
    this.typeListIndex = typeListIndex;
  }

  /**
   * @see compiler.parser.Action#perform(compiler.parser.Token, java.util.Deque, java.util.Deque)
   */
  @Override
  public boolean perform(Token token, Deque<IState> stateStack, Deque<Token> tokenStack)
  {
    Object[] ruleElements = rule.getRequirementTypeLists()[typeListIndex];
    if (stateStack.size() <= ruleElements.length || tokenStack.size() < ruleElements.length)
    {
      throw new IllegalStateException("Bad reduction of rule, not enough elements");
    }

    // get the list of token values
    Object[] values = new Object[ruleElements.length];
    for (int i = 0; i < values.length; i++)
    {
      Token t = tokenStack.removeFirst();
      if (!t.getType().equals(ruleElements[i]))
      {
        throw new IllegalStateException("Bad reduction of rule, invalid token type");
      }
      values[i] = t.getValue();

      // remove the top state from the state stack
      stateStack.removeFirst();
    }

    Object result = rule.match(ruleElements, values);
    Token nonTerminal = new Token(rule.getType(), result);

    IState topState = stateStack.peekFirst();
    IState gotoState = topState.getGoto(nonTerminal);

    stateStack.addFirst(gotoState);
    tokenStack.addFirst(nonTerminal);

    return false;
  }
}
