package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.CLASS_DEFINITION;
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

  public TypeDefinitionRule()
  {
    super(TYPE_DEFINITION, CLASS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == CLASS_PRODUCTION)
    {
      // ClassDefinition is a subclass of TypeDefinition, so just return the ClassDefinition argument
      return args[0];
    }
    throw badTypeList();
  }

}
