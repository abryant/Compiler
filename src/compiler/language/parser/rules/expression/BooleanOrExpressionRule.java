package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BOOLEAN_OR_EXPRESSION;
import static compiler.language.parser.ParseType.BOOLEAN_XOR_EXPRESSION;
import static compiler.language.parser.ParseType.DOUBLE_PIPE;
import static compiler.language.parser.ParseType.QNAME_OR_LESS_THAN_EXPRESSION;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
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
      ExpressionAST firstExpression = (ExpressionAST) args[0];
      ExpressionAST secondExpression = (ExpressionAST) args[2];
      return new BooleanOrExpressionAST(firstExpression, secondExpression, LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[1], secondExpression.getLexicalPhrase()));
    }
    throw badTypeList();
  }

}
