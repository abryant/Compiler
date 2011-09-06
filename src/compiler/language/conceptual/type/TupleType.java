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

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canAssign(Type type)
  {
    if (!(type instanceof TupleType))
    {
      return types.length == 1 && types[0].canAssign(type);
    }
    TupleType other = (TupleType) type;
    if (types.length != other.types.length)
    {
      return false;
    }
    for (int i = 0; i < types.length; i++)
    {
      if (!types[i].canAssign(other.types[i]))
      {
        return false;
      }
    }
    return true;
  }



}
