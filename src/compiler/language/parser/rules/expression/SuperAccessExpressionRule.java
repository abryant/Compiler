package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.SUPER_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.SuperAccessExpression;
import compiler.language.ast.misc.QName;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 15 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class SuperAccessExpressionRule extends Rule
{

  private static final Object[] SUPER_PRODUCTION = new Object[] {SUPER_KEYWORD};
  private static final Object[] QNAME_SUPER_PRODUCTION = new Object[] {QNAME, DOT, SUPER_KEYWORD};

  public SuperAccessExpressionRule()
  {
    super(SUPER_ACCESS_EXPRESSION, SUPER_PRODUCTION, QNAME_SUPER_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == SUPER_PRODUCTION)
    {
      return new SuperAccessExpression(null, (ParseInfo) args[0]);
    }
    if (types == QNAME_SUPER_PRODUCTION)
    {
      QName qname = (QName) args[0];
      return new SuperAccessExpression(qname, ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1], (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
