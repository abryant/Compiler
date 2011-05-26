package compiler.language.parser;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.expression.ExpressionAST;
import compiler.language.ast.expression.FieldAccessExpressionAST;
import compiler.language.ast.expression.ParenthesisedExpressionAST;
import compiler.language.ast.expression.TupleExpressionAST;
import compiler.language.ast.misc.QNameAST;
import compiler.language.ast.terminal.NameAST;
import compiler.language.ast.type.PointerTypeAST;
import compiler.language.ast.type.TupleTypeAST;
import compiler.language.ast.type.TypeAST;
import compiler.language.ast.type.TypeParameterAST;

/*
 * Created on 17 Aug 2010
 */

/**
 * A class which holds either a QNameAST or a nested list of QNameElementASTs.
 * This should only be used during parsing, and should never occur in the final
 * AST.
 * @author Anthony Bryant
 */
public class QNameElementAST
{

  private QNameAST qname = null;
  private ParseList<QNameElementAST> elements = null;
  private ParseInfo listParseInfo = null;

  /**
   * Creates a new QNameElementAST to store the specified QNameAST
   * @param qname - the QNameAST to store
   */
  public QNameElementAST(QNameAST qname)
  {
    this.qname = qname;
  }

  /**
   * Creates a new QNameElementAST to store the specified list of QNameElements
   * @param elements - the list of QNameElements to store
   * @param listParseInfo - the parsing information for the list this represents, including the surrounding brackets
   */
  public QNameElementAST(ParseList<QNameElementAST> elements, ParseInfo listParseInfo)
  {
    this.listParseInfo = listParseInfo;
    this.elements = elements;
  }

  /**
   * @return the parsing information for this QNameElementAST
   */
  public ParseInfo getParseInfo()
  {
    if (qname != null)
    {
      return qname.getParseInfo();
    }
    return listParseInfo;
  }

  /**
   * Converts this QNameElementAST into either a FieldAccessExpressionAST or a ParenthesisedExpressionAST containing a TupleExpressionAST
   * @return the resulting ExpressionAST
   */
  public ExpressionAST toExpression()
  {
    if (qname != null)
    {
      return new FieldAccessExpressionAST(qname, qname.getParseInfo());
    }

    QNameElementAST[] elems = elements.toArray(new QNameElementAST[0]);
    ExpressionAST[] expressions = new ExpressionAST[elems.length];
    for (int i = 0; i < elems.length; i++)
    {
      expressions[i] = elems[i].toExpression();
    }
    TupleExpressionAST expression = new TupleExpressionAST(expressions, elements.getParseInfo());

    return new ParenthesisedExpressionAST(expression, listParseInfo);
  }

  /**
   * Converts this QNameElementAST into either a PointerTypeAST or a TupleTypeAST.
   * @return the resulting TypeAST
   */
  public TypeAST toType()
  {
    if (qname != null)
    {
      NameAST[] names = qname.getNames();
      TypeParameterAST[][] typeParameterLists = new TypeParameterAST[names.length][];
      return new PointerTypeAST(false, qname.getNames(), typeParameterLists, qname.getParseInfo());
    }

    QNameElementAST[] elems = elements.toArray(new QNameElementAST[0]);
    TypeAST[] types = new TypeAST[elems.length];
    for (int i = 0; i < elems.length; i++)
    {
      types[i] = elems[i].toType();
    }
    return new TupleTypeAST(types, listParseInfo);
  }

}
