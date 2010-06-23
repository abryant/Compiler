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
  private int typeListIndex;
  private int offset;

  /**
   * Creates a new TypeUseEntry to store the specified values
   * @param rule - the rule to store
   * @param typeListIndex - the index to store
   * @param offset - the offset to store
   */
  public TypeUseEntry(Rule rule, int typeListIndex, int offset)
  {
    this.rule = rule;
    this.typeListIndex = typeListIndex;
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
   * @return the typeListIndex
   */
  public int getTypeListIndex()
  {
    return typeListIndex;
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
           typeListIndex == other.typeListIndex &&
           offset == other.offset;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    return (rule.hashCode() * 31 + typeListIndex) * 31 + offset;
  }
}