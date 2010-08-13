package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.PRIMARY;
import static compiler.language.parser.ParseType.PRIMARY_NOT_QNAME;
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
public class PrimaryRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {PRIMARY_NOT_QNAME};
  private static final Object[] QNAME_PRODUCTION = new Object[] {QNAME};

  public PrimaryRule()
  {
    super(PRIMARY, PRODUCTION, QNAME_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      // return the existing Expression
      return args[0];
    }
    if (types == QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      return new FieldAccessExpression(qname, qname.getParseInfo());
    }
    throw badTypeList();
  }

}
