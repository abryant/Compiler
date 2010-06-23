package compiler.parser.lalr;

import java.util.HashSet;
import java.util.Set;

/*
 * Created on 21 Jun 2010
 */

/**
 * Represents a set of LALR items, used during generation of an LALR parse table.
 *
 * @author Anthony Bryant
 */
public class LALRItemSet
{

  private Set<LALRItem> kernelItems;
  private Set<LALRItem> closureItems;

  /**
   * Creates a new empty LALR item set
   */
  public LALRItemSet()
  {
    kernelItems = new HashSet<LALRItem>();
    closureItems = new HashSet<LALRItem>();
  }

  /**
   * Adds the specified item to the kernel of the item set.
   * After a kernel item is added, calculateClosureItems() should be called to recalculate the closure of the item set
   * @param item - the item to add
   */
  public void addKernelItem(LALRItem item)
  {
    kernelItems.add(item);
  }

  /**
   * Calculates the closure of this Item set based on the specified rule set
   * @param rules - the rule set to calculate the closure based on
   */
  public void calculateClosureItems(LALRRuleSet rules)
  {
    closureItems = rules.calculateClosureItems(kernelItems);
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
    for (LALRItem item : kernelItems)
    {
      if (!other.kernelItems.contains(item))
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
    // sum the hash codes of all of the kernel items
    int hashCode = 0;
    for (LALRItem item : kernelItems)
    {
      hashCode += item.hashCode();
    }
    return hashCode;
  }

}
