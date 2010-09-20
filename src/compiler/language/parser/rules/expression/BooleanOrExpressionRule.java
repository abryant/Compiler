package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BOOLEAN_OR_EXPRESSION;
import static compiler.language.parser.ParseType.BOOLEAN_XOR_EXPRESSION;
import static compiler.language.parser.ParseType.DOUBLE_PIPE;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BooleanOrExpression;
import compiler.language.ast.expression.Expression;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanOrExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production START_PRODUCTION          = new Production(BOOLEAN_XOR_EXPRESSION);
  private static final Production OR_PRODUCTION             = new Production(BOOLEAN_OR_EXPRESSION,         DOUBLE_PIPE, BOOLEAN_XOR_EXPRESSION);
  private static final Production OR_QNAME_PRODUCTION       = new Production(BOOLEAN_OR_EXPRESSION,         DOUBLE_PIPE, QNAME_OR_LESS_THAN_EXPRESSION);
  private static final Production QNAME_OR_PRODUCTION       = new Production(QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_PIPE, BOOLEAN_XOR_EXPRESSION);
  private static final Production QNAME_OR_QNAME_PRODUCTION = new Production(QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_PIPE, QNAME_OR_LESS_THAN_EXPRESSION);

  public BooleanOrExpressionRule()
  {
    super(BOOLEAN_OR_EXPRESSION, START_PRODUCTION, OR_PRODUCTION, OR_QNAME_PRODUCTION, QNAME_OR_PRODUCTION, QNAME_OR_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      // return the existing Expression
      return args[0];
    }
    if (OR_PRODUCTION.equals(production) || OR_QNAME_PRODUCTION.equals(production) || QNAME_OR_PRODUCTION.equals(production) || QNAME_OR_QNAME_PRODUCTION.equals(production))
    {
      Expression secondExpression = (Expression) args[2];
      if (args[0] instanceof BooleanOrExpression)
      {
        // continue the existing BooleanOrExpression if we've already started one
        BooleanOrExpression startExpression = (BooleanOrExpression) args[0];
        return new BooleanOrExpression(startExpression, secondExpression, ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
      }
      Expression firstExpression = (Expression) args[0];
      return new BooleanOrExpression(firstExpression, secondExpression, ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
