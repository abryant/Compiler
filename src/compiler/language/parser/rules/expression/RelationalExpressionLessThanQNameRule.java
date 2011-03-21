package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.RELATIONAL_EXPRESSION_LESS_THAN_QNAME;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.expression.FieldAccessExpressionAST;
import compiler.language.ast.expression.RelationalExpressionAST;
import compiler.language.ast.expression.RelationalExpressionTypeAST;
import compiler.language.ast.misc.QNameAST;
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
      QNameAST firstQName = (QNameAST) args[0];
      QNameAST secondQName = (QNameAST) args[2];
      ExpressionAST firstExpression = new FieldAccessExpressionAST(firstQName, firstQName.getParseInfo());
      ExpressionAST secondExpression = new FieldAccessExpressionAST(secondQName, secondQName.getParseInfo());
      return new RelationalExpressionAST(firstExpression, RelationalExpressionTypeAST.LESS_THAN, secondExpression,
                                      ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    if (QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      QNameElementAST element = (QNameElementAST) args[2];
      ExpressionAST firstExpression = new FieldAccessExpressionAST(qname, qname.getParseInfo());
      ExpressionAST secondExpression = element.toExpression();
      return new RelationalExpressionAST(firstExpression, RelationalExpressionTypeAST.LESS_THAN, secondExpression,
                                      ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    if (NESTED_QNAME_LESS_THAN_QNAME_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      QNameAST qname = (QNameAST) args[2];
      ExpressionAST firstExpression = element.toExpression();
      ExpressionAST secondExpression = new FieldAccessExpressionAST(qname, qname.getParseInfo());
      return new RelationalExpressionAST(firstExpression, RelationalExpressionTypeAST.LESS_THAN, secondExpression,
                                      ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    if (NESTED_QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION.equals(production))
    {
      QNameElementAST firstElement = (QNameElementAST) args[0];
      QNameElementAST secondElement = (QNameElementAST) args[2];
      ExpressionAST firstExpression = firstElement.toExpression();
      ExpressionAST secondExpression = secondElement.toExpression();
      return new RelationalExpressionAST(firstExpression, RelationalExpressionTypeAST.LESS_THAN, secondExpression,
                                      ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
