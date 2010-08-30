package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.QUESTION_MARK;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.WildcardTypeParameter;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class WildcardTypeParameterRule extends Rule
{

  private static final Object[] PRODUCTION               = new Object[] {QUESTION_MARK};
  private static final Object[] EXTENDS_PRODUCTION       = new Object[] {QUESTION_MARK, EXTENDS_KEYWORD, TYPE_BOUND_LIST};
  private static final Object[] SUPER_PRODUCTION         = new Object[] {QUESTION_MARK, SUPER_KEYWORD,   TYPE_BOUND_LIST};
  private static final Object[] EXTENDS_SUPER_PRODUCTION = new Object[] {QUESTION_MARK, EXTENDS_KEYWORD, TYPE_BOUND_LIST, SUPER_KEYWORD,   TYPE_BOUND_LIST};
  private static final Object[] SUPER_EXTENDS_PRODUCTION = new Object[] {QUESTION_MARK, SUPER_KEYWORD,   TYPE_BOUND_LIST, EXTENDS_KEYWORD, TYPE_BOUND_LIST};

  public WildcardTypeParameterRule()
  {
    super(WILDCARD_TYPE_PARAMETER, PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      return new WildcardTypeParameter(new PointerType[0], new PointerType[0], (ParseInfo) args[0]);
    }
    if (types == EXTENDS_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> superTypes = (ParseList<PointerType>) args[2];
      return new WildcardTypeParameter(superTypes.toArray(new PointerType[0]), new PointerType[0],
                                       ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo()));
    }
    if (types == SUPER_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> subTypes = (ParseList<PointerType>) args[2];
      return new WildcardTypeParameter(new PointerType[0], subTypes.toArray(new PointerType[0]),
                                       ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subTypes.getParseInfo()));
    }
    if (types == EXTENDS_SUPER_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> superTypes = (ParseList<PointerType>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> subTypes = (ParseList<PointerType>) args[4];
      return new WildcardTypeParameter(superTypes.toArray(new PointerType[0]), subTypes.toArray(new PointerType[0]),
                                       ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo(), (ParseInfo) args[3], subTypes.getParseInfo()));
    }
    if (types == SUPER_EXTENDS_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> subTypes = (ParseList<PointerType>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> superTypes = (ParseList<PointerType>) args[4];
      return new WildcardTypeParameter(superTypes.toArray(new PointerType[0]), subTypes.toArray(new PointerType[0]),
                                       ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subTypes.getParseInfo(), (ParseInfo) args[3], superTypes.getParseInfo()));
    }
    throw badTypeList();
  }

}
