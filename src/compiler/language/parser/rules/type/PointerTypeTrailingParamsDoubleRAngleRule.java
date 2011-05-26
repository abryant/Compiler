package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOT;
import static compiler.language.parser.ParseType.DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.HASH;
import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.QNAME;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST_TRIPLE_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;
import compiler.language.parser.ParseUtil;

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
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (DOUBLE_RANGLE_PRODUCTION.equals(production))
    {
      PointerTypeAST type = (PointerTypeAST) args[0];
      ParseInfo doubleRAngleInfo = (ParseInfo) args[1];
      ParseInfo firstRAngleInfo = ParseUtil.splitDoubleRAngle(doubleRAngleInfo);
      ParseContainer<PointerTypeAST> firstContainer = new ParseContainer<PointerTypeAST>(type, ParseInfo.combine(type.getParseInfo(), firstRAngleInfo));
      return new ParseContainer<ParseContainer<PointerTypeAST>>(firstContainer, ParseInfo.combine(type.getParseInfo(), doubleRAngleInfo));
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
    ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameterAST>>>> firstParamsContainer =
      (ParseContainer<ParseContainer<ParseContainer<ParseList<TypeParameterAST>>>>) args[parameterListIndex];
    ParseContainer<ParseContainer<ParseList<TypeParameterAST>>> secondParamsContainer = firstParamsContainer.getItem();
    ParseContainer<ParseList<TypeParameterAST>> thirdParamsContainer = secondParamsContainer.getItem();
    ParseList<TypeParameterAST> parameters = thirdParamsContainer.getItem();

    // create the PointerTypeAST to encapsulate, and the ParseInfo of everything but the trailing type parameter list
    PointerTypeAST type;
    ParseInfo startInfo;
    if (MUTABLE_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[0];

      NameAST[] names = qname.getNames();
      TypeParameterAST[][] typeParameterLists = new TypeParameterAST[names.length][];
      typeParameterLists[typeParameterLists.length - 1] = parameters.toArray(new TypeParameterAST[parameters.size()]);

      startInfo = ParseInfo.combine(qname.getParseInfo(), (ParseInfo) args[1]);
      type = new PointerTypeAST(false, names, typeParameterLists,
                             ParseInfo.combine(startInfo, thirdParamsContainer.getParseInfo()));
    }
    else if (IMMUTABLE_PRODUCTION.equals(production))
    {
      QNameAST qname = (QNameAST) args[1];

      NameAST[] names = qname.getNames();
      TypeParameterAST[][] typeParameterLists = new TypeParameterAST[names.length][];
      typeParameterLists[typeParameterLists.length - 1] = parameters.toArray(new TypeParameterAST[parameters.size()]);

      startInfo = ParseInfo.combine((ParseInfo) args[0], qname.getParseInfo(), (ParseInfo) args[2]);
      type = new PointerTypeAST(true, names, typeParameterLists,
                   ParseInfo.combine(startInfo, thirdParamsContainer.getParseInfo()));
    }
    else if (TRAILING_PARAMS_PRODUCTION.equals(production))
    {
      PointerTypeAST baseType = (PointerTypeAST) args[0];
      QNameAST qname = (QNameAST) args[2];

      NameAST[] names = qname.getNames();
      TypeParameterAST[][] typeParameterLists = new TypeParameterAST[names.length][];
      typeParameterLists[typeParameterLists.length - 1] = parameters.toArray(new TypeParameterAST[parameters.size()]);

      startInfo = ParseInfo.combine(baseType.getParseInfo(), (ParseInfo) args[1], qname.getParseInfo(), (ParseInfo) args[3]);
      type = new PointerTypeAST(baseType, baseType.isImmutable(), names, typeParameterLists,
                   ParseInfo.combine(startInfo, thirdParamsContainer.getParseInfo()));
    }
    else
    {
      throw badTypeList();
    }

    // encapsulate the result in two ParseContainers
    ParseContainer<PointerTypeAST> embeddedContainer = new ParseContainer<PointerTypeAST>(type,
                 ParseInfo.combine(startInfo, secondParamsContainer.getParseInfo()));
    return new ParseContainer<ParseContainer<PointerTypeAST>>(embeddedContainer,
                 ParseInfo.combine(startInfo, firstParamsContainer.getParseInfo()));
  }

}
