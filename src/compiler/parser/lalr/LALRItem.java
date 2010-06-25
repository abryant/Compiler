package compiler.parser.lalr;

import java.util.HashSet;
import java.util.Set;

import compiler.parser.Rule;

/*
 * Created on 21 Jun 2010
 */

/**
 * Represents an LALR item, used during generation of an LALR parse table.
 *
 * @author Anthony Bryant
 */
public final class LALRItem
{

  private Rule rule;
  private int productionIndex;
  private int offset;

  private Set<Object> lookaheadTokens;

  /**
   * Creates a new LALRItem with the specified rule, production index and offset
   * @param rule - the rule that this item is for
   * @param productionIndex - the index of the production in this rule
   * @param offset - the current position in the rule
   */
  public LALRItem(Rule rule, int productionIndex, int offset)
  {
    this.rule = rule;
    this.productionIndex = productionIndex;
    this.offset = offset;
    lookaheadTokens = new HashSet<Object>();
  }

  /**
   * Adds the specified lookahead token type to this LALR item
   * @param lookahead - the lookahead token type to add
   */
  public void addLookahead(Object lookahead)
  {
    lookaheadTokens.add(lookahead);
  }

  /**
   * Adds all of the specified lookahead token types to this LALR item
   * @param lookaheads - the lookahead token types to add
   */
  public void addLookaheads(Set<Object> lookaheads)
  {
    lookaheadTokens.addAll(lookaheads);
  }

  /**
   * @return the rule
   */
  public Rule getRule()
  {
    return rule;
  }

  /**
   * @return the productionIndex
   */
  public int getProductionIndex()
  {
    return productionIndex;
  }

  /**
   * A convenience method for <code>item.getRule().getProductions()[item.getProductionIndex()]</code>
   * @return the production that this LALR item represents.
   */
  public Object[] getProduction()
  {
    return rule.getProductions()[productionIndex];
  }

  /**
   * @return the offset
   */
  public int getOffset()
  {
    return offset;
  }

  /**
   * Finds whether this item has the specified token in its lookahead set
   * @param tokenType - the token type to check for
   * @return true if the specified token type is in the lookahead set, false otherwise
   */
  public boolean hasLookahead(Object tokenType)
  {
    return lookaheadTokens.contains(tokenType);
  }

  /**
   * @return the set of lookahead tokens for this LALR item
   */
  public Set<Object> getLookaheads()
  {
    return lookaheadTokens;
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o)
  {
    if (!(o instanceof LALRItem))
    {
      return false;
    }
    LALRItem item = (LALRItem) o;
    return rule.equals(item.rule) && productionIndex == item.productionIndex && offset == item.offset;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    return (rule.hashCode() * 31 + productionIndex) * 31 + offset;
  }
}
