package test.parser;

import compiler.parser.ParseException;
import compiler.parser.Parser;
import compiler.parser.RuleSet;

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
    RuleSet ruleSet = new RuleSet();
    ruleSet.add(new ExpressionRule());
    ruleSet.add(new SumRule());
    ruleSet.add(new ProductRule());
    ruleSet.add(new ValueRule());
    
    ExpressionTokenizer tokenizer = new ExpressionTokenizer();
    
    Parser parser = new Parser(ruleSet, tokenizer);
    Expression expression = (Expression) parser.parse(ExpressionType.EXPRESSION);
    System.out.println("expression:" + expression);
    System.out.println("result = " + expression.evaluate());
  }
  
}
