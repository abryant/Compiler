package compiler.language.conceptual.type;

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
   * {@inheritDoc}
   */
  @Override
  public Resolvable resolve(String name) throws NameConflictException
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
