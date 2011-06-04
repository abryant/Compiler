package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.ENUM_CONSTANT;
import static compiler.language.parser.ParseType.LBRACE;
import static compiler.language.parser.ParseType.MEMBER_LIST;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.ARGUMENTS;
import static compiler.language.parser.ParseType.RBRACE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.member.MemberAST;
import compiler.language.ast.misc.ArgumentAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.typeDefinition.EnumConstantAST;
import compiler.language.parser.ParseList;
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
  private static final Production<ParseType> ARGUMENTS_PRODUCTION          = new Production<ParseType>(NAME, ARGUMENTS);
  private static final Production<ParseType> MEMBERS_PRODUCTION            = new Production<ParseType>(NAME,             LBRACE, MEMBER_LIST, RBRACE);
  private static final Production<ParseType> ARGUMENTS_MEMBERS_PRODUCTION  = new Production<ParseType>(NAME, ARGUMENTS, LBRACE, MEMBER_LIST, RBRACE);

  @SuppressWarnings("unchecked")
  public EnumConstantRule()
  {
    super(ENUM_CONSTANT, PRODUCTION, ARGUMENTS_PRODUCTION, MEMBERS_PRODUCTION, ARGUMENTS_MEMBERS_PRODUCTION);
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
      return new EnumConstantAST(name, new ArgumentAST[0], null, name.getParseInfo());
    }
    if (ARGUMENTS_PRODUCTION.equals(production))
    {
      NameAST name = (NameAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<ArgumentAST> arguments = (ParseList<ArgumentAST>) args[1];
      return new EnumConstantAST(name, arguments.toArray(new ArgumentAST[0]), null, ParseInfo.combine(name.getParseInfo(), arguments.getParseInfo()));
    }
    if (MEMBERS_PRODUCTION.equals(production))
    {
      NameAST name = (NameAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<MemberAST> members = (ParseList<MemberAST>) args[2];
      return new EnumConstantAST(name, new ArgumentAST[0], members.toArray(new MemberAST[0]),
                              ParseInfo.combine(name.getParseInfo(), (ParseInfo) args[1], members.getParseInfo(), (ParseInfo) args[3]));
    }
    if (ARGUMENTS_MEMBERS_PRODUCTION.equals(production))
    {
      NameAST name = (NameAST) args[0];
      @SuppressWarnings("unchecked")
      ParseList<ArgumentAST> arguments = (ParseList<ArgumentAST>) args[1];
      @SuppressWarnings("unchecked")
      ParseList<MemberAST> members = (ParseList<MemberAST>) args[3];
      return new EnumConstantAST(name, arguments.toArray(new ArgumentAST[0]), members.toArray(new MemberAST[0]),
                              ParseInfo.combine(name.getParseInfo(), arguments.getParseInfo(), (ParseInfo) args[2], members.getParseInfo(), (ParseInfo) args[4]));
    }
    throw badTypeList();
  }

}
