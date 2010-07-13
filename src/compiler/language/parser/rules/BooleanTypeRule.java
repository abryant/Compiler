package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.BOOLEAN_KEYWORD;
import static compiler.language.parser.ParseType.BOOLEAN_TYPE;

import compiler.language.ast.BooleanType;
import compiler.parser.Rule;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanTypeRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {BOOLEAN_KEYWORD};

  public BooleanTypeRule()
  {
    super(BOOLEAN_TYPE, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new BooleanType();
    }
    throw badTypeList();
  }

}
