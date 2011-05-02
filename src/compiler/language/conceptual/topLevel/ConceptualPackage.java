package compiler.language.conceptual.topLevel;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import compiler.language.ast.ParseInfo;
import compiler.language.conceptual.NameConflictException;
import compiler.language.conceptual.QName;
import compiler.language.conceptual.Resolvable;
import compiler.language.conceptual.ScopeType;
import compiler.language.conceptual.UnresolvableException;
import compiler.language.conceptual.typeDefinition.ConceptualClass;
import compiler.language.conceptual.typeDefinition.ConceptualEnum;
import compiler.language.conceptual.typeDefinition.ConceptualInterface;
import compiler.language.translator.conceptual.ConceptualTranslator;

/*
 * Created on 13 Feb 2011
 */

/**
 * Represents a package in the conceptual hierarchy.
 * Contains mappings from the names of type definitions it contains to the conceptual files they are defined in.
 * @author Anthony Bryant
 */
public final class ConceptualPackage extends Resolvable
{
  private static final String FILE_EXTENSION = ".x"; // TODO: move this constant to a more sensible place

  private ConceptualTranslator translator;

  // the root of the package hierarchy, or null if we are the root package
  private ConceptualPackage rootPackage;
  private ConceptualPackage parentPackage;

  private QName name;

  // this stores the directories that this package can represent
  // if there are source files in this package, then only one directory is allowed in this list (unless it is the root package)
  // otherwise, multiple directories can be stored, which will be based on the original classpath
  private List<File> directories;

  // the sub-packages of this package - this is populated by addSubPackage()
  private Map<String, ConceptualPackage> subPackages = new HashMap<String, ConceptualPackage>();

  // stores a mapping from type definition name to the conceptual file it is declared in
  private Map<String, ConceptualFile> typeFiles = new HashMap<String, ConceptualFile>();

  /**
   * Creates a new ConceptualPackage to act as a root package.
   * @param translator - the ConceptualTranslator to use to create any ConceptualFile in this package and its sub-packages
   * @param directories - the directories that this root package can represent, this should be initialised to the classpath
   */
  public ConceptualPackage(ConceptualTranslator translator, List<File> directories)
  {
    this.translator = translator;
    name = new QName();
    this.directories = directories;
  }

  /**
   * Creates a new ConceptualPackage that will take files from the specified directory.
   * @param translator - the ConceptualTranslator to use to create any ConceptualFile in this package and its sub-packages
   * @param rootPackage - the root package
   * @param parentPackage - the package directly above this one in the hierarchy
   * @param packageName - the name of this package inside its parent
   * @param directories - the directories that this package can represent
   */
  public ConceptualPackage(ConceptualTranslator translator, ConceptualPackage rootPackage, ConceptualPackage parentPackage, String packageName, List<File> directories)
  {
    this.translator = translator;
    this.rootPackage = rootPackage;
    this.parentPackage = parentPackage;
    if (parentPackage != null && packageName != null)
    {
      this.name = new QName(parentPackage.getName(), packageName);
    }
    this.directories = directories;
  }

  /**
   * Loads all of the source files from this package's only directory.
   * @throws IllegalStateException - if this package has multiple directories
   */
  public void loadFiles()
  {
    if (directories.size() != 1)
    {
      throw new IllegalStateException("Cannot load source files for a package unless it has only one directory");
    }
    File directory = directories.get(0);

    // populate the unparsed files map
    if (!directory.isDirectory())
    {
      throw new IllegalStateException("Cannot create a ConceptualPackage without a valid directory to read from.");
    }
    for (File file : directory.listFiles())
    {
      if (!file.isFile())
      {
        continue;
      }
      String name =  file.getName();
      if (!name.endsWith(FILE_EXTENSION))
      {
        continue;
      }
      ConceptualFile conceptualFile = translator.parseFile(file);
      if (conceptualFile == null)
      {
        continue;
      }
      for (String typeName : conceptualFile.getDeclaredTypeNames())
      {
        typeFiles.put(typeName, conceptualFile);
      }
    }
  }

  /**
   * @return the name of this ConceptualPackage
   */
  public QName getName()
  {
    return name;
  }

  /**
   * @return the directories of this ConceptualPackage
   */
  public List<File> getDirectories()
  {
    return directories;
  }

  /**
   * Finds the sub-package with the specified name.
   * If the package does not already exist, this method will search through it's associated folder(s) for matching subdirectory names and add the package is possible.
   * @param name - the name of the sub-package
   * @return the ConceptualPackage added, or null if no directory for a package with that name could be found
   * @throws NameConflictException - if there is a conflict between a package and a source file, or
   *                                 if two package directories are found with the specified name which both contain source files
   */
  public ConceptualPackage getSubPackage(String name) throws NameConflictException
  {
    ConceptualPackage existing = subPackages.get(name);
    if (existing != null)
    {
      return existing;
    }

    List<File> newPackageDirs = new LinkedList<File>();
    for (File directory : directories)
    {
      for (File file : directory.listFiles())
      {
        if (!file.isDirectory())
        {
          continue;
        }
        String dirName = file.getName();
        if (typeFiles.containsKey(dirName))
        {
          // TODO: fill in this ParseInfo object with references to both the package directory and the type definition
          throw new NameConflictException("Package name \"" + name + "\" conflicts with a type definition.", (ParseInfo[]) null);
        }
        if (name.equals(dirName))
        {
          newPackageDirs.add(file);
        }
      }
    }
    if (newPackageDirs.isEmpty())
    {
      return null;
    }

    File sourceFilesDir = null;
    for (File dir : newPackageDirs)
    {
      boolean hasSourceFiles = false;
      for (File file : dir.listFiles())
      {
        if (file.isFile() && file.getName().endsWith(FILE_EXTENSION))
        {
          hasSourceFiles = true;
          break;
        }
      }
      if (hasSourceFiles)
      {
        if (sourceFilesDir != null)
        {
          // TODO: add the ParseInfo of the two conflicting package directories
          throw new NameConflictException("Unable to add sub-package " + this.name + "." + name + " as multiple package directories exist.");
        }
        sourceFilesDir = dir;
      }
    }
    if (sourceFilesDir != null)
    {
      // remove all but the directory containing the source files
      newPackageDirs.clear();
      newPackageDirs.add(sourceFilesDir);
    }

    ConceptualPackage conceptualPackage = new ConceptualPackage(translator, rootPackage, this, name, newPackageDirs);
    subPackages.put(name, conceptualPackage);
    if (newPackageDirs.size() == 1)
    {
      conceptualPackage.loadFiles();
    }
    return conceptualPackage;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Resolvable resolve(String name) throws NameConflictException, UnresolvableException
  {
    ConceptualPackage subPackage = getSubPackage(name);
    if (subPackage != null)
    {
      return subPackage;
    }
    ConceptualFile file = typeFiles.get(name);
    if (file == null)
    {
      return null;
    }
    ConceptualClass conceptualClass = file.getClass(name);
    if (conceptualClass != null)
    {
      return conceptualClass;
    }
    ConceptualInterface conceptualInterface = file.getInterface(name);
    if (conceptualInterface != null)
    {
      return conceptualInterface;
    }
    ConceptualEnum conceptualEnum = file.getEnum(name);
    if (conceptualEnum != null)
    {
      return conceptualEnum;
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ScopeType getType()
  {
    return ScopeType.PACKAGE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Resolvable getParent()
  {
    return parentPackage;
  }

}
