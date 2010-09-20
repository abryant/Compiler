package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.RELATIONAL_EXPRESSION_LESS_THAN_QNAME;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.FieldAccessExpression;
import compiler.language.ast.expression.RelationalExpression;
import compiler.language.ast.expression.RelationalExpressionType;
import compiler.language.ast.misc.QName;
import compiler.language.ast.misc.QNameElement;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

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
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (QNAME_LESS_THAN_QNAME_PRODUCTION.equals(production))
    {
      QName firstQName = (QName) args[0];
      QName secondQName = (QName) args[2];
      Expression firstExpression = new FieldAccessExpression(firstQName, firstQName.getParseInfo());
      Expression secondExpression = new FieldAccessExpression(secondQName, secondQName.getParseInfo());
      return new RelationalExpression(firstExpression, RelationalExpressionType.LESS_THAN, secondExpression,
                                      ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    if (QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      QNameElement element = (QNameElement) args[2];
      Expression firstExpression = new FieldAccessExpression(qname, qname.getParseInfo());
      Expression secondExpression = element.toExpression();
      return new RelationalExpression(firstExpression, RelationalExpressionType.LESS_THAN, secondExpression,
                                      ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    if (NESTED_QNAME_LESS_THAN_QNAME_PRODUCTION.equals(production))
    {
      QNameElement element = (QNameElement) args[0];
      QName qname = (QName) args[2];
      Expression firstExpression = element.toExpression();
      Expression secondExpression = new FieldAccessExpression(qname, qname.getParseInfo());
      return new RelationalExpression(firstExpression, RelationalExpressionType.LESS_THAN, secondExpression,
                                      ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    if (NESTED_QNAME_LESS_THAN_NESTED_QNAME_PRODUCTION.equals(production))
    {
      QNameElement firstElement = (QNameElement) args[0];
      QNameElement secondElement = (QNameElement) args[2];
      Expression firstExpression = firstElement.toExpression();
      Expression secondExpression = secondElement.toExpression();
      return new RelationalExpression(firstExpression, RelationalExpressionType.LESS_THAN, secondExpression,
                                      ParseInfo.combine(firstExpression.getParseInfo(), (ParseInfo) args[1], secondExpression.getParseInfo()));
    }
    throw badTypeList();
  }

}
