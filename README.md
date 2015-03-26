Technical Interview: RC Challenge
=============================
**Purpose**

The purpose of this exercise is to measure the candidate’s ability to satisfy business rules, while also demonstrating
their ability to further enhance the solution. The solution should not only satisfy the business requirements outlined
below, but should also give the candidate an opportunity to show-off their skills.

We should be able to pull down the source, and type ``./gradlew build`` at the command line and the project should
compile. We will reject submissions that do not follow this rule. Once you think you have finished, it is
suggested that you clone the repository into a different directory and try to build it to ensure it works properly.
If your app needs any additional libraries, make sure they are included in the ``build.gradle`` file.  Any libraries
that you wish to add are perfectly acceptable.

**Pre-Interview Requirements**

1. You are given the initial starting point (x, y) of the car and the direction (N, S, E, W) it is facing.
1. The car receives a string of commands.
1. Implement commands that move the car forward/backward (f, b).
1. Implement commands that turn the car left/right (l, r).
1. Implement wrapping from one edge of the grid to another
1. Implement obstacle detection before each move to a new square.  If a given sequence of commands encounters an
obstacle, the care moves up to the last possible point and reports the obstacle.
1. Add any additional commands that you would want in an RC Car.

**A Pull Request must be made to prior to the due date**

**Tip**

It is NOT expected that this will be a production ready application. With that being said it is expected that this
application will be cleanly designed and maintainable. This should be something that will be maintained by you or
someone else for several years.

**You Will Need**

1. A GitHub account
1. Implement the features above in a repository forked from https://github.com/Praeses/rc-challenge