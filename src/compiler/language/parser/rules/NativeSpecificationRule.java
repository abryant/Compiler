package compiler.language.parser.rules;

import compiler.language.ast.NativeSpecification;
import compiler.language.parser.Type;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class NativeSpecificationRule extends Rule
{

  private static final Object[] PRODUCTION = new Object[] {Type.NATIVE_KEYWORD, Type.LPAREN, Type.STRING_LITERAL, Type.RPAREN};

  public NativeSpecificationRule()
  {
    super(Type.NATIVE_SPECIFIER, PRODUCTION);
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
