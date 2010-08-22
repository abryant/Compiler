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
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 17 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeListNotQNameListRule extends Rule
{

  private static final Object[] QNAME_PRODUCTION = new Object[] {QNAME, COMMA, TYPE_LIST_NOT_QNAME_LIST};
  private static final Object[] NESTED_QNAME_PRODUCTION = new Object[] {NESTED_QNAME_LIST, COMMA, TYPE_LIST_NOT_QNAME_LIST};
  private static final Object[] REAL_TYPE_LIST_PRODUCTION = new Object[] {TYPE_NOT_QNAME_LIST, COMMA, TYPE_LIST};
  private static final Object[] REAL_TYPE_PRODUCTION = new Object[] {TYPE_NOT_QNAME_LIST};

  public TypeListNotQNameListRule()
  {
    super(TYPE_LIST_NOT_QNAME_LIST, QNAME_PRODUCTION, NESTED_QNAME_PRODUCTION,
                                    REAL_TYPE_LIST_PRODUCTION, REAL_TYPE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == QNAME_PRODUCTION)
    {
      QName qname = (QName) args[0];
      @SuppressWarnings("unchecked")
      ParseList<Type> list = (ParseList<Type>) args[2];
      list.addFirst(new PointerType(qname, qname.getParseInfo()),
                    ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (types == NESTED_QNAME_PRODUCTION)
    {
      QNameElement element = (QNameElement) args[0];
      @SuppressWarnings("unchecked")
      ParseList<Type> list = (ParseList<Type>) args[2];
      list.addFirst(element.toType(), ParseInfo.combine(element.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (types == REAL_TYPE_LIST_PRODUCTION)
    {
      Type type = (Type) args[0];
      @SuppressWarnings("unchecked")
      ParseList<Type> list = (ParseList<Type>) args[2];
      list.addFirst(type, ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1], list.getParseInfo()));
      return list;
    }
    if (types == REAL_TYPE_PRODUCTION)
    {
      Type type = (Type) args[0];
      return new ParseList<Type>(type, type.getParseInfo());
    }
    throw badTypeList();
  }

}
