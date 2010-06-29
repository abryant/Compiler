package compiler.parser.lalr;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import compiler.parser.AcceptAction;
import compiler.parser.Action;
import compiler.parser.IState;
import compiler.parser.ReduceAction;
import compiler.parser.Rule;
import compiler.parser.ShiftAction;
import compiler.parser.Token;

/*
 * Created on 21 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class LALRState implements IState
{

  private Map<Object, LALRState> shiftRules;
  private Map<Object, ReduceAction> reduceActions;
  // this should only ever contain null -> new AcceptAction(), (null means there are no more tokens)
  // but other conditions for accept are allowed by the parser, so this is more general
  private Set<Object> acceptActions;

  private Map<Object, LALRState> gotoRules;

  /**
   * Creates a new LALRState with no shift or reduce actions or goto rules.
   */
  public LALRState()
  {
    shiftRules = new HashMap<Object, LALRState>();
    reduceActions = new HashMap<Object, ReduceAction>();
    gotoRules = new HashMap<Object, LALRState>();
    acceptActions = new HashSet<Object>();
  }

  /**
   * Adds a shift action from this state to the specified state via the specified token type
   * @param tokenType - the token type to shift on
   * @param shiftTo - the state to shift to
   */
  public void addShift(Object tokenType, LALRState shiftTo)
  {
    if (shiftRules.containsKey(tokenType))
    {
      throw new IllegalStateException("Shift-shift conflict! (this should not happen) Via terminal: " + tokenType);
    }
    ReduceAction reduceAction = reduceActions.get(tokenType);
    if (reduceAction != null)
    {
      Rule rule = reduceAction.getRule();
      Object[] production = rule.getProductions()[reduceAction.getProductionIndex()];
      throw shiftReduceConflict(tokenType, rule.getType(), production);
    }
    if (acceptActions.contains(tokenType))
    {
      throw new IllegalStateException("Shift-accept conflict! (this should not happen) On input: " + tokenType);
    }
    shiftRules.put(tokenType, shiftTo);
  }

  /**
   * Adds a reduce action from this state via the specified rule and production with the specified token type
   * @param tokenType - the token type to shift on
   * @param reduceRule - the rule to reduce with
   * @param productionIndex - the production of the rule to use
   */
  public void addReduce(Object tokenType, Rule reduceRule, int productionIndex)
  {
    if (shiftRules.containsKey(tokenType))
    {
      Object[] production = reduceRule.getProductions()[productionIndex];
      throw shiftReduceConflict(tokenType, reduceRule.getType(), production);
    }
    ReduceAction existingReduce = reduceActions.get(tokenType);
    if (existingReduce != null)
    {
      Object[] newProduction = reduceRule.getProductions()[productionIndex];
      throw reduceReduceConflict(tokenType, existingReduce, reduceRule.getType(), newProduction);
    }
    if (acceptActions.contains(tokenType))
    {
      Object[] newProduction = reduceRule.getProductions()[productionIndex];
      throw acceptReduceConflict(tokenType, reduceRule.getType(), newProduction);
    }
    reduceActions.put(tokenType, new ReduceAction(reduceRule, productionIndex));
  }

  /**
   * Adds an accept action from this state via the specified token type.
   * @param tokenType - the token type to accept on
   */
  public void addAccept(Object tokenType)
  {
    if (shiftRules.containsKey(tokenType))
    {
      throw new IllegalStateException("Shift-accept conflict! (this should not happen) On input: " + tokenType);
    }
    ReduceAction existingReduce = reduceActions.get(tokenType);
    if (existingReduce != null)
    {
      Rule existingRule = existingReduce.getRule();
      throw acceptReduceConflict(tokenType, existingRule.getType(), existingRule.getProductions()[existingReduce.getProductionIndex()]);
    }
    if (acceptActions.contains(tokenType))
    {
      throw new IllegalStateException("Accept-accept conflict! (this should not happen)");
    }
    acceptActions.add(tokenType);
  }

  /**
   * Adds a goto from this state with the specified token to the specified other state.
   * @param tokenType - the token type for the goto condition
   * @param state - the state to go to on the specified token
   */
  public void addGoto(Object tokenType, LALRState state)
  {
    if (gotoRules.containsKey(tokenType))
    {
      throw new IllegalStateException("Goto-goto conflict! (this should not happen) Type:" + tokenType);
    }
    gotoRules.put(tokenType, state);
  }

  /**
   * @see compiler.parser.IState#getAction(compiler.parser.Token)
   */
  @Override
  public Action getAction(Token terminal)
  {
    // try to return a shift rule first
    LALRState state = shiftRules.get(terminal);
    if (state != null)
    {
      return new ShiftAction(state);
    }
    // there was no shift rule, so try a reduce rule
    ReduceAction action = reduceActions.get(terminal);
    if (action != null)
    {
      return action;
    }
    // there were no shift or reduce rules, so try an accept rule
    if (acceptActions.contains(terminal))
    {
      return new AcceptAction();
    }
    // there is nothing in the table for this terminal from this state
    return null;
  }

  /**
   * @see compiler.parser.IState#getExpectedTerminalTypes()
   */
  @Override
  public Object[] getExpectedTerminalTypes()
  {
    Set<Object> types = new HashSet<Object>();
    types.addAll(shiftRules.keySet());
    types.addAll(reduceActions.keySet());
    types.addAll(acceptActions);
    return types.toArray(new Object[types.size()]);
  }

  /**
   * @see compiler.parser.IState#getGoto(compiler.parser.Token)
   */
  @Override
  public IState getGoto(Token nonTerminal)
  {
    return gotoRules.get(nonTerminal);
  }


  /**
   * Creates an exception representing a shift-reduce conflict.
   * This exception contains details of the input token type,
   * the type that the reduce action would produce, and
   * the production it would take to get there.
   * @param tokenType - the input token that leads to this conflict
   * @param reduceRuleType - the token type that reducing would produce
   * @param reduceProduction - the production that the reduce rule uses
   * @return an exception representing the shift-reduce conflict
   */
  private static IllegalStateException shiftReduceConflict(Object tokenType, Object reduceRuleType, Object[] reduceProduction)
  {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < reduceProduction.length; i++)
    {
      buffer.append(String.valueOf(reduceProduction[i]));
      if (i != reduceProduction.length - 1)
      {
        buffer.append(", ");
      }
    }
    String production = "[" + buffer + "]";
    return new IllegalStateException("Shift-reduce conflict! On input: " + tokenType + ", can either shift or reduce to " + reduceRuleType + " via production " + production);
  }

  /**
   * Creates an exception representing a reduce-reduce conflict.
   * This exception contains details of the input token type,
   * the type that each reduce action would produce, and
   * the production it would take to get there in each case.
   * @param tokenType - the input token that leads to this conflict
   * @param existingReduce - the existing reduce action
   * @param newRuleType - the token type that the new reduce rule would produce
   * @param newProduction - the production that the new reduce rule uses
   * @return an exception representing the reduce-reduce conflict
   */
  private static IllegalStateException reduceReduceConflict(Object tokenType, ReduceAction existingReduce, Object newRuleType, Object[] newProduction)
  {
    Rule existingRule = existingReduce.getRule();
    String existingProductionStr = getProductionString(existingRule.getType(), existingRule.getProductions()[existingReduce.getProductionIndex()]);
    String newProductionStr = getProductionString(newRuleType, newProduction);
    return new IllegalStateException("Reduce-reduce conflict! On input: " + tokenType + ", can reduce via " + existingProductionStr + " or " + newProductionStr);
  }

  /**
   * Creates an exception representing an accept-reduce conflict.
   * This exception contains details of the input token type,
   * the type that the reduce action would produce, and
   * the production that it would take to get there.
   * @param tokenType - the input token that leads to this conflict
   * @param reduceRuleType - the token type that reducing would produce
   * @param reduceProduction - the production that the reduce rule uses
   * @return an exception representing the accept-reduce conflict
   */
  private static IllegalStateException acceptReduceConflict(Object tokenType, Object reduceRuleType, Object[] reduceProduction)
  {
    String productionStr = getProductionString(reduceRuleType, reduceProduction);
    return new IllegalStateException("Accept-reduce conflict! On input: " + tokenType + ", can accept or reduce via " + productionStr);
  }

  /**
   * Returns a string representation of a production.
   * @param type - the type that the production reduces to
   * @param production - the list of types in the production
   * @return a string representation of the production
   */
  private static String getProductionString(Object type, Object[] production)
  {
    StringBuffer existingBuffer = new StringBuffer();
    existingBuffer.append("[");
    existingBuffer.append(type);
    existingBuffer.append(" <- ");
    for (int i = 0; i < production.length; i++)
    {
      existingBuffer.append(production[i]);
      if (i != production.length - 1)
      {
        existingBuffer.append(", ");
      }
      else
      {
        existingBuffer.append("]");
      }
    }
    return existingBuffer.toString();
  }

}
