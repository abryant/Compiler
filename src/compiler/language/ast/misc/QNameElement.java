package compiler.language.ast.misc;

import compiler.language.ast.ParseInfo;
import compiler.language.ast.ParseList;
import compiler.language.ast.expression.Expression;
import compiler.language.ast.expression.FieldAccessExpression;
import compiler.language.ast.expression.ParenthesisedExpression;
import compiler.language.ast.expression.TupleExpression;
import compiler.language.ast.terminal.Name;
import compiler.language.ast.type.PointerType;
import compiler.language.ast.type.TupleType;
import compiler.language.ast.type.Type;
import compiler.language.ast.type.TypeParameter;

/*
 * Created on 17 Aug 2010
 */

/**
 * A class which holds either a QName or a nested list of QNameElements.
 * This should only be used during parsing, and should not be used in the final
 * AST.
 * @author Anthony Bryant
 */
public class QNameElement
{

  private QName qname = null;
  private ParseList<QNameElement> elements = null;
  private ParseInfo listParseInfo = null;

  /**
   * Creates a new QNameElement to store the specified QName
   * @param qname - the QName to store
   */
  public QNameElement(QName qname)
  {
    this.qname = qname;
  }

  /**
   * Creates a new QNameElement to store the specified list of QNameElements
   * @param elements - the list of QNameElements to store
   * @param listParseInfo - the parsing information for the list this represents, including the surrounding brackets
   */
  public QNameElement(ParseList<QNameElement> elements, ParseInfo listParseInfo)
  {
    this.listParseInfo = listParseInfo;
    this.elements = elements;
  }

  /**
   * @return the parsing information for this QNameElement
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
   * Converts this QNameElement into either a FieldAccessExpression or a ParenthesisedExpression containing a TupleExpression
   * @return the resulting Expression
   */
  public Expression toExpression()
  {
    if (qname != null)
    {
      return new FieldAccessExpression(qname, qname.getParseInfo());
    }

    QNameElement[] elems = elements.toArray(new QNameElement[0]);
    Expression[] expressions = new Expression[elems.length];
    for (int i = 0; i < elems.length; i++)
    {
      expressions[i] = elems[i].toExpression();
    }
    TupleExpression expression = new TupleExpression(expressions, elements.getParseInfo());

    return new ParenthesisedExpression(expression, listParseInfo);
  }

  /**
   * Converts this QNameElement into either a PointerType or a TupleType.
   * @return the resulting Type
   */
  public Type toType()
  {
    if (qname != null)
    {
      Name[] names = qname.getNames();
      TypeParameter[][] typeParameterLists = new TypeParameter[names.length][];
      for (int i = 0; i < typeParameterLists.length; i++)
      {
        typeParameterLists[i] = new TypeParameter[0];
      }
      return new PointerType(false, qname.getNames(), typeParameterLists, qname.getParseInfo());
    }

    QNameElement[] elems = elements.toArray(new QNameElement[0]);
    Type[] types = new Type[elems.length];
    for (int i = 0; i < elems.length; i++)
    {
      types[i] = elems[i].toType();
    }
    return new TupleType(types, listParseInfo);
  }

}
