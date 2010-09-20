package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.THROWS_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.PointerType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 8 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class ThrowsListRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production START_PRODUCTION = new Production(POINTER_TYPE);
  private static final Production CONTINUATION_PRODUCTION = new Production(THROWS_LIST, COMMA, POINTER_TYPE);

  public ThrowsListRule()
  {
    super(THROWS_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      PointerType type = (PointerType) args[0];
      return new ParseList<PointerType>(type, type.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
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
