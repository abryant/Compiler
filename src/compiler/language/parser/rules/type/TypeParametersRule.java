package compiler.language.parser.rules.type;

import static compiler.language.parser.ParseType.LANGLE;
import static compiler.language.parser.ParseType.TYPE_PARAMETERS;
import static compiler.language.parser.ParseType.TYPE_PARAMETER_LIST_RANGLE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.type.TypeParameterAST;
import compiler.language.parser.ParseType;

/*
 * Created on 13 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class TypeParametersRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(LANGLE, TYPE_PARAMETER_LIST_RANGLE);

  @SuppressWarnings("unchecked")
  public TypeParametersRule()
  {
    super(TYPE_PARAMETERS, PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseContainer<ParseList<TypeParameterAST>> container = (ParseContainer<ParseList<TypeParameterAST>>) args[1];
      ParseList<TypeParameterAST> list = container.getItem();
      list.setParseInfo(ParseInfo.combine((ParseInfo) args[0], container.getParseInfo()));
      return list;
    }
    throw badTypeList();
  }

}
