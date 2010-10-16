package parser;


/*
 * Created on 22 Jun 2010
 */

/**
 * An immutable value object which represents a particular type in a production in a Rule.
 *
 * @author Anthony Bryant
 * @param <T> - the enum type that holds all possible values for the token type
 */
public final class TypeUseEntry<T extends Enum<T>>
{
  private Rule<T> rule;
  private int productionIndex;
  private int offset;

  /**
   * Creates a new TypeUseEntry to store the specified values
   * @param rule - the rule to store
   * @param productionIndex - the index to store
   * @param offset - the offset to store
   */
  public TypeUseEntry(Rule<T> rule, int productionIndex, int offset)
  {
    this.rule = rule;
    this.productionIndex = productionIndex;
    this.offset = offset;
  }

  /**
   * @return the rule
   */
  public Rule<T> getRule()
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
    // this is not nice, but is necessary to check whether the TypeUseEntrys
    // are equal without knowing their generic type parameters
    @SuppressWarnings("unchecked")
    TypeUseEntry<T> other = (TypeUseEntry<T>) o;
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