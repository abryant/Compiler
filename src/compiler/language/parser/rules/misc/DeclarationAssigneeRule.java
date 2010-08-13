package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.DECLARATION_ASSIGNEE;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.UNDERSCORE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.DeclarationAssignee;
import compiler.language.ast.terminal.Name;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class DeclarationAssigneeRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {NAME};
  private static final Object[] EMPTY_PRODUCTION = new Object[] {UNDERSCORE};

  public DeclarationAssigneeRule()
  {
    super(DECLARATION_ASSIGNEE, PRODUCTION, EMPTY_PRODUCTION);
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
      Name name = (Name) args[0];
      return new DeclarationAssignee(name, name.getParseInfo());
    }
    if (types == EMPTY_PRODUCTION)
    {
      return new DeclarationAssignee(null, (ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
