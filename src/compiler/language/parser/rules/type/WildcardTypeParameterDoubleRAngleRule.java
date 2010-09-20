package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.QUESTION_MARK;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST;
import static compiler.language.parser.ParseType.TYPE_BOUND_LIST_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER_DOUBLE_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.TypeParameter;
import compiler.language.ast.type.WildcardTypeParameter;
import compiler.language.parser.ParseUtil;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 21 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class WildcardTypeParameterDoubleRAngleRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION               = new Production(QUESTION_MARK, DOUBLE_RANGLE);
  private static final Production EXTENDS_PRODUCTION       = new Production(QUESTION_MARK, EXTENDS_KEYWORD, TYPE_BOUND_LIST_DOUBLE_RANGLE);
  private static final Production SUPER_PRODUCTION         = new Production(QUESTION_MARK, SUPER_KEYWORD,   TYPE_BOUND_LIST_DOUBLE_RANGLE);
  private static final Production EXTENDS_SUPER_PRODUCTION = new Production(QUESTION_MARK, EXTENDS_KEYWORD, TYPE_BOUND_LIST, SUPER_KEYWORD,   TYPE_BOUND_LIST_DOUBLE_RANGLE);
  private static final Production SUPER_EXTENDS_PRODUCTION = new Production(QUESTION_MARK, SUPER_KEYWORD,   TYPE_BOUND_LIST, EXTENDS_KEYWORD, TYPE_BOUND_LIST_DOUBLE_RANGLE);

  public WildcardTypeParameterDoubleRAngleRule()
  {
    super(WILDCARD_TYPE_PARAMETER_DOUBLE_RANGLE, PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      TypeParameter parameter = new WildcardTypeParameter(new PointerType[0], new PointerType[0], (ParseInfo) args[0]);
      ParseInfo doubleRAngleInfo = (ParseInfo) args[1];
      ParseInfo firstRAngleInfo = ParseUtil.splitDoubleRAngle(doubleRAngleInfo);
      ParseContainer<TypeParameter> firstContainer = new ParseContainer<TypeParameter>(parameter, ParseInfo.combine(parameter.getParseInfo(), firstRAngleInfo));
      return new ParseContainer<ParseContainer<TypeParameter>>(firstContainer, ParseInfo.combine(parameter.getParseInfo(), doubleRAngleInfo));
    }
    if (EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseList<PointerType>>> oldContainer = (ParseContainer<ParseContainer<ParseList<PointerType>>>) args[2];
      ParseList<PointerType> superTypes = oldContainer.getItem().getItem();
      TypeParameter parameter = new WildcardTypeParameter(superTypes.toArray(new PointerType[0]), new PointerType[0],
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo()));
      ParseContainer<TypeParameter> firstContainer = new ParseContainer<TypeParameter>(parameter,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], oldContainer.getItem().getParseInfo()));
      return new ParseContainer<ParseContainer<TypeParameter>>(firstContainer,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], oldContainer.getParseInfo()));
    }
    if (SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseList<PointerType>>> oldContainer = (ParseContainer<ParseContainer<ParseList<PointerType>>>) args[2];
      ParseList<PointerType> subTypes = oldContainer.getItem().getItem();
      TypeParameter parameter = new WildcardTypeParameter(new PointerType[0], subTypes.toArray(new PointerType[0]),
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subTypes.getParseInfo()));
      ParseContainer<TypeParameter> firstContainer = new ParseContainer<TypeParameter>(parameter,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], oldContainer.getItem().getParseInfo()));
      return new ParseContainer<ParseContainer<TypeParameter>>(firstContainer,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], oldContainer.getParseInfo()));
    }
    if (EXTENDS_SUPER_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> superTypes = (ParseList<PointerType>) args[2];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseList<PointerType>>> oldContainer = (ParseContainer<ParseContainer<ParseList<PointerType>>>) args[4];
      ParseList<PointerType> subTypes = oldContainer.getItem().getItem();
      TypeParameter parameter = new WildcardTypeParameter(superTypes.toArray(new PointerType[0]), subTypes.toArray(new PointerType[0]),
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo(), (ParseInfo) args[3], subTypes.getParseInfo()));
      ParseContainer<TypeParameter> firstContainer = new ParseContainer<TypeParameter>(parameter,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo(), (ParseInfo) args[3], oldContainer.getItem().getParseInfo()));
      return new ParseContainer<ParseContainer<TypeParameter>>(firstContainer,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superTypes.getParseInfo(), (ParseInfo) args[3], oldContainer.getParseInfo()));
    }
    if (SUPER_EXTENDS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<PointerType> subTypes = (ParseList<PointerType>) args[2];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseList<PointerType>>> oldContainer = (ParseContainer<ParseContainer<ParseList<PointerType>>>) args[4];
      ParseList<PointerType> superTypes = oldContainer.getItem().getItem();
      TypeParameter parameter = new WildcardTypeParameter(superTypes.toArray(new PointerType[0]), subTypes.toArray(new PointerType[0]),
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subTypes.getParseInfo(), (ParseInfo) args[3], superTypes.getParseInfo()));
      ParseContainer<TypeParameter> firstContainer = new ParseContainer<TypeParameter>(parameter,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subTypes.getParseInfo(), (ParseInfo) args[3], oldContainer.getItem().getParseInfo()));
      return new ParseContainer<ParseContainer<TypeParameter>>(firstContainer,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subTypes.getParseInfo(), (ParseInfo) args[3], oldContainer.getParseInfo()));
    }
    throw badTypeList();
  }

}
