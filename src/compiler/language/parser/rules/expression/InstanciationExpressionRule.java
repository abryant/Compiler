package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.INSTANCIATION_EXPRESSION;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.MEMBER_LIST;
import static compiler.language.parser.ParseType.NEW_KEYWORD;
import static compiler.language.parser.ParseType.PARAMETERS;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import static compiler.language.parser.ParseType.RBRACE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.InstanciationExpression;
import compiler.language.ast.member.Member;
import compiler.language.ast.misc.Parameter;
import compiler.language.ast.type.PointerType;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class InstanciationExpressionRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {NEW_KEYWORD, POINTER_TYPE, PARAMETERS};
  private static final Object[] MEMBER_LIST_PRODUCTION = new Object[] {NEW_KEYWORD, POINTER_TYPE, PARAMETERS, LBRACE, MEMBER_LIST, RBRACE};

  public InstanciationExpressionRule()
  {
    super(INSTANCIATION_EXPRESSION, PRODUCTION, MEMBER_LIST_PRODUCTION);
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
      PointerType type = (PointerType) args[1];
      @SuppressWarnings("unchecked")
      ParseList<Parameter> parameters = (ParseList<Parameter>) args[2];
      return new InstanciationExpression(type, parameters.toArray(new Parameter[0]), null,
                                         ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), parameters.getParseInfo()));
    }
    if (types == MEMBER_LIST_PRODUCTION)
    {
      PointerType type = (PointerType) args[1];
      @SuppressWarnings("unchecked")
      ParseList<Parameter> parameters = (ParseList<Parameter>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<Member> members = (ParseList<Member>) args[4];
      return new InstanciationExpression(type, parameters.toArray(new Parameter[0]), members.toArray(new Member[0]),
                                         ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), parameters.getParseInfo(),
                                                           (ParseInfo) args[3], members.getParseInfo(), (ParseInfo) args[5]));
    }
    throw badTypeList();
  }

}
