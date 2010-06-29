package compiler.parser.lalr;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import compiler.parser.TypeUseEntry;

/*
 * Created on 21 Jun 2010
 */

/**
 * Represents a set of LALR items, used during generation of an LALR parse table.
 *
 * @author Anthony Bryant
 */
public final class LALRItemSet
{

  // these are Maps from the key to itself, this is because LALRItem overrides
  // equals() and hashCode() but does not take all information into account,
  // which means that we have to be able to extract this information somehow
  // and a Map from key to itself is much faster than iterating through
  // the whole set for every lookup
  private Map<TypeUseEntry, LALRItem> kernelItems;
  private Map<TypeUseEntry, LALRItem> closureItems;

  /**
   * Creates a new empty LALR item set
   */
  public LALRItemSet()
  {
    kernelItems = new HashMap<TypeUseEntry, LALRItem>();
    closureItems = new HashMap<TypeUseEntry, LALRItem>();
  }

  /**
   * Adds the specified item to the kernel of the item set.
   * After a kernel item is added, calculateClosureItems() must be called to recalculate the closure of the item set
   * @param item - the item to add
   */
  public void addKernelItem(LALRItem item)
  {
    kernelItems.put(item.getNextTypeUse(), item);
    closureItems = null;
  }

  /**
   * @return all of the kernel items in this item set
   */
  public Collection<LALRItem> getKernelItems()
  {
    return kernelItems.values();
  }

  /**
   * @return a new Set containing all of the items in this LALRItemSet
   */
  public Set<LALRItem> getItems()
  {
    Set<LALRItem> items = new HashSet<LALRItem>(kernelItems.values());
    items.addAll(closureItems.values());
    return items;
  }

  /**
   * Calculates the closure of this Item set based on the specified rule set
   * @param rules - the rule set to calculate the closure based on
   */
  public void calculateClosureItems(LALRRuleSet rules)
  {
    closureItems = rules.calculateClosureItems(getKernelItems());
  }

  /**
   * Adds the lookahead sets of the specified LALRItemSet's kernel items to this item set's items
   * After the lookaheads are combined, calculateClosureItems() must be called to recalculate the closure of this item set
   * @param other - the item set to combine the lookaheads from
   */
  public void combineLookaheads(LALRItemSet other)
  {
    if (!equals(other))
    {
      throw new IllegalArgumentException("Tried to combine lookaheads with an LALRItemSet with a different kernel set.");
    }

    for (LALRItem item : kernelItems.values())
    {
      LALRItem otherItem = other.kernelItems.get(item.getNextTypeUse());
      item.addLookaheads(otherItem.getLookaheads());
    }

    closureItems = null;
  }

  /**
   * Tests whether this LALR item set is identical to the specified other set.
   * This will compare not only the kernel items, but the closure items and all of the items' associated lookaheads.
   * @param other - the other item set to compare this LALRItemSet to
   * @return true if the specified item sets are exactly identical, false otherwise
   */
  public boolean isIdentical(LALRItemSet other)
  {
    if (other == this)
    {
      return true;
    }

    if (kernelItems.size() != other.kernelItems.size() || closureItems.size() != other.closureItems.size())
    {
      return false;
    }
    for (LALRItem item : kernelItems.values())
    {
      LALRItem otherItem = other.kernelItems.get(item.getNextTypeUse());
      if (!item.equalLookaheads(otherItem))
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
    if (!(o instanceof LALRItemSet))
    {
      return false;
    }
    LALRItemSet other = (LALRItemSet) o;

    // make sure the kernel item sets are equal, do not care about the closure items
    if (kernelItems.size() != other.kernelItems.size())
    {
      return false;
    }
    for (TypeUseEntry typeUse : kernelItems.keySet())
    {
      if (!other.kernelItems.containsKey(typeUse))
      {
        return false;
      }
    }
    return true;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    // sum the hash codes of all of the kernel items' type use entries
    int hashCode = 0;
    for (TypeUseEntry typeUse : kernelItems.keySet())
    {
      hashCode += typeUse.hashCode();
    }
    return hashCode;
  }

}
