package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME_LIST;
import static compiler.language.parser.ParseType.RPAREN;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.QNameElement;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 17 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class NestedQNameListRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(LPAREN, QNAME_LIST, RPAREN);

  public NestedQNameListRule()
  {
    super(NESTED_QNAME_LIST, PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<QNameElement> list = (ParseList<QNameElement>) args[1];
      return new QNameElement(list, ParseInfo.combine((ParseInfo) args[0], list.getParseInfo(), (ParseInfo) args[2]));
    }
    throw badTypeList();
  }

}
