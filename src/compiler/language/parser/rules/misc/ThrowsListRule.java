package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.THROWS_LIST;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 8 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ThrowsListRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> START_PRODUCTION = new Production<ParseType>(POINTER_TYPE);
  private static final Production<ParseType> CONTINUATION_PRODUCTION = new Production<ParseType>(THROWS_LIST, COMMA, POINTER_TYPE);

  @SuppressWarnings("unchecked")
  public ThrowsListRule()
  {
    super(THROWS_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (START_PRODUCTION.equals(production))
    {
      PointerTypeAST type = (PointerTypeAST) args[0];
      return new ParseList<PointerTypeAST>(type, type.getParseInfo());
    }
    if (CONTINUATION_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> list = (ParseList<PointerTypeAST>) args[0];
      PointerTypeAST type = (PointerTypeAST) args[2];
      list.addLast(type, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], type.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
