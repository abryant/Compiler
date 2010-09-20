package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.BOOLEAN_KEYWORD;
import static compiler.language.parser.ParseType.BOOLEAN_TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.BooleanType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class BooleanTypeRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(BOOLEAN_KEYWORD);

  public BooleanTypeRule()
  {
    super(BOOLEAN_TYPE, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      return new BooleanType((ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
