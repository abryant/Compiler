package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.ENUM_CONSTANT;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.MEMBER_LIST;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.PARAMETERS;
import static compiler.language.parser.ParseType.RBRACE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.member.Member;
import compiler.language.ast.misc.Parameter;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.typeDefinition.EnumConstant;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class EnumConstantRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION                    = new Production(NAME);
  private static final Production PARAMETERS_PRODUCTION         = new Production(NAME, PARAMETERS);
  private static final Production MEMBERS_PRODUCTION            = new Production(NAME,             LBRACE, MEMBER_LIST, RBRACE);
  private static final Production PARAMETERS_MEMBERS_PRODUCTION = new Production(NAME, PARAMETERS, LBRACE, MEMBER_LIST, RBRACE);

  public EnumConstantRule()
  {
    super(ENUM_CONSTANT, PRODUCTION, PARAMETERS_PRODUCTION, MEMBERS_PRODUCTION, PARAMETERS_MEMBERS_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      Name name = (Name) args[0];
      return new EnumConstant(name, new Parameter[0], null, name.getParseInfo());
    }
    if (PARAMETERS_PRODUCTION.equals(production))
    {
      Name name = (Name) args[0];
      @SuppressWarnings("unchecked")
      ParseList<Parameter> parameters = (ParseList<Parameter>) args[1];
      return new EnumConstant(name, parameters.toArray(new Parameter[0]), null, ParseInfo.combine(name.getParseInfo(), parameters.getParseInfo()));
    }
    if (MEMBERS_PRODUCTION.equals(production))
    {
      Name name = (Name) args[0];
      @SuppressWarnings("unchecked")
      ParseList<Member> members = (ParseList<Member>) args[2];
      return new EnumConstant(name, new Parameter[0], members.toArray(new Member[0]),
                              ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], members.getParseInfo(), (ParseInfo) args[3]));
    }
    if (PARAMETERS_MEMBERS_PRODUCTION.equals(production))
    {
      Name name = (Name) args[0];
      @SuppressWarnings("unchecked")
      ParseList<Parameter> parameters = (ParseList<Parameter>) args[1];
      @SuppressWarnings("unchecked")
      ParseList<Member> members = (ParseList<Member>) args[3];
      return new EnumConstant(name, parameters.toArray(new Parameter[0]), members.toArray(new Member[0]),
                              ParseInfo.combine(name.getParseInfo(), parameters.getParseInfo(), (ParseInfo) args[2], members.getParseInfo(), (ParseInfo) args[4]));
    }
    throw badTypeList();
  }

}
