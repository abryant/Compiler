package compiler.parser;


/*
 * Created on 22 Jun 2010
 */

/**
 * A value object which represents a use of a type in a rule set.
 *
 * @author Anthony Bryant
 */
public final class TypeUseEntry
{
  private Rule rule;
  private int productionIndex;
  private int offset;

  /**
   * Creates a new TypeUseEntry to store the specified values
   * @param rule - the rule to store
   * @param productionIndex - the index to store
   * @param offset - the offset to store
   */
  public TypeUseEntry(Rule rule, int productionIndex, int offset)
  {
    this.rule = rule;
    this.productionIndex = productionIndex;
    this.offset = offset;
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
   * @return the offset
   */
  public int getOffset()
  {
    return offset;
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o)
  {
    if (!(o instanceof TypeUseEntry))
    {
      return false;
    }
    TypeUseEntry other = (TypeUseEntry) o;
    return rule.equals(other.rule) &&
           productionIndex == other.productionIndex &&
           offset == other.offset;
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