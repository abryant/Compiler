package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BITWISE_OR_EXPRESSION;
import static compiler.language.parser.ParseType.BITWISE_XOR_EXPRESSION;
import static compiler.language.parser.ParseType.PIPE;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BitwiseOrExpression;
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
public class BitwiseOrExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production START_PRODUCTION          = new Production(BITWISE_XOR_EXPRESSION);
  private static final Production OR_PRODUCTION             = new Production(BITWISE_OR_EXPRESSION, PIPE, BITWISE_XOR_EXPRESSION);
  private static final Production OR_QNAME_PRODUCTION       = new Production(BITWISE_OR_EXPRESSION, PIPE, QNAME_EXPRESSION);
  private static final Production QNAME_OR_PRODUCTION       = new Production(QNAME_EXPRESSION,      PIPE, BITWISE_XOR_EXPRESSION);
  private static final Production QNAME_OR_QNAME_PRODUCTION = new Production(QNAME_EXPRESSION,      PIPE, QNAME_EXPRESSION);

  public BitwiseOrExpressionRule()
  {
    super(BITWISE_OR_EXPRESSION, START_PRODUCTION, OR_PRODUCTION, OR_QNAME_PRODUCTION, QNAME_OR_PRODUCTION, QNAME_OR_QNAME_PRODUCTION);
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
      if (args[0] instanceof BitwiseOrExpression)
      {
        // continue the current BitwiseOrExpression if we've already started one
        BitwiseOrExpression startExpression = (BitwiseOrExpression) args[0];
        return new BitwiseOrExpression(startExpression, secondExpression, ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
      }
      Expression firstExpression = (Expression) args[0];
      return new BitwiseOrExpression(firstExpression, secondExpression, ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
