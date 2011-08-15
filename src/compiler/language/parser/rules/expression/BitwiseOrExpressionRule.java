package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.BITWISE_OR_EXPRESSION;
import static compiler.language.parser.ParseType.BITWISE_XOR_EXPRESSION;
import static compiler.language.parser.ParseType.PIPE;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.BitwiseOrExpressionAST;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.parser.ParseType;

/*
 * Created on 4 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class BitwiseOrExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION          = new Production<ParseType>(BITWISE_XOR_EXPRESSION);
  private static final Production<ParseType> OR_PRODUCTION             = new Production<ParseType>(BITWISE_OR_EXPRESSION, PIPE, BITWISE_XOR_EXPRESSION);
  private static final Production<ParseType> OR_QNAME_PRODUCTION       = new Production<ParseType>(BITWISE_OR_EXPRESSION, PIPE, QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_OR_PRODUCTION       = new Production<ParseType>(QNAME_EXPRESSION,      PIPE, BITWISE_XOR_EXPRESSION);
  private static final Production<ParseType> QNAME_OR_QNAME_PRODUCTION = new Production<ParseType>(QNAME_EXPRESSION,      PIPE, QNAME_EXPRESSION);

  @SuppressWarnings("unchecked")
  public BitwiseOrExpressionRule()
  {
    super(BITWISE_OR_EXPRESSION, START_PRODUCTION, OR_PRODUCTION, OR_QNAME_PRODUCTION, QNAME_OR_PRODUCTION, QNAME_OR_QNAME_PRODUCTION);
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
      if (args[0] instanceof BitwiseOrExpressionAST)
      {
        // continue the current BitwiseOrExpressionAST if we've already started one
        BitwiseOrExpressionAST startExpression = (BitwiseOrExpressionAST) args[0];
        return new BitwiseOrExpressionAST(startExpression, secondExpression, LexicalPhrase.combine(startExpression.getLexicalPhrase(), (LexicalPhrase) args[1], secondExpression.getLexicalPhrase()));
      }
      ExpressionAST firstExpression = (ExpressionAST) args[0];
      return new BitwiseOrExpressionAST(firstExpression, secondExpression, LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[1], secondExpression.getLexicalPhrase()));
    }
    throw badTypeList();
  }

}
