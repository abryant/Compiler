package compiler.language.ast.type;

import compiler.language.ast.misc.QName;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerType extends Type
{

  private QName typeName;
  private boolean immutable;
  private TypeParameter[] typeParameters;

  /**
   * Creates a new PointerType with the specified name and immutability
   * @param typeName - the name of the type
   * @param immutable - true if this type should be immutable, false otherwise
   * @param typeParameters - the type parameters for this pointer type
   */
  public PointerType(QName typeName, boolean immutable, TypeParameter[] typeParameters)
  {
    this.typeName = typeName;
    this.immutable = immutable;
    this.typeParameters = typeParameters;
  }

  /**
   * @return the type name
   */
  public QName getTypeName()
  {
    return typeName;
  }

  /**
   * @return true if this type is immutable, false otherwise
   */
  public boolean isImmutable()
  {
    return immutable;
  }

  /**
   * @return the typeParameters
   */
  public TypeParameter[] getTypeParameters()
  {
    return typeParameters;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    if (immutable)
    {
      buffer.append("#");
    }
    buffer.append(typeName);
    if (typeParameters.length > 0)
    {
      buffer.append("<");
      for (int i = 0; i < typeParameters.length; i++)
      {
        buffer.append(typeParameters[i]);
        if (i != typeParameters.length - 1)
        {
          buffer.append(", ");
        }
      }
      buffer.append(">");
    }
    return buffer.toString();
  }
}
