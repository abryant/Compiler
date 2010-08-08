package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.QUESTION_MARK;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.WildcardTypeParameter;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class WildcardTypeParameterRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {QUESTION_MARK};
  private static final Object[] EXTENDS_PRODUCTION = new Object[] {QUESTION_MARK, EXTENDS_KEYWORD, POINTER_TYPE};
  private static final Object[] SUPER_PRODUCTION = new Object[] {QUESTION_MARK, SUPER_KEYWORD, POINTER_TYPE};
  private static final Object[] EXTENDS_SUPER_PRODUCTION = new Object[] {QUESTION_MARK, EXTENDS_KEYWORD, POINTER_TYPE, SUPER_KEYWORD, POINTER_TYPE};
  private static final Object[] SUPER_EXTENDS_PRODUCTION = new Object[] {QUESTION_MARK, SUPER_KEYWORD, POINTER_TYPE, EXTENDS_KEYWORD, POINTER_TYPE};

  public WildcardTypeParameterRule()
  {
    super(WILDCARD_TYPE_PARAMETER, PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new WildcardTypeParameter(null, null, (ParseInfo) args[0]);
    }
    if (types == EXTENDS_PRODUCTION)
    {
      PointerType superType = (PointerType) args[2];
      return new WildcardTypeParameter(superType, null, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superType.getParseInfo()));
    }
    if (types == SUPER_PRODUCTION)
    {
      PointerType subType = (PointerType) args[2];
      return new WildcardTypeParameter(null, subType, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subType.getParseInfo()));
    }
    if (types == EXTENDS_SUPER_PRODUCTION)
    {
      PointerType superType = (PointerType) args[2];
      PointerType subType = (PointerType) args[4];
      return new WildcardTypeParameter(superType, subType, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superType.getParseInfo(), (ParseInfo) args[3], subType.getParseInfo()));
    }
    if (types == SUPER_EXTENDS_PRODUCTION)
    {
      PointerType subType = (PointerType) args[2];
      PointerType superType = (PointerType) args[4];
      return new WildcardTypeParameter(superType, subType, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subType.getParseInfo(), (ParseInfo) args[3], superType.getParseInfo()));
    }
    throw badTypeList();
  }

}
