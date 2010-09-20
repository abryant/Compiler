package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ARRAY_INSTANCIATION_EXPRESSION_NO_INITIALIZER;
import static compiler.language.parser.ParseType.DIMENSIONS;
import static compiler.language.parser.ParseType.DIMENSION_EXPRESSIONS;
import static compiler.language.parser.ParseType.NEW_KEYWORD;
import static compiler.language.parser.ParseType.TYPE_NOT_ARRAY_TYPE;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.ArrayInstanciationExpression;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.misc.Dimensions;
import compiler.language.ast.type.Type;
import compiler.parser.ParseException;
import compiler.parser.Production;
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArrayInstanciationExpressionNoInitializerRule extends Rule
{
  private static final long serialVersionUID = 1L;

  private static final Production PRODUCTION = new Production(NEW_KEYWORD, TYPE_NOT_ARRAY_TYPE, DIMENSION_EXPRESSIONS);
  private static final Production DIMENSIONS_PRODUCTION = new Production(NEW_KEYWORD, TYPE_NOT_ARRAY_TYPE, DIMENSION_EXPRESSIONS, DIMENSIONS);

  public ArrayInstanciationExpressionNoInitializerRule()
  {
    super(ARRAY_INSTANCIATION_EXPRESSION_NO_INITIALIZER, PRODUCTION, DIMENSIONS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(compiler.parser.Production, java.lang.Object[])
   */
  @Override
  public Object match(Production production, Object[] args) throws ParseException
  {
    if (PRODUCTION.equals(production))
    {
      Type type = (Type) args[1];
      @SuppressWarnings("unchecked")
      ParseList<Expression> dimensionExpressions = (ParseList<Expression>) args[2];
      return new ArrayInstanciationExpression(type, dimensionExpressions.toArray(new Expression[0]), dimensionExpressions.size(), null,
                                              ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), dimensionExpressions.getParseInfo()));
    }
    if (DIMENSIONS_PRODUCTION.equals(production))
    {
      Type type = (Type) args[1];
      @SuppressWarnings("unchecked")
      ParseList<Expression> dimensionExpressions = (ParseList<Expression>) args[2];
      Dimensions dimensions = (Dimensions) args[3];
      int totalDimensions = dimensionExpressions.size() + dimensions.getDimensions();
      return new ArrayInstanciationExpression(type, dimensionExpressions.toArray(new Expression[0]), totalDimensions, null,
                                              ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), dimensionExpressions.getParseInfo(), dimensions.getParseInfo()));
    }
    throw badTypeList();
  }

}
