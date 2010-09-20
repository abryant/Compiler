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
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 21 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeTripleRAngleRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION                    = new Production(TYPE_NOT_POINTER_TYPE_NOT_TUPLE_TYPE, TRIPLE_RANGLE);
  private static final Production POINTER_TYPE_PRODUCTION       = new Production(POINTER_TYPE_TRIPLE_RANGLE);
  private static final Production TUPLE_TYPE_PRODUCTION         = new Production(TUPLE_TYPE_NOT_QNAME_LIST,            TRIPLE_RANGLE);
  private static final Production NESTED_QNAME_LIST_PRODUCTION  = new Production(NESTED_QNAME_LIST,                    TRIPLE_RANGLE);

  public TypeTripleRAngleRule()
  {
    super(TYPE_TRIPLE_RANGLE, PRODUCTION, POINTER_TYPE_PRODUCTION, TUPLE_TYPE_PRODUCTION, NESTED_QNAME_LIST_PRODUCTION);
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
      // repackage the ParseContainers here to contain a Type and not a PointerType
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<PointerType>>> oldContainer = (ParseContainer<ParseContainer<ParseContainer<PointerType>>>) args[0];
      ParseContainer<Type> firstContainer = new ParseContainer<Type>(oldContainer.getItem().getItem().getItem(), oldContainer.getItem().getItem().getParseInfo());
      ParseContainer<ParseContainer<Type>> secondContainer = new ParseContainer<ParseContainer<Type>>(firstContainer, oldContainer.getItem().getParseInfo());
      return new ParseContainer<ParseContainer<ParseContainer<Type>>>(secondContainer, oldContainer.getParseInfo());
    }

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

    ParseInfo tripleRAngleInfo = (ParseInfo) args[1];
    ParseInfo firstRAngleInfo = ParseUtil.splitTripleRAngleFirst(tripleRAngleInfo);
    ParseInfo firstTwoRAnglesInfo = ParseUtil.splitTripleRAngleFirstTwo(tripleRAngleInfo);
    ParseContainer<Type> firstContainer = new ParseContainer<Type>(type, ParseInfo.combine(type.getParseInfo(), firstRAngleInfo));
    ParseContainer<ParseContainer<Type>> secondContainer = new ParseContainer<ParseContainer<Type>>(firstContainer, ParseInfo.combine(type.getParseInfo(), firstTwoRAnglesInfo));
    return new ParseContainer<ParseContainer<ParseContainer<Type>>>(secondContainer, ParseInfo.combine(type.getParseInfo(), tripleRAngleInfo));
  }

}
