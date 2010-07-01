package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.LPAREN;
import static compiler.language.parser.ParseType.NATIVE_KEYWORD;
import static compiler.language.parser.ParseType.NATIVE_SPECIFIER;
import static compiler.language.parser.ParseType.RPAREN;
import static compiler.language.parser.ParseType.STRING_LITERAL;

import compiler.language.ast.NativeSpecification;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class NativeSpecificationRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {NATIVE_KEYWORD, LPAREN, STRING_LITERAL, RPAREN};

  public NativeSpecificationRule()
  {
    super(NATIVE_SPECIFIER, PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == PRODUCTION)
    {
      return new NativeSpecification((String) args[2]);
    }
    throw badTypeList();
  }

}
