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
import compiler.language.parser.ParseType;
import compiler.language.parser.ParseUtil;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 20 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PointerTypeTrailingParamsDoubleRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> MUTABLE_PRODUCTION = new Production<ParseType>(QNAME, LANGLE, TYPE_PARAMETER_LIST_TRIPLE_RANGLE);
  private static final Production<ParseType> IMMUTABLE_PRODUCTION = new Production<ParseType>(HASH, QNAME, LANGLE, TYPE_PARAMETER_LIST_TRIPLE_RANGLE);
  private static final Production<ParseType> TRAILING_PARAMS_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_PARAMS, DOT, QNAME, LANGLE, TYPE_PARAMETER_LIST_TRIPLE_RANGLE);
  private static final Production<ParseType> DOUBLE_RANGLE_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_PARAMS, DOUBLE_RANGLE);

  @SuppressWarnings("unchecked")
  public PointerTypeTrailingParamsDoubleRAngleRule()
  {
    super(POINTER_TYPE_TRAILING_PARAMS_DOUBLE_RANGLE, MUTABLE_PRODUCTION, IMMUTABLE_PRODUCTION, TRAILING_PARAMS_PRODUCTION, DOUBLE_RANGLE_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (DOUBLE_RANGLE_PRODUCTION.equals(production))
    {
      PointerType type = (PointerType) args[0];
      ParseInfo doubleRAngleInfo = (ParseInfo) args[1];
      ParseInfo firstRAngleInfo = ParseUtil.splitDoubleRAngle(doubleRAngleInfo);
      ParseContainer<PointerType> firstContainer = new ParseContainer<PointerType>(type, ParseInfo.combine(type.getParseInfo(), firstRAngleInfo));
      return new ParseContainer<ParseContainer<PointerType>>(firstContainer, ParseInfo.combine(type.getParseInfo(), doubleRAngleInfo));
    }

    // find the index of the TYPE_PARAMETER_LIST_TRIPLE_RANGLE based on which production this is
    int parameterListIndex;
    if (MUTABLE_PRODUCTION.equals(production))
    {
      parameterListIndex = 2;
    }
    else if (IMMUTABLE_PRODUCTION.equals(production))
    {
      parameterListIndex = 3;
    }
    else if (TRAILING_PARAMS_PRODUCTION.equals(production))
    {
      parameterListIndex = 4;
    }
    else
    {
      throw badTypeList();
    }

    // find the list of type parameters
    @SuppressWarnings("unchecked")
    ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameter>>>> firstParamsContainer =
      (ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameter>>>>) args[parameterListIndex];
    ParseContainer<ParseContainer<ParseList<TypeParameter>>> secondParamsContainer = firstParamsContainer.getItem();
    ParseContainer<ParseList<TypeParameter>> thirdParamsContainer = secondParamsContainer.getItem();
    ParseList<TypeParameter> parameters = thirdParamsContainer.getItem();

    // create the PointerType to encapsulate, and the ParseInfo of everything but the trailing type parameter list
    PointerType type;
    ParseInfo startInfo;
    if (MUTABLE_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[0];

      Name[] names = qname.getNames();
      TypeParameter[][] typeParameterLists = new TypeParameter[names.length][];
      for (int i = 0; i < typeParameterLists.length - 1; i++)
      {
        typeParameterLists[i] = new TypeParameter[0];
      }
      typeParameterLists[typeParameterLists.length - 1] = parameters.toArray(new TypeParameter[0]);

      startInfo = ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1]);
      type = new PointerType(false, names, typeParameterLists,
                             ParseInfo.combine(startInfo, thirdParamsContainer.getParseInfo()));
    }
    else if (IMMUTABLE_PRODUCTION.equals(production))
    {
      QName qname = (QName) args[1];

      Name[] names = qname.getNames();
      TypeParameter[][] typeParameterLists = new TypeParameter[names.length][];
      for (int i = 0; i < typeParameterLists.length - 1; i++)
      {
        typeParameterLists[i] = new TypeParameter[0];
      }
      typeParameterLists[typeParameterLists.length - 1] = parameters.toArray(new TypeParameter[0]);

      startInfo = ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), (ParseInfo) args[2]);
      type = new PointerType(true, names, typeParameterLists,
                   ParseInfo.combine(startInfo, thirdParamsContainer.getParseInfo()));
    }
    else if (TRAILING_PARAMS_PRODUCTION.equals(production))
    {
      PointerType baseType = (PointerType) args[0];
      QName qname = (QName) args[2];

      Name[] names = qname.getNames();
      TypeParameter[][] typeParameterLists = new TypeParameter[names.length][];
      for (int i = 0; i < typeParameterLists.length - 1; i++)
      {
        typeParameterLists[i] = new TypeParameter[0];
      }
      typeParameterLists[typeParameterLists.length - 1] = parameters.toArray(new TypeParameter[0]);

      startInfo = ParseInfo.combine(baseType.getParseInfo(), (ParseInfo) args[1], qname.getParseInfo(), (ParseInfo) args[3]);
      type = new PointerType(baseType, baseType.isImmutable(), names, typeParameterLists,
                   ParseInfo.combine(startInfo, thirdParamsContainer.getParseInfo()));
    }
    else
    {
      throw badTypeList();
    }

    // encapsulate the result in two ParseContainers
    ParseContainer<PointerType> embeddedContainer = new ParseContainer<PointerType>(type,
                 ParseInfo.combine(startInfo, secondParamsContainer.getParseInfo()));
    return new ParseContainer<ParseContainer<PointerType>>(embeddedContainer,
                 ParseInfo.combine(startInfo, firstParamsContainer.getParseInfo()));
  }

}
