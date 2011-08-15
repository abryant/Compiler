package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BOOLEAN_AND_EXPRESSION;
import static compiler.language.parser.ParseType.DOUBLE_AMPERSAND;
import static compiler.language.parser.ParseType.EQUALITY_EXPRESSION;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.BooleanAndExpressionAST;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.parser.ParseType;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class BooleanAndExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION           = new Production<ParseType>(EQUALITY_EXPRESSION);
  private static final Production<ParseType> AND_PRODUCTION             = new Production<ParseType>(BOOLEAN_AND_EXPRESSION,        DOUBLE_AMPERSAND, EQUALITY_EXPRESSION);
  private static final Production<ParseType> AND_QNAME_PRODUCTION       = new Production<ParseType>(BOOLEAN_AND_EXPRESSION,        DOUBLE_AMPERSAND, QNAME_OR_LESS_THAN_EXPRESSION);
  private static final Production<ParseType> QNAME_AND_PRODUCTION       = new Production<ParseType>(QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_AMPERSAND, EQUALITY_EXPRESSION);
  private static final Production<ParseType> QNAME_AND_QNAME_PRODUCTION = new Production<ParseType>(QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_AMPERSAND, QNAME_OR_LESS_THAN_EXPRESSION);

  @SuppressWarnings("unchecked")
  public BooleanAndExpressionRule()
  {
    super(BOOLEAN_AND_EXPRESSION, START_PRODUCTION, AND_PRODUCTION, AND_QNAME_PRODUCTION, QNAME_AND_PRODUCTION, QNAME_AND_QNAME_PRODUCTION);
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
      if (args[0] instanceof BooleanAndExpressionAST)
      {
        // continue the current BooleanAndExpressionAST if we've already started one
        BooleanAndExpressionAST startExpression = (BooleanAndExpressionAST) args[0];
        return new BooleanAndExpressionAST(startExpression, secondExpression, LexicalPhrase.combine(startExpression.getLexicalPhrase(), (LexicalPhrase) args[1], secondExpression.getLexicalPhrase()));
      }
      ExpressionAST firstExpression = (ExpressionAST) args[0];
      return new BooleanAndExpressionAST(firstExpression, secondExpression, LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[1], secondExpression.getLexicalPhrase()));
    }
    throw badTypeList();
  }

}
