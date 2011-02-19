package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOUBLE_KEYWORD;
import static compiler.language.parser.ParseType.FLOATING_TYPE;
import static compiler.language.parser.ParseType.FLOAT_KEYWORD;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.FloatingTypeAST;
import compiler.language.ast.type.FloatingTypeLengthAST;
import compiler.language.parser.ParseType;

/*
 * Created on 12 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class FloatingTypeRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> FLOAT_PRODUCTION = new Production<ParseType>(FLOAT_KEYWORD);
  private static final Production<ParseType> DOUBLE_PRODUCTION = new Production<ParseType>(DOUBLE_KEYWORD);

  @SuppressWarnings("unchecked")
  public FloatingTypeRule()
  {
    super(FLOATING_TYPE, FLOAT_PRODUCTION, DOUBLE_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (FLOAT_PRODUCTION.equals(production))
    {
      return new FloatingTypeAST(FloatingTypeLengthAST.FLOAT, (ParseInfo) args[0]);
    }
    if (DOUBLE_PRODUCTION.equals(production))
    {
      return new FloatingTypeAST(FloatingTypeLengthAST.DOUBLE, (ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
