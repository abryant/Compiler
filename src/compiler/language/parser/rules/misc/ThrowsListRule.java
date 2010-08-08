package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.THROWS_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.PointerType;
import compiler.parser.Rule;

/*
 * Created on 8 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ThrowsListRule extends Rule
{

  private static final Object[] START_PRODUCTION = new Object[] {POINTER_TYPE};
  private static final Object[] CONTINUATION_PRODUCTION = new Object[] {THROWS_LIST, COMMA, POINTER_TYPE};

  public ThrowsListRule()
  {
    super(THROWS_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
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
