package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ARRAY_INSTANCIATION_EXPRESSION_NO_INITIALIZER;
import static compiler.language.parser.ParseType.BASIC_PRIMARY;
import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.PRIMARY;
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
public final class PrimaryRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(BASIC_PRIMARY);
  private static final Production<ParseType> PARENTHESISED_PRODUCTION = new Production<ParseType>(LPAREN, TUPLE_EXPRESSION, RPAREN);
  private static final Production<ParseType> ARRAY_INSTANCIATION_PRODUCTION = new Production<ParseType>(ARRAY_INSTANCIATION_EXPRESSION_NO_INITIALIZER);

  @SuppressWarnings("unchecked")
  public PrimaryRule()
  {
    super(PRIMARY, PRODUCTION, PARENTHESISED_PRODUCTION, ARRAY_INSTANCIATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production) || ARRAY_INSTANCIATION_PRODUCTION.equals(production))
    {
      // return the existing Expression
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
