package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.ENUM_CONSTANT;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.MEMBER_LIST;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.PARAMETERS;
import static compiler.language.parser.ParseType.RBRACE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.member.MemberAST;
import compiler.language.ast.misc.ParameterAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.typeDefinition.EnumConstantAST;
import compiler.language.parser.ParseType;

/*
 * Created on 31 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public final class EnumConstantRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION                    = new Production<ParseType>(NAME);
  private static final Production<ParseType> PARAMETERS_PRODUCTION         = new Production<ParseType>(NAME, PARAMETERS);
  private static final Production<ParseType> MEMBERS_PRODUCTION            = new Production<ParseType>(NAME,             LBRACE, MEMBER_LIST, RBRACE);
  private static final Production<ParseType> PARAMETERS_MEMBERS_PRODUCTION = new Production<ParseType>(NAME, PARAMETERS, LBRACE, MEMBER_LIST, RBRACE);

  @SuppressWarnings("unchecked")
  public EnumConstantRule()
  {
    super(ENUM_CONSTANT, PRODUCTION, PARAMETERS_PRODUCTION, MEMBERS_PRODUCTION, PARAMETERS_MEMBERS_PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      NameAST name = (NameAST) args[0];
      return new EnumConstantAST(name, new ParameterAST[0], null, name.getParseInfo());
    }
    if (PARAMETERS_PRODUCTION.equals(production))
    {
      NameAST name = (NameAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<ParameterAST> parameters = (ParseList<ParameterAST>) args[1];
      return new EnumConstantAST(name, parameters.toArray(new ParameterAST[0]), null, ParseInfo.combine(name.getParseInfo(), parameters.getParseInfo()));
    }
    if (MEMBERS_PRODUCTION.equals(production))
    {
      NameAST name = (NameAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<MemberAST> members = (ParseList<MemberAST>) args[2];
      return new EnumConstantAST(name, new ParameterAST[0], members.toArray(new MemberAST[0]),
                              ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], members.getParseInfo(), (ParseInfo) args[3]));
    }
    if (PARAMETERS_MEMBERS_PRODUCTION.equals(production))
    {
      NameAST name = (NameAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<ParameterAST> parameters = (ParseList<ParameterAST>) args[1];
      @SuppressWarnings("unchecked")
      ParseList<MemberAST> members = (ParseList<MemberAST>) args[3];
      return new EnumConstantAST(name, parameters.toArray(new ParameterAST[0]), members.toArray(new MemberAST[0]),
                              ParseInfo.combine(name.getParseInfo(), parameters.getParseInfo(), (ParseInfo) args[2], members.getParseInfo(), (ParseInfo) args[4]));
    }
    throw badTypeList();
  }

}
