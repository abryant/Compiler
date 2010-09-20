package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.CLASS_KEYWORD;
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
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 11 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class InstanciationExpressionRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(NEW_KEYWORD, POINTER_TYPE, PARAMETERS);
  private static final Production MEMBER_LIST_PRODUCTION = new Production(NEW_KEYWORD, POINTER_TYPE, PARAMETERS, CLASS_KEYWORD, LBRACE, MEMBER_LIST, RBRACE);

  public InstanciationExpressionRule()
  {
    super(INSTANCIATION_EXPRESSION, PRODUCTION, MEMBER_LIST_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      PointerType type = (PointerType) args[1];
      @SuppressWarnings("unchecked")
      ParseList<Parameter> parameters = (ParseList<Parameter>) args[2];
      return new InstanciationExpression(type, parameters.toArray(new Parameter[0]), null,
                                         ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), parameters.getParseInfo()));
    }
    if (MEMBER_LIST_PRODUCTION.equals(production))
    {
      PointerType type = (PointerType) args[1];
      @SuppressWarnings("unchecked")
      ParseList<Parameter> parameters = (ParseList<Parameter>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<Member> members = (ParseList<Member>) args[5];
      return new InstanciationExpression(type, parameters.toArray(new Parameter[0]), members.toArray(new Member[0]),
                                         ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), parameters.getParseInfo(),
                                                           (ParseInfo) args[3], (ParseInfo) args[4], members.getParseInfo(), (ParseInfo) args[6]));
    }
    throw badTypeList();
  }

}
