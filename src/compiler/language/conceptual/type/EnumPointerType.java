package compiler.language.conceptual.type;

import compiler.language.conceptual.typeDefinition.ConceptualEnum;

/*
 * Created on 21 Oct 2010
 */

/**
 * @author Anthony Bryant
 */
public class EnumPointerType extends PointerType
{

  private ConceptualEnum enumType;

  /**
   * Creates a new EnumPointerType with the specified ConceptualEnum
   * @param enumType - the enum type
   * @param immutable - true if this PointerType should be immutable, false otherwise
   */
  public EnumPointerType(ConceptualEnum enumType, boolean immutable)
  {
    super(immutable);
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
