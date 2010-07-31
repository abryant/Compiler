package compiler.language.parser.rules.topLevel;

import static compiler.language.parser.ParseType.CLASS_DEFINITION;
import static compiler.language.parser.ParseType.ENUM_DEFINITION;
import static compiler.language.parser.ParseType.INTERFACE_DEFINITION;
import static compiler.language.parser.ParseType.TYPE_DEFINITION;

import compiler.parser.Rule;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeDefinitionRule extends Rule
{

  private static final Object[] CLASS_PRODUCTION = new Object[] {CLASS_DEFINITION};
  private static final Object[] INTERFACE_PRODUCTION = new Object[] {INTERFACE_DEFINITION};
  private static final Object[] ENUM_PRODUCTION = new Object[] {ENUM_DEFINITION};

  public TypeDefinitionRule()
  {
    super(TYPE_DEFINITION, CLASS_PRODUCTION, INTERFACE_PRODUCTION, ENUM_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == CLASS_PRODUCTION || types == INTERFACE_PRODUCTION || types == ENUM_PRODUCTION)
    {
      // ClassDefinition, InterfaceDefinition and EnumDefinition are all subclasses of TypeDefinition, so just return the argument
      return args[0];
    }
    throw badTypeList();
  }

}
