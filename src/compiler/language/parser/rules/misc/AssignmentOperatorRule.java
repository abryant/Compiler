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
import parser.ParseException;
import parser.Production;
import parser.Rule;

import compiler.language.ast.ParseContainer;
import compiler.language.ast.ParseInfo;
import compiler.language.ast.misc.AssignmentOperatorAST;
import compiler.language.parser.ParseType;

/*
 * Created on 23 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public final class AssignmentOperatorRule extends Rule<ParseType>
{
  private static final long serialVersionUID = 1L;

  private static final Production<ParseType> EQUALS_PRODUCTION                  = new Production<ParseType>(EQUALS);
  private static final Production<ParseType> PLUS_EQUALS_PRODUCTION             = new Production<ParseType>(PLUS_EQUALS);
  private static final Production<ParseType> MINUS_EQUALS_PRODUCTION            = new Production<ParseType>(MINUS_EQUALS);
  private static final Production<ParseType> STAR_EQUALS_PRODUCTION             = new Production<ParseType>(STAR_EQUALS);
  private static final Production<ParseType> FORWARD_SLASH_EQUALS_PRODUCTION    = new Production<ParseType>(FORWARD_SLASH_EQUALS);
  private static final Production<ParseType> PERCENT_EQUALS_PRODUCTION          = new Production<ParseType>(PERCENT_EQUALS);
  private static final Production<ParseType> DOUBLE_AMPERSAND_EQUALS_PRODUCTION = new Production<ParseType>(DOUBLE_AMPERSAND_EQUALS);
  private static final Production<ParseType> DOUBLE_PIPE_EQUALS_PRODUCTION      = new Production<ParseType>(DOUBLE_PIPE_EQUALS);
  private static final Production<ParseType> DOUBLE_CARET_EQUALS_PRODUCTION     = new Production<ParseType>(DOUBLE_CARET_EQUALS);
  private static final Production<ParseType> AMPERSAND_EQUALS_PRODUCTION        = new Production<ParseType>(AMPERSAND_EQUALS);
  private static final Production<ParseType> PIPE_EQUALS_PRODUCTION             = new Production<ParseType>(PIPE_EQUALS);
  private static final Production<ParseType> CARET_EQUALS_PRODUCTION            = new Production<ParseType>(CARET_EQUALS);
  private static final Production<ParseType> DOUBLE_LANGLE_EQUALS_PRODUCTION    = new Production<ParseType>(DOUBLE_LANGLE_EQUALS);
  private static final Production<ParseType> DOUBLE_RANGLE_EQUALS_PRODUCTION    = new Production<ParseType>(DOUBLE_RANGLE_EQUALS);
  private static final Production<ParseType> TRIPLE_RANGLE_EQUALS_PRODUCTION    = new Production<ParseType>(TRIPLE_RANGLE_EQUALS);

  @SuppressWarnings("unchecked")
  public AssignmentOperatorRule()
  {
    super(ASSIGNMENT_OPERATOR, EQUALS_PRODUCTION,
                               PLUS_EQUALS_PRODUCTION,
                               MINUS_EQUALS_PRODUCTION,
                               STAR_EQUALS_PRODUCTION,
                               FORWARD_SLASH_EQUALS_PRODUCTION,
                               PERCENT_EQUALS_PRODUCTION,
                               DOUBLE_AMPERSAND_EQUALS_PRODUCTION,
                               DOUBLE_PIPE_EQUALS_PRODUCTION,
                               DOUBLE_CARET_EQUALS_PRODUCTION,
                               AMPERSAND_EQUALS_PRODUCTION,
                               PIPE_EQUALS_PRODUCTION,
                               CARET_EQUALS_PRODUCTION,
                               DOUBLE_LANGLE_EQUALS_PRODUCTION,
                               DOUBLE_RANGLE_EQUALS_PRODUCTION,
                               TRIPLE_RANGLE_EQUALS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see parser.Rule#match(parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production<ParseType> production, Object[] args) throws ParseException
  {
    if (EQUALS_PRODUCTION.equals(production))
    {
      return new ParseContainer<AssignmentOperatorAST>(AssignmentOperatorAST.EQUALS, (ParseInfo) args[0]);
    }
    if (PLUS_EQUALS_PRODUCTION.equals(production))
    {
      return new ParseContainer<AssignmentOperatorAST>(AssignmentOperatorAST.PLUS_EQUALS, (ParseInfo) args[0]);
    }
    if (MINUS_EQUALS_PRODUCTION.equals(production))
    {
      return new ParseContainer<AssignmentOperatorAST>(AssignmentOperatorAST.MINUS_EQUALS, (ParseInfo) args[0]);
    }
    if (STAR_EQUALS_PRODUCTION.equals(production))
    {
      return new ParseContainer<AssignmentOperatorAST>(AssignmentOperatorAST.TIMES_EQUALS, (ParseInfo) args[0]);
    }
    if (FORWARD_SLASH_EQUALS_PRODUCTION.equals(production))
    {
      return new ParseContainer<AssignmentOperatorAST>(AssignmentOperatorAST.DIVIDE_EQUALS, (ParseInfo) args[0]);
    }
    if (PERCENT_EQUALS_PRODUCTION.equals(production))
    {
      return new ParseContainer<AssignmentOperatorAST>(AssignmentOperatorAST.MODULUS_EQUALS, (ParseInfo) args[0]);
    }
    if (DOUBLE_AMPERSAND_EQUALS_PRODUCTION.equals(production))
    {
      return new ParseContainer<AssignmentOperatorAST>(AssignmentOperatorAST.BOOLEAN_AND_EQUALS, (ParseInfo) args[0]);
    }
    if (DOUBLE_PIPE_EQUALS_PRODUCTION.equals(production))
    {
      return new ParseContainer<AssignmentOperatorAST>(AssignmentOperatorAST.BOOLEAN_OR_EQUALS, (ParseInfo) args[0]);
    }
    if (DOUBLE_CARET_EQUALS_PRODUCTION.equals(production))
    {
      return new ParseContainer<AssignmentOperatorAST>(AssignmentOperatorAST.BOOLEAN_XOR_EQUALS, (ParseInfo) args[0]);
    }
    if (AMPERSAND_EQUALS_PRODUCTION.equals(production))
    {
      return new ParseContainer<AssignmentOperatorAST>(AssignmentOperatorAST.BITWISE_AND_EQUALS, (ParseInfo) args[0]);
    }
    if (PIPE_EQUALS_PRODUCTION.equals(production))
    {
      return new ParseContainer<AssignmentOperatorAST>(AssignmentOperatorAST.BITWISE_OR_EQUALS, (ParseInfo) args[0]);
    }
    if (CARET_EQUALS_PRODUCTION.equals(production))
    {
      return new ParseContainer<AssignmentOperatorAST>(AssignmentOperatorAST.BITWISE_XOR_EQUALS, (ParseInfo) args[0]);
    }
    if (DOUBLE_LANGLE_EQUALS_PRODUCTION.equals(production))
    {
      return new ParseContainer<AssignmentOperatorAST>(AssignmentOperatorAST.LEFT_SHIFT_EQUALS, (ParseInfo) args[0]);
    }
    if (DOUBLE_RANGLE_EQUALS_PRODUCTION.equals(production))
    {
      return new ParseContainer<AssignmentOperatorAST>(AssignmentOperatorAST.ARITHMETIC_RIGHT_SHIFT_EQUALS, (ParseInfo) args[0]);
    }
    if (TRIPLE_RANGLE_EQUALS_PRODUCTION.equals(production))
    {
      return new ParseContainer<AssignmentOperatorAST>(AssignmentOperatorAST.LOGICAL_RIGHT_SHIFT_EQUALS, (ParseInfo) args[0]);
    }
    throw badTypeList();
  }

}
