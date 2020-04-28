# CS113-FGP-Graph
## Final Group Project for CS113 - Graph

Complete the `Graph` class, either as an adjacency list or adjacency matrix, per the ADT proposal in the lecture slides along with an advanced algorithm (Dijkstra's, Prim's, DFS, BFS, or some other one we haven't covered [clear with instructor first]).  Use the data structure in a core/useful way for your groups idea. Ideally (but not required), you should include a GUI frontend to showcase your project.

**Use Scrum (Agile) development to build your group project over 3 weeks:**
- Sprint 0 = planning sprint
	- End of sprint should have: project proposal/description, decide graph algorithm, initial UML + sequence diagram
- Sprint 1 = first half of development
- Sprint 2 = second half of development
	- End of sprint should have: project completed+documented, screenshots, updated UML+sequence diagrams
- *See past group projects for general sprint requirements (i.e., unit tests for each sprint, updated documentation, etc.)*

**You will be graded on the following:**
- Complete/updated UML Class and relationship diagram
- Complete/updated Sequence diagram for driver (main part of program that creates objects, does user input, etc.)
- JUnit tests for all model classes + data structure (Graph)
	- Note: GUI classes need not have JUnit tests
- All code documented (author boxes, algorithm for driver, methods have description/precondition/postcondition, class invariants)
- No crashes/compile issues
**- Every group member writes significant amount of code**, ideally proportional to other members.


> ***NOTE:***
> - You'll be tempted to push the project off for the end, or give certain group members monolithic parts (someone build the Graph, someone build the GUI, etc.).  **DON'T!**  Embrace the agile development process! Plan in Sprint 0, build a working product by the end of Sprint 1 and Sprint 2.
> - Trouble splitting up the UML/sequence diagram? Build them together! That way everyone's on the same page! 
> - Plan plan plan plan! When you're done planning, PLAN SOME MORE!
> - Create and use Trello boards, Slack, etc. to help you all communicate and organize yourselves

## Project proposal/description:
- A game in which the goal is to reach a specific point on the map before being hit by an enemy computer.
  The user controls the player using the keyboard arrow keys.
  The game will use Graphs to move the enemies closer to the player. 
  The game is won if the player succeeds in reaching the specified goal
  The game is lost if the player collides with an enemy.

## Graph algorithm used:
[Adjacency Matrix / Dijkstra's algorithm]
- We used Dijkstra's algorithm so the Enemy objects could track and and try and reach the player.
  Dijkstra's algorithm is a greedy algorithm that goes towards the closest path before even considering another one.
  After testing other paths available it moves towards its objective using the shortest path found.

## Screenshot(s):
![Start menu](resources/StartMenu.PNG)
![How to play](resources/HowToPlay.PNG)
![Gameplay](resources/Gameplay.PNG)
![Reaching the goal](resources/ReachingTheGoal.PNG)
![Losing](resources/Losing.PNG)




## UML Class+Relationship Diagram:
![UML Class](resources/GraphGameUML.png)

## Sequence Diagram (for driver):
![Driver sequence diagram](resources/DriverSequenceDiagram.PNG)
