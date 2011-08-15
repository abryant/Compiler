package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_LIST;
import static compiler.language.parser.ParseType.TYPE_LIST_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_NOT_QNAME_LIST;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;
import compiler.language.parser.QNameElementAST;

/*
 * Created on 17 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeListNotQNameListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> QNAME_PRODUCTION = new Production<ParseType>(QNAME, COMMA, TYPE_LIST_NOT_QNAME_LIST);
  private static final Production<ParseType> NESTED_QNAME_PRODUCTION = new Production<ParseType>(NESTED_QNAME_LIST, COMMA, TYPE_LIST_NOT_QNAME_LIST);
  private static final Production<ParseType> REAL_TYPE_LIST_PRODUCTION = new Production<ParseType>(TYPE_NOT_QNAME_LIST, COMMA, TYPE_LIST);
  private static final Production<ParseType> REAL_TYPE_PRODUCTION = new Production<ParseType>(TYPE_NOT_QNAME_LIST);

  @SuppressWarnings("unchecked")
  public TypeListNotQNameListRule()
  {
    super(TYPE_LIST_NOT_QNAME_LIST, QNAME_PRODUCTION, NESTED_QNAME_PRODUCTION,
                                    REAL_TYPE_LIST_PRODUCTION, REAL_TYPE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (QNAME_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<TypeAST> list = (ParseList<TypeAST>) args[2];
      list.addFirst(new PointerTypeAST(qname, qname.getLexicalPhrase()),
                    LexicalPhrase.combine(qname.getLexicalPhrase(), (LexicalPhrase) args[1], list.getLexicalPhrase()));
      return list;
    }
    if (NESTED_QNAME_PRODUCTION.equals(production))
    {
      QNameElementAST element = (QNameElementAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<TypeAST> list = (ParseList<TypeAST>) args[2];
      list.addFirst(element.toType(), LexicalPhrase.combine(element.getLexicalPhrase(), (LexicalPhrase) args[1], list.getLexicalPhrase()));
      return list;
    }
    if (REAL_TYPE_LIST_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<TypeAST> list = (ParseList<TypeAST>) args[2];
      list.addFirst(type, LexicalPhrase.combine(type.getLexicalPhrase(), (LexicalPhrase) args[1], list.getLexicalPhrase()));
      return list;
    }
    if (REAL_TYPE_PRODUCTION.equals(production))
    {
      TypeAST type = (TypeAST) args[0];
      return new ParseList<TypeAST>(type, type.getLexicalPhrase());
    }
    throw badTypeList();
  }

}
