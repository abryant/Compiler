package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.INTERFACE_EXTENDS_CLAUSE;
import static compiler.language.parser.ParseType.INTERFACE_LIST;

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
public class InterfaceExtendsClauseRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production EMPTY_PRODUCTION = new Production();
  private static final Production PRODUCTION = new Production(EXTENDS_KEYWORD, INTERFACE_LIST);

  public InterfaceExtendsClauseRule()
  {
    super(INTERFACE_EXTENDS_CLAUSE, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseList<PointerType>(null);
    }
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> list = (ParseList<PointerType>) args[1];
      list.setParseInfo(ParseInfo.combine((ParseInfo) args[0], list.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
