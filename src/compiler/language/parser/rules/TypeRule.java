package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.REFERENCE_TYPE;
import static compiler.language.parser.ParseType.TYPE;

import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeRule extends Rule
{

  private static final Object[] REFERENCE_TYPE_PRODUCTION = new Object[] {REFERENCE_TYPE};

  public TypeRule()
  {
    super(TYPE, REFERENCE_TYPE_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == REFERENCE_TYPE_PRODUCTION)
    {
      // ReferenceType is a subclass of Type, so just return the ReferenceType argument
      return args[0];
    }
    throw badTypeList();
  }

}
