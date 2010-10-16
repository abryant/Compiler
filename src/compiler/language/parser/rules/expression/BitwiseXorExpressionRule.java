package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BITWISE_AND_EXPRESSION;
import static compiler.language.parser.ParseType.BITWISE_XOR_EXPRESSION;
import static compiler.language.parser.ParseType.CARET;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BitwiseXorExpressionAST;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.parser.ParseType;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class BitwiseXorExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION           = new Production<ParseType>(BITWISE_AND_EXPRESSION);
  private static final Production<ParseType> XOR_PRODUCTION             = new Production<ParseType>(BITWISE_XOR_EXPRESSION, CARET, BITWISE_AND_EXPRESSION);
  private static final Production<ParseType> XOR_QNAME_PRODUCTION       = new Production<ParseType>(BITWISE_XOR_EXPRESSION, CARET, QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_XOR_PRODUCTION       = new Production<ParseType>(QNAME_EXPRESSION,       CARET, BITWISE_AND_EXPRESSION);
  private static final Production<ParseType> QNAME_XOR_QNAME_PRODUCTION = new Production<ParseType>(QNAME_EXPRESSION,       CARET, QNAME_EXPRESSION);

  @SuppressWarnings("unchecked")
  public BitwiseXorExpressionRule()
  {
    super(BITWISE_XOR_EXPRESSION, START_PRODUCTION, XOR_PRODUCTION, XOR_QNAME_PRODUCTION, QNAME_XOR_PRODUCTION, QNAME_XOR_QNAME_PRODUCTION);
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
    if (XOR_PRODUCTION.equals(production) || XOR_QNAME_PRODUCTION.equals(production) || QNAME_XOR_PRODUCTION.equals(production) || QNAME_XOR_QNAME_PRODUCTION.equals(production))
    {
      ExpressionAST secondExpression = (ExpressionAST) args[2];
      if (args[0] instanceof BitwiseXorExpressionAST)
      {
        // continue the current BitwiseXorExpressionAST if we've already started one
        BitwiseXorExpressionAST startExpression = (BitwiseXorExpressionAST) args[0];
        return new BitwiseXorExpressionAST(startExpression, secondExpression, ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
      }
      ExpressionAST firstExpression = (ExpressionAST) args[0];
      return new BitwiseXorExpressionAST(firstExpression, secondExpression, ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
