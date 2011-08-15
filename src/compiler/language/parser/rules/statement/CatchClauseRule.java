package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.CATCH_CLAUSE;
import static compiler.language.parser.ParseType.CATCH_KEYWORD;
import static compiler.language.parser.ParseType.MODIFIERS;
import static compiler.language.parser.ParseType.NAME;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.statement.BlockAST;
import compiler.language.ast.statement.CatchClauseAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.ParseList;
import compiler.language.parser.ParseType;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class CatchClauseRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION           = new Production<ParseType>(CATCH_KEYWORD,            POINTER_TYPE, NAME, BLOCK);
  private static final Production<ParseType> MODIFIERS_PRODUCTION = new Production<ParseType>(CATCH_KEYWORD, MODIFIERS, POINTER_TYPE, NAME, BLOCK);

  @SuppressWarnings("unchecked")
  public CatchClauseRule()
  {
    super(CATCH_CLAUSE, PRODUCTION, MODIFIERS_PRODUCTION);
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
      PointerTypeAST type = (PointerTypeAST) args[1];
      NameAST name = (NameAST) args[2];
      BlockAST block = (BlockAST) args[3];
      return new CatchClauseAST(new ModifierAST[0], type, name, block,
                             LexicalPhrase.combine((LexicalPhrase) args[0], type.getLexicalPhrase(), name.getLexicalPhrase(), block.getLexicalPhrase()));
    }
    if (MODIFIERS_PRODUCTION.equals(production))
    {
      @SuppressWarnings("unchecked")
      ParseList<ModifierAST> modifiers = (ParseList<ModifierAST>) args[1];
      PointerTypeAST type = (PointerTypeAST) args[2];
      NameAST name = (NameAST) args[3];
      BlockAST block = (BlockAST) args[4];
      return new CatchClauseAST(modifiers.toArray(new ModifierAST[0]), type, name, block,
                             LexicalPhrase.combine((LexicalPhrase) args[0], modifiers.getLexicalPhrase(), type.getLexicalPhrase(), name.getLexicalPhrase(), block.getLexicalPhrase()));
    }
    throw badTypeList();
  }

}
