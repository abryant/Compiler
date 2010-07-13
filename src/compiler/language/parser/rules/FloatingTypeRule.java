package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.FLOATING_TYPE;
import static compiler.language.parser.ParseType.FLOATING_TYPE_LENGTH;

import compiler.language.ast.FloatingType;
import compiler.language.ast.FloatingTypeLength;
import compiler.parser.Rule;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class FloatingTypeRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {FLOATING_TYPE_LENGTH};

  public FloatingTypeRule()
  {
    super(FLOATING_TYPE, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new FloatingType((FloatingTypeLength) args[0]);
    }
    throw badTypeList();
  }

}
