package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.QUESTION_MARK;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.WildcardTypeParameterAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class WildcardTypeParameterRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION               = new Production<ParseType>(QUESTION_MARK);
  private static final Production<ParseType> EXTENDS_PRODUCTION       = new Production<ParseType>(QUESTION_MARK, EXTENDS_KEYWORD, TYPE_BOUND_LIST);
  private static final Production<ParseType> SUPER_PRODUCTION         = new Production<ParseType>(QUESTION_MARK, SUPER_KEYWORD,   TYPE_BOUND_LIST);
  private static final Production<ParseType> EXTENDS_SUPER_PRODUCTION = new Production<ParseType>(QUESTION_MARK, EXTENDS_KEYWORD, TYPE_BOUND_LIST, SUPER_KEYWORD,   TYPE_BOUND_LIST);
  private static final Production<ParseType> SUPER_EXTENDS_PRODUCTION = new Production<ParseType>(QUESTION_MARK, SUPER_KEYWORD,   TYPE_BOUND_LIST, EXTENDS_KEYWORD, TYPE_BOUND_LIST);

  @SuppressWarnings("unchecked")
  public WildcardTypeParameterRule()
  {
    super(WILDCARD_TYPE_PARAMETER, PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      return new WildcardTypeParameterAST(new PointerTypeAST[0], new PointerTypeAST[0], (ParseInfo) args[0]);
    }
    if (EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> superTypes = (ParseList<PointerTypeAST>) args[2];
      return new WildcardTypeParameterAST(superTypes.toArray(new PointerTypeAST[0]), new PointerTypeAST[0],
                                       ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo()));
    }
    if (SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> subTypes = (ParseList<PointerTypeAST>) args[2];
      return new WildcardTypeParameterAST(new PointerTypeAST[0], subTypes.toArray(new PointerTypeAST[0]),
                                       ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subTypes.getParseInfo()));
    }
    if (EXTENDS_SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> superTypes = (ParseList<PointerTypeAST>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> subTypes = (ParseList<PointerTypeAST>) args[4];
      return new WildcardTypeParameterAST(superTypes.toArray(new PointerTypeAST[0]), subTypes.toArray(new PointerTypeAST[0]),
                                       ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo(), (ParseInfo) args[3], subTypes.getParseInfo()));
    }
    if (SUPER_EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> subTypes = (ParseList<PointerTypeAST>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> superTypes = (ParseList<PointerTypeAST>) args[4];
      return new WildcardTypeParameterAST(superTypes.toArray(new PointerTypeAST[0]), subTypes.toArray(new PointerTypeAST[0]),
                                       ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subTypes.getParseInfo(), (ParseInfo) args[3], superTypes.getParseInfo()));
    }
    throw badTypeList();
  }

}
