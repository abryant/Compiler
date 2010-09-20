package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_LIST;
import static compiler.language.parser.ParseType.TYPE_LIST_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_NOT_QNAME_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.QName;
import compiler.language.ast.misc.QNameElement;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.Type;
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
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (QNAME_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];
      @SuppressWarnings("unchecked")
      ParseList<Type> list = (ParseList<Type>) args[2];
      list.addFirst(new PointerType(qname, qname.getParseInfo()),
                    ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (NESTED_QNAME_PRODUCTION.equals(production))
    {
      QNameElement element = (QNameElement) args[0];
      @SuppressWarnings("unchecked")
      ParseList<Type> list = (ParseList<Type>) args[2];
      list.addFirst(element.toType(), ParseInfo.combine(element.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (REAL_TYPE_LIST_PRODUCTION.equals(production))
    {
      Type type = (Type) args[0];
      @SuppressWarnings("unchecked")
      ParseList<Type> list = (ParseList<Type>) args[2];
      list.addFirst(type, ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (REAL_TYPE_PRODUCTION.equals(production))
    {
      Type type = (Type) args[0];
      return new ParseList<Type>(type, type.getParseInfo());
    }
    throw badTypeList();
  }

}
