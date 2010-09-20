package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.NATIVE_KEYWORD;
import static compiler.language.parser.ParseType.NATIVE_SPECIFIER;
import static compiler.language.parser.ParseType.RPAREN;
import static compiler.language.parser.ParseType.STRING_LITERAL;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.NativeSpecifier;
import compiler.language.ast.terminal.StringLiteral;
import compiler.language.parser.ParseType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

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
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      StringLiteral literal = (StringLiteral) args[2];
      return new NativeSpecifier(literal, ParseInfo.combine((ParseInfo) args[0], (ParseInfo) args[1], literal.getParseInfo(), (ParseInfo) args[3]));
    }
    throw badTypeList();
  }

}
