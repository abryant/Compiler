package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.CLASS_EXTENDS_CLAUSE;
import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.POINTER_TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.PointerType;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ClassExtendsClauseRule extends Rule
{

  private static final Object[] EMPTY_PRODUCTION = new Object[] {};
  private static final Object[] PRODUCTION = new Object[] {EXTENDS_KEYWORD, POINTER_TYPE};

  public ClassExtendsClauseRule()
  {
    super(CLASS_EXTENDS_CLAUSE, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == EMPTY_PRODUCTION)
    {
      return null;
    }
    if (types == PRODUCTION)
    {
      PointerType type = (PointerType) args[1];
      type.setParseInfo(ParseInfo.combine((ParseInfo) args[0], type.getParseInfo()));
      return type;
    }
    throw badTypeList();
  }

}
