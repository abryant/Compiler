package compiler.language.parser.rules;

import compiler.language.ast.Modifier;
import compiler.language.parser.Type;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ModifierRule extends Rule
{

  private static final Object[] STATIC_PRODUCTION = new Object[] {Type.STATIC_KEYWORD};
  private static final Object[] ABSTRACT_PRODUCTION = new Object[] {Type.ABSTRACT_KEYWORD};
  private static final Object[] FINAL_PRODUCTION = new Object[] {Type.FINAL_KEYWORD};
  private static final Object[] IMMUTABLE_PRODUCTION = new Object[] {Type.IMMUTABLE_KEYWORD};
  private static final Object[] SYNCHRONIZED_PRODUCTION = new Object[] {Type.SYNCHRONIZED_KEYWORD};
  private static final Object[] TRANSIENT_PRODUCTION = new Object[] {Type.TRANSIENT_KEYWORD};
  private static final Object[] VOLATILE_PRODUCTION = new Object[] {Type.VOLATILE_KEYWORD};
  private static final Object[] NATIVE_SPECIFIER_PRODUCTION = new Object[] {Type.NATIVE_SPECIFIER};

  public ModifierRule()
  {
    super(Type.MODIFIER, STATIC_PRODUCTION, ABSTRACT_PRODUCTION, FINAL_PRODUCTION, IMMUTABLE_PRODUCTION, SYNCHRONIZED_PRODUCTION,
                         TRANSIENT_PRODUCTION, VOLATILE_PRODUCTION, NATIVE_SPECIFIER_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args)
  {
    if (types == STATIC_PRODUCTION)
    {
      return Modifier.STATIC;
    }
    if (types == ABSTRACT_PRODUCTION)
    {
      return Modifier.ABSTRACT;
    }
    if (types == FINAL_PRODUCTION)
    {
      return Modifier.FINAL;
    }
    if (types == IMMUTABLE_PRODUCTION)
    {
      return Modifier.IMMUTABLE;
    }
    if (types == SYNCHRONIZED_PRODUCTION)
    {
      return Modifier.SYNCHRONIZED;
    }
    if (types == TRANSIENT_PRODUCTION)
    {
      return Modifier.TRANSIENT;
    }
    if (types == VOLATILE_PRODUCTION)
    {
      return Modifier.VOLATILE;
    }
    if (types == NATIVE_SPECIFIER_PRODUCTION)
    {
      // NativeSpecification is a subclass of Modifier, so we can just return the NativeSpecification directly
      return args[0];
    }
    return null;
  }

}
