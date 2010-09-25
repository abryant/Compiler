package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.BOOLEAN_KEYWORD;
import static compiler.language.parser.ParseType.BOOLEAN_TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.BooleanTypeAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class BooleanTypeRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(BOOLEAN_KEYWORD);

  @SuppressWarnings("unchecked")
  public BooleanTypeRule()
  {
    super(BOOLEAN_TYPE, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      return new BooleanTypeAST((ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
