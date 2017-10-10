# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* Quick summary
* Version
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

Implementing Uninformed search - BFS, DFS and informed search - Simulated Annehealing for a tweaked version for the N-Queen problem.


Problem statement : 
You are a zookeeper in the reptile house. One of your rare South Pacific Tufted Wizzo lizards (Tufticus Wizzocus) has just had several babies. Your job is to find a place to put each baby lizard in a nursery.
However, there is a catch, the baby lizards have very long tongues. A baby lizard can shoot out its tongue and eat any other baby lizard before you have time to save it. As such, you want to make sure that no baby lizard can eat another baby lizard in the nursery (burp).
For each baby lizard, you can place them in one spot on a grid. From there, they can shoot out their tongue up, down, left, right and diagonally as well. Their tongues are very long and can reach to the edge of the nursery from any location.
Figure 1 shows in what ways a baby lizard can eat another.
 Figure 1 (A) the baby lizard can attack any other lizard in a red square. Thus it can be seen that a baby lizard can eat another lizard to its top, bottom, left right or diagonal. (B) In this example setup, both lizards can eat each other. Your algorithm will try to avoid this.
In addition to baby lizards, your nursery may have some trees planted in it. Your lizards cannot shoot their tongues through the trees nor can you move a lizard into the same place as a tree. As such, a tree will block any lizard from eating another lizard if it is in the path. Additionally, the tree will block you from moving the lizard to that location.
 
You will write a program that will take an input file that has an arrangement of trees and will output a new arrangement of lizards (and trees; as already mentioned, you can’t move the trees) such that no baby lizard can eat another one. You will be required to create a program that finds the solution. To find the solution you will use the following algorithms:
- Breadth-first search (BFS)
- Depth-first search (DFS)
- Simulated annealing (SA).


Input: The file input.txt in the current directory of your program will be formatted as follows:
First line: Second line: Third line: Next n lines:
instruction of which algorithm to use: BFS, DFS or SA
strictly positive 32-bit integer n, the width and height of the square nursery strictly positive 32-bit integer p, the number of baby lizards
the n x n nursery, one file line per nursery row (to show you where the trees are). It will have a 0 where there is nothing, and a 2 where there is a tree.
So for instance, an input file arranged like figure 2B (but with no lizards yet) and requesting you to use the DFS algorithm to place 8 lizards in the 8x8 nursery would look like:
DFS
8
8 
00000000 
00000000 
00000000 
00002000 
00000000 
00000200 
00000000 
00000000


Output: The file output.txt which your program creates in the current directory should be formatted as follows:
First line: OK or FAIL, indicating whether a solution was found or not. If FAIL, any following lines are ignored.
Next n lines: the n x n nursery, one line in the file per nursery row, including the baby lizards and trees. It will have a 0 where there is nothing, a 1 where you placed a baby lizard, and a 2 where there is a tree.
For example, a correct output.txt for the above sample input.txt (and matching Figure 2B) is:

OK 
00000100 
10000000 
00001000 
01002001 
00000000 
00100200 
00000010 
00010000


Notes and hints:
- Please name your program “homework.xxx” where ‘xxx’ is the extension for the programming language you choose (“py” for python, “cpp” for C++, and “java” for Java). If you are using C++11, then the name of your file should be “homework11.cpp” and if you are using python3 then the name of your file should be “homework3.py”.
- n (width and height of nursery) and p (number of lizards) will be 32-bit integers with values 1 or more, and they may take different values (n1p) or not.
- If your output.txt does not contain exactly p lizards, that test case will fail irrespective of whether your output.txt says “OK” on the first line.
- If your output.txt is such that some lizards can eat each other, that test case will fail irrespective of whether your output.txt says “OK” on the first line.
- We recommend that you first start with the BFS and DFS implementations, and that you first focus on the simpler situation where there are no trees. You may want to consider the following questions:
- Is the problem solvable for p>n when there are no trees?
- Is the problem solvable for p<=n when there are no trees?
- In particular, what happens for low values of n?
- What is a good representation of states and operators?(seenextbulletforfurther hint that may help you with this one).
- How about a simple strategy to get started in which an operator at depth d in the search tree places a lizard in one of the empty cells in column d+1 of the nursery (depth starts at 0 for the root, columns are numbered starting with 1 for the leftmost)? Do you think that would work when there are no trees?
o How would you modify the operators when there are trees, using the definition of the problem given to you above?
- For simulated annealing:
o How about starting with an initial random set of lizard locations (which does not
conflict with any trees)?
o You will need to play with the temperature schedule and see what seems to work.
well on test cases that you create yourself.
- Likely (but no guarantee) we will create 15 DFS, 15 BFS, and 20 SA text cases.
- Your program will be killed after 5 minutes of execution on a given text case, to allow us
to grade the whole class in a reasonable amount of time.
Extra credit:
Among the programs that get 100% correct on all 50 test cases,
- the fastest 10% on the DFS test cases will get an extra 5% credit on this homework
- the fastest 10% on the BFS test cases will get an extra 5% credit on this homework
- the fastest 10% on the SA test cases will get an extra 5% credit on this homework
So, if you are in the top 10% speed on all 3 algorithms (and 100% correct), there is opportunity for up to 15% extra points on this homework (score of 115 for this homework).
Example 1:
For this input.txt:
BFS 2
2 00 00
one possible correct output.txt is:
FAIL
Example 2:
For this input.txt:
DFS
4
4
0000
0000
0000
0000
one possible correct output.txt is:
OK
0010
1000
0001
0100
Example 3:
For this input.txt (same layout of trees as in Figure 2B, but we now want to place 9 lizards in this 8x8 nursery with 2 trees):
SA
8
9
00000000
00000000
00000000
00002000
00000000
00000200
00000000
00000000
one possible correct output.txt is:
OK
00000100
10000000
00001000
01002001
00001000
00100200
00000010
00010000
