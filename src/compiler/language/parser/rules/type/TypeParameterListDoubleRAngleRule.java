package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.RANGLE;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST_DOUBLE_RANGLE;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST_RANGLE;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.TypeParameter;
import compiler.language.parser.LanguageParseException;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 10 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class TypeParameterListDoubleRAngleRule extends Rule
{

  private static final Object[] DOUBLE_RANGLE_PRODUCTION = new Object[] {TYPE_PARAMETER_LIST, DOUBLE_RANGLE};
  private static final Object[] SINGLE_RANGLE_PRODUCTION = new Object[] {TYPE_PARAMETER_LIST_RANGLE, RANGLE};

  public TypeParameterListDoubleRAngleRule()
  {
    super(TYPE_PARAMETER_LIST_DOUBLE_RANGLE, DOUBLE_RANGLE_PRODUCTION, SINGLE_RANGLE_PRODUCTION);
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
      @SuppressWarnings("unchecked")
      ParseList<TypeParameter> typeParameterList = (ParseList<TypeParameter>) args[0];
      ParseInfo doubleRAngleInfo = (ParseInfo) args[1];
      int line = doubleRAngleInfo.getStartLine();
      if (line != doubleRAngleInfo.getEndLine())
      {
        throw new LanguageParseException("Found a DOUBLE_RANGLE \">>\" token which does not start and finish on the same line", doubleRAngleInfo);
      }
      int startPos = doubleRAngleInfo.getStartPos();
      if (doubleRAngleInfo.getEndPos() - startPos != 2)
      {
        throw new LanguageParseException("Found a DOUBLE_RANGLE \">>\" token which is not 2 characters long", doubleRAngleInfo);
      }
      ParseInfo firstAngleInfo = new ParseInfo(line, startPos, line, startPos + 1);
      ParseContainer<ParseList<TypeParameter>> firstContainer = new ParseContainer<ParseList<TypeParameter>>(typeParameterList,
                                                                      ParseInfo.combine(typeParameterList.getParseInfo(), firstAngleInfo));
      return new ParseContainer<ParseContainer<ParseList<TypeParameter>>>(firstContainer,
                                                                          ParseInfo.combine(typeParameterList.getParseInfo(), doubleRAngleInfo));
    }
    if (types == SINGLE_RANGLE_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<TypeParameter>> typeParameterList = (ParseContainer<ParseList<TypeParameter>>) args[0];
      return new ParseContainer<ParseContainer<ParseList<TypeParameter>>>(typeParameterList,
                                                                          ParseInfo.combine(typeParameterList.getParseInfo(), (ParseInfo) args[1]));
    }
    throw badTypeList();
  }

}
