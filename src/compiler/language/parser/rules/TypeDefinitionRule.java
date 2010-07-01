package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.CLASS_DEFINITION;
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

  public TypeDefinitionRule()
  {
    super(TYPE_DEFINITION, CLASS_PRODUCTION, INTERFACE_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == CLASS_PRODUCTION || types == INTERFACE_PRODUCTION)
    {
      // ClassDefinition and InterfaceDefinition are both subclasses of TypeDefinition, so just return the ClassDefinition or InterfaceDefinition argument
      return args[0];
    }
    throw badTypeList();
  }

}
