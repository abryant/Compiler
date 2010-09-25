package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.QNAME_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.misc.QNameElementAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 17 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class QNameListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> END_QNAME_PRODUCTION = new Production<ParseType>(QNAME);
  private static final Production<ParseType> END_NESTED_QNAME_LIST_PRODUCTION = new Production<ParseType>(NESTED_QNAME_LIST);
  private static final Production<ParseType> CONTINUATION_QNAME_PRODUCTION = new Production<ParseType>(QNAME, COMMA, QNAME_LIST);
  private static final Production<ParseType> CONTINUATION_NESTED_QNAME_LIST_PRODUCTION = new Production<ParseType>(NESTED_QNAME_LIST, COMMA, QNAME_LIST);

  @SuppressWarnings("unchecked")
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
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (END_QNAME_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      return new ParseList<QNameElementAST>(new QNameElementAST(qname), qname.getParseInfo());
    }
    if (END_NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      return new ParseList<QNameElementAST>(element, element.getParseInfo());
    }
    if (CONTINUATION_QNAME_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<QNameElementAST> list = (ParseList<QNameElementAST>) args[2];
      list.addFirst(new QNameElementAST(qname), ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (CONTINUATION_NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElementAST nestedList = (QNameElementAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<QNameElementAST> list = (ParseList<QNameElementAST>) args[2];
      list.addFirst(nestedList, ParseInfo.combine(nestedList.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
