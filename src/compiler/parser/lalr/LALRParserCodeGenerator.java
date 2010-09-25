package compiler.parser.lalr;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import compiler.parser.AcceptAction;
import compiler.parser.ReduceAction;
import compiler.parser.Rule;

/*
 * Created on 20 Sep 2010
 */

/**
 * @author Anthony Bryant
 * @param <T> - the enum type that holds all possible values for the token type
 */
public class LALRParserCodeGenerator<T extends Enum<T>>
{

  private LALRState<T> startState;
  private Rule<T> startRule;

  private T generatedStartRuleType;
  private String tokenType;

  /**
   * Creates a new LALR Parser code generator to generate a parser for the specified rule set.
   * @param rules - the set of rules to create a parser for
   * @param generatedStartRuleType - the type in the type enum that will represent the generated start rule and should have its rule added by the generator
   */
  public LALRParserCodeGenerator(LALRRuleSet<T> rules, T generatedStartRuleType)
  {
    this.generatedStartRuleType = generatedStartRuleType;
    tokenType = generatedStartRuleType.getClass().getSimpleName();
    // get the start rule here, as it will be overwritten by the generator later on
    startRule = rules.getStartRule();

    long startTime = System.currentTimeMillis();
    LALRParserGenerator<T> generator = new LALRParserGenerator<T>(rules);
    generator.generate(generatedStartRuleType);
    System.err.println("Generated parser in " + (System.currentTimeMillis() - startTime) + "ms");

    startState = generator.getStartState();
  }

  /**
   * Generates the code for the parser, and writes it to the specified PrintStream
   * @param out - the PrintStream to write the code to
   */
  public void generateCode(PrintStream out)
  {
    // find all of the states and rules that will be used in the parser
    List<LALRState<T>> states = new ArrayList<LALRState<T>>();
    Map<LALRState<T>, Integer> stateNumbers = new HashMap<LALRState<T>, Integer>();

    List<Rule<T>> rules = new ArrayList<Rule<T>>();
    Map<Rule<T>, Integer> ruleNumbers = new HashMap<Rule<T>, Integer>();

    Deque<LALRState<T>> queue = new LinkedList<LALRState<T>>();

    queue.add(startState);

    while (!queue.isEmpty())
    {
      LALRState<T> state = queue.poll();
      if (stateNumbers.containsKey(state))
      {
        continue;
      }
      states.add(state);
      stateNumbers.put(state, states.size() - 1);

      Map<T, LALRState<T>> shiftRules = state.getShiftRules();
      if (shiftRules != null)
      {
        for (LALRState<T> subState : shiftRules.values())
        {
          queue.add(subState);
        }
      }
      Map<T, LALRState<T>> gotoRules = state.getGotoRules();
      if (gotoRules != null)
      {
        for (LALRState<T> subState : gotoRules.values())
        {
          queue.add(subState);
        }
      }

      Map<T, ReduceAction<T>> reduceActions = state.getReduceActions();
      if (reduceActions != null)
      {
        for (ReduceAction<T> action : reduceActions.values())
        {
          Rule<T> rule = action.getRule();
          if (!ruleNumbers.containsKey(rule))
          {
            rules.add(rule);
            ruleNumbers.put(rule, rules.size() - 1);
          }
        }
      }
      Map<T, AcceptAction<T>> acceptActions = state.getAcceptActions();
      if (acceptActions != null)
      {
        for (AcceptAction<T> action : acceptActions.values())
        {
          Rule<T> rule = action.getRule();
          if (!ruleNumbers.containsKey(rule))
          {
            rules.add(rule);
            ruleNumbers.put(rule, rules.size() - 1);
          }
        }
      }
    }

    for (int i = 0; i < rules.size(); i++)
    {
      Rule<T> rule = rules.get(i);
      if (LALRParserGenerator.GeneratedStartRule.class.isInstance(rule))
      {
        // this is the generated start rule, so create our own generated start rule for it
        out.println("private static final GeneratedStartRule RULE_" + i + " = new GeneratedStartRule();");
        continue;
      }
      String className = rule.getClass().getSimpleName();
      out.println("private static final " + className + " RULE_" + i + " = new " + className + "();");
    }
    out.println();
    out.println("private Deque<Integer> stateStack = new LinkedList<Integer>();");
    out.println("private Deque<Token<" + tokenType + ">> tokenStack = new LinkedList<Token<" + tokenType + ">>();");
    out.println("private Tokenizer<" + tokenType + "> tokenizer;");
    out.println();
    out.println("private boolean accepted = false;");
    out.println();
    out.println("public ConstructorAST(Tokenizer<" + tokenType + "> tokenizer)");
    out.println("{");
    out.println("  this.tokenizer = tokenizer;");
    out.println("}");
    out.println();
    out.println("private final boolean reduce(Rule<" + tokenType + "> rule, int productionIndex) throws ParseException");
    out.println("{");
    out.println("  Production<" + tokenType + "> production = rule.getProductions()[productionIndex];");
    out.println("  " + tokenType + "[] productionTypes = production.getTypes();");
    out.println("  if (stateStack.size() <= productionTypes.length || tokenStack.size() < productionTypes.length)");
    out.println("  {");
    out.println("    throw new ParseException(\"Bad reduction of rule, not enough elements\");");
    out.println("  }");
    out.println();
    out.println("  // get the list of token values");
    out.println("  Object[] values = new Object[productionTypes.length];");
    out.println("  for (int i = values.length - 1; i >= 0; i--)");
    out.println("  {");
    out.println("    Token<" + tokenType + "> t = tokenStack.removeFirst();");
    out.println("    if (!t.getType().equals(productionTypes[i]))");
    out.println("    {");
    out.println("      throw new ParseException(\"Bad reduction of rule, invalid token type\");");
    out.println("    }");
    out.println("    values[i] = t.getValue();");
    out.println();
    out.println("    // remove the top state from the state stack");
    out.println("    stateStack.removeFirst();");
    out.println("  }");
    out.println();
    out.println("  Object result = rule.match(production, values);");
    out.println("  Token<" + tokenType + "> nonTerminal = new Token<" + tokenType + ">(rule.getType(), result);");
    out.println();
    out.println("  Integer topState = stateStack.peekFirst();");
    out.println("  Integer gotoState = findGotoState(topState, nonTerminal.getType());");
    out.println();
    out.println("  stateStack.addFirst(gotoState);");
    out.println("  tokenStack.addFirst(nonTerminal);");
    out.println();
    out.println("  return false;");
    out.println("}");
    out.println();

    out.println("private static class GeneratedStartRule extends Rule<" + tokenType + ">");
    out.println("{");
    out.println("  private static final long serialVersionUID = 1L;");
    out.println();
    out.println("  @SuppressWarnings(\"unchecked\") // this is unchecked because a generic array has to be created for the varargs");
    out.println("  public GeneratedStartRule()");
    out.println("  {");
    out.println("    super(" + generatedStartRuleType + ", new Production<" + tokenType + ">(" + startRule.getType() + "));");
    out.println("  }");
    out.println();
    out.println("  @Override");
    out.println("  public Object match(Production<" + tokenType + "> production, Object[] args) throws ParseException");
    out.println("  {");
    out.println("    " + tokenType + "[] productionTypes = production.getTypes();");
    out.println("    if (productionTypes.length == 1 && productionTypes[0] == " + startRule.getType() + ")");
    out.println("    {");
    out.println("      return args[0];");
    out.println("    }");
    out.println("    throw badTypeList();");
    out.println("  }");
    out.println("}");
    out.println();

    out.println("public Token<" + tokenType + "> parse() throws ParseException, BadTokenException");
    out.println("{");
    out.println("  stateStack.addFirst(" + stateNumbers.get(startState) + ");");
    out.println("  Token<" + tokenType + "> lookahead = tokenizer.next();");
    out.println();
    out.println("  while (true)");
    out.println("  {");
    out.println("    Integer state = stateStack.peekFirst();");
    out.println("    boolean used = false;");
    out.println("    switch (state)");
    out.println("    {");
    for (int i = 0; i < states.size(); i++)
    {
      out.println("    case " + i + ": used = action" + i + "(lookahead); break;");
    }
    out.println("    default: throw new IllegalStateException();");
    out.println("    }");
    out.println("    if (accepted)");
    out.println("    {");
    out.println("      return tokenStack.removeFirst();");
    out.println("    }");
    out.println("    if (used)");
    out.println("    {");
    out.println("      lookahead = tokenizer.next();");
    out.println("    }");
    out.println("  }");
    out.println("}");
    out.println();
    out.println("private final int findGotoState(int state, " + tokenType + " tokenType)");
    out.println("{");
    out.println("  switch (state)");
    out.println("  {");
    for (int i = 0; i < states.size(); i++)
    {
      Map<T, LALRState<T>> gotoRules = states.get(i).getGotoRules();
      if (gotoRules == null || gotoRules.size() == 0)
      {
        continue;
      }
      out.println("  case " + i + ": return goto" + i + "(tokenType);");
    }
    out.println("  default: break;");
    out.println("  }");
    out.println("  throw new IllegalArgumentException();");
    out.println("}");

    for (int i = 0; i < states.size(); i++)
    {
      LALRState<T> state = states.get(i);
      Map<T, LALRState<T>> shiftRules = state.getShiftRules();
      Map<T, ReduceAction<T>> reduceActions = state.getReduceActions();
      Map<T, AcceptAction<T>> acceptActions = state.getAcceptActions();
      boolean canThrowParseException = (reduceActions != null && !reduceActions.isEmpty()) ||
                                       (acceptActions != null && !acceptActions.isEmpty());
      out.println();
      out.println("private final boolean action" + i + "(Token<" + tokenType + "> token) throws " + (canThrowParseException ? "ParseException, " : "") + "BadTokenException");
      out.println("{");
      if (shiftRules != null && shiftRules.containsKey(null))
      {
        out.println("  if (token == null)");
        out.println("  {");
        out.println("    stateStack.addFirst(" + stateNumbers.get(shiftRules.get(null)) + ");");
        out.println("    tokenStack.addFirst(token);");
        out.println("    return true;");
        out.println("  }");
        out.println("  switch (token.getType().ordinal())");
      }
      else if (reduceActions != null && reduceActions.containsKey(null))
      {
        ReduceAction<T> action = reduceActions.get(null);
        out.println("  if (token == null)");
        out.println("  {");
        out.println("    return reduce(RULE_" + ruleNumbers.get(action.getRule()) + ", " + action.getProductionIndex() + ");");
        out.println("  }");
        out.println("  switch (token.getType().ordinal())");
      }
      else if (acceptActions != null && acceptActions.containsKey(null))
      {
        AcceptAction<T> action = acceptActions.get(null);
        out.println("  if (token == null)");
        out.println("  {");
        out.println("    accepted = true;");
        out.println("    return reduce(RULE_" + ruleNumbers.get(action.getRule()) + ", " + action.getProductionIndex() + ");");
        out.println("  }");
        out.println("  switch (token.getType().ordinal())");
      }
      else
      {
        out.println("  switch (token != null ? token.getType().ordinal() : null)");
      }
      out.println("  {");
      Set<T> possibleTokenTypes = new HashSet<T>();
      if (shiftRules != null)
      {
        for (Entry<T, LALRState<T>> entry : shiftRules.entrySet())
        {
          if (entry.getKey() == null)
          {
            continue;
          }
          out.println("  case " + entry.getKey().ordinal() + ":");
          out.println("    stateStack.addFirst(" + stateNumbers.get(entry.getValue()) + ");");
          out.println("    tokenStack.addFirst(token);");
          out.println("    return true;");
        }
        possibleTokenTypes.addAll(shiftRules.keySet());
      }
      if (reduceActions != null)
      {
        for (Entry<T, ReduceAction<T>> entry : reduceActions.entrySet())
        {
          if (entry.getKey() == null)
          {
            continue;
          }
          ReduceAction<T> action = entry.getValue();
          out.println("  case " + entry.getKey().ordinal() + ":");
          out.println("    return reduce(RULE_" + ruleNumbers.get(action.getRule()) + ", " + action.getProductionIndex() + ");");
        }
        possibleTokenTypes.addAll(reduceActions.keySet());
      }
      if (acceptActions != null)
      {
        for (Entry<T, AcceptAction<T>> entry : acceptActions.entrySet())
        {
          if (entry.getKey() == null)
          {
            continue;
          }
          AcceptAction<T> action = entry.getValue();
          out.println("  case " + entry.getKey().ordinal() + ":");
          out.println("    accepted = true;");
          out.println("    return reduce(RULE_" + ruleNumbers.get(action.getRule()) + ", " + action.getProductionIndex() + ");");
        }
        possibleTokenTypes.addAll(acceptActions.keySet());
      }
      out.println("  default: throw new BadTokenException(token, new Object[] {" + buildStringList(possibleTokenTypes.toArray()) + "});");
      out.println("  }");
      out.println("}");

      Map<T, LALRState<T>> gotoRules = state.getGotoRules();
      if (gotoRules == null || gotoRules.size() == 0)
      {
        continue;
      }

      out.println();
      out.println("private final int goto" + i + "(" + tokenType + " tokenType)");
      out.println("{");
      out.println("  switch (tokenType.ordinal())");
      out.println("  {");
      for (Entry<T, LALRState<T>> entry : gotoRules.entrySet())
      {
        out.println("  case " + entry.getKey().ordinal() + ": return " + stateNumbers.get(entry.getValue()) + ";");
      }
      out.println("  default: return -1;");
      out.println("  }");
      out.println("}");
    }
  }

  /**
   * Builds a string representing a list of the specified objects, separated by commas.
   * @param objects - the objects to convert to Strings and add to the list
   * @return the String representation of the list
   */
  private static String buildStringList(Object[] objects)
  {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < objects.length; i++)
    {
      buffer.append(objects[i]);
      if (i != objects.length - 1)
      {
        buffer.append(", ");
      }
    }
    return buffer.toString();
  }
}
