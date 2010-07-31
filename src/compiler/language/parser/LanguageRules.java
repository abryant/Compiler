package compiler.language.parser;

import compiler.language.parser.rules.AccessSpecifierRule;
import compiler.language.parser.rules.ArgumentListRule;
import compiler.language.parser.rules.ArgumentRule;
import compiler.language.parser.rules.ArgumentsRule;
import compiler.language.parser.rules.AssigneeListRule;
import compiler.language.parser.rules.AssigneeRule;
import compiler.language.parser.rules.BlockRule;
import compiler.language.parser.rules.BooleanTypeRule;
import compiler.language.parser.rules.CharacterTypeRule;
import compiler.language.parser.rules.ClassDefinitionRule;
import compiler.language.parser.rules.ClassExtendsClauseRule;
import compiler.language.parser.rules.ClosureTypeRule;
import compiler.language.parser.rules.CompilationUnitRule;
import compiler.language.parser.rules.ConstructorRule;
import compiler.language.parser.rules.EmptyStatementRule;
import compiler.language.parser.rules.EnumConstantListRule;
import compiler.language.parser.rules.EnumConstantRule;
import compiler.language.parser.rules.EnumConstantsRule;
import compiler.language.parser.rules.EnumDefinitionRule;
import compiler.language.parser.rules.FieldRule;
import compiler.language.parser.rules.FloatingTypeLengthRule;
import compiler.language.parser.rules.FloatingTypeRule;
import compiler.language.parser.rules.ImplementsClauseRule;
import compiler.language.parser.rules.ImportDeclarationRule;
import compiler.language.parser.rules.IntegerTypeLengthRule;
import compiler.language.parser.rules.IntegerTypeRule;
import compiler.language.parser.rules.InterfaceDefinitionRule;
import compiler.language.parser.rules.InterfaceExtendsClauseRule;
import compiler.language.parser.rules.InterfaceListRule;
import compiler.language.parser.rules.MemberHeaderRule;
import compiler.language.parser.rules.MemberListRule;
import compiler.language.parser.rules.MemberRule;
import compiler.language.parser.rules.MethodRule;
import compiler.language.parser.rules.ModifierRule;
import compiler.language.parser.rules.ModifiersRule;
import compiler.language.parser.rules.NativeSpecifierRule;
import compiler.language.parser.rules.NormalTypeParameterRule;
import compiler.language.parser.rules.OptionalBlockRule;
import compiler.language.parser.rules.PackageDeclarationRule;
import compiler.language.parser.rules.ParameterListRule;
import compiler.language.parser.rules.ParameterRule;
import compiler.language.parser.rules.ParametersRule;
import compiler.language.parser.rules.PointerTypeRule;
import compiler.language.parser.rules.PrimitiveTypeRule;
import compiler.language.parser.rules.PropertyRule;
import compiler.language.parser.rules.QNameRule;
import compiler.language.parser.rules.SinceSpecifierRule;
import compiler.language.parser.rules.StatementRule;
import compiler.language.parser.rules.StatementsRule;
import compiler.language.parser.rules.StaticInitializerRule;
import compiler.language.parser.rules.ThrowsClauseRule;
import compiler.language.parser.rules.ThrowsListRule;
import compiler.language.parser.rules.TupleTypeRule;
import compiler.language.parser.rules.TypeArgumentListRule;
import compiler.language.parser.rules.TypeArgumentRule;
import compiler.language.parser.rules.TypeArgumentsRule;
import compiler.language.parser.rules.TypeDefinitionRule;
import compiler.language.parser.rules.TypeListRule;
import compiler.language.parser.rules.TypeParameterListRule;
import compiler.language.parser.rules.TypeParameterRule;
import compiler.language.parser.rules.TypeParametersRule;
import compiler.language.parser.rules.TypeRule;
import compiler.language.parser.rules.VersionNumberRule;
import compiler.language.parser.rules.VoidTypeRule;
import compiler.language.parser.rules.WildcardTypeParameterRule;
import compiler.parser.Rule;
import compiler.parser.lalr.LALRRuleSet;

/*
 * Created on 30 Jun 2010
 */

/**
 * Manages the set of rules used by the language parser.
 *
 * @author Anthony Bryant
 */
public class LanguageRules
{

  private static final Rule startRule = new CompilationUnitRule();
  private static final Rule[] rules = new Rule[]
  {
    // TODO: split these and their associated packages up into sections, like is done in ParseType
    new AccessSpecifierRule(),
    new ArgumentListRule(),
    new ArgumentRule(),
    new ArgumentsRule(),
    new AssigneeListRule(),
    new AssigneeRule(),
    new BlockRule(),
    new BooleanTypeRule(),
    new CharacterTypeRule(),
    new ClassDefinitionRule(),
    new ClassExtendsClauseRule(),
    new ClosureTypeRule(),
    // startRule (does not need to be included here): new CompilationUnitRule(),
    new ConstructorRule(),
    new EmptyStatementRule(),
    new EnumConstantListRule(),
    new EnumConstantRule(),
    new EnumConstantsRule(),
    new EnumDefinitionRule(),
    new FieldRule(),
    new FloatingTypeLengthRule(),
    new FloatingTypeRule(),
    new ImplementsClauseRule(),
    new ImportDeclarationRule(),
    new IntegerTypeLengthRule(),
    new IntegerTypeRule(),
    new InterfaceDefinitionRule(),
    new InterfaceExtendsClauseRule(),
    new InterfaceListRule(),
    new MemberHeaderRule(),
    new MemberListRule(),
    new MemberRule(),
    new MethodRule(),
    new ModifierRule(),
    new ModifiersRule(),
    new NativeSpecifierRule(),
    new NormalTypeParameterRule(),
    new OptionalBlockRule(),
    new PackageDeclarationRule(),
    new ParameterListRule(),
    new ParameterRule(),
    new ParametersRule(),
    new PointerTypeRule(),
    new PrimitiveTypeRule(),
    new PropertyRule(),
    new QNameRule(),
    new SinceSpecifierRule(),
    new StatementRule(),
    new StatementsRule(),
    new StaticInitializerRule(),
    new ThrowsClauseRule(),
    new ThrowsListRule(),
    new TupleTypeRule(),
    new TypeArgumentListRule(),
    new TypeArgumentRule(),
    new TypeArgumentsRule(),
    new TypeDefinitionRule(),
    new TypeListRule(),
    new TypeParameterListRule(),
    new TypeParameterRule(),
    new TypeParametersRule(),
    new TypeRule(),
    new VersionNumberRule(),
    new VoidTypeRule(),
    new WildcardTypeParameterRule(),
  };

  /**
   * @return an LALRRuleSet containing all of the rules needed to parse this language
   */
  public static LALRRuleSet getRuleSet()
  {
    LALRRuleSet ruleSet = new LALRRuleSet();
    ruleSet.addStartRule(startRule);
    for (Rule rule : rules)
    {
      ruleSet.addRule(rule);
    }
    return ruleSet;
  }
}
