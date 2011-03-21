package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.IMPLEMENTS_CLAUSE;
import static compiler.language.parser.ParseType.IMPLEMENTS_KEYWORD;
import static compiler.language.parser.ParseType.INTERFACE_LIST;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 1 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ImplementsClauseRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> EMPTY_PRODUCTION = new Production<ParseType>();
  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(IMPLEMENTS_KEYWORD, INTERFACE_LIST);

  @SuppressWarnings("unchecked")
  public ImplementsClauseRule()
  {
    super(IMPLEMENTS_CLAUSE, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new ParseList<PointerTypeAST>(null);
    }
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> list = (ParseList<PointerTypeAST>) args[1];
      list.setParseInfo(ParseInfo.combine((ParseInfo) args[0], list.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
