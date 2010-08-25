package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.QUESTION_MARK;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;
import static compiler.language.parser.ParseType.TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER_TRIPLE_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.TypeParameter;
import compiler.language.ast.type.WildcardTypeParameter;
import compiler.language.parser.ParseUtil;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 21 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class WildcardTypeParameterTripleRAngleRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {QUESTION_MARK, TRIPLE_RANGLE};
  private static final Object[] EXTENDS_PRODUCTION = new Object[] {QUESTION_MARK, EXTENDS_KEYWORD, POINTER_TYPE_TRIPLE_RANGLE};
  private static final Object[] SUPER_PRODUCTION = new Object[] {QUESTION_MARK, SUPER_KEYWORD, POINTER_TYPE_TRIPLE_RANGLE};
  private static final Object[] EXTENDS_SUPER_PRODUCTION = new Object[] {QUESTION_MARK, EXTENDS_KEYWORD, POINTER_TYPE, SUPER_KEYWORD, POINTER_TYPE_TRIPLE_RANGLE};
  private static final Object[] SUPER_EXTENDS_PRODUCTION = new Object[] {QUESTION_MARK, SUPER_KEYWORD, POINTER_TYPE, EXTENDS_KEYWORD, POINTER_TYPE_TRIPLE_RANGLE};

  public WildcardTypeParameterTripleRAngleRule()
  {
    super(WILDCARD_TYPE_PARAMETER_TRIPLE_RANGLE, PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == PRODUCTION)
    {
      TypeParameter parameter = new WildcardTypeParameter(null, null, (ParseInfo) args[0]);
      ParseInfo tripleRAngleInfo = (ParseInfo) args[1];
      ParseInfo firstRAngleInfo = ParseUtil.splitTripleRAngleFirst(tripleRAngleInfo);
      ParseInfo firstTwoRAnglesInfo = ParseUtil.splitTripleRAngleFirstTwo(tripleRAngleInfo);
      ParseContainer<TypeParameter> firstContainer = new ParseContainer<TypeParameter>(parameter,
                   ParseInfo.combine((ParseInfo) args[0], firstRAngleInfo));
      ParseContainer<ParseContainer<TypeParameter>> secondContainer = new ParseContainer<ParseContainer<TypeParameter>>(firstContainer,
                   ParseInfo.combine((ParseInfo) args[0], firstTwoRAnglesInfo));
      return new ParseContainer<ParseContainer<ParseContainer<TypeParameter>>>(secondContainer,
                   ParseInfo.combine((ParseInfo) args[0], tripleRAngleInfo));
    }
    if (types == EXTENDS_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<PointerType>>> oldContainer = (ParseContainer<ParseContainer<ParseContainer<PointerType>>>) args[2];
      PointerType superType = oldContainer.getItem().getItem().getItem();
      TypeParameter typeParameter = new WildcardTypeParameter(superType, null,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superType.getParseInfo()));
      ParseContainer<TypeParameter> firstContainer = new ParseContainer<TypeParameter>(typeParameter,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], oldContainer.getItem().getItem().getParseInfo()));
      ParseContainer<ParseContainer<TypeParameter>> secondContainer = new ParseContainer<ParseContainer<TypeParameter>>(firstContainer,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], oldContainer.getItem().getParseInfo()));
      return new ParseContainer<ParseContainer<ParseContainer<TypeParameter>>>(secondContainer,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], oldContainer.getParseInfo()));
    }
    if (types == SUPER_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<PointerType>>> oldContainer = (ParseContainer<ParseContainer<ParseContainer<PointerType>>>) args[2];
      PointerType subType = oldContainer.getItem().getItem().getItem();
      TypeParameter typeParameter = new WildcardTypeParameter(null, subType,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subType.getParseInfo()));
      ParseContainer<TypeParameter> firstContainer = new ParseContainer<TypeParameter>(typeParameter,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], oldContainer.getItem().getItem().getParseInfo()));
      ParseContainer<ParseContainer<TypeParameter>> secondContainer = new ParseContainer<ParseContainer<TypeParameter>>(firstContainer,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], oldContainer.getItem().getParseInfo()));
      return new ParseContainer<ParseContainer<ParseContainer<TypeParameter>>>(secondContainer,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], oldContainer.getParseInfo()));
    }
    if (types == EXTENDS_SUPER_PRODUCTION)
    {
      PointerType superType = (PointerType) args[2];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<PointerType>>> oldContainer = (ParseContainer<ParseContainer<ParseContainer<PointerType>>>) args[4];
      PointerType subType = oldContainer.getItem().getItem().getItem();
      TypeParameter typeParameter = new WildcardTypeParameter(superType, subType,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superType.getParseInfo(), (ParseInfo) args[3], subType.getParseInfo()));
      ParseContainer<TypeParameter> firstContainer = new ParseContainer<TypeParameter>(typeParameter,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superType.getParseInfo(), (ParseInfo) args[3], oldContainer.getItem().getItem().getParseInfo()));
      ParseContainer<ParseContainer<TypeParameter>> secondContainer = new ParseContainer<ParseContainer<TypeParameter>>(firstContainer,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superType.getParseInfo(), (ParseInfo) args[3], oldContainer.getItem().getParseInfo()));
      return new ParseContainer<ParseContainer<ParseContainer<TypeParameter>>>(secondContainer,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superType.getParseInfo(), (ParseInfo) args[3], oldContainer.getParseInfo()));
    }
    if (types == SUPER_EXTENDS_PRODUCTION)
    {
      PointerType subType = (PointerType) args[2];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<PointerType>>> oldContainer = (ParseContainer<ParseContainer<ParseContainer<PointerType>>>) args[4];
      PointerType superType = oldContainer.getItem().getItem().getItem();
      TypeParameter typeParameter = new WildcardTypeParameter(superType, subType,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subType.getParseInfo(), (ParseInfo) args[3], superType.getParseInfo()));
      ParseContainer<TypeParameter> firstContainer = new ParseContainer<TypeParameter>(typeParameter,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subType.getParseInfo(), (ParseInfo) args[3], oldContainer.getItem().getItem().getParseInfo()));
      ParseContainer<ParseContainer<TypeParameter>> secondContainer = new ParseContainer<ParseContainer<TypeParameter>>(firstContainer,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subType.getParseInfo(), (ParseInfo) args[3], oldContainer.getItem().getParseInfo()));
      return new ParseContainer<ParseContainer<ParseContainer<TypeParameter>>>(secondContainer,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subType.getParseInfo(), (ParseInfo) args[3], oldContainer.getParseInfo()));
    }
    throw badTypeList();
  }

}