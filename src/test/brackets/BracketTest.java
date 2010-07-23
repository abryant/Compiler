package test.brackets;

import compiler.parser.ParseException;
import compiler.parser.Parser;
import compiler.parser.Token;
import compiler.parser.lalr.LALRParserGenerator;
import compiler.parser.lalr.LALRRuleSet;

/*
 * Created on 15 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class BracketTest
{

  public static void main(String[] args) throws ParseException
  {
    LALRRuleSet rules = new LALRRuleSet();
    rules.addStartRule(new BracketsRule());
    rules.addRule(new BracketRule());

    LALRParserGenerator generator = new LALRParserGenerator(rules);
    generator.generate();

    BracketTokenizer tokenizer = new BracketTokenizer();

    Parser parser = new Parser(generator.getStartState(), tokenizer);

    Token result = parser.parse();
    System.out.println("Success! got: " + result.getType());
    Bracket[] brackets = (Bracket[]) result.getValue();
    for (Bracket b : brackets)
    {
      System.out.print(b);
    }
    System.out.println();
  }

}
