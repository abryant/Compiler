package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.DECLARATION_ASSIGNEE;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.UNDERSCORE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.misc.DeclarationAssigneeAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.parser.ParseType;

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
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      NameAST name = (NameAST) args[0];
      return new DeclarationAssigneeAST(name, name.getLexicalPhrase());
    }
    if (EMPTY_PRODUCTION.equals(production))
    {
      return new DeclarationAssigneeAST(null, (LexicalPhrase) args[0]);
    }
    throw badTypeList();
  }

}
