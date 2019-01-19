# Pong
Andorid game in which you can play PONG in two modes. Singleplayer or Multiplayer.


Singleplayer mode is rather simple. You play against Computer and the goal is to score more points. On the other hand, we have Multiplayer
mode. For mulitplayer mode you need TWO phones. On both phones you need to install this game. To start the game you also need to be in same
Local network because the game is played via UDP connection. One player is randomly choosen as client and the other one is server. On the
server side the game is really played and the client is just receving a ton of UDP packets about every possible position in game. The client
also sends their position to server. The game ends when one of players exits the app.
