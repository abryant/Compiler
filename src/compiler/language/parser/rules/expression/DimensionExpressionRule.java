package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.DIMENSION_EXPRESSION;
import static compiler.language.parser.ParseType.EXPRESSION;
import static compiler.language.parser.ParseType.LSQUARE;
import static compiler.language.parser.ParseType.RSQUARE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseType;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class DimensionExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(LSQUARE, EXPRESSION, RSQUARE);

  @SuppressWarnings("unchecked")
  public DimensionExpressionRule()
  {
    super(DIMENSION_EXPRESSION, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      ExpressionAST expression = (ExpressionAST) args[1];
      return new ParseContainer<ExpressionAST>(expression, ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
