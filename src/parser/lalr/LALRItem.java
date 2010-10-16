package parser.lalr;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import parser.Production;
import parser.Rule;
import parser.TypeUseEntry;


/*
 * Created on 21 Jun 2010
 */

/**
 * Represents an LALR item, used during generation of an LALR parse table.
 *
 * @author Anthony Bryant
 * @param <T> - the enum type that holds all possible values for the token type
 */
public final class LALRItem<T extends Enum<T>>
{

  // a TypeUseEntry representing the next token to be used
  // e.g. if this item is A -> b.Ca
  // then the next type would represent the C in this production
  // for items of the form A -> bCa.
  // the offset of the type use entry is equal to the length of the production
  /**
   * A TypeUseEntry representing the next token to be used
   * e.g. if this item is <code>A -> b.Cd</code>
   * then the next type would represent the C in this production.
   * For items of the form <code>A -> bCd.</code>
   * the offset of the type use entry is equal to the length of the production.
   */
  private TypeUseEntry<T> nextTypeUse;

  private Set<T> lookaheadTokens;

  /**
   * Creates a new LALRItem with the specified rule, production index and offset
   * @param rule - the rule that this item is for
   * @param productionIndex - the index of the production in this rule
   * @param offset - the current position in the rule
   */
  public LALRItem(Rule<T> rule, int productionIndex, int offset)
  {
    nextTypeUse = new TypeUseEntry<T>(rule, productionIndex, offset);
    lookaheadTokens = new HashSet<T>();
  }

  /**
   * Copy constructor. Copies all of the specified item's state into this new LALR item
   * @param item - the item to copy the state from
   */
  public LALRItem(LALRItem<T> item)
  {
    // TypeUseEntry is immutable, so we do not have to copy it's values out
    nextTypeUse = item.nextTypeUse;
    // copy all of the lookahead tokens from the original item
    lookaheadTokens = new HashSet<T>(item.lookaheadTokens);
  }

  /**
   * Adds the specified lookahead token type to this LALR item
   * @param lookahead - the lookahead token type to add
   */
  public void addLookahead(T lookahead)
  {
    lookaheadTokens.add(lookahead);
  }

  /**
   * Adds all of the specified lookahead token types to this LALR item
   * @param lookaheads - the lookahead token types to add
   */
  public void addLookaheads(Set<T> lookaheads)
  {
    lookaheadTokens.addAll(lookaheads);
  }

  /**
   * @return the rule
   */
  public Rule<T> getRule()
  {
    return nextTypeUse.getRule();
  }

  /**
   * @return the productionIndex
   */
  public int getProductionIndex()
  {
    return nextTypeUse.getProductionIndex();
  }

  /**
   * A convenience method for <code>item.getRule().getProductions()[item.getProductionIndex()]</code>
   * @return the production that this LALR item represents.
   */
  public Production<T> getProduction()
  {
    return getRule().getProductions()[getProductionIndex()];
  }

  /**
   * @return the offset
   */
  public int getOffset()
  {
    return nextTypeUse.getOffset();
  }

  /**
   * @return the next type use to be specified by the production associated with this item.
   *         This should be used to index maps to LALRItems.
   */
  public TypeUseEntry<T> getNextTypeUse()
  {
    return nextTypeUse;
  }

  /**
   * Finds whether this item has the specified token in its lookahead set
   * @param tokenType - the token type to check for
   * @return true if the specified token type is in the lookahead set, false otherwise
   */
  public boolean hasLookahead(T tokenType)
  {
    return lookaheadTokens.contains(tokenType);
  }

  /**
   * @return the set of lookahead tokens for this LALR item
   */
  public Set<T> getLookaheads()
  {
    return lookaheadTokens;
  }

  /**
   * Finds whether this item has all of the specified lookahead types that the specified other item has
   * @param item - the item to compare the lookahead token types of
   * @return true if this item has all of the lookahead token types that the other item has, false otherwise
   */
  public boolean containsLookaheads(LALRItem<T> item)
  {
    if (item == this)
    {
      return true;
    }

    // if the other one has more lookahead tokens than this one, we can skip the rest of the tests
    if (lookaheadTokens.size() < item.lookaheadTokens.size())
    {
      return false;
    }
    // check that each lookahead in item.lookaheadTokens is also in lookaheadTokens
    for (Object lookahead : item.lookaheadTokens)
    {
      if (!lookaheadTokens.contains(lookahead))
      {
        return false;
      }
    }
    return true;
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
    // this is not nice, but is necessary to check whether the items
    // are equal without knowing their generic type parameters
    @SuppressWarnings("unchecked")
    LALRItem<T> item = (LALRItem<T>) o;
    return nextTypeUse.equals(item.nextTypeUse);
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    return nextTypeUse.hashCode();
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[");
    buffer.append(nextTypeUse.getRule().getType());
    buffer.append(" <- ");
    Production<T> production = nextTypeUse.getRule().getProductions()[nextTypeUse.getProductionIndex()];
    Object[] productionTypes = production.getTypes();
    int offset = nextTypeUse.getOffset();
    for (int i = 0; i < productionTypes.length; i++)
    {
      if (offset == i)
      {
        buffer.append(". ");
      }
      buffer.append(productionTypes[i]);
      if (i != productionTypes.length - 1)
      {
        buffer.append(" ");
      }
    }
    if (offset == productionTypes.length)
    {
      buffer.append(" .");
    }
    buffer.append(" | ");
    Iterator<T> it = lookaheadTokens.iterator();
    while (it.hasNext())
    {
      buffer.append(it.next());
      if (it.hasNext())
      {
        buffer.append(" ");
      }
    }
    buffer.append("]");
    return buffer.toString();
  }
}
