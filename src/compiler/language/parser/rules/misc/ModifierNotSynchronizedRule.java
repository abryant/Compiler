package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.ABSTRACT_KEYWORD;
import static compiler.language.parser.ParseType.FINAL_KEYWORD;
import static compiler.language.parser.ParseType.IMMUTABLE_KEYWORD;
import static compiler.language.parser.ParseType.MODIFIER_NOT_SYNCHRONIZED;
import static compiler.language.parser.ParseType.MUTABLE_KEYWORD;
import static compiler.language.parser.ParseType.NATIVE_SPECIFIER;
import static compiler.language.parser.ParseType.SEALED_KEYWORD;
import static compiler.language.parser.ParseType.SINCE_SPECIFIER;
import static compiler.language.parser.ParseType.STATIC_KEYWORD;
import static compiler.language.parser.ParseType.TRANSIENT_KEYWORD;
import static compiler.language.parser.ParseType.VOLATILE_KEYWORD;
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.ModifierAST;
import compiler.language.ast.misc.ModifierTypeAST;
import compiler.language.parser.ParseType;

/*
 * Created on 29 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class ModifierNotSynchronizedRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> STATIC_PRODUCTION = new Production<ParseType>(STATIC_KEYWORD);
  private static final Production<ParseType> ABSTRACT_PRODUCTION = new Production<ParseType>(ABSTRACT_KEYWORD);
  private static final Production<ParseType> FINAL_PRODUCTION = new Production<ParseType>(FINAL_KEYWORD);
  private static final Production<ParseType> MUTABLE_PRODUCTION = new Production<ParseType>(MUTABLE_KEYWORD);
  private static final Production<ParseType> IMMUTABLE_PRODUCTION = new Production<ParseType>(IMMUTABLE_KEYWORD);
  private static final Production<ParseType> TRANSIENT_PRODUCTION = new Production<ParseType>(TRANSIENT_KEYWORD);
  private static final Production<ParseType> VOLATILE_PRODUCTION = new Production<ParseType>(VOLATILE_KEYWORD);
  private static final Production<ParseType> NATIVE_SPECIFIER_PRODUCTION = new Production<ParseType>(NATIVE_SPECIFIER);
  private static final Production<ParseType> SINCE_SPECIFIER_PRODUCTION = new Production<ParseType>(SINCE_SPECIFIER);
  private static final Production<ParseType> SEALED_PRODUCTION = new Production<ParseType>(SEALED_KEYWORD);

  @SuppressWarnings("unchecked")
  public ModifierNotSynchronizedRule()
  {
    super(MODIFIER_NOT_SYNCHRONIZED, STATIC_PRODUCTION, ABSTRACT_PRODUCTION, FINAL_PRODUCTION, MUTABLE_PRODUCTION, IMMUTABLE_PRODUCTION,
                                     TRANSIENT_PRODUCTION, VOLATILE_PRODUCTION, NATIVE_SPECIFIER_PRODUCTION, SINCE_SPECIFIER_PRODUCTION, SEALED_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (STATIC_PRODUCTION.equals(production))
    {
      return new ModifierAST(ModifierTypeAST.STATIC, (ParseInfo) args[0]);
    }
    if (ABSTRACT_PRODUCTION.equals(production))
    {
      return new ModifierAST(ModifierTypeAST.ABSTRACT, (ParseInfo) args[0]);
    }
    if (FINAL_PRODUCTION.equals(production))
    {
      return new ModifierAST(ModifierTypeAST.FINAL, (ParseInfo) args[0]);
    }
    if (MUTABLE_PRODUCTION.equals(production))
    {
      return new ModifierAST(ModifierTypeAST.MUTABLE, (ParseInfo) args[0]);
    }
    if (IMMUTABLE_PRODUCTION.equals(production))
    {
      return new ModifierAST(ModifierTypeAST.IMMUTABLE, (ParseInfo) args[0]);
    }
    if (TRANSIENT_PRODUCTION.equals(production))
    {
      return new ModifierAST(ModifierTypeAST.TRANSIENT, (ParseInfo) args[0]);
    }
    if (VOLATILE_PRODUCTION.equals(production))
    {
      return new ModifierAST(ModifierTypeAST.VOLATILE, (ParseInfo) args[0]);
    }
    if (SEALED_PRODUCTION.equals(production))
    {
      return new ModifierAST(ModifierTypeAST.SEALED, (ParseInfo) args[0]);
    }
    if (NATIVE_SPECIFIER_PRODUCTION.equals(production) || SINCE_SPECIFIER_PRODUCTION.equals(production))
    {
      // NativeSpecifierAST and SinceSpecifierAST are subclasses of ModifierAST, so we can just return them directly
      return args[0];
    }
    throw badTypeList();
  }

}
