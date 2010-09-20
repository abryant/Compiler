package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.ABSTRACT_KEYWORD;
import static compiler.language.parser.ParseType.FINAL_KEYWORD;
import static compiler.language.parser.ParseType.IMMUTABLE_KEYWORD;
import static compiler.language.parser.ParseType.MODIFIER_NOT_SYNCHRONIZED;
import static compiler.language.parser.ParseType.MUTABLE_KEYWORD;
import static compiler.language.parser.ParseType.NATIVE_SPECIFIER;
import static compiler.language.parser.ParseType.SINCE_SPECIFIER;
import static compiler.language.parser.ParseType.STATIC_KEYWORD;
import static compiler.language.parser.ParseType.TRANSIENT_KEYWORD;
import static compiler.language.parser.ParseType.VOLATILE_KEYWORD;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.Modifier;
import compiler.language.ast.misc.ModifierType;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ModifierNotSynchronizedRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production STATIC_PRODUCTION = new Production(STATIC_KEYWORD);
  private static final Production ABSTRACT_PRODUCTION = new Production(ABSTRACT_KEYWORD);
  private static final Production FINAL_PRODUCTION = new Production(FINAL_KEYWORD);
  private static final Production MUTABLE_PRODUCTION = new Production(MUTABLE_KEYWORD);
  private static final Production IMMUTABLE_PRODUCTION = new Production(IMMUTABLE_KEYWORD);
  private static final Production TRANSIENT_PRODUCTION = new Production(TRANSIENT_KEYWORD);
  private static final Production VOLATILE_PRODUCTION = new Production(VOLATILE_KEYWORD);
  private static final Production NATIVE_SPECIFIER_PRODUCTION = new Production(NATIVE_SPECIFIER);
  private static final Production SINCE_SPECIFIER_PRODUCTION = new Production(SINCE_SPECIFIER);

  public ModifierNotSynchronizedRule()
  {
    super(MODIFIER_NOT_SYNCHRONIZED, STATIC_PRODUCTION, ABSTRACT_PRODUCTION, FINAL_PRODUCTION, MUTABLE_PRODUCTION, IMMUTABLE_PRODUCTION,
                                     TRANSIENT_PRODUCTION, VOLATILE_PRODUCTION, NATIVE_SPECIFIER_PRODUCTION, SINCE_SPECIFIER_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (STATIC_PRODUCTION.equals(production))
    {
      return new Modifier(ModifierType.STATIC, (ParseInfo) args[0]);
    }
    if (ABSTRACT_PRODUCTION.equals(production))
    {
      return new Modifier(ModifierType.ABSTRACT, (ParseInfo) args[0]);
    }
    if (FINAL_PRODUCTION.equals(production))
    {
      return new Modifier(ModifierType.FINAL, (ParseInfo) args[0]);
    }
    if (MUTABLE_PRODUCTION.equals(production))
    {
      return new Modifier(ModifierType.MUTABLE, (ParseInfo) args[0]);
    }
    if (IMMUTABLE_PRODUCTION.equals(production))
    {
      return new Modifier(ModifierType.IMMUTABLE, (ParseInfo) args[0]);
    }
    if (TRANSIENT_PRODUCTION.equals(production))
    {
      return new Modifier(ModifierType.TRANSIENT, (ParseInfo) args[0]);
    }
    if (VOLATILE_PRODUCTION.equals(production))
    {
      return new Modifier(ModifierType.VOLATILE, (ParseInfo) args[0]);
    }
    if (NATIVE_SPECIFIER_PRODUCTION.equals(production) || SINCE_SPECIFIER_PRODUCTION.equals(production))
    {
      // NativeSpecifier and SinceSpecifier are subclasses of Modifier, so we can just return them directly
      return args[0];
    }
    throw badTypeList();
  }

}
