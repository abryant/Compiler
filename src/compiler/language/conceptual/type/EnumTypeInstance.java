package compiler.language.conceptual.type;

import compiler.language.conceptual.typeDefinition.ConceptualEnum;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class EnumTypeInstance extends TypeInstance
{

  private ConceptualEnum enumType;

  /**
   * Creates a new EnumTypeInstance with the specified ConceptualEnum
   * @param enumType - the enum type
   */
  public EnumTypeInstance(ConceptualEnum enumType)
  {
    this.enumType = enumType;
  }

  /**
   * @return the enumType
   */
  public ConceptualEnum getEnumType()
  {
    return enumType;
  }

}
