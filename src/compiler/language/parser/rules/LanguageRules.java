package compiler.language.parser.rules;

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
    new PackageDeclarationRule(),
    new ImportDeclarationsRule(),
    new ImportDeclarationRule(),
    new TypeDefinitionsRule(),
    new QNameRule()
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
