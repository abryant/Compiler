package test.brackets;

import parser.BadTokenException;
import parser.ParseException;
import parser.Parser;
import parser.Token;
import parser.lalr.LALRParserGenerator;
import parser.lalr.LALRRuleSet;

/*
 * Created on 15 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class BracketTest
{

  public static void main(String[] args) throws ParseException, BadTokenException
  {
    LALRRuleSet<BracketsType> rules = new LALRRuleSet<BracketsType>();
    rules.addStartRule(new BracketsRule());
    rules.addRule(new BracketRule());

    LALRParserGenerator<BracketsType> generator = new LALRParserGenerator<BracketsType>(rules);
    generator.generate(BracketsType.GENERATED_START_RULE);

    BracketTokenizer tokenizer = new BracketTokenizer();

    Parser<BracketsType> parser = new Parser<BracketsType>(generator.getStartState(), tokenizer);

    Token<BracketsType> result = parser.parse();
    System.out.println("Success! got: " + result.getType());
    Bracket[] brackets = (Bracket[]) result.getValue();
    for (Bracket b : brackets)
    {
      System.out.print(b);
    }
    System.out.println();
  }

}
