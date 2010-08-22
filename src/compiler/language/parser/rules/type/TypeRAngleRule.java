package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.POINTER_TYPE_RANGLE;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.TUPLE_TYPE_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE;
import static compiler.language.parser.ParseType.TYPE_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameElement;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.Type;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeRAngleRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE, RANGLE};
  private static final Object[] POINTER_TYPE_PRODUCTION = new Object[] {POINTER_TYPE_RANGLE};
  private static final Object[] TUPLE_TYPE_PRODUCTION = new Object[] {TUPLE_TYPE_NOT_QNAME_LIST, RANGLE};
  private static final Object[] NESTED_QNAME_LIST_PRODUCTION = new Object[] {NESTED_QNAME_LIST, RANGLE};

  public TypeRAngleRule()
  {
    super(TYPE_RANGLE, PRODUCTION, POINTER_TYPE_PRODUCTION, TUPLE_TYPE_PRODUCTION, NESTED_QNAME_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION || types == TUPLE_TYPE_PRODUCTION)
    {
      Type type = (Type) args[0];
      return new ParseContainer<Type>(type, ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1]));
    }
    if (types == POINTER_TYPE_PRODUCTION)
    {
      // change the ParseContainer<PointerType> for a ParseContainer<Type>
      @SuppressWarnings("unchecked")
      ParseContainer<PointerType> oldContainer = (ParseContainer<PointerType>) args[0];
      return new ParseContainer<Type>(oldContainer.getItem(), oldContainer.getParseInfo());
    }
    if (types == NESTED_QNAME_LIST_PRODUCTION)
    {
      QNameElement element = (QNameElement) args[0];
      Type type = element.toType();
      return new ParseContainer<Type>(type, ParseInfo.combine(type.getParseInfo(), (ParseInfo) args[1]));
    }
    throw badTypeList();
  }

}
