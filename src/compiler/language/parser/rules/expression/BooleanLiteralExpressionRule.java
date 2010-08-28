package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BOOLEAN_LITERAL_EXPRESSION;
import static compiler.language.parser.ParseType.FALSE_KEYWORD;
import static compiler.language.parser.ParseType.TRUE_KEYWORD;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BooleanLiteralExpression;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 27 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanLiteralExpressionRule extends Rule
{

  private static final Object[] TRUE_PRODUCTION = new Object[] {TRUE_KEYWORD};
  private static final Object[] FALSE_PRODUCTION = new Object[] {FALSE_KEYWORD};

  public BooleanLiteralExpressionRule()
  {
    super(BOOLEAN_LITERAL_EXPRESSION, TRUE_PRODUCTION, FALSE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == TRUE_PRODUCTION)
    {
      return new BooleanLiteralExpression(true, (ParseInfo) args[0]);
    }
    if (types == FALSE_PRODUCTION)
    {
      return new BooleanLiteralExpression(false, (ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
