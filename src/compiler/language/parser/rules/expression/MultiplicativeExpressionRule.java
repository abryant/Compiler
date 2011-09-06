package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.FORWARD_SLASH;
import static compiler.language.parser.ParseType.MULTIPLICATIVE_EXPRESSION;
import static compiler.language.parser.ParseType.PERCENT;
import static compiler.language.parser.ParseType.QNAME_EXPRESSION;
import static compiler.language.parser.ParseType.STAR;
import static compiler.language.parser.ParseType.TUPLE_INDEX_EXPRESSION;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.expression.DivisionExpressionAST;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.expression.ModulusExpressionAST;
import compiler.language.ast.expression.MultiplicationExpressionAST;
import compiler.language.parser.ParseType;

/*
 * Created on 8 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class MultiplicativeExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION               = new Production<ParseType>(TUPLE_INDEX_EXPRESSION);
  private static final Production<ParseType> TIMES_PRODUCTION               = new Production<ParseType>(MULTIPLICATIVE_EXPRESSION, STAR,          TUPLE_INDEX_EXPRESSION);
  private static final Production<ParseType> TIMES_QNAME_PRODUCTION         = new Production<ParseType>(MULTIPLICATIVE_EXPRESSION, STAR,          QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_TIMES_PRODUCTION         = new Production<ParseType>(QNAME_EXPRESSION,          STAR,          TUPLE_INDEX_EXPRESSION);
  private static final Production<ParseType> QNAME_TIMES_QNAME_PRODUCTION   = new Production<ParseType>(QNAME_EXPRESSION,          STAR,          QNAME_EXPRESSION);
  private static final Production<ParseType> DIVIDE_PRODUCTION              = new Production<ParseType>(MULTIPLICATIVE_EXPRESSION, FORWARD_SLASH, TUPLE_INDEX_EXPRESSION);
  private static final Production<ParseType> DIVIDE_QNAME_PRODUCTION        = new Production<ParseType>(MULTIPLICATIVE_EXPRESSION, FORWARD_SLASH, QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_DIVIDE_PRODUCTION        = new Production<ParseType>(QNAME_EXPRESSION,          FORWARD_SLASH, TUPLE_INDEX_EXPRESSION);
  private static final Production<ParseType> QNAME_DIVIDE_QNAME_PRODUCTION  = new Production<ParseType>(QNAME_EXPRESSION,          FORWARD_SLASH, QNAME_EXPRESSION);
  private static final Production<ParseType> MODULUS_PRODUCTION             = new Production<ParseType>(MULTIPLICATIVE_EXPRESSION, PERCENT,       TUPLE_INDEX_EXPRESSION);
  private static final Production<ParseType> MODULUS_QNAME_PRODUCTION       = new Production<ParseType>(MULTIPLICATIVE_EXPRESSION, PERCENT,       QNAME_EXPRESSION);
  private static final Production<ParseType> QNAME_MODULUS_PRODUCTION       = new Production<ParseType>(QNAME_EXPRESSION,          PERCENT,       TUPLE_INDEX_EXPRESSION);
  private static final Production<ParseType> QNAME_MODULUS_QNAME_PRODUCTION = new Production<ParseType>(QNAME_EXPRESSION,          PERCENT,       QNAME_EXPRESSION);

  @SuppressWarnings("unchecked")
  public MultiplicativeExpressionRule()
  {
    super(MULTIPLICATIVE_EXPRESSION, START_PRODUCTION,
                                     TIMES_PRODUCTION,   TIMES_QNAME_PRODUCTION,   QNAME_TIMES_PRODUCTION,   QNAME_TIMES_QNAME_PRODUCTION,
                                     DIVIDE_PRODUCTION,  DIVIDE_QNAME_PRODUCTION,  QNAME_DIVIDE_PRODUCTION,  QNAME_DIVIDE_QNAME_PRODUCTION,
                                     MODULUS_PRODUCTION, MODULUS_QNAME_PRODUCTION, QNAME_MODULUS_PRODUCTION, QNAME_MODULUS_QNAME_PRODUCTION);
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

    ExpressionAST leftExpression = (ExpressionAST) args[0];
    ExpressionAST rightExpression = (ExpressionAST) args[2];

    if (TIMES_PRODUCTION.equals(production) || TIMES_QNAME_PRODUCTION.equals(production) || QNAME_TIMES_PRODUCTION.equals(production) || QNAME_TIMES_QNAME_PRODUCTION.equals(production))
    {
      return new MultiplicationExpressionAST(leftExpression, rightExpression, LexicalPhrase.combine(leftExpression.getLexicalPhrase(), (LexicalPhrase) args[1], rightExpression.getLexicalPhrase()));
    }
    else if (DIVIDE_PRODUCTION.equals(production) || DIVIDE_QNAME_PRODUCTION.equals(production) || QNAME_DIVIDE_PRODUCTION.equals(production) || QNAME_DIVIDE_QNAME_PRODUCTION.equals(production))
    {
      return new DivisionExpressionAST(leftExpression, rightExpression, LexicalPhrase.combine(leftExpression.getLexicalPhrase(), (LexicalPhrase) args[1], rightExpression.getLexicalPhrase()));
    }
    else if (MODULUS_PRODUCTION.equals(production) || MODULUS_QNAME_PRODUCTION.equals(production) || QNAME_MODULUS_PRODUCTION.equals(production) || QNAME_MODULUS_QNAME_PRODUCTION.equals(production))
    {
      return new ModulusExpressionAST(leftExpression, rightExpression, LexicalPhrase.combine(leftExpression.getLexicalPhrase(), (LexicalPhrase) args[1], rightExpression.getLexicalPhrase()));
    }
    else
    {
      throw badTypeList();
    }
  }

}
