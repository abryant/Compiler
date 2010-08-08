package compiler.language.ast.type;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.terminal.Name;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerType extends Type
{

  private boolean immutable;

  private Name[] names;
  private TypeParameter[][] typeParameterLists;

  /**
   * Creates a new PointerType with the specified immutability, qualifying names and type parameter lists.
   * @param immutable - true if this type should be immutable, false otherwise
   * @param names - the list of (qualifier) names in this PointerType, ending in the actual type name
   * @param typeParameterLists - the type parameter list for each name in this pointer type, with empty arrays for names that do not have type parameters
   * @param parseInfo - the parsing information
   */
  public PointerType(boolean immutable, Name[] names, TypeParameter[][] typeParameterLists, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.immutable = immutable;

    if (names.length != typeParameterLists.length)
    {
      throw new IllegalArgumentException("A PointerType must have an equal number of names and lists of type parameters");
    }
    this.names = names;
    this.typeParameterLists = typeParameterLists;
  }

  /**
   * Creates a new PointerType with the specified immutability, qualifying names and type parameter lists.
   * @param baseType - the base PointerType to copy the old qualifying names and type parameters from
   * @param immutable - true if this type should be immutable, false otherwise
   * @param addedNames - the list of added (qualifier) names in this PointerType, ending in the actual type name
   * @param addedTypeParameterLists - the type parameter list for each added name in this pointer type, with empty arrays for names that do not have type parameters
   * @param parseInfo - the parsing information
   */
  public PointerType(PointerType baseType, boolean immutable, Name[] addedNames, TypeParameter[][] addedTypeParameterLists, ParseInfo parseInfo)
  {
    super(parseInfo);
    this.immutable = immutable;
    if (addedNames.length != addedTypeParameterLists.length)
    {
      throw new IllegalArgumentException("A PointerType must have an equal number of names and lists of type parameters");
    }
    Name[] oldNames = baseType.getNames();
    TypeParameter[][] oldTypeParameterLists = baseType.getTypeParameterLists();

    names = new Name[oldNames.length + addedNames.length];
    typeParameterLists = new TypeParameter[oldTypeParameterLists.length + addedTypeParameterLists.length][];
    System.arraycopy(oldNames, 0, names, 0, oldNames.length);
    System.arraycopy(addedNames, 0, names, oldNames.length, addedNames.length);
    System.arraycopy(oldTypeParameterLists, 0, typeParameterLists, 0, oldTypeParameterLists.length);
    System.arraycopy(addedTypeParameterLists, 0, typeParameterLists, oldTypeParameterLists.length, addedTypeParameterLists.length);
  }

  /**
   * @return true if this type is immutable, false otherwise
   */
  public boolean isImmutable()
  {
    return immutable;
  }

  /**
   * @return the names
   */
  public Name[] getNames()
  {
    return names;
  }

  /**
   * @return the typeParameterLists
   */
  public TypeParameter[][] getTypeParameterLists()
  {
    return typeParameterLists;
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
    for (int i = 0; i < names.length; i++)
    {
      buffer.append(names[i]);
      TypeParameter[] typeParameterList = typeParameterLists[i];
      if (typeParameterList.length > 0)
      {
        buffer.append("<");
        for (int j = 0; j < typeParameterList.length; j++)
        {
          buffer.append(typeParameterList[j]);
          if (j != typeParameterList.length - 1)
          {
            buffer.append(", ");
          }
        }
        buffer.append(">");
      }
      if (i != names.length - 1)
      {
        buffer.append(".");
      }
    }
    return buffer.toString();
  }
}
