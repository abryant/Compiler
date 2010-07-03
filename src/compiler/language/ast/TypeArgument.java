package compiler.language.ast;

/*
 * Created on 3 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeArgument
{

  private Name name;
  private PointerType superType; // the type that this type argument extends
  private PointerType subType;   // the type that this type argument is a superclass of

  /**
   * Creates a new TypeArgument with the specified name, super type and sub type.
   * @param name - the name of this type argument
   * @param superType - the type that this argument must extend
   * @param subType - the type that this argument must be a superclass of
   */
  public TypeArgument(Name name, PointerType superType, PointerType subType)
  {
    this.name = name;
    this.superType = superType;
    this.subType = subType;
  }

  /**
   * @return the name
   */
  public Name getName()
  {
    return name;
  }

  /**
   * @return the type that this argument must extend
   */
  public PointerType getSuperType()
  {
    return superType;
  }

  /**
   * @return the type that this argument must be a superclass of
   */
  public PointerType getSubType()
  {
    return subType;
  }

}
