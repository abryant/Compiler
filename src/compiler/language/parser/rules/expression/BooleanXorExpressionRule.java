package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BOOLEAN_AND_EXPRESSION;
import static compiler.language.parser.ParseType.BOOLEAN_XOR_EXPRESSION;
import static compiler.language.parser.ParseType.DOUBLE_CARET;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BooleanXorExpressionAST;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class BooleanXorExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION           = new Production<ParseType>(BOOLEAN_AND_EXPRESSION);
  private static final Production<ParseType> XOR_PRODUCTION             = new Production<ParseType>(BOOLEAN_XOR_EXPRESSION,        DOUBLE_CARET, BOOLEAN_AND_EXPRESSION);
  private static final Production<ParseType> XOR_QNAME_PRODUCTION       = new Production<ParseType>(BOOLEAN_XOR_EXPRESSION,        DOUBLE_CARET, QNAME_OR_LESS_THAN_EXPRESSION);
  private static final Production<ParseType> QNAME_XOR_PRODUCTION       = new Production<ParseType>(QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_CARET, BOOLEAN_AND_EXPRESSION);
  private static final Production<ParseType> QNAME_XOR_QNAME_PRODUCTION = new Production<ParseType>(QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_CARET, QNAME_OR_LESS_THAN_EXPRESSION);

  @SuppressWarnings("unchecked")
  public BooleanXorExpressionRule()
  {
    super(BOOLEAN_XOR_EXPRESSION, START_PRODUCTION, XOR_PRODUCTION, XOR_QNAME_PRODUCTION, QNAME_XOR_PRODUCTION, QNAME_XOR_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
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
      if (args[0] instanceof BooleanXorExpressionAST)
      {
        // continue the existing BooleanXorExpressionAST if we've already started one
        BooleanXorExpressionAST startExpression = (BooleanXorExpressionAST) args[0];
        return new BooleanXorExpressionAST(startExpression, secondExpression, ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
      }
      ExpressionAST firstExpression = (ExpressionAST) args[0];
      return new BooleanXorExpressionAST(firstExpression, secondExpression, ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
