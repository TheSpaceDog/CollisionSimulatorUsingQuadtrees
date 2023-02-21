# CollisionSimulatorUsingQuadtrees

Collision detection using naive methods often run in n^2 time. Using a quadtree to partition space into equally sized quadrants, querying collisions of
individual points takes nlog4n time instead. This java implementation takes inspiration from the Coding Train's Javascript Demonstration: https://www.youtube.com/watch?v=OJxEcs0w_kE
