package y;

class Foo
{
  class Bar extends Foo
  {
    class X extends Foo.Bar.Bar.Bar.Bar
    {
    }
    int foo;
  }
}
