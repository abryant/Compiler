package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.CLASS_KEYWORD;
import static compiler.language.parser.ParseType.INSTANCIATION_EXPRESSION;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.MEMBER_LIST;
import static compiler.language.parser.ParseType.NEW_KEYWORD;
import static compiler.language.parser.ParseType.PARAMETERS;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.RBRACE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.InstanciationExpressionAST;
import compiler.language.ast.member.MemberAST;
import compiler.language.ast.misc.ParameterAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class InstanciationExpressionRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(NEW_KEYWORD, POINTER_TYPE, PARAMETERS);
  private static final Production<ParseType> MEMBER_LIST_PRODUCTION = new Production<ParseType>(NEW_KEYWORD, POINTER_TYPE, PARAMETERS, CLASS_KEYWORD, LBRACE, MEMBER_LIST, RBRACE);

  @SuppressWarnings("unchecked")
  public InstanciationExpressionRule()
  {
    super(INSTANCIATION_EXPRESSION, PRODUCTION, MEMBER_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      PointerTypeAST type = (PointerTypeAST) args[1];
      @SuppressWarnings("unchecked")
      ParseList<ParameterAST> parameters = (ParseList<ParameterAST>) args[2];
      return new InstanciationExpressionAST(type, parameters.toArray(new ParameterAST[0]), null,
                                         ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), parameters.getParseInfo()));
    }
    if (MEMBER_LIST_PRODUCTION.equals(production))
    {
      PointerTypeAST type = (PointerTypeAST) args[1];
      @SuppressWarnings("unchecked")
      ParseList<ParameterAST> parameters = (ParseList<ParameterAST>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<MemberAST> members = (ParseList<MemberAST>) args[5];
      return new InstanciationExpressionAST(type, parameters.toArray(new ParameterAST[0]), members.toArray(new MemberAST[0]),
                                         ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), parameters.getParseInfo(),
                                                           (ParseInfo) args[3], (ParseInfo) args[4], members.getParseInfo(), (ParseInfo) args[6]));
    }
    throw badTypeList();
  }

}
