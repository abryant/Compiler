package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.AMPERSAND;
import static compiler.language.parser.ParseType.POINTER_TYPE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.PointerType;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeBoundListRAngleRule extends Rule
{

  private static final Object[] PRODUCTION      = new Object[] {POINTER_TYPE_RANGLE};
  private static final Object[] LIST_PRODUCTION = new Object[] {TYPE_BOUND_LIST, AMPERSAND, POINTER_TYPE_RANGLE};

  public TypeBoundListRAngleRule()
  {
    super(TYPE_BOUND_LIST_RANGLE, PRODUCTION, LIST_PRODUCTION);
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
      ParseContainer<PointerType> type = (ParseContainer<PointerType>) args[0];
      ParseList<PointerType> list = new ParseList<PointerType>(type.getItem(), type.getItem().getParseInfo());
      return new ParseContainer<ParseList<PointerType>>(list, type.getParseInfo());
    }
    if (types == LIST_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> list = (ParseList<PointerType>) args[0];
      @SuppressWarnings("unchecked")
      ParseContainer<PointerType> type = (ParseContainer<PointerType>) args[2];
      list.addLast(type.getItem(), ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], type.getItem().getParseInfo()));
      return new ParseContainer<ParseList<PointerType>>(list, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], type.getParseInfo()));
    }
    throw badTypeList();
  }

}
