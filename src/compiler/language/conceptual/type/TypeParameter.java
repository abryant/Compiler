package compiler.language.conceptual.type;

import compiler.language.conceptual.ConceptualException;
import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.ScopeType;


/*
 * Created on 17 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParameter extends Resolvable
{

  private String name;
  private PointerType[] superTypes;
  private PointerType[] subTypes;

  /**
   * Creates a new TypeParameter with the specified name.
   * @param name - the name of the TypeParameter
   */
  public TypeParameter(String name)
  {
    this.name = name;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * @return the superTypes
   */
  public PointerType[] getSuperTypes()
  {
    return superTypes;
  }

  /**
   * @param superTypes - the superTypes to set
   */
  public void setSuperTypes(PointerType[] superTypes)
  {
    this.superTypes = superTypes;
  }

  /**
   * @return the subTypes
   */
  public PointerType[] getSubTypes()
  {
    return subTypes;
  }

  /**
   * @param subTypes - the subTypes to set
   */
  public void setSubTypes(PointerType[] subTypes)
  {
    this.subTypes = subTypes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ScopeType getType()
  {
    return ScopeType.TYPE_PARAMETER;
  }

  /**
   * Checks whether the specified Type can be assigned to this TypeParameter.
   * @param type - the Type to check
   * @return true if the specified type can be assigned to this TypeParameter, false otherwise
   */
  public boolean canAssign(Type type)
  {
    if (type instanceof TypeParameterPointerType)
    {
      return canAssign(((TypeParameterPointerType) type).getTypeParameter());
    }

    for (PointerType superType : superTypes)
    {
      if (!superType.canAssign(type))
      {
        return false;
      }
    }
    for (PointerType subType : subTypes)
    {
      if (!type.canAssign(subType))
      {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks whether the specified TypeParameter can be assigned to this TypeParameter.
   * @param typeParameter - the TypeParameter to check
   * @return true if all possible instantiations of the specified TypeParameter can be assigned to this TypeParameter
   */
  public boolean canAssign(TypeParameter typeParameter)
  {
    // for each super type, we must find at least one super type in typeParameter that we can assign to that super type
    // this ensures that the specified typeParameter allows a subset of the types allowed by this TypeParameter.
    for (PointerType superType : superTypes)
    {
      boolean found = false;
      for (PointerType otherSuperType : typeParameter.getSuperTypes())
      {
        if (superType.canAssign(otherSuperType))
        {
          found = true;
          break;
        }
      }
      if (!found)
      {
        return false;
      }
    }
    // similarly, for each sub type, we much find at least on sub type in typeParameter that we can assign that sub type to
    // this also ensures that the specified typeParameter only allows a subset of the types this TypeParameter allows
    for (PointerType subType : subTypes)
    {
      boolean found = false;
      for (PointerType otherSubType : typeParameter.getSubTypes())
      {
        if (otherSubType.canAssign(subType))
        {
          found = true;
          break;
        }
      }
      if (!found)
      {
        return false;
      }
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Resolvable resolve(String name) throws NameConflictException, ConceptualException
  {
    // TODO: implement
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Resolvable getParent()
  {
    // TODO: implement (should it just return null?)
    return null;
  }

}
