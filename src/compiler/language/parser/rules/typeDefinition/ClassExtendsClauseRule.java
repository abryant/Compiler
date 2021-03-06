package compiler.language.parser.rules.typeDefinition;

import static compiler.language.parser.ParseType.CLASS_EXTENDS_CLAUSE;
import static compiler.language.parser.ParseType.EXTENDS_KEYWORD;
import static compiler.language.parser.ParseType.POINTER_TYPE;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.parser.ParseType;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ClassExtendsClauseRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> EMPTY_PRODUCTION = new Production<ParseType>();
  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(EXTENDS_KEYWORD, POINTER_TYPE);

  @SuppressWarnings("unchecked")
  public ClassExtendsClauseRule()
  {
    super(CLASS_EXTENDS_CLAUSE, EMPTY_PRODUCTION, PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EMPTY_PRODUCTION.equals(production))
    {
      return null;
    }
    if (PRODUCTION.equals(production))
    {
      PointerTypeAST type = (PointerTypeAST) args[1];
      type.setLexicalPhrase(LexicalPhrase.combine((LexicalPhrase) args[0], type.getLexicalPhrase()));
      return type;
    }
    throw badTypeList();
  }

}
