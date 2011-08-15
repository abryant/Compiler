package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.QNAME_LIST;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.misc.QNameAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;
import compiler.language.parser.QNameElementAST;

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
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (END_QNAME_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      return new ParseList<QNameElementAST>(new QNameElementAST(qname), qname.getLexicalPhrase());
    }
    if (END_NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      return new ParseList<QNameElementAST>(element, element.getLexicalPhrase());
    }
    if (CONTINUATION_QNAME_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<QNameElementAST> list = (ParseList<QNameElementAST>) args[2];
      list.addFirst(new QNameElementAST(qname), LexicalPhrase.combine(qname.getLexicalPhrase(), (LexicalPhrase) args[1], list.getLexicalPhrase()));
      return list;
    }
    if (CONTINUATION_NESTED_QNAME_LIST_PRODUCTION.equals(production))
    {
      QNameElementAST nestedList = (QNameElementAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<QNameElementAST> list = (ParseList<QNameElementAST>) args[2];
      list.addFirst(nestedList, LexicalPhrase.combine(nestedList.getLexicalPhrase(), (LexicalPhrase) args[1], list.getLexicalPhrase()));
      return list;
    }
    throw badTypeList();
  }

}
