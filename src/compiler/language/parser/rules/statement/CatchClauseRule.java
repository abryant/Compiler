package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.CATCH_CLAUSE;
import static compiler.language.parser.ParseType.CATCH_KEYWORD;
import static compiler.language.parser.ParseType.MODIFIERS;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.POINTER_TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.misc.Modifier;
import compiler.language.ast.statement.Block;
import compiler.language.ast.statement.CatchClause;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class CatchClauseRule extends Rule
{

  private static final Object[] PRODUCTION           = new Object[] {CATCH_KEYWORD,            POINTER_TYPE, NAME, BLOCK};
  private static final Object[] MODIFIERS_PRODUCTION = new Object[] {CATCH_KEYWORD, MODIFIERS, POINTER_TYPE, NAME, BLOCK};

  public CatchClauseRule()
  {
    super(CATCH_CLAUSE, PRODUCTION, MODIFIERS_PRODUCTION);
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
      Name name = (Name) args[2];
      Block block = (Block) args[3];
      return new CatchClause(new Modifier[0], type, name, block,
                             ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), name.getParseInfo(), block.getParseInfo()));
    }
    if (types == MODIFIERS_PRODUCTION)
    {
      @SuppressWarnings("unchecked")
      ParseList<Modifier> modifiers = (ParseList<Modifier>) args[1];
      PointerType type = (PointerType) args[2];
      Name name = (Name) args[3];
      Block block = (Block) args[4];
      return new CatchClause(modifiers.toArray(new Modifier[0]), type, name, block,
                             ParseInfo.combine((ParseInfo) args[0], modifiers.getParseInfo(), type.getParseInfo(), name.getParseInfo(), block.getParseInfo()));
    }
    throw badTypeList();
  }

}
