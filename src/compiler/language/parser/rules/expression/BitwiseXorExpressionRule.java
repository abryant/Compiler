package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BITWISE_AND_EXPRESSION;
import static compiler.language.parser.ParseType.BITWISE_XOR_EXPRESSION;
import static compiler.language.parser.ParseType.CARET;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BitwiseXorExpression;
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
public class BitwiseXorExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production START_PRODUCTION           = new Production(BITWISE_AND_EXPRESSION);
  private static final Production XOR_PRODUCTION             = new Production(BITWISE_XOR_EXPRESSION, CARET, BITWISE_AND_EXPRESSION);
  private static final Production XOR_QNAME_PRODUCTION       = new Production(BITWISE_XOR_EXPRESSION, CARET, QNAME_EXPRESSION);
  private static final Production QNAME_XOR_PRODUCTION       = new Production(QNAME_EXPRESSION,       CARET, BITWISE_AND_EXPRESSION);
  private static final Production QNAME_XOR_QNAME_PRODUCTION = new Production(QNAME_EXPRESSION,       CARET, QNAME_EXPRESSION);

  public BitwiseXorExpressionRule()
  {
    super(BITWISE_XOR_EXPRESSION, START_PRODUCTION, XOR_PRODUCTION, XOR_QNAME_PRODUCTION, QNAME_XOR_PRODUCTION, QNAME_XOR_QNAME_PRODUCTION);
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
    if (XOR_PRODUCTION.equals(production) || XOR_QNAME_PRODUCTION.equals(production) || QNAME_XOR_PRODUCTION.equals(production) || QNAME_XOR_QNAME_PRODUCTION.equals(production))
    {
      Expression secondExpression = (Expression) args[2];
      if (args[0] instanceof BitwiseXorExpression)
      {
        // continue the current BitwiseXorExpression if we've already started one
        BitwiseXorExpression startExpression = (BitwiseXorExpression) args[0];
        return new BitwiseXorExpression(startExpression, secondExpression, ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
      }
      Expression firstExpression = (Expression) args[0];
      return new BitwiseXorExpression(firstExpression, secondExpression, ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
