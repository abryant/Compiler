package compiler.language.parser.rules.statement;

import static compiler.language.parser.ParseType.ASSIGNMENT;
import static compiler.language.parser.ParseType.BLOCK;
import static compiler.language.parser.ParseType.BREAK_STATEMENT;
import static compiler.language.parser.ParseType.CONSTRUCTOR_INVOCATION_STATEMENT;
import static compiler.language.parser.ParseType.CONTINUE_STATEMENT;
import static compiler.language.parser.ParseType.DECREMENT;
import static compiler.language.parser.ParseType.DO_STATEMENT;
import static compiler.language.parser.ParseType.EMPTY_STATEMENT;
import static compiler.language.parser.ParseType.FALLTHROUGH_STATEMENT;
import static compiler.language.parser.ParseType.FOR_EACH_STATEMENT;
import static compiler.language.parser.ParseType.FOR_STATEMENT;
import static compiler.language.parser.ParseType.IF_STATEMENT;
import static compiler.language.parser.ParseType.INCREMENT;
import static compiler.language.parser.ParseType.LOCAL_DECLARATION;
import static compiler.language.parser.ParseType.RETURN_STATEMENT;
import static compiler.language.parser.ParseType.SEMICOLON;
import static compiler.language.parser.ParseType.STATEMENT;
import static compiler.language.parser.ParseType.STATEMENT_EXPRESSION;
import static compiler.language.parser.ParseType.SWITCH_STATEMENT;
import static compiler.language.parser.ParseType.SYNCHRONIZED_STATEMENT;
import static compiler.language.parser.ParseType.THROW_STATEMENT;
import static compiler.language.parser.ParseType.TRY_STATEMENT;
import static compiler.language.parser.ParseType.WHILE_STATEMENT;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.StatementExpression;
import compiler.language.ast.statement.ExpressionStatement;
import compiler.language.ast.statement.Statement;
import compiler.parser.ParseException;
import compiler.parser.Rule;

/*
 * Created on 14 Jul 2010
 */

/**
 * @author Anthony Bryant
 */
public class StatementRule extends Rule
{

  private static final Object[] BLOCK_PRODUCTION = new Object[] {BLOCK};
  private static final Object[] EMPTY_PRODUCTION = new Object[] {EMPTY_STATEMENT};
  private static final Object[] CONSTRUCTOR_INVOCATION_PRODUCTION = new Object[] {CONSTRUCTOR_INVOCATION_STATEMENT};
  private static final Object[] LOCAL_DECLARATION_PRODUCTION = new Object[] {LOCAL_DECLARATION, SEMICOLON};
  private static final Object[] ASSIGNMENT_PRODUCTION = new Object[] {ASSIGNMENT, SEMICOLON};
  private static final Object[] IF_PRODUCTION = new Object[] {IF_STATEMENT};
  private static final Object[] WHILE_PRODUCTION = new Object[] {WHILE_STATEMENT};
  private static final Object[] DO_PRODUCTION = new Object[] {DO_STATEMENT};
  private static final Object[] FOR_PRODUCTION = new Object[] {FOR_STATEMENT};
  private static final Object[] FOR_EACH_PRODUCTION = new Object[] {FOR_EACH_STATEMENT};
  private static final Object[] SWITCH_PRODUCTION = new Object[] {SWITCH_STATEMENT};
  private static final Object[] BREAK_PRODUCTION = new Object[] {BREAK_STATEMENT};
  private static final Object[] CONTINUE_PRODUCTION = new Object[] {CONTINUE_STATEMENT};
  private static final Object[] FALLTHROUGH_PRODUCTION = new Object[] {FALLTHROUGH_STATEMENT};
  private static final Object[] RETURN_PRODUCTION = new Object[] {RETURN_STATEMENT};
  private static final Object[] SYNCHRONIZED_PRODUCTION = new Object[] {SYNCHRONIZED_STATEMENT};
  private static final Object[] THROW_PRODUCTION = new Object[] {THROW_STATEMENT};
  private static final Object[] TRY_PRODUCTION = new Object[] {TRY_STATEMENT};
  private static final Object[] INCREMENT_PRODUCTION = new Object[] {INCREMENT, SEMICOLON};
  private static final Object[] DECREMENT_PRODUCTION = new Object[] {DECREMENT, SEMICOLON};
  private static final Object[] STATEMENT_EXPRESSION_PRODUCTION = new Object[] {STATEMENT_EXPRESSION, SEMICOLON};

  public StatementRule()
  {
    super(STATEMENT, BLOCK_PRODUCTION,
                     EMPTY_PRODUCTION,
                     CONSTRUCTOR_INVOCATION_PRODUCTION,
                     LOCAL_DECLARATION_PRODUCTION,
                     ASSIGNMENT_PRODUCTION,
                     IF_PRODUCTION,
                     WHILE_PRODUCTION,
                     DO_PRODUCTION,
                     FOR_PRODUCTION,
                     FOR_EACH_PRODUCTION,
                     SWITCH_PRODUCTION,
                     BREAK_PRODUCTION,
                     CONTINUE_PRODUCTION,
                     FALLTHROUGH_PRODUCTION,
                     RETURN_PRODUCTION,
                     SYNCHRONIZED_PRODUCTION,
                     THROW_PRODUCTION,
                     TRY_PRODUCTION,
                     INCREMENT_PRODUCTION,
                     DECREMENT_PRODUCTION,
                     STATEMENT_EXPRESSION_PRODUCTION);
  }

  /**
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == BLOCK_PRODUCTION  || types == EMPTY_PRODUCTION        || types == CONSTRUCTOR_INVOCATION_PRODUCTION ||
        types == IF_PRODUCTION     || types == WHILE_PRODUCTION        || types == DO_PRODUCTION ||
        types == FOR_PRODUCTION    || types == FOR_EACH_PRODUCTION     || types == SWITCH_PRODUCTION ||
        types == BREAK_PRODUCTION  || types == CONTINUE_PRODUCTION     || types == FALLTHROUGH_PRODUCTION ||
        types == RETURN_PRODUCTION || types == SYNCHRONIZED_PRODUCTION || types == THROW_PRODUCTION ||
        types == TRY_PRODUCTION)
    {
      // these productions only take one argument and all return a Statement, so return the argument
      return args[0];
    }

    if (types == LOCAL_DECLARATION_PRODUCTION || types == ASSIGNMENT_PRODUCTION ||
        types == INCREMENT_PRODUCTION         || types == DECREMENT_PRODUCTION)
    {
      // add the semicolon's ParseInfo to the statement before returning it
      Statement statement = (Statement) args[0];
      statement.setParseInfo(ParseInfo.combine(statement.getParseInfo(), (ParseInfo) args[1]));
      return statement;
    }

    if (types == STATEMENT_EXPRESSION_PRODUCTION)
    {
      StatementExpression expression = (StatementExpression) args[0];
      return new ExpressionStatement(expression, ParseInfo.combine(expression.getParseInfo(), (ParseInfo) args[1]));
    }

    throw badTypeList();
  }

}
