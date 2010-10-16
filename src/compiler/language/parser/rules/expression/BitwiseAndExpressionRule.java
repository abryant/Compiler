package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.AMPERSAND;
import static compiler.language.parser.ParseType.BITWISE_AND_EXPRESSION;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import static compiler.language.parser.ParseType.SHIFT_EXPRESSION;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BitwiseAndExpressionAST;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.parser.ParseType;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class BitwiseAndExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION           = new Production<ParseType>(SHIFT_EXPRESSION);
  private static final Production<ParseType> AND_PRODUCTION             = new Production<ParseType>(BITWISE_AND_EXPRESSION, AMPERSAND, SHIFT_EXPRESSION);
  private static final Production<ParseType> AND_QNAME_PRODUCTION       = new Production<ParseType>(BITWISE_AND_EXPRESSION, AMPERSAND, QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_AND_PRODUCTION       = new Production<ParseType>(QNAME_EXPRESSION,       AMPERSAND, SHIFT_EXPRESSION);
  private static final Production<ParseType> QNAME_AND_QNAME_PRODUCTION = new Production<ParseType>(QNAME_EXPRESSION,       AMPERSAND, QNAME_EXPRESSION);

  @SuppressWarnings("unchecked")
  public BitwiseAndExpressionRule()
  {
    super(BITWISE_AND_EXPRESSION, START_PRODUCTION,
                                  AND_PRODUCTION, AND_QNAME_PRODUCTION, QNAME_AND_PRODUCTION, QNAME_AND_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      // return the existing ExpressionAST
      return args[0];
    }
    if (AND_PRODUCTION.equals(production) || AND_QNAME_PRODUCTION.equals(production) || QNAME_AND_PRODUCTION.equals(production) || QNAME_AND_QNAME_PRODUCTION.equals(production))
    {
      ExpressionAST secondExpression = (ExpressionAST) args[2];
      if (args[0] instanceof BitwiseAndExpressionAST)
      {
        // continue the current BitwiseAndExpressionAST if we've already started one
        BitwiseAndExpressionAST startExpression = (BitwiseAndExpressionAST) args[0];
        return new BitwiseAndExpressionAST(startExpression, secondExpression, ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
      }
      ExpressionAST firstExpression = (ExpressionAST) args[0];
      return new BitwiseAndExpressionAST(firstExpression, secondExpression, ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
