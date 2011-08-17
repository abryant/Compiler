package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ARRAY_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.DIMENSION_EXPRESSION;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.PRIMARY_NO_TRAILING_DIMENSIONS_NOT_QNAME;
import static compiler.language.parser.ParseType.QNAME;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.QName;
import compiler.language.ast.expression.ArrayAccessExpressionAST;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.expression.FieldAccessExpressionAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseType;
import compiler.language.parser.QNameElementAST;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ArrayAccessExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION                   = new Production<ParseType>(PRIMARY_NO_TRAILING_DIMENSIONS_NOT_QNAME, DIMENSION_EXPRESSION);
  private static final Production<ParseType> QNAME_PRODUCTION             = new Production<ParseType>(QNAME,                                    DIMENSION_EXPRESSION);
  private static final Production<ParseType> NESTED_QNAME_LIST_PRODUCTION = new Production<ParseType>(NESTED_QNAME_LIST,                        DIMENSION_EXPRESSION);

  @SuppressWarnings("unchecked")
  public ArrayAccessExpressionRule()
  {
    super(ARRAY_ACCESS_EXPRESSION, PRODUCTION, QNAME_PRODUCTION, NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      ExpressionAST expression = (ExpressionAST) args[0];
      @SuppressWarnings("unchecked")
      ParseContainer<ExpressionAST> dimensionExpression = (ParseContainer<ExpressionAST>) args[1];
      return new ArrayAccessExpressionAST(expression, dimensionExpression.getItem(),
                                       LexicalPhrase.combine(expression.getLexicalPhrase(), dimensionExpression.getLexicalPhrase()));
    }
    if (QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      FieldAccessExpressionAST fieldAccessExpression = new FieldAccessExpressionAST(qname, qname.getLexicalPhrase());
      @SuppressWarnings("unchecked")
      ParseContainer<ExpressionAST> dimensionExpression = (ParseContainer<ExpressionAST>) args[1];
      return new ArrayAccessExpressionAST(fieldAccessExpression, dimensionExpression.getItem(),
                                       LexicalPhrase.combine(qname.getLexicalPhrase(), dimensionExpression.getLexicalPhrase()));
    }
    if (NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      ExpressionAST expression = element.toExpression();
      @SuppressWarnings("unchecked")
      ParseContainer<ExpressionAST> dimensionExpression = (ParseContainer<ExpressionAST>) args[1];
      return new ArrayAccessExpressionAST(expression, dimensionExpression.getItem(),
                                       LexicalPhrase.combine(expression.getLexicalPhrase(), dimensionExpression.getLexicalPhrase()));
    }
    throw badTypeList();
  }

}
