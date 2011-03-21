package compiler.language.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import parser.lalr.LALRParserGenerator;
import parser.lalr.LALRRuleSet;
import parser.lalr.LALRState;


/*
 * Created on 19 Sep 2010
 */

/**
 * A parser generator that writes its results to an object output file using Java's Serialisation API.
 * The parser can then be loaded back in on demand whenever it is desired by LanguageParseTableLoader.
 * @author Anthony Bryant
 */
public class LanguageParseTableGenerator
{

  public static void main(String[] args)
  {
    if (args.length < 1)
    {
      System.err.println("Usage: java LanguageParseTableGenerator <Output file>");
      System.exit(1);
    }
    File file = new File(args[0]);
    if (file.exists())
    {
      System.err.println("File already exists, deleting: " + file.getAbsolutePath());
      if (!file.delete())
      {
        System.err.println("Could not delete existing parse table file: " + file.getAbsolutePath());
        System.exit(2);
      }
    }

    LALRRuleSet<ParseType> rules = LanguageRules.getRuleSet();
    LALRParserGenerator<ParseType> generator = new LALRParserGenerator<ParseType>(rules);
    generator.generate(ParseType.GENERATED_START_RULE);
    LALRState<ParseType> startState = generator.getStartState();

    try
    {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
      out.writeObject(startState);
      out.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      System.exit(3);
    }

    System.out.println("Successfully wrote parse table to: " + file.getAbsolutePath());
  }

}
