package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.COMMA;
import static compiler.language.parser.ParseType.INTERFACE_LIST;
import static compiler.language.parser.ParseType.POINTER_TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.PointerType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class InterfaceListRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production START_PRODUCTION = new Production(POINTER_TYPE);
  private static final Production CONTINUATION_PRODUCTION = new Production(INTERFACE_LIST, COMMA, POINTER_TYPE);

  public InterfaceListRule()
  {
    super(INTERFACE_LIST, START_PRODUCTION, CONTINUATION_PRODUCTION);
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
      PointerType newType = (PointerType) args[2];
      list.addLast(newType, ParseInfo.combine(list.getParseInfo(), (ParseInfo) args[1], newType.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
