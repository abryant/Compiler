package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.POINTER_TYPE_RANGLE;
import static compiler.language.parser.ParseType.QUESTION_MARK;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.SUPER_KEYWORD;
import static compiler.language.parser.ParseType.WILDCARD_TYPE_PARAMETER_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.TypeParameter;
import compiler.language.ast.type.WildcardTypeParameter;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class WildcardTypeParameterRAngleRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {QUESTION_MARK, RANGLE};
  private static final Object[] EXTENDS_PRODUCTION = new Object[] {QUESTION_MARK, EXTENDS_KEYWORD, POINTER_TYPE_RANGLE};
  private static final Object[] SUPER_PRODUCTION = new Object[] {QUESTION_MARK, SUPER_KEYWORD, POINTER_TYPE_RANGLE};
  private static final Object[] EXTENDS_SUPER_PRODUCTION = new Object[] {QUESTION_MARK, EXTENDS_KEYWORD, POINTER_TYPE, SUPER_KEYWORD, POINTER_TYPE_RANGLE};
  private static final Object[] SUPER_EXTENDS_PRODUCTION = new Object[] {QUESTION_MARK, SUPER_KEYWORD, POINTER_TYPE, EXTENDS_KEYWORD, POINTER_TYPE_RANGLE};

  public WildcardTypeParameterRAngleRule()
  {
    super(WILDCARD_TYPE_PARAMETER_RANGLE, PRODUCTION, EXTENDS_PRODUCTION, SUPER_PRODUCTION, EXTENDS_SUPER_PRODUCTION, SUPER_EXTENDS_PRODUCTION);
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
      WildcardTypeParameter typeParameter = new WildcardTypeParameter(null, null, (ParseInfo) args[0]);
      return new ParseContainer<TypeParameter>(typeParameter, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1]));
    }
    if (types == EXTENDS_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseContainer<PointerType> container = (ParseContainer<PointerType>) args[2];
      PointerType superType = container.getItem();
      WildcardTypeParameter typeParameter = new WildcardTypeParameter(superType, null, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superType.getParseInfo()));
      return new ParseContainer<TypeParameter>(typeParameter, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], container.getParseInfo()));
    }
    if (types == SUPER_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseContainer<PointerType> container = (ParseContainer<PointerType>) args[2];
      PointerType subType = container.getItem();
      WildcardTypeParameter typeParameter = new WildcardTypeParameter(null, subType, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subType.getParseInfo()));
      return new ParseContainer<TypeParameter>(typeParameter, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], container.getParseInfo()));
    }
    if (types == EXTENDS_SUPER_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseContainer<PointerType> container = (ParseContainer<PointerType>) args[4];
      PointerType superType = (PointerType) args[2];
      PointerType subType = container.getItem();
      WildcardTypeParameter typeParameter = new WildcardTypeParameter(superType, subType,
                                                  ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superType.getParseInfo(),
                                                                    (ParseInfo) args[3], subType.getParseInfo()));
      return new ParseContainer<TypeParameter>(typeParameter, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], superType.getParseInfo(),
                                                                                        (ParseInfo) args[3], container.getParseInfo()));
    }
    if (types == SUPER_EXTENDS_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseContainer<PointerType> container = (ParseContainer<PointerType>) args[4];
      PointerType subType = (PointerType) args[2];
      PointerType superType = container.getItem();
      WildcardTypeParameter typeParameter = new WildcardTypeParameter(superType, subType,
                                                  ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subType.getParseInfo(),
                                                                    (ParseInfo) args[3], superType.getParseInfo()));
      return new ParseContainer<TypeParameter>(typeParameter,
                   ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], subType.getParseInfo(), (ParseInfo) args[3], container.getParseInfo()));
    }
    throw badTypeList();
  }

}
