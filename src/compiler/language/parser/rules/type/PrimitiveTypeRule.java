package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.BOOLEAN_TYPE;
import static compiler.language.parser.ParseType.CHARACTER_TYPE;
import static compiler.language.parser.ParseType.FLOATING_TYPE;
import static compiler.language.parser.ParseType.INTEGER_TYPE;
import static compiler.language.parser.ParseType.PRIMITIVE_TYPE;

import compiler.parser.Rule;

/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class PrimitiveTypeRule extends Rule
{

  private static final Object[] BOOLEAN_PRODUCTION = new Object[] {BOOLEAN_TYPE};
  private static final Object[] INTEGER_PRODUCTION = new Object[] {INTEGER_TYPE};
  private static final Object[] CHARACTER_PRODUCTION = new Object[] {CHARACTER_TYPE};
  private static final Object[] FLOATING_PRODUCTION = new Object[] {FLOATING_TYPE};

  public PrimitiveTypeRule()
  {
    super(PRIMITIVE_TYPE, BOOLEAN_PRODUCTION, INTEGER_PRODUCTION, CHARACTER_PRODUCTION, FLOATING_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == BOOLEAN_PRODUCTION || types == INTEGER_PRODUCTION || types == CHARACTER_PRODUCTION || types == FLOATING_PRODUCTION)
    {
      // All primitive types that can be reduced to a PrimitiveType are actually subclasses of PrimitiveType, so just return the argument in any case
      return args[0];
    }
    throw badTypeList();
  }

}
