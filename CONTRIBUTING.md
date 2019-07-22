# Contributing

If you wish to contribute to the development of TABBA, we ask that you adhere to the following guidelines.

## Code Style

### Naming Conventions

We use lowerCamelCase for naming methods and fields, and PascalCase (UpperCamelCase) for naming classes and constructors. Below you can find an example class:

    public class Foo {

        private String bar;
	
        public Foo(String bar) {
            this.bar = bar;
        }
        
        public String getBar() {
            return bar;
        }
    }
  
### Line Length

Whilst we try to follow the convention of keeping lines shorter than 81 characters, we understand that it is infuriating to have to change a line of code because it's 82 characters long. Just try to keep your lines no longer than ~90 characters and if they can be condensed or split across multiple lines while retaining beauty then please do so! Always value clarity and readability over meeting the line length limit.

### Curly Braces

Curly braces should not have their own line; they really aren't special enough to deserve that kind of attention! Remember: we're programming in Java, not C#. **NEVER** omit braces. 

    // Good!
    if(condition) {
        good();
    } else {
        bad();
    }

    // Bad!
    if(condition)
    {
        good();
    }
    else
    {
        bad();
    }

### Package Names

Package names should be singular nouns.

    // Good!
    package dev.tinkererr.tabba.builder;
    
    // Bad!
    package dev.tinkererr.tabba.builders;
    
### The `this` Keyword

The `this` keyword should always be used. This ensures that the scope of a variable is never ambiguous.

### Javadocs

Where possible you should add Javadocs to all methods and classes. There are a few exceptions however:
* The constructor of a class that only calls `super()`, such as the constructor of a custom exception class. In this scenario you should just provide documentation on the class itself detailing the purpose of the exception.
* Getters and Setters. If a getter or setter implements no additional logic other than simply returning a value if a getter or assigning a variable if a setter then documentation is not required. If a getter or setter implements additional logic (such as validation) then documentation should be present.  

### And More...

We follow Google's [Java Style Guide](https://google.github.io/styleguide/javaguide.html)! Any conventions defined above override Google's declared conventions.

## Code of Conduct

When contributing to TABBA, we ask that you adhere to our code of conduct which can be found [here](CODE_OF_CONDUCT.md). Anyone found to be breaching this code will be investigated by the TABBA team and intervened with if necessary.
