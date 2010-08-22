package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.TRIPLE_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.PointerType;
import compiler.language.parser.ParseUtil;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 22 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class PointerTypeTrailingParamsTripleRAngleRule extends Rule
{

  private static final Object[] TRAILING_PARAMS_PRODUCTION = new Object[] {POINTER_TYPE_TRAILING_PARAMS, TRIPLE_RANGLE};

  public PointerTypeTrailingParamsTripleRAngleRule()
  {
    super(POINTER_TYPE_TRAILING_PARAMS_TRIPLE_RANGLE, TRAILING_PARAMS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == TRAILING_PARAMS_PRODUCTION)
    {
      PointerType type = (PointerType) args[0];
      ParseInfo tripleRAngleInfo = (ParseInfo) args[1];
      ParseInfo firstRAngleInfo = ParseUtil.splitTripleRAngleFirst(tripleRAngleInfo);
      ParseInfo firstTwoRAnglesInfo  = ParseUtil.splitTripleRAngleFirstTwo(tripleRAngleInfo);

      ParseContainer<PointerType> firstContainer = new ParseContainer<PointerType>(type,
          ParseInfo.combine(type.getParseInfo(), firstRAngleInfo));
      ParseContainer<ParseContainer<PointerType>> secondContainer = new ParseContainer<ParseContainer<PointerType>>(firstContainer,
          ParseInfo.combine(type.getParseInfo(), firstTwoRAnglesInfo));
      return new ParseContainer<ParseContainer<ParseContainer<PointerType>>>(secondContainer,
          ParseInfo.combine(type.getParseInfo(), tripleRAngleInfo));
    }
    throw badTypeList();
  }

}
