package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.THIS_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.THIS_KEYWORD;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ThisAccessExpression;
import compiler.language.ast.misc.QName;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ThisAccessExpressionRule extends Rule
{

  private static final Object[] THIS_PRODUCTION = new Object[] {THIS_KEYWORD};
  private static final Object[] QNAME_THIS_PRODUCTION = new Object[] {QNAME, DOT, THIS_KEYWORD};

  public ThisAccessExpressionRule()
  {
    super(THIS_ACCESS_EXPRESSION, THIS_PRODUCTION, QNAME_THIS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == THIS_PRODUCTION)
    {
      return new ThisAccessExpression(null, (ParseInfo) args[0]);
    }
    if (types == QNAME_THIS_PRODUCTION)
    {
      QName qname = (QName) args[0];
      return new ThisAccessExpression(qname, ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1], (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
