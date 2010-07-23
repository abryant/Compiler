package compiler.language.parser.rules;

import static compiler.language.parser.ParseType.ABSTRACT_KEYWORD;
import static compiler.language.parser.ParseType.FINAL_KEYWORD;
import static compiler.language.parser.ParseType.IMMUTABLE_KEYWORD;
import static compiler.language.parser.ParseType.MODIFIER;
import static compiler.language.parser.ParseType.MUTABLE_KEYWORD;
import static compiler.language.parser.ParseType.NATIVE_SPECIFIER;
import static compiler.language.parser.ParseType.SINCE_SPECIFIER;
import static compiler.language.parser.ParseType.STATIC_KEYWORD;
import static compiler.language.parser.ParseType.SYNCHRONIZED_KEYWORD;
import static compiler.language.parser.ParseType.TRANSIENT_KEYWORD;
import static compiler.language.parser.ParseType.VOLATILE_KEYWORD;

import compiler.language.ast.Modifier;
import compiler.parser.Rule;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class ModifierRule extends Rule
{

  private static final Object[] STATIC_PRODUCTION = new Object[] {STATIC_KEYWORD};
  private static final Object[] ABSTRACT_PRODUCTION = new Object[] {ABSTRACT_KEYWORD};
  private static final Object[] FINAL_PRODUCTION = new Object[] {FINAL_KEYWORD};
  private static final Object[] MUTABLE_PRODUCTION = new Object[] {MUTABLE_KEYWORD};
  private static final Object[] IMMUTABLE_PRODUCTION = new Object[] {IMMUTABLE_KEYWORD};
  private static final Object[] SYNCHRONIZED_PRODUCTION = new Object[] {SYNCHRONIZED_KEYWORD};
  private static final Object[] TRANSIENT_PRODUCTION = new Object[] {TRANSIENT_KEYWORD};
  private static final Object[] VOLATILE_PRODUCTION = new Object[] {VOLATILE_KEYWORD};
  private static final Object[] NATIVE_SPECIFIER_PRODUCTION = new Object[] {NATIVE_SPECIFIER};
  private static final Object[] SINCE_SPECIFIER_PRODUCTION = new Object[] {SINCE_SPECIFIER};

  public ModifierRule()
  {
    super(MODIFIER, STATIC_PRODUCTION, ABSTRACT_PRODUCTION, FINAL_PRODUCTION, MUTABLE_PRODUCTION, IMMUTABLE_PRODUCTION,
                    SYNCHRONIZED_PRODUCTION, TRANSIENT_PRODUCTION, VOLATILE_PRODUCTION, NATIVE_SPECIFIER_PRODUCTION, SINCE_SPECIFIER_PRODUCTION);
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
    if (types == MUTABLE_PRODUCTION)
    {
      return Modifier.MUTABLE;
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
    if (types == NATIVE_SPECIFIER_PRODUCTION || types == SINCE_SPECIFIER_PRODUCTION)
    {
      // NativeSpecifier and SinceSpecifier are subclasses of Modifier, so we can just return them directly
      return args[0];
    }
    return null;
  }

}
