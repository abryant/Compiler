package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.NESTED_QNAME_LIST;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.TUPLE_TYPE_NOT_QNAME_LIST;
import static compiler.language.parser.ParseType.TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE;
import static compiler.language.parser.ParseType.TYPE_TRIPLE_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameElement;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.Type;
import compiler.language.parser.ParseUtil;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 21 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeTripleRAngleRule extends Rule
{

  private static final Object[] PRODUCTION                    = new Object[] {TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE, TRIPLE_RANGLE};
  private static final Object[] POINTER_TYPE_PRODUCTION       = new Object[] {POINTER_TYPE_TRIPLE_RANGLE};
  private static final Object[] TUPLE_TYPE_PRODUCTION         = new Object[] {TUPLE_TYPE_NOT_QNAME_LIST,            TRIPLE_RANGLE};
  private static final Object[] NESTED_QNAME_LIST_PRODUCTION  = new Object[] {NESTED_QNAME_LIST,                    TRIPLE_RANGLE};

  public TypeTripleRAngleRule()
  {
    super(TYPE_TRIPLE_RANGLE, PRODUCTION, POINTER_TYPE_PRODUCTION, TUPLE_TYPE_PRODUCTION, NESTED_QNAME_LIST_PRODUCTION);
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
      // repackage the ParseContainers here to contain a Type and not a PointerType
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<PointerType>>> oldContainer = (ParseContainer<ParseContainer<ParseContainer<PointerType>>>) args[0];
      ParseContainer<Type> firstContainer = new ParseContainer<Type>(oldContainer.getItem().getItem().getItem(), oldContainer.getItem().getItem().getParseInfo());
      ParseContainer<ParseContainer<Type>> secondContainer = new ParseContainer<ParseContainer<Type>>(firstContainer, oldContainer.getItem().getParseInfo());
      return new ParseContainer<ParseContainer<ParseContainer<Type>>>(secondContainer, oldContainer.getParseInfo());
    }

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

    ParseInfo tripleRAngleInfo = (ParseInfo) args[1];
    ParseInfo firstRAngleInfo = ParseUtil.splitTripleRAngleFirst(tripleRAngleInfo);
    ParseInfo firstTwoRAnglesInfo = ParseUtil.splitTripleRAngleFirstTwo(tripleRAngleInfo);
    ParseContainer<Type> firstContainer = new ParseContainer<Type>(type, ParseInfo.combine(type.getParseInfo(), firstRAngleInfo));
    ParseContainer<ParseContainer<Type>> secondContainer = new ParseContainer<ParseContainer<Type>>(firstContainer, ParseInfo.combine(type.getParseInfo(), firstTwoRAnglesInfo));
    return new ParseContainer<ParseContainer<ParseContainer<Type>>>(secondContainer, ParseInfo.combine(type.getParseInfo(), tripleRAngleInfo));
  }

}
