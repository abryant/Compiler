package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.FIELD_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.FIELD_ACCESS_EXPRESSION_NOT_QNAME;
import static compiler.language.parser.ParseType.QNAME;

import compiler.language.ast.expression.FieldAccessExpression;
import compiler.language.ast.misc.QName;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class FieldAccessExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production QNAME_PRODUCTION = new Production(QNAME);
  private static final Production PRODUCTION = new Production(FIELD_ACCESS_EXPRESSION_NOT_QNAME);

  public FieldAccessExpressionRule()
  {
    super(FIELD_ACCESS_EXPRESSION, QNAME_PRODUCTION, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      return new FieldAccessExpression(qname, qname.getParseInfo());
    }
    if (PRODUCTION.equals(production))
    {
      // return the existing FieldAccessExpression
      return args[0];
    }
    throw badTypeList();
  }

}
