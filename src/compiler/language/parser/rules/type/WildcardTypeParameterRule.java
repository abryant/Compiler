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
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class WildcardTypeParameterRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION               = new Production(QUESTION_MARK);
  private static final Production EXTENDS_PRODUCTION       = new Production(QUESTION_MARK, EXTENDS_KEYWORD, TYPE_BOUND_LIST);
  private static final Production SUPER_PRODUCTION         = new Production(QUESTION_MARK, SUPER_KEYWORD,   TYPE_BOUND_LIST);
  private static final Production EXTENDS_SUPER_PRODUCTION = new Production(QUESTION_MARK, EXTENDS_KEYWORD, TYPE_BOUND_LIST, SUPER_KEYWORD,   TYPE_BOUND_LIST);
  private static final Production SUPER_EXTENDS_PRODUCTION = new Production(QUESTION_MARK, SUPER_KEYWORD,   TYPE_BOUND_LIST, EXTENDS_KEYWORD, TYPE_BOUND_LIST);

  public WildcardTypeParameterRule()
  {
    super(WILDCARD_TYPE_PARAMETER, PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      return new WildcardTypeParameter(new PointerType[0], new PointerType[0], (ParseInfo) args[0]);
    }
    if (EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> superTypes = (ParseList<PointerType>) args[2];
      return new WildcardTypeParameter(superTypes.toArray(new PointerType[0]), new PointerType[0],
                                       ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo()));
    }
    if (SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> subTypes = (ParseList<PointerType>) args[2];
      return new WildcardTypeParameter(new PointerType[0], subTypes.toArray(new PointerType[0]),
                                       ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subTypes.getParseInfo()));
    }
    if (EXTENDS_SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> superTypes = (ParseList<PointerType>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<PointerType> subTypes = (ParseList<PointerType>) args[4];
      return new WildcardTypeParameter(superTypes.toArray(new PointerType[0]), subTypes.toArray(new PointerType[0]),
                                       ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo(), (ParseInfo) args[3], subTypes.getParseInfo()));
    }
    if (SUPER_EXTENDS_PRODUCTION.equals(production))
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
