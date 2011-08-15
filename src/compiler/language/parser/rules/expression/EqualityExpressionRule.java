package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.DOUBLE_EQUALS;
import static compiler.language.parser.ParseType.EQUALITY_EXPRESSION;
import static compiler.language.parser.ParseType.EXCLAIMATION_MARK_EQUALS;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;
import static compiler.language.parser.ParseType.RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.EqualityExpressionAST;
import compiler.language.ast.expression.EqualityExpressionTypeAST;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.parser.ParseType;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class EqualityExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION                  = new Production<ParseType>(RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME);
  private static final Production<ParseType> EQUALS_PRODUCTION                 = new Production<ParseType>(EQUALITY_EXPRESSION,           DOUBLE_EQUALS,            RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME);
  private static final Production<ParseType> EQUALS_QNAME_PRODUCTION           = new Production<ParseType>(EQUALITY_EXPRESSION,           DOUBLE_EQUALS,            QNAME_OR_LESS_THAN_EXPRESSION);
  private static final Production<ParseType> QNAME_EQUALS_PRODUCTION           = new Production<ParseType>(QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_EQUALS,            RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME);
  private static final Production<ParseType> QNAME_EQUALS_QNAME_PRODUCTION     = new Production<ParseType>(QNAME_OR_LESS_THAN_EXPRESSION, DOUBLE_EQUALS,            QNAME_OR_LESS_THAN_EXPRESSION);
  private static final Production<ParseType> NOT_EQUALS_PRODUCTION             = new Production<ParseType>(EQUALITY_EXPRESSION,           EXCLAIMATION_MARK_EQUALS, RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME);
  private static final Production<ParseType> NOT_EQUALS_QNAME_PRODUCTION       = new Production<ParseType>(EQUALITY_EXPRESSION,           EXCLAIMATION_MARK_EQUALS, QNAME_OR_LESS_THAN_EXPRESSION);
  private static final Production<ParseType> QNAME_NOT_EQUALS_PRODUCTION       = new Production<ParseType>(QNAME_OR_LESS_THAN_EXPRESSION, EXCLAIMATION_MARK_EQUALS, RELATIONAL_EXPRESSION_NOT_LESS_THAN_QNAME);
  private static final Production<ParseType> QNAME_NOT_EQUALS_QNAME_PRODUCTION = new Production<ParseType>(QNAME_OR_LESS_THAN_EXPRESSION, EXCLAIMATION_MARK_EQUALS, QNAME_OR_LESS_THAN_EXPRESSION);

  @SuppressWarnings("unchecked")
  public EqualityExpressionRule()
  {
    super(EQUALITY_EXPRESSION, START_PRODUCTION,
                               EQUALS_PRODUCTION,     EQUALS_QNAME_PRODUCTION,     QNAME_EQUALS_PRODUCTION,     QNAME_EQUALS_QNAME_PRODUCTION,
                               NOT_EQUALS_PRODUCTION, NOT_EQUALS_QNAME_PRODUCTION, QNAME_NOT_EQUALS_PRODUCTION, QNAME_NOT_EQUALS_QNAME_PRODUCTION);
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
      // return the existing expression
      return args[0];
    }

    EqualityExpressionTypeAST separator = null;
    if (EQUALS_PRODUCTION.equals(production) || EQUALS_QNAME_PRODUCTION.equals(production) || QNAME_EQUALS_PRODUCTION.equals(production) || QNAME_EQUALS_QNAME_PRODUCTION.equals(production))
    {
      separator = EqualityExpressionTypeAST.EQUAL;
    }
    else if (NOT_EQUALS_PRODUCTION.equals(production) || NOT_EQUALS_QNAME_PRODUCTION.equals(production) || QNAME_NOT_EQUALS_PRODUCTION.equals(production) || QNAME_NOT_EQUALS_QNAME_PRODUCTION.equals(production))
    {
      separator = EqualityExpressionTypeAST.NOT_EQUAL;
    }
    else
    {
      throw badTypeList();
    }
    ExpressionAST newExpression = (ExpressionAST) args[2];

    if (args[0] instanceof EqualityExpressionAST)
    {
      // continue the current EqualityExpressionAST if we've started one
      EqualityExpressionAST startExpression = (EqualityExpressionAST) args[0];
      return new EqualityExpressionAST(startExpression, separator, newExpression, LexicalPhrase.combine(startExpression.getLexicalPhrase(), (LexicalPhrase) args[1], newExpression.getLexicalPhrase()));
    }
    ExpressionAST firstExpression = (ExpressionAST) args[0];
    return new EqualityExpressionAST(firstExpression, separator, newExpression, LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[1], newExpression.getLexicalPhrase()));
  }

}
