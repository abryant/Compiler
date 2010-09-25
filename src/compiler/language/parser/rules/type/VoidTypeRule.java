package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.VOID_KEYWORD;
import static compiler.language.parser.ParseType.VOID_TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.VoidTypeAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 11 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class VoidTypeRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(VOID_KEYWORD);

  @SuppressWarnings("unchecked")
  public VoidTypeRule()
  {
    super(VOID_TYPE, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      return new VoidTypeAST((ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
