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
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 20 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeDoubleRAngleRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION                   = new Production(TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE, DOUBLE_RANGLE);
  private static final Production POINTER_TYPE_PRODUCTION      = new Production(POINTER_TYPE_DOUBLE_RANGLE);
  private static final Production TUPLE_TYPE_PRODUCTION        = new Production(TUPLE_TYPE_NOT_QNAME_LIST,            DOUBLE_RANGLE);
  private static final Production NESTED_QNAME_LIST_PRODUCTION = new Production(NESTED_QNAME_LIST,                    DOUBLE_RANGLE);

  public TypeDoubleRAngleRule()
  {
    super(TYPE_DOUBLE_RANGLE, PRODUCTION, POINTER_TYPE_PRODUCTION, TUPLE_TYPE_PRODUCTION, NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (POINTER_TYPE_PRODUCTION.equals(production))
    {
      // repackage the ParseContainer<ParseContainer<PointerType>> to a ParseContainer<ParseContainer<Type>>
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<PointerType>> oldContainer = (ParseContainer<ParseContainer<PointerType>>) args[0];
      ParseContainer<Type> firstContainer = new ParseContainer<Type>(oldContainer.getItem().getItem(), oldContainer.getItem().getParseInfo());
      return new ParseContainer<ParseContainer<Type>>(firstContainer, oldContainer.getParseInfo());
    }

    // the other three productions are similar, so they can share code once the Type has been found
    Type type;
    if (PRODUCTION.equals(production) || TUPLE_TYPE_PRODUCTION.equals(production))
    {
      type = (Type) args[0];
    }
    else if (NESTED_QNAME_LIST_PRODUCTION.equals(production))
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
