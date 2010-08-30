package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.AMPERSAND;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST;

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
public class TypeBoundListRule extends Rule
{

  private static final Object[] START_PRODUCTION        = new Object[] {POINTER_TYPE};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {TYPE_BOUND_LIST, AMPERSAND, POINTER_TYPE};

  public TypeBoundListRule()
  {
    super(TYPE_BOUND_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == START_PRODUCTION)
    {
      PointerType type = (PointerType) args[0];
      return new ParseList<PointerType>(type, type.getParseInfo());
    }
    if (types == CONTINUATION_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> list = (ParseList<PointerType>) args[0];
      PointerType type = (PointerType) args[2];
      list.addLast(type, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], type.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
