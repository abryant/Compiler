package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST_TRIPLE_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.QName;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.TypeParameter;
import compiler.language.parser.ParseUtil;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 20 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerTypeTrailingParamsDoubleRAngleRule extends Rule
{

  private static final Object[] MUTABLE_PRODUCTION = new Object[] {QNAME, LANGLE, TYPE_PARAMETER_LIST_TRIPLE_RANGLE};
  private static final Object[] IMMUTABLE_PRODUCTION = new Object[] {HASH, QNAME, LANGLE, TYPE_PARAMETER_LIST_TRIPLE_RANGLE};
  private static final Object[] TRAILING_PARAMS_PRODUCTION = new Object[] {POINTER_TYPE_TRAILING_PARAMS, DOT, QNAME, LANGLE, TYPE_PARAMETER_LIST_TRIPLE_RANGLE};
  private static final Object[] DOUBLE_RANGLE_PRODUCTION = new Object[] {POINTER_TYPE_TRAILING_PARAMS, DOUBLE_RANGLE};

  public PointerTypeTrailingParamsDoubleRAngleRule()
  {
    super(POINTER_TYPE_TRAILING_PARAMS_DOUBLE_RANGLE, MUTABLE_PRODUCTION, IMMUTABLE_PRODUCTION, TRAILING_PARAMS_PRODUCTION, DOUBLE_RANGLE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == DOUBLE_RANGLE_PRODUCTION)
    {
      PointerType type = (PointerType) args[0];
      ParseInfo doubleRAngleInfo = (ParseInfo) args[1];
      ParseInfo firstRAngleInfo = ParseUtil.splitDoubleRAngle(doubleRAngleInfo);
      ParseContainer<PointerType> firstContainer = new ParseContainer<PointerType>(type, ParseInfo.combine(type.getParseInfo(), firstRAngleInfo));
      return new ParseContainer<ParseContainer<PointerType>>(firstContainer, ParseInfo.combine(type.getParseInfo(), doubleRAngleInfo));
    }

    if (types == MUTABLE_PRODUCTION)
    {
      QName qname = (QName) args[0];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameter>>>> firstParamsContainer =
        (ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameter>>>>) args[2];
      ParseContainer<ParseContainer<ParseList<TypeParameter>>> secondParamsContainer = firstParamsContainer.getItem();
      ParseContainer<ParseList<TypeParameter>> thirdParamsContainer = secondParamsContainer.getItem();
      ParseList<TypeParameter> parameters = thirdParamsContainer.getItem();

      Name[] names = qname.getNames();
      TypeParameter[][] typeParameterLists = new TypeParameter[names.length][];
      for (int i = 0; i < typeParameterLists.length - 1; i++)
      {
        typeParameterLists[i] = new TypeParameter[0];
      }
      typeParameterLists[typeParameterLists.length - 1] = parameters.toArray(new TypeParameter[0]);

      PointerType type = new PointerType(false, names, typeParameterLists,
                                         ParseInfo.combine(qname.getParseInfo(),
                                                           (ParseInfo) args[1],
                                                           thirdParamsContainer.getParseInfo()));
      ParseContainer<PointerType> embeddedContainer =
        new ParseContainer<PointerType>(type,
                                        ParseInfo.combine(qname.getParseInfo(),
                                                          (ParseInfo) args[1],
                                                          secondParamsContainer.getParseInfo()));
      return new ParseContainer<ParseContainer<PointerType>>(embeddedContainer,
                                                             ParseInfo.combine(qname.getParseInfo(),
                                                                               (ParseInfo) args[1],
                                                                               firstParamsContainer.getParseInfo()));
    }
    if (types == IMMUTABLE_PRODUCTION)
    {
      QName qname = (QName) args[1];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameter>>>> firstParamsContainer =
        (ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameter>>>>) args[3];
      ParseContainer<ParseContainer<ParseList<TypeParameter>>> secondParamsContainer = firstParamsContainer.getItem();
      ParseContainer<ParseList<TypeParameter>> thirdParamsContainer = secondParamsContainer.getItem();
      ParseList<TypeParameter> parameters = thirdParamsContainer.getItem();

      Name[] names = qname.getNames();
      TypeParameter[][] typeParameterLists = new TypeParameter[names.length][];
      for (int i = 0; i < typeParameterLists.length - 1; i++)
      {
        typeParameterLists[i] = new TypeParameter[0];
      }
      typeParameterLists[typeParameterLists.length - 1] = parameters.toArray(new TypeParameter[0]);

      PointerType type = new PointerType(true, names, typeParameterLists,
                                         ParseInfo.combine((ParseInfo) args[0],
                                                           qname.getParseInfo(),
                                                           (ParseInfo) args[2],
                                                           thirdParamsContainer.getParseInfo()));
      ParseContainer<PointerType> embeddedContainer =
        new ParseContainer<PointerType>(type,
                                        ParseInfo.combine((ParseInfo) args[0],
                                                          qname.getParseInfo(),
                                                          (ParseInfo) args[2],
                                                          secondParamsContainer.getParseInfo()));
      return new ParseContainer<ParseContainer<PointerType>>(embeddedContainer,
                                                             ParseInfo.combine((ParseInfo) args[0],
                                                                               qname.getParseInfo(),
                                                                               (ParseInfo) args[2],
                                                                               firstParamsContainer.getParseInfo()));
    }
    if (types == TRAILING_PARAMS_PRODUCTION)
    {
      PointerType baseType = (PointerType) args[0];
      QName qname = (QName) args[2];
      @SuppressWarnings("unchecked")
      ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameter>>>> firstParamsContainer =
        (ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameter>>>>) args[4];
      ParseContainer<ParseContainer<ParseList<TypeParameter>>> secondParamsContainer = firstParamsContainer.getItem();
      ParseContainer<ParseList<TypeParameter>> thirdParamsContainer = secondParamsContainer.getItem();
      ParseList<TypeParameter> parameters = thirdParamsContainer.getItem();

      Name[] names = qname.getNames();
      TypeParameter[][] typeParameterLists = new TypeParameter[names.length][];
      for (int i = 0; i < typeParameterLists.length - 1; i++)
      {
        typeParameterLists[i] = new TypeParameter[0];
      }
      typeParameterLists[typeParameterLists.length - 1] = parameters.toArray(new TypeParameter[0]);

      PointerType type = new PointerType(baseType, baseType.isImmutable(), names, typeParameterLists,
                                         ParseInfo.combine(baseType.getParseInfo(),
                                                           (ParseInfo) args[1],
                                                           qname.getParseInfo(),
                                                           (ParseInfo) args[3],
                                                           thirdParamsContainer.getParseInfo()));
      ParseContainer<PointerType> embeddedContainer =
        new ParseContainer<PointerType>(type,
                                        ParseInfo.combine(baseType.getParseInfo(),
                                                          (ParseInfo) args[1],
                                                          qname.getParseInfo(),
                                                          (ParseInfo) args[3],
                                                          secondParamsContainer.getParseInfo()));
      return new ParseContainer<ParseContainer<PointerType>>(embeddedContainer,
                   ParseInfo.combine(baseType.getParseInfo(),
                                     (ParseInfo) args[1],
                                     qname.getParseInfo(),
                                     (ParseInfo) args[3],
                                     firstParamsContainer.getParseInfo()));
    }
    throw badTypeList();
  }

}
