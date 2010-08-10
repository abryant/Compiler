package compiler.language.parser;

import compiler.parser.ParseException;
import compiler.parser.Parser;
import compiler.parser.Token;
import compiler.parser.Tokenizer;
import compiler.parser.lalr.LALRParserGenerator;
import compiler.parser.lalr.LALRRuleSet;

/*
 * Created on 30 Jun 2010
 */

/**
 * @author Anthony Bryant
 */
public class LanguageParser
{

  public static void main(String[] args)
  {
    LALRRuleSet rules = LanguageRules.getRuleSet();
    LALRParserGenerator generator = new LALRParserGenerator(rules);
    generator.generate();

    Tokenizer tokenizer = new LanguageTokenizer();
    Parser parser = new Parser(generator.getStartState(), tokenizer);
    try
    {
      Token result = parser.parse();
      System.out.println("Success!");
      System.out.println(result.getValue());
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }

  }

}
