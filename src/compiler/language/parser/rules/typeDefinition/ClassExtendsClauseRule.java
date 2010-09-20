package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.CLASS_EXTENDS_CLAUSE;
import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.POINTER_TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.PointerType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ClassExtendsClauseRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production EMPTY_PRODUCTION = new Production();
  private static final Production PRODUCTION = new Production(EXTENDS_KEYWORD, POINTER_TYPE);

  public ClassExtendsClauseRule()
  {
    super(CLASS_EXTENDS_CLAUSE, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return null;
    }
    if (PRODUCTION.equals(production))
    {
      PointerType type = (PointerType) args[1];
      type.setParseInfo(ParseInfo.combine((ParseInfo) args[0], type.getParseInfo()));
      return type;
    }
    throw badTypeList();
  }

}
