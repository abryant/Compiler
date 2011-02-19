package compiler.language.conceptual.type;


/*
 * Created on 17 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeArgument
{

  private String name;
  private PointerType superType;
  private PointerType subType;

  /**
   * Creates a new TypeArgument with the specified name.
   * @param name - the name of the TypeArgument
   */
  public TypeArgument(String name)
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
   * @return the superType
   */
  public PointerType getSuperType()
  {
    return superType;
  }

  /**
   * @param superType - the superType to set
   */
  public void setSuperType(PointerType superType)
  {
    this.superType = superType;
  }

  /**
   * @return the subType
   */
  public PointerType getSubType()
  {
    return subType;
  }

  /**
   * @param subType - the subType to set
   */
  public void setSubType(PointerType subType)
  {
    this.subType = subType;
  }

}
