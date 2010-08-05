package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.TUPLE_EXPRESSION;

import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ExpressionRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {TUPLE_EXPRESSION};

  public ExpressionRule()
  {
    super(EXPRESSION, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      // return the already generated expression
      return args[0];
    }
    throw badTypeList();
  }

}
