package compiler.parser;

/*
 * Created on 7 Apr 2010
 */

/**
 * @author Anthony Bryant
 * 
 */
class TypeUseEntry
{
  
  private final Rule rule;
  private final int typeListNum;
  private final int offset;
  
  /**
   * Creates a new TypeUseEntry with the specified rule, type list number in that rule's list of type lists, and offset in the type list.
   * @param rule - the Rule that this type is used in
   * @param typeListNum - the index of the Rule's type requirement list that this type is included in
   * @param offset - the offset of the type in the Rule's specified type requirement list
   */
  public TypeUseEntry(Rule rule, int typeListNum, int offset)
  {
    this.rule = rule;
    this.typeListNum = typeListNum;
    this.offset = offset;
  }

  /**
   * @return the Rule that the type is used in
   */
  public Rule getRule()
  {
    return rule;
  }
  
  /**
   * @return the index of the Rule's type requirement list that this type is included in
   */
  public int getTypeListNum()
  {
    return typeListNum;
  }
  
  /**
   * @return the offset of the type in the Rule's specified type requirement list
   */
  public int getOffset()
  {
    return offset;
  }
  
}
