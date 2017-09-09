# MTGStream
Software used to aid streaming tabletop magic with OBS
## Prerequisites
For use with OBS which can be downloaded at https://obsproject.com/
## How to use
Once the project is running, there are 2 different tabs. The "Card Panel" tab is used for modifying
the current state of the game being streamed,
while the "Players" panel is used to keep track of which players are involved in the tournament. 
### - - Card Panel Tab - -
#### Player life, poison, and win input fields
The top input fields are of type JSpinner and are used to keep track of player Life Totals, Poison Counters,
and Wins in the given round. The ones on the left correspond with the left player, and the opposite is true for the right.

#### Card Search Field
The next input field is a text field used to search cards. Once 4 characters are typed into the box, it will
automatically search for any card that contains those characters. It's kind of slow during this search, but every
consecutive letter typed will only search through the cards gathered from those initial 4 letters.
This reduces extraneous calls to the API.
Because of the above behavior, the search button isn't actually necessary anymore. 

#### Card List Box
Once a a list of cards has appeared in the box below, clicking it will display it in the card viewer on the right. 

#### Reset Life Button
The "Reset Life" button will reset the Life Totals and Poison Counters for both players but NOT the wins. This button should be pressed after every game of a round.

#### Reset All Button
The "Reset All" button will reset everything. Life Totals go to 20, Poison to 0, Wins to 0.

#### Apply Everything Button
Until "Apply Everything" is pressed, no changes will be able to be seen on OBS

###  - - Players Tab - - 
#### Far Left List Box
The box on the far left contains players loaded from "players.json". It acts as a database of all the players who have played magic
at CBS. The Textfield above this box performs the same way as the Card Search text field in the Card Panel Tab. To add a player to 
the list, press the "Add Player" button, fill in the information, and press OK. To edit a player already in the list, double click that
player, alter the information, and press OK. To save the players to "players.json", press the "Save Players" button. To fill the center
two boxes, highlight players from the leftmost listbox and press "Confirm Selection". You can select multiple players by holding CTRL
and clicking or CMD for Mac. A current bug is that you can add duplicate players.

#### Pairing Setup List Boxes
The two center boxes will be identical once "Confirm Selection" is pressed. Select one from each side and press "Pair" to add them to
the far right Match Pair box. If you want to remove all of the participating players and matches, press "Reset Players"

#### Match Pair List Box
Contains all of the pairs you have set up. Selecting a single match and pressing "Set Active Match" will save the players' info of that 
match to files corresponding to the fields. "Generate Standings" will generate standings for all matches and save them to a file. The
"Reset Pairings" button will clear the pairings once a round is over. 

# Configuring with OBS
In OBS, add a new source of type Text (FreeType 2). Give it a relevant name like "leftLife". This source will be used to keep track
of the left player's life. Click the "Read From File" checkbox and then browse to the file in the project directory called "dataFiles/leftLife.txt".
Repeat this process for all of the text fields.

For the image, do the same thing except create a source of type Image.
