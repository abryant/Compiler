package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.DIMENSION_EXPRESSION;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.LSQUARE;
import static compiler.language.parser.ParseType.RSQUARE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class DimensionExpressionRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {LSQUARE, EXPRESSION, RSQUARE};

  public DimensionExpressionRule()
  {
    super(DIMENSION_EXPRESSION, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      Expression expression = (Expression) args[1];
      return new ParseContainer<Expression>(expression, ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
