package compiler.language.conceptual.type;

import compiler.language.conceptual.typeDefinition.ConceptualEnum;


/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class InnerEnumTypeInstance extends EnumTypeInstance
{

  private TypeInstance outerTypeInstance;

  /**
   * Creates a new InnerEnumTypeInstance with the specified ConceptualEnum and outer TypeInstance
   * @param enumType - the enum type
   * @param outerTypeInstance - the outer type instance
   */
  public InnerEnumTypeInstance(ConceptualEnum enumType, TypeInstance outerTypeInstance)
  {
    super(enumType);
    this.outerTypeInstance = outerTypeInstance;
  }

  /**
   * @return the outerTypeInstance
   */
  public TypeInstance getOuterTypeInstance()
  {
    return outerTypeInstance;
  }

}
