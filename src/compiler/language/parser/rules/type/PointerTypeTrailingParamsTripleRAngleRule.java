package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS;
import static compiler.language.parser.ParseType.POINTER_TYPE_TRAILING_PARAMS_TRIPLE_RANGLE;
import static compiler.language.parser.ParseType.TRIPLE_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.ParseContainer;
import compiler.language.parser.ParseType;
import compiler.language.parser.ParseUtil;

/*
 * Created on 22 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class PointerTypeTrailingParamsTripleRAngleRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> TRAILING_PARAMS_PRODUCTION = new Production<ParseType>(POINTER_TYPE_TRAILING_PARAMS, TRIPLE_RANGLE);

  @SuppressWarnings("unchecked")
  public PointerTypeTrailingParamsTripleRAngleRule()
  {
    super(POINTER_TYPE_TRAILING_PARAMS_TRIPLE_RANGLE, TRAILING_PARAMS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (TRAILING_PARAMS_PRODUCTION.equals(production))
    {
      PointerTypeAST type = (PointerTypeAST) args[0];
      ParseInfo tripleRAngleInfo = (ParseInfo) args[1];
      ParseInfo firstRAngleInfo = ParseUtil.splitTripleRAngleFirst(tripleRAngleInfo);
      ParseInfo firstTwoRAnglesInfo  = ParseUtil.splitTripleRAngleFirstTwo(tripleRAngleInfo);

      ParseContainer<PointerTypeAST> firstContainer = new ParseContainer<PointerTypeAST>(type,
          ParseInfo.combine(type.getParseInfo(), firstRAngleInfo));
      ParseContainer<ParseContainer<PointerTypeAST>> secondContainer = new ParseContainer<ParseContainer<PointerTypeAST>>(firstContainer,
          ParseInfo.combine(type.getParseInfo(), firstTwoRAnglesInfo));
      return new ParseContainer<ParseContainer<ParseContainer<PointerTypeAST>>>(secondContainer,
          ParseInfo.combine(type.getParseInfo(), tripleRAngleInfo));
    }
    throw badTypeList();
  }

}
