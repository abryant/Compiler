package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOUBLE_KEYWORD;
import static compiler.language.parser.ParseType.FLOATING_TYPE;
import static compiler.language.parser.ParseType.FLOAT_KEYWORD;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.FloatingType;
import compiler.language.ast.type.FloatingTypeLength;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class FloatingTypeRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production FLOAT_PRODUCTION = new Production(FLOAT_KEYWORD);
  private static final Production DOUBLE_PRODUCTION = new Production(DOUBLE_KEYWORD);

  public FloatingTypeRule()
  {
    super(FLOATING_TYPE, FLOAT_PRODUCTION, DOUBLE_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (FLOAT_PRODUCTION.equals(production))
    {
      return new FloatingType(FloatingTypeLength.FLOAT, (ParseInfo) args[0]);
    }
    if (DOUBLE_PRODUCTION.equals(production))
    {
      return new FloatingType(FloatingTypeLength.DOUBLE, (ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
