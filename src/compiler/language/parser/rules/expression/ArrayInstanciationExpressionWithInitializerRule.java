package compiler.language.parser.rules.expression;

import static compiler.language.parser.ParseType.ARRAY_INITIALIZER;
import static compiler.language.parser.ParseType.ARRAY_INSTANCIATION_EXPRESSION_WITH_INITIALIZER;
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
import compiler.parser.Rule;

/*
 * Created on 12 Aug 2010
 */

/**
 * @author Anthony Bryant
 */
public class ArrayInstanciationExpressionWithInitializerRule extends Rule
{

  private static final Object[] EXPRESSIONS_DIMENSIONS_PRODUCTION = new Object[] {NEW_KEYWORD, TYPE_NOT_ARRAY_TYPE, DIMENSION_EXPRESSIONS, DIMENSIONS, ARRAY_INITIALIZER};
  private static final Object[] EXPRESSIONS_PRODUCTION = new Object[] {NEW_KEYWORD, TYPE_NOT_ARRAY_TYPE, DIMENSION_EXPRESSIONS, ARRAY_INITIALIZER};
  private static final Object[] DIMENSIONS_PRODUCTION = new Object[] {NEW_KEYWORD, TYPE_NOT_ARRAY_TYPE, DIMENSIONS, ARRAY_INITIALIZER};

  public ArrayInstanciationExpressionWithInitializerRule()
  {
    super(ARRAY_INSTANCIATION_EXPRESSION_WITH_INITIALIZER, EXPRESSIONS_DIMENSIONS_PRODUCTION, EXPRESSIONS_PRODUCTION, DIMENSIONS_PRODUCTION);
  }

  /**
   * {@inheritDoc}
   * @see compiler.parser.Rule#match(java.lang.Object[], java.lang.Object[])
   */
  @Override
  public Object match(Object[] types, Object[] args) throws ParseException
  {
    if (types == EXPRESSIONS_DIMENSIONS_PRODUCTION)
    {
      Type type = (Type) args[1];
      @SuppressWarnings("unchecked")
      ParseList<Expression> dimensionExpressions = (ParseList<Expression>) args[2];
      Dimensions dimensions = (Dimensions) args[3];
      @SuppressWarnings("unchecked")
      ParseList<Expression> arrayInitializer = (ParseList<Expression>) args[4];
      int totalDimensions = dimensionExpressions.size() + dimensions.getDimensions();
      return new ArrayInstanciationExpression(type, dimensionExpressions.toArray(new Expression[0]), totalDimensions, arrayInitializer.toArray(new Expression[0]),
                                              ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), dimensionExpressions.getParseInfo(),
                                                                dimensions.getParseInfo(), arrayInitializer.getParseInfo()));
    }
    if (types == EXPRESSIONS_PRODUCTION)
    {
      Type type = (Type) args[1];
      @SuppressWarnings("unchecked")
      ParseList<Expression> dimensionExpressions = (ParseList<Expression>) args[2];
      @SuppressWarnings("unchecked")
      ParseList<Expression> arrayInitializer = (ParseList<Expression>) args[3];
      return new ArrayInstanciationExpression(type, dimensionExpressions.toArray(new Expression[0]), dimensionExpressions.size(), arrayInitializer.toArray(new Expression[0]),
                                              ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(),
                                                                dimensionExpressions.getParseInfo(), arrayInitializer.getParseInfo()));
    }
    if (types == DIMENSIONS_PRODUCTION)
    {
      Type type = (Type) args[1];
      Dimensions dimensions = (Dimensions) args[2];
      @SuppressWarnings("unchecked")
      ParseList<Expression> arrayInitializer = (ParseList<Expression>) args[3];
      return new ArrayInstanciationExpression(type, new Expression[0], dimensions.getDimensions(), arrayInitializer.toArray(new Expression[0]),
                                              ParseInfo.combine((ParseInfo) args[0], type.getParseInfo(), dimensions.getParseInfo(),
                                                                arrayInitializer.getParseInfo()));
    }
    throw badTypeList();
  }

}