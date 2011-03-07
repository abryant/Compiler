package compiler.language.conceptual.type;

/*
 * Created on 7 Mar 2011
 */

/**
 * @author Anthony Bryant
 */
public class TupleType extends Type
{

  private Type[] types;


  /**
   * Creates a new TupleType with the specified types.
   * @param types - the enclosed types
   */
  public TupleType(Type[] types)
  {
    this.types = types;
  }

  /**
   * @return the types
   */
  public Type[] getTypes()
  {
    return types;
  }

}
