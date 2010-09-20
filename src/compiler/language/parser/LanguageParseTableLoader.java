package compiler.language.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import compiler.parser.lalr.LALRParserGenerator;
import compiler.parser.lalr.LALRRuleSet;
import compiler.parser.lalr.LALRState;

/*
 * Created on 19 Sep 2010
 */

/**
 * @author Anthony Bryant
 */
public class LanguageParseTableLoader
{

  private static final String PARSE_TABLE_LOCATION_PROPERTY = "ParseTableLocation";
  private static final String DEFAULT_PARSE_TABLE_LOCATION = "ParseTable.object";

  /**
   * Loads the parse table from disk, or if that fails (either because the file does not exist, or because of some deserialization problem)
   * @return the start state of the parse table
   */
  public static LALRState loadParseTable()
  {
    String filePath = System.getProperty(PARSE_TABLE_LOCATION_PROPERTY, DEFAULT_PARSE_TABLE_LOCATION);
    File file = new File(filePath);
    if (!file.isFile())
    {
      System.err.println("Parse table file does not exist. It needs to be generated using: compiler.language.parser.LanguageParseTableGenerator");
      return generateParseTable();
    }

    FileInputStream fileIn = null;
    try
    {
      fileIn = new FileInputStream(file);
      ObjectInputStream objectIn = new ObjectInputStream(fileIn);
      Object startState = objectIn.readObject();
      return (LALRState) startState;
    }
    catch (Exception e)
    {
      System.err.println("Error reading parse table, falling back to generating it.");
      e.printStackTrace();
      return generateParseTable();
    }
    finally
    {
      if (fileIn != null)
      {
        try
        {
          fileIn.close();
        }
        catch (IOException e)
        {
          System.err.println("Error closing file: " + file.getAbsolutePath());
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Generates the parse table and returns it.
   * @return the start state of the generated parse table
   */
  public static LALRState generateParseTable()
  {
    System.err.println("Generating parse table, this could take some time...");
    LALRRuleSet rules = LanguageRules.getRuleSet();

    LALRParserGenerator generator = new LALRParserGenerator(rules);
    generator.generate();
    return generator.getStartState();
  }

  private LanguageParseTableLoader()
  {
    throw new UnsupportedOperationException();
  }
}
