package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOUBLE_KEYWORD;
import static compiler.language.parser.ParseType.FLOATING_TYPE;
import static compiler.language.parser.ParseType.FLOAT_KEYWORD;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.FloatingType;
import compiler.language.ast.type.FloatingTypeLength;
import compiler.parser.Rule;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class FloatingTypeRule extends Rule
{

  private static final Object[] FLOAT_PRODUCTION = new Object[] {FLOAT_KEYWORD};
  private static final Object[] DOUBLE_PRODUCTION = new Object[] {DOUBLE_KEYWORD};

  public FloatingTypeRule()
  {
    super(FLOATING_TYPE, FLOAT_PRODUCTION, DOUBLE_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == FLOAT_PRODUCTION)
    {
      return new FloatingType(FloatingTypeLength.FLOAT, (ParseInfo) args[0]);
    }
    if (types == DOUBLE_PRODUCTION)
    {
      return new FloatingType(FloatingTypeLength.DOUBLE, (ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
