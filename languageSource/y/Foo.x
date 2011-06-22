package y;

class Foo<A extends x.String super Foo<A>.Bar> // extends Foo<A>.Bar
{
  class Bar extends Foo<Foo<A>.Bar>
  {
    class X extends Foo<Foo<x.String>.Bar.X>.Bar
    {
    }
    int foo;
  }
}

class Super
{
  class A
  {
  }
}

enum SubEnum extends Super
{
  FOO, BAR;

  class B extends Super.A
  {
  }
}
