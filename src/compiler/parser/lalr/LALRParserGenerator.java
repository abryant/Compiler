package compiler.parser.lalr;

import compiler.parser.Rule;

/*
 * Created on 21 Jun 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class LALRParserGenerator
{

  private LALRRuleSet rules;

  /**
   * Creates a new LALR parser generator for the specified set of rules.
   * @param rules - the rule set to generate an LALR parser for
   */
  public LALRParserGenerator(LALRRuleSet rules)
  {
    this.rules = rules;
  }

  /**
   * Generates the parse table for the set of rules passed into this parser generator.
   * The parse table can be obtained from the getStartState() function.
   */
  public void generate()
  {
    Rule startRule = rules.getStartRule();

    // create the initial item set
    LALRItemSet initialSet = new LALRItemSet();
    int typeListCount = startRule.getRequirementTypeLists().length;
    for (int i = 0; i < typeListCount; i++)
    {
      initialSet.addKernelItem(new LALRItem(startRule, i, 0));
    }
    initialSet.calculateClosureItems(rules);


    // TODO: finish
  }



}
