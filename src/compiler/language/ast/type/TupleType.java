package compiler.language.ast.type;


/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TupleType extends Type
{

  private Type[] types;

  /**
   * Creates a new Tuple Type with the specified list of types
   * @param types - the types contained in this tuple type
   */
  public TupleType(Type[] types)
  {
    this.types = types;
  }

  /**
   * @return the types contained in this tuple type
   */
  public Type[] getTypes()
  {
    return types;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("(");
    for (int i = 0; i < types.length; i++)
    {
      buffer.append(types[i]);
      if (i != types.length - 1)
      {
        buffer.append(", ");
      }
    }
    buffer.append(")");
    return buffer.toString();
  }

}
