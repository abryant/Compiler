package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.DECLARATION_ASSIGNEE;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.UNDERSCORE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.DeclarationAssigneeAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class DeclarationAssigneeRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(NAME);
  private static final Production<ParseType> EMPTY_PRODUCTION = new Production<ParseType>(UNDERSCORE);

  @SuppressWarnings("unchecked")
  public DeclarationAssigneeRule()
  {
    super(DECLARATION_ASSIGNEE, PRODUCTION, EMPTY_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      NameAST name = (NameAST) args[0];
      return new DeclarationAssigneeAST(name, name.getParseInfo());
    }
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new DeclarationAssigneeAST(null, (ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
