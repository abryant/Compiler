package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.QNAME_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.QName;
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
public class QNameListRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production END_QNAME_PRODUCTION = new Production(QNAME);
  private static final Production END_NESTED_QNAME_LIST_PRODUCTION = new Production(NESTED_QNAME_LIST);
  private static final Production CONTINUATION_QNAME_PRODUCTION = new Production(QNAME, COMMA, QNAME_LIST);
  private static final Production CONTINUATION_NESTED_QNAME_LIST_PRODUCTION = new Production(NESTED_QNAME_LIST, COMMA, QNAME_LIST);

  public QNameListRule()
  {
    super(QNAME_LIST, END_QNAME_PRODUCTION, END_NESTED_QNAME_LIST_PRODUCTION,
                                CONTINUATION_QNAME_PRODUCTION, CONTINUATION_NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (END_QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      return new ParseList<QNameElement>(new QNameElement(qname), qname.getParseInfo());
    }
    if (END_NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElement element = (QNameElement) args[0];
      return new ParseList<QNameElement>(element, element.getParseInfo());
    }
    if (CONTINUATION_QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      @SuppressWarnings("unchecked")
      ParseList<QNameElement> list = (ParseList<QNameElement>) args[2];
      list.addFirst(new QNameElement(qname), ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (CONTINUATION_NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElement nestedList = (QNameElement) args[0];
      @SuppressWarnings("unchecked")
      ParseList<QNameElement> list = (ParseList<QNameElement>) args[2];
      list.addFirst(nestedList, ParseInfo.combine(nestedList.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
