package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.DECLARATION_ASSIGNEE;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.UNDERSCORE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.DeclarationAssignee;
import compiler.language.ast.terminal.Name;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class DeclarationAssigneeRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(NAME);
  private static final Production EMPTY_PRODUCTION = new Production(UNDERSCORE);

  public DeclarationAssigneeRule()
  {
    super(DECLARATION_ASSIGNEE, PRODUCTION, EMPTY_PRODUCTION);
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
      Name name = (Name) args[0];
      return new DeclarationAssignee(name, name.getParseInfo());
    }
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new DeclarationAssignee(null, (ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
