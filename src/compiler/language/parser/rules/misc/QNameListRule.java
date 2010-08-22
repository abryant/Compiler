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
import compiler.parser.Rule;

/*
 * Created on 17 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class QNameListRule extends Rule
{

  private static final Object[] END_QNAME_PRODUCTION = new Object[] {QNAME};
  private static final Object[] END_NESTED_QNAME_LIST_PRODUCTION = new Object[] {NESTED_QNAME_LIST};
  private static final Object[] CONTINUATION_QNAME_PRODUCTION = new Object[] {QNAME, COMMA, QNAME_LIST};
  private static final Object[] CONTINUATION_NESTED_QNAME_LIST_PRODUCTION = new Object[] {NESTED_QNAME_LIST, COMMA, QNAME_LIST};

  public QNameListRule()
  {
    super(QNAME_LIST, END_QNAME_PRODUCTION, END_NESTED_QNAME_LIST_PRODUCTION,
                                CONTINUATION_QNAME_PRODUCTION, CONTINUATION_NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == END_QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      return new ParseList<QNameElement>(new QNameElement(qname), qname.getParseInfo());
    }
    if (types == END_NESTED_QNAME_LIST_PRODUCTION)
    {
      QNameElement element = (QNameElement) args[0];
      return new ParseList<QNameElement>(element, element.getParseInfo());
    }
    if (types == CONTINUATION_QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      @SuppressWarnings("unchecked")
      ParseList<QNameElement> list = (ParseList<QNameElement>) args[2];
      list.addFirst(new QNameElement(qname), ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (types == CONTINUATION_NESTED_QNAME_LIST_PRODUCTION)
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
