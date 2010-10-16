package compiler.language.parser;

import parser.lalr.LALRParserCodeGenerator;
import parser.lalr.LALRRuleSet;

/*
 * Created on 20 Sep 2010
 */

/**
 * @author Anthony Bryant
 */
public class LanguageParserGenerator
{

  public static void main(String[] args)
  {
    LALRRuleSet<ParseType> rules = LanguageRules.getRuleSet();
    LALRParserCodeGenerator<ParseType> codeGenerator = new LALRParserCodeGenerator<ParseType>(rules, ParseType.GENERATED_START_RULE);
    codeGenerator.generateCode(System.out);
  }

}
