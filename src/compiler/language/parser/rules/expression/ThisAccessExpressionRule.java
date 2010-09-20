package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.THIS_ACCESS_EXPRESSION;
import static compiler.language.parser.ParseType.THIS_KEYWORD;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ThisAccessExpression;
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
public class ThisAccessExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production THIS_PRODUCTION = new Production(THIS_KEYWORD);
  private static final Production QNAME_THIS_PRODUCTION = new Production(QNAME, DOT, THIS_KEYWORD);

  public ThisAccessExpressionRule()
  {
    super(THIS_ACCESS_EXPRESSION, THIS_PRODUCTION, QNAME_THIS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (THIS_PRODUCTION.equals(production))
    {
      return new ThisAccessExpression(null, (ParseInfo) args[0]);
    }
    if (QNAME_THIS_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      return new ThisAccessExpression(qname, ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1], (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
