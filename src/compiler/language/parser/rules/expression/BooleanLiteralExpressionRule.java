package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BOOLEAN_LITERAL_EXPRESSION;
import static compiler.language.parser.ParseType.FALSE_KEYWORD;
import static compiler.language.parser.ParseType.TRUE_KEYWORD;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BooleanLiteralExpression;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 27 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanLiteralExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production TRUE_PRODUCTION = new Production(TRUE_KEYWORD);
  private static final Production FALSE_PRODUCTION = new Production(FALSE_KEYWORD);

  public BooleanLiteralExpressionRule()
  {
    super(BOOLEAN_LITERAL_EXPRESSION, TRUE_PRODUCTION, FALSE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (TRUE_PRODUCTION.equals(production))
    {
      return new BooleanLiteralExpression(true, (ParseInfo) args[0]);
    }
    if (FALSE_PRODUCTION.equals(production))
    {
      return new BooleanLiteralExpression(false, (ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
