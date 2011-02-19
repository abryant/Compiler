package compiler.language.conceptual.topLevel;

import compiler.language.conceptual.QName;

/*
 * Created on 13 Feb 2011
 */

/**
 * Represents an import at the beginning of a source file.
 * @author Anthony Bryant
 */
public class Import
{
  private QName importedQName;
  private boolean addChildren;

  /**
   * Creates a new Import with the specified imported QName.
   * @param importedQName - the QName that this Import represents
   * @param addChildren - true for only the children of the QName to be imported, false for the last name in the QName to be imported
   */
  public Import(QName importedQName, boolean addChildren)
  {
    this.importedQName = importedQName;
    this.addChildren = addChildren;
  }

  /**
   * @return the importedQName
   */
  public QName getImportedQName()
  {
    return importedQName;
  }

  /**
   * @return true if only the children of the QName should be imported, false if the last name in the QName should be imported
   */
  public boolean isAddChildren()
  {
    return addChildren;
  }


}
