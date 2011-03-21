package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.QUESTION_MARK;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER_DOUBLE_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.ast.type.WildcardTypeParameterAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;
import compiler.language.parser.ParseUtil;

/*
 * Created on 21 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class WildcardTypeParameterDoubleRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION               = new Production<ParseType>(QUESTION_MARK, DOUBLE_RANGLE);
  private static final Production<ParseType> EXTENDS_PRODUCTION       = new Production<ParseType>(QUESTION_MARK, EXTENDS_KEYWORD, TYPE_BOUND_LIST_DOUBLE_RANGLE);
  private static final Production<ParseType> SUPER_PRODUCTION         = new Production<ParseType>(QUESTION_MARK, SUPER_KEYWORD,   TYPE_BOUND_LIST_DOUBLE_RANGLE);
  private static final Production<ParseType> EXTENDS_SUPER_PRODUCTION = new Production<ParseType>(QUESTION_MARK, EXTENDS_KEYWORD, TYPE_BOUND_LIST, SUPER_KEYWORD,   TYPE_BOUND_LIST_DOUBLE_RANGLE);
  private static final Production<ParseType> SUPER_EXTENDS_PRODUCTION = new Production<ParseType>(QUESTION_MARK, SUPER_KEYWORD,   TYPE_BOUND_LIST, EXTENDS_KEYWORD, TYPE_BOUND_LIST_DOUBLE_RANGLE);

  @SuppressWarnings("unchecked")
  public WildcardTypeParameterDoubleRAngleRule()
  {
    super(WILDCARD_TYPE_PARAMETER_DOUBLE_RANGLE, PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      TypeParameterAST parameter = new WildcardTypeParameterAST(new PointerTypeAST[0], new PointerTypeAST[0], (ParseInfo) args[0]);
      ParseInfo doubleRAngleInfo = (ParseInfo) args[1];
      ParseInfo firstRAngleInfo = ParseUtil.splitDoubleRAngle(doubleRAngleInfo);
      ParseContainer<TypeParameterAST> firstContainer = new ParseContainer<TypeParameterAST>(parameter, ParseInfo.combine(parameter.getParseInfo(), firstRAngleInfo));
      return new ParseContainer<ParseContainer<TypeParameterAST>>(firstContainer, ParseInfo.combine(parameter.getParseInfo(), doubleRAngleInfo));
    }
    if (EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseList<PointerTypeAST>>> oldContainer = (ParseContainer<ParseContainer<ParseList<PointerTypeAST>>>) args[2];
      ParseList<PointerTypeAST> superTypes = oldContainer.getItem().getItem();
      TypeParameterAST parameter = new WildcardTypeParameterAST(superTypes.toArray(new PointerTypeAST[0]), new PointerTypeAST[0],
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo()));
      ParseContainer<TypeParameterAST> firstContainer = new ParseContainer<TypeParameterAST>(parameter,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], oldContainer.getItem().getParseInfo()));
      return new ParseContainer<ParseContainer<TypeParameterAST>>(firstContainer,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], oldContainer.getParseInfo()));
    }
    if (SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseList<PointerTypeAST>>> oldContainer = (ParseContainer<ParseContainer<ParseList<PointerTypeAST>>>) args[2];
      ParseList<PointerTypeAST> subTypes = oldContainer.getItem().getItem();
      TypeParameterAST parameter = new WildcardTypeParameterAST(new PointerTypeAST[0], subTypes.toArray(new PointerTypeAST[0]),
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subTypes.getParseInfo()));
      ParseContainer<TypeParameterAST> firstContainer = new ParseContainer<TypeParameterAST>(parameter,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], oldContainer.getItem().getParseInfo()));
      return new ParseContainer<ParseContainer<TypeParameterAST>>(firstContainer,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], oldContainer.getParseInfo()));
    }
    if (EXTENDS_SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> superTypes = (ParseList<PointerTypeAST>) args[2];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseList<PointerTypeAST>>> oldContainer = (ParseContainer<ParseContainer<ParseList<PointerTypeAST>>>) args[4];
      ParseList<PointerTypeAST> subTypes = oldContainer.getItem().getItem();
      TypeParameterAST parameter = new WildcardTypeParameterAST(superTypes.toArray(new PointerTypeAST[0]), subTypes.toArray(new PointerTypeAST[0]),
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo(), (ParseInfo) args[3], subTypes.getParseInfo()));
      ParseContainer<TypeParameterAST> firstContainer = new ParseContainer<TypeParameterAST>(parameter,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo(), (ParseInfo) args[3], oldContainer.getItem().getParseInfo()));
      return new ParseContainer<ParseContainer<TypeParameterAST>>(firstContainer,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo(), (ParseInfo) args[3], oldContainer.getParseInfo()));
    }
    if (SUPER_EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerTypeAST> subTypes = (ParseList<PointerTypeAST>) args[2];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseList<PointerTypeAST>>> oldContainer = (ParseContainer<ParseContainer<ParseList<PointerTypeAST>>>) args[4];
      ParseList<PointerTypeAST> superTypes = oldContainer.getItem().getItem();
      TypeParameterAST parameter = new WildcardTypeParameterAST(superTypes.toArray(new PointerTypeAST[0]), subTypes.toArray(new PointerTypeAST[0]),
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subTypes.getParseInfo(), (ParseInfo) args[3], superTypes.getParseInfo()));
      ParseContainer<TypeParameterAST> firstContainer = new ParseContainer<TypeParameterAST>(parameter,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subTypes.getParseInfo(), (ParseInfo) args[3], oldContainer.getItem().getParseInfo()));
      return new ParseContainer<ParseContainer<TypeParameterAST>>(firstContainer,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subTypes.getParseInfo(), (ParseInfo) args[3], oldContainer.getParseInfo()));
    }
    throw badTypeList();
  }

}
