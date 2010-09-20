package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BASIC_PRIMARY;
import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.PRIMARY_NO_TRAILING_DIMENSIONS_NOT_QNAME;
import static compiler.language.parser.ParseType.RPAREN;
import static compiler.language.parser.ParseType.TUPLE_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.ParenthesisedExpression;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PrimaryNoTrailingDimensionsNotQNameRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> BASIC_PRODUCTION = new Production<ParseType>(BASIC_PRIMARY);
  private static final Production<ParseType> PARENTHESISED_PRODUCTION = new Production<ParseType>(LPAREN, TUPLE_EXPRESSION, RPAREN);

  @SuppressWarnings("unchecked")
  public PrimaryNoTrailingDimensionsNotQNameRule()
  {
    super(PRIMARY_NO_TRAILING_DIMENSIONS_NOT_QNAME, BASIC_PRODUCTION, PARENTHESISED_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (BASIC_PRODUCTION.equals(production))
    {
      // an expression has already been generated, so return it
      return args[0];
    }
    if (PARENTHESISED_PRODUCTION.equals(production))
    {
      Expression expression = (Expression) args[1];
      return new ParenthesisedExpression(expression, ParseInfo.combine((ParseInfo) args[0], expression.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
