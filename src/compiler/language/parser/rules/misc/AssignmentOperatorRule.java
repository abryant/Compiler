package compiler.language.parser.rules.misc;

import static compiler.language.parser.ParseType.AMPERSAND_EQUALS;
import static compiler.language.parser.ParseType.ASSIGNMENT_OPERATOR;
import static compiler.language.parser.ParseType.CARET_EQUALS;
import static compiler.language.parser.ParseType.DOUBLE_AMPERSAND_EQUALS;
import static compiler.language.parser.ParseType.DOUBLE_CARET_EQUALS;
import static compiler.language.parser.ParseType.DOUBLE_LANGLE_EQUALS;
import static compiler.language.parser.ParseType.DOUBLE_PIPE_EQUALS;
import static compiler.language.parser.ParseType.DOUBLE_RANGLE_EQUALS;
import static compiler.language.parser.ParseType.EQUALS;
import static compiler.language.parser.ParseType.FORWARD_SLASH_EQUALS;
import static compiler.language.parser.ParseType.MINUS_EQUALS;
import static compiler.language.parser.ParseType.PERCENT_EQUALS;
import static compiler.language.parser.ParseType.PIPE_EQUALS;
import static compiler.language.parser.ParseType.PLUS_EQUALS;
import static compiler.language.parser.ParseType.STAR_EQUALS;
import static compiler.language.parser.ParseType.TRIPLE_RANGLE_EQUALS;

import java.util.HashMap;
import java.util.Map;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.AssignmentOperator;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 23 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class AssignmentOperatorRule extends Rule
{

  private static final Map<Object[], AssignmentOperator> PRODUCTIONS = new HashMap<Object[], AssignmentOperator>();

  static
  {
    PRODUCTIONS.put(new Object[] {EQUALS},                  AssignmentOperator.EQUALS);
    PRODUCTIONS.put(new Object[] {PLUS_EQUALS},             AssignmentOperator.PLUS_EQUALS);
    PRODUCTIONS.put(new Object[] {MINUS_EQUALS},            AssignmentOperator.MINUS_EQUALS);
    PRODUCTIONS.put(new Object[] {STAR_EQUALS},             AssignmentOperator.TIMES_EQUALS);
    PRODUCTIONS.put(new Object[] {FORWARD_SLASH_EQUALS},    AssignmentOperator.DIVIDE_EQUALS);
    PRODUCTIONS.put(new Object[] {PERCENT_EQUALS},          AssignmentOperator.MODULUS_EQUALS);
    PRODUCTIONS.put(new Object[] {DOUBLE_AMPERSAND_EQUALS}, AssignmentOperator.BOOLEAN_AND_EQUALS);
    PRODUCTIONS.put(new Object[] {DOUBLE_PIPE_EQUALS},      AssignmentOperator.BOOLEAN_OR_EQUALS);
    PRODUCTIONS.put(new Object[] {DOUBLE_CARET_EQUALS},     AssignmentOperator.BOOLEAN_XOR_EQUALS);
    PRODUCTIONS.put(new Object[] {AMPERSAND_EQUALS},        AssignmentOperator.BITWISE_AND_EQUALS);
    PRODUCTIONS.put(new Object[] {PIPE_EQUALS},             AssignmentOperator.BITWISE_OR_EQUALS);
    PRODUCTIONS.put(new Object[] {CARET_EQUALS},            AssignmentOperator.BITWISE_XOR_EQUALS);
    PRODUCTIONS.put(new Object[] {DOUBLE_LANGLE_EQUALS},    AssignmentOperator.LEFT_SHIFT_EQUALS);
    PRODUCTIONS.put(new Object[] {DOUBLE_RANGLE_EQUALS},    AssignmentOperator.ARITHMETIC_RIGHT_SHIFT_EQUALS);
    PRODUCTIONS.put(new Object[] {TRIPLE_RANGLE_EQUALS},    AssignmentOperator.LOGICAL_RIGHT_SHIFT_EQUALS);
  }

  public AssignmentOperatorRule()
  {
    super(ASSIGNMENT_OPERATOR, PRODUCTIONS.keySet().toArray(new Object[0][]));
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    AssignmentOperator operator = PRODUCTIONS.get(types);
    if (operator != null)
    {
      return new ParseContainer<AssignmentOperator>(operator, (ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
