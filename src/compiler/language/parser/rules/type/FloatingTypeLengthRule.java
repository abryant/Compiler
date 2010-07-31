package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOUBLE_KEYWORD;
import static compiler.language.parser.ParseType.FLOATING_TYPE_LENGTH;
import static compiler.language.parser.ParseType.FLOAT_KEYWORD;

import compiler.language.ast.type.FloatingTypeLength;
import compiler.parser.Rule;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class FloatingTypeLengthRule extends Rule
{

  private static final Object[] FLOAT_PRODUCTION = new Object[] {FLOAT_KEYWORD};
  private static final Object[] DOUBLE_PRODUCTION = new Object[] {DOUBLE_KEYWORD};

  public FloatingTypeLengthRule()
  {
    super(FLOATING_TYPE_LENGTH, FLOAT_PRODUCTION, DOUBLE_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == FLOAT_PRODUCTION)
    {
      return FloatingTypeLength.FLOAT;
    }
    if (types == DOUBLE_PRODUCTION)
    {
      return FloatingTypeLength.DOUBLE;
    }
    throw badTypeList();
  }

}
