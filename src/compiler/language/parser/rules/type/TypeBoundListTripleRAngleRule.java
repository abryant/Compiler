package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.AMPERSAND;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST_TRIPLE_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.PointerType;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 30 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeBoundListTripleRAngleRule extends Rule
{

  private static final Object[] PRODUCTION      = new Object[] {POINTER_TYPE_TRIPLE_RANGLE};
  private static final Object[] LIST_PRODUCTION = new Object[] {TYPE_BOUND_LIST, AMPERSAND, POINTER_TYPE_TRIPLE_RANGLE};

  public TypeBoundListTripleRAngleRule()
  {
    super(TYPE_BOUND_LIST_TRIPLE_RANGLE, PRODUCTION, LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<PointerType>>> container = (ParseContainer<ParseContainer<ParseContainer<PointerType>>>) args[0];
      ParseList<PointerType> list = new ParseList<PointerType>(container.getItem().getItem().getItem(), container.getItem().getItem().getItem().getParseInfo());

      ParseContainer<ParseList<PointerType>> firstContainer =
             new ParseContainer<ParseList<PointerType>>(list, container.getItem().getItem().getParseInfo());
      ParseContainer<ParseContainer<ParseList<PointerType>>> secondContainer =
             new ParseContainer<ParseContainer<ParseList<PointerType>>>(firstContainer, container.getItem().getParseInfo());
      return new ParseContainer<ParseContainer<ParseContainer<ParseList<PointerType>>>>(secondContainer, container.getParseInfo());
    }
    if (types == LIST_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> list = (ParseList<PointerType>) args[0];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<PointerType>>> container = (ParseContainer<ParseContainer<ParseContainer<PointerType>>>) args[2];
      ParseInfo listParseInfo = list.getParseInfo();

      list.addLast(container.getItem().getItem().getItem(), ParseInfo.combine(listParseInfo, (ParseInfo) args[1], container.getItem().getItem().getItem().getParseInfo()));

      ParseContainer<ParseList<PointerType>> firstContainer = new ParseContainer<ParseList<PointerType>>(list,
                   ParseInfo.combine(listParseInfo, (ParseInfo) args[1], container.getItem().getItem().getParseInfo()));
      ParseContainer<ParseContainer<ParseList<PointerType>>> secondContainer = new ParseContainer<ParseContainer<ParseList<PointerType>>>(firstContainer,
                   ParseInfo.combine(listParseInfo, (ParseInfo) args[1], container.getItem().getParseInfo()));
      return new ParseContainer<ParseContainer<ParseContainer<ParseList<PointerType>>>>(secondContainer,
                   ParseInfo.combine(listParseInfo, (ParseInfo) args[1], container.getParseInfo()));
    }
    throw badTypeList();
  }

}
