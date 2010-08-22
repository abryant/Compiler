package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.POINTER_TYPE_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.TUPLE_TYPE_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameElement;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.Type;
import compiler.language.parser.ParseUtil;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 20 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeDoubleRAngleRule extends Rule
{

  private static final Object[] PRODUCTION                   = new Object[] {TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE, DOUBLE_RANGLE};
  private static final Object[] POINTER_TYPE_PRODUCTION      = new Object[] {POINTER_TYPE_DOUBLE_RANGLE};
  private static final Object[] TUPLE_TYPE_PRODUCTION        = new Object[] {TUPLE_TYPE_NOT_QNAME_LIST,            DOUBLE_RANGLE};
  private static final Object[] NESTED_QNAME_LIST_PRODUCTION = new Object[] {NESTED_QNAME_LIST,                    DOUBLE_RANGLE};

  public TypeDoubleRAngleRule()
  {
    super(TYPE_DOUBLE_RANGLE, PRODUCTION, POINTER_TYPE_PRODUCTION, TUPLE_TYPE_PRODUCTION, NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == POINTER_TYPE_PRODUCTION)
    {
      // repackage the ParseContainer<ParseContainer<PointerType>> to a ParseContainer<ParseContainer<Type>>
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<PointerType>> oldContainer = (ParseContainer<ParseContainer<PointerType>>) args[0];
      ParseContainer<Type> firstContainer = new ParseContainer<Type>(oldContainer.getItem().getItem(), oldContainer.getItem().getParseInfo());
      return new ParseContainer<ParseContainer<Type>>(firstContainer, oldContainer.getParseInfo());
    }

    // the other three productions are similar, so they can share code once the Type has been found
    Type type;
    if (types == PRODUCTION || types == TUPLE_TYPE_PRODUCTION)
    {
      type = (Type) args[0];
    }
    else if (types == NESTED_QNAME_LIST_PRODUCTION)
    {
      QNameElement element = (QNameElement) args[0];
      type = element.toType();
    }
    else
    {
      throw badTypeList();
    }

    ParseInfo doubleRAngleInfo = (ParseInfo) args[1];
    ParseInfo firstRAngleInfo = ParseUtil.splitDoubleRAngle(doubleRAngleInfo);
    ParseContainer<Type> firstContainer = new ParseContainer<Type>(type, ParseInfo.combine(type.getParseInfo(), firstRAngleInfo));
    return new ParseContainer<ParseContainer<Type>>(firstContainer, ParseInfo.combine(type.getParseInfo(), doubleRAngleInfo));
  }

}
