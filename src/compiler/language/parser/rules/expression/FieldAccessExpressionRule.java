package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.FIELD_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.FIELD_ACCESS_EXPRESSION_NOT_QNAME;
import static compiler.language.parser.ParseType.QNAME;

import compiler.language.ast.expression.FieldAccessExpression;
import compiler.language.ast.misc.QName;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class FieldAccessExpressionRule extends Rule
{

  private static final Object[] QNAME_PRODUCTION = new Object[] {QNAME};
  private static final Object[] PRODUCTION = new Object[] {FIELD_ACCESS_EXPRESSION_NOT_QNAME};

  public FieldAccessExpressionRule()
  {
    super(FIELD_ACCESS_EXPRESSION, QNAME_PRODUCTION, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      return new FieldAccessExpression(qname, qname.getParseInfo());
    }
    if (types == PRODUCTION)
    {
      // return the existing FieldAccessExpression
      return args[0];
    }
    throw badTypeList();
  }

}
