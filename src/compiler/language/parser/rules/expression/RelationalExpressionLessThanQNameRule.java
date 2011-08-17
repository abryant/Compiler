package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.RELATIONAL_EXPRESSION_LESS_THAN_QNAME;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.QName;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.expression.FieldAccessExpressionAST;
import compiler.language.ast.expression.RelationalExpressionAST;
import compiler.language.ast.expression.RelationalExpressionTypeAST;
import compiler.language.parser.ParseType;
import compiler.language.parser.QNameElementAST;

/*
 * Created on 19 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class RelationalExpressionLessThanQNameRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> QNAME_LESS_THAN_QNAME_PRODUCTION               = new Production<ParseType>(QNAME,             LANGLE, QNAME);
  private static final Production<ParseType> QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION        = new Production<ParseType>(QNAME,             LANGLE, NESTED_QNAME_LIST);
  private static final Production<ParseType> NESTED_QNAME_LESS_THAN_QNAME_PRODUCTION        = new Production<ParseType>(NESTED_QNAME_LIST, LANGLE, QNAME);
  private static final Production<ParseType> NESTED_QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION = new Production<ParseType>(NESTED_QNAME_LIST, LANGLE, NESTED_QNAME_LIST);

  @SuppressWarnings("unchecked")
  public RelationalExpressionLessThanQNameRule()
  {
    super(RELATIONAL_EXPRESSION_LESS_THAN_QNAME, QNAME_LESS_THAN_QNAME_PRODUCTION,
                                                 QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION,
                                                 NESTED_QNAME_LESS_THAN_QNAME_PRODUCTION,
                                                 NESTED_QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (QNAME_LESS_THAN_QNAME_PRODUCTION.equals(production))
    {
      QName firstQName = (QName) args[0];
      QName secondQName = (QName) args[2];
      ExpressionAST firstExpression = new FieldAccessExpressionAST(firstQName, firstQName.getLexicalPhrase());
      ExpressionAST secondExpression = new FieldAccessExpressionAST(secondQName, secondQName.getLexicalPhrase());
      return new RelationalExpressionAST(firstExpression, RelationalExpressionTypeAST.LESS_THAN, secondExpression,
                                      LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[1], secondExpression.getLexicalPhrase()));
    }
    if (QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      QNameElementAST element = (QNameElementAST) args[2];
      ExpressionAST firstExpression = new FieldAccessExpressionAST(qname, qname.getLexicalPhrase());
      ExpressionAST secondExpression = element.toExpression();
      return new RelationalExpressionAST(firstExpression, RelationalExpressionTypeAST.LESS_THAN, secondExpression,
                                      LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[1], secondExpression.getLexicalPhrase()));
    }
    if (NESTED_QNAME_LESS_THAN_QNAME_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      QName qname = (QName) args[2];
      ExpressionAST firstExpression = element.toExpression();
      ExpressionAST secondExpression = new FieldAccessExpressionAST(qname, qname.getLexicalPhrase());
      return new RelationalExpressionAST(firstExpression, RelationalExpressionTypeAST.LESS_THAN, secondExpression,
                                      LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[1], secondExpression.getLexicalPhrase()));
    }
    if (NESTED_QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION.equals(production))
    {
      QNameElementAST firstElement = (QNameElementAST) args[0];
      QNameElementAST secondElement = (QNameElementAST) args[2];
      ExpressionAST firstExpression = firstElement.toExpression();
      ExpressionAST secondExpression = secondElement.toExpression();
      return new RelationalExpressionAST(firstExpression, RelationalExpressionTypeAST.LESS_THAN, secondExpression,
                                      LexicalPhrase.combine(firstExpression.getLexicalPhrase(), (LexicalPhrase) args[1], secondExpression.getLexicalPhrase()));
    }
    throw badTypeList();
  }

}
