package test.parser;

import compiler.parser.ParseException;
import compiler.parser.Parser;
import compiler.parser.Token;
import compiler.parser.lalr.LALRParserGenerator;
import compiler.parser.lalr.LALRRuleSet;
import compiler.parser.lalr.LALRState;

/*
 * Created on 7 Apr 2010
 */

/**
 * @author Anthony Bryant
 *
 */
public class ExpressionParser
{

  public static void main(String[] args) throws ParseException
  {
    LALRRuleSet ruleSet = new LALRRuleSet();
    ruleSet.addStartRule(new ExpressionRule());
    ruleSet.addRule(new SumRule());
    ruleSet.addRule(new ProductRule());
    ruleSet.addRule(new ValueRule());

    LALRParserGenerator generator = new LALRParserGenerator(ruleSet);
    generator.generate();

    LALRState startState = generator.getStartState();

    ExpressionTokenizer tokenizer = new ExpressionTokenizer();

    Parser parser = new Parser(startState, tokenizer);
    Token result = parser.parse();
    if (result.getType() != ExpressionType.EXPRESSION)
    {
      System.err.println("Resulted in non-expression type: " + result.getType());
      return;
    }
    Expression expression = (Expression) result.getValue();
    System.out.println("expression:" + expression);
    System.out.println("result = " + expression.evaluate());
  }

}
