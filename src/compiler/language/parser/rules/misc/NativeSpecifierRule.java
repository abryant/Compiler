package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.NATIVE_KEYWORD;
import static compiler.language.parser.ParseType.NATIVE_SPECIFIER;
import static compiler.language.parser.ParseType.RPAREN;
import static compiler.language.parser.ParseType.STRING_LITERAL;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.LexicalPhrase;
import compiler.language.ast.misc.NativeSpecifierAST;
import compiler.language.ast.terminal.StringLiteralAST;
import compiler.language.parser.ParseType;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public final class NativeSpecifierRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> PRODUCTION = new Production<ParseType>(NATIVE_KEYWORD, LPAREN, STRING_LITERAL, RPAREN);

  @SuppressWarnings("unchecked")
  public NativeSpecifierRule()
  {
    super(NATIVE_SPECIFIER, PRODUCTION);
  }

  /**
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      StringLiteralAST literal = (StringLiteralAST) args[2];
      return new NativeSpecifierAST(literal, LexicalPhrase.combine((LexicalPhrase) args[0], (LexicalPhrase) args[1], literal.getLexicalPhrase(), (LexicalPhrase) args[3]));
    }
    throw badTypeList();
  }

}
