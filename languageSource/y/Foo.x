package y;

class Foo<A extends x.String super Foo<A>.Bar>
{
  class Bar extends Foo<Foo<A>.Bar>
  {
    static class X extends Foo<X>.Bar
    {
    }
    int foo;
  }
}
