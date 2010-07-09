package compiler.language.parser;

import compiler.language.parser.rules.AccessSpecifierRule;
import compiler.language.parser.rules.AssigneeListRule;
import compiler.language.parser.rules.AssigneeRule;
import compiler.language.parser.rules.BlockRule;
import compiler.language.parser.rules.ClassDefinitionRule;
import compiler.language.parser.rules.ClassExtendsClauseRule;
import compiler.language.parser.rules.CompilationUnitRule;
import compiler.language.parser.rules.FieldRule;
import compiler.language.parser.rules.ImplementsClauseRule;
import compiler.language.parser.rules.ImportDeclarationRule;
import compiler.language.parser.rules.InterfaceDefinitionRule;
import compiler.language.parser.rules.InterfaceExtendsClauseRule;
import compiler.language.parser.rules.InterfaceListRule;
import compiler.language.parser.rules.MemberListRule;
import compiler.language.parser.rules.MemberRule;
import compiler.language.parser.rules.ModifierRule;
import compiler.language.parser.rules.ModifiersRule;
import compiler.language.parser.rules.NativeSpecificationRule;
import compiler.language.parser.rules.PackageDeclarationRule;
import compiler.language.parser.rules.PointerTypeRule;
import compiler.language.parser.rules.QNameRule;
import compiler.language.parser.rules.StatementsRule;
import compiler.language.parser.rules.ThrowsClauseRule;
import compiler.language.parser.rules.ThrowsListRule;
import compiler.language.parser.rules.TypeArgumentListRule;
import compiler.language.parser.rules.TypeArgumentRule;
import compiler.language.parser.rules.TypeArgumentsRule;
import compiler.language.parser.rules.TypeDefinitionRule;
import compiler.language.parser.rules.TypeRule;
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
    new AccessSpecifierRule(),
    new AssigneeListRule(),
    new AssigneeRule(),
    new BlockRule(),
    new ClassDefinitionRule(),
    new ClassExtendsClauseRule(),
    // startRule: new CompilationUnitRule(),
    new FieldRule(),
    new ImplementsClauseRule(),
    new ImportDeclarationRule(),
    new InterfaceDefinitionRule(),
    new InterfaceExtendsClauseRule(),
    new InterfaceListRule(),
    new MemberListRule(),
    new MemberRule(),
    new ModifierRule(),
    new ModifiersRule(),
    new NativeSpecificationRule(),
    new PackageDeclarationRule(),
    new PointerTypeRule(),
    new QNameRule(),
    new StatementsRule(),
    new ThrowsClauseRule(),
    new ThrowsListRule(),
    new TypeArgumentListRule(),
    new TypeArgumentRule(),
    new TypeArgumentsRule(),
    new TypeDefinitionRule(),
    new TypeRule(),
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
