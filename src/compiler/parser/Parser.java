package compiler.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/*
 * Created on 7 Apr 2010
 */

/**
 * @author Anthony Bryant
 * 
 */
public class Parser
{
  
  /**
   * The empty type, a terminal used only when there are no input tokens.
   */
  public static final Object EMPTY_TYPE = new Object();
  
  private RuleSet ruleSet;
  private Tokenizer tokenizer;
  
  private LinkedList<Token> stack;
  
  /**
   * Creates a parser to parse the specified rule set.
   * @param ruleSet - the rule set to parse according to
   * @param tokenizer - the tokenizer to read input tokens from
   */
  public Parser(RuleSet ruleSet, Tokenizer tokenizer)
  {
    this.ruleSet = ruleSet;
    this.tokenizer = tokenizer;
  }
  
  /**
   * Parses the input from the tokenizer in order to reach target.
   * @param target - the target type to aim towards reaching
   * @return the value matched from the target type
   * @throws ParseException - if there is an error while parsing
   */
  public Object parse(Object target) throws ParseException
  {
    stack = new LinkedList<Token>();
    
    while (!(tokenizer.isFinished() && stack.size() == 1 && stack.getFirst().getType() == target))
    {
      if (stack.isEmpty())
      {
        if (tokenizer.isFinished())
        {
          // add the empty token if there are no more tokens to read and the stack is empty
          // i.e. if there is no input
          stack.add(new Token(EMPTY_TYPE, EMPTY_TYPE));
        }
        else
        {
          shift();
        }
        continue;
      }
      
      List<TypeUseEntry> typeUses = ruleSet.getTypeUses(stack.getLast().getType());
      if (typeUses == null)
      {
        // this type is never used, yet we have one on the stack
        throw new ParseException("Unexpected token: Type:" + stack.getLast().getType() + " Value:" + stack.getLast().getValue());
      }
      
      TypeUseEntry longestCompleteMatch = null;
      int longestCompleteMatchLength = -1;
      List<TypeUseEntry> incompleteMatches = new ArrayList<TypeUseEntry>();
      for (TypeUseEntry entry : typeUses)
      {
        Object[] requirementTypeList = entry.getRule().getRequirementTypeLists()[entry.getTypeListNum()];
        
        // check that the rule matches the tokens on the end of the stack
        int offset = entry.getOffset();
        if (stack.size() <= offset)
        {
          // there are not enough tokens on the stack to match this entry
          continue;
        }
        
        boolean matches = true;
        for (int i = 0; i < offset; i++)
        {
          if (stack.get(stack.size() - 1 - i).getType() != requirementTypeList[offset - i])
          {
            matches = false;
            break;
          }
        }
        // skip this use of the type if it does not match
        if (!matches)
        {
          continue;
        }
        
        // the entry matches, so either update the longest complete match or the incomplete matches set
        if (offset != requirementTypeList.length - 1)
        {
          // this is an incomplete match, so add it to the set
          incompleteMatches.add(entry);
          continue;
        }
        
        // this is a complete match, so update the longestCompleteMatch variable
        if (longestCompleteMatchLength < requirementTypeList.length)
        {
          // we have a new longest complete match
          longestCompleteMatch = entry;
          longestCompleteMatchLength = requirementTypeList.length;
        }
        else if (longestCompleteMatchLength == requirementTypeList.length)
        {
          // we have another complete match that has exactly the same length as the previous best
          // so the grammar has a problem - there are two instances of the right hand side of a rule
          StringBuffer ruleBuffer = new StringBuffer();
          for (Object type : requirementTypeList)
          {
            ruleBuffer.append(" " + type);
          }
          throw new ParseException("Grammar error! There are (at least) two possible reductions for:" + ruleBuffer.toString());
        }
      }
      
      // find the immediate follow set for the last token
      Set<Object> immediateFollowSet = ruleSet.getImmediateFollowSet(incompleteMatches);
      if (!immediateFollowSet.isEmpty())
      {
        Token lookahead = tokenizer.lookahead(1);
        if (lookahead != null && immediateFollowSet.contains(lookahead.getType()))
        {
          shift();
          continue;
        }
      }
      
      Set<Object> fullFollowSet = ruleSet.getFullFollowSet(stack.getLast().getType(), target);
      if (fullFollowSet.isEmpty())
      {
        // nothing can follow the current token
        if (!tokenizer.isFinished())
        {
          // error in the grammar! no tokens can follow the current one
          throw new ParseException("Grammar error! Nothing can follow the current token type: " + stack.getLast().getType());
        }
        
        // there are no more tokens, so carry on below and try to reduce or accept
      }
      else
      {
        Token lookahead = tokenizer.lookahead(1);
        if (lookahead == null ? !fullFollowSet.contains(null) : !fullFollowSet.contains(lookahead.getType()))
        {
          // the next token cannot follow this one - syntax error
          StringBuffer followSetBuffer = new StringBuffer();
          Iterator<Object> it = fullFollowSet.iterator();
          while (it.hasNext())
          {
            Object current = it.next();
            followSetBuffer.append(current == null ? "(end of input)" : current);
            if (it.hasNext())
            {
              followSetBuffer.append(", ");
            }
          }
          throw new ParseException("Syntax Error, got: " + (lookahead == null ? "(end of input)" : lookahead.getType()) + " expected one of: " + followSetBuffer); // TODO: change to "Syntax Error at " + tokenizer.getPosition()
        }
      }
      
      if (longestCompleteMatch == null)
      {
        if (!tokenizer.isFinished())
        {
          shift();
          continue;
        }
        // we cannot shift, as the tokenizer is finished, and there is no rule to reduce with
        // so check if we have generated a working match
        if (stack.size() == 1 && stack.get(0).getType() == target && tokenizer.isFinished())
        {
          System.out.println("Returning - not end"); // TODO
          return stack.get(0).getValue();
        }
        // we do not have a good match, so throw a ParseException
        StringBuffer followSetBuffer = new StringBuffer();
        // we would not have expected the end of input, or we would not be here, so remove it from the follow set
        // before we generate the list of expected tokens
        fullFollowSet.remove(null);
        Iterator<Object> it = fullFollowSet.iterator();
        while (it.hasNext())
        {
          followSetBuffer.append(it.next());
          if (it.hasNext())
          {
            followSetBuffer.append(", ");
          }
        }
        throw new ParseException("Syntax Error, got: (end of input) expected one of: " + followSetBuffer); // TODO: change to "Syntax Error at " + tokenizer.getPosition()
      }
      
      // reduce according to the longest rule that matches the end of the current stack
      reduce(longestCompleteMatch.getRule(), longestCompleteMatch.getTypeListNum());
      
    }
    
    // here we assume that the stack has one element containing the target token
    System.out.println("Returning - end"); // TODO
    return stack.get(0).getValue();
  }
  
  /**
   * Shifts the input by adding the next token from the tokenizer to the stack
   */
  private void shift()
  {
    stack.add(tokenizer.next());
  }
  
  /**
   * Reduces the stack according to the specified rule (and the number of the type list in that rule)
   * @param rule - the rule to reduce with
   * @param typeListNum - the number of the type list in the specified rule to reduce with
   * @throws ParseException - if the types on the stack do not match the type list in the rule
   */
  private void reduce(Rule rule, int typeListNum) throws ParseException
  {
    Object[] typeList = rule.getRequirementTypeLists()[typeListNum];
    Object[] valueList = new Object[typeList.length];
    if (stack.size() < typeList.length)
    {
      throw new ParseException("Bad stack length in reduce() Rule:" + rule);
    }
    for (int i = typeList.length - 1; i >= 0; i--)
    {
      Token t = stack.removeLast();
      if (t.getType() != typeList[i])
      {
        throw new ParseException("Type mismatch in reduce() Rule:" + rule + " index:" + i);
      }
      valueList[i] = t.getValue();
    }
    stack.add(new Token(rule.getType(), rule.match(valueList)));
  }
  
}
