package test.expression;

import compiler.parser.BadTokenException;
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

  public static void main(String[] args) throws ParseException, BadTokenException
  {
    LALRRuleSet<ExpressionType> ruleSet = new LALRRuleSet<ExpressionType>();
    ruleSet.addStartRule(new ExpressionRule());
    ruleSet.addRule(new SumRule());
    ruleSet.addRule(new ProductRule());
    ruleSet.addRule(new ValueRule());

    LALRParserGenerator<ExpressionType> generator = new LALRParserGenerator<ExpressionType>(ruleSet);
    generator.generate(ExpressionType.GENERATED_START_RULE);

    LALRState<ExpressionType> startState = generator.getStartState();

    ExpressionTokenizer tokenizer = new ExpressionTokenizer();

    Parser<ExpressionType> parser = new Parser<ExpressionType>(startState, tokenizer);
    Token<ExpressionType> result = parser.parse();
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
