package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BOOLEAN_OR_EXPRESSION;
import static compiler.language.parser.ParseType.BOOLEAN_XOR_EXPRESSION;
import static compiler.language.parser.ParseType.DOUBLE_PIPE;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.BooleanOrExpressionAST;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.parser.ParseType;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class BooleanOrExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION          = new Production<ParseType>(BOOLEAN_XOR_EXPRESSION);
  private static final Production<ParseType> OR_PRODUCTION             = new Production<ParseType>(BOOLEAN_OR_EXPRESSION,         DOUBLE_PIPE, BOOLEAN_XOR_EXPRESSION);
  private static final Production<ParseType> OR_QNAME_PRODUCTION       = new Production<ParseType>(BOOLEAN_OR_EXPRESSION,         DOUBLE_PIPE, QNAME_OR_LESS_THAN_EXPRESSION);
  private static final Production<ParseType> QNAME_OR_PRODUCTION       = new Production<ParseType>(QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_PIPE, BOOLEAN_XOR_EXPRESSION);
  private static final Production<ParseType> QNAME_OR_QNAME_PRODUCTION = new Production<ParseType>(QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_PIPE, QNAME_OR_LESS_THAN_EXPRESSION);

  @SuppressWarnings("unchecked")
  public BooleanOrExpressionRule()
  {
    super(BOOLEAN_OR_EXPRESSION, START_PRODUCTION, OR_PRODUCTION, OR_QNAME_PRODUCTION, QNAME_OR_PRODUCTION, QNAME_OR_QNAME_PRODUCTION);
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
    if (OR_PRODUCTION.equals(production) || OR_QNAME_PRODUCTION.equals(production) || QNAME_OR_PRODUCTION.equals(production) || QNAME_OR_QNAME_PRODUCTION.equals(production))
    {
      ExpressionAST secondExpression = (ExpressionAST) args[2];
      if (args[0] instanceof BooleanOrExpressionAST)
      {
        // continue the existing BooleanOrExpressionAST if we've already started one
        BooleanOrExpressionAST startExpression = (BooleanOrExpressionAST) args[0];
        return new BooleanOrExpressionAST(startExpression, secondExpression, ParseInfo.combine(startExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
      }
      ExpressionAST firstExpression = (ExpressionAST) args[0];
      return new BooleanOrExpressionAST(firstExpression, secondExpression, ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
